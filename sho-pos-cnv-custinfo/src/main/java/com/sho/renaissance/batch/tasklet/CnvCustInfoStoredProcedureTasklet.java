package com.sho.renaissance.batch.tasklet;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.service.IBatchItemService;
import com.sho.renaissance.batch.constants.BatchConstants;

/** Call the stored procedure.<br>
 * The procedure being called populates the table to be used
 * for updating/deleting/adding the data in Netsuite.
 * @author parthaka
 *
 */
public class CnvCustInfoStoredProcedureTasklet implements Tasklet, StepExecutionListener {
	
	private static Log logger = LogFactory.getLog(CnvCustInfoStoredProcedureTasklet.class);

	private IBatchItemService batchItemService;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Map<String, Object> result = batchItemService.callCnvCustInfoProcedure();
		
		if((Integer)result.get(BatchConstants.ERROR_CODE) != 0){
			throw ExceptionHandler.createException(BatchConstants.CNV_STOREDPROCEDURE_ERROR, BatchConstants.POS_CNV_CUSTINFO_FRIENDLY_NAME, (String)result.get(BatchConstants.ERROR_MESSAGE));
		}


		return RepeatStatus.FINISHED;
	}
	
	public void setBatchItemService(IBatchItemService batchItemService) {
		this.batchItemService = batchItemService;
	}
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		JobParameters jobParameters = stepExecution.getJobParameters();
		logger.info("******************** Starting : "+stepExecution.getStepName()+" Entity : "+jobParameters.getString(BatchConstants.ENTITY_TYPE)+ " Correlation Id : "+jobParameters.getString(BatchConstants.CORRELATION_ID));

		
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		JobParameters jobParameters = stepExecution.getJobParameters();
		logger.info("******************** Stopping : "+stepExecution.getStepName()+" Entity : "+jobParameters.getString(BatchConstants.ENTITY_TYPE)+ " Correlation Id : "+jobParameters.getString(BatchConstants.CORRELATION_ID));

		return null;
	}

}
