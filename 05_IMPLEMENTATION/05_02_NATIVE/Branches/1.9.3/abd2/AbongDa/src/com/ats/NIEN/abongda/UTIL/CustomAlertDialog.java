package com.ats.NIEN.abongda.UTIL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

@SuppressLint("InflateParams")
public class CustomAlertDialog extends AlertDialog implements android.view.View.OnClickListener{
	public String header, content, btnText;
	public LinearLayout btn;
	public LayoutInflater inflater;
	public Context context;
	public CustomAlertDialog mCustomAlertDialog;

	public CustomAlertDialog(Context context, String header, String content,
			String btnText) {
		super(context);
		this.context = context;
		this.header = header;
		this.content = content;
		this.btnText = btnText;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mCustomAlertDialog = this;
	}

	@Override
	public void show() {
		super.show();
		View v = inflater.inflate(R.layout.signin_dialog, null);

		((TextView) v.findViewById(R.id.signinDialog_header)).setText(header);
		((TextView) v.findViewById(R.id.signinDialog_content)).setText(content);
		((TextView) v.findViewById(R.id.signinDialog_btnText)).setText(btnText);
		btn = (LinearLayout) v.findViewById(R.id.signinDialog_btn);
		if (btnText.equals("")) {
			btn.setVisibility(View.GONE);
			v.setOnClickListener(this);
			this.setCanceledOnTouchOutside(true);
		}
		btn.setOnClickListener(this);
		setContentView(v);
		Log.d("aDialog", header + " - " + content);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.signinDialog_btn) {
			act();
		}else if (btnText.equals("")) {
			act();
		}
	}
	
	public void act(){
		mCustomAlertDialog.dismiss();
	}

}
