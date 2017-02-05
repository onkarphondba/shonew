package com.sho.renaissance.batch.partitioner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sho.renaissance.batch.constants.BatchConstants;

/** This class generates the partitioning query required to get
 *         data from NetSuite using Partitioning logic in Spring batch.
 * @author parthaka
 *
 */
public class NetSuiteCnvCustInfoPartitioner extends KeyBasedPartitioner {

	private static Log logger = LogFactory.getLog(NetSuiteCnvCustInfoPartitioner.class);

	/* (non-Javadoc)
	 * @see com.sho.renaissance.batch.partitioner.KeyBasedPartitioner#getPrimaryKeyQuery()
	 */
	@Override
	public String getPrimaryKeyQuery() {
		if (logger.isDebugEnabled()) {
			logger.debug("----------- Get index for CustCnvInfo Feed-------------");
		}
		
		StringBuilder partiionQuery = new StringBuilder();
		partiionQuery.append(BatchConstants.CUSTCNVINFO_SELECT_CLAUSE);
		partiionQuery.append(BatchConstants.CUSTCNVINFO_FROM_CLAUSE);
		
		return partiionQuery.toString();
	}

}