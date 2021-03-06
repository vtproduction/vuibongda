package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.CustomAlertDialog;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class FULLSCR_DangNhapFragment extends BaseFragment implements OnClickListener{
	public LinearLayout mSigninPanel, mSigninBtn, mSignupBtn;
	public ImageView mCancelBtn;
	public EditText mUsername;
	public EditText mPassword;
	public ProgressDialog pDialog;
	public int nextActionCode;
	public List<Object> argsList;
	public FULLSCR_DangNhapFragment(int nextActionCode, List<Object> argsList) {
		// TODO Auto-generated constructor stub
		super();
		this.nextActionCode = nextActionCode;
		this.argsList = new ArrayList<Object>();
		if (argsList != null) {
			this.argsList.addAll(argsList);
		}
	}
	
	

	public FULLSCR_DangNhapFragment() {
		super();
		// TODO Auto-generated constructor stub
		this.nextActionCode = 0;
		this.argsList = null;
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		int mCurrentUserId = mSharePreferences.getInt("CURRENT_USER_ID", 0);
		if (mCurrentUserId != 0) {
			if (nextActionCode == Constant.MATCHDETAIL_CODE) {
				nextActionCode = Constant.DATCUOCFRAGMENT;
			}
			MAINVIEW_MainFragment fragment = new MAINVIEW_MainFragment(
					nextActionCode, argsList);
			((MainActivity) getRealActivity()).switchContentWithoutBackstack(fragment);
		}
		((BaseActivity)getRealActivity()).hideActionBar();
		View v = inflater.inflate(R.layout.signin_main_layout, null);
		mCancelBtn = (ImageView) v.findViewById(R.id.SigninPanel_cancelBtn);
		mSigninPanel = (LinearLayout) v.findViewById(R.id.SigninPanel);
		mSigninBtn = (LinearLayout) v.findViewById(R.id.SigninPanel_signinbtn);
		mSignupBtn = (LinearLayout) v.findViewById(R.id.SigninPanel_signupbtn);
		mUsername = (EditText) v.findViewById(R.id.SigninPanel_username);
		mPassword = (EditText) v.findViewById(R.id.SigninPanel_password);

		changeSigninBtnState();
		mCancelBtn.setOnClickListener(this);
		mSigninBtn.setOnClickListener(this); 
		mSignupBtn.setOnClickListener(this);
		mUsername.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
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
		mPassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
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

	@SuppressWarnings("deprecation")
	public boolean changeSigninBtnState(){
		int i1 = mUsername.getText().toString().length();
		int i2 = mPassword.getText().toString().length();
		if (i1 == 0 || i2 == 0) {
			mSigninBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_btn_item_shape2));
			mSigninBtn.setClickable(false);
			return false;
		}else{
			mSigninBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_btn_item_shape));
			mSigninBtn.setClickable(true);
			return true;
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.DANGNHAPFRAGMENT) {
			return;
		}
		((BaseActivity)getRealActivity()).hideActionBar();
		
	}

	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
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
										String list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.SigninPanel_cancelBtn:
			((BaseActivity)getRealActivity()).onBackPressed();
			/*if (nextActionCode != Constant.TK_HOSO_CODE) {
				
			}else{
				MAINVIEW_MainFragment fragment = new MAINVIEW_MainFragment(
						Constant.LIVESCORE_CODE, argsList);
				((MainActivity) getRealActivity()).switchContentWithoutBackstack(fragment);
			}*/
			break;
		case R.id.SigninPanel_signinbtn:
			if (changeSigninBtnState() == true) {
				netAsync();
				Log.d("signin", "signin press");
			}
			break;
		case R.id.SigninPanel_signupbtn:
			if (nextActionCode == Constant.MATCHDETAIL_CODE) {
				MAINVIEW_MainFragment mf = new MAINVIEW_MainFragment(Constant.DANGKYFRAGMENT, new ArrayList<Object>(){{
					add(true);
				}});
				((MainActivity)getRealActivity()).switchContent(mf);
			}else{
				MAINVIEW_MainFragment mf = new MAINVIEW_MainFragment(Constant.DANGKYFRAGMENT, new ArrayList<Object>(){{
					add(false);
				}});
				((MainActivity)getRealActivity()).switchContent(mf);
			}
					
			break;
		default:
			break;
		}
		
	}
	
	private class ProcessListContents extends
			AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected String doInBackground(String... args) {
			String username = (mUsername.getText().toString().trim().length() == 0)? "[foo]":mUsername.getText().toString().trim();
			int userId = (new JSONParser(getRealActivity())).performSignin("123456", username, mPassword.getText().toString().trim());
			return userId+"";
		}

		@Override
		protected void onPostExecute(String userId) {

			Log.d("signin", userId);
			if(Integer.parseInt(userId)==0){
				final CustomAlertDialog aDialog = new CustomAlertDialog(getRealActivity(), "Đăng nhập",
						"Đăng nhập thất bại !", "Ok");
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
				SharedPreferences.Editor editor = mSharePreferences.edit();
				
				if (nextActionCode == Constant.MATCHDETAIL_CODE) {
					nextActionCode = Constant.DATCUOCFRAGMENT;
				}
				editor.putInt("CURRENT_USER_ID", Integer.parseInt(userId));
				editor.putString("CURRENT_USER_NAME", mUsername.getText().toString().trim());
				editor.putInt("NEXT_ACTION_CODE", nextActionCode);
				editor.commit();
				/*MAINVIEW_MainFragment fragment = new MAINVIEW_MainFragment(
						nextActionCode, argsList);
				((MainActivity) getRealActivity()).switchContentWithoutBackstack(fragment);*/
				if (nextActionCode == Constant.TK_HOSO_CODE) {
					((BaseActivity)getRealActivity()).onBackPressed();
					MAINVIEW_MainFragment mf = (MAINVIEW_MainFragment) ((BaseActivity)getRealActivity()).getSupportFragmentManager().findFragmentById(R.id.content_frame);
					mf.onTabChanged("Tài khoản");
				}else{
					MAINVIEW_MainFragment fragment = new MAINVIEW_MainFragment(
							nextActionCode, argsList);
					((MainActivity) getRealActivity()).switchContentWithoutBackstack(fragment);
				}
				
				
			}
		}
	}


	
}
