package com.capgemini.cif.core.domain;

public class BatchItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4554014245262667324L;
	private Long id;
	private String correlationId;
	private String itemXMLData;
	private Boolean isValid;
	private String errorMessage;
	private String entityType;
	private Boolean sentToTarget;


	public BatchItem() {
	}
	
	public BatchItem(Long id, String correlationId, String itemXMLData, Boolean isValid, String errorMessage,
			String entityType) {
		super();
		this.id = id;
		this.correlationId = correlationId;
		this.itemXMLData = itemXMLData;
		this.isValid = isValid;
		this.errorMessage = errorMessage;
		this.entityType = entityType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getItemXMLData() {
		return itemXMLData;
	}

	public void setItemXMLData(String itemXMLData) {
		this.itemXMLData = itemXMLData;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Boolean getSentToTarget() {
		return sentToTarget;
	}

	public void setSentToTarget(Boolean sentToTarget) {
		this.sentToTarget = sentToTarget;
	}
	
}