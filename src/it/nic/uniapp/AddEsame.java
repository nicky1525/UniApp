package it.nic.uniapp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import it.nic.uniapp.adapters.EsameAdapter;
import it.nic.uniapp.core.PageLoader;
import it.nic.uniapp.core.PageLoader.PageType;
import it.nic.uniapp.db.DBHandler;
import it.nic.uniapp.db.DatabaseHelper;
import it.nic.uniapp.db.EsameEntity;
import it.nic.uniapp.db.IDBHandler;
import it.nic.uniapp.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddEsame extends Activity {
	final Calendar c = Calendar.getInstance();

    int maxYear = c.get(Calendar.YEAR) - 20; // this year ( 2014 ) - 20 = 1994
    int maxMonth = c.get(Calendar.MONTH);
    int maxDay = c.get(Calendar.DAY_OF_MONTH);

    int minYear = 1960;
    int minMonth = 0; // january
    int minDay = 25;
	
	private DBHandler db = new DBHandler(this);
	private ArrayList<String> stringhe = null;
	private int id = -1 ;
	private Button btnAdd = null;
	private Button btnAnnulla = null;
	private EditText edtNome = null;
	private EditText edtTotCred = null;
	private EditText edtVoto = null;
	private EditText edtCred = null;
	private DatePicker date = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_esame);

		this.btnAdd = (Button)this.findViewById(R.id.add_esame__btnADDESAME);
		this.btnAnnulla =(Button)this.findViewById(R.id.add_esame__btnANNULLA);
		this.edtCred = (EditText)this.findViewById(R.id.add_esame__edtCREDITI);
		this.edtNome = (EditText)this.findViewById(R.id.add_esame__edtNOME);
		this.edtTotCred = (EditText)this.findViewById(R.id.add_esame__edtTOTCREDITI);
		this.edtVoto = (EditText)this.findViewById(R.id.add_esame__edtVOTO);
		this.date = (DatePicker)this.findViewById(R.id.add_esame__datePicker);
		
		this.btnAdd.setOnClickListener(btn_OnClickListener);
		this.btnAnnulla.setOnClickListener(btn_OnClickListener);
		this.edtNome.setImeOptions(EditorInfo.IME_ACTION_DONE);
		
	}
	
	
	
	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("add_esame__btnANNULLA")) {
			this.finish();
		}

		else if (tag != null && tag.equals("add_esame__btnADDESAME")) {
			
			Intent i = this.getIntent();		
			i.putExtra("Esame","Esame");
			setResult(RESULT_OK,i);
			
			EsameEntity e = new EsameEntity(Util.getDateFromDatePicker(date), this.edtNome.getText().toString(), this.edtTotCred.getText().toString(), this.edtVoto.getText().toString(), this.edtCred.getText().toString());
			IDBHandler dbhandler = new DBHandler(this);
			try {
				
				dbhandler.insertNewEsame(e);
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			finish();
			
			

		}

	}

	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};
}
