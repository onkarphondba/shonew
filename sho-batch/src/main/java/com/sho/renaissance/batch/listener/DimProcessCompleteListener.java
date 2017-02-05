package com.sho.renaissance.batch.listener;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.sho.renaissance.batch.cif.LogActivityRequest;
import com.sho.renaissance.batch.cif.LogProcessActivityRequest;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * 
 * This class adds entry in DIM on completion of process
 * @author parthaka
 *
 */
public class DimProcessCompleteListener implements StepExecutionListener
{
	AmazonSQSClient amazonSQSClient;
	private static Log logger = LogFactory.getLog(DimProcessCompleteListener.class);
	

	@Override
	public void beforeStep(StepExecution stepExecution) {
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		String dimQueueUrl=null;	
		String jsonRequest = null;
		LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
		Date startDate = stepExecution.getStartTime();
		JobParameters jobParameters =  stepExecution.getJobParameters();
		String status = BatchConstants.CIF_PROCESSED;
		String comment = jobParameters.getString("entityType") + " Successfully Completed";
		
		
		LogProcessActivityRequest logActivityRequest;
		try {
			logActivityRequest = logActivityRequestTransformer.transform((Date)startDate, jobParameters.getString("entityType"), status,jobParameters.getString("correlationId"), comment,0, jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"));
		
		ObjectMapper objMapper = new ObjectMapper();
		jsonRequest = objMapper.writeValueAsString(logActivityRequest);
		dimQueueUrl =CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.DIM_QUEUE);
		SendMessageResult result =  amazonSQSClient.sendMessage(dimQueueUrl,jsonRequest);
		return ExitStatus.COMPLETED;
		
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return ExitStatus.FAILED;
		}
		
	}

	public AmazonSQSClient getAmazonSQSClient() {
		return amazonSQSClient;
	}

	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}
	
}
