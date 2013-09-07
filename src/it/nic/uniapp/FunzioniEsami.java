package it.nic.uniapp;


import java.sql.SQLException;

import it.nic.uniapp.db.DBHandler;
import it.nic.uniapp.db.EsameEntity;
import it.nic.uniapp.db.IDBHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FunzioniEsami extends Activity {
	
	
	private TextView txtMedia = null;
	private TextView txtMd = null;
	private TextView txtPassedEx = null;
	private TextView txtPdE = null;
	private TextView txtFailedE = null;
	private TextView txtFdE = null;
	private TextView txtTotCred = null;
	private TextView txtTotC = null;
	private Button btnBack = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funzioni_esami);
		
		IDBHandler db = new DBHandler(this.getApplicationContext());
		
		
		this.btnBack = (Button)this.findViewById(R.id.funzioni_esami__btnBACK);
		this.txtMedia = (TextView)this.findViewById(R.id.funzioni_esami__txtMEDIA);
		this.txtMd = (TextView)this.findViewById(R.id.funzioni_esami__txtMED);
		this.txtPassedEx = (TextView)this.findViewById(R.id.funzioni_esami__txtPASSEDEXAMS);
		this.txtPdE = (TextView)this.findViewById(R.id.funzioni_esami__txtPASSEDEX);
		this.txtFailedE = (TextView)this.findViewById(R.id.funzioni_esami__txtFAILEDEXAMS);
		this.txtFdE = (TextView)this.findViewById(R.id.funzioni_esami__txtFAILEDEX);
		this.txtTotCred = (TextView)this.findViewById(R.id.funzioni_esami__txtTOTCREDITI);
		this.txtTotC = (TextView)this.findViewById(R.id.funzioni_esami__txtTOTCRED);
		
		this.btnBack.setOnClickListener(btn_OnClickListener);
		
		//TextView SetText with Results
		try {
			this.txtMd.setText(db.getMedia());
			this.txtTotC.setText(db.getCrediti());
			this.txtPdE.setText(db.getSuperati());
			this.txtFdE.setText(db.getFalliti());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("funzioni_esami__btnBACK")) {
			this.finish();
		}
		
	}
	
	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};


}
