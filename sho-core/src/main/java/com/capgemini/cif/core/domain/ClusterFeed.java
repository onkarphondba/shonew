package com.capgemini.cif.core.domain;

import java.util.Date;

public class ClusterFeed implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 35267159373953956L;
	private Integer id;  
	private String locationName; 
	private Integer locationId;
	private String clusterName;
	private Integer clusterId;
	private Boolean inputDataError;
	private Boolean lookupFailure; 
	private Boolean sentToNetsuite;
	private Date created;
	private Date lastModified;
	
    public ClusterFeed() {
		super();
	}
	
	public ClusterFeed(Integer id, String locationName, Integer locationId, String clusterName, Integer clusterId,
			Boolean inputDataError, Boolean lookupFailure, Boolean sentToNetsuite,Date created,Date lastModified) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationId = locationId;
		this.clusterName = clusterName;
		this.clusterId = clusterId;
		this.inputDataError = inputDataError;
		this.lookupFailure = lookupFailure;
		this.sentToNetsuite = sentToNetsuite;
		this.created = created;
		this.lastModified = lastModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public Integer getClusterId() {
		return clusterId;
	}

	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}

	public Boolean getInputDataError() {
		return inputDataError;
	}

	public void setInputDataError(Boolean inputDataError) {
		this.inputDataError = inputDataError;
	}

	public Boolean getLookupFailure() {
		return lookupFailure;
	}

	public void setLookupFailure(Boolean lookupFailure) {
		this.lookupFailure = lookupFailure;
	}

	public Boolean getSentToNetsuite() {
		return sentToNetsuite;
	}

	public void setSentToNetsuite(Boolean sentToNetsuite) {
		this.sentToNetsuite = sentToNetsuite;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
    
}
