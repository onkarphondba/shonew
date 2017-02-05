package com.sho.renaissance.batch.partitioner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author gdhimate
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KeyBasedPartitionerTest {

	@Spy
	KeyBasedPartitioner keyBasedPartitioner = Mockito.mock(KeyBasedPartitioner.class);

	@Spy
	JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);

	private String partitionerQuery = "select bi.id from Remote_batch_item bi where bi.Process_Execution_Id =1 order by bi.id";

	@Before
	public void prepare() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPartition() {

		List<Object> keys = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			keys.add(i);
		}

		Mockito.when(jdbcTemplate.queryForList(partitionerQuery, Object.class)).thenReturn(keys);

		Mockito.when(keyBasedPartitioner.partition(5)).thenCallRealMethod();

		//Assert.assertEquals(5, keyBasedPartitioner.partition(5).size());
	}
}
