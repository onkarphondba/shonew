package com.sho.renaissance.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.sho.renaissance.batch.domain.NetsuiteCategoryLookupCnvCustInfo;

public class NetSuitelookupCategoryCnvCustinfoMapper implements RowMapper<NetsuiteCategoryLookupCnvCustInfo> {
	
	private String correlationId;

	@Override
	public NetsuiteCategoryLookupCnvCustInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		NetsuiteCategoryLookupCnvCustInfo temp = new NetsuiteCategoryLookupCnvCustInfo();
		temp.setCategoryInternalId(String.valueOf(rs.getLong("customer_type_id")));
		temp.setCategoryName(rs.getString("name"));
		temp.setCorrelationid(correlationId);
		return temp;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}
