package com.capgemini.cif.core.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.capgemini.cif.core.domain.CommonEntityAttribute;


public class CommonEntityAttributesMapper implements RowMapper<CommonEntityAttribute>
{

	//@Override
	public CommonEntityAttribute mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		CommonEntityAttribute itemEntityAttribute = new CommonEntityAttribute();
		itemEntityAttribute.setJustEnoughAttributeName(rs.getString("JE_ATTRIBUTE_NAME"));
		itemEntityAttribute.setAttributeSqlType(rs.getString("ATTRIBUTE_SQL_TYPE"));
		return itemEntityAttribute;
	}

}
