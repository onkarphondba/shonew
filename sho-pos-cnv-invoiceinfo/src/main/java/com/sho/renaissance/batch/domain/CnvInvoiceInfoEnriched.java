package com.sho.renaissance.batch.domain;

import java.sql.Timestamp;
import java.util.Date;

public class CnvInvoiceInfoEnriched {

	private String shoCustomerId;
    private String shccustomeridentifier; 
    private String shoInvoiceNumber;
    private String shcInvoiceNumber;
    private String shcSalesCheckNumber;
    private Date shctransactiondate;
    private Timestamp shctransactiontime;
    private String sywmemberid;
	private Long sywpointsredeemed;
    private Long sywpointsearned;
    private String externalid;
    private String storenumber;
    private String locationinternalid;
    private String department;
    private String departmentinternalid;
    private Double totalamount;
    private Double totaltax;
    private String discountitem;
    private Double associatediscount;
    private Double transactiondiscount;
    private String memo;
    private String shippingAddrLine1;
    private String shippingAddrLine2;
    private String shippingCity;
    private String shippingState;
    private String shippingZip;
    private String billingaddresstype;
    private String billingAddrLine1;
    private String billingAddrLine2;
    private String billingCity;
    private String billingState;
    private String billingZip;
    private String shcitemnumber;
    private String iteminternalid;
    private String plus4;
    private String sequenceid;
    private Long quantity;
    private Double regularprice;
    private Double pluprice;
    private Double sellingprice;
    private String taxcode;
    private Double taxrate;
    private Double taxamount;
    private String returnallowed;
    private String updatedInNetsuite;
    private String source;
    private String correlationid;
    private Integer id;
    
	/**
	 * @return the shoCustomerId
	 */
	public String getShoCustomerId() {
		return shoCustomerId;
	}
	/**
	 * @param shoCustomerId the shoCustomerId to set
	 */
	public void setShoCustomerId(String shoCustomerId) {
		this.shoCustomerId = shoCustomerId;
	}
	/**
	 * @return the shccustomeridentifier
	 */
	public String getShccustomeridentifier() {
		return shccustomeridentifier;
	}
	/**
	 * @param shccustomeridentifier the shccustomeridentifier to set
	 */
	public void setShccustomeridentifier(String shccustomeridentifier) {
		this.shccustomeridentifier = shccustomeridentifier;
	}
	/**
	 * @return the shoInvoiceNumber
	 */
	public String getShoInvoiceNumber() {
		return shoInvoiceNumber;
	}
	/**
	 * @param shoInvoiceNumber the shoInvoiceNumber to set
	 */
	public void setShoInvoiceNumber(String shoInvoiceNumber) {
		this.shoInvoiceNumber = shoInvoiceNumber;
	}
	/**
	 * @return the shcInvoiceNumber
	 */
	public String getShcInvoiceNumber() {
		return shcInvoiceNumber;
	}
	/**
	 * @param shcInvoiceNumber the shcInvoiceNumber to set
	 */
	public void setShcInvoiceNumber(String shcInvoiceNumber) {
		this.shcInvoiceNumber = shcInvoiceNumber;
	}
	/**
	 * @return the shcSalesCheckNumber
	 */
	public String getShcSalesCheckNumber() {
		return shcSalesCheckNumber;
	}
	/**
	 * @param shcSalesCheckNumber the shcSalesCheckNumber to set
	 */
	public void setShcSalesCheckNumber(String shcSalesCheckNumber) {
		this.shcSalesCheckNumber = shcSalesCheckNumber;
	}
	/**
	 * @return the shctransactiondate
	 */
	public Date getShctransactiondate() {
		return shctransactiondate;
	}
	/**
	 * @param shctransactiondate the shctransactiondate to set
	 */
	public void setShctransactiondate(Date shctransactiondate) {
		this.shctransactiondate = shctransactiondate;
	}
	/**
	 * @return the shctransactiontime
	 */
	public Timestamp getShctransactiontime() {
		return shctransactiontime;
	}
	/**
	 * @param shctransactiontime the shctransactiontime to set
	 */
	public void setShctransactiontime(Timestamp shctransactiontime) {
		this.shctransactiontime = shctransactiontime;
	}
	/**
	 * @return the sywmemberid
	 */
	public String getSywmemberid() {
		return sywmemberid;
	}
	/**
	 * @param sywmemberid the sywmemberid to set
	 */
	public void setSywmemberid(String sywmemberid) {
		this.sywmemberid = sywmemberid;
	}
	/**
	 * @return the sywpointsredeemed
	 */
	public Long getSywpointsredeemed() {
		return sywpointsredeemed;
	}
	/**
	 * @param sywpointsredeemed the sywpointsredeemed to set
	 */
	public void setSywpointsredeemed(Long sywpointsredeemed) {
		this.sywpointsredeemed = sywpointsredeemed;
	}
	/**
	 * @return the sywpointsearned
	 */
	public Long getSywpointsearned() {
		return sywpointsearned;
	}
	/**
	 * @param sywpointsearned the sywpointsearned to set
	 */
	public void setSywpointsearned(Long sywpointsearned) {
		this.sywpointsearned = sywpointsearned;
	}
	/**
	 * @return the externalid
	 */
	public String getExternalid() {
		return externalid;
	}
	/**
	 * @param externalid the externalid to set
	 */
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	/**
	 * @return the storenumber
	 */
	public String getStorenumber() {
		return storenumber;
	}
	/**
	 * @param storenumber the storenumber to set
	 */
	public void setStorenumber(String storenumber) {
		this.storenumber = storenumber;
	}
	/**
	 * @return the locationinternalid
	 */
	public String getLocationinternalid() {
		return locationinternalid;
	}
	/**
	 * @param locationinternalid the locationinternalid to set
	 */
	public void setLocationinternalid(String locationinternalid) {
		this.locationinternalid = locationinternalid;
	}
	
    /**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * @return the departmentinternalid
	 */
	public String getDepartmentinternalid() {
		return departmentinternalid;
	}
	/**
	 * @param departmentinternalid the departmentinternalid to set
	 */
	public void setDepartmentinternalid(String departmentinternalid) {
		this.departmentinternalid = departmentinternalid;
	}
	/**
	 * @return the totalamount
	 */
	public Double getTotalamount() {
		return totalamount;
	}
	/**
	 * @param totalamount the totalamount to set
	 */
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	/**
	 * @return the totaltax
	 */
	public Double getTotaltax() {
		return totaltax;
	}
	/**
	 * @param totaltax the totaltax to set
	 */
	public void setTotaltax(Double totaltax) {
		this.totaltax = totaltax;
	}
	/**
	 * @return the discountitem
	 */
	public String getDiscountitem() {
		return discountitem;
	}
	/**
	 * @param discountitem the discountitem to set
	 */
	public void setDiscountitem(String discountitem) {
		this.discountitem = discountitem;
	}
	/**
	 * @return the associatediscount
	 */
	public Double getAssociatediscount() {
		return associatediscount;
	}
	/**
	 * @param associatediscount the associatediscount to set
	 */
	public void setAssociatediscount(Double associatediscount) {
		this.associatediscount = associatediscount;
	}
	/**
	 * @return the transactiondiscount
	 */
	public Double getTransactiondiscount() {
		return transactiondiscount;
	}
	/**
	 * @param transactiondiscount the transactiondiscount to set
	 */
	public void setTransactiondiscount(Double transactiondiscount) {
		this.transactiondiscount = transactiondiscount;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the shippingAddrLine1
	 */
	public String getShippingAddrLine1() {
		return shippingAddrLine1;
	}
	/**
	 * @param shippingAddrLine1 the shippingAddrLine1 to set
	 */
	public void setShippingAddrLine1(String shippingAddrLine1) {
		this.shippingAddrLine1 = shippingAddrLine1;
	}
	/**
	 * @return the shippingAddrLine2
	 */
	public String getShippingAddrLine2() {
		return shippingAddrLine2;
	}
	/**
	 * @param shippingAddrLine2 the shippingAddrLine2 to set
	 */
	public void setShippingAddrLine2(String shippingAddrLine2) {
		this.shippingAddrLine2 = shippingAddrLine2;
	}
	/**
	 * @return the shippingCity
	 */
	public String getShippingCity() {
		return shippingCity;
	}
	/**
	 * @param shippingCity the shippingCity to set
	 */
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	/**
	 * @return the shippingState
	 */
	public String getShippingState() {
		return shippingState;
	}
	/**
	 * @param shippingState the shippingState to set
	 */
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	/**
	 * @return the shippingZip
	 */
	public String getShippingZip() {
		return shippingZip;
	}
	/**
	 * @param shippingZip the shippingZip to set
	 */
	public void setShippingZip(String shippingZip) {
		this.shippingZip = shippingZip;
	}
	/**
	 * @return the billingaddresstype
	 */
	public String getBillingaddresstype() {
		return billingaddresstype;
	}
	/**
	 * @param billingaddresstype the billingaddresstype to set
	 */
	public void setBillingaddresstype(String billingaddresstype) {
		this.billingaddresstype = billingaddresstype;
	}
	/**
	 * @return the billingAddrLine1
	 */
	public String getBillingAddrLine1() {
		return billingAddrLine1;
	}
	/**
	 * @param billingAddrLine1 the billingAddrLine1 to set
	 */
	public void setBillingAddrLine1(String billingAddrLine1) {
		this.billingAddrLine1 = billingAddrLine1;
	}
	/**
	 * @return the billingAddrLine2
	 */
	public String getBillingAddrLine2() {
		return billingAddrLine2;
	}
	/**
	 * @param billingAddrLine2 the billingAddrLine2 to set
	 */
	public void setBillingAddrLine2(String billingAddrLine2) {
		this.billingAddrLine2 = billingAddrLine2;
	}
	/**
	 * @return the billingCity
	 */
	public String getBillingCity() {
		return billingCity;
	}
	/**
	 * @param billingCity the billingCity to set
	 */
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	/**
	 * @return the billingState
	 */
	public String getBillingState() {
		return billingState;
	}
	/**
	 * @param billingState the billingState to set
	 */
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	/**
	 * @return the billingZip
	 */
	public String getBillingZip() {
		return billingZip;
	}
	/**
	 * @param billingZip the billingZip to set
	 */
	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}
	/**
	 * @return the shcitemnumber
	 */
	public String getShcitemnumber() {
		return shcitemnumber;
	}
	/**
	 * @param shcitemnumber the shcitemnumber to set
	 */
	public void setShcitemnumber(String shcitemnumber) {
		this.shcitemnumber = shcitemnumber;
	}
	/**
	 * @return the iteminternalid
	 */
	public String getIteminternalid() {
		return iteminternalid;
	}
	/**
	 * @param iteminternalid the iteminternalid to set
	 */
	public void setIteminternalid(String iteminternalid) {
		this.iteminternalid = iteminternalid;
	}
	/**
	 * @return the plus4
	 */
	public String getPlus4() {
		return plus4;
	}
	/**
	 * @param plus4 the plus4 to set
	 */
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	/**
	 * @return the sequenceid
	 */
	public String getSequenceid() {
		return sequenceid;
	}
	/**
	 * @param sequenceid the sequenceid to set
	 */
	public void setSequenceid(String sequenceid) {
		this.sequenceid = sequenceid;
	}
	/**
	 * @return the quantity
	 */
	public Long getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the regularprice
	 */
	public Double getRegularprice() {
		return regularprice;
	}
	/**
	 * @param regularprice the regularprice to set
	 */
	public void setRegularprice(Double regularprice) {
		this.regularprice = regularprice;
	}
	/**
	 * @return the pluprice
	 */
	public Double getPluprice() {
		return pluprice;
	}
	/**
	 * @param pluprice the pluprice to set
	 */
	public void setPluprice(Double pluprice) {
		this.pluprice = pluprice;
	}
	/**
	 * @return the sellingprice
	 */
	public Double getSellingprice() {
		return sellingprice;
	}
	/**
	 * @param sellingprice the sellingprice to set
	 */
	public void setSellingprice(Double sellingprice) {
		this.sellingprice = sellingprice;
	}
	/**
	 * @return the taxcode
	 */
	public String getTaxcode() {
		return taxcode;
	}
	/**
	 * @param taxcode the taxcode to set
	 */
	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	/**
	 * @return the taxrate
	 */
	public Double getTaxrate() {
		return taxrate;
	}
	/**
	 * @param taxrate the taxrate to set
	 */
	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
	}
	/**
	 * @return the taxamount
	 */
	public Double getTaxamount() {
		return taxamount;
	}
	/**
	 * @param taxamount the taxamount to set
	 */
	public void setTaxamount(Double taxamount) {
		this.taxamount = taxamount;
	}
	/**
	 * @return the returnallowed
	 */
	public String getReturnallowed() {
		return returnallowed;
	}
	/**
	 * @param returnallowed the returnallowed to set
	 */
	public void setReturnallowed(String returnallowed) {
		this.returnallowed = returnallowed;
	}
	/**
	 * @return the updatedInNetsuite
	 */
	public String getUpdatedInNetsuite() {
		return updatedInNetsuite;
	}
	/**
	 * @param updatedInNetsuite the updatedInNetsuite to set
	 */
	public void setUpdatedInNetsuite(String updatedInNetsuite) {
		this.updatedInNetsuite = updatedInNetsuite;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the correlationid
	 */
	public String getCorrelationid() {
		return correlationid;
	}
	/**
	 * @param correlationid the correlationid to set
	 */
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
    
	
	
	
}
