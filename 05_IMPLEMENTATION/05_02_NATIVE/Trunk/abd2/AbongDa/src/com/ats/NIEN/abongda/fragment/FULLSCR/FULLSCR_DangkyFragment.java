package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.CustomAlertDialog;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.model.UserModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class FULLSCR_DangkyFragment extends BaseFragment implements
		OnClickListener {
	public LinearLayout mSignupPanel, mSignupBtn;
	public ImageView mCancelBtn;
	public EditText mUsername;
	public EditText mPassword;
	public EditText mPasswordCf;
	public ProgressDialog pDialog;
	public boolean isGoFromMatchDetail = false;
	public FULLSCR_DangkyFragment() {
		// TODO Auto-generated constructor stub
	}

	public FULLSCR_DangkyFragment(boolean isGoFromMatchDetail) {
		// TODO Auto-generated constructor stub
		this.isGoFromMatchDetail = true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.signup_main_layout, null);
		mCancelBtn = (ImageView) v.findViewById(R.id.SignupPanel_cancelBtn);
		mSignupPanel = (LinearLayout) v.findViewById(R.id.SignupPanel);
		mSignupBtn = (LinearLayout) v.findViewById(R.id.SignupPanel_signupbtn);
		mUsername = (EditText) v.findViewById(R.id.SignupPanel_username);
		mPassword = (EditText) v.findViewById(R.id.SignupPanel_password);
		mPasswordCf = (EditText) v.findViewById(R.id.SignupPanel_passwordcmf);
		changeSignupBtnState();
		mCancelBtn.setOnClickListener(this);
		mSignupBtn.setOnClickListener(this);
		mUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changeSignupBtnState();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changeSignupBtnState();
			}
		});
		mPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changeSignupBtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changeSignupBtnState();
			}
		});
		mPasswordCf.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changeSignupBtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changeSignupBtnState();
			}
		});
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.DANGKYFRAGMENT) {
			return;
		}
		((BaseActivity) getRealActivity()).hideActionBar();
	}

	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
	}

	@SuppressWarnings("deprecation")
	public boolean changeSignupBtnState() {
		int i1 = mUsername.getText().toString().length();
		int i2 = mPassword.getText().toString().length();
		int i3 = mPasswordCf.getText().toString().length();
		if (i1 == 0 || i2 == 0 || i3 == 0) {
			mSignupBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_btn_item_shape2));
			mSignupBtn.setClickable(false);
			return false;
		} else {
			mSignupBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_btn_item_shape));
			mSignupBtn.setClickable(true);
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.SignupPanel_cancelBtn:
			((BaseActivity) getRealActivity()).onBackPressed();
			Log.d("Signup", "on back press");
			break;
		case R.id.SignupPanel_signupbtn:
			if (changeSignupBtnState() == true) {
				if (mPassword.getText().length() < 3) {
					new CustomAlertDialog(getRealActivity(), "Lỗi", "Mật khẩu cần tối thiểu 3 ký tự !", "OK").show();
				}else if (!mPassword.getText().toString().equals(mPasswordCf.getText().toString())) {
					new CustomAlertDialog(getRealActivity(), "Lỗi", "Xác nhận mật khẩu không chính xác !", "OK").show();
				}else{
					netAsync();
				}
				Log.d("Signup", "Signup press");
			}

			break;
		default:
			break;
		}

	}

	@Override
	public void netAsync() {
		// TODO Auto-generated method stub
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
										UserModel list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}

	private class ProcessListContents extends AsyncTask<String, String, UserModel> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected UserModel doInBackground(String... args) {
			String username = (mUsername.getText().toString().trim().length() == 0) ? "[foo]"
					: mUsername.getText().toString().trim();
			UserModel um = (new JSONParser(getRealActivity())).performSignUp(username, mPassword.getText().toString().trim());
			return um;
		}

		@Override
		protected void onPostExecute(UserModel um) {

			Log.d("signup: ", um.getId()+"");
			if (um.getId() == 0) {
				final CustomAlertDialog aDialog = new CustomAlertDialog(
						getRealActivity(), "Đăng ký", "Tên đăng nhập đã được sử dụng !",
						"Ok");
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
				SharedPreferences.Editor editor = mSharePreferences.edit();
				editor.putInt("CURRENT_USER_ID", um.getId());
				editor.putString("CURRENT_USER_NAME", mUsername.getText()
						.toString().trim());
				editor.commit();
				if (um.getAccount() > 0) {
					Toast.makeText(getRealActivity(), "Được tặng " + um.getAccount() + " xu cho lần đăng ký đầu tiên !", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getRealActivity(), "Đăng ký thành công !", Toast.LENGTH_LONG).show();
				}
				if (isGoFromMatchDetail) {
					((BaseActivity)getRealActivity()).onBackPressed();
				}
				else{
					MAINVIEW_MainFragment fragment = new MAINVIEW_MainFragment(
							Constant.TK_HOSO_CODE, new ArrayList<Object>());
					((MainActivity) getRealActivity())
							.switchContentWithoutBackstack(fragment);
				}
				/**/
			}
		}
	}

}
