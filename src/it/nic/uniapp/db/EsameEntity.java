package it.nic.uniapp.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tabella1")
public class EsameEntity {
	public static final String ID = "ID";
	public static final String DATA = "Data";
	public static final String NOME = "Nome Esame";
	public static final String TOTCRED = "TOT Crediti";
	public static final String VOTO = "Voto";
	public static final String CREDACQ = "Crediti Acquisiti";
	
	public EsameEntity(){
		
	}
	
	public EsameEntity(String data, String nome, String totCred, String voto, String credAcq){
		this.data = data;
		this.nome = nome;
		this.totCred = totCred;
		this.voto = voto;
		this.credAcq = credAcq;
	}

//	public EsameEntity(Parcel parcel) {
//		this.data = parcel.readString();
//		this.nome = parcel.readString();
//		this.totCred = parcel.readString();
//		this.voto = parcel.readString();
//		this.credAcq = parcel.readString();
//	}

	// PRIVATE MEMBERS
	@DatabaseField(generatedId = true, columnName = ID)
	private int id;

	@DatabaseField(columnName = DATA, useGetSet = true, canBeNull = false)
	private String data = null;

	@DatabaseField(columnName = NOME, useGetSet = true, canBeNull = false)
	private String nome = null;

	@DatabaseField(columnName = TOTCRED, useGetSet = true, canBeNull = false)
	private String totCred = null;

	@DatabaseField(columnName = VOTO, useGetSet = true, canBeNull = false)
	private String voto = null;

	@DatabaseField(columnName = CREDACQ, useGetSet = true, canBeNull = false)
	private String credAcq = null;

	// PUBLIC MEMBERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data.trim();
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNome() {
		return nome.trim();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTotCred() {
		return totCred.trim();
	}

	public void setTotCred(String totCred) {
		this.totCred = totCred;
	}

	public String getVoto() {
		return voto.trim();
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public String getCredAcq() {
		return credAcq.trim();
	}

	public void setCredAcq(String credAcq) {
		this.credAcq = credAcq;
	}

	public boolean Superato() {

		boolean superato = false;
		if (Integer.parseInt(this.getVoto()) >= 18) {
			superato = true;
		}
		return superato;
	}

	@Override
	public String toString() {
		return "EsameEntity: Nome: " + getNome() + "ID: " + getId();
	}
//
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		dest.writeInt(id);
//		dest.writeString(data);
//		dest.writeString(nome);
//		dest.writeString(totCred);
//		dest.writeString(voto);
//		dest.writeString(credAcq);
//
//	}
//
//	public final static Parcelable.Creator CREATOR = new Parcelable.Creator<EsameEntity>() {
//		@Override
//		public EsameEntity createFromParcel(Parcel source) {
//			return new EsameEntity(source);
//		}
//
//		@Override
//		public EsameEntity[] newArray(int size) {
//
//			return new EsameEntity[size];
//		}
//
//	};

}