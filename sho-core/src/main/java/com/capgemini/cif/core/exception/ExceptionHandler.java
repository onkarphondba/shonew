/**
 * 
 */
package com.capgemini.cif.core.exception;

import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

/**
 * @author Venkat Bhat
 *
 */

public class ExceptionHandler {
	private static Log logger = LogFactory.getLog(ExceptionHandler.class);

	protected static MessageSource resources;

	protected static String key_code_suffix = "code";
	protected static String key_msg_suffix = "message";

	
	/**
	 * This method returns Message code for a given key.
	 * @param key
	 * @return String
	 */
	protected static String getActualCodeKey(String key) {
		StringBuilder sbuf = new StringBuilder(256);
		sbuf.append(key).append('.').append(key_code_suffix);
		return sbuf.toString();
	}

	/**
	 * This method returns Message  for a given key.
	 * @param key
	 * @return
	 */
	protected static String getActualMessageKey(String key) {
		StringBuilder sbuf = new StringBuilder(256);
		sbuf.append(key).append('.').append(key_msg_suffix);
		return sbuf.toString();
	}

	/**
	 * This method creates  GenericCoreException object for a given set of input parameters.
	 * @param key
	 * @param args
	 * @return GenericCoreException
	 */
	public static GenericCoreException createException(String key, Object... args) {
		String exceptionCode = resources.getMessage(getActualCodeKey(key), null, Locale.US);
		String message = resources.getMessage(getActualMessageKey(key), args, Locale.US);
		GenericCoreException e = new GenericCoreException(message, exceptionCode);
		return e;
	}

	/**
	 * This method returns Exception message to be used in Validation/logging for a given exception.
	 * @param e
	 * @return String
	 */
	public static String getDisplayMessage(GenericCoreException e) {
		StringBuilder sbuf = new StringBuilder(1024);

		sbuf.append("Exception Code :").append(e.getExceptionCode()).append("; Validation Error : ")
				.append(e.getMessage());
		
		return sbuf.toString();
	}

	/**
	 * This method forms and returns GenericCoreException object for a given exception.
	 * @param e
	 * @return GenericCoreException
	 */
	protected static GenericCoreException getGenericCoreExceptionIfCausedByGenericCoreException(Exception e) {
		if (e instanceof GenericCoreException)
			return (GenericCoreException) e;
		Throwable t = e;
		for (int i = 0; i < 7; i++) {
			t = t.getCause();
			if (t == null)
				return null;
			if (t instanceof GenericCoreException)
				return (GenericCoreException) t;
		}
		return null;
	}

	/**
	 *  This method returns exception message of a provided Exception.
	 * @param e
	 * @param limitMessageLengthTo
	 * @return String
	 */
	public static String getExceptionMessage(Exception e, int limitMessageLengthTo) {
			logger.debug("Entering method getExceptionMessage ");
		if (limitMessageLengthTo < 0)
			limitMessageLengthTo = 200;
		Assert.notNull(e);
		StringBuilder sbuf = new StringBuilder(1024);
		logger.debug("Exception Class is:" + e.getClass().getName());
		GenericCoreException tge = getGenericCoreExceptionIfCausedByGenericCoreException(e);
		if (tge != null) {
			sbuf.append(getDisplayMessage(tge));
		} else {
			sbuf.append(ExceptionUtils.getRootCauseMessage(e));
			sbuf.append(";   First Cause: ").append(e.getMessage());
			Throwable t = e.getCause();
			if (t != null) {
				sbuf.append(";   II Cause: ").append(t.getMessage());
				t = t.getCause();
				if (t != null)
					sbuf.append(";   III Cause: ").append(t.getMessage());
			}
		}

		String r;
		if (sbuf.length() > limitMessageLengthTo){
			r = sbuf.substring(0, limitMessageLengthTo - 1);
		}else{
			r = sbuf.toString();
		}
			logger.debug(r);
		return r;

	}

	/**
	 *  This method returns whether root cause is present in the provided Exception.
	 * @param e
	 * @param pattern1
	 * @return boolean
	 */
	public static boolean rootCauseContains(Exception e, String pattern1) {
			logger.debug("Entering method rootCauseContains(Exception e, String pattern1) ");
		if (pattern1 == null)
			return false;
		String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e).toLowerCase();
		return rootCauseMessage.contains(pattern1.toLowerCase());
	}

	/**
	 * This method returns whether root cause is present in the provided Exception.
	 * @param e
	 * @param pattern1
	 * @param pattern2
	 * @return boolean
	 */
	public static boolean rootCauseContains(Exception e, String pattern1, String pattern2) {
			logger.debug("Entering method rootCauseContains(Exception e, String pattern1, String pattern2) ");
		if (pattern1 == null || pattern2 == null)
			return false;
		String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e).toLowerCase();
		return rootCauseMessage.contains(pattern1.toLowerCase()) && rootCauseMessage.contains(pattern2.toLowerCase());
	}

	/**
	 * @param pattern
	 * @param ctx
	 * @return
	 */
	public static boolean externalIdMissing(String pattern, String ctx) {
			logger.debug("Entering method externalIdMissing ");
		if (pattern == null || ctx == null)
			return false;
		if (pattern.equalsIgnoreCase(ctx))
			return true;
		return false;
	}

	public MessageSource getResources() {
		return resources;
	}

	public static void setResources(MessageSource resources) {
		ExceptionHandler.resources = resources;
	}

	public String getKey_code_suffix() {
		return key_code_suffix;
	}

	public static void setKey_code_suffix(String key_code_suffix) {
		ExceptionHandler.key_code_suffix = key_code_suffix;
	}

	public String getKey_msg_suffix() {
		return key_msg_suffix;
	}

	public static void setKey_msg_suffix(String key_msg_suffix) {
		ExceptionHandler.key_msg_suffix = key_msg_suffix;
	}

}