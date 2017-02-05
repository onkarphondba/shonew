package com.sho.renaissance.batch.domain;



/**	
 * @author parthaka
 * This class is use to maintain Lookup data which is coming 
 * from Netsuite to compare with 'isPurchaseOrder' field.
 */

public class NetSuiteLookupCnvCustInfo { 

	private String customerId;
	private String shcCustomerIdentifier;
	private String customerType;
	private String firstName;
	private String middleName;
	private String lastName;
	private String companyName;
	private String categoryName;
	private String categoryId;
	private String comments;
	private String email;
	private String phone;
	private String subsidaryName;
	private String subsidaryId;
	private String url;
	private String shipAddrLine1;
	private String shipAddrLine2;
	private String shipCity;
	private String shipState;
	private String shipZip;
	private String isDefaultShipAddress;
	private String billAddrLine1;
	private String billAddrLine2;
	private String billCity;
	private String billState;
	private String billZip;
	private String isDefaultBillAddress;
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
	public void setCategoryId(String string) {
		this.categoryId = string;
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
	public void setSubsidaryId(String string) {
		this.subsidaryId = string;
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
	
	public String getShcCustomerIdentifier() {
		return shcCustomerIdentifier;
	}
	public void setShcCustomerIdentifier(String shcCustomerIdentifier) {
		this.shcCustomerIdentifier = shcCustomerIdentifier;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubsidaryName() {
		return subsidaryName;
	}
	public void setSubsidaryName(String subsidaryName) {
		this.subsidaryName = subsidaryName;
	}
	public String getShipAddrLine1() {
		return shipAddrLine1;
	}
	public void setShipAddrLine1(String shipAddrLine1) {
		this.shipAddrLine1 = shipAddrLine1;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipAddrLine2() {
		return shipAddrLine2;
	}
	public void setShipAddrLine2(String shipAddrLine2) {
		this.shipAddrLine2 = shipAddrLine2;
	}
	public String getShipState() {
		return shipState;
	}
	public void setShipState(String shipState) {
		this.shipState = shipState;
	}
	public String getShipZip() {
		return shipZip;
	}
	public void setShipZip(String shipZip) {
		this.shipZip = shipZip;
	}
	public String getIsDefaultShipAddress() {
		return isDefaultShipAddress;
	}
	public void setIsDefaultShipAddress(String isDefaultShipAddress) {
		this.isDefaultShipAddress = isDefaultShipAddress;
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

}
