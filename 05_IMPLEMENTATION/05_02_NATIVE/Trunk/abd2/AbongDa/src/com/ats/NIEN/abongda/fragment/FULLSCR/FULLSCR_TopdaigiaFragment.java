package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class FULLSCR_TopdaigiaFragment extends BaseFragment {
	public ProgressDialog pDialog;
	public LinearLayout mainPanel;


	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.topdaigia_main_layout, container,
				false);
		mainPanel = (LinearLayout) v.findViewById(R.id.topdaigiaPage_main_layout);
		((BaseActivity)getRealActivity()).changeTitle("Top đại gia");
		((BaseActivity)getRealActivity()).hideRightImage();
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
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TOPDAIGIA_CODE) {
			return;
		}
		NetAsync(getView());
	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}



	private class ProcessListContents extends
			AsyncTask<String, String, List<UserModel>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			mainPanel.removeAllViews();
		}

		@Override
		protected List<UserModel> doInBackground(String... args) {
			List<UserModel> list = new ArrayList<UserModel>();
			// list = new JSONParser(mActivity).parseTintuc("0");
			list = new JSONParser(getRealActivity()).parseTopdaigia(mSharePreferences.getString("CURRENT_USER_NAME", ""));

			return list;
		}

		@Override
		protected void onPostExecute(final List<UserModel> list) {
			if (isAdded()) {
				boolean isPrShowing = true;
				
				mainPanel.removeAllViews();
				LayoutInflater inflater = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				for (int i = 0; i < list.size(); i++) {
					LinearLayout item = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_1, null);
					UserModel model = list.get(i);
					ImageView logo = (ImageView) item.findViewById(R.id.topdaigiaPage_img);
					
					switch (i) {
					case 0:
						logo.setImageResource(R.drawable.sortnum1);
						break;
					case 1:
					case 2:
						logo.setImageResource(R.drawable.sortnum2);
						break;
					default:
						logo.setImageResource(R.drawable.sortnum4);
						break;
					}
					((TextView) item.findViewById(R.id.topdaigiaPage_stt)).setText(""+(i+1));
					((TextView) item.findViewById(R.id.topdaigiaPage_name)).setText(model.getName());
					((TextView) item.findViewById(R.id.topdaigiaPage_sotien)).setText(getRoundOffValue(model.getAccount()));
					if (mSharePreferences.getString("CURRENT_USER_NAME", "").equals("")) {
						if (i < list.size() - 1) {
							mainPanel.addView(item);
						}
					}else{
						if (i < list.size()-1) {
							mainPanel.addView(item);
						}else{
							LinearLayout gap = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_2, null);
							mainPanel.addView(gap);
							LinearLayout lastItem = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_3, null);
							((TextView) lastItem.findViewById(R.id.topdaigiaPage_stt_2)).setText(""+model.getRank());
							((TextView) lastItem.findViewById(R.id.topdaigiaPage_name_2)).setText(model.getName());
							((TextView) lastItem.findViewById(R.id.topdaigiaPage_xu_2)).setText(""+model.getAccount());
							mainPanel.addView(lastItem);
						}
					}
					
					if (i == list.size() - 1 || list.size() == 0) {
						//pDialog.dismiss();
						isPrShowing = false;
					}
				}
				if (isPrShowing) {
					//pDialog.dismiss();
				}
				
			}

		}
	}

	public void NetAsync(View view) {
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
										List<UserModel> list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}

	public static String getRoundOffValue(double value){

	    DecimalFormat df = new DecimalFormat("##,###,###,###,###");
	    return df.format(value);
	}
}
