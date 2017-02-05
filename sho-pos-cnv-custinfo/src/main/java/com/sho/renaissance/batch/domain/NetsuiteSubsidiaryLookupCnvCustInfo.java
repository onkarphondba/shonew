package com.sho.renaissance.batch.domain;

import java.io.Serializable;
import java.util.List;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteProduct;
import com.sho.renaissance.batch.constants.BatchConstants;

public class NetsuiteSubsidiaryLookupCnvCustInfo implements ConcreteProduct, Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 2009960245664401275L;
	private String subsidiaryName;
	private String subsidiaryInternalId;
	private String correlationid;
	
	public String getSubsidiaryName() {
		return subsidiaryName;
	}
	
	public void setSubsidiaryName(String subsidiaryName) {
		this.subsidiaryName = subsidiaryName;
	}
	
	public String getSubsidiaryInternalId() {
		return subsidiaryInternalId;
	}
	
	public void setSubsidiaryInternalId(String subsidiaryInternalId) {
		this.subsidiaryInternalId = subsidiaryInternalId;
	}
	
	public String getCorrelationid() {
		return correlationid;
	}

	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}
	
	@Override
	public List<ConcreteComponentProduct> getAllChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getFriendlyName() {
		return BatchConstants.POS_CNV_CUSTINFO_FRIENDLY_NAME;
	}
	
	@Override
	public Long getInternalId() {
		// TODO Auto-generated method stub
		return null;
	}
	   
}
