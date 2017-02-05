package com.sho.renaissance.batch.domain;

import java.io.Serializable;
import java.util.List;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteProduct;
import com.sho.renaissance.batch.constants.BatchConstants;

public class ShcFileCnvCustInfo implements ConcreteProduct, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1817713576460632594L;
	private String customerId;
	private String customerIdentifier;
	private String customerType;
	private String firstName;
	private String middleName;
	private String lastName;
	private String companyName;
	private String categoryId;
	private String comments;
	private String email;
	private String phone;
	private String subsidaryId;
	private String url;
	private String shippingAddrLine1;
	private String shippingAddrLine2;
	private String shippingCity;
	private String shippingState;
	private String shippingZip;
	private String isDefaultShippingAddress;
	private String billAddrLine1;
	private String billAddrLine2;
	private String billCity;
	private String billState;
	private String billZip;
	private String isDefaultBillAddress;
	private String isValid;
	private String source;
	private String correlationid;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSubsidaryId() {
		return subsidaryId;
	}
	public void setSubsidaryId(String subsidaryId) {
		this.subsidaryId = subsidaryId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBillAddrLine1() {
		return billAddrLine1;
	}
	public void setBillAddrLine1(String billAddrLine1) {
		this.billAddrLine1 = billAddrLine1;
	}
	public String getBillAddrLine2() {
		return billAddrLine2;
	}
	public void setBillAddrLine2(String billAddrLine2) {
		this.billAddrLine2 = billAddrLine2;
	}
	public String getBillCity() {
		return billCity;
	}
	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}
	public String getBillState() {
		return billState;
	}
	public void setBillState(String billState) {
		this.billState = billState;
	}
	public String getBillZip() {
		return billZip;
	}
	public void setBillZip(String billZip) {
		this.billZip = billZip;
	}
	public String getIsDefaultBillAddress() {
		return isDefaultBillAddress;
	}
	public void setIsDefaultBillAddress(String isDefaultBillAddress) {
		this.isDefaultBillAddress = isDefaultBillAddress;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getCustomerIdentifier() {
		return customerIdentifier;
	}
	public void setCustomerIdentifier(String customerIdentifier) {
		this.customerIdentifier = customerIdentifier;
	}
	public String getShippingAddrLine1() {
		return shippingAddrLine1;
	}
	public void setShippingAddrLine1(String shippingAddrLine1) {
		this.shippingAddrLine1 = shippingAddrLine1;
	}
	public String getShippingAddrLine2() {
		return shippingAddrLine2;
	}
	public void setShippingAddrLine2(String shippingAddrLine2) {
		this.shippingAddrLine2 = shippingAddrLine2;
	}
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	public String getShippingState() {
		return shippingState;
	}
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	public String getShippingZip() {
		return shippingZip;
	}
	public void setShippingZip(String shippingZip) {
		this.shippingZip = shippingZip;
	}
	public String getIsDefaultShippingAddress() {
		return isDefaultShippingAddress;
	}
	public void setIsDefaultShippingAddress(String isDefaultShippingAddress) {
		this.isDefaultShippingAddress = isDefaultShippingAddress;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCorrelationid() {
		return correlationid;
	}
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}
	
	@Override
	public List<ConcreteComponentProduct> getAllChildren() {
		return null;
	}
	@Override
	public String getFriendlyName() {
		return BatchConstants.POS_CNV_CUSTINFO_FRIENDLY_NAME;
	}
	@Override
	public Long getInternalId() {
		return null;
	}

	
	
}
