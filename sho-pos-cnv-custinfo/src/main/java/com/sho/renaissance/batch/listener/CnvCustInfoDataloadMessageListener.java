package com.sho.renaissance.batch.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.runtime.directive.Break;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import com.sho.renaissance.batch.launcher.AgentJavaProcessHelper;
import com.sho.renaissance.batch.util.CommonUtils;
import com.capgemini.cif.core.service.IBatchItemService;
import com.sho.renaissance.batch.constants.BatchConstants;

/**
 * @author parthaka
 * 
 * This class is message listener for POAndTransfers Dataload MessageListener
 */
public class CnvCustInfoDataloadMessageListener extends MasterMessageListener {
	
	private static Log logger = LogFactory.getLog(CnvCustInfoDataloadMessageListener.class);
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	private AgentJavaProcessHelper agentJavaProcessHelper;
	private TextMessage textMessage;
	private String source;
	
	/** 
	 * This method is responsible for getting correct correlationId for a dataload job
     * If completed then invoke batch job for current process else send error message to IEM.
     * 
     * @param message
	 */
	@Override
	public void onMessage(Message message) {
		String custInfo = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.CUSTCNVINFO_FILE_PREFIX);
		//String custInfo2 = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.CUSTCNVINFO_FILE2_PREFIX);
		String correlationId = null;
		source = null;
		
		try {
			
			logger.debug(" Calling onMessage for CNV-CustomerInfo Dataload Message Listener .........................");
			
			if (!filterQueueMessages(message, custInfo)) {
				return;
			}
			
			/*else if (!filterQueueMessages(message, custInfo2)) {
				
			} */
			
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
			}
			
			String fileName = null;
			String isRestartable = message.getStringProperty(BatchConstants.IS_RESTARTABLE);
			
			if (null != isRestartable) {
				fileName = message.getStringProperty(BatchConstants.FILE_NAME);
				correlationId = message.getStringProperty(BatchConstants.CORRELATION_ID);
			}
			
			else {
				fileName = CommonUtils.getFileName(textMessage);
				correlationId = UUID.randomUUID().toString();
				dimProcessExcecutionListner.initialBatchEntery(BatchConstants.POS_CNV_CUSTINFO_ENTITY.toLowerCase(),
						correlationId, BatchConstants.SHC, BatchConstants.NETSUITE);
			}
			
			
			if (correlationId == null || fileName == null) {
				throw new NullArgumentException(BatchConstants.CORRELATIONID_OR_FILENAME_NOT_FOUND);
			}
			
			
			if(fileName.equalsIgnoreCase("CashSale_3H_CustomerInfo_dev_20170201.txt")){
				source ="3HCOM";
			}
			
			else
			{
				source ="OUTLETCOM";
			}
			
			
			agentJavaProcessHelper.invokeBatchJob(intiBatchContext(BatchConstants.POS_CNV_CUSTINFO_ENTITY,correlationId, 
					BatchConstants.BATCH_DATA_LOAD, fileName, source, BatchConstants.NETSUITE));
			
		} catch (Exception e) {
			logger.error("" + e);
			sendErrorMessageToIEM(e, correlationId);
		} 
	}
	
	/**
	 * Method to send exception to IEM
	 * 
	 * @param exception
	 * @param correlationId
	 */	
	@SuppressWarnings("unchecked")
	private void sendErrorMessageToIEM(Exception exception, String correlationId) {
		List<Throwable> exeptions = new ArrayList<Throwable>();
		exeptions.add(exception);
		Map map = new HashMap<String, JobParameter>();
		map.put(BatchConstants.CORRELATION_ID, new JobParameter(correlationId));
		map.put(BatchConstants.ENTITY_TYPE, new JobParameter(BatchConstants.POS_CNV_CUSTINFO_ENTITY));
		map.put(BatchConstants.SOURCE_SYSTEM, new JobParameter(source));
		map.put(BatchConstants.DESTINATION_SYSTEM, new JobParameter(BatchConstants.NETSUITE));
		JobParameters jobParameters = new JobParameters(map);
		dimProcessExcecutionListner.sendToIEM(exeptions, jobParameters, new Date());
	}

	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}

	public void setAgentJavaProcessHelper(AgentJavaProcessHelper agentJavaProcessHelper) {
		this.agentJavaProcessHelper = agentJavaProcessHelper;
	}
	
}
	