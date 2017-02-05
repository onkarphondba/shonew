package com.capgemini.cif.core.domain;

import java.util.Date;

public class BatchItemProcessMessage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3013412524588748359L;
	private Long id;
	private Long batchItemId;
	private Integer errorMsgSeq;
	private Integer iemLevel;
	private Integer iemSequence;
	private Boolean isError;
	private String message;
	private Date created;
	private Date lastModified;

	public BatchItemProcessMessage() {
	}

	public BatchItemProcessMessage(Long id, Long batchItemId, Integer erroMsgSeq, Integer iemLevel, Integer iemSequence,
			Boolean isError, String message, Date created, Date lastModified) {
		super();
		this.id = id;
		this.batchItemId = batchItemId;
		this.errorMsgSeq = erroMsgSeq;
		this.iemLevel = iemLevel;
		this.iemSequence = iemSequence;
		this.isError = isError;
		this.message = message;
		this.created = created;
		this.lastModified = lastModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBatchItemId() {
		return batchItemId;
	}

	public void setBatchItemId(Long batchItemId) {
		this.batchItemId = batchItemId;
	}

	public Integer getErrorMsgSeq() {
		return errorMsgSeq;
	}

	public void setErrorMsgSeq(Integer erroMsgSeq) {
		this.errorMsgSeq = erroMsgSeq;
	}

	public Integer getIemLevel() {
		return iemLevel;
	}

	public void setIemLevel(Integer iemLevel) {
		this.iemLevel = iemLevel;
	}

	public Integer getIemSequence() {
		return iemSequence;
	}

	public void setIemSequence(Integer iemSequence) {
		this.iemSequence = iemSequence;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
