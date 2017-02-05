package com.capgemini.cif.core.domain;

import java.io.Serializable;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteProduct;
import com.capgemini.cif.core.validation.ValidationParameterConstraintConstants;

/**
 * @author parthaka This class is used to map NetSuite's  weekly sales entity
 *         to BatchItem object.There are annotation based validations applied on
 *         required fields.
 */
@XmlRootElement(name = "SalesHistory")
@XmlType(propOrder = { "itemCode", "siteCode", "salesTypeCode", "returnFlag",
		"salesDate", "salesQty", "seasonCode", "totalCost", "totalRevenue", "totalOriginalRetail"})
public class NetSuiteWeeklySalesHistory implements ConcreteProduct, Serializable {

	
	public NetSuiteWeeklySalesHistory() {
		super();
	}

	private static final long serialVersionUID = 1L;
	private String itemCode; 
	private String siteCode;
	private String salesTypeCode;
	private String returnFlag;
	private String salesDate;
	private Double salesQty;
	private String seasonCode;	
	private Double totalCost;
	private Double totalRevenue;
	private Double totalOriginalRetail;
	
	
	@Size(max = ValidationParameterConstraintConstants.MAX_VAL_128)
	@XmlElement(nillable = true,name="ItemCode")
	@NotNull
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Size(max = ValidationParameterConstraintConstants.MAX_VAL_128)
	@XmlElement(nillable = true,name="SiteCode")
	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	
	@Size(max = ValidationParameterConstraintConstants.MAX_VAL_128)
	@XmlElement(nillable = true,name="SalesTypeCode")
	@NotNull
	public String getSalesTypeCode() {
		return salesTypeCode;
	}

	public void setSalesTypeCode(String salesTypeCode) {
		this.salesTypeCode = salesTypeCode;
	}
	
	@XmlElement(nillable = true,name="ReturnFlag")
	@NotNull
	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	
	@XmlElement(nillable = true,name="SalesDate")
	@NotNull
	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	@XmlElement(nillable = true,name="SalesQty")
	@NotNull
	public Double getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(Double salesQty) {
		this.salesQty = salesQty;
	}

	@Size(max = ValidationParameterConstraintConstants.MAX_VAL_128)
	@XmlElement(nillable = true,name="SeasonCode")
	public String getSeasonCode() {
		return seasonCode;
	}

	public void setSeasonCode(String seasonCode) {
		this.seasonCode = seasonCode;
	}

	@XmlElement(nillable = true,name="TotalCost")
	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	@XmlElement(nillable = true,name="TotalRevenue")
	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	@XmlElement(nillable = true,name="TotalOriginalRetail")
	public Double getTotalOriginalRetail() {
		return totalOriginalRetail;
	}

	public void setTotalOriginalRetail(Double totalOriginalRetail) {
		this.totalOriginalRetail = totalOriginalRetail;
	}


	public List<ConcreteComponentProduct> getAllChildren() {
		return null;
	}

	public String getFriendlyName() {
		return null;
	}

	public Long getInternalId() {
		return 0L;
	}



}