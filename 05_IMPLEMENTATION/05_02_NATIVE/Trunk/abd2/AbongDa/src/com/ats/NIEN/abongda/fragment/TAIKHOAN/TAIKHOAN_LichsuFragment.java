package com.ats.NIEN.abongda.fragment.TAIKHOAN;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.model.TK_LichsuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class TAIKHOAN_LichsuFragment extends BaseFragment {
	public int currentUserId;
	public String currentUsername;
	public int itemPerPage;
	public List<TK_LichsuModel> list;
	public List<String> spinnerOption;
	public Spinner spinner;
	public LinearLayout mainPanel;
	

	public TAIKHOAN_LichsuFragment() {
		spinnerOption = new ArrayList<String>();
		spinnerOption.add("10 Giao dịch gần nhất");
		spinnerOption.add("20 Giao dịch gần nhất");
		spinnerOption.add("50 Giao dịch gần nhất");
		spinnerOption.add("100 Giao dịch gần nhất");
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
		View v = inflater.inflate(R.layout.taikhoanlichsu_main_layout,
				container, false);
		spinner = (Spinner) v.findViewById(R.id.taikhoanlichsuPage_spinner);
		mainPanel = (LinearLayout) v
				.findViewById(R.id.taikhoanlichsuPage_main_layout);
		((BaseActivity)getRealActivity()).hideRightImage();
		return v;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TK_LICHSU_CODE) {
			return;
		}
		mSharePreferences = getRealActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		currentUsername = mSharePreferences.getString("CURRENT_USER_NAME", "xxx");
		itemPerPage = 10;
		ArrayAdapter<String> seasonSpinnerAdapter = new ArrayAdapter<String>(
				getRealActivity(), R.layout.lichthidau_page_spinner_layout,
				spinnerOption);
		seasonSpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(seasonSpinnerAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					itemPerPage  = 10;
					break;
				case 1:
					itemPerPage  = 20;
					break;
				case 2:
					itemPerPage  = 50;
					break;
				case 3:
					itemPerPage  = 100;
					break;

				default:
					break;
				}
				netAsync();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
	}

	

	private class ProcessListContents extends
			AsyncTask<String, String, List<TK_LichsuModel>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			mainPanel.removeAllViews();
		}

		@Override
		protected List<TK_LichsuModel> doInBackground(String... args) {
			List<TK_LichsuModel> list = new ArrayList<TK_LichsuModel>();
			// list = new JSONParser(mActivity).parseTintuc("0");
			list = new JSONParser(getRealActivity()).parseAccHistoryLog(
					currentUsername, itemPerPage);

			return list;
		}

		@Override
		protected void onPostExecute(final List<TK_LichsuModel> list) {
			boolean isPrShowing = true;
			mainPanel.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				TK_LichsuModel model = list.get(i);
				LinearLayout item = (LinearLayout) inflater.inflate(
						R.layout.taikhoanlichsu_item, null);
				TextView stt = (TextView) item.findViewById(R.id.tklichsu_stt);
				stt.setText("" + (i + 1));
				TextView date = (TextView) item
						.findViewById(R.id.tklichsu_date);
				date.setText(model.getRegisterDatetime());
				TextView thaydoi = (TextView) item
						.findViewById(R.id.tklichsu_thaydoi);
				if (model.getLogType() < 0) {
					thaydoi.setText("-" + model.getChange() + "");
					thaydoi.setTextColor(getResources().getColor(R.color.myredcolor));
				}else{
					thaydoi.setText(model.getChange() + "");
					thaydoi.setTextColor(getResources().getColor(R.color.mygreencolor));
				}
				
				TextView sauthaydoi = (TextView) item
						.findViewById(R.id.tklichsu_sauthaydoi);
				sauthaydoi.setTextColor(getResources().getColor(R.color.base_app_color));
				sauthaydoi.setText(model.getAfterChange() + "");
				TextView noidung = (TextView) item
						.findViewById(R.id.tklichsu_noidung);
				noidung.setText(model.getDescription());
				mainPanel.addView(item);
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
										List<TK_LichsuModel> list) {
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

}
