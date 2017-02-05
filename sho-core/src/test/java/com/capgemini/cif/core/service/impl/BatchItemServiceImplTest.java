package com.capgemini.cif.core.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.cif.core.service.impl.BatchItemServiceImpl;

@ContextConfiguration(locations = { "classpath:applicationContextTest.xml " })
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchItemServiceImplTest {
	@Autowired
	BatchItemServiceImpl batchItemServiceImpl;

	@Test
	public void testIsThresholdBreachedTrue() {
		Boolean returnValue = batchItemServiceImpl.isThresholdBreached(1, "1212", "syncnsitem");
		Assert.assertEquals(Boolean.TRUE, returnValue);
	}

	@Test
	public void testIsThresholdBreachedFalse() {
		Boolean retVal = batchItemServiceImpl.isThresholdBreached(1, "1213", "syncnsstore");
		Assert.assertEquals(Boolean.FALSE, retVal);
	}

	/*
	 * @Test public void isAllPredecessorsComplete() { boolean returnValue =
	 * batchItemServiceImpl.isAllPredecessorsComplete(null);
	 * Assert.assertEquals(Boolean.TRUE, returnValue); }
	 * 
	 * @Test public void isAllPredecessorsCompleteNotNull() { String[]
	 * predecessors = {"SYNCNSCLUSTERFEED","SYNCNSSTOREINVFEED"}; boolean
	 * returnValue =
	 * batchItemServiceImpl.isAllPredecessorsComplete(predecessors);
	 * Assert.assertEquals(Boolean.TRUE, returnValue); }
	 */
}
