package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.CustomAlertDialog;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class FULLSCR_DoimkFragment extends BaseFragment implements OnClickListener {
	public LinearLayout mChangePassBtn, mChangePassPanel;
	public ImageView mCancelBtn;
	public EditText mPassword;
	public EditText mNewPassword;
	public EditText mNewPasswordCf;
	public Context mContext;
	public ProgressDialog pDialog;
	public FULLSCR_DoimkFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.changepass_main_layout, null);
		mCancelBtn = (ImageView) v.findViewById(R.id.changePassPanel_cancelBtn);
		mChangePassPanel = (LinearLayout) v.findViewById(R.id.changePassPanel);
		mChangePassBtn = (LinearLayout) v.findViewById(R.id.changePassPanel_changePassbtn);
		mPassword = (EditText) v.findViewById(R.id.changePassPanel_pass);
		mNewPassword = (EditText) v.findViewById(R.id.changePassPanel_newpass);
		mNewPasswordCf = (EditText) v.findViewById(R.id.changePassPanel_newpasscf);
		changeSigninBtnState();
		mCancelBtn.setOnClickListener(this);
		mChangePassBtn.setOnClickListener(this);
		mPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changeSigninBtnState();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changeSigninBtnState();
			}
		});
		mNewPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changeSigninBtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changeSigninBtnState();
			}
		});
		mNewPasswordCf.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changeSigninBtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changeSigninBtnState();
			}
		});
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.DOIMKFRAGMENT) {
			return;
		}
		((BaseActivity)getRealActivity()).hideActionBar();
	}
	
	@SuppressWarnings("deprecation")
	public boolean changeSigninBtnState() {
		int i1 = mPassword.getText().toString().length();
		int i2 = mNewPassword.getText().toString().length();
		int i3 = mNewPasswordCf.getText().toString().length();
		if (i1 == 0 || i2 == 0 || i3 == 0) {
			mChangePassBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_btn_item_shape2));
			mChangePassBtn.setClickable(false);
			return false;
		} else {
			mChangePassBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_btn_item_shape));
			mChangePassBtn.setClickable(true);
			return true;
		}
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.changePassPanel_cancelBtn:
			((BaseActivity)getRealActivity()).onBackPressed();
			Log.d("signin", "on back press");
			break;
		case R.id.changePassPanel_changePassbtn:
			if (changeSigninBtnState() == true) {
				if (mNewPassword.getText().toString().length() < 4) {
					final CustomAlertDialog aDialog = new CustomAlertDialog(mContext,
							"Đổi mật khẩu", "Mật khẩu phải có tối thiểu 4 ký tự !", "Ok");
					aDialog.setCanceledOnTouchOutside(true);
					aDialog.show();
					aDialog.btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							aDialog.hide();

						}
					});
				}else if(!mNewPassword.getText().toString().equals(mNewPasswordCf.getText().toString())){
					final CustomAlertDialog aDialog = new CustomAlertDialog(mContext,
							"Đổi mật khẩu", "Xác nhận mật khẩu không trùng khớp !", "Ok");
					aDialog.setCanceledOnTouchOutside(true);
					aDialog.show();
					aDialog.btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							aDialog.hide();

						}
					});
				}else{
					netAsync();
				}				
				Log.d("signin", "signin press");
			}
			break;
		
		default:
			break;
		}

	}



	private class ProcessListContents extends AsyncTask<String, String, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected Boolean doInBackground(String... args) {
			String pass = mPassword.getText().toString().trim();
			String newPass = mNewPassword.getText().toString().trim();
			String userId = mSharePreferences.getInt("CURRENT_USER_ID", 21061992)+""; //dummy
			return new JSONParser(getRealActivity()).changeUserPassword(userId, pass, newPass);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			
			
			if (!result) {
				final CustomAlertDialog aDialog = new CustomAlertDialog(mContext,
						"Đổi mật khẩu", "Mật khẩu cũ không trùng khớp !", "Ok");
				aDialog.setCanceledOnTouchOutside(true);
				aDialog.show();
				aDialog.btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						aDialog.hide();

					}
				});
			} else {
				/*Intent i = new Intent(mContext,MainActivity.class);
				Bundle b = new Bundle();
				b.putInt("callback_act", Constant.TK_HOSO_CODE); 
				i.putExtras(b);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				finish();*/
				Toast.makeText(getRealActivity(), "Đổi mật khẩu thành công !", Toast.LENGTH_LONG).show();
				MAINVIEW_MainFragment mf = new MAINVIEW_MainFragment(Constant.TK_HOSO_CODE);
				((MainActivity)getRealActivity()).switchContentWithoutBackstack(mf);
			}
		}
	}

	@Override
	public void netAsync() {
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
									progressDialog.dismiss();
									super.onPostExecute(list);
									
								}
							}.execute();
						}
					}
				}).execute();
	}

	


}
