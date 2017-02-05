package com.sho.renaissance.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.domain.NetsuiteSubsidiaryLookupCnvCustInfo;

public class NetSuiteLookupSubsidiaryCnvCustInfoMapper implements RowMapper<NetsuiteSubsidiaryLookupCnvCustInfo>
{

	private String correlationId;
	
	@Override
	public NetsuiteSubsidiaryLookupCnvCustInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		//correlationId = UUID.randomUUID().toString();
		
		NetsuiteSubsidiaryLookupCnvCustInfo temp = new NetsuiteSubsidiaryLookupCnvCustInfo();
		temp.setSubsidiaryInternalId(String.valueOf(rs.getLong("SUBSIDIARY_ID")));
		temp.setSubsidiaryName(rs.getString("name"));
		temp.setCorrelationid(correlationId);
		return temp;
	}


	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	

	
}
