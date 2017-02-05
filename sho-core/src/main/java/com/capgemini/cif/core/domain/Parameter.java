package com.capgemini.cif.core.domain;

public class Parameter {

	private String objectId;
	private String processId;
	private String srcSysId;
	private Object value;
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getSrcSysId() {
		return srcSysId;
	}
	public void setSrcSysId(String srcSysId) {
		this.srcSysId = srcSysId;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
