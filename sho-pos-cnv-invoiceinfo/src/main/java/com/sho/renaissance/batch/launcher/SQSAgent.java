package com.sho.renaissance.batch.launcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * @author gdhimate
 * 
 * This class is the launching point for po and transfer interface
 *
 */
public class  SQSAgent {

	private static Log logger = LogFactory.getLog(SQSAgent.class);

	public static void main(String[] args) {

			logger.debug("**************************** Started loading batch Context ******************************");
			
			System.setProperty("spring.profiles.active", CommonUtils.getPropertyFromClassPath(
					BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.CNVCUSTINFO_NS_PROFILE));
			final AbstractApplicationContext context = new ClassPathXmlApplicationContext("pos_cnvcustinfo_context.xml");

			Runtime runTime = Runtime.getRuntime();
			runTime.addShutdownHook(new Thread(){
				@Override
				public void run() {
					context.close();
				}
			});
			
			logger.debug("**************************** Finished loading batch Context ******************************");

		}
		
	}
	


