package com.capgemini.cif.core.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;



/**
 * @author gdhimate
 * 
 *         Mapper for NetSuiteItem
 *
 */
public class NetSuiteClusterMapper implements RowMapper<NetSuiteCluster> {

	@Override
	public NetSuiteCluster mapRow(ResultSet rs, int rowNumber) throws SQLException {
		NetSuiteCluster netSuiteCluster = new NetSuiteCluster();
		netSuiteCluster.setClusterId(rs.getInt("price_type_id"));
		netSuiteCluster.setClusterName(rs.getString("name"));
		netSuiteCluster.setCreated(new Date());
		netSuiteCluster.setLastModified(new Date());
		return netSuiteCluster;
	}

}
