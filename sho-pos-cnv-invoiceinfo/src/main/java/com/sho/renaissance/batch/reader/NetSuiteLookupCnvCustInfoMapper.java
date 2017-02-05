package com.sho.renaissance.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sho.renaissance.batch.domain.NetSuiteLookupCnvCustInfo;

/**
 * @author parthaka
 * This class is a Mapper for lookup data coming from  NetSuite.
 */
public class NetSuiteLookupCnvCustInfoMapper implements RowMapper<NetSuiteLookupCnvCustInfo>{
	
	private String sourceSystem;
	private String correlationId;

	@Override
	public NetSuiteLookupCnvCustInfo mapRow(ResultSet rs, int rowNumber) throws SQLException {

		NetSuiteLookupCnvCustInfo cnvCustInfo = new NetSuiteLookupCnvCustInfo();
		
		Integer custId = (int)rs.getDouble("CUSTOMER_ID");
		Integer categoryId = (int)rs.getDouble("CATEGORY ID");
		Integer subsidiaryId = (int)rs.getDouble("SUBSIDIARY ID");
		
		cnvCustInfo.setCustomerId(custId.toString());
		cnvCustInfo.setShcCustomerIdentifier(rs.getString("SHC_CUSTOMER_IDENTIFIER"));
		cnvCustInfo.setCustomerType(rs.getString("IS_PERSON"));
		cnvCustInfo.setFirstName(rs.getString("FIRSTNAME"));
		cnvCustInfo.setMiddleName(rs.getString("MIDDLENAME"));
		cnvCustInfo.setLastName(rs.getString("LASTNAME"));
		cnvCustInfo.setCompanyName(rs.getString("COMPANYNAME"));
		cnvCustInfo.setCategoryName(rs.getString("CATEGORY NAME"));
		cnvCustInfo.setCategoryId(categoryId.toString());
		cnvCustInfo.setComments(rs.getString("COMMENTS"));
		cnvCustInfo.setEmail(rs.getString("EMAIL"));
		cnvCustInfo.setPhone(rs.getString("PHONE"));
		cnvCustInfo.setSubsidaryName(rs.getString("SUBSIDIARY NAME"));
		cnvCustInfo.setSubsidaryId(subsidiaryId.toString());
		cnvCustInfo.setUrl(rs.getString("URL"));
		cnvCustInfo.setShipAddrLine1(rs.getString("SHIP_ADDRESS_LINE_1"));
		cnvCustInfo.setShipAddrLine2(rs.getString("SHIP_ADDRESS_LINE_2"));
		cnvCustInfo.setShipCity(rs.getString("SHIP_CITY"));
		cnvCustInfo.setShipState(rs.getString("SHIP_STATE"));
		cnvCustInfo.setShipZip(rs.getString("SHIP_ZIP"));
		cnvCustInfo.setIsDefaultShipAddress(rs.getString("IS_DEFAULT_SHIP_ADDRESS"));
		cnvCustInfo.setBillAddrLine1(rs.getString("BILL_ADDRESS_LINE_1"));
		cnvCustInfo.setBillAddrLine2(rs.getString("BILL_ADDRESS_LINE_2"));
		cnvCustInfo.setBillCity(rs.getString("BILL_CITY"));
		cnvCustInfo.setBillState(rs.getString("BILL_STATE"));
		cnvCustInfo.setBillZip(rs.getString("BILL_ZIP"));
		cnvCustInfo.setIsDefaultBillAddress(rs.getString("IS_DEFAULT_BILL_ADDRESS"));
		cnvCustInfo.setSource(sourceSystem);
		cnvCustInfo.setCorrelationid(correlationId);
	
		return cnvCustInfo;	
	}



	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}