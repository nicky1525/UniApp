package it.nic.uniapp.cmp;

import it.nic.uniapp.R;
import it.nic.uniapp.db.EsameEntity;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EsameRowControl extends LinearLayout {

	private Context context = null;

	
	private TextView txtData = null;
	private TextView txtNome = null;
	private TextView txtTotCrediti = null;
	private TextView txtVoto = null;
	private TextView txtCredAcq = null;
	
	private EsameEntity entity = null;

	public EsameRowControl(Context context) {
		super(context);

		this.context = context;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		inflater.inflate(R.layout.esame_row_control, this, true);

		this.txtData = (TextView) this.findViewById(R.id.row_esame__txtDATA);
		this.txtNome = (TextView) this.findViewById(R.id.row_esame__txtNOME);
		this.txtTotCrediti = (TextView) this.findViewById(R.id.row_esame__txtTOTCREDITI);
		this.txtVoto = (TextView) this.findViewById(R.id.row_esame__txtVOTO);
		this.txtCredAcq = (TextView) this.findViewById(R.id.row_esame__txtCREDITI);
		

	}

	public void LoadEntity(EsameEntity esame) {

		this.txtData.setText(esame.getData());
		this.txtNome.setText(esame.getNome());
		this.txtTotCrediti.setText(esame.getTotCred());
		this.txtVoto.setText(esame.getVoto());
		this.txtCredAcq.setText(esame.getCredAcq());

	}

	public EsameEntity getEntity() {
		return entity;
	}
}
