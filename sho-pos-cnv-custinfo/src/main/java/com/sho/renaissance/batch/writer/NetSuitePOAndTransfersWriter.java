/*package com.sho.renaissance.batch.writer;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import com.capgemini.cif.core.domain.BatchItem;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.exception.GenericCoreException;
import com.capgemini.cif.core.service.IBatchItemService;
import com.capgemini.cif.core.util.JAXBToXMLConverter;
import com.netsuite.webservices.platform.core_2016_1.CustomFieldList;
import com.netsuite.webservices.platform.core_2016_1.CustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.DateCustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.DoubleCustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.ListOrRecordRef;
import com.netsuite.webservices.platform.core_2016_1.LongCustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.RecordRef;
import com.netsuite.webservices.platform.core_2016_1.SelectCustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.StringCustomFieldRef;
import com.netsuite.webservices.platform.faults_2016_1.ExceededRecordCountFault;
import com.netsuite.webservices.platform.faults_2016_1.ExceededUsageLimitFault;
import com.netsuite.webservices.platform.faults_2016_1.InvalidSessionFault;
import com.netsuite.webservices.platform.faults_2016_1.UnexpectedErrorFault;
import com.netsuite.webservices.platform.messages_2016_1.WriteResponseList;
import com.netsuite.webservices.platform_2016_1.NetSuitePortType;
import com.netsuite.webservices.setup.customization_2016_1.CustomRecord;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.domain.NetSuitePOAndTransfers;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;
import com.sho.renaissance.batch.util.CommonUtils;
import com.sho.renaissance.batch.util.NetSuiteWebServiceUtils;

*//**
 * @author ophondba
 * 
 * The class sends data to NEtSuite API over Webservice
 * in XML format.
 *//*
public class NetSuitePOAndTransfersWriter extends JdbcBatchItemWriter implements StepExecutionListener {
	
	private static Log logger = LogFactory.getLog(NetSuitePOAndTransfersWriter.class);
	private NetSuiteWebServiceUtils nsUtils;
	private NetSuitePortType _port;
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	private JobParameters jobParameters;
	private String correlationId;
	private String entityType;
	private IBatchItemService batchItemService;
	private List<Map<String, Object>> purchaseOrders;
	

	@SuppressWarnings("unchecked")
	@Override
	public void write(List items) throws Exception {
		
		List<BatchItem> batchItems = (List<BatchItem>)items;
		_port = nsUtils.getPort();
		List<BatchItem> updatedData = createOrders(batchItems);
		
		if (logger.isInfoEnabled() && !batchItems.isEmpty()) {
			logger.info("NetSuite API Add Call for " + BatchConstants.PO_AND_TRANSFERS_FRIENDLY_NAME + " has been Completed");
		}
		super.write(updatedData);
	}

	private CustomRecord getCustomRecord(NetSuitePOAndTransfers temp) throws SQLException{
		
		CustomRecord customRecord = new CustomRecord();
		CustomFieldList customFieldList = new CustomFieldList();
		
		customFieldList.setCustomField(new CustomFieldRef[12]);
		
		LongCustomFieldRef orderHeaderId = new LongCustomFieldRef();
		orderHeaderId.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDERHEADER_ID);
		orderHeaderId.setValue(temp.getJeOrderHeaderId());
		customFieldList.setCustomField(0,orderHeaderId);

		LongCustomFieldRef orderLineNumber = new LongCustomFieldRef();
		orderLineNumber.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_LINE_NUMBER);
		orderLineNumber.setValue(temp.getJeOrderLineNumber());
		customFieldList.setCustomField(1,orderLineNumber);
		
		StringCustomFieldRef orderItemNumber = new StringCustomFieldRef();
		orderItemNumber.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_ITEM_NUMBER);
		orderItemNumber.setValue(temp.getItemNumber());
		customFieldList.setCustomField(2,orderItemNumber);
		
		StringCustomFieldRef vendorShipFrom = new StringCustomFieldRef();
		vendorShipFrom.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_VENDOR_SHIP_FROM);
		vendorShipFrom.setValue(temp.getLocationFrom());
		customFieldList.setCustomField(3,vendorShipFrom);

		StringCustomFieldRef vendorNameField = new StringCustomFieldRef();
		vendorNameField.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_VENDOR_NAME_FIELD);
		vendorNameField.setValue(temp.getVendorName());
		customFieldList.setCustomField(4,vendorNameField);
		
		StringCustomFieldRef locationToField = new StringCustomFieldRef();
		locationToField.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_LOCATION_TO_FIELD);
		locationToField.setValue(temp.getLocationTo());
		customFieldList.setCustomField(5,locationToField);
		
		DoubleCustomFieldRef orderQuantity = new DoubleCustomFieldRef();
		orderQuantity.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_QUANTITY);
		orderQuantity.setValue(temp.getQuantity());
		customFieldList.setCustomField(6,orderQuantity);
		
		DateCustomFieldRef orderDateField = new DateCustomFieldRef();
		orderDateField.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_DATE_FIELD);
		Calendar orderDatecalendar = Calendar.getInstance();
		orderDatecalendar.setTime(temp.getOrderDate());
		orderDateField.setValue(orderDatecalendar);
		customFieldList.setCustomField(7,orderDateField);
				
		DateCustomFieldRef arrivalDateField = new DateCustomFieldRef();
		arrivalDateField.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ARRIVAL_DATE_FIELD);
		Calendar arrivalDatecalendar = Calendar.getInstance();
		arrivalDatecalendar.setTime(temp.getArrivalDate());
		arrivalDateField.setValue(arrivalDatecalendar);
		customFieldList.setCustomField(8,arrivalDateField);
						
		SelectCustomFieldRef poToFlag = new SelectCustomFieldRef();
		poToFlag.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_PO_TO_FLAG);
		ListOrRecordRef netPOTO = new ListOrRecordRef();
		
		for (Map<String, Object> each : purchaseOrders)
		{
			if(temp.getPoVsTransfer().equalsIgnoreCase((String) each.get("list_PurchaseOrder")))
			{
				temp.setPoVsTransfer((String) each.get("list_id"));
				break;
			}
		}	
		
		netPOTO.setInternalId(String.valueOf(temp.getPoVsTransfer()));
		poToFlag.setValue(netPOTO);
		customFieldList.setCustomField(9, poToFlag);
		
		
		LongCustomFieldRef originalPoNum = new LongCustomFieldRef();
		originalPoNum.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORIGINAL_PO_NUM);
		originalPoNum.setValue(temp.getOriginalPONumber());
		customFieldList.setCustomField(10,originalPoNum);
		
		DoubleCustomFieldRef costPriceField = new DoubleCustomFieldRef();
		costPriceField.setScriptId(BatchConstants.POANDTRANSFERS_JE_CUSTOM_RECORD_JE_COST_PRICE_FIELD);
		costPriceField.setValue(temp.getCostPrice());
		customFieldList.setCustomField(11,costPriceField);
		
		customRecord.setCustomFieldList(customFieldList);
		
		RecordRef customRecordType = new RecordRef();
		customRecordType.setInternalId(CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
		BatchConstants.CUSTRECORD_SHO_POANDTRANSFERS_INTEG_BATCH_DETAIL));
		customRecord.setRecType(customRecordType);
		return customRecord;
		
	}
	*//**
	 * Adds an InventoryItem. Asks the user for the Item Name/Number and the
	 * costing method (Average, FIFO or LIFO).
	 *
	 * @throws RemoteException
	 * @throws ExceededUsageLimitFault
	 * @throws UnexpectedErrorFault
	 * @throws InvalidSessionFault
	 * @throws ExceededRecordCountFault
	 * @throws JAXBException 
	 * @throws SQLException 
	 *//*
	public List<BatchItem> createOrders(List<BatchItem> batchItems) throws RemoteException, ExceededUsageLimitFault, UnexpectedErrorFault,
	InvalidSessionFault, ExceededRecordCountFault, JAXBException, SQLException {
		
		List<NetSuitePOAndTransfers> poAndTransfers = new ArrayList<NetSuitePOAndTransfers>();
		for(BatchItem batchItem : batchItems){ 
			poAndTransfers.add((NetSuitePOAndTransfers) JAXBToXMLConverter.jaxbXMLToObject(new NetSuitePOAndTransfers(),batchItem.getItemXMLData()));
		}

		CustomRecord[] ordersList = new CustomRecord[batchItems.size()];
		int i = batchItems.size() -1;
		for(NetSuitePOAndTransfers temp :poAndTransfers){
			ordersList[i] = getCustomRecord(temp);
			i--;
		}

		logger.debug("ArrayList "+ordersList.length);
				
		WriteResponseList writeResopnseList = null;
				
		try{
		writeResopnseList = _port.addList(ordersList);
		}catch(Exception e){
			List<Throwable> exeptions = new ArrayList<Throwable>();
			exeptions.add(e);
			dimProcessExcecutionListner.sendToIEM(exeptions, jobParameters, new Date());
			return Collections.emptyList();
		}
		

		int count = 0;
		GenericCoreException exception = null;
		List<Throwable> exceptions = new ArrayList<Throwable>();
		
		
		for(BatchItem temp :batchItems){
			if (writeResopnseList.getWriteResponse(count).getStatus().isIsSuccess()) {
				temp.setSentToTarget(Boolean.TRUE);
			}else {
				temp.setSentToTarget(Boolean.FALSE);
				exception = ExceptionHandler.createException(
						"sho.common.error.technical.sql.add.send_to_netsuite_failed",
						BatchConstants.PO_AND_TRANSFERS_FRIENDLY_NAME,temp.getEntityType() );
				exceptions.add(exception);
		     }
			count++;
		  }
			
				
		return batchItems;
	}
	
	*//**
	 * In this we are taking values of purchase orders 
	 * comparing in key value pair
	 *//*
	@Override
	public void beforeStep(StepExecution stepExecution) {
		jobParameters = stepExecution.getJobParameters();
		correlationId = jobParameters.getString(BatchConstants.CORRELATION_ID);
		entityType = jobParameters.getString(BatchConstants.ENTITY_TYPE);
		logger.info("******************** Starting : " + stepExecution.getStepName() + " Entity : " + entityType
				+ " Correlation Id : " + correlationId);
		
	try {
		purchaseOrders = batchItemService.getPoAndTransfersListValues();
	} catch (SQLException e) {
		List<Throwable> exeptions = new ArrayList<Throwable>();
		exeptions.add(e);
		dimProcessExcecutionListner.sendToIEM(exeptions, jobParameters, new Date());
		return;
	}
}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("******************** Stopping : " + stepExecution.getStepName() + " Entity : " + entityType
				+ " Correlation Id : " + correlationId);
		return null;
	}
	
	*//**
	 * @param dimProcessExcecutionListner
	 *            the dimProcessExcecutionListner to set
	 *//*
	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}
	
	*//**
	 * @param nsUtils
	 *//*
	public void setNsUtils(NetSuiteWebServiceUtils nsUtils) {
		this.nsUtils = nsUtils;
	}

	
	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}
}
*/