package it.nic.uniapp;



import it.nic.uniapp.db.EsameEntity;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;




public class PopUpWindow extends PopupWindow {
	
	private Button btnClose = null;
	private ListView lista = null;
	private List<EsameEntity>esami = null;
	
	public PopUpWindow(Context context, Activity activity) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.popup_window, (ViewGroup) activity.findViewById(R.id.popup__element));

		Display display = activity.getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		this.setHeight(height - height / 3);
		this.setWidth(width - width / 4);
		
		this.showAtLocation(layout, Gravity.CENTER, 0, 0);

		this.btnClose = (Button)activity.findViewById(R.id.popup__btnclose);
		
		this.btnClose.setOnClickListener(btn_OnClickListener);
		
	}

	private void OnClick(View view) {
		String tag = view.getTag() != null ? (String) view.getTag() : null;

		if (tag != null && tag.equals("popup__btnclose")) {
			this.dismiss();
		}
		
	}
		
		

	private OnClickListener btn_OnClickListener = new OnClickListener() {
		public void onClick(View view) {
			OnClick(view);
		}

	};

}
