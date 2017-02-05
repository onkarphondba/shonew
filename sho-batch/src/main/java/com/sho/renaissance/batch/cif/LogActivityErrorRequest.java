package com.sho.renaissance.batch.cif;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.capgemini.cif.core.domain.BatchItem;
import com.sho.renaissance.batch.cif.LogProcessActivityErrorRequest;
import com.sho.renaissance.batch.cif.ProcessActivityErrorDetailType;
import com.sho.renaissance.batch.cif.ProcessActivityErrorType;
import com.sho.renaissance.batch.cif.ProcessActivityHeaderType;
import com.sho.renaissance.batch.cif.LogProcessActivityErrorRequest.ProcessActivityErrors;
import com.sho.renaissance.batch.cif.ProcessActivityErrorType.ProcessActivityErrorDetails;

public class LogActivityErrorRequest 
{

	// Header constants
	public static final String ORGANIZATION_ID = "SHO";
	public static final String DEFAULT_SOURCE_SYSTEM_ID = "NETSUITE";
	public static final String DEFAULT_DEST_SYSTEM_ID = "JE";
	
	public static final String PROCESS_ID_FLOW_VARIABLE = "SHO_Sample_Process";
	public static final String PROCESS_PAYLOAD = "_batchEntry";

	// Payload constants
	public static final String ERROR_TYPE = "TECHNICAL";
	public static final String STATUS_ID = "ERROR";
	public static final int PROCESS_LEVEL = 1;
	

	
	public LogProcessActivityErrorRequest transformIemMessage(Date createdDate, String processName, String correlationID, String comment, String errorType, List<String> errorMessageList, String sourceSystem, String destinationSystem, int sequenceNumber) throws DatatypeConfigurationException
	{
		
	    
        Integer processPayloadLevel = 1; //
        String errorMessage = comment;  //error message
        LogProcessActivityErrorRequest logProcessActivityErrorRequest = new LogProcessActivityErrorRequest();
        ProcessActivityErrors processActivityErrors = null;
        
        
        ProcessActivityHeaderType processActivityHeaderType = null;
        
        processActivityHeaderType = createProcessActivityHeaderType(correlationID,
				sourceSystem, destinationSystem, processName, createdDate);

		if (processActivityHeaderType != null) {
			logProcessActivityErrorRequest.setProcessActivityHeader(processActivityHeaderType);
		}

		
		processActivityErrors = createProcessActivityErrors(processPayloadLevel, errorMessage,createdDate, errorType, errorMessageList, sequenceNumber,sourceSystem);

		logProcessActivityErrorRequest.setProcessActivityErrors(processActivityErrors);
		
		return logProcessActivityErrorRequest;
	}

	
	private ProcessActivityHeaderType createProcessActivityHeaderType(
			String correlationID, String sourceSystem, String targetSystem, String processName,
			Date createdDate) throws DatatypeConfigurationException{
		ProcessActivityHeaderType processActivityHeaderType = new ProcessActivityHeaderType();

		DatatypeFactory df =  DatatypeFactory.newInstance();
		processActivityHeaderType.setProcessActivityHeaderId(correlationID);
		processActivityHeaderType.setOrganizationId(ORGANIZATION_ID);
		processActivityHeaderType.setProcessId(processName);
		processActivityHeaderType.setSourceSystemId(sourceSystem);
		processActivityHeaderType.setDestinationSystemId(targetSystem);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(createdDate);
		processActivityHeaderType.setOriginalProcessDatetime(df
				.newXMLGregorianCalendar(gc));

		// processActivityErrorType.setProcessDate(MuleDateConversionUtil.getXMLGregorianCalendar(new
		// Date()));
		return processActivityHeaderType;
	}




private ProcessActivityErrors createProcessActivityErrors(
		int processPayloadLevel, String errorMessage, Date createdDate, String errorType, List<String> errorMessageList, int sequenceNumber, String sourceSystem) throws DatatypeConfigurationException{

		ProcessActivityErrors processActivityErrors = new ProcessActivityErrors();
		List<ProcessActivityErrorType> processActivityErrorTypeList = new ArrayList<ProcessActivityErrorType>(); 
	    
		
		ProcessActivityErrorType processActivityErrorType = null;

		int processIndex = 1;
		DatatypeFactory df =  DatatypeFactory.newInstance();

		
		if(!errorMessageList.isEmpty())
		{
		  for(String dataErrorMessage : errorMessageList)
		  {
				processActivityErrorType = new ProcessActivityErrorType();
				sequenceNumber++;
				processActivityErrorType.setProcessSequence(sequenceNumber);
				processActivityErrorType.setProcessIndex(processIndex);
				processActivityErrorType.setSourceSystemRefId(sourceSystem);
				processActivityErrorType.setPayload(dataErrorMessage);
				processActivityErrorType.setErrorMessage(dataErrorMessage);
				processActivityErrorType.setStatusId(STATUS_ID);
				processActivityErrorType.setErrorType(errorType);
				processActivityErrorType.setProcessActivityErrorDetails((ProcessActivityErrorDetails) createProcessActivityErrorDetails(dataErrorMessage, 1, processPayloadLevel));
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(createdDate);
				processActivityErrorType.setProcessDate(df
						.newXMLGregorianCalendar(gc));
				processActivityErrorTypeList.add(processActivityErrorType);
		  }
		}
		else if(errorType.equals("TECHNICAL") && errorMessageList.isEmpty())
		{
		processActivityErrorType = new ProcessActivityErrorType();
		processActivityErrorType.setProcessSequence(sequenceNumber);
		processActivityErrorType.setProcessIndex(processIndex);
		processActivityErrorType.setSourceSystemRefId(sourceSystem);
		processActivityErrorType.setPayload(errorMessage);
		processActivityErrorType.setErrorMessage(errorMessage);
		processActivityErrorType.setStatusId(STATUS_ID);
		processActivityErrorType.setErrorType(errorType);
		processActivityErrorType.setProcessActivityErrorDetails((ProcessActivityErrorDetails) createProcessActivityErrorDetails(errorMessage, 1, processPayloadLevel));
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(createdDate);
		processActivityErrorType.setProcessDate(df
				.newXMLGregorianCalendar(gc));

		processActivityErrorTypeList.add(processActivityErrorType);
		}
		
		
		
		processActivityErrors
			.setProcessActivityError(processActivityErrorTypeList);

		return processActivityErrors;
}

private ProcessActivityErrorDetails createProcessActivityErrorDetails(
		String errorMessage, int processSequence, int processPayloadLevel) {
	ProcessActivityErrorDetails processActivityErrorDetails = new ProcessActivityErrorDetails();

	List<ProcessActivityErrorDetailType> processActivityErrorDetailTypeList = new ArrayList<ProcessActivityErrorDetailType>();

	ProcessActivityErrorDetailType processActivityErrorDetailType = new ProcessActivityErrorDetailType();
	processActivityErrorDetailType.setProcessLevel(1);
	processActivityErrorDetailType.setProcessSequence(String.valueOf(1));
	processActivityErrorDetailType.setStatusId(STATUS_ID);
	processActivityErrorDetailType.setErrorMessage(errorMessage);

	processActivityErrorDetailTypeList.add(processActivityErrorDetailType);

	processActivityErrorDetails
			.setProcessActivityErrorDetail(processActivityErrorDetailTypeList);

	return processActivityErrorDetails;
 }
}
