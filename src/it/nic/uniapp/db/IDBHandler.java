package it.nic.uniapp.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

public interface IDBHandler {

	// REQUIRED METHODS
	public void openHandler(Context context);
	public void closeHandler();
	public void copyDataBase(Context context) throws IOException;
	
	// OTHER METHODS
	public List<EsameEntity> getAllEsami() throws SQLException;
	public List<EsameEntity> getEsameByDate(String date)throws SQLException;
	public boolean deleteEsameById(int id)throws SQLException;
	public void deleteAllEsami() throws SQLException;
	public boolean updateEsameById(Bundle bundle)throws SQLException;
	public boolean insertNewEsame(EsameEntity e)throws SQLException;
	public String getMedia() throws SQLException;
	public String getCrediti() throws SQLException;
	public String getSuperati()throws SQLException;
	public String getFalliti()throws SQLException;
	
}
