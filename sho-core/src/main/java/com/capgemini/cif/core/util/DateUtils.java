package com.capgemini.cif.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * @author gdhimate
 * 
 *         All the methods related to date formatting and creation
 */
public final class DateUtils {

	private static final String DATE_FORMAT="yyyy-MM-dd";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final int GRETER_THAN=1;
	public static final int LESS_THAN=-1;
	public static final int EQUAL=0;

	private DateUtils(){}
	/**
	 * Get formatted date with pattern yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date,String format) {
		if(format==null){
			format=DATE_TIME_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return sdf.format(c.getTime());
	}

	public static String formatDateToTimeZone(Date date,String format,String timeZone) {
		if(format==null){
			format=DATE_TIME_FORMAT;
		}
		if (date == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// default system timezone if passed null or empty
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		// set timezone to SimpleDateFormat
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// return Date in required format with timezone as String
		return sdf.format(c.getTime());
	}
	public static Date parseDate(String dateString) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(dateString);
	}
	
	public static Date parseDate(String dateString,String format) throws ParseException{
		if(format==null){
			format=DATE_TIME_FORMAT;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateString);
	}


	/**
	 * Add days to the given date
	 * 
	 * @param date
	 * @param noOfDays
	 * @return
	 * @throws ParseException
	 */
	public static Date addDaysToDate(Date date, int noOfDays, Date defaultDate){
		if(date == null){
			date = defaultDate;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, noOfDays);
		return c.getTime();

	}
	
	
	/** Get year difference between two dates.
	 * @param timeToBeCompared
	 * @param defaultDate
	 * @return
	 */
	public static int getElapsedYearTime(Date timeToBeCompared, Date defaultDate){
		
		if(timeToBeCompared == null)
			timeToBeCompared = defaultDate;
		
		Date date = new Date();

		Calendar a = getCalendar(timeToBeCompared);
		Calendar b = getCalendar(date);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
				(a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) 
			diff--;
		
		return diff;

	}

	/** Get US calander from date.
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * Get day of the week
	 * 
	 * @param day
	 * @return
	 */
	public static int getDayOfWeek(String day) {
		if ("Sunday".equalsIgnoreCase(day)) {
			return Calendar.SUNDAY;
		} else if ("Monday".equalsIgnoreCase(day)) {
			return Calendar.MONDAY;
		} else if ("Tuesday".equalsIgnoreCase(day)) {
			return Calendar.TUESDAY;
		} else if ("Wednesday".equalsIgnoreCase(day)) {
			return Calendar.WEDNESDAY;
		} else if ("Thursday".equalsIgnoreCase(day)) {
			return Calendar.THURSDAY;
		} else if ("Friday".equalsIgnoreCase(day)) {
			return Calendar.FRIDAY;
		} else
			return Calendar.SATURDAY;
	}
	public static Date getWeekDate(String paramValue) throws ParseException {
		String[] times = paramValue.split(" ");
		SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

		Calendar cal = Calendar.getInstance();
		int daysBackToSat = cal.get(getDayOfWeek(times[0]));
		cal.add(Calendar.DATE, daysBackToSat*-1);
		
		String returnDate = sdf1.format(cal.getTime())+" 00:00:00";
		

		return sdf.parse(returnDate);
				
		//org.apache.commons.lang.time.DateUtils.parseDate(sdf.format(cal.getTime())+" "+times[1], new String[]{"yyyy-MM-dd hh:mm:ss"});
	}

	public static int compareDates(Date firstDate, Date secondDate)throws ParseException{
		
		 if(firstDate.compareTo(secondDate)>0){
          return GRETER_THAN;
       }else if(firstDate.compareTo(secondDate)<0){
      	 return LESS_THAN;
       }else if(firstDate.compareTo(secondDate)==0){
          return EQUAL;
       }else{
      	 return EQUAL;
       }
	}
	
}
