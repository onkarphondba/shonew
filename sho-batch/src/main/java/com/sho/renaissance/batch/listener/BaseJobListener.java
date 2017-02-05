package com.sho.renaissance.batch.listener;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.capgemini.cif.core.exception.GenericCoreException;
import com.sho.renaissance.batch.cif.LogActivityRequest;
import com.sho.renaissance.batch.cif.LogProcessActivityRequest;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**Class extends JobExecutionListener for enhancing the
 * logging capabilities with IEM. 
 * @author gdhimate
 *
 */
public class BaseJobListener implements JobExecutionListener {

	private static Log logger = LogFactory.getLog(BaseJobListener.class);
	AmazonSQSClient amazonSQSClient;

	public AmazonSQSClient getAmazonSQSClient() {
		return amazonSQSClient;
	}

	public void setAmazonSQSClient(AmazonSQSClient amazonSQSClient) {
		this.amazonSQSClient = amazonSQSClient;
	}

	public BaseJobListener()
	{
		
	}
	
	/** Method prints job execution parameters in the console, logger and <br>
	 * Sends relevant information to IEM.
	 * @param jobExecution
	 */ 
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Job [" + jobExecution.getJobInstance().getJobName() + "], Job Id [" + jobExecution.getJobId()
				+ "], Started @ " + jobExecution.getStartTime());

		final StringBuilder jobMetaData = new StringBuilder();
		jobMetaData.append("Job-Parameter: \n");
		JobParameters jp = jobExecution.getJobParameters();
		for (Iterator<Entry<String, JobParameter>> i = jp.getParameters().entrySet().iterator(); i.hasNext();) {
			Entry<String, JobParameter> entry = i.next();
			jobMetaData.append("  " + entry.getKey() + "=" + entry.getValue() + "\n");
			
		}
		
		String jobName = jobExecution.getJobInstance().getJobName();
		String interfaceName = jp.getString("entityType");
		
		logInDIM(jp, BatchConstants.CIF_INPROCESS, jobName+" "+BatchConstants.CIF_INPROCESS, "0");
		
		logger.debug(jobMetaData);

	}

	/** Method prints job execution parameters in the console, logger and <br>
	 * Sends relevant information to IEM.
	 * @param jobExecution
	 * @param StepExecution 
	 */ 
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("Job [ Name: " + jobExecution.getJobInstance().getJobName() + " \t ID: " + jobExecution.getJobId()
				+ " ] Ended @ " + jobExecution.getEndTime() + "\n");

		Date jobStart = jobExecution.getStartTime();
		Date jobEnd = jobExecution.getEndTime();
		long duration = jobEnd.getTime() - jobStart.getTime();
		long duratoinS = duration / 1000 % 60;
		long durationM = duration / (60 * 1000) % 60;
		long durationH = duration / (60 * 60 * 1000);

		// ExecutionContext executionContext
		// =jobExecution.getExecutionContext();
		final StringBuilder jobMetaData = new StringBuilder();
		jobMetaData.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		jobMetaData.append("+++++++++++++ B A T C H   S U M M A R Y +++++++++++++++++ \n");
		jobMetaData.append("  Job Name    : " + jobExecution.getJobInstance().getJobName() + "\n");
		// jobMetaData.append(" Process Execution ID : "
		// +executionContext.getLong("__processid") +"\n");
		jobMetaData.append("  Duration    : " + durationH + "hrs :" + durationM + "mins :" + duratoinS + "s\n");
		jobMetaData.append("  Exit-Code   : " + jobExecution.getExitStatus().getExitCode() + "\n");
		jobMetaData.append("  Exit-Descr. : " + jobExecution.getExitStatus().getExitDescription() + "\n");
		jobMetaData.append("  Status      : " + jobExecution.getStatus() + "\n");
		jobMetaData.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		logger.info(jobMetaData);
		
		JobParameters jp = jobExecution.getJobParameters();
		String jobName = jobExecution.getJobInstance().getJobName();
		String interfaceName = jp.getString("entityType");
		String comment="";
		
		if(jobExecution.getExitStatus().getExitCode().equals("FAILED"))
		{
			List<Throwable> exeptions = jobExecution.getAllFailureExceptions();
			List<StepExecution> stpExec = (List<StepExecution>) jobExecution.getStepExecutions();
			
			Optional<StepExecution>  stepExecStream =  stpExec.stream().filter(s->s.getStatus().equals(BatchStatus.FAILED)).findAny();
			
			if(!exeptions.isEmpty() && exeptions.get(0) instanceof GenericCoreException)
			{
			 GenericCoreException genericCore = (GenericCoreException) exeptions.get(0);	
			 comment = genericCore.getExceptionCode()+": " + genericCore.getMessage();
			 logInDIM(jp, BatchConstants.CIF_ERROR,comment,"0");
			}
			else
			{
				String stepName = stepExecStream.isPresent() ? stepExecStream.get().getStepName():null ;
				comment = "T0099: Failed job is "+ jobExecution.getJobInstance().getJobName()+ ". Failed Step name is " + stepName;
				logInDIM(jp, BatchConstants.CIF_ERROR,comment,"0");
			}
			return;
		}
		
		
		if(null!=jobExecution.getExecutionContext().get(BatchConstants.RECORD_COUNT))
		{
		  comment = "Interface name: "+interfaceName + "\n"+ "Status: SUCCESS  " + "\nTotal Records: "+jobExecution.getExecutionContext().get(BatchConstants.RECORD_COUNT)  + " \nUnsuccessful records: " 
				+jobExecution.getExecutionContext().get(BatchConstants.UNPROCESSED_RECORD_COUNT);
		  
		  logInDIM(jp, BatchConstants.SUCCESS,comment,jobExecution.getExecutionContext().get(BatchConstants.UNPROCESSED_RECORD_COUNT).toString());
		  return;
		}
		
		logInDIM(jp, BatchConstants.CIF_PROCESSED, jobName+" "+BatchConstants.CIF_PROCESSED,"0");  //for job logging
		

	}

	
	/** Give Rest call to IEM to log in the job parameters with the status of the execution.
	 * @param jobParameters
	 * @param jobName
	 * @param status
	 */
	public void logInDIM(JobParameters jobParameters, String status ,String comment, String errornousRecords) {
		
		String dimQueueUrl=null;	
		String jsonRequest = null;
		
		
		LogActivityRequest logActivityRequestTransformer = new LogActivityRequest();
		
		try
		{
			LogProcessActivityRequest logActivityRequest = logActivityRequestTransformer.transform((Date)new Date(), jobParameters.getString("entityType"), status,jobParameters.getString("correlationId"), comment, Integer.valueOf(errornousRecords),jobParameters.getString("sourceSystem"), jobParameters.getString("destinationSystem"));
			ObjectMapper objMapper = new ObjectMapper();
			jsonRequest = objMapper.writeValueAsString(logActivityRequest);
			logger.info(jsonRequest);
			dimQueueUrl =CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.SQS_URI)+CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.DIM_QUEUE);
		}
		catch(Exception ee)
		{
			logger.debug(ee.getMessage());
		}
		
		amazonSQSClient.sendMessage(dimQueueUrl,jsonRequest);
		
	}
}