package com.sho.renaissance.batch.tasklet;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;


/**
 * This class is to initialized the job context with required properties
 *         which are going to be used in future
 *         
 * @author gdhimate
 * 
 */
public class FlowExecutionTasklet implements Tasklet, StepExecutionListener {

	private static Log logger = LogFactory.getLog(FlowExecutionTasklet.class);

	@Override
	public ExitStatus afterStep(StepExecution afterStepExecution) {
		logger.info("************** Finished : Get Flow Execution **********************");
		return null;
	}

	@Override
	public void beforeStep(StepExecution arg0) {
		logger.info("************** Started : Get Flow Execution **********************");
	}

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

		Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();

		ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();	
		
		return RepeatStatus.FINISHED;
	}

	
}
