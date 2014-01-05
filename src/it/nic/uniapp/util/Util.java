package it.nic.uniapp.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;


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
//	    cal.add(Calendar.DATE, 1);
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
	

}