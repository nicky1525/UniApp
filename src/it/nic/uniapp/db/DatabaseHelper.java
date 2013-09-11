package it.nic.uniapp.db;

import it.nic.uniapp.core.Constants;

import java.sql.SQLException;


import it.nic.uniapp.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

	private static final int DB_VERSION = 1;
	private Dao<EsameEntity, Integer> esameEntityRuntimeDao = null;
	
	public DatabaseHelper(Context context)
	{
		super(context, Constants.DB_NAME, null, DB_VERSION,R.raw.esami_config );
	}
	
	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
	{}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{}
	
	public Dao<EsameEntity, Integer> getEsameEntityDao() throws SQLException
	{
		if (this.esameEntityRuntimeDao == null)
		{
			this.esameEntityRuntimeDao = getDao(EsameEntity.class);
		}

		return this.esameEntityRuntimeDao;
	}
	
	@Override
	public void close()
	{
		if (this.isOpen())
		{
			super.close();

			try
			{
				if (esameEntityRuntimeDao != null)
				{
					esameEntityRuntimeDao.closeLastIterator();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			esameEntityRuntimeDao = null;
		}
	}
}
