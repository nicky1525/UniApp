package it.nic.uniapp;

import it.nic.uniapp.core.PageLoader;
import it.nic.uniapp.core.PageLoader.PageType;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private PageLoader pageLoader = null;
	
	private TextView txtBenvenuto = null;
	private ImageView imageView1 = null;
	private ImageButton btnCalendar = null;
	private Button btnRes = null;
	private Button btnAllEsami = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.pageLoader = new PageLoader();
		
		this.txtBenvenuto = (TextView)this.findViewById(R.id.activity_main__txtBENVENUTO);
		this.imageView1 = (ImageView)this.findViewById(R.id.activity_main__imageView1);
		this.btnCalendar = (ImageButton)this.findViewById(R.id.activity_main__btnCALENDAR);
		this.btnAllEsami = (Button)this.findViewById(R.id.activity_main__btnAllEsami);
		this.btnRes = (Button)this.findViewById(R.id.activity_main__btnRES);
		
		this.btnCalendar.setOnClickListener(btn_OnClickListener);
		this.btnRes.setOnClickListener(btn_OnClickListener);
		this.btnAllEsami.setOnClickListener(btn_OnClickListener);
		
	}
	
	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("activity_main__btnCALENDAR")) {
			pageLoader.startPageDependentActivity(this, PageType.CalendarioEsami,false, null);
		}
		
		else if (tag != null && tag.equals("activity_main__btnAllEsami")){
			pageLoader.startPageDependentActivity(this, PageType.ListaEsami,false, null);
		}
		
		else if (tag != null && tag.equals("activity_main__btnRES")){
			pageLoader.startPageDependentActivity(this, PageType.FunzioniEsami,false, null);
		}
	}

	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
