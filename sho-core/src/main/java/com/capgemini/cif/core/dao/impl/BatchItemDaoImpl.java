package com.capgemini.cif.core.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.capgemini.cif.core.dao.IBatchItemDao;
import com.capgemini.cif.core.domain.BatchItem;
import com.capgemini.cif.core.domain.BatchItemMapper;
import com.capgemini.cif.core.domain.CommonEntityAttribute;
import com.capgemini.cif.core.domain.CommonEntityAttributesMapper;
import com.capgemini.cif.core.domain.ProcessStatus;
import com.capgemini.cif.core.domain.ProcessStatusMapper;
import com.capgemini.cif.core.validation.ConcreteComponentProduct;

/**
 * @author gdhimate
 *
 */

public class BatchItemDaoImpl extends GenericDao implements IBatchItemDao {

	private static Log logger = LogFactory.getLog(BatchItemDaoImpl.class);

	Connection connection = null;

	@Override
	public Integer getBatchItemCount(String correlationId, String entityType,Boolean isValid) {
		if(correlationId==null && entityType== null){
			return null;
		}
		SqlParameterSource namedParameters;
		StringBuilder generatedQuery;
		String itemHierarchy = "Sup_ItemHierarchy";
		if(entityType == itemHierarchy)
			entityType = entityType+"%";
		if(entityType.equalsIgnoreCase("sup_inventory") )
		{
			generatedQuery = new StringBuilder("SELECT COUNT(*) FROM inv_batch_item WHERE correlation_id=:correlation_id AND entity_type like :entity_type ");
		}
		else
		{
		 generatedQuery = new StringBuilder("SELECT COUNT(*) FROM batch_item WHERE correlation_id=:correlation_id AND entity_type like :entity_type ");
		}
		if(isValid!=null){
			namedParameters = new MapSqlParameterSource("correlation_id", correlationId).
					addValue("entity_type", entityType).addValue("isValid", isValid.equals(Boolean.TRUE)?1:0);
			generatedQuery.append(" AND isValid=:isValid ");

		}
		else
		{
			namedParameters = new MapSqlParameterSource("correlation_id", correlationId).
					addValue("entity_type", entityType);

		}
		return getNamedParameterJdbcTemplate().queryForObject(generatedQuery.toString(), namedParameters, Integer.class);

	}
	@Override
	public List<BatchItem> getBatchItemByStatus(Boolean status)
	{
		logger.debug("Entering method getBatchItemByStatus : with Status" + status);
		SqlParameterSource namedParameters = new MapSqlParameterSource("status", status==Boolean.TRUE?1:0);  
		List<BatchItem> batchItemList = getNamedParameterJdbcTemplate().query(GET_BATCH_ITEM_BY_STATUS, namedParameters, new BatchItemMapper());
		return batchItemList;
	}


	@Override
	public List<Object> getSelectedItems(String sql,RowMapper rowMapper)
	{
		if(sql==null){
			return new ArrayList<Object>();
		}
			logger.debug("Entering method getListOfObject : with Query" + sql);
		List<Object> listOfObjects = getNamedParameterJdbcTemplate().query(sql,rowMapper);
		return listOfObjects;
	}

	@Override
	public Integer getTotalCount(String sql,Map<String,String> paramMap)
	{
		if(sql==null){
			return null;
		}
		
		logger.debug("Entering method getListOfObject : with Query" + sql);
		Integer totalCount = getNamedParameterJdbcTemplate().queryForObject(sql,paramMap,Integer.class);
		return totalCount;
	}

	@Override
	public void createProcessStatus (String processname , String status){
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", processname).addValue("status",status);
		getNamedParameterJdbcTemplate().update("insert into PROCESS_STATUS (NAME,STATUS) values (:name,:status) ",namedParameters);
	}

	@Override
	public List<ProcessStatus> getProcessStatus (List<String> processes){
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", processes).addValue("count", processes.size()).addValue("status", STATUS_COMPLETED);
		return getNamedParameterJdbcTemplate().query(GET_PREDECESSORS_COMPLETE_FLAG, namedParameters,new ProcessStatusMapper() );
	}

	@Override
	public List<Map<String, Object>> getClusterFeedFailedRecords(){
		SqlParameterSource namedParameters = new MapSqlParameterSource("lookupFailure", true).addValue("inputDataError", true);
		return getNamedParameterJdbcTemplate().queryForList(CLUSTERFEED_GET_ENRICHED_TABLE_FAILED_RECORDS,namedParameters);
	}

	@Override
	public Integer getClusterFeedAllRecords(){
		SqlParameterSource namedParameters = new MapSqlParameterSource("", null);
		return getNamedParameterJdbcTemplate().queryForObject(CLUSTERFEED_GET_ENRICHED_TABLE_ALL_RECORDS_COUNT,namedParameters,Integer.class);
	}
	
	@Override
	public List<ConcreteComponentProduct> getTotalRecordsFromEnrichedTable(ResultSetExtractor<List<ConcreteComponentProduct>> resultSetExtractor, String tableName){
		String query = PRICEFEED_GET_ALL_RECORDS_ENRICHED.replace("&table", tableName);
		return (List<ConcreteComponentProduct>) getNamedParameterJdbcTemplate().query(query,resultSetExtractor);
	}


	@Override
	
	public List<ConcreteComponentProduct> getFailedRecordsFromEnrichedTable(ResultSetExtractor<List<ConcreteComponentProduct>> resultSetExtractor, String tableName){
		String query = PRICEFEED_GET_FAILED_RECORDS_ENRICHED.replace("&table", tableName);
		return  (List<ConcreteComponentProduct>) getNamedParameterJdbcTemplate().query(query,resultSetExtractor);
	}

	@Override
	public Integer getClusterfeedId(String clusterFeedName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("clusterFeedName", clusterFeedName);
		return  getNamedParameterJdbcTemplate().queryForObject(CLUSTERFEED_GET_CLUSTERID_FOR_ONLINEPRICE, namedParameters, Integer.class);
	}

	@Override
	public Map<String,Object> callClusterFeedStoredProcedure() throws SQLException {
		Integer cluster_id = getClusterfeedId(CLUSTERFEED_DEFAULT_CLUSTERNAME);
		connection = getJdbcTemplate().getDataSource().getConnection();

		if (cluster_id == 0) {
			throw new RuntimeException("No cluster_id for cluster name Online Price ");
		}

		CallableStatement callableSt = connection.prepareCall(CLUSTERFEED_PROCEDURECALL);
		callableSt.setInt(1, cluster_id);
		callableSt.registerOutParameter(2, Types.INTEGER);
		callableSt.registerOutParameter(3, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(2));
		result.put(ERROR_MESSAGE, callableSt.getString(3));
		callableSt.close();
		connection.close();

		return result;
	}

	@Override
	public Map<String, Object> callPricefeedStoredProcedure() throws SQLException {

		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(PRICEFEED_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
	@Override
	public Map<String, Object> callCnvHistoricalInventoryStoredProcedure() throws SQLException {

		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(CNVHISTINVENT_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}

	@Override
	public Map<String, Object> callItemDescriptiveAttributesStoredProcedure() throws SQLException {

		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(ITEMDESCATTR_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
	



	@Override
	public String getNetsuiteActionType(String actionName){
		Assert.notNull(actionName);
		SqlParameterSource namedParameters = new MapSqlParameterSource("action", actionName);
		return  getNamedParameterJdbcTemplate().queryForObject(GET_NETSUITE_ACTION_TYPE_ID, namedParameters, String.class);
	}

	@Override
	public String getNetsuiteFileType(String fileTypeName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("filetype", fileTypeName);
		return  getNamedParameterJdbcTemplate().queryForObject(GET_NETSUITE_FILETYPE_TYPE_ID, namedParameters, String.class);
	}

	@Override
	public String getNetsuiteBatchStatus(String statusName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("status_id", statusName);
		return  getNamedParameterJdbcTemplate().queryForObject(GET_NETSUITE_BATCH_STATUS, namedParameters, String.class);
	}

	@Override
	public void truncateTable(String sql) {
		getJdbcTemplate().execute(sql);
	}
	
	@Override
	
	public void deleteTable(String conditionalDeleteTable) {
		getJdbcTemplate().execute(conditionalDeleteTable);
		
	}
	
	@Override
	public Map<String, Object> callSiteCategoriesStoredProcedure() throws SQLException {
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(SITECATEGORIES_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}

	@Override
	public int deleteTableDataOlderThanNDays(String tableName, int numberOfDays) {
		String query = DELETE_DATA_OLDER_THAN_NDAYS.replace("&tableName", tableName);
		return getJdbcTemplate().update(query, numberOfDays);
	}



	/*public Map<String, Object> callRelatedItemsStoredProcedure() throws SQLException{
		connection = getJdbcTemplate().getDataSource().getConnection();

		
		CallableStatement callableSt = connection.prepareCall(RELATEDITEMS_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}*/
	
	
	@Override
	public Long getNumberOfRecordsProcessed(String tableName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("updatedInNetsuite", true);
		String sql = GET_UPDATED_RECORDS.replace("$tableName", tableName);
		return getNamedParameterJdbcTemplate().queryForObject(sql ,namedParameters, Long.class);
	}
	@Override
	public Map<String,Object> callStoredInvFeedProcedure() throws SQLException {
		
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(STOREINVFEED_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ErrorCode", callableSt.getInt(1));
		result.put("ErrorMessage", callableSt.getString(2));
		callableSt.close();
		connection.close();

		return result;
	}

	@Override
	public ResultSet getStoreInvEnrichedOnhandQuantityTotalRecords() throws SQLException {
		ResultSet rs = null;
		Connection con = getJdbcTemplate().getDataSource().getConnection();
		Statement st = con.createStatement();
		rs = st.executeQuery("SELECT * FROM temp_dataload_ns_onhandquantity_enriched");
		st.close();
		return rs;
	}

	@Override
	public ResultSet getStoreInvEnrichedOnhandQuantityFailedRecords() throws SQLException {
		ResultSet rs = null;
		Connection con = getJdbcTemplate().getDataSource().getConnection();
		Statement st = con.createStatement();
		rs = st.executeQuery(
				"SELECT * FROM temp_dataload_ns_onhandquantity_enriched where update_flag=0 and present_in_netsuite=0");
		st.close();
		return rs;
	}
	
	@Override
	public ResultSet getNsNonExistantRecors() throws SQLException
	{
		ResultSet rs = null;
		Connection con = getJdbcTemplate().getDataSource().getConnection();
		Statement st = con.createStatement();
		rs = st.executeQuery(
				"SELECT * FROM temp_dataload_ns_onhandquantity_enriched where update_flag=0 and present_in_netsuite=0 and operation_type is NULL");
		st.close();
		return rs;

	}
	
	@Override
	public Map<String, Object> callSpinAssetsStoredProcedure()
			throws SQLException {
		connection = getJdbcTemplate().getDataSource().getConnection();


		CallableStatement callableSt = connection.prepareCall(SPINASSETS_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	
	}

	
	public Map<String, Object> callSpinImagesStoredProcedure()
			throws SQLException {
		connection = getJdbcTemplate().getDataSource().getConnection();
		CallableStatement callableSt = connection.prepareCall(SPINIMAGES_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);    
		callableSt.registerOutParameter(2, Types.VARCHAR);
		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
		@Override
	public Map<String, Object> callSpinItemStoredProcedure()
			throws SQLException {
		connection = getJdbcTemplate().getDataSource().getConnection();


		CallableStatement callableSt = connection.prepareCall(SPINITEM_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
	@Override
	public List<BatchItem> getBatchItems(String correlationId, String entityType,Boolean isValid) {
		if(correlationId==null && entityType== null){
			return null;
		}
		SqlParameterSource namedParameters;
		StringBuilder generatedQuery = new StringBuilder("SELECT * FROM batch_item WHERE correlation_id=:correlation_id AND entity_type=:entity_type ");

		if(isValid!=null){
			namedParameters = new MapSqlParameterSource("correlation_id", correlationId).
					addValue("entity_type", entityType).addValue("isValid", isValid.equals(Boolean.TRUE)?1:0);
			generatedQuery.append(" AND isValid=:isValid ");

		}
		else
		{
			namedParameters = new MapSqlParameterSource("correlation_id", correlationId).
					addValue("entity_type", entityType);

		}
		return getNamedParameterJdbcTemplate().query(generatedQuery.toString(), namedParameters,new BatchItemMapper());

	}
	@Cacheable(value = { "AttributesByType" })
	@Override
	public List<CommonEntityAttribute> getAllAttributesByType(String attributeType) {
			logger.debug("Entering method getAllAttributesByType with attributeType");

		List<CommonEntityAttribute> commonEntityAttributeList = null;

		// SqlParameterSource namedParametre = new MapSqlParameterSource();
		SqlParameterSource namedParameters = new MapSqlParameterSource("attributeType", attributeType);

		if (null == attributeType) {
			commonEntityAttributeList = getNamedParameterJdbcTemplate().query(GET_ALL_ATTRIBUTES,
					new CommonEntityAttributesMapper());

			return commonEntityAttributeList;
		} else {
			commonEntityAttributeList = getNamedParameterJdbcTemplate().query(GET_ALL_ATTRIBUTES_BY_TYPE, namedParameters,
					new CommonEntityAttributesMapper());
			return commonEntityAttributeList;
		}

	}
public Map<String, Object> callPartsAccessoriesStoredProcedure() throws SQLException{
		connection = getJdbcTemplate().getDataSource().getConnection();

		
		CallableStatement callableSt = connection.prepareCall(PARTS_AND_ACCESSORIES_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
	
	public Map<String, Object> callProtectionAgreementsStoredProcedure() throws SQLException{
		connection = getJdbcTemplate().getDataSource().getConnection();

		
		CallableStatement callableSt = connection.prepareCall(PROTECTION_AGREEMENTS_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getPoAndTransfersListValues(){
		SqlParameterSource namedParameters = new MapSqlParameterSource("temp", "");
		return getNamedParameterJdbcTemplate().queryForList(POANDTRANSFERS_GETLISTVALUES,namedParameters);
	}
	@Override
	public Date getRunTimeForEntity(String entityName, boolean lastSucessfull) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("entityName", entityName)
												.addValue("status", lastSucessfull);
		return  getNamedParameterJdbcTemplate().queryForObject(GET_LAST_SUCCESSFUL_RUN_TIME, namedParameters, Date.class);
	}
	@Override
	public long insertProcessTime(String entityName, Date date) {
			logger.debug("Entering method insertProcessTime(entityName ,date)");
		SqlParameterSource namedParameters = new MapSqlParameterSource("entityName",
				entityName).addValue("date", date);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(CREATE_PROCESS_ENTRY, namedParameters, keyHolder, new String[] { "Id" });
			logger.debug("Exitingmethod insertProcessTime(entityName ,entityName)");

		return keyHolder.getKey().longValue();
	}
	@Override
	public void createBatchItem(BatchItem batchItem) {
			logger.debug("Entering method createBatchItem(BatchItem batchItem)");
		SqlParameterSource namedParameters = new MapSqlParameterSource("correlation_id",
				batchItem.getCorrelationId()).addValue("item_xml_data", batchItem.getItemXMLData())
				.addValue("isValid", batchItem.getIsValid())
				.addValue("error_message", batchItem.getErrorMessage())
				.addValue("entity_type", batchItem.getEntityType());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(CREATE_BATCH_ITEM, namedParameters, keyHolder, new String[] { "Id" });
			logger.debug("Exitingmethod insertProcessTime(entityName ,entityName)");
	}
	@Override
	public void deleteOlderRecords(String entityType) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("entity_name",
				entityType);
		String maxTime = getNamedParameterJdbcTemplate().queryForObject(MAX_TIME, namedParameters, String.class);
		getJdbcTemplate().update(DELETE_PREVIOUS_DATA,entityType, maxTime);
	}
	@Override
	public void updateCurrentStatus(String entityType) {
			logger.debug("Entering method updateCurrentStatus(String entityType)");
		SqlParameterSource namedParameters = new MapSqlParameterSource("entity_name",
				entityType);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(UPDATE_PROCESS_STATUS, namedParameters, keyHolder, new String[] { "Id" });
			logger.debug("Exitingmethod updateCurrentStatus(String entityType)");
		
	}

	
	@Override
	public Map<String, Object> callCnvSalesHistoryStoredProcedure() throws SQLException {

		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(CNVSALESHISTORY_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	

	
	@Override
	public Map<String, Object> callCnvInventoryOnHandStoredProcedure() throws SQLException {
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(INVENTORY_ONHAND_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getLatestInCompleteProcessId() {
		Map<String, Object> result;
		SqlParameterSource namedParameters = new MapSqlParameterSource("correlation_id", "");
		StringBuilder generatedQuery = new StringBuilder(GET_LATEST_COMPLETE_PROCESS);
		try {
			result = getNamedParameterJdbcTemplate().queryForList(generatedQuery.toString(),namedParameters).get(0);
		} catch (Exception ex) {
			return null;
		}

		if (result.isEmpty()) {
			return null;
		}
		return getNamedParameterJdbcTemplate().queryForList(generatedQuery.toString(), namedParameters).get(0);
	}

	@Override
	public void createCnvLocProcessStatus(String name, String status, String entity) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", name).addValue("status", status).addValue("entity", entity);
		getNamedParameterJdbcTemplate().update(
				SET_PROCESS_STATUS_LOC, namedParameters);
	}

	@Override
	public void createCnvRimProcessStatus(String name, String status, String entity) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", name).addValue("status", status).addValue("entity", entity);;
		getNamedParameterJdbcTemplate().update(
				SET_PROCESS_STATUS_RIM, namedParameters);
	}

	@Override
	public Long getCompleteProcessStatus(String entityName) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("entity", entityName).addValue("status",
				STATUS_COMPLETED);

		StringBuilder generatedQuery = new StringBuilder(
				GET_LATEST_COMPLETE_PROCESS_ID);

		return getNamedParameterJdbcTemplate().queryForObject(generatedQuery.toString(), namedParameters, Long.class);
	}

	@Override
	public void updateProcessStatus(Long processId, String entityName, String status) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("entity", entityName)
				.addValue("status", status).addValue("processId", processId);

		StringBuilder generatedQuery = new StringBuilder(
				UPDATE_PROCESS_STATUS_ON_ID);

		getNamedParameterJdbcTemplate().update(generatedQuery.toString(), namedParameters);

	}
	
	@Override
	public void updateCnvProcessStatus(Long processId ,String name, String status, String entity){
		StringBuilder generatedQuery = null;

		SqlParameterSource namedParameters = new MapSqlParameterSource("dataload", name)
				.addValue("status", status).addValue("processId", processId).addValue("entity", entity);
		
		if(name.contains(LOC_)){
			generatedQuery = new StringBuilder(UPDATE_LOC_STATUS);
		}else if (name.contains(RIM_)){
			generatedQuery = new StringBuilder(UPDATE_RIM_STATUS);
		}else if(name.equals(entity)){
			generatedQuery = new StringBuilder(UPDATE_PROCESS_ENITY_STATUS);
		}
		if(null != generatedQuery){
			getNamedParameterJdbcTemplate().update(generatedQuery.toString(),namedParameters);
		}
	}

	@Override
	public Map<String, Object> callCnvHistoricalMetricsStoredProcedure() throws SQLException
	{
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(CNVHISTORICALMETRICS_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	
	@Override
	public Map<String, Object> callCNVReceiptsStoredProcedure() throws SQLException
	{
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(CNVRECEIPTS_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(ERROR_CODE, callableSt.getInt(1));
		result.put(ERROR_MESSAGE, callableSt.getString(2));
		callableSt.close();
		connection.close();
		return result;
	}
	@Override
	public Long getNumberOfUnProcessedRecords(String tableName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("updatedInNetsuite", false);
		String sql = GET_UPDATED_RECORDS.replace("$tableName", tableName);
		return getNamedParameterJdbcTemplate().queryForObject(sql ,namedParameters, Long.class);
	}
	
	@Override
	public Long getCNVNumberOfUnProcessedRecords(String tableName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("sentToJE", false);
		String sql = GET_UPDATED_RECORDS.replace("$tableName", tableName);
		return getNamedParameterJdbcTemplate().queryForObject(sql ,namedParameters, Long.class);
	}
	
	@Override
	public Long getCNVNumberOfRecordsProcessed(String tableName){
		SqlParameterSource namedParameters = new MapSqlParameterSource("sentToJE", true);
		String sql = GET_UPDATED_RECORDS.replace("$tableName", tableName);
		return getNamedParameterJdbcTemplate().queryForObject(sql ,namedParameters, Long.class);
	}
	

	@Override
	public Map<String,Object> callcnvCustInfoProcedure() throws SQLException {
		
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(CNVCUSTINFO_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ErrorCode", callableSt.getInt(1));
		result.put("ErrorMessage", callableSt.getString(2));
		callableSt.close();
		connection.close();

		return result;
	}


	@Override
	public Integer getCnvCustInfoFailedRecords(String correlationId) throws SQLException {
		if(correlationId==null ){
			return null;
		}
		String	fail = null;
		ResultSet rs = null;
		Connection con = getJdbcTemplate().getDataSource().getConnection();
		Statement st = con.createStatement();
		rs = st.executeQuery("SELECT count(*) count FROM pos_cnvcustinfo_dataload WHERE isvalid = 'No' ");
		//rs = st.executeQuery("SELECT count(*) count FROM pos_cnvcustinfo_dataload temp where isvalid='No'");
		if(rs.next()){
			fail = rs.getString("count");
			}
		return Integer.valueOf(fail);
	}

	
	@Override
	public Integer getCnvCustInfoTotalRecordCount(String correlationId) throws SQLException {
		if(correlationId==null ){
			return null;
		}
		String	pass = null;
		ResultSet rs = null;
		Connection con = getJdbcTemplate().getDataSource().getConnection();
		Statement st = con.createStatement();
		rs = st.executeQuery("SELECT count(*) count FROM pos_cnvcustinfo_dataload");
		rs.next();
		pass = rs.getString("count");
			
		
		return Integer.valueOf(pass);
		
		
	}
	

	@Override
	public Map<String,Object> callCnvInvoiceInfoProcedure() throws SQLException {
		
		connection = getJdbcTemplate().getDataSource().getConnection();

		CallableStatement callableSt = connection.prepareCall(CNVINVOICEINFO_PROCEDURECALL);
		callableSt.registerOutParameter(1, Types.INTEGER);
		callableSt.registerOutParameter(2, Types.VARCHAR);

		// Call Stored Procedure
		callableSt.executeUpdate();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ErrorCode", callableSt.getInt(1));
		result.put("ErrorMessage", callableSt.getString(2));
		callableSt.close();
		connection.close();

		return result;
	}
}
