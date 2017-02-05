package com.capgemini.cif.core.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProcessStatusMapper  implements RowMapper<ProcessStatus>{

	@Override
	public ProcessStatus mapRow(ResultSet res, int arg1) throws SQLException {
		ProcessStatus processStatus = new ProcessStatus();
		processStatus.setName(res.getString("name"));
		processStatus.setStatus(res.getString("status"));
		return processStatus;
	}

}
