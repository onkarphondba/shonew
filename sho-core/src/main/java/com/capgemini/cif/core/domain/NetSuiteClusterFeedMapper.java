package com.capgemini.cif.core.domain;

import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;



public class NetSuiteClusterFeedMapper implements FieldSetMapper<EcomClusterFeed>{

	@Override
	public EcomClusterFeed mapFieldSet(FieldSet fieldSet) throws BindException {
		EcomClusterFeed obj = new EcomClusterFeed();
		String [] arr = fieldSet.getValues();
		obj.setLocationName(arr[0]);
		obj.setClusterName(arr[1]);
		obj.setLastModified(new Date());
		obj.setCreated(new Date());
		return obj;
	
	}

}
