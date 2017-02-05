package com.sho.renaissance.batch.tasklet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.capgemini.cif.core.domain.BatchItem;
import com.capgemini.cif.core.domain.Parameter;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.exception.GenericCoreException;
import com.capgemini.cif.core.service.IBatchItemService;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * This class is used for calculating threshold
 * 
 * @author gdhimate
 * 
 */
public class ThresholdCalculationTasklet implements Tasklet, StepExecutionListener {

	private static Log logger = LogFactory.getLog(ThresholdCalculationTasklet.class);

	private JobParameters jobParameters;
	private String queueUrl;
	private String serviceUrl;
	private String correlationId;
	private String entityType;
	private String PROCESS_QUEUE = "sho.xxx.yyy.queue";

	private String nextFlow;
	private IBatchItemService batchItemService;
	private AmazonSQSClient amazonSQSClient;
	private DimProcessExcecutionListner dimProcessExcecutionListner;

	@Override
	public ExitStatus afterStep(StepExecution arg0) {
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {

		serviceUrl = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.CIF_PARAMETER_HOSTNAME)
				+ CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
						BatchConstants.CIF_PARAMETER_URI);
		jobParameters = stepExecution.getJobParameters();
		correlationId = jobParameters.getString(BatchConstants.CORRELATION_ID);
		entityType = jobParameters.getString(BatchConstants.ENTITY_TYPE);
		queueUrl = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.QUEUE_URL)
				+ CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
						PROCESS_QUEUE.replace("xxx", entityType.toLowerCase()).replace("yyy", nextFlow));
	}

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		Parameter parameter = new Parameter();
		parameter.setObjectId(BatchConstants.OBJECT_ID);
		parameter.setProcessId(entityType);
		parameter.setSrcSysId(BatchConstants.SOURCE_SYSTEM_NETSUITE);

		Parameter responseParameter = new Parameter();

		// Call IEM to get threshold
		responseParameter = (Parameter) CommonUtils.postJSONForObject(serviceUrl, parameter);

		if (batchItemService.isThresholdBreached(Integer.parseInt(responseParameter.getValue().toString()),
				correlationId, entityType)) {
				logger.debug("Threshold has been breached \n Calling IEM........");
			throw ExceptionHandler.createException("sho.technical.error.business.thresholdbreach",
					entityType);
		} else {
			List<com.capgemini.cif.core.domain.BatchItem> batchItemList = batchItemService.getBatchItems(correlationId,
					entityType, false);
			if (null != batchItemList && batchItemList.size() > 0) {
				List<String> errorMessages = new ArrayList<String>();

				for (BatchItem item : batchItemList) {
					errorMessages.add(item.getErrorMessage());
				}
				// send all error messages to IEM
				sendErrorMessageToIEM(errorMessages);
			}
			sendMessage();
			return RepeatStatus.FINISHED;
		}
	}

	/**
	 * Send message to SQS queue to start next job
	 * 
	 * @throws Exception
	 * 
	 */
	private void sendMessage() throws GenericCoreException {

		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.withMessageBody("Start Next Job");

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put(BatchConstants.CORRELATION_ID,
				new MessageAttributeValue().withDataType("String").withStringValue(correlationId));

		sendMessageRequest.withMessageAttributes(messageAttributes);

		if (queueUrl == null) {
			throw new GenericCoreException(
					"Queue URL is not valid , Check whether property exits in the property file or not");
		}
		sendMessageRequest.setQueueUrl(queueUrl);

			logger.debug("Started  : Sending message to Queue [" + queueUrl + "]");
		amazonSQSClient.sendMessage(sendMessageRequest);
			logger.debug("Finished : Sending message to Queue [" + queueUrl + "]");
	}

	public AmazonSQSClient getAmazonSQSClient() {
		return amazonSQSClient;
	}

	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}

	public IBatchItemService getBatchItemService() {
		return batchItemService;
	}

	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}

	public String getNextFlow() {
		return nextFlow;
	}

	public void setNextFlow(String nextFlow) {
		this.nextFlow = nextFlow;
	}

	public DimProcessExcecutionListner getDimProcessExcecutionListner() {
		return dimProcessExcecutionListner;
	}

	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}

	/**
	 * Method to send exception to IEM
	 * 
	 * @param exception
	 * @param correlationId
	 */
	@SuppressWarnings("unchecked")
	private void sendErrorMessageToIEM(List<String> errorMessageList) {
		dimProcessExcecutionListner.sendMessageListToIem(errorMessageList, jobParameters, new Date(),
				BatchConstants.TECHNICAL_ERROR);
	}

}
