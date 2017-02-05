package com.sho.renaissance.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.capgemini.cif.core.service.IBatchItemService;
import com.sho.renaissance.batch.constants.BatchConstants;

public class UpdateEntityStatusTasklet implements Tasklet {
	
	IBatchItemService batchItemService;
	private String tableName;
	
	public IBatchItemService getBatchItemService() {
		return batchItemService;
	}

	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Long failedRecords = null;
		Long processedRecords = null;
		
		if(tableName.equals(BatchConstants.BATCH_ITEM)||tableName.equals(BatchConstants.INV_BATCH_ITEM))
		{
		 String	correlationId = chunkContext.getStepContext().getStepExecution().getJobParameters().getString(BatchConstants.CORRELATION_ID);
		 String entityType = chunkContext.getStepContext().getStepExecution().getJobParameters().getString(BatchConstants.ENTITY_TYPE);
		 processedRecords = Long.parseLong(batchItemService.getTotalBatchCount(correlationId, entityType).toString());
		 failedRecords = Long.parseLong(batchItemService.getTotalFailedBatchCount(correlationId, entityType).toString());
		}
		
		else if(tableName.equals(BatchConstants.CNV_HISTORICAL_METRICS_TABLE)||tableName.equals(BatchConstants.CNV_HISTORICAL_INVENTORY_TABLE)||tableName.equals(BatchConstants.CNV_SALES_HISTORY_TABLE))
        { 
                failedRecords = batchItemService.getCNVNumberOfFailedRecords(this.tableName); 
                processedRecords = batchItemService.getCNVNumberOfRecordsProcessed(this.tableName); 
        }
		
		else
		{
			failedRecords = batchItemService.getNumberOfFailedRecords(this.tableName);
			processedRecords = batchItemService.getNumberOfRecordsProcessed(this.tableName);
		}
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(BatchConstants.UNPROCESSED_RECORD_COUNT, failedRecords);
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(BatchConstants.RECORD_COUNT, processedRecords);
		batchItemService.createProcessStatus((String) chunkContext.getStepContext().getJobParameters().get(BatchConstants.ENTITY_TYPE) , BatchConstants.STATUS_COMPLETED);
		return RepeatStatus.FINISHED;
	}

}
