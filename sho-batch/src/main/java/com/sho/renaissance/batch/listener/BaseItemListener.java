package com.sho.renaissance.batch.listener;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;

/**Class extends ItemListenerSupport to add logging statements. 
 * @author gdhimate
 *
 */
public class BaseItemListener extends ItemListenerSupport<Object, Object> {

	private static Log logger = LogFactory.getLog(BaseItemListener.class);

	@Override
	public void afterRead(Object item) {
		logger.debug("Total Records read: " + item);
	}

	@Override
	public void beforeProcess(Object item) {
		logger.debug("Beginning processing of a Record: " + item);
	}

	@Override
	public void afterProcess(Object item, Object result) {
		logger.debug("End processing of a Record: " + item.toString() + " Result: " + result);
	}

	@Override
	public void beforeWrite(List<? extends Object> item) {
		logger.info("Beginning writing of Record(s) with size (Commit Interval):" + item.size());
		logger.debug("Beginning writing of Record(s):" + item);
	}

	@Override
	public void afterWrite(List<? extends Object> item) {
		logger.info("Finished writing of Record(s) with size (Commit Interval): " + item.size());
		logger.debug("Finished writing of Record(s): " + item);
	}

	// #################### E R R O R L O G G I N G #################### //
	// Called if an error occurs while trying to read.
	@Override
	public void onReadError(Exception e) {
		logger.error("Error while Reading: " + e);

	}

	// Called if an exception was thrown from ItemProcessor.process(Object).
	@Override
	public void onProcessError(Object item, Exception e) {
		logger.error("Error while Processing: " + item + " \nError: " + e.getMessage());
		logger.error("Error while Procesing details:" + e);
	}

	// Called if an error occurs while trying to write.
	@Override
	public void onWriteError(Exception e, List<? extends Object> item) {
		logger.error("Error while Writing : {} \nError: {} [" + item + "]" + e.getMessage());
		logger.error("Error while Writing details:" + e);
	}
}