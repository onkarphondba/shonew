package com.sho.renaissance.batch.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;

import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.launcher.BatchAgentContext;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * @author gdhimate
 * 
 *         Master listener for all listener which will initiated the
 *         BatchAgentContext
 *
 */
public abstract class MasterMessageListener implements MessageListener {

	private static Log logger = LogFactory.getLog(MasterMessageListener.class);

	private static Date predecessorCompletedflag = null;
	private static String fileloadingCompletedFlag = null;
	private String FILENAME_POST_FIX = "xxx.properties"; // syncnssupersession.properties
	private String BATCH_FILENAME_PATTERN = "xxx.yyy.job.filename"; // syncnssupersession_dataload_job.xml
	private String BATCH_JOB_PATTERN = "xxx.yyy.job.name"; // syncnssupersession_dataload_job

	/**
	 * Initialize the job parameters and job context
	 * @param entity
	 * @param correlationId
	 * @param job
	 * @param fileName
	 * @param sourceSystem
	 * @param destinationSystem
	 * @param interfaceId
	 * @return
	 */
	public BatchAgentContext intiBatchContext(String entity, String correlationId, String job, String fileName,String sourceSystem,String destinationSystem ,String interfaceId) {

		// Set Batch Context
		String entityName = entity.trim().toLowerCase();
		String propertyFile = FILENAME_POST_FIX.replace("xxx", entityName);

		BatchAgentContext batchAgentContext = new BatchAgentContext();
		batchAgentContext.setContextFileName(CommonUtils.getPropertyFromClassPath(propertyFile,
				BATCH_FILENAME_PATTERN.replace("xxx", entityName).replace("yyy", job.toLowerCase())));
		batchAgentContext.setJobName(CommonUtils.getPropertyFromClassPath(propertyFile,
				BATCH_JOB_PATTERN.replace("xxx", entityName).replace("yyy", job.toLowerCase())));

		// Set job parameters
		Map<String, String> jobParams = new HashMap<>();
		jobParams.put(BatchConstants.CORRELATION_ID, correlationId);
		jobParams.put(BatchConstants.ENTITY_TYPE, entity);
		jobParams.put(BatchConstants.FILE_NAME, fileName);
		jobParams.put(BatchConstants.SOURCE_SYSTEM, sourceSystem);
		jobParams.put(BatchConstants.DESTINATION_SYSTEM, destinationSystem);
		jobParams.put(BatchConstants.INTERFACE_ID, interfaceId);
		batchAgentContext.setJobParams(jobParams);

			logger.debug(" BatchAgentContext \n File Name :" + batchAgentContext.getContextFileName()
					+ " \n Job Name : " + batchAgentContext.getJobName());

		return batchAgentContext;
	}

	/**
	 * Initialize the job parameters and job context
	 * @param entity
	 * @param correlationId
	 * @param job
	 * @param fileName
	 * @param sourceSystem
	 * @param destinationSystem
	 * @return
	 */
	public BatchAgentContext intiBatchContext(String entity, String correlationId, String job, String fileName,String sourceSystem,String destinationSystem) {

		return intiBatchContext(entity, correlationId, job, fileName, sourceSystem, destinationSystem, "");
	}

	/**
	 * @param message
	 * @param fileNamePrefix
	 * @return
	 * @throws JMSException
	 * @throws JSONException
	 */
	public boolean filterMessages(Message message, String fileNamePrefix) throws JMSException, JSONException {
		TextMessage textMessage = null;
		if (message instanceof TextMessage) {
			textMessage = (TextMessage) message;
		}
		String isRestartable = message.getStringProperty(BatchConstants.IS_RESTARTABLE);
		
		if (isRestartable == null ){
			String fileName = CommonUtils.getFileNameFromTopicMessage(textMessage);//.toUpperCase();
			if(null == fileName)
			{
				return false;
			}
			Integer fileNamePrefSize = fileNamePrefix.split("_").length;
			Integer fileNameSize = fileName.split("_").length;
			fileNamePrefSize++;
			
			if(!fileNamePrefSize.equals(fileNameSize))
			{
				return false;
			}
			
			if(!fileName.startsWith(fileNamePrefix)) {
				return false;
				}
			
		}

		return true;
     }

	
	/**
	 * @param message
	 * @param fileNamePrefix
	 * @return
	 * @throws JMSException
	 * @throws JSONException
	 */
	public boolean filterQueueMessages(Message message, String fileNamePrefix) throws JMSException, JSONException {
		TextMessage textMessage = null;
		if (message instanceof TextMessage) {
			textMessage = (TextMessage) message;
		}
		String isRestartable = message.getStringProperty(BatchConstants.IS_RESTARTABLE);
		
		if (isRestartable == null ){
			String fileName = CommonUtils.getFileName(textMessage);//.toUpperCase();
			if(null == fileName){
				return false;
			}
			Integer fileNamePrefSize = fileNamePrefix.split("_").length;
			Integer fileNameSize = fileName.split("_").length;
			fileNamePrefSize++;
			
			if(!fileNamePrefSize.equals(fileNameSize))
			{
				return false;
			}
			
			if(!fileName.startsWith(fileNamePrefix)) {
				return false;
				}
			
		}

		return true;
     }
	public static String getFileloadingCompletedFlag() {
		return fileloadingCompletedFlag;
	}

	public static void setFileloadingCompletedFlag(String fileloadingCompletedFlag) {
		MasterMessageListener.fileloadingCompletedFlag = fileloadingCompletedFlag;
	}

	public static Date getPredecessorCompletedflag() {
		return predecessorCompletedflag;
	}

	public static void setPredecessorCompletedflag(Date predecessorCompletedflag) {
		MasterMessageListener.predecessorCompletedflag = predecessorCompletedflag;
	}
}
