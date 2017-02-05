package com.capgemini.cif.core.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class BatchItemMapper implements RowMapper<BatchItem> {

	@Override
	public BatchItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		BatchItem batchItem = new BatchItem();
		
		batchItem.setId(resultSet.getLong("Id"));
		batchItem.setCorrelationId(resultSet.getString("correlation_id"));
		batchItem.setItemXMLData(resultSet.getString("item_xml_data"));
		batchItem.setIsValid(resultSet.getInt("isValid")==1?Boolean.TRUE:Boolean.FALSE);
		batchItem.setErrorMessage(resultSet.getString("error_message"));
		batchItem.setEntityType(resultSet.getString("entity_type"));
		batchItem.setSentToTarget(resultSet.getInt("sent_to_target")==1?Boolean.TRUE:Boolean.FALSE);
		
		return batchItem;
	}

}
