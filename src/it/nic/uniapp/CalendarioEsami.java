package it.nic.uniapp;

import it.nic.uniapp.adapters.GridCellAdapter;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class CalendarioEsami extends Activity {
	// manca da definire come aggiungere e rimuovere un esame e il calendario!

	private Button btnBack = null;
	private ImageButton prevMonth = null;
	private ImageButton follMonth = null;
	private TextView currentMonth = null;
	private GridView calendar_view = null;
	private LinearLayout calendarDays = null;
	private Calendar calendar = null;
	private int month = 0;
	private int year = 0;
	private GridCellAdapter adapter = null;

	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendario_esami);

		calendar = Calendar.getInstance(Locale.getDefault());
		month = calendar.get(Calendar.MONTH) + 1;
		year = calendar.get(Calendar.YEAR);

		this.calendar_view = (GridView) this.findViewById(R.id.calendario_esami__calendar);
		this.btnBack = (Button) this.findViewById(R.id.calendario_esami__btnBack);
		
		this.currentMonth = (TextView) this.findViewById(R.id.calendario_esami__current_month);
		this.prevMonth = (ImageButton) this.findViewById(R.id.calendario_esami__prevMonth);
		this.follMonth = (ImageButton) this.findViewById(R.id.calendario_esami__follMonth);
		this.calendarDays = (LinearLayout)this.findViewById(R.id.calendario_esami__giorni);
		
		this.btnBack.setOnClickListener(btn_OnClickListener);
	
		this.prevMonth.setOnClickListener(btn_OnClickListener);
		this.follMonth.setOnClickListener(btn_OnClickListener);
		this.currentMonth.setText(DateFormat.format(dateTemplate, calendar.getTime()));

		adapter = new GridCellAdapter(getApplicationContext(), R.id.grid_cell_giorno, month, year);
		adapter.notifyDataSetChanged();
		calendar_view.setAdapter(adapter);
		
	}

	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getApplicationContext(), R.id.grid_cell_giorno, month, year);
		calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate, calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendar_view.setAdapter(adapter);
	
	}

	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("calendario_esami__btnBack")) {
			this.finish();
		}

		if (tag != null && tag.equals("calendario_esami__prevMonth")) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			
			setGridCellAdapterToDate(month,year);
		}

		if (tag != null && tag.equals("calendario_esami__follMonth")) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			
			setGridCellAdapterToDate(month,year);

		}
		
		if (tag != null && tag.equals("grid_cell_giorno")) {
			//System.out.println(this.btnDay.getText().toString());
			
		}

	}

	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};

}
