package com.capgemini.cif.core;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;
import com.capgemini.cif.core.validation.ConcreteProduct;

@XmlRootElement(name = "NetsuiteChildItem")
@XmlType(propOrder = { "itemId", "name" })
public class NetsuiteChildTestItemInterface implements Serializable, ConcreteProduct {

	private static final long serialVersionUID = -5646031438808104690L;
	private Long itemId;
	private String name;

	public NetsuiteChildTestItemInterface() {
		super();
	}

	public NetsuiteChildTestItemInterface(Long itemId, String name) {
		super();
		this.itemId = itemId;
		this.name = name;
	}

	@XmlElement(nillable = true)
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@NotNull
	@XmlElement(nillable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<ConcreteComponentProduct> getAllChildren() {
		return null;
	}

	@Override
	public String getFriendlyName() {
		return "ChildTestInterface";
	}

	@Override
	public Long getInternalId() {

		return itemId;
	}

}
