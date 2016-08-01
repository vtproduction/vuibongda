package com.ats.NIEN.abongda.fragment.TAIKHOAN;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_LichthidauDoibongFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_MatchDetailFragment;
import com.ats.NIEN.abongda.model.BetModel;
import com.ats.NIEN.abongda.model.League;
import com.ats.NIEN.abongda.model.MatchModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class TAIKHOAN_DatcuocFragment extends BaseFragment{
	public ProgressDialog pDialog;
	public LinearLayout mainPanel;
	public Spinner mSpinner;
	public String[] spinnerData;
	public int currentSelection;
	public boolean gotoSigninPage;
	
	
	public TAIKHOAN_DatcuocFragment(){
		super();
		// TODO Auto-generated constructor stub
		spinnerData = new String[]{"Trong hôm nay","Trong 2 ngày gần đây","Trong 3 ngày gần đây","Trong tuần này","Trong tháng này"};
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
		View v = inflater.inflate(R.layout.taikhoan_datcuoc_mainlayout, container,
				false);
		mainPanel = (LinearLayout) v.findViewById(R.id.tkdc_mainlayout);
		
		((BaseActivity)getRealActivity()).hideRightImage();
		mSpinner = (Spinner) v.findViewById(R.id.tkdc_spinner);
		
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
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TK_DATCUOC_CODE) {
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
				netAsync();
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
			AsyncTask<String, String, List<BetModel>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			mainPanel.removeAllViews();
		}

		@Override
		protected List<BetModel> doInBackground(String... args) {
			List<BetModel> list = new ArrayList<BetModel>();
			// list = new JSONParser(mActivity).parseTintuc("0");
			String zz ="";
			switch (currentSelection) {
			case 0:
				zz = "0";
				break;
			case 1:
				zz = "-1";
				break;
			case 2:
				zz = "-2";
				break;
			case 3:
				zz = "-6";
			case 4:
				zz = "-29";
				break;

			default:
				break;
			}
			list = new JSONParser(getRealActivity()).getBetResult(mSharePreferences.getInt("CURRENT_USER_ID", 0)+"", zz);

			return list;
		}

		@Override
		protected void onPostExecute(final List<BetModel> list) {
			boolean isPrShowing = true;
			
			mainPanel.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.taikhoan_datcuoc_item, null);
				final BetModel model = list.get(i);
				ImageView logo = (ImageView) item.findViewById(R.id.tkdc_child_logo);
				logo.setImageResource(new League(model.league_row_id).getTrueLeagueImageResource());
				MatchModel match = new MatchModel(model.register_datetime);
				
				//((TextView) item.findViewById(R.id.topcaothuPage_stt)).setText(""+(i+1));
				((TextView) item.findViewById(R.id.tkdc_child_host)).setText(model.home_club);
				((TextView) item.findViewById(R.id.tkdc_child_host_goal)).setText(model.home_club_goal + "");
				((TextView) item.findViewById(R.id.tkdc_child_guest)).setText(model.guest_club);
				((TextView) item.findViewById(R.id.tkdc_child_guest_goal)).setText(model.guest_club_goal + "");
				((TextView) item.findViewById(R.id.tkdc_child_matchTime)).setText(match.getMatchShortStartDate()+"\n"+match.getMatchStartTimeWithoutPlus());
				switch (model.bet_result) {
				case -1:
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setText(
							"Kết quả: thua " + (model.bet_result_value*-1) + " xu"
							);
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setTextColor(getRealActivity().getResources().getColor(R.color.redcolor));
					break;
				case 1:
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setText(
							"Kết quả: thắng " + model.bet_result_value + " xu"
							);
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setTextColor(getRealActivity().getResources().getColor(R.color.mygreencolor));
					break;
				case -2:
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setText(
							"Số xu đặt: " + model.user_bet_value + " xu"
							);
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setTextColor(getRealActivity().getResources().getColor(R.color.base_app_color));
					break;
				default:
					((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setVisibility(View.INVISIBLE);
					break;
				}
				item.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*
						 * FragmentManager fm = getFragmentManager();
						 * FULLSCR_MatchDetailFragment frag = new
						 * FULLSCR_MatchDetailFragment(model.matchId);
						 * ((BaseActivity)
						 * getRealActivity()).changeRightImage();
						 * fm.beginTransaction
						 * ().setCustomAnimations(anim.slide_in_left
						 * ,anim.slide_out_right ).replace(R.id.content_frame,
						 * frag).addToBackStack(getTag()).commit();
						 */
						MAINVIEW_MainFragment  mf=  new MAINVIEW_MainFragment(Constant.MATCHDETAILFRAGMENT,new ArrayList<Object>(){{
							add(model.matchId);
						}});
						((MainActivity)getRealActivity()).switchContent(mf);
						
					}
				});
				mainPanel.addView(item);
				
			}
			if (isPrShowing) {
				//pDialog.dismiss();
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
										List<BetModel> list) {
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

