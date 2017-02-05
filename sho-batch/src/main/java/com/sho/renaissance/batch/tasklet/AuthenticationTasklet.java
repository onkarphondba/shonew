package com.sho.renaissance.batch.tasklet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import com.capgemini.cif.core.exception.GenericCoreException;
import com.concretepage.wsdl.AuthenticateUser;
import com.concretepage.wsdl.AuthenticateUserResponse;
import com.concretepage.wsdl.ObjectFactory;
import com.sho.renaissance.batch.util.JEClient;

/**
 * @author PRKARAMB
 * 
 * This class calls JustEnough Authentication API
 *
 */
public class AuthenticationTasklet implements Tasklet, StepExecutionListener {
	private static Log logger = LogFactory.getLog(AuthenticationTasklet.class);
	private JEClient soapClient;
	private static final String TOKEN = "__token";
	private String jeCompany;
	private String jeUsername;
	private String jePassword;
	
	public JEClient getSoapClient() {
		return soapClient;
	}

	public void setSoapClient(JEClient soapClient) {
		this.soapClient = soapClient;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("********************************Started Invoking JE Authentication API***********************************");

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("********************************Finished Invoking JE Authentication API***********************************");
		return null;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws GenericCoreException {
		ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();
		
		ObjectFactory objectFactory = new ObjectFactory();

		//form JustEnough user authentication object
		AuthenticateUser user = objectFactory.createAuthenticateUser();
		user.setCompany(objectFactory.createAuthenticateUserCompany(jeCompany));
		user.setUsername(objectFactory.createAuthenticateUserUsername(jeUsername));
		user.setPassword(objectFactory.createAuthenticateUserPassword(jePassword));
		user.setToken(objectFactory.createAuthenticateUserToken(objectFactory.createAuthenticationToken()));

		//call to JustEnough Authentication API  
		AuthenticateUserResponse response = soapClient.AuthenticateUser(user);
		if(response.getAuthenticateUserResult().getValue().getResult().get(0).getSeverity().getValue().equals("Error")){
			throw new GenericCoreException("Authentication failed :"+response.getAuthenticateUserResult().getValue().getResult().get(0).getMessage().getValue());
		} else {
			jobContext.put(TOKEN, response.getToken().getValue());
		}
		return null;
	}

	public void setJeCompany(String jeCompany) {
		this.jeCompany = jeCompany;
	}

	public void setJeUsername(String jeUsername) {
		this.jeUsername = jeUsername;
	}

	public void setJePassword(String jePassword) {
		this.jePassword = jePassword;
	}
	
}