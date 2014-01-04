package it.nic.uniapp;

import java.util.ArrayList;

import it.nic.uniapp.core.PageLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class UpdateEsame extends Activity {
	

	private Bundle bundle = null;

	public static final String KEY2 = "Modifica_Esame__key2";
	private Button btnAdd = null;
	private Button btnAnnulla = null;
	private EditText edtNome = null;
	private EditText edtTotCred = null;
	private EditText edtVoto = null;
	private EditText edtCred = null;
	private DatePicker date = null;
	private String data = null;
	private String id = null;
	private ArrayList<String> stringhe = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_esame);

		this.data = "3/01/14";

		this.btnAdd = (Button) this.findViewById(R.id.update_esame__btnADDESAME);
		this.btnAnnulla = (Button) this.findViewById(R.id.update_esame__btnANNULLA);
		this.edtCred = (EditText) this.findViewById(R.id.update_esame__edtCREDITI);
		this.edtNome = (EditText) this.findViewById(R.id.update_esame__edtNOME);
		this.edtTotCred = (EditText) this.findViewById(R.id.update_esame__edtTOTCREDITI);
		this.edtVoto = (EditText) this.findViewById(R.id.update_esame__edtVOTO);
		this.date = (DatePicker) this.findViewById(R.id.update_esame__datePicker1);

		this.bundle = this.getIntent().getExtras();

		this.id = bundle.getStringArrayList(ListaEsami.KEY1).get(0);
		//String date = bundle.getStringArrayList(ListaEsami.KEY1).get(1);
		String nome = bundle.getStringArrayList(ListaEsami.KEY1).get(1);
		String totCred = bundle.getStringArrayList(ListaEsami.KEY1).get(2);
		String voto = bundle.getStringArrayList(ListaEsami.KEY1).get(3);
		String cred = bundle.getStringArrayList(ListaEsami.KEY1).get(4);

		
		
		this.edtNome.setText(nome);

		this.edtTotCred.setText(totCred);

		this.edtVoto.setText(voto);

		this.edtCred.setText(cred);

		this.btnAdd.setOnClickListener(btn_OnClickListener);
		this.btnAnnulla.setOnClickListener(btn_OnClickListener);

	}

	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("update_esame__btnANNULLA")) {
			Bundle b = this.getIntent().getExtras();
			b.clear();
			setResult(RESULT_OK,this.getIntent());
			this.finish();
			
		}

		else if (tag != null && tag.equals("update_esame__btnADDESAME")) {
			Intent i = getIntent();
			this.stringhe = new ArrayList<String>();
			stringhe.add(this.id);
			stringhe.add(this.data.toString());
			stringhe.add(this.edtNome.getText().toString());
			stringhe.add(this.edtTotCred.getText().toString());
			stringhe.add(this.edtVoto.getText().toString());
			stringhe.add(this.edtCred.getText().toString());

			i.putExtra(KEY2, stringhe);
			setResult(RESULT_OK, i);

			finish();

		}

	}

	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};
}
