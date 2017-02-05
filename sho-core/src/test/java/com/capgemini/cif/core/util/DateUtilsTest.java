package com.capgemini.cif.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.cif.core.util.DateUtils;

public class DateUtilsTest 
{
	@Autowired
	DateUtils dateUtils;
	
	@Test
	public void testAddDaysToDate() throws Exception{
		
		Calendar c = Calendar.getInstance();
		Date date =DateUtils.addDaysToDate(c.getTime(), -1, null);
		c.add(Calendar.DATE, -1);
		
		Assert.assertEquals(date,c.getTime());
	}
	
	@Test
	public void testAddDaysToDateNullDate() throws Exception{
		
		Calendar c = Calendar.getInstance();
		Date date =DateUtils.addDaysToDate(null, -1, c.getTime());
		c.add(Calendar.DATE, -1);
		
		Assert.assertEquals(date,c.getTime());
	}
	
	@Test
	public void testFormatDate(){
		Calendar c = Calendar.getInstance();
		
		String formattedDate =DateUtils.formatDate(c.getTime(),null);
		
		Assert.assertNotNull(formattedDate);
	}
	
	@Test
	public void testGetElapsedYearTime(){
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.add(Calendar.YEAR, 4);
		Assert.assertEquals(-4, DateUtils.getElapsedYearTime(c.getTime(), null));
	}
	
	@Test
	public void testGetElapsedYearTimeMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.add(Calendar.YEAR, 4);
		c.add(Calendar.MONTH, 2);
		Assert.assertEquals(-5, DateUtils.getElapsedYearTime(c.getTime(), null));
	}
	
	@Test
	public void testGetElapsedYearTimeMonthNullDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.add(Calendar.YEAR, 4);
		c.add(Calendar.MONTH, 2);
		Assert.assertEquals(-5, DateUtils.getElapsedYearTime(null, c.getTime()));
	}
	
	@Test
	public void testGetElapsedYearTimeNullDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		c.add(Calendar.YEAR, 4);
		Assert.assertEquals(-4, DateUtils.getElapsedYearTime(null, c.getTime()));
	}	
	@Test
	public void testGetDayOfWeek(){
		Assert.assertEquals(Calendar.MONDAY,DateUtils.getDayOfWeek("monday"));
		Assert.assertEquals(Calendar.TUESDAY,DateUtils.getDayOfWeek("tuesday"));
		Assert.assertEquals(Calendar.WEDNESDAY,DateUtils.getDayOfWeek("wednesday"));
		Assert.assertEquals(Calendar.THURSDAY,DateUtils.getDayOfWeek("thursday"));
		Assert.assertEquals(Calendar.FRIDAY,DateUtils.getDayOfWeek("friday"));
		Assert.assertEquals(Calendar.SATURDAY,DateUtils.getDayOfWeek("saturday"));
		Assert.assertEquals(Calendar.SUNDAY,DateUtils.getDayOfWeek("sunday"));
	}
	
	@Test
	public void testGetWeekDate() throws ParseException{
		String weekEnd="SATURDAY 22:00:00";
		Date returnDate = DateUtils.getWeekDate(weekEnd);
		Assert.assertNotNull(returnDate);
		
	}
	@Test
	public void testParseDate(){
		Date date = null;
		try {
			date = DateUtils.parseDate("2016-03-06 11:12:13");
		} catch (ParseException e) {
			Assert.assertNull(date);
		}
		Assert.assertNotNull(date);
	}
	
}
