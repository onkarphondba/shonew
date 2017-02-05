package com.sho.renaissance.batch.tasklet;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.capgemini.cif.core.domain.Parameter;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.exception.GenericCoreException;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * This class is used for sending message on specified queue
 * 
 * @author snkesarw
 * 
 */
public class SendMessageToQueue implements Tasklet, StepExecutionListener{

	private static Log logger = LogFactory.getLog(SendMessageToQueue.class);

	private JobParameters jobParameters;
	private String queueUrl;
	private String correlationId;
	private String entityType;
	private String nextFlow;
	private AmazonSQSClient amazonSQSClient;

	private String queuePropertyName;

	@Override
	public ExitStatus afterStep(StepExecution arg0) {
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		jobParameters = stepExecution.getJobParameters();
		correlationId = jobParameters.getString(BatchConstants.CORRELATION_ID);
		entityType = jobParameters.getString(BatchConstants.ENTITY_TYPE);
		queueUrl= CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.QUEUE_URL)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,queuePropertyName);
	}

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		Parameter parameter = new Parameter();
		parameter.setObjectId(BatchConstants.OBJECT_ID);
		parameter.setProcessId(entityType);
		parameter.setSrcSysId(BatchConstants.SOURCE_SYSTEM_NETSUITE);

		sendMessage();
		return RepeatStatus.FINISHED;
	}

	/**
	 * Send message to SQS queue to start next job set by the job
	 * @throws GenericCoreException 
	 * @throws Exception 
	 * 
	 */
	private void sendMessage() throws GenericCoreException{

		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.withMessageBody(nextFlow);

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put(BatchConstants.CORRELATION_ID, new MessageAttributeValue().withDataType("String").withStringValue(correlationId));

		sendMessageRequest.withMessageAttributes(messageAttributes);

		if(queueUrl==null){
			throw ExceptionHandler.createException("sho.common.error.technical.endpointconnection.queue_url_missing",queueUrl);
		}
		sendMessageRequest.setQueueUrl(queueUrl);

		logger.debug("Started  : Sending message to Queue ["+queueUrl+"]");
		amazonSQSClient.sendMessage(sendMessageRequest);
		logger.debug("Finished : Sending message to Queue ["+queueUrl+"]");

	}

	public AmazonSQSClient getAmazonSQSClient() {
		return amazonSQSClient;
	}

	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}

	public String getNextFlow() {
		return nextFlow;
	}

	public void setNextFlow(String nextFlow) {
		this.nextFlow = nextFlow;
	}
	
	public String getQueuePropertyName() {
		return queuePropertyName;
	}

	public void setQueuePropertyName(String queuePropertyName) {
		this.queuePropertyName = queuePropertyName;
	}

}
