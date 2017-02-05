package com.capgemini.cif.core.domain;


/**
 * @author parthaka
 * 
 * this class is to maintain justEnough attribute name and attribute type 
 */
public class CommonEntityAttribute 
{

	private String justEnoughAttributeName;
	private String attributeSqlType;
	
	public String getJustEnoughAttributeName() {
		return justEnoughAttributeName;
	}
	public void setJustEnoughAttributeName(String justEnoughAttributeName) {
		this.justEnoughAttributeName = justEnoughAttributeName;
	}
	public String getAttributeSqlType() {
		return attributeSqlType;
	}
	public void setAttributeSqlType(String attributeSqlType) {
		this.attributeSqlType = attributeSqlType;
	}
	
	
}
