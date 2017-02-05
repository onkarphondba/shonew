package com.sho.renaissance.batch.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**Class extends StepExecutionListener to add logging capabilities. 
 * @author gdhimate
 *
 */
public class BaseStepListener implements StepExecutionListener {

	private static Log logger = LogFactory.getLog(BaseStepListener.class);

	/**Method adds information in the logger after the step has completed
	 * its execution.
	 * @param stepExecution
	 * @return
	 */ 
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("\nJob [ Name: " + stepExecution.getJobExecution().getJobInstance().getJobName() + ", ID: "
				+ stepExecution.getJobExecution().getJobInstance().getId() + " ] " + "\nStep [ Name: "
				+ stepExecution.getStepName() + ", ID: " + stepExecution.getId() + " ] " + "\nRecords Read: "
				+ stepExecution.getReadCount() + "\nRecords Written: " + stepExecution.getCommitCount());

		logger.info(
				"Step [ " + stepExecution.getStepName() + " ] completed with the status " + stepExecution.getStatus());

		if (stepExecution.getReadCount() == 0) {
			return ExitStatus.COMPLETED;
		}

		return stepExecution.getExitStatus();
	}

	/**Method adds information in the logger after the step has begun
	 * its execution.
	 * @param stepExecution
	 */ 
	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("Step [ " + stepExecution.getStepName() + " ] began execution");
	}

}