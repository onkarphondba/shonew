package com.capgemini.cif.core.constants;

public enum NetSuiteBatchStatusEnum {
	FAILED("Failed"), FILE_1_PROCESSED("File 1 Processed"), PARTIALLY_PROCESSED("Partially Processed"), PROCESSED("Processed");

	private String batchStatusType;

	NetSuiteBatchStatusEnum(String batchStatusType) {
		this.batchStatusType = batchStatusType;

	}

	public String batchStatusType() {
		return batchStatusType;
	}
}
