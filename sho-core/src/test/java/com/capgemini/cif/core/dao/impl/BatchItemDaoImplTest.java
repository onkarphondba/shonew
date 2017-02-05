package com.capgemini.cif.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.cif.core.constants.NetSuiteActionNameEnum;
import com.capgemini.cif.core.constants.NetSuiteBatchStatusEnum;
import com.capgemini.cif.core.constants.NetSuiteFileTypeEnum;
import com.capgemini.cif.core.domain.BatchItem;
import com.capgemini.cif.core.domain.BatchItemMapper;

@ContextConfiguration(locations = { "classpath:applicationContextTest.xml "})
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchItemDaoImplTest extends GenericDao{
	
    @Autowired
	BatchItemDaoImpl batchItemDaoImpl;
	
    @Test
    public void testGetBatchItemCountWithNull(){

		Integer returnValue = batchItemDaoImpl.getBatchItemCount(null,null,null);
		Assert.assertEquals(null, returnValue);
	}
    
    @Test
	public void testGetBatchItemCountWithValidIsNull(){

		Integer returnValue = batchItemDaoImpl.getBatchItemCount("1212","syncnsitem",null);
		Assert.assertEquals(new Integer(4), returnValue);
	}
    @Test
	public void testGetBatchItemCountWithValidIsTrue(){

		Integer returnValue = batchItemDaoImpl.getBatchItemCount("1212","syncnsitem",Boolean.TRUE);
		Assert.assertEquals(new Integer(2), returnValue);
	}
    @Test
	public void testGetBatchItemCountWithValidIsFalse(){

		Integer returnValue = batchItemDaoImpl.getBatchItemCount("1212","syncnsitem",Boolean.FALSE);
		Assert.assertEquals(new Integer(2), returnValue);
	}
    @Test
   	public void testGetBatchItemByStatusIsFalse(){
    	
   		List<BatchItem> returnValue = batchItemDaoImpl.getBatchItemByStatus(Boolean.FALSE);
   		 
   		Assert.assertTrue(returnValue.size()==2);
   	}
    @Test
   	public void testGetBatchItemByStatusIsTrue(){

   		List<BatchItem> returnValue = batchItemDaoImpl.getBatchItemByStatus(Boolean.TRUE);
   		Assert.assertTrue(returnValue.size()==5);
   	}
    
    @Test
   	public void testGetSelectedItems(){

   		List<Object> returnValue = batchItemDaoImpl.getSelectedItems("select * from batch_item", new BatchItemMapper());
   		Assert.assertEquals(7, returnValue.size());
   	}
    @Test
   	public void testGetSelectedItemsWithSqlNull(){

   		List<Object> returnValue = batchItemDaoImpl.getSelectedItems(null, new BatchItemMapper());
   		Assert.assertEquals(null,null);
   	}
    

    @Test
    public void testGetClusterfeedTotalRecords()
    {
    	Integer returnValue = batchItemDaoImpl.getClusterFeedAllRecords();
    	Assert.assertEquals(8, returnValue.intValue());
    }
    
    @Test
    public void testGetClusterFeedFailedRecords()
    {
    	List<Map<String, Object>> returnValue = batchItemDaoImpl.getClusterFeedFailedRecords();
    	Assert.assertEquals(7, returnValue.size());
    }
    
    @Test
    public void testGetClusterfeedId()
    {
    	Integer returnValue = batchItemDaoImpl.getClusterfeedId("Online Price");
    	Assert.assertEquals(new Integer(5), returnValue);
    }
    
    @Test
    public void testGetNetsuiteActionType()
    {
    	String returnValue = batchItemDaoImpl.getNetsuiteActionType(NetSuiteActionNameEnum.CREATE.actionName());
    	Assert.assertEquals(String.valueOf(2), returnValue);
    }
    
    @Test
    public void testGetNetsuiteFileType()
    {
    	String returnValue = batchItemDaoImpl.getNetsuiteFileType(NetSuiteFileTypeEnum.ASSETS.fileType());
    	Assert.assertEquals(String.valueOf(3), returnValue);
    }
    
    @Test
    public void testGetNetsuiteBatchStatus()
    {
    	String returnValue = batchItemDaoImpl.getNetsuiteBatchStatus(NetSuiteBatchStatusEnum.FAILED.batchStatusType());
    	Assert.assertEquals(String.valueOf(1), returnValue);
    }
    
    
    
  /* @Test
    public void testCallStoredProcedure() throws SQLException
    {
    	Map<String, Object> returnValue = batchItemDaoImpl.callStoredProcedure();
    	
		if( (Integer)returnValue.get("ErrorCode") != 0){
			throw new RuntimeException((String)returnValue.get("ErrorMessage"));
		} 
	 
			Assert.assertTrue((Integer)returnValue.get("ErrorCode") == 0); 

    }*/
    
  /*  @Test
    public void testCallPricefeedStoredProcedure()
    {
    	
    }
    */

      
  /*  @Test
   	public void testGetTotalCount(){

   		Integer returnValue = batchItemDaoImpl.getTotalCount("select * from batch_item", null);
   		Assert.assertEquals(new Integer(9), returnValue);
   	}
    
    @Test
    public void testGetProcessStatus()
    {
    	List<String> processes = new ArrayList<>();
    	processes.add("SYNCNSCLUSTERFEED");
    	processes.add("SYNCNSSTOREINVFEED");
		List<ProcessStatus> returnValue = batchItemDaoImpl.getProcessStatus(processes);
		Assert.assertEquals(2, returnValue.size());
    }*/
    
    
}
