package com.sho.renaissance.batch.domain;

public class NetsuiteCategoryLookupCnvCustInfo 
{
	private String categoryName;
	private String categoryInternalId;
	private String correlationid;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryInternalId() {
		return categoryInternalId;
	}
	public void setCategoryInternalId(String categoryInternalId) {
		this.categoryInternalId = categoryInternalId;
	}
	public String getCorrelationid() {
		return correlationid;
	}
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}
}
