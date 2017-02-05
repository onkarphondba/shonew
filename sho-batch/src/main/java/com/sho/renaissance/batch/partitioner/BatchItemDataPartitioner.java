package com.sho.renaissance.batch.partitioner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sho.renaissance.batch.constants.BatchConstants;

/**
 * Partitioner for file creation job
 * 
 * @author gdhimate
 *
 */
public class BatchItemDataPartitioner extends KeyBasedPartitioner {

	private static Log logger = LogFactory.getLog(BatchItemDataPartitioner.class);

	private String selectclause = BatchConstants.FILE_SELECT_CLAUSE;
	private String whereClause = BatchConstants.FILE_WHERE_CLAUSE;
	private String orderByClause = BatchConstants.FILE_ORDER_BY_CLAUSE;

	private String correlationId;
	private String entityType;

	@Override
	public String getPrimaryKeyQuery() {
		StringBuilder partionQuery = new StringBuilder();

		partionQuery.append(selectclause);
		partionQuery.append(whereClause.replace(":correlationId", correlationId).replace(":entityType", entityType));
		partionQuery.append(orderByClause);

		logger.debug("File creation partitioner query is : " + partionQuery.toString());
		return partionQuery.toString();
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

}
