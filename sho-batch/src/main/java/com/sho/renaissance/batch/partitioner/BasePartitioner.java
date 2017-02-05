package com.sho.renaissance.batch.partitioner;

import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Base partitioner which will be extended by all partitioner
 * 
 * @author gdhimate
 *
 */
public abstract class BasePartitioner extends JdbcTemplate implements Partitioner {

	public abstract Map<String, ExecutionContext> partition(int gridSize);

}