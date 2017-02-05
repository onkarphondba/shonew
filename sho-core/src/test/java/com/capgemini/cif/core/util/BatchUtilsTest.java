package com.capgemini.cif.core.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.capgemini.cif.core.service.impl.BatchItemServiceImpl;
@Ignore
public class BatchUtilsTest 
{
	@Test
	public void testInvokPrivateMethod()
	{
		BatchItemServiceImpl batchItemServiceImpl = new BatchItemServiceImpl();
		
		BatchUtils.invokPrivateMethod(new BatchItemServiceImpl(), "createProcessStatus",2,null,null);
		
		//Assert.assertEquals(returnresult,false);
	}
	
}
