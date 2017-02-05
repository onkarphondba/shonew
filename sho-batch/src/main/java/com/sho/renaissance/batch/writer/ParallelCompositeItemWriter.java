package com.sho.renaissance.batch.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.core.task.TaskExecutor;

import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;


/**
 * Custom Composite writer to write to both JE and Staging DB
 * 
 * @author mamthimm
 *
 */
public class ParallelCompositeItemWriter<T> extends CompositeItemWriter<T> implements StepExecutionListener {



	@Override
	public ExitStatus afterStep(StepExecution arg0) {

		return null;
	}


	@Override
	public void beforeStep(StepExecution arg0) {
		// TODO Auto-generated method stub

	}  

}



