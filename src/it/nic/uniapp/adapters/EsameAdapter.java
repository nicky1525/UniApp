package it.nic.uniapp.adapters;

import it.nic.uniapp.cmp.EsameRowControl;
import it.nic.uniapp.db.EsameEntity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class EsameAdapter extends BaseAdapter {

	private Context context = null;
	private ArrayList<EsameEntity> esami = null;

	public EsameAdapter(Context context, List<EsameEntity> objects) {
		super();

		this.context = context;
		this.esami = new ArrayList(objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (esami != null && context != null) {

			View itemView;

			if (convertView == null) {

				itemView = new EsameRowControl(this.context);

			} else {
				itemView = (EsameRowControl) convertView;
			}

			EsameEntity currentRow = esami.get(position);

			((EsameRowControl) itemView).LoadEntity(currentRow);

			return itemView;
		}
		
		return null;
	}

	@Override
	public Object getItem(int position) {

		if (this.esami != null) {
			return this.esami.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		if (this.esami != null) {
			return this.esami.size();
		}

		return 0;
	}
}