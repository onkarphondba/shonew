package com.sho.renaissance.batch.cif;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.sho.renaissance.batch.constants.BatchConstants;



/**
 * @author parthaka
 * to create request for DIM.
 */

public class LogActivityRequest  {

	HashMap<String, String> processMap;
	HashMap<String, Integer> processPayloadLevelMap;

	public LogProcessActivityRequest transform(Date date, String processName, String status,String correlationId,String comment, int errorRecordSize, String sourceSystem, String destinationSystem) throws DatatypeConfigurationException 
	{
	
		LogProcessActivityRequest logProcessActivityRequest = new LogProcessActivityRequest();
		ProcessActivityHeaderType processActivityHeaderType = null;

		processActivityHeaderType = createProcessActivityHeaderType(
				sourceSystem , destinationSystem, processName, date,correlationId);
        ProcessActivityBatchType processActivityBatch = createProcessActivityBatch(status, processName, date,comment,errorRecordSize);
		if (processActivityHeaderType != null) 
		{
			logProcessActivityRequest
			.setProcessActivityHeader(processActivityHeaderType);
			logProcessActivityRequest.setProcessActivityBatch(processActivityBatch);
		}

		return logProcessActivityRequest;
	}





	private ProcessActivityBatchType createProcessActivityBatch(String status, String ProcessName, Date date,String comment, int errorRecordSize) throws DatatypeConfigurationException
	{
		ProcessActivityBatchType processActivityBatch = new ProcessActivityBatchType();
		DatatypeFactory df =  DatatypeFactory.newInstance();
		processActivityBatch.setComment(comment); // job name 
		processActivityBatch.setStatusId(status); // its status
		processActivityBatch.setErrorRecordCount(errorRecordSize);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		processActivityBatch.setProcessStartDatetime(df
				.newXMLGregorianCalendar(gc));
		return processActivityBatch;
	}



	/**
	 * @param sourceSystem
	 * @param targetSystem
	 * @param processId
	 * @param processExecutionId
	 * @param createdDate
	 * @return
	 * @throws DatatypeConfigurationException 
	 * @throws Exception
	 * @desc Method to set Process activity header.
	 */
	private ProcessActivityHeaderType createProcessActivityHeaderType(
			String sourceSystem, String targetSystem, String processName,
			Date createdDate,String correlationId) throws DatatypeConfigurationException{
		ProcessActivityHeaderType processActivityHeaderType = new ProcessActivityHeaderType();

		DatatypeFactory df =  DatatypeFactory.newInstance();
		processActivityHeaderType.setProcessActivityHeaderId(correlationId);
		processActivityHeaderType.setOrganizationId(BatchConstants.ORGANIZATION_ID);
		processActivityHeaderType.setProcessId(processName);
		processActivityHeaderType.setSourceSystemId(sourceSystem);
		processActivityHeaderType.setDestinationSystemId(targetSystem);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(createdDate);
		processActivityHeaderType.setOriginalProcessDatetime(df
				.newXMLGregorianCalendar(gc));

		return processActivityHeaderType;
	}

}
