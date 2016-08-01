package com.ats.NIEN.abongda.UTIL;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class ProgressHUD extends Dialog {

	AnimationDrawable mSpinner;
	boolean flag;
	public ProgressHUD(Context context) {
		super(context);
		this.flag = true;
	}
	
	public ProgressHUD(Context context, boolean flag) {
		super(context);
		this.flag = flag;
	}
	

	public ProgressHUD(Context context, int theme, boolean flag) {
		
		super(context, theme);
		this.flag = flag;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setCanceledOnTouchOutside(false);
		if (flag) {
			setContentView(R.layout.hud_progress);
			ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
			mSpinner = (AnimationDrawable) imageView.getBackground();
		}else{
			setContentView(R.layout.hud_progress_empty);
		}
		
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);
		if (flag) {
			mSpinner.start();
		}
	}

	@Override
	protected void onStop() {

		if (flag) {
			mSpinner.stop();
		}
		super.onStop();
	}

	public void setMessage(CharSequence message) {

		if (message != null && message.length() > 0) {
			findViewById(R.id.message).setVisibility(View.VISIBLE);
			TextView txt = (TextView) findViewById(R.id.message);
			txt.setText(message);
			txt.invalidate();
		}
	}

	public static ProgressHUD show(Context context, CharSequence message,
			boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		Log.d("HUD", "show mess HUD");
		ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD,true);
		dialog.setTitle("");
		dialog.setContentView(R.layout.hud_progress);
		if (message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);
		} else {
			TextView txt = (TextView) dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.2f;
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		return dialog;
	}
	
	public static ProgressHUD show(Context context,
			boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		Log.d("HUD", "show HUD");
		ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD,false);
		dialog.setTitle("");
		dialog.setContentView(R.layout.hud_progress_empty);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.2f;
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		return dialog;
	}
}
