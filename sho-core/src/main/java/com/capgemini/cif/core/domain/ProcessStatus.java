package com.capgemini.cif.core.domain;

import java.io.Serializable;
import java.util.Date;

public class ProcessStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5792919170594144301L;
	String name;
	String status;
	Date created; 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
}
