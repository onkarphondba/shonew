package com.sho.renaissance.batch.listener;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.capgemini.cif.core.domain.BatchItem;
import com.sho.renaissance.batch.cif.LogActivityErrorRequest;
import com.sho.renaissance.batch.cif.LogActivityRequest;
import com.sho.renaissance.batch.cif.LogProcessActivityErrorRequest;
import com.sho.renaissance.batch.cif.LogProcessActivityRequest;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;
import com.sun.media.jfxmedia.logging.Logger;

/**
 * This class monitors for number of records failed
 * @author parthaka
 *
 */
public class DimItemRecordCountListener implements StepExecutionListener, ChunkListener {

	private static Log logger = LogFactory.getLog(DimItemRecordCountListener.class);
	AmazonSQSClient amazonSQSClient;

	private int  recordReadCounter;
	private int sequenceNumber = 100;
	@Override
	public void beforeChunk(ChunkContext context) {


	}

	/** Method is executed before a chunk is executed, to get the count of the records read
	 * in an ongoing step.
	 * @param context
	 */ 
	@Override
	public void afterChunk(ChunkContext context) {
		String jsonRequest=null;
		String dimQueueUrl=null;

		StepContext stepContext = context.getStepContext();
		int recordReadCount = stepContext.getStepExecution().getReadCount();
		String stepName = stepContext.getStepName();
		recordReadCounter+=recordReadCount;
		JobParameters jobParameters =  stepContext.getStepExecution().getJobParameters();
		try
		{
			LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
			LogProcessActivityRequest logActivityRequest = logActivityRequestTransformer.transform(new Date(), jobParameters.getString("entityType"), BatchConstants.CIF_INPROCESS, jobParameters.getString("correlationId"), stepName+ "Inprocess", 0, jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"));
			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logActivityRequest);
			dimQueueUrl = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.DIM_QUEUE);

		SendMessageResult result = amazonSQSClient.sendMessage(dimQueueUrl,jsonRequest);
		}
		catch(Exception ee )
		{
            logger.debug(ee);
            
		}


	}

	/** Method is executed after a chunk is executed if there was an error
	 * while executing the chunk.<br>
	 * This will send the error to IEM with the job parameters.
	 * in an ongoing step.
	 * @param context
	 */ 
	@Override
	public void afterChunkError(ChunkContext context)
	{
		String dimQueueUrl=null;	
		String jsonRequest = null;
		String errorCode = null;
		String comment = context.getAttribute("sb_rollback_exception").toString();
		JobParameters jobParameters =  context.getStepContext().getStepExecution().getJobParameters();

		LogActivityErrorRequest logActivityErrorRequestTransformer = new LogActivityErrorRequest();
		LogProcessActivityErrorRequest logProcessActivityErrorRequest;
		try {
			sequenceNumber++;
			logProcessActivityErrorRequest = logActivityErrorRequestTransformer.transformIemMessage(new Date(), jobParameters.getString("entityType"), jobParameters.getString("correlationId"), comment,BatchConstants.TECHNICAL_ERROR,new ArrayList<String>(), jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"), sequenceNumber);

			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logProcessActivityErrorRequest);
		String iemQueueUrl=CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.IEM_QUEUE);
		SendMessageResult result = amazonSQSClient.sendMessage(iemQueueUrl,jsonRequest);

		} catch (Exception e) {
			logger.debug(e);
		}
 
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		recordReadCounter=0;
		return null;
	}

	public AmazonSQSClient getAmazonSQSClient() {
		return amazonSQSClient;
	}

	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}




}
