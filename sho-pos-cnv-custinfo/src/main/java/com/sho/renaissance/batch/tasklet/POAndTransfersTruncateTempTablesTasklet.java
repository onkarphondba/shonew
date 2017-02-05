package com.sho.renaissance.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.capgemini.cif.core.service.IBatchItemService;
import com.sho.renaissance.batch.constants.BatchConstants;

/**
 * This class truncates cluster-feed specific temporary tables 
 * @author ophondba
 *
 */
public class POAndTransfersTruncateTempTablesTasklet implements Tasklet {

	private IBatchItemService batchItemService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		batchItemService.truncateTable(BatchConstants.TRUNCATE_POANDTRANSFERS_LOOKUP_TABLE);
		

		return RepeatStatus.FINISHED;
	}

	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}

}
