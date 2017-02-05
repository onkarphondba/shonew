package com.sho.renaissance.batch.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.Message;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.launcher.AgentJavaProcessHelper;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * @author ophondba
 * 
 * This class is message listener for POAndTransfers process MessageListener
 */
public class CnvCustInfoProcessMessageListener extends MasterMessageListener {
	
	private static Log logger = LogFactory.getLog(CnvCustInfoProcessMessageListener.class);
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	private AgentJavaProcessHelper agentJavaProcessHelper;
	
	/** 
	 * This method is responsible for getting correct correlationId for a process job
     * If completed then invoke batch job for current process else send error message to IEM.
     * 
     * @param message
	 */
	@Override
	public void onMessage(Message message) {
		String correlationId=null;
		try {
			 logger.debug(" Calling onMessage for listener POAndTransfers process Message Listener .........................");
			
			 correlationId = message.getStringProperty(BatchConstants.CORRELATION_ID);
	
			if(correlationId==null){
				throw new NullArgumentException(correlationId);
			}
		
			String source = null;
			
			agentJavaProcessHelper.invokeBatchJob(
					intiBatchContext(BatchConstants.POS_CNV_CUSTINFO_ENTITY, correlationId, BatchConstants.BATCH_UPLOAD_TO_NS,
							"", BatchConstants.SHC, BatchConstants.NETSUITE,
							CommonUtils.getInterfaceId(BatchConstants.PO_AND_TRANSFERS_FRIENDLY_NAME)));
			
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
		map.put(BatchConstants.SOURCE_SYSTEM, new JobParameter(BatchConstants.SHC));
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
