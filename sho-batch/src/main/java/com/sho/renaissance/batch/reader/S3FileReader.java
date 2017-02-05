package com.sho.renaissance.batch.reader;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.BufferedReaderFactory;
import org.springframework.batch.item.file.DefaultBufferedReaderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;

import com.amazonaws.services.s3.AmazonS3Client;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;

public class S3FileReader extends FlatFileItemReader implements StepExecutionListener {

	private static Log logger = LogFactory.getLog(S3FileReader.class);

	@Autowired
	private ResourceLoader resourceLoader;
	private TaskExecutor taskExecutor;
	private String fileName;
	private Map<String, Object> jobParams;
	private BufferedReader reader;
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	private AmazonS3Client amazonS3Client;
	private int lineCount = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void setResource(Resource resource) {

		String buckteName = ((ClassPathResource) resource).getPath();
		Resource newResource = new SimpleStorageResource(amazonS3Client, buckteName, fileName, this.taskExecutor, null);
		BufferedReaderFactory bufferedReaderFactory = new DefaultBufferedReaderFactory();

		super.setResource(newResource);
		try {
			reader = bufferedReaderFactory.create(newResource, Charset.defaultCharset().name());
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (lineCount > 2)
					break;
				lineCount++;
			}
			reader.close();

			if (lineCount <= 1) {
				List<Throwable> exeptions = new ArrayList<Throwable>();
				exeptions.add(ExceptionHandler.createException("sho.common.error.technical.runtime.file.empty",
						jobParams.get(BatchConstants.ENTITY_TYPE), fileName, buckteName));

				dimProcessExcecutionListner.sendToIEM(exeptions, createJobParameters(), new Date());

			}
		} catch (Exception e) {
			List<Throwable> exeptions = new ArrayList<Throwable>();
			exeptions.add(e);
			dimProcessExcecutionListner.sendToIEM(exeptions, createJobParameters(), new Date());
		}
	}

	private JobParameters createJobParameters() {
		Map map = new HashMap<String, JobParameter>();
		map.put(BatchConstants.CORRELATION_ID, new JobParameter((String) jobParams.get(BatchConstants.CORRELATION_ID)));
		map.put(BatchConstants.ENTITY_TYPE, new JobParameter((String) jobParams.get(BatchConstants.ENTITY_TYPE)));
		map.put(BatchConstants.SOURCE_SYSTEM, new JobParameter((String) jobParams.get(BatchConstants.SOURCE_SYSTEM)));
		map.put(BatchConstants.DESTINATION_SYSTEM,
				new JobParameter((String) jobParams.get(BatchConstants.DESTINATION_SYSTEM)));
		JobParameters jobParameters = new JobParameters(map);
		return jobParameters;
	}

	public Map<String, Object> getJobParams() {
		return jobParams;
	}

	public void setJobParams(Map<String, Object> jobParams) {
		this.jobParams = jobParams;
	}

	public DimProcessExcecutionListner getDimProcessExcecutionListner() {
		return dimProcessExcecutionListner;
	}

	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}

	public void setTaskExecutor(TaskExecutor baseTaskExecutor) {
		this.taskExecutor = baseTaskExecutor;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AmazonS3Client getAmazonS3Client() {
		return amazonS3Client;
	}

	public void setAmazonS3Client(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("******************** Starting : " + stepExecution.getStepName() + " Entity : "
				+ jobParams.get(BatchConstants.ENTITY_TYPE) + " Correlation Id : "
				+ jobParams.get(BatchConstants.CORRELATION_ID));
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0 && stepExecution.getWriteCount() == 0) {
			stepExecution.getJobExecution().stop();
		}
		logger.info("******************** Stopping : " + stepExecution.getStepName() + " Entity : "
				+ jobParams.get(BatchConstants.ENTITY_TYPE) + " Correlation Id : "
				+ jobParams.get(BatchConstants.CORRELATION_ID));
		return null;
	}

}
