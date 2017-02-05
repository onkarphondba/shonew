package com.capgemini.cif.core.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;



public class ClusterFeedMapper implements RowMapper<ClusterFeed> {

	@Override
	public ClusterFeed mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ClusterFeed temp = new ClusterFeed();
		temp.setClusterId(resultSet.getInt("cluster_id"));
		temp.setClusterName(resultSet.getString("cluster_name"));
		temp.setId(resultSet.getInt("id"));
		temp.setInputDataError(resultSet.getBoolean("input_data_error"));
		temp.setLocationId(resultSet.getInt("location_id"));
		temp.setLocationName(resultSet.getString("location_name"));
		temp.setLookupFailure(resultSet.getBoolean("lookup_failure"));
		temp.setSentToNetsuite(resultSet.getBoolean("sent_to_netsuite"));
		temp.setLastModified(new Date());
		return temp;
	} 

}
