package com.capgemini.cif.core.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author gdhimate
 * 
 */
public final class BatchUtils {

	private static Log logger = LogFactory.getLog(BatchUtils.class);

	private BatchUtils() {
	}

	/**
	 * Method to invoke any private method using reflection
	 * 
	 * @param obj
	 * @param methodName
	 * @param paramCount
	 * @param params
	 * @return
	 */
	public static Object invokPrivateMethod(Object obj, String methodName, int paramCount, Object... params) {
		Method method;
		Object requiredObj = null;
		Object[] parameters = new Object[paramCount];
		Class<?>[] classArray = new Class<?>[paramCount];

		for (int i = 0; i < paramCount; i++) {
			parameters[i] = params[i];
			classArray[i] = params[i].getClass();
		}
		try {
			method = obj.getClass().getDeclaredMethod(methodName, classArray);
			method.setAccessible(true);
			requiredObj = method.invoke(obj, params);
		}  catch (Exception e) {
			
			logger.error("Error calling private method: "+e);
			
		}
		return requiredObj;
	}
	

}
