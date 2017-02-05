package com.sho.renaissance.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * This class is extension of AbstractPagingItemReader to change the
 *         default query which is getting genrated
 * @author gdhimate
 *
 * @param <T>
 */
@SuppressWarnings("deprecation")
public class BatchJdbcPagingItemReader<T> extends AbstractPagingItemReader<T> implements InitializingBean {

	public static final int FETCH_VALUE_NOT_SET = -1;

	private DataSource dataSource;

	private PagingQueryProvider queryProvider;

	private Map<String, Object> parameterValues;

	private SimpleJdbcTemplate simpleJdbcTemplate;

	private RowMapper<?> rowMapper;

	private String firstPageSql;

	private String remainingPagesSql;

	private Object startAfterValue;

	private int fetchSize = FETCH_VALUE_NOT_SET;

	public BatchJdbcPagingItemReader() {
		setName(ClassUtils.getShortName(JdbcPagingItemReader.class));
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public void setQueryProvider(PagingQueryProvider queryProvider) {
		this.queryProvider = queryProvider;
	}

	public void setRowMapper(RowMapper<?> rowMapper) {
		this.rowMapper = rowMapper;
	}

	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();

		// set datasource & jdbc template
		Assert.notNull(dataSource);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		// Set fetch value
		if (fetchSize != FETCH_VALUE_NOT_SET) {
			jdbcTemplate.setFetchSize(fetchSize);
		}

		// Set page size
		jdbcTemplate.setMaxRows(getPageSize());

		this.simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate);
		Assert.notNull(queryProvider);
		queryProvider.init(dataSource);

		// Queries
		this.firstPageSql = queryProvider.generateFirstPageQuery(getPageSize());
		this.remainingPagesSql = queryProvider.generateRemainingPagesQuery(getPageSize());

	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		if (getPage() > 0) {

			String jumpToItemSql;
			jumpToItemSql = queryProvider.generateJumpToItemQuery(itemIndex, getPageSize());

				logger.debug("SQL used for jumping: [" + jumpToItemSql + "]");

			startAfterValue = simpleJdbcTemplate.getJdbcOperations().queryForObject(jumpToItemSql,
					new RowMapper<Object>() {
						public Object mapRow(ResultSet rs, int i) throws SQLException {
							return rs.getObject(1);
						}
					});

		}

	}

	@Override
	protected void doReadPage() {
		PagingRowCallbackHandler rowCallback = new PagingRowCallbackHandler();
		// Currently it will be always be 0
		if (getPage() == 0) {
				logger.debug("SQL used for reading first page: [" + firstPageSql + "]");
			if (parameterValues != null && parameterValues.size() > 0) {
				if (this.queryProvider.isUsingNamedParameters()) {
					simpleJdbcTemplate.getNamedParameterJdbcOperations().query(firstPageSql,
							getParameterMap(parameterValues, null), rowCallback);
				} else {
					simpleJdbcTemplate.getJdbcOperations().query(firstPageSql,
							getParameterList(parameterValues, null).toArray(), rowCallback);
				}
			} else {
				simpleJdbcTemplate.getJdbcOperations().query(firstPageSql, rowCallback);
			}

		}
		// Will be required if order by clause is to be used
		else {
				logger.debug("SQL used for reading remaining pages: [" + remainingPagesSql + "]");
			if (this.queryProvider.isUsingNamedParameters()) {
				simpleJdbcTemplate.getNamedParameterJdbcOperations().query(remainingPagesSql,
						getParameterMap(parameterValues, startAfterValue), rowCallback);
			} else {
				simpleJdbcTemplate.getJdbcOperations().query(remainingPagesSql,
						getParameterList(parameterValues, startAfterValue).toArray(), rowCallback);
			}
		}

		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(rowCallback.getResults());

	}

	private Map<String, Object> getParameterMap(Map<String, Object> values, Object sortKeyValue) {
		Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
		if (values != null) {
			parameterMap.putAll(values);
		}
		if (sortKeyValue != null) {
			parameterMap.put("_"+((String)(queryProvider.getSortKeys().keySet().toArray()[0])).trim(), sortKeyValue);
		}
		
			logger.debug("Using parameterMap:" + parameterMap);
		return parameterMap;
	}

	private List<Object> getParameterList(Map<String, Object> values, Object sortKeyValue) {
		SortedMap<String, Object> sm = new TreeMap<String, Object>();
		if (values != null) {
			sm.putAll(values);
		}
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.addAll(sm.values());
		if (sortKeyValue != null) {
			parameterList.add(sortKeyValue);
		}
			logger.debug("Using parameterList:" + parameterList);
		return parameterList;
	}

	private class PagingRowCallbackHandler implements RowCallbackHandler {
		private final List<T> results = new ArrayList<T>();

		public List<T> getResults() {
			return results;
		}

		@SuppressWarnings("unchecked")
		public void processRow(ResultSet rs) throws SQLException {
			startAfterValue = rs.getObject(1);
			results.add((T) rowMapper.mapRow(rs, results.size()));
		}
	}

}
