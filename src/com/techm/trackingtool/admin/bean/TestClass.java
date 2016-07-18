package com.techm.trackingtool.admin.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class TestClass {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = null;
		
		String value ="03/03/2016 16:01:40";
		
		String[] yearStrValue = value.split("/");
	System.out.println("yearStrValue is ::"+yearStrValue.length+" posuition :: "+yearStrValue[2]);
		String[] yearValue=yearStrValue[2].split(" ");
		
		System.out.println("yearStrValue isfsdfdsf ::"+yearValue[0].length());
		
		if(yearStrValue[2].length() == 4)
			 sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		else
			sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		
			Date date = (Date) sdf.parse(value);
			System.out.println("date  "+date.toString());
			//java.sql.Date	sqlDate =  new java.sql.Date(date.getTime());
			
			sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			System.out.println("new date String  " +sdf.format(date));
			
			System.out.println("new date Stringadadadasdadsa  " +sdf.parse(sdf.format(Calendar.getInstance().getTime())));
			
			
			 int monthsToAdd = 4;
	         int monthsToSubtract = 0;
	         Calendar c = Calendar.getInstance();
	         System.out.println("Current date : " + (c.get(Calendar.MONTH) + 1) +
			                 "-" + c.get(Calendar.DATE) + "-" + c.get(Calendar.YEAR));
			         // add months to current date
			         c.add(Calendar.MONTH, monthsToAdd);
			         System.out.println("Date (after): " + (c.get(Calendar.MONTH) + 1) +
			                 "-" + c.get(Calendar.DATE) + "-" + c.get(Calendar.YEAR));
			        
			         c = Calendar.getInstance();
			         c.add(Calendar.MONTH, -monthsToSubtract);
			         
			         System.out.println("Date (before): " +c.getTime().getMonth() +"tesrdsfsdf "+ (c.get(Calendar.MONTH) + 1) +
			                 "-" + c.get(Calendar.DATE) + "-" + c.get(Calendar.YEAR));

			         
			         for(int loop=0;loop>-8;loop--)
					  {
			        	 Calendar cal = Calendar.getInstance();
			        	 System.out.println( "loop value is ::: "+loop);
			        	 cal.add(cal.MONTH, loop);
						  
						  String  month = cal.getDisplayName(cal.MONTH, cal.LONG, Locale.getDefault())+","+cal.get(Calendar.YEAR);
						   System.out.println( "month value is "+month);
					  }

	}

}
