package it.nic.uniapp.adapters;

import it.nic.uniapp.PopUpWindow;
import it.nic.uniapp.R;
import it.nic.uniapp.core.PageLoader;
import it.nic.uniapp.core.PageLoader.PageType;
import it.nic.uniapp.db.DBHandler;
import it.nic.uniapp.db.EsameEntity;
import it.nic.uniapp.db.IDBHandler;
import it.nic.uniapp.util.Util;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Calendar;

public class GridCellAdapter extends BaseAdapter implements OnClickListener {
	//manca la segnalazione degli esami sui pulsanti e collegare lista dettagli a bottone.
	private Context context = null;

	private ArrayList<String> list = null;
	private static final int DAY_OFFSET = 1;
	private String[] weekdays = new String[] { "Luned�", "Marted�", "Mercoled�", "Gioved�", "Venerd�", "Sabato", "Domenica" };
	private final String[] months = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" };
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private int daysInMonth = 0;
	private int currentDayOfMonth = 0;
	private int currentWeekDay = 0;
	private Button selectedDayMonthYearButton;
	private Button gridcell = null;
	private List<EsameEntity>esami = null;
	private PopupWindow pw = null;
	private Activity activity = null;

	// private TextView num_events_per_day;
	// private final HashMap<String, Integer> eventsPerMonthMap;
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	public GridCellAdapter(Context context, int textViewResourceId, int month, int year, Activity a) {
		super();
		this.context = context;
		this.list = new ArrayList<String>();
		this.activity = a;
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

		printMonth(month, year);
	}

	private String getMonthAsString(int i) {
		return months[i];
	}
	
	private int getMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
		
	}
	
	private String getWeekDayAsString(int i) {
		return weekdays[i];
	}

	private int getNumberOfDaysOfMonth(int i) {
		return daysOfMonth[i];
	}

	public String getItem(int position) {
		return list.get(position);
	}

	public int getCount() {
		return list.size();
	}

	public long getItemId(int position) {
		return position;
	}

	private void printMonth(int mm, int yy) {
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;

		int currentMonth = mm - 1;
		String currentMonthName = getMonthAsString(currentMonth);
		daysInMonth = getNumberOfDaysOfMonth(currentMonth);

		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

		if (currentMonth == 11) {
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;

		} else if (currentMonth == 0) {
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 1;

		} else {
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);

		}

		int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 2;
		trailingSpaces = currentWeekDay;

		if (cal.isLeapYear(cal.get(Calendar.YEAR)))
			if (mm == 2)
				++daysInMonth;
			else if (mm == 3)
				++daysInPrevMonth;

		// Trailing Month days
		for (int i = 0; i < trailingSpaces; i++) {
			list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
		}

		// Current Month Days
		for (int i = 1; i <= daysInMonth; i++) {

			if (i == getCurrentDayOfMonth() && getMonth() == currentMonth) {
				list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
			} else {
				list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
			}
		}

		// Leading Month days
		for (int i = 0; i < list.size() % 7; i++) {

			list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.grid_cell, parent, false);
		}

		gridcell = (Button) row.findViewById(R.id.grid_cell_giorno);
		gridcell.setOnClickListener(this);

		String[] day_color = list.get(position).split("-");
		String theday = day_color[0];
		String themonth = day_color[2];
		String theyear = day_color[3];
		// if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
		// if (eventsPerMonthMap.containsKey(theday)) {
		// num_events_per_day = (TextView) row
		// .findViewById(R.id.num_events_per_day);
		// Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
		// num_events_per_day.setText(numEvents.toString());

		gridcell.setText(theday);
		gridcell.setTag(theday + "/" + themonth + "/" + theyear);

		if (day_color[1].equals("GREY")) {
			gridcell.setTextColor(Color.WHITE);
			gridcell.setBackgroundColor(Color.LTGRAY);

		}
		if (day_color[1].equals("WHITE")) {
			gridcell.setTextColor(Color.BLACK);
			
		}
		if (day_color[1].equals("BLUE")) {
			gridcell.setTextColor(Color.RED);
		}
		return row;

	}

	@Override
	public void onClick(View view) {
		String date_month_year = (String) view.getTag();
		String date = Util.getDateFormattedFromString(date_month_year);
		IDBHandler db = new DBHandler(this.context);
		
		this.esami = new ArrayList<EsameEntity>(); 
		//ottengo tutti gli esami con quella data e apro una lista con i dettagli
		try {
			this.esami = db.getEsameByDate(date);
			pw = new PopupWindow(this.activity);
			 LinearLayout layout = new LinearLayout(this.activity);
		     LayoutParams params;
		     //ListView lista = (ListView)this.activity.findViewById(R.id.popup__listview);
		     LinearLayout mainLayout = new LinearLayout(this.activity);
		     boolean click = true;
		     TextView v = new TextView(this.context);
		     v.setText("ciao");
		     Button but = new Button(this.context);
		     but.setText("chiudi!");
		     but.setOnClickListener(new OnClickListener(){
		    	 public void onClick(View v) {
		    		 pw.dismiss();
		    	 }
		    	 
		     });
		     
		     params = new LayoutParams(LayoutParams.WRAP_CONTENT,
		    	        LayoutParams.WRAP_CONTENT);
		    	      layout.setOrientation(LinearLayout.VERTICAL);
		    	      layout.addView(v,params);
		    	      pw.setContentView(layout);
		    	      
		    	      mainLayout.addView(but, params);
		    	      pw.setContentView(mainLayout);
		    	     
		      
		    LayoutInflater inflater = (LayoutInflater)this.activity .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		    layout = (LinearLayout)inflater.inflate(R.layout.popup_window,(ViewGroup)this.activity.findViewById(R.id.popup__element));  
		    pw.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(date);
	}
	
	//devo ciclare tutti i giorni e verificare se in quella data ci sono esami e colorare il giorno corrispondente
	private void checkEsami() {
		List<EsameEntity>lista = new ArrayList<EsameEntity>();
		IDBHandler db = new DBHandler (this.context);
		try {
			lista = db.getAllEsami();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (EsameEntity e : lista){
			
		}
	}

	private void setCurrentDayOfMonth(int currentDayOfMonth) {
		this.currentDayOfMonth = currentDayOfMonth;
	}

	public void setCurrentWeekDay(int currentWeekDay) {
		this.currentWeekDay = currentWeekDay;
	}

	public int getCurrentWeekDay() {
		return currentWeekDay;
	}

	public int getCurrentDayOfMonth() {
		return currentDayOfMonth;
	}

}
