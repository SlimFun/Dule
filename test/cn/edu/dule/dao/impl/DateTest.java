package cn.edu.dule.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DateTest {
	@Test
	public void dateTest(){
		String dateStr = "9 October, 2016";
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		java.util.Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date);
	}
	
	@Test
	public void test(){
		
		Date date=new Date();
		DateFormat format=DateFormat.getDateInstance(DateFormat.FULL,Locale.CHINA);
		String str=format.format(date);
		System.out.println("中国日期:\t"+str);
		format=DateFormat.getDateInstance(DateFormat.FULL,Locale.CANADA);
		str=format.format(date);
		System.out.println("加拿大日期:\t"+str);
		format=DateFormat.getDateInstance(DateFormat.FULL,Locale.JAPAN);
		str=format.format(date);
		System.out.println("小日本日期:\t"+str);
		format=DateFormat.getDateInstance(DateFormat.FULL,Locale.FRANCE);
		str=format.format(date);
		System.out.println("法国日期:\t"+str);
		format=DateFormat.getDateInstance(DateFormat.LONG,Locale.ENGLISH);
		str=format.format(date);
		
		System.out.println("德国日期:\t"+str);
		format=DateFormat.getDateInstance(DateFormat.FULL,Locale.ITALIAN);
		str=format.format(date);
		System.out.println("意大利日期:\t"+str);
		format=DateFormat.getDateInstance(DateFormat.FULL,Locale.US);
		str=format.format(date);
		System.out.println("美国日期:\t"+str);
	}
	
	@Test
	public void computeDays(){
		try {
			System.out.println(daysBetween("2016-10-20", "2016-10-25"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
}
