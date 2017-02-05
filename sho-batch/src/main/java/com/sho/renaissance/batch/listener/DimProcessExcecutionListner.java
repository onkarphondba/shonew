package com.sho.renaissance.batch.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.dao.EmptyResultDataAccessException;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.capgemini.cif.core.dao.IBatchItemDao;
import com.capgemini.cif.core.domain.BatchItem;
import com.sho.renaissance.batch.cif.LogActivityErrorRequest;
import com.sho.renaissance.batch.cif.LogActivityRequest;
import com.sho.renaissance.batch.cif.LogProcessActivityErrorRequest;
import com.sho.renaissance.batch.cif.LogProcessActivityRequest;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * 
 * This class makes entry of process in DIM on start
 * @author parthaka
 *
 */
public class DimProcessExcecutionListner implements StepExecutionListener {

	private static Log logger = LogFactory.getLog(DimProcessExcecutionListner.class);
	
	AmazonSQSClient amazonSQSClient;
	IBatchItemDao batchItemDao;
	private Integer sequenceNumber = 0;
	
	
	
	public void predcessorsStatusEntry(String entityType, String corelationId, String sourceSystem, String destinationSystem, String predecessorName, Date predecessorsStartTime, Date predecessorsEndTime)
	{
		LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
		LogProcessActivityRequest logActivityRequest = null;
		String jsonRequest;
		String comment = "Predecessors " + predecessorName +  " Successfully Completed. Start Time is: " + predecessorsStartTime + "\n End Time is: " + predecessorsEndTime;
		try
		{
			logActivityRequest = logActivityRequestTransformer.transform(predecessorsStartTime, entityType.toUpperCase(), BatchConstants.SUCCESS, corelationId, comment,0, sourceSystem, destinationSystem);
			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logActivityRequest);
			
		    String serviceUrl = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.CIF_PARAMETER_HOSTNAME)
				+ CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
						BatchConstants.CIF_DIM_ACTIVITY_URI);
		
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(serviceUrl);
		StringEntity input = new StringEntity(jsonRequest);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		httpClient.getConnectionManager().shutdown();
		httpClient.close();

		BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
		
		}
		catch(Exception ee)
		{
			logger.debug(ee);
		}
	
   }
	
	
	
	
	public void initialBatchEntery(String entityType, String corelationId, String sourceSystem, String destinationSystem ) throws ClientProtocolException, IOException
	{
		String dimQueueUrl=null;	
		String jsonRequest = null;
		LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
		LogProcessActivityRequest logActivityRequest = null;
		
		
		try
		{
			logActivityRequest = logActivityRequestTransformer.transform((Date)new Date(), entityType.toUpperCase(), BatchConstants.CIF_INPROCESS, corelationId, entityType+" interface Started",0, sourceSystem, destinationSystem);
			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logActivityRequest);
			dimQueueUrl =CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.DIM_QUEUE);
		

		//Object obj = CommonUtils.postJSONForObject("http://localhost:8082/cif/cifrs/v1/dim/activity", logActivityRequest);
		
		
		String serviceUrl = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.CIF_PARAMETER_HOSTNAME)
				+ CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
						BatchConstants.CIF_DIM_ACTIVITY_URI);
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(serviceUrl);
		StringEntity input = new StringEntity(jsonRequest);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		
		
		BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
		
        httpClient.getConnectionManager().shutdown();
		httpClient.close();
		
		}
		catch(Exception ee)
		{
			logger.debug(ee);
		}

	}
	
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		
		String dimQueueUrl=null;	
		String jsonRequest = null;
		JobParameters jobParameters =  stepExecution.getJobParameters();
		String stepName = stepExecution.getStepName();
		LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
		
		try
		{
			LogProcessActivityRequest logActivityRequest = logActivityRequestTransformer.transform((Date)new Date(), jobParameters.getString("entityType"), BatchConstants.CIF_INPROCESS,jobParameters.getString("correlationId"),stepName+" Started",0,jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"));
			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logActivityRequest);
			dimQueueUrl =CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.DIM_QUEUE);
			logger.debug(objMapper.writeValueAsString(logActivityRequest.getProcessActivityHeader()));	
			amazonSQSClient.sendMessage(dimQueueUrl,jsonRequest);
		}
		catch(Exception ee)
		{
			logger.debug(ee);
		}
	
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		String dimQueueUrl=null;	
		String jsonRequest = null;
		JobParameters jobParameters =  stepExecution.getJobParameters();
		String stepName = stepExecution.getStepName() ;
		LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
		Date startDate = stepExecution.getStartTime();
		ExitStatus exitStatus = ExitStatus.COMPLETED;
		String comment = stepName + " Completed";
		String status = BatchConstants.CIF_PROCESSED;
		
		try
		{
		
		if(BatchStatus.FAILED.equals(stepExecution.getStatus()) || null == stepExecution.getStatus())
		{
			List<Throwable> exeptions = stepExecution.getFailureExceptions();
			sendToIEM(exeptions, jobParameters, startDate);
			exitStatus = ExitStatus.FAILED;
			status = BatchConstants.CIF_PROCESSED;
			comment = "Interface  name: "+jobParameters.getString("entityType")+"\nStatus: Failed"+ "\nErrorMessage: "+ exeptions.remove(0).toString();
			
			
		}
		
		
			LogProcessActivityRequest logActivityRequest = logActivityRequestTransformer.transform((Date)startDate, jobParameters.getString("entityType"), status,jobParameters.getString("correlationId"), comment,0, jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"));
			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logActivityRequest);
			dimQueueUrl =CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.DIM_QUEUE);
		
		logger.debug(objMapper.writeValueAsString(logActivityRequest.getProcessActivityHeader()));
		amazonSQSClient.sendMessage(dimQueueUrl,jsonRequest);
		return exitStatus;
		
		}
		catch(Exception ee)
		{
			logger.debug(ee);
			return ExitStatus.FAILED;
		}
	}
	
	
	public void  sendToIEM(List<Throwable> exception, JobParameters jobParameters, Date startDate)// throws JsonGenerationException, JsonMappingException, IOException, DatatypeConfigurationException 
	{
		String dimQueueUrl=null;	
		String jsonRequest = null;
		String errorCode = null;
		String comment = null;
		for(Throwable throwable: exception)
		{
		
			Exception ee = (Exception) throwable;

			if(ee.getClass().isInstance(new EmptyResultDataAccessException(0)))
			{
				errorCode = CommonUtils.getPropertyFromClassPath(BatchConstants.SHO_APPLICATION_PROPERTY_FILE,BatchConstants.RUNTIME_ERROR_CODE);
			}
			else if(ee.getClass().isInstance(new java.sql.SQLException()))
			{
				errorCode = CommonUtils.getPropertyFromClassPath(BatchConstants.SHO_APPLICATION_PROPERTY_FILE,BatchConstants.SQL_ERROR_CODE);
			}
			else if(ee.getClass().isInstance(new JobExecutionException("")))
			{
				errorCode = CommonUtils.getPropertyFromClassPath(BatchConstants.SHO_APPLICATION_PROPERTY_FILE,BatchConstants.RUNTIME_ERROR_CODE);
			}
			else 
			{
				errorCode = CommonUtils.getPropertyFromClassPath(BatchConstants.SHO_APPLICATION_PROPERTY_FILE,BatchConstants.RUNTIME_ERROR_CODE);		
			}
			
			String stackTrace = "";
			
			for(StackTraceElement stElement:throwable.getStackTrace())
			{
				
				stackTrace=stackTrace+stElement.toString()+"\n";
			}
		comment = errorCode+ ":" + throwable.toString()+"\n" + stackTrace;
		sequenceNumber++;
		try {
		LogActivityErrorRequest logActivityErrorRequestTransformer = new LogActivityErrorRequest();
		LogProcessActivityErrorRequest logProcessActivityErrorRequest = logActivityErrorRequestTransformer.transformIemMessage(startDate, jobParameters.getString("entityType").toUpperCase(), jobParameters.getString("correlationId"), comment,BatchConstants.TECHNICAL_ERROR,new ArrayList<String>(), jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"), sequenceNumber);
		ObjectMapper objMapper = new ObjectMapper();
	
			jsonRequest = objMapper.writeValueAsString(logProcessActivityErrorRequest);
		
		String iemQueueUrl=CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.IEM_QUEUE);
		logger.debug(objMapper.writeValueAsString(logProcessActivityErrorRequest.getProcessActivityHeader()));
		amazonSQSClient.sendMessage(iemQueueUrl,jsonRequest);
		
		} catch (Exception e) {
			logger.debug(e);
		} 
	  }

	}
	
	
    public void sendMessageListToIem(List<String> errorMessageList, JobParameters jobParameters, Date startDate, String errorType)
    {
           
           String jsonRequest = null;
           Integer processSequence = 0;
           processSequence+=sequenceNumber;
           LogActivityErrorRequest logActivityErrorRequestTransformer = new LogActivityErrorRequest();
           LogProcessActivityErrorRequest logProcessActivityErrorRequest;
           try {
                  logProcessActivityErrorRequest = logActivityErrorRequestTransformer.transformIemMessage(startDate, jobParameters.getString("entityType").toUpperCase(), jobParameters.getString("correlationId"), "",errorType, errorMessageList, jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"), processSequence);
                  ObjectMapper objMapper = new ObjectMapper();
                  jsonRequest = objMapper.writeValueAsString(logProcessActivityErrorRequest);
                  sequenceNumber = sequenceNumber + errorMessageList.size() + 1;
                  String iemQueueUrl=CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.IEM_QUEUE);
                  logger.debug(objMapper.writeValueAsString(logProcessActivityErrorRequest.getProcessActivityHeader()));
                  amazonSQSClient.sendMessage(iemQueueUrl,jsonRequest);
           } catch (Exception e) {
        	   logger.debug(e);               
           }
   }
	
	public AmazonSQSClient getAmazonSQSClient() {
		return amazonSQSClient;
	}

	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}
	

}
