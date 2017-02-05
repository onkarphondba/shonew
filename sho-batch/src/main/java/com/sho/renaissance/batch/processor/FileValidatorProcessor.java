package com.sho.renaissance.batch.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.capgemini.cif.core.domain.BatchItemProcessMessage;
import com.capgemini.cif.core.exception.GenericCoreException;
import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteComponentProductValidator;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;

/**
 * @author gdhimate
 *
 */
public class FileValidatorProcessor implements ItemProcessor<ConcreteComponentProduct, ConcreteComponentProduct>, StepExecutionListener {

	private static Log logger = LogFactory.getLog(FileValidatorProcessor.class);
	
	private static List<String> failedRecords;
	private JobParameters jobParameters;
	private DimProcessExcecutionListner dimProcessExcecutionListner;
	
	@Override
	public ConcreteComponentProduct process(ConcreteComponentProduct item) throws Exception {
		
		List<BatchItemProcessMessage> messages = ConcreteComponentProductValidator.validate(item);
		
		if(messages.isEmpty()){
			return item;
		}
		StringBuilder fullMessage = new StringBuilder();
		for(BatchItemProcessMessage message:messages){
			fullMessage.append(message.getMessage());
		}

		failedRecords.add("Data :"+item.toString()+"\n"+fullMessage.toString());
		return null;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		List<Throwable> excpetions= new ArrayList<>();
		excpetions.add(new GenericCoreException("Row Id"));
		if (!failedRecords.isEmpty()) {
			dimProcessExcecutionListner.sendMessageListToIem(failedRecords, jobParameters, new Date(),
					BatchConstants.TECHNICAL_ERROR);
		}
		
		logger.debug("******************** Finished : "+stepExecution.getStepName());
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		 //failedRecords=new ArrayList<>();
		 intializeStaticContext();
		 jobParameters = stepExecution.getJobParameters();
		 logger.debug("******************** Starting : "+stepExecution.getStepName());
	}

	private static void intializeStaticContext() {
		failedRecords = new ArrayList<>();
	}
	
	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}
	
}
