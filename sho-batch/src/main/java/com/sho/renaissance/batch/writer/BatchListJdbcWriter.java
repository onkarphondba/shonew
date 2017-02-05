package com.sho.renaissance.batch.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import com.capgemini.cif.core.domain.BatchItem;

/**
 * Custom jdbc writer to write the list of items
 *  
 * @author gdhimate
 *
 */
public class BatchListJdbcWriter extends JdbcBatchItemWriter {

	private String tableName;

	/**
	 * get List<List<BatchItem>> and convert to List<BatchItem>
	 */
	@Override
	public void write(List items) throws Exception {

		List<BatchItem> batchItemList = new ArrayList<BatchItem>();
		for (Object ObjbatchItems : items) {
			List<BatchItem> batchItems = (List<BatchItem>) ObjbatchItems;
			batchItemList.addAll(batchItems);
		}
		super.write(batchItemList);
	}

	/**
	 * Set sql with variable table name
	 */
	@Override
	public void setSql(String sql) {
		sql = sql.replace("$table", tableName);
		logger.debug("CustomJdbcWriter sql setter:" + sql);
		super.setSql(sql);

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
