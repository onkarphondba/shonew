package com.sho.renaissance.batch.partitioner;

import com.sho.renaissance.batch.constants.BatchConstants;

public class CnvCustInfoEnrichedPartitioner extends KeyBasedPartitioner {
	
	private String correlationId;
	
	@Override
	public String getPrimaryKeyQuery() {
		StringBuilder query = new StringBuilder();
		
		query.append(BatchConstants.CUSTCNVINFO_ENRICHED_SELECT_CLAUSE); 
		query.append(BatchConstants.CUSTCNVINFO_ENRICHED_FROM_CLAUSE.replace(":correlationId", correlationId));
		return query.toString();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
