/**
 * 
 */
package com.capgemini.cif.core.exception;

import java.sql.SQLException;

import org.springframework.jdbc.UncategorizedSQLException;

/**
 * @author Venkat Bhat
 *
 */
public class GenericCoreException extends Exception {

	private static final long serialVersionUID = -1776631507072582049L;

	protected String exceptionCode = "";
	protected String shortDescr = "";
	protected String additionalErrorText = "";

	protected GenericCoreException() {
	}

	public GenericCoreException(final String message) {
		super(message);
	}

	public GenericCoreException(final String message, final String exceptionCode) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public GenericCoreException(final String message, final Throwable e) {
		super(message);
		if (e instanceof UncategorizedSQLException) {
			SQLException e1 = ((UncategorizedSQLException) e).getSQLException();
			exceptionCode = Integer.toString(e1.getErrorCode());
			additionalErrorText = e1.getMessage();
		}
		if (e instanceof GenericCoreException) {
			exceptionCode = "" + ((GenericCoreException) e).getExceptionCode();
		}
		this.initCause(e);
	}

	public GenericCoreException(final Throwable e) {
		super(getFormattedMessage(e));
		if (e instanceof UncategorizedSQLException) {
			SQLException e1 = ((UncategorizedSQLException) e).getSQLException();
			exceptionCode =Integer.toString(e1.getErrorCode());
			additionalErrorText = ((UncategorizedSQLException) e).getMessage();
		}
		if (e instanceof GenericCoreException) {
			exceptionCode = "" + ((GenericCoreException) e).getExceptionCode();
		}
		this.initCause(e);
	}

	public GenericCoreException(final String message, final Throwable exception, final String exceptionCode) {
		super(message, exception);
		this.exceptionCode = exceptionCode;
	}

	public GenericCoreException(final String message, final String AdditionalErrorText, final String exceptionCode) {
		super(message);
		this.exceptionCode = exceptionCode;
		this.additionalErrorText = AdditionalErrorText;
	}

	public GenericCoreException(final String message, final Throwable exception, final String AdditionalErrorText,
			final String exceptionCode) {
		super(message, exception);
		this.exceptionCode = exceptionCode;
		this.additionalErrorText = AdditionalErrorText;
	}

	public GenericCoreException(final String message, final String shortdescr, final Throwable exception,
			final String exceptionCode) {
		super(message, exception);
		this.exceptionCode = exceptionCode;
		this.shortDescr = shortdescr;

	}

	public GenericCoreException(final Throwable exception, final String exceptionCode) {
		super(exception);
		this.exceptionCode = exceptionCode;
	}

	@Override
	public String toString() {
		return "GenericCoreException [exceptionCode=" + exceptionCode + ", shortDescr=" + shortDescr
				+ ", AdditionalErrorText=" + additionalErrorText + ", Message=" + this.getMessage() + "]";
	}

	private static String getFormattedMessage(Throwable e) {
		String message;
		if (e instanceof UncategorizedSQLException) {
			SQLException e1 = ((UncategorizedSQLException) e).getSQLException();
			message = e1.getMessage();

		} else {
			message = e.getMessage();
		}
		return message;
	}

	public String getShortDescr() {
		return shortDescr;
	}

	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}

	public String getAdditionalErrorText() {
		return additionalErrorText;
	}

	public void setAdditionalErrorText(String additionalErrorText) {
		this.additionalErrorText = additionalErrorText;
	}

	public final String getExceptionCode() {
		return exceptionCode;
	}

	public final void setExceptionCode(final String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

}