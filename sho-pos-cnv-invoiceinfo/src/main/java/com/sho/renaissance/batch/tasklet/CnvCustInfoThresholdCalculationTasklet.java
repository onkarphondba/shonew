/*package com.sho.renaissance.batch.tasklet;

import java.sql.ResultSet;

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
import com.capgemini.cif.core.domain.Parameter;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.service.IBatchItemService;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;
import com.sho.renaissance.batch.util.CommonUtils;



*//**
 * @author parthaka
 * To check threshold limit of failed records
 *//*
public class CnvCustInfoThresholdCalculationTasklet implements Tasklet, StepExecutionListener {

	private static Log logger = LogFactory.getLog(CnvCustInfoThresholdCalculationTasklet.class);

	private JobParameters jobParameters;
	private String serviceUrl;
	private IBatchItemService batchItemService;
	private String correlationId;
	private String entityType;
	private AmazonSQSClient amazonSQSClient;
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	
	
	
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		serviceUrl = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.CIF_PARAMETER_HOSTNAME)
				+ CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
						BatchConstants.CIF_PARAMETER_URI);
		jobParameters = stepExecution.getJobParameters();
		correlationId = jobParameters.getString(BatchConstants.CORRELATION_ID);
		entityType = jobParameters.getString(BatchConstants.ENTITY_TYPE);
		
	}

	
	public Boolean isThresholdExceeded(Integer failedCount,Integer totalCount,Integer threshold) {
	if(null == threshold){
		threshold = 200;
	}
		double thresholdBreach = (totalCount * threshold) * 0.01;
		logger.debug("--- Threshold Value :" + threshold + "\n--- Total  Item :" + totalCount
				+ "\n--- Total Failed Item :" + failedCount + "\n--- Threshold :" + thresholdBreach);
		if (failedCount >= thresholdBreach) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		Parameter parameter = new Parameter();
		parameter.setObjectId(BatchConstants.OBJECT_ID);
		parameter.setProcessId(entityType);
		parameter.setSrcSysId("3HCOM");
		
		//Parameter responseParameter = new Parameter();
		
		//Call IEM to get threshold
	  //responseParameter=(Parameter) CommonUtils.postJSONForObject(serviceUrl,parameter);
		
		
		
		//ResultSet allRecords = batchItemService.getEnrichedOnhandQuantityTotalRecords();
		
		
		
		Integer  invalidRecordsCount = batchItemService.getCnvCustInfoInvalidRecordCount();
		
		
		
		Integer totalRecordCount = 	batchItemService.getCnvCustInfoTotalRecordCount();
            
			

		
	if (isThresholdExceeded(invalidRecordsCount,totalRecordCount,Integer.valueOf("200"responseParameter.getValue().toString()))) {
			 logger.debug("Threshold has been breached \n Calling IEM........");
			 throw ExceptionHandler.createException("sho.technical.error.business.thresholdbreach", BatchConstants.POS_CNV_CUSTINFO_ENTITY);
		} else{
	//			dimProcessExcecutionListner.sendToIEM(exceptions, jobParameters, new Date());
				return RepeatStatus.FINISHED;
		}
	}


	public IBatchItemService getBatchItemService() {
		return batchItemService;
	}
	
	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}

	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}


	public DimProcessExcecutionListner getDimProcessExcecutionListner() {
		return dimProcessExcecutionListner;
	}


	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}
}
*/