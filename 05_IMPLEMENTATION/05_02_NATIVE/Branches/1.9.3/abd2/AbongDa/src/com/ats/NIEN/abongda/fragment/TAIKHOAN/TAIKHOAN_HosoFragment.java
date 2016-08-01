package com.ats.NIEN.abongda.fragment.TAIKHOAN;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.CustomAlertDialog;
import com.ats.NIEN.abongda.UTIL.DeviceUtil;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.model.UserModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class TAIKHOAN_HosoFragment extends BaseFragment implements
		OnClickListener {
	public int currentUserId;
	public String currentUserEmail;
	public View mainView;

	public TAIKHOAN_HosoFragment() {
		super();
		// TODO Auto-generated constructor stub

	}

	public TAIKHOAN_HosoFragment(int currentUserId) {
		super();
		this.currentUserId = currentUserId;
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
		View v = inflater.inflate(R.layout.account_page_main_layout, container,
				false);
		mainView = v;
		return v;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TK_HOSO_CODE) {
			return;
		}
		((BaseActivity) getRealActivity()).changeTitle("Hồ sơ");
		((BaseActivity) getRealActivity()).changeRightImageToSetting();
		netAsync();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}

	private class ProcessListContents extends
			AsyncTask<String, String, UserModel> {
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected UserModel doInBackground(String... args) {
			mSharePreferences = getRealActivity().getSharedPreferences(
					"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
			currentUserId = mSharePreferences.getInt("CURRENT_USER_ID", 0);
			UserModel model = new JSONParser(getRealActivity())
					.getUser(currentUserId);
			return model;
		}

		@Override
		protected void onPostExecute(final UserModel model) {

			((TextView) mainView.findViewById(R.id.TKHS_name))
					.setText(mSharePreferences.getString("CURRENT_USER_NAME",
							""));
			((TextView) mainView.findViewById(R.id.TKHS_fullname))
					.setText((model.getFullname() == null) ? "" : model
							.getFullname());
			((TextView) mainView.findViewById(R.id.TKHS_coin)).setText(model
					.getAccount() + " xu");
			((TextView) mainView.findViewById(R.id.TKHS_address))
					.setText((model.getAddress() == null) ? "" : model
							.getAddress());
			((TextView) mainView.findViewById(R.id.TKHS_phone)).setText((model
					.getPhone() == null) ? (new DeviceUtil(getRealActivity()))
					.getPhoneNumber() : model.getPhone());
			((TextView) mainView.findViewById(R.id.TKHS_email)).setText((model
					.getEmail() == null) ? "" : model.getEmail());
			currentUserEmail = (model.getEmail() == null) ? "" : model
					.getEmail();
			if (model.getIsPhoneVerified() == 0) {
				((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
						.setImageResource(R.drawable.iconxacthucsms);
			} else {
				((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
						.setImageResource(R.drawable.badge_tick);
				((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
						.setTag("ok");
				((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
						.getLayoutParams().width = ((ImageView) mainView
						.findViewById(R.id.TKHS_phone_cf)).getLayoutParams().height;

			}

			if (model.getIsEmailVerified() == 0) {
				((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
						.setImageResource(R.drawable.iconxacthucemail);
			} else {
				((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
						.setImageResource(R.drawable.badge_tick);
				((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
						.setTag("ok");
				((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
						.getLayoutParams().width = ((ImageView) mainView
						.findViewById(R.id.TKHS_email_cf)).getLayoutParams().height;

			}
			((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if ((v.getTag()==null)||(!v.getTag().equals("ok"))) {
								new PhoneAlertDialog(getRealActivity()).show();
							}

						}
					});
			
			
			((ImageView) mainView.findViewById(R.id.TKHS_phone_cf)).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if ((v.getTag()==null)||(!v.getTag().equals("ok"))) {
								openMessageApp();
							}

						}
					});
			SharedPreferences.Editor editor = mSharePreferences.edit();
			editor.putString(
					"CURRENT_USER_DATA",
					((TextView) mainView.findViewById(R.id.TKHS_fullname))
							.getText().toString()
							+ "|||"
							+ ((TextView) mainView
									.findViewById(R.id.TKHS_address)).getText()
									.toString()
							+ "|||"
							+ ((TextView) mainView
									.findViewById(R.id.TKHS_phone)).getText()
									.toString()
							+ "|||"
							+ ((TextView) mainView
									.findViewById(R.id.TKHS_email)).getText()
									.toString()
							+ "|||"
							+ ((TextView) mainView.findViewById(R.id.TKHS_coin))
									.getText().toString());
			editor.commit();

		}
	}

	private void openMessageApp() {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address", "7042");
		smsIntent.putExtra(
				"sms_body",
				"abd phone "
						+ mSharePreferences.getString("CURRENT_USER_NAME", ""));
		startActivity(smsIntent);
	}

	private class PhoneAlertDialog extends Dialog implements
			android.view.View.OnClickListener {
		public LayoutInflater inflater;
		public Context context;
		public LinearLayout btn1, btn2;
		public EditText edittext;
		public PhoneAlertDialog mCustomAlertDialog;

		public PhoneAlertDialog(Context context) {
			super(context);
			this.context = context;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mCustomAlertDialog = this;
		}

		@Override
		public void show() {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.show();
			View v = inflater.inflate(R.layout.verifyemail_dialog, null);
			btn1 = (LinearLayout) v.findViewById(R.id.verifyEmail_ok);
			btn2 = (LinearLayout) v.findViewById(R.id.verifyEmail_cancel);
			edittext = (EditText) v.findViewById(R.id.verifyEmail_text);
			edittext.setText(currentUserEmail);
			btn1.setOnClickListener(this);
			btn2.setOnClickListener(this);
			setContentView(v);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.verifyEmail_ok:
				dismiss();
				new ProcessVerifyEmail(edittext.getText().toString()).execute();
				break;
			case R.id.verifyEmail_cancel:
				dismiss();
				break;

			default:
				break;
			}
		}

		public void act() {
			mCustomAlertDialog.dismiss();
		}

	}

	private class ProcessVerifyEmail extends AsyncTask<String, String, Integer> {
		ProgressHUD mDialog;
		public String inputEmail;

		public ProcessVerifyEmail(String inputEmail) {
			super();
			this.inputEmail = inputEmail;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog = new ProgressHUD(getRealActivity()).show(
					getRealActivity(), "", true, false, null);
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new JSONParser(getRealActivity()).verifyEmail(currentUserId,
					inputEmail);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mDialog.dismiss();
			if (result == 1) {
				new CustomAlertDialog(getRealActivity(), "Thông báo",
						"Vui lòng kiểm tra hộp thư email của bạn để tiếp tục!",
						"Đóng").show();
			} else if (result == -4) {
				new CustomAlertDialog(getRealActivity(), "Thông báo",
						"Email đã được đăng ký !", "Đóng").show();
			} else {
				new CustomAlertDialog(getRealActivity(), "Thông báo",
						"Có lỗi xảy ra, vui lòng thử lại sau !", "Đóng").show();
			}

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
								protected void onPostExecute(UserModel list) {
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
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		netAsync();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
