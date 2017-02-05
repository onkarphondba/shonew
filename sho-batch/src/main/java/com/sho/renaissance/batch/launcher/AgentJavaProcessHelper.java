/**
 * 
 */
package com.sho.renaissance.batch.launcher;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sho.renaissance.batch.constants.BatchConstants;

/**
 * Class to invoke batch Job.
 * 
 * @author gdhimate
 *
 */
public class AgentJavaProcessHelper {

	private static Log logger = LogFactory.getLog(AgentJavaProcessHelper.class);

	/**
	 * Starts the spring batch
	 * 
	 * @param batchAgentContext
	 * @param fe
	 * @throws JobExecutionAlreadyRunningException 
	 * @throws JobRestartException 
	 * @throws JobInstanceAlreadyCompleteException 
	 * @throws JobParametersInvalidException 
	 * @throws NoSuchJobException 
	 * @throws NoSuchJobExecutionException 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public JobExecution invokeBatchJob(BatchAgentContext batchAgentContext)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException, NoSuchJobExecutionException, NoSuchJobException {
		logger.debug("Entering invokeBatchJob(...) ...");

		logger.debug("loading xml file for " + batchAgentContext.getContextFileName());

		ApplicationContext context = null;
		JobExecution execution = null;
		JobParameters parameters = null;
		try {
			context = new ClassPathXmlApplicationContext(batchAgentContext.getContextFileName());

			JobExplorer jobExplorer = context.getBean(JobExplorer.class);
			Job job = (Job) context.getBean(batchAgentContext.getJobName());
			JobOperator operator = (JobOperator) context.getBean("jobOperator");

			Optional<JobInstance> existingInstance = jobExplorer.getJobInstances(job.getName(), 0, 1).stream()
					.findFirst();
			if (existingInstance.isPresent()) {
				parameters = jobExplorer.getJobExecution(existingInstance.get().getId()).getJobParameters();
			}

			if (existingInstance.isPresent() && isSameJobInstance(batchAgentContext, parameters)
					&& job.getName().equalsIgnoreCase(existingInstance.get().getJobName())
					&& !jobExplorer.getJobExecution(existingInstance.get().getId()).isRunning()) {

				Long id = operator.restart(existingInstance.get().getId());
				execution = jobExplorer.getJobExecution(id);

			} else {
				JobLauncher joblauncher = context.getBean(JobLauncher.class);
				execution = joblauncher.run(job, getJobParametersBuilder(batchAgentContext).toJobParameters());
			}

		// ((ConfigurableApplicationContext) context).close();
		} catch (JobExecutionAlreadyRunningException e) {
			throw e;
		} catch (JobRestartException e) {
			throw e;
		} catch (JobInstanceAlreadyCompleteException e) {
			throw e;
		} catch (JobParametersInvalidException e) {
			throw e;
		}/*finally{
			if(null != context){
			((ConfigurableApplicationContext) context).close();
			}
		}*/

		logger.debug("Exiting invokeBatchJob(...) ...");
		return execution;
	}

	/**
	 * Builds the job parameters required for running batch
	 * 
	 * @param batchAgentContext
	 * @return
	 */
	protected JobParametersBuilder getJobParametersBuilder(BatchAgentContext batchAgentContext) {
		JobParametersBuilder jobparameterBuilder = new JobParametersBuilder();

		// get job params
		Map<String, String> jobParams = batchAgentContext.getJobParams();
		for (String key : jobParams.keySet()) {
			String value = jobParams.get(key);
			jobparameterBuilder.addString(key, value);
		}
		// add date
		//jobparameterBuilder.addDate("TIMESTAMP", new Date());
		//jobparameterBuilder.addDouble("rand", RandomUtils.nextDouble());
		return jobparameterBuilder;
	}

	/**
	 * @param jobToRun
	 * @param jobExsiting
	 * @return
	 */
	private boolean isSameJobInstance(BatchAgentContext jobToRun, JobParameters jobExsiting) {

		if (jobToRun.getJobParams().get(BatchConstants.CORRELATION_ID)
				.equalsIgnoreCase(jobExsiting.getString(BatchConstants.CORRELATION_ID))) {
			return true;
		}
		return false;
	}

}
