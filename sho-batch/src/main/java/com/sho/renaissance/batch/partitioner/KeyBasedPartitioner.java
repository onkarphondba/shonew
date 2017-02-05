package com.sho.renaissance.batch.partitioner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Partitioner used for getting keys which are then used to retrieve data.
 * 
 * @author gdhimate
 *
 */
public abstract  class KeyBasedPartitioner extends BasePartitioner {

	private static Log logger = LogFactory.getLog(KeyBasedPartitioner.class);

	/**
	 * Ignore's grid size if this value is greater than partition size
	 */
	private int skipGridSizeForRecordsLessThenCount = 0;

	/**
	 * create the partition based on key
	 */
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>();

		String partitionerQuery =this.getPrimaryKeyQuery();
		
		ResultSetExtractor<List<Object>> resultSetExtractor = new ResultSetExtractor<List<Object>>() {

			@Override
			public List<Object> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				List<Object> listOfIds = new ArrayList<Object>();
				while(rs.next()){

					listOfIds.add(rs.getObject(1));
				}
				return listOfIds;
			}
		};
		
		List<Object> keys = query(partitionerQuery, resultSetExtractor);

		int recordsToProcess = keys.size();
		logger.info("-------------------- Total records to process: " + recordsToProcess);

		int partitionSize = (int) (Math.ceil((float) recordsToProcess / (float) gridSize));

		if (partitionSize < skipGridSizeForRecordsLessThenCount) {
			partitionSize = skipGridSizeForRecordsLessThenCount;
		}

		logger.info("--------------------- Total records per Partition: " + partitionSize);

		int startKey = 0;
		int endKey = startKey + partitionSize - 1;
		int partitionId = 1;

		while (startKey < recordsToProcess) {
			if (endKey > recordsToProcess - 1) {
				endKey = recordsToProcess - 1;
			}
			ExecutionContext context = new ExecutionContext();

			logger.info("-------------------- Start index(" + partitionId + "): " + keys.get(startKey));
			logger.info("-------------------- End index(" + partitionId + "): " + keys.get(endKey));

			partitionMap.put("partition-" + partitionId, context);
			context.put("startKey", keys.get(startKey));
			context.put("endKey", keys.get(endKey));
			context.put("partitionId", partitionId);
			context.put("partitionSize", partitionSize);
			context.put("totalRecords", keys.size());

			startKey += partitionSize;
			endKey += partitionSize;
			partitionId++;
		}

		logger.info("Total no. of Partitions: " + partitionMap.size());
		return partitionMap;
	}

	public abstract String getPrimaryKeyQuery();

	public void setSkipGridSizeForRecordsLessThenCount(int skipGridSizeForRecordsLessThenCount) {
		this.skipGridSizeForRecordsLessThenCount = skipGridSizeForRecordsLessThenCount;
	}
}