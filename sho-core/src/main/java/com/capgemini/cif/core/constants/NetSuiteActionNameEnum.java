package com.capgemini.cif.core.constants;

public enum NetSuiteActionNameEnum {
	UPDATE("Update"), CREATE("Create"), DELETE("Delete"), ERROR("Error"), INACTIVE_ITEM("Inactive Item"), CREATE_ITEM("Create Item");

	private String actionName;

	NetSuiteActionNameEnum(String actionName) {
		this.actionName = actionName;

	}

	public String actionName() {
		return actionName;
	}

}
