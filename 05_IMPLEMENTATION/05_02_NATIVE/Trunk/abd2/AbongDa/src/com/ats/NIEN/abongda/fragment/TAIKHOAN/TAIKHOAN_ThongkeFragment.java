package com.ats.NIEN.abongda.fragment.TAIKHOAN;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.model.UserModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class TAIKHOAN_ThongkeFragment extends BaseFragment implements OnClickListener {
	public LinearLayout btnLeft, btnRight;
	public View mainView;
	public int currentAccountId;
	public ProgressDialog pDialog;
	public UserModel mModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.taikhoanthongke_main_layout,
				container, false);
		mainView = v;
		btnLeft = (LinearLayout) mainView.findViewById(R.id.tktk_btnleft);
		btnRight = (LinearLayout) mainView.findViewById(R.id.tktk_btnright);
		mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		return v;

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TK_THONGKE_CODE) {
			return;
		}
		((BaseActivity) getRealActivity()).hideRightImage();
		netAsync();
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}

	private class ProcessListContents extends
			AsyncTask<String, String, UserModel> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected UserModel doInBackground(String... args) {
			currentAccountId = mSharePreferences.getInt("CURRENT_USER_ID", 0);
			UserModel model = new JSONParser(getRealActivity())
					.parseBetStatic(currentAccountId + "");
			return model;
		}

		@Override
		protected void onPostExecute(final UserModel model) {
			mModel = model;
			((TextView) mainView.findViewById(R.id.tktk_text1left))
					.setText("Số cửa thắng");
			((TextView) mainView.findViewById(R.id.tktk_text2left))
					.setText("Số cửa đặt");
			((TextView) mainView.findViewById(R.id.tktk_text3left))
					.setText("Hiệu suất");

			((TextView) mainView.findViewById(R.id.tktk_text1right)).setText(""
					+ model.getWonMatchScore());
			((TextView) mainView.findViewById(R.id.tktk_text2right)).setText(""
					+ model.getTotalMatchScore());
			if (model.getTotalMatchScore() > 0) {
				float percent = (model.getWonMatchScore() * 100.0f)
						/ model.getTotalMatchScore();
				((TextView) mainView.findViewById(R.id.tktk_text3right))
						.setText(Math.round(percent) + " %");
			} else {
				((TextView) mainView.findViewById(R.id.tktk_text3right))
						.setText("");
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

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tktk_btnleft:
			((View) mainView.findViewById(R.id.tktk_btn))
					.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.greenbtn));
			((TextView) mainView.findViewById(R.id.tktk_btnlefttext))
					.setTextColor(getResources().getColor(R.color.white_color));
			((TextView) mainView.findViewById(R.id.tktk_btnrighttext))
					.setTextColor(getResources().getColor(
							R.color.base_app_color));
			((TextView) mainView.findViewById(R.id.tktk_text1left))
					.setText("Số cửa thắng");
			((TextView) mainView.findViewById(R.id.tktk_text2left))
					.setText("Số cửa đặt");
			((TextView) mainView.findViewById(R.id.tktk_text3left))
					.setText("Hiệu suất");

			((TextView) mainView.findViewById(R.id.tktk_text1right)).setText(""
					+ mModel.getWonMatchScore());
			((TextView) mainView.findViewById(R.id.tktk_text2right)).setText(""
					+ mModel.getTotalMatchScore());
			if (mModel.getTotalMatchScore() > 0) {
				float percent = (mModel.getWonMatchScore() * 100.0f)
						/ mModel.getTotalMatchScore();
				((TextView) mainView.findViewById(R.id.tktk_text3right))
						.setText(Math.round(percent) + " %");
			} else {
				((TextView) mainView.findViewById(R.id.tktk_text3right))
						.setText("");
			}
			break;
		case R.id.tktk_btnright:
			((View) mainView.findViewById(R.id.tktk_btn))
					.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.greenbtnrv));
			((TextView) mainView.findViewById(R.id.tktk_btnrighttext))
					.setTextColor(getResources().getColor(R.color.white_color));
			((TextView) mainView.findViewById(R.id.tktk_btnlefttext))
					.setTextColor(getResources().getColor(
							R.color.base_app_color));

			((TextView) mainView.findViewById(R.id.tktk_text1left))
					.setText("Số xu thắng");
			((TextView) mainView.findViewById(R.id.tktk_text2left))
					.setText("Số xu đặt");
			((TextView) mainView.findViewById(R.id.tktk_text3left))
					.setText("Hiệu suất");

			((TextView) mainView.findViewById(R.id.tktk_text1right))
					.setText(getRoundOffValue(mModel.getWonCoinScore()));
			((TextView) mainView.findViewById(R.id.tktk_text2right))
					.setText(getRoundOffValue(mModel.getTotalCoinScore()));
			if (mModel.getTotalCoinScore() > 0) {
				float percent = (mModel.getWonCoinScore() * 100.0f)
						/ mModel.getTotalCoinScore();
				((TextView) mainView.findViewById(R.id.tktk_text3right))
						.setText(Math.round(percent) + " %");
			} else {
				((TextView) mainView.findViewById(R.id.tktk_text3right))
						.setText("");
			}
			break;
		default:
			break;
		}

	}

	public static String getRoundOffValue(double value) {

		DecimalFormat df = new DecimalFormat("##,###,###,###,###");
		return df.format(value);
	}

}
