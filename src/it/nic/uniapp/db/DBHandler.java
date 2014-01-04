package it.nic.uniapp.db;

import it.nic.uniapp.UpdateEsame;
import it.nic.uniapp.core.Constants;
import it.nic.uniapp.core.PageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.BaseAdapter;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

public class DBHandler extends Activity implements IDBHandler {

	private static final String TAG = "DBHandler";
	private Context context = null;
	private DatabaseHelper databaseHelper = null;

	private PageLoader pageLoader = null;
	private BaseAdapter adapter = null;

	public DBHandler(Context context) {
		this.context = context;

		this.openHandler(context);

	}

	@Override
	public void openHandler(Context context) {

		if (this.databaseHelper == null) {
			this.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
	}

	@Override
	public void closeHandler() {

		if (this.databaseHelper != null && this.databaseHelper.isOpen()) {
			OpenHelperManager.releaseHelper();

			this.databaseHelper = null;
		}
	}

	@Override
	public void copyDataBase(Context context) throws IOException {
		// Path to the just created empty db
		String outFileName = context.getDatabasePath(Constants.DB_NAME).getAbsolutePath();

		// se il file esiste già non faccio nulla
		File outputTmp = new File(outFileName);
		if (outputTmp.exists()) {
			return;
		}

		File dir = new File(outputTmp.getParent());
		if (!dir.exists()) {
			dir.mkdir();
		}

		// Open your local db as the input stream
		InputStream myInput = context.getAssets().open(Constants.DB_NAME);

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	@Override
	public List<EsameEntity> getAllEsami() throws SQLException {

		List<EsameEntity> result = new ArrayList<EsameEntity>();

		if (this.databaseHelper == null || !this.databaseHelper.isOpen()) {
			return result;
		}

		QueryBuilder<EsameEntity, Integer> queryBuilder = databaseHelper.getEsameEntityDao().queryBuilder();

		queryBuilder.distinct().selectColumns(EsameEntity.ID, EsameEntity.DATA, EsameEntity.NOME, EsameEntity.TOTCRED, EsameEntity.VOTO, EsameEntity.CREDACQ);

		PreparedQuery<EsameEntity> preparedQuery = queryBuilder.prepare();

		result = databaseHelper.getEsameEntityDao().query(preparedQuery);

		return result;
	}

	@Override
	public void deleteAllEsami() throws SQLException {
		boolean result = false;
		if (this.databaseHelper != null && this.databaseHelper.isOpen()) {

			DeleteBuilder<EsameEntity, Integer> deleteBuilder = databaseHelper.getEsameEntityDao().deleteBuilder();
			deleteBuilder.delete();
			result = true;

		}
		if (result == true) {
			Log.v(TAG, "Database is now empty");

		}

	}

	public boolean deleteEsameById(int id) throws SQLException {

		if (this.databaseHelper != null && this.databaseHelper.isOpen()) {

			Dao<EsameEntity, Integer> dao = databaseHelper.getEsameEntityDao();

			int queryResult = dao.deleteById(id);

			if (queryResult == 1) {
				Log.v(TAG, "Item deleted");
				return true;
			}

			Log.v(TAG, "Item NOT deleted");
			return false;
		}
		return false;
	}

	@Override
	public boolean updateEsameById(Bundle bundle) throws SQLException {
		if (this.databaseHelper != null && this.databaseHelper.isOpen() && bundle != null) {

			UpdateBuilder<EsameEntity, Integer> updateBuilder = databaseHelper.getEsameEntityDao().updateBuilder();
			updateBuilder.where().eq(EsameEntity.ID, bundle.getStringArrayList(UpdateEsame.KEY2).get(0));

			// updateBuilder.updateColumnValue(EsameEntity.DATA,
			// bundle.getStringArrayList(UpdateEsame.KEY2).get(1).toString());
			updateBuilder.updateColumnValue(EsameEntity.NOME, bundle.getStringArrayList(UpdateEsame.KEY2).get(1).toString());
			updateBuilder.updateColumnValue(EsameEntity.TOTCRED, bundle.getStringArrayList(UpdateEsame.KEY2).get(2).toString());
			updateBuilder.updateColumnValue(EsameEntity.VOTO, bundle.getStringArrayList(UpdateEsame.KEY2).get(3).toString());
			updateBuilder.updateColumnValue(EsameEntity.CREDACQ, bundle.getStringArrayList(UpdateEsame.KEY2).get(4).toString());

			updateBuilder.update();

			return true;
		}
		return false;
	}

	@Override
	public boolean insertNewEsame(EsameEntity e) throws SQLException {

		if (this.databaseHelper.isOpen() && e != null) {

			Dao<EsameEntity, Integer> dao = databaseHelper.getEsameEntityDao();
			int queryResult = dao.create(e);

			if (queryResult != 1) {
				Log.v(TAG, "Fallito");
				return false;

			} else {

				Log.v(TAG, "Esame aggiunto con successo");
				return true;
			}

		}
		return false;

	}

	public String getMedia() throws SQLException {

		int tot = 0;
		String media = null;

		List<EsameEntity> esami = this.getAllEsami();
		if (esami != null) {
			for (EsameEntity e : esami) {
				int v = Integer.parseInt(e.getVoto());

				tot = tot + v;
				media = Double.toString(tot / esami.size());

			}
		}

		return media;

	}

	@Override
	public String getCrediti() throws SQLException {
		int tot = 0;
		String crediti = null;
		List<EsameEntity> esami = this.getAllEsami();

		if (esami != null) {
			for (EsameEntity e : esami) {
				int count = Integer.parseInt(e.getCredAcq());
				tot = tot + count;
				crediti = Integer.toString(tot);
			}

		}
		return crediti;

	}

	@Override
	public String getSuperati() throws SQLException {
		int tot = 0;
		String superati = null;
		List<EsameEntity> esami = this.getAllEsami();

		if (esami != null) {
			for (EsameEntity e : esami) {
				if (e.Superato()) {
					tot = tot + 1;
					superati = Integer.toString(tot);
				}
			}

		}
		return superati;
	}

	@Override
	public String getFalliti() throws SQLException {
		int tot = 0;
		String falliti = null;
		List<EsameEntity> esami = this.getAllEsami();

		if (esami != null) {
			for (EsameEntity e : esami) {
				if (!e.Superato()) {
					tot = tot + 1;
					falliti = Integer.toString(tot);
				} else if (tot <= 0) {

					falliti = Integer.toString(0);

				}
			}

		}
		return falliti;

	}

}
