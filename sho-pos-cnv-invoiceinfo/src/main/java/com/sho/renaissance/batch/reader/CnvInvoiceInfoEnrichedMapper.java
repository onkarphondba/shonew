package com.sho.renaissance.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sho.renaissance.batch.domain.CnvInvoiceInfoEnriched;

public class CnvInvoiceInfoEnrichedMapper implements RowMapper<CnvInvoiceInfoEnriched> {

	@Override
	public CnvInvoiceInfoEnriched mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CnvInvoiceInfoEnriched invoiceinfo = new CnvInvoiceInfoEnriched();
		
		invoiceinfo.setShoCustomerId(rs.getString("sho_customer_id"));
		invoiceinfo.setShccustomeridentifier(rs.getString("shc_customer_identifier"));
		invoiceinfo.setShoInvoiceNumber(rs.getString("sho_invoice_number"));
		invoiceinfo.setShcInvoiceNumber(rs.getString("shc_invoice_number"));
		invoiceinfo.setShcSalesCheckNumber(rs.getString("shc_sales_check_number"));
		invoiceinfo.setShctransactiondate(rs.getDate("shc_transaction_date"));
		invoiceinfo.setShctransactiontime(rs.getTimestamp("shc_transaction_time"));
		invoiceinfo.setSywmemberid(rs.getString("syw_member_id"));
		invoiceinfo.setSywpointsredeemed(rs.getLong("syw_points_redeemed"));
		invoiceinfo.setSywpointsearned(rs.getLong("syw_points_earned"));
		invoiceinfo.setExternalid(rs.getString("external_id"));
		invoiceinfo.setStorenumber(rs.getString("store_number"));
		invoiceinfo.setLocationinternalid(rs.getString("location_internal_id"));
		invoiceinfo.setDepartment(rs.getString("department"));
		invoiceinfo.setDepartmentinternalid(rs.getString("department_internal_id"));
		invoiceinfo.setTotalamount(rs.getDouble("total_amount"));
		invoiceinfo.setTotaltax(rs.getDouble("total_tax"));
		invoiceinfo.setDiscountitem(rs.getString("discount_item"));
		invoiceinfo.setAssociatediscount(rs.getDouble("associate_discount"));
		invoiceinfo.setTransactiondiscount(rs.getDouble("transaction_discount"));
		invoiceinfo.setMemo(rs.getString("memo"));
		invoiceinfo.setShippingAddrLine1(rs.getString("shipping_address_line1"));
		invoiceinfo.setShippingAddrLine2(rs.getString("shipping_address_line2"));
		invoiceinfo.setShippingCity(rs.getString("shipping_city"));
		invoiceinfo.setShippingState(rs.getString("shipping_state"));
		invoiceinfo.setShippingZip(rs.getString("shipping_zip"));
		invoiceinfo.setBillingaddresstype(rs.getString("billing_address_type"));
		invoiceinfo.setBillingAddrLine1(rs.getString("billing_addr_line1"));
		invoiceinfo.setBillingAddrLine2(rs.getString("billing_addr_line2"));
		invoiceinfo.setBillingCity(rs.getString("billing_city"));
		invoiceinfo.setBillingState(rs.getString("billing_state"));
		invoiceinfo.setBillingZip(rs.getString("billing_zip"));
		invoiceinfo.setShcitemnumber(rs.getString("shc_item_number"));
		invoiceinfo.setIteminternalid(rs.getString("item_internal_id"));
		invoiceinfo.setPlus4(rs.getString("plus4"));
		invoiceinfo.setSequenceid(rs.getString("sequence_id"));
		invoiceinfo.setQuantity(rs.getLong("quantity"));
		invoiceinfo.setRegularprice(rs.getDouble("regular_price"));
		invoiceinfo.setPluprice(rs.getDouble("plu_price"));
		invoiceinfo.setSellingprice(rs.getDouble("selling_price"));
		invoiceinfo.setTaxcode(rs.getString("tax_code"));
		invoiceinfo.setTaxrate(rs.getDouble("tax_rate"));
		invoiceinfo.setTaxamount(rs.getDouble("tax_amount"));
		invoiceinfo.setReturnallowed(rs.getString("return_allowed"));
		invoiceinfo.setUpdatedInNetsuite(rs.getString("updated_in_netsuite"));
		invoiceinfo.setSource(rs.getString("source"));
		invoiceinfo.setCorrelationid(rs.getString("correlationid"));
		invoiceinfo.setId(rs.getInt("id"));
		
		return invoiceinfo;
	}

}