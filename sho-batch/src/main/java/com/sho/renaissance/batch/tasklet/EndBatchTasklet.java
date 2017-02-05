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
import com.concretepage.wsdl.EndBatch;
import com.concretepage.wsdl.EndBatchResponse;
import com.concretepage.wsdl.ObjectFactory;
import com.sho.renaissance.batch.util.JEClient;

/**
 * @author PRKARAMB
 * 
 * This class calls JustEnough EndBatch API
 *
 */
public class EndBatchTasklet implements Tasklet, StepExecutionListener {

	private JEClient soapClient;
	private static final String BATCH_ID = "__batchId";
	private static final String TOKEN = "__token";
	private static Log logger = LogFactory.getLog(EndBatchTasklet.class);
	
	public JEClient getSoapClient() {
		return soapClient;
	}

	public void setSoapClient(JEClient soapClient) {
		this.soapClient = soapClient;
	}
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("********************************Invoking EndBatch***********************************");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("********************************Finished Invoking EndBatch***********************************");
		return null;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws GenericCoreException {
		ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();
		//form JustEnough Endbatch object
		ObjectFactory objectFactory = new ObjectFactory();
		AuthenticationToken token = (AuthenticationToken) jobContext.get(TOKEN);
		Long batchId = (Long) jobContext.get(BATCH_ID);
		EndBatch endBatch = objectFactory.createEndBatch();
		endBatch.setBatchId(objectFactory.createEndBatchBatchId(batchId));
		endBatch.setToken(objectFactory.createEndBatchToken(token));
		
		EndBatchResponse  endBatchResponse = soapClient.EndBatch(endBatch);
		if(endBatchResponse.getEndBatchResult().getValue().getResult() != null && endBatchResponse.getEndBatchResult().getValue().getResult().get(0).getSeverity().getValue().equals("Success")){
			if(jobContext.containsKey(TOKEN)){
				jobContext.remove(TOKEN);
			}
			
			if(jobContext.containsKey(BATCH_ID)){
				jobContext.remove(BATCH_ID);
			}
			
		}else if(endBatchResponse.getEndBatchResult().getValue().getResult().get(0).getSeverity().getValue().equals("Error")){
			throw new GenericCoreException("EndBatch failed :"+endBatchResponse.getEndBatchResult().getValue().getResult().get(0).getMessage().getValue());
		}
		return null;
	}

}
