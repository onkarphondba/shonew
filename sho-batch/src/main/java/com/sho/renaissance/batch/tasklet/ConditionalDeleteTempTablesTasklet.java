package com.sho.renaissance.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.capgemini.cif.core.service.IBatchItemService;

/**
 * @author snkesarw
 *
 */
public class ConditionalDeleteTempTablesTasklet implements Tasklet{
	private IBatchItemService batchItemService;
	private String tableName;
	private int numberOfDays;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		batchItemService.deleteTableDataOlderThanNDays(tableName, numberOfDays);
		return RepeatStatus.FINISHED;
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

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
}
