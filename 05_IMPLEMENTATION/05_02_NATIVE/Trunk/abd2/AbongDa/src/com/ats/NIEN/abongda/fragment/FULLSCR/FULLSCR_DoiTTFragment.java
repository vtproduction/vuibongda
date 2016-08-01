package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class FULLSCR_DoiTTFragment extends BaseFragment implements OnClickListener {
	public ProgressDialog pDialog;
	public LinearLayout mainPanel, changeBtn;
	public EditText mName, mAddress, mPhone, mEmail;
	public String Name, Address, Phone, Email;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.doitt_main_layout, container, false);
		mainPanel = (LinearLayout) v.findViewById(R.id.doitt_mainpanel);
		changeBtn = (LinearLayout) v.findViewById(R.id.doitt_btn);
		mName = (EditText) v.findViewById(R.id.doitt_ten);
		mAddress = (EditText) v.findViewById(R.id.doitt_diachi);
		mPhone = (EditText) v.findViewById(R.id.doitt_sdt);
		mEmail = (EditText) v.findViewById(R.id.doitt_email);
		changeBtn.setOnClickListener(this);
		((BaseActivity) getRealActivity()).changeTitle("Cập nhật thông tin");
		((BaseActivity) getRealActivity()).changeRightImageToBack();

		return v;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.DOITTFRAGMENT) {
			return;
		}
		mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String UserData = mSharePreferences.getString("CURRENT_USER_DATA", "");
		Log.d("tag8", UserData);
		Pattern pattern = Pattern.compile("\\|\\|\\|");
		String[] UserDataArray = pattern.split(UserData);
		for (int i = 0; i < UserDataArray.length; i++) {
			Log.d("tag8", UserDataArray[i]);
		}
		mName.setText(UserDataArray[0]);
		mAddress.setText(UserDataArray[1]);
		mPhone.setText(UserDataArray[2]);
		mEmail.setText(UserDataArray[3]);
		// NetAsync(getView());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.doitt_btn) {
			Name = mName.getText().toString();
			Address = mAddress.getText().toString();
			Phone = mPhone.getText().toString();
			Email = mEmail.getText().toString();
			netAsync();
			/*
			 * if (!validatePhone(Phone)) { final MyAlertDialog aDialog = new
			 * MyAlertDialog(getRealActivity(), "Lỗi",
			 * "Số điện thoại không đúng !", "Ok");
			 * aDialog.setCanceledOnTouchOutside(true); aDialog.show();
			 * aDialog.btn.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub aDialog.hide();
			 * 
			 * } }); }else if (!validatePhone(Email)) { final MyAlertDialog
			 * aDialog = new MyAlertDialog(getRealActivity(), "Lỗi",
			 * "Email không đúng !", "Ok");
			 * aDialog.setCanceledOnTouchOutside(true); aDialog.show();
			 * aDialog.btn.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub aDialog.hide();
			 * 
			 * } }); }else{ NetAsync(getView()); }
			 */
		}

	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}

	

	private class ProcessListContents extends
			AsyncTask<String, String, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			mainPanel.removeAllViews();
		}

		@Override
		protected Boolean doInBackground(String... args) {
			String currentUserId = mSharePreferences.getInt("CURRENT_USER_ID",
					21061992) + ""; // dummy
			return new JSONParser(getRealActivity()).changeUserInfo(
					currentUserId, Name, Address, Phone, Email);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			
			getRealActivity().onBackPressed();
			
		}
	}

	@Override
	public void netAsync() {
		super.netAsync();
		new InternetChecking(new WeakReference<Context>(getRealActivity()),
				new InternetCheckingCallback() {

					@Override
					public void onCheckComplete(boolean result,
							final Dialog progressDialog) {
						// TODO Auto-generated method stub
						if (!result) {
							progressDialog.dismiss();
						} else {
							new ProcessListContents() {
								@Override
								protected void onPostExecute(
										Boolean list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}

	class MyAlertDialog extends AlertDialog {

		public String header, content, btnText;
		public LinearLayout btn;
		public LayoutInflater inflater;
		public Context context;

		public MyAlertDialog(Context context, String header, String content,
				String btnText) {
			super(context);
			this.context = context;
			this.header = header;
			this.content = content;
			this.btnText = btnText;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public void show() {

			super.show();
			View v = inflater.inflate(R.layout.signin_dialog, null);

			((TextView) v.findViewById(R.id.signinDialog_header))
					.setText(header);
			((TextView) v.findViewById(R.id.signinDialog_content))
					.setText(content);
			((TextView) v.findViewById(R.id.signinDialog_btnText))
					.setText(btnText);
			btn = (LinearLayout) v.findViewById(R.id.signinDialog_btn);
			setContentView(v);

		}

	}

	public boolean validateEmail(final String hex) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public boolean validatePhone(final String hex) {
		String PHONE_PATTERN = "[0-9]+";
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
	}

}
