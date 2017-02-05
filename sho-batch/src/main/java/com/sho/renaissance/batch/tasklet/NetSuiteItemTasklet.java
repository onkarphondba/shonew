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
import com.concretepage.wsdl.AuthenticationToken;
import com.concretepage.wsdl.BeginBatch;
import com.concretepage.wsdl.BeginBatchResponse;
import com.concretepage.wsdl.ObjectFactory;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.JEClient;

/**
 * @author PRKARAMB
 *
 * This class calls JustEnough BeginBatch API
 */
public class NetSuiteItemTasklet implements Tasklet, StepExecutionListener {
	private static Log logger = LogFactory.getLog(NetSuiteItemTasklet.class);
	private JEClient soapClient;
	private static final String BATCH_ID = "__batchId";
	private static final String TOKEN = "__token";
	
	public JEClient getSoapClient() {
	return soapClient;
    }

     public void setSoapClient(JEClient soapClient) {
	this.soapClient = soapClient;
    }
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("********************************Invoking BeginBatch***********************************");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("********************************Finished Invoking BeginBatch***********************************");
		return null;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws GenericCoreException {
		ObjectFactory objectFactory = new ObjectFactory();
		ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();
		AuthenticationToken token = (AuthenticationToken) jobContext.get(TOKEN);
		//form BeginBatch object
		BeginBatch batch = objectFactory.createBeginBatch();
		batch.setBatchId(objectFactory.createBeginBatchBatchId(null));
		batch.setToken(objectFactory.createAuthenticateUserToken(token));
		BeginBatchResponse response = soapClient.BeginBatch(batch);
		
		if(BatchConstants.CIF_ERROR.equalsIgnoreCase(response.getBeginBatchResult().getValue().getResult().get(0).getSeverity().getValue())){
			throw new GenericCoreException("BeginBatch failed :"+response.getBeginBatchResult().getValue().getResult().get(0).getMessage());
		} else {
			jobContext.put(BATCH_ID, response.getBatchId().getValue());
		}
		
		return null;
	}

}
