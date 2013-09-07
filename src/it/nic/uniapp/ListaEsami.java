package it.nic.uniapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.nic.uniapp.adapters.EsameAdapter;
import it.nic.uniapp.core.PageLoader;
import it.nic.uniapp.core.PageLoader.PageType;
import it.nic.uniapp.db.DBHandler;
import it.nic.uniapp.db.EsameEntity;
import it.nic.uniapp.db.IDBHandler;
import it.nic.uniapp.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

public class ListaEsami extends Activity {
	// manca come aggiungere un esame
	private PageLoader pageLoader = null;
	private IDBHandler dbhandler = null;
	private Bundle bundle = null;

	public static final String TAG = "ListaEsami";

	public static final String KEY1 = "Modifica_Esame__key1";

	private ListView listaEsami = null;
	private Button btnBack = null;
	private Button btnAddEsame = null;

	private EsameAdapter adapter = null;

	private List<EsameEntity> lista = null;
	private ArrayList<String> stringhe = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_esami);

		dbhandler = new DBHandler(this);
		try {
			dbhandler.copyDataBase(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			this.lista = this.dbhandler.getAllEsami();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		this.adapter = new EsameAdapter(this, this.lista);

		this.listaEsami = (ListView) this.findViewById(R.id.lista_esami__listView1);
		registerForContextMenu(listaEsami);
		this.btnBack = (Button) this.findViewById(R.id.lista_esami__btnBACK);
		this.btnAddEsame = (Button) this.findViewById(R.id.lista_esami__btnAddEsame);

		this.listaEsami.setAdapter(adapter);

		this.btnBack.setOnClickListener(btn_OnClickListener);
		this.btnAddEsame.setOnClickListener(btn_OnClickListener);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Bundle b = data.getExtras();

		if (b != null && b.containsKey(UpdateEsame.KEY2)) {

			try {
				dbhandler.updateEsameById(b);
				lista = dbhandler.getAllEsami();
				adapter = new EsameAdapter(this, this.lista);

				this.listaEsami.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (b != null && b.containsKey("Esame")) {

			EsameEntity e = (EsameEntity) b.getParcelable("Esame");
			try {
				dbhandler.insertNewEsame(e);
				lista = dbhandler.getAllEsami();
				adapter = new EsameAdapter(this, lista);
				this.listaEsami.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		EsameEntity entity = (EsameEntity) (this.adapter.getItem(info.position));
		Log.v(TAG, entity.toString());

		switch (item.getItemId()) {
		case R.id.menu__modifica:
			this.pageLoader = new PageLoader();

			this.stringhe = new ArrayList<String>();
			this.stringhe.add(Integer.toString(entity.getId()));
			// this.stringhe.add(entity.getData());
			this.stringhe.add(entity.getNome());
			this.stringhe.add(entity.getTotCred());
			this.stringhe.add(entity.getVoto());
			this.stringhe.add(entity.getCredAcq());

			this.bundle = new Bundle();
			bundle.putStringArrayList(KEY1, this.stringhe);
			pageLoader.startPageDependentActivity(this, PageType.UpdateEsame, true, bundle);

			return true;

		case R.id.menu__eliminaTutti:

			try {
				dbhandler.deleteAllEsami();
				lista = dbhandler.getAllEsami();
				adapter = new EsameAdapter(this, this.lista);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// / ---
			this.adapter = new EsameAdapter(this, this.lista);
			this.listaEsami.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			// ---

			return true;

		case R.id.menu__elimina:

			try {
				dbhandler.deleteEsameById(entity.getId());
				lista = dbhandler.getAllEsami();
				adapter = new EsameAdapter(this, this.lista);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// / ---
			this.adapter = new EsameAdapter(this, this.lista);
			this.listaEsami.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			// ---

			Util.showToast(this, entity.getNome() + " rimosso");
			return true;
		case R.id.menu__annulla:
			// non fa nulla
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("lista_esami__btnBACK")) {
			this.finish();
		}

		else if (tag != null && tag.equals("lista_esami__btnAddEsame")) {
			this.pageLoader = new PageLoader();
			pageLoader.startPageDependentActivity(this, PageType.AddEsame, false, null);

		}

	}

	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};

	@Override
	protected void onResume() {

		super.onResume();
		adapter.notifyDataSetChanged();
	};

}