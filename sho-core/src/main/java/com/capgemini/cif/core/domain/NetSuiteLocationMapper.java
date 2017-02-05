package com.capgemini.cif.core.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class NetSuiteLocationMapper implements RowMapper<NetSuiteLocation> {

	@Override
	public NetSuiteLocation mapRow(ResultSet rs, int rowNumber) throws SQLException {
		NetSuiteLocation netSuiteLocation = new NetSuiteLocation();
		netSuiteLocation.setLocationId(rs.getInt("location_id"));
		netSuiteLocation.setLocationName(rs.getString("name").trim());
		netSuiteLocation.setLastModified(new Date());
		netSuiteLocation.setCreated(new Date());
		return netSuiteLocation;
	}
}
