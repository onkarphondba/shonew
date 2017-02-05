package com.sho.renaissance.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.capgemini.cif.core.domain.BatchItem;


/**
 * Base Processor class which will be extend by all processor
 * 
 * @author gdhimate
 *        
 */
public abstract class BaseItemProcessor implements ItemProcessor<BatchItem, BatchItem> {

	@Override
	public abstract BatchItem process(BatchItem item) throws Exception;

}