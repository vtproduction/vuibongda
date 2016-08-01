package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.model.UserModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class FULLSCR_TopcaothuFragment extends BaseFragment{
	public ProgressDialog pDialog;
	public LinearLayout mainPanel;
	public Spinner mSpinner;
	public String[] spinnerData;
	public int currentSelection;
	
	
	
	public FULLSCR_TopcaothuFragment() {
		super();
		// TODO Auto-generated constructor stub
		spinnerData = new String[]{"Theo thời gian","Theo giải thưởng"};
		currentSelection = 0;
	}

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
		View v = inflater.inflate(R.layout.topcaothu_main_layout, container,
				false);
		mainPanel = (LinearLayout) v.findViewById(R.id.topcaothuPage_main_layout);
		((BaseActivity)getRealActivity()).changeTitle("Top cao thủ");
		((BaseActivity)getRealActivity()).hideRightImage();
		mSpinner = (Spinner) v.findViewById(R.id.topcaothuPage_spinner);
		
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
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TOPCAOTHU_CODE) {
			return;
		}
		//NetAsync(getView());
		ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(
				getRealActivity(), R.layout.lichthidau_page_spinner_layout,
				spinnerData);
		mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				currentSelection = position;
				NetAsync(getView());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
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
			list = new JSONParser(getRealActivity()).parseTopcaothu(mSharePreferences.getInt("CURRENT_USER_ID", 0), currentSelection);

			return list;
		}

		@Override
		protected void onPostExecute(final List<UserModel> list) {
			boolean isPrShowing = true;
			
			mainPanel.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				LinearLayout item = (LinearLayout) inflater.inflate(R.layout.topcaothu_item_1, null);
				UserModel model = list.get(i);
				ImageView logo = (ImageView) item.findViewById(R.id.topcaothuPage_img);
				
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
				((TextView) item.findViewById(R.id.topcaothuPage_stt)).setText(""+(i+1));
				((TextView) item.findViewById(R.id.topcaothuPage_name)).setText(model.getName());
				((TextView) item.findViewById(R.id.topcaothuPage_thang)).setText(""+model.getWonMatchScore());
				((TextView) item.findViewById(R.id.topcaothuPage_thua)).setText(""+model.getLostMatchScore());
				((TextView) item.findViewById(R.id.topcaothuPage_hieuso)).setText(""+model.getDifferenceMatchScore());
				
				if (mSharePreferences.getInt("CURRENT_USER_ID", 0)==0) {
					mainPanel.addView(item);
				}else{
					if (i < list.size()-1) {
						mainPanel.addView(item);
					}else{
						LinearLayout gap = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_2, null);
						mainPanel.addView(gap);
						((ImageView) item.findViewById(R.id.topcaothuPage_img)).setImageDrawable(null);
						((TextView) item.findViewById(R.id.topcaothuPage_stt)).setText(""+model.getRank());
						((TextView) item.findViewById(R.id.topcaothuPage_stt)).setTextColor(getResources().getColor(R.color.myredcolor));
						mainPanel.addView(item);
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


}
