package com.sho.renaissance.batch.tasklet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.capgemini.cif.core.constants.NetSuiteBatchStatusEnum;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.service.IBatchItemService;
import com.netsuite.webservices.platform.core_2016_1.CustomFieldList;
import com.netsuite.webservices.platform.core_2016_1.CustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.ListOrRecordRef;
import com.netsuite.webservices.platform.core_2016_1.LongCustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.RecordRef;
import com.netsuite.webservices.platform.core_2016_1.SelectCustomFieldRef;
import com.netsuite.webservices.platform.core_2016_1.StringCustomFieldRef;
import com.netsuite.webservices.platform.messages_2016_1.WriteResponse;
import com.netsuite.webservices.platform_2016_1.NetSuitePortType;
import com.netsuite.webservices.setup.customization_2016_1.CustomRecord;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;
import com.sho.renaissance.batch.util.CommonUtils;
import com.sho.renaissance.batch.util.NetSuiteWebServiceUtils;

public class UpdateBatchStatusTasklet implements Tasklet ,StepExecutionListener {

	private IBatchItemService batchItemService;
	private NetSuiteWebServiceUtils nsUtils;
	private NetSuitePortType _port;
	private String tableName;
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	private JobParameters jobParameters;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		_port = nsUtils.getPort();
		WriteResponse writeResponse = _port.add(getCustomRecord((String)chunkContext.getStepContext().getJobParameters().get(BatchConstants.CORRELATION_ID),batchItemService.getNumberOfRecordsProcessed(this.tableName), (String)chunkContext.getStepContext().getJobParameters().get(BatchConstants.INTERFACE_ID)));
		if(!writeResponse.getStatus().isIsSuccess()){
			List<Throwable> exceptions = new ArrayList<Throwable>();
			exceptions.add(ExceptionHandler.createException(
					"sho.common.error.technical.netsuite.update.batchstatus.failed",
					BatchConstants.SITECATEGORIES_ENTITY, writeResponse.getStatus().getStatusDetail(0).getCode()));
			
			dimProcessExcecutionListner.sendToIEM(exceptions, jobParameters, new Date());
		}
		//
		
		return RepeatStatus.FINISHED;
	}
	
	/**
	 * This method forms location object for NetSuite call
	 * @param temp
	 * @return
	 */
	private CustomRecord getCustomRecord(String correlationId,Long numberOfRecordImported,String interfaceId){
		CustomRecord customRecord = new CustomRecord();
		RecordRef recordRef = new RecordRef();
		recordRef.setInternalId(CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.CUSTRECORD_SHO_MULE_INTEG_BATCH_DETAIL));
		customRecord.setRecType(recordRef);
		
		CustomFieldList customFieldList = new CustomFieldList();
		
		LongCustomFieldRef integrationId = new LongCustomFieldRef();
		integrationId.setScriptId(BatchConstants.MULE_INTEG_INTG_ID);
		integrationId.setValue(Long.valueOf(interfaceId));
		
		LongCustomFieldRef numberOfRecordsImported = new LongCustomFieldRef();
		numberOfRecordsImported.setScriptId(BatchConstants.MULE_INTEG_REC_IMPORTED);
		numberOfRecordsImported.setValue(numberOfRecordImported);
		
		SelectCustomFieldRef status = new SelectCustomFieldRef();
		status.setScriptId(BatchConstants.MULE_INTEG_BATCH_STATUS);
		ListOrRecordRef statusRecordRef = new ListOrRecordRef();
		statusRecordRef.setInternalId(batchItemService.getNetsuiteBatchStatus(NetSuiteBatchStatusEnum.PROCESSED.batchStatusType()));
		status.setValue(statusRecordRef);
		
		StringCustomFieldRef integrationBatchId = new StringCustomFieldRef();
		integrationBatchId.setScriptId(BatchConstants.MULE_INTEG_BATCH_ID);
		integrationBatchId.setValue(correlationId);
		
	
		CustomFieldRef[] refList = new CustomFieldRef[4];
		refList[0] = integrationId;
		refList[1] = numberOfRecordsImported;
		refList[2] = status;
		refList[3] = integrationBatchId;
		
		customFieldList.setCustomField(refList);
		
		customRecord.setCustomFieldList(customFieldList);
		return customRecord;
	}

	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @param dimProcessExcecutionListner
	 */
	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		jobParameters = stepExecution.getJobParameters();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}

	public void setNsUtils(NetSuiteWebServiceUtils nsUtils) {
		this.nsUtils = nsUtils;
	}
}
