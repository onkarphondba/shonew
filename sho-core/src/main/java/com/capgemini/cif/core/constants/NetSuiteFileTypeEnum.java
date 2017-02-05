package com.capgemini.cif.core.constants;

public enum NetSuiteFileTypeEnum {
	ITEM("Item"), ITEM_DESCRIPTIVE_ATTRIBTUES("Item Descriptive Attributes"), ASSETS("Assets"), IMAGES("Images"), RELATED_ITEMS("Related Items"), SITE_CATEGORIES("Site Categories"), DC_AVAILABILITY("DC Availability");

	private String fileType;

	NetSuiteFileTypeEnum(String fileType) {
		this.fileType = fileType;

	}

	public String fileType() {
		return fileType;
	}

}
