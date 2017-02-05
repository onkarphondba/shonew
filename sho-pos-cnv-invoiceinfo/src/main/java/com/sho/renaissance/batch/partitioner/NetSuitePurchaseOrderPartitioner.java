package com.sho.renaissance.batch.partitioner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sho.renaissance.batch.constants.BatchConstants;

/**This class generated the partitioning query required to get
 *   data from JE using Partitioning logic in Spring batch.
 * @author snkesarw
 *
 */
public class NetSuitePurchaseOrderPartitioner extends KeyBasedPartitioner {
	private static Log log = LogFactory.getLog(NetSuitePurchaseOrderPartitioner.class);

	@Override
	public String getPrimaryKeyQuery() {
		
		log.debug("------------------- Get index for Items -------------------------");
		
		StringBuilder partiionQuery = new StringBuilder();
		partiionQuery.append(BatchConstants.PO_AND_TRANSFERS_SELECT_QUERY);
		partiionQuery.append(BatchConstants.PO_AND_TRANSFERS_FROM_QUERY);
		partiionQuery.append(BatchConstants.PO_AND_TRANSFERS_ORDERBY_QUERY);
		return partiionQuery.toString();
	}
}