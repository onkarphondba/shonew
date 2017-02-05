package com.sho.renaissance.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sho.renaissance.batch.domain.CnvCustInfoEnriched;

public class CnvCustInfoEnrichedMapper implements RowMapper<CnvCustInfoEnriched> {

	@Override
	public CnvCustInfoEnriched mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CnvCustInfoEnriched temp = new CnvCustInfoEnriched();
		temp.setShoCustomerId(rs.getString("sho_customer_id"));
		temp.setShcCustomerIndentifier(rs.getString("shc_customer_identifier"));
		temp.setCustomerType(rs.getString("customer_type"));
		temp.setCustomerFirstName(rs.getString("customer_first_name"));
		temp.setCustomerMiddleName(rs.getString("customer_middle_name"));
		temp.setCustomerLastName(rs.getString("customer_last_name"));
		temp.setCompanyName(rs.getString("company_name"));
		temp.setCategoryName(rs.getString("category_name"));
		temp.setCategoryInternalId(rs.getString("category_internal_id"));
		temp.setComments(rs.getString("comments"));
		temp.setEmail(rs.getString("email"));
		temp.setPhone(rs.getString("phone"));
		temp.setSubsidiaryName(rs.getString("subsidiary_name"));
		temp.setSubsidiaryInternalId(rs.getString("subsidiary_internal_id"));
		temp.setWebAddress(rs.getString("web_address"));
		temp.setShippingAddrLine1(rs.getString("shipping_addr_line1"));
		temp.setShippingAddrLine2(rs.getString("shipping_addr_line2"));
		temp.setShippingCity(rs.getString("shipping_city"));
		temp.setShippingState(rs.getString("shipping_state"));
		temp.setShippingZip(rs.getString("shipping_zip"));
		temp.setDefaultShippingFlag(rs.getString("default_shipping_flag"));
		temp.setBillingAddrLine1(rs.getString("billing_addr_line1"));
		temp.setBillingAddrLine2(rs.getString("billing_addr_line2"));
		temp.setBillingCity(rs.getString("billing_city"));
		temp.setBillingState(rs.getString("billing_state"));
		temp.setBillingZip(rs.getString("billing_zip"));
		temp.setDefaultBillingFlag(rs.getString("default_billing_flag"));
		temp.setOperationType(rs.getString("operation_type"));
		temp.setUpdatedInNetsuite(rs.getString("Y"));
		temp.setSource(rs.getString("source"));
		temp.setCorrelationid(rs.getString("correlationid"));
		
		return temp;
	}

}
