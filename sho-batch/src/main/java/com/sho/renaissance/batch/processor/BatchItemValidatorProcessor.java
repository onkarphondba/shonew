package com.sho.renaissance.batch.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.capgemini.cif.core.domain.BatchItem;
import com.capgemini.cif.core.domain.BatchItemProcessMessage;
import com.capgemini.cif.core.util.JAXBToXMLConverter;
import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteComponentProductValidator;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * Common validator class which will be used for validating the items
 * 
 * @author gdhimate
 *
 */
public class BatchItemValidatorProcessor
		implements ItemProcessor<ConcreteComponentProduct, BatchItem>, StepExecutionListener {

	private static Log logger = LogFactory.getLog(BatchItemValidatorProcessor.class);
	
	private JobParameters jobParameters;
	private String friendlyName;
	private String correlationId;
	private String entityType;
	
	@Override
	public BatchItem process(ConcreteComponentProduct item) throws Exception {

		BatchItem batchItem = new BatchItem();
		
		batchItem.setCorrelationId(correlationId);
		batchItem.setIsValid(Boolean.TRUE);
		batchItem.setEntityType(entityType);
		
		String xml = JAXBToXMLConverter.jaxbObjectToXML(item);
		batchItem.setItemXMLData(xml);
		
        List<BatchItemProcessMessage> messages = ConcreteComponentProductValidator.validate(item);
        
        if (messages != null && !messages.isEmpty()) {
			logger.info("Netsuite item with internal id [" + item.getInternalId() + "] failed with ["
					+ messages.size() + " validation errors]");

			batchItem.setIsValid(Boolean.FALSE);
			batchItem.setErrorMessage(CommonUtils.getErrorMessage(messages));

		}
	
		return batchItem;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.debug("******************** Finished : "+stepExecution.getStepName());
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {

        jobParameters = stepExecution.getJobParameters();
	    correlationId = jobParameters.getString(BatchConstants.CORRELATION_ID);
	    entityType = jobParameters.getString(BatchConstants.ENTITY_TYPE);
		
		logger.debug("******************** Starting : "+stepExecution.getStepName());
		logger.debug(" Interface : " + friendlyName);

	}
	

	
	public void setFriendlyName(String friendlyNameParam) {
		friendlyName = friendlyNameParam;
	}

}