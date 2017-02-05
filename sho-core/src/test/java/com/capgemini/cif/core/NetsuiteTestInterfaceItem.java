package com.capgemini.cif.core;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteCompositeProduct;

@XmlRootElement(name = "NetsuiteItem")
@XmlType(propOrder = { "itemId", "name", "salesDescription", "upcCode", "shcItemNumber", "sizeName", "sizeCurveName",
		"styleCode", "colorCode", "variantCode", "child" })
public class NetsuiteTestInterfaceItem implements ConcreteCompositeProduct, Serializable {

	/**
	 * 
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

	private List<ConcreteComponentProduct> child;

	@XmlElement(nillable = true)
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Size(max = 5)
	@NotNull
	@XmlElement(nillable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(nillable = true)
	public String getSalesDescription() {
		return salesDescription;
	}

	public void setSalesDescription(String salesDescription) {
		this.salesDescription = salesDescription;
	}

	@XmlElement(nillable = true)
	public String getUpcCode() {
		return upcCode;
	}

	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}

	@XmlElement(nillable = true)
	public String getShcItemNumber() {
		return shcItemNumber;
	}

	public void setShcItemNumber(String shcItemNumber) {
		this.shcItemNumber = shcItemNumber;
	}

	@XmlElement(nillable = true)
	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	@XmlElement(nillable = true)
	public String getSizeCurveName() {
		return sizeCurveName;
	}

	public void setSizeCurveName(String sizeCurveName) {
		this.sizeCurveName = sizeCurveName;
	}

	@XmlElement(nillable = true)
	public String getStyleCode() {
		return styleCode;
	}

	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	@XmlElement(nillable = true)
	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	@XmlElement(nillable = true)
	public String getVariantCode() {
		return variantCode;
	}

	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}

	// @XmlElementWrapper
	// @XmlAnyElement
	@XmlElement(type = NetsuiteChildTestItemInterface.class, name = "NetsuiteChildItem", nillable = true)
	public List<ConcreteComponentProduct> getChild() {
		return child;
	}

	public void setChild(List<ConcreteComponentProduct> child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "NetsuiteItem [itemId=" + itemId + ", name=" + name + ", salesDescription=" + salesDescription
				+ ", upcCode=" + upcCode + ", shcItemNumber=" + shcItemNumber + ", sizeName=" + sizeName
				+ ", sizeCurveName=" + sizeCurveName + ", styleCode=" + styleCode + ", colorCode=" + colorCode
				+ ", variantCode=" + variantCode + "]";
	}

	@Override
	public List<ConcreteComponentProduct> getAllChildren() {
		return child;
	}

	@Override
	public String getFriendlyName() {

		return "ItemTest";
	}

	@Override
	public Long getInternalId() {

		return itemId;
	}
	
	
}
