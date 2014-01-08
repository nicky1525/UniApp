package it.nic.uniapp.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

public class Util {

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static void showToast(Context context, String message) {
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
	
	public static String getDateFromDatePicker(DatePicker datePicker){
	    int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();
	    
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month, day);
	    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");
	    System.out.println(cal.getTime());
	  

	    String formatted = format1.format(cal.getTime());
	    System.out.println(formatted);

	    

	    return formatted;
	}
	
	public static Calendar getCalendarFromString(String date){
		
		String[] dateParts = date.split("/");
		int year = Integer.parseInt("20"+dateParts[2]);
		int month = Integer.parseInt(dateParts[1])-1;
		int day = Integer.parseInt(dateParts[0]);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		
		return calendar;
	 
	}
	
	public static String getDateFormattedFromString(String m){
		
	    DateTimeFormatter format = DateTimeFormat.forPattern("dd/MMMM/yyyy").withLocale(Locale.ITALY);
	    DateTime instance        = format.parseDateTime(m.toLowerCase());
	    int month = instance.getMonthOfYear()-1;
	    int day = instance.getDayOfMonth();
	    int year = instance.getYear();
	    Calendar c = Calendar.getInstance();
	    c.set(year, month, day);
	    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
	    String data = f.format(c.getTime());
    	
	   
	    return data;  
		
	}
	

}