package com.sho.renaissance.batch.processor;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteProduct;
import com.capgemini.cif.core.validation.ValidationParameterConstraintConstants;
import com.sho.renaissance.batch.constants.BatchConstants;

/**
 * @author gdhimate
 *         This is the NetSuite 'NetSuiteSiteOpenClosed' entity. It contains the list of
 *         attributes which are required to be moved to JustEnough.There are
 *         annotation based validations applied on required fields.
 */
@XmlRootElement(name = "NetSuiteItem")
@XmlType(propOrder = { "itemId",
 "name",
 "salesDescription",
 "upcCode",
 "shcItemNumber",
 "sizeName",
 "sizeCurveName",
 "styleCode",
 "colorCode",
 "variantCode" })
public class NetSuiteItem implements ConcreteProduct, Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5646031438808104690L;

	private Long itemId;
	private String name;
	private String salesDescription;
	private String upcCode;
	private String shcItemNumber;
	private String sizeName;
	private String sizeCurveName;
	private String styleCode;
	private String colorCode;
	private String variantCode;

	/** getter method for itemid
	 * @return
	 */
	@XmlElement(nillable = true)
	public Long getItemId() {
		return itemId;
	}

	/** setter method for itemid
	 * @param itemId
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/** getter method for name
	 * @return
	 */
	@Size(max = ValidationParameterConstraintConstants.MAX_VAL_128)
	@NotNull
	@XmlElement(nillable = true)
	public String getName() {
		return name;
	}

	/** setter method for Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** getter method for sales description
	 * @return
	 */
	@XmlElement(nillable = true)
	public String getSalesDescription() {
		return salesDescription;
	}

	/** setter method for Sales Description
	 * @param salesDescription
	 */
	public void setSalesDescription(String salesDescription) {
		this.salesDescription = salesDescription;
	}

	/** getter method for upc code
	 * @return
	 */
	@XmlElement(nillable = true)
	public String getUpcCode() {
		return upcCode;
	}

	/** setter method for Upc Code
	 * @param upcCode
	 */
	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}

	/** shc item number
	 * @return
	 */
	@XmlElement(nillable = true)
	public String getShcItemNumber() {
		return shcItemNumber;
	}

	/** Shc Item number
	 * @param shcItemNumber
	 */
	public void setShcItemNumber(String shcItemNumber) {
		this.shcItemNumber = shcItemNumber;
	}

	/** getter method for size name
	 * @return
	 */
	public String getSizeName() {
		return sizeName;
	}

	/** Size Name
	 * @param sizeName
	 */
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	/** getter method for size curve name
	 * @return
	 */
	public String getSizeCurveName() {
		return sizeCurveName;
	}

	/** Curve Name
	 * @param sizeCurveName
	 */
	public void setSizeCurveName(String sizeCurveName) {
		this.sizeCurveName = sizeCurveName;
	}

	/** getter method for style code
	 * @return
	 */
	public String getStyleCode() {
		return styleCode;
	}

	/** Style Code
	 * @param styleCode
	 */
	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	/** getter method for color code
	 * @return
	 */
	public String getColorCode() {
		return colorCode;
	}

	/** Color code
	 * @param colorCode
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	/** getter method for variant code
	 * @return
	 */
	public String getVariantCode() {
		return variantCode;
	}

	/** Variant code
	 * @param variantCode
	 */
	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NetSuiteItem [itemId=" + itemId + ", name=" + name + ", salesDescription=" + salesDescription
				+ ", upcCode=" + upcCode + ", shcItemNumber=" + shcItemNumber + ", sizeName=" + sizeName
				+ ", sizeCurveName=" + sizeCurveName + ", styleCode=" + styleCode + ", colorCode=" + colorCode
				+ ", variantCode=" + variantCode + "]";
	}

	/* (non-Javadoc)
	 * @see com.capgemini.cif.core.validation.ConcreteComponentProduct#getAllChildren()
	 */
	@Override
	public List<ConcreteComponentProduct> getAllChildren() {

		return null;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.cif.core.validation.ConcreteComponentProduct#getFriendlyName()
	 */
	@Override
	public String getFriendlyName() {

		return BatchConstants.ITEM_FRIENDLY_NAME;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.cif.core.validation.ConcreteComponentProduct#getInternalId()
	 */
	@Override
	public Long getInternalId() {
		return itemId;
	}

}
