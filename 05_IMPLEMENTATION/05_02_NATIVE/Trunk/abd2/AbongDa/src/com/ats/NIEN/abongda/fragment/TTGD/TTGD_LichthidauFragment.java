package com.ats.NIEN.abongda.fragment.TTGD;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.DeviceUtil;
import com.ats.NIEN.abongda.UTIL.GeneralConfig;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_LichthidauDoibongFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_MatchDetailFragment;
import com.ats.NIEN.abongda.model.League;
import com.ats.NIEN.abongda.model.LeagueSeason;
import com.ats.NIEN.abongda.model.MatchModel;
import com.ats.NIEN.abongda.model.MonthData;
import vn.ats.aBongDa.R;

@SuppressLint({ "InflateParams", "NewApi" })
public class TTGD_LichthidauFragment extends BaseFragment{
	public Activity mActivity;
	public int leagueCode;
	public List<LeagueSeason> seasonList;
	public List<MatchModel> matchList;
	public Spinner seasonSpinner, monthSpinner;
	public boolean parseBySeason, parseByMonth, parseByBoth;
	public List<String> seasonNameList;
	public List<String> monthList;
	public static int currentMonth;
	public static int currentSeason;
	public LinearLayout mainPanel;
	public LinearLayout loaderLayout;
	public int savedLeagueCode, savedMonth, savedSeason;
	public ScrollView mainScrollView;
	public boolean isScrolledToRightPos = false;
	public String[] rounds;
	public int currentCLRound;
	public String initialMonth;
	public View viewToScroll;
	
	public TTGD_LichthidauFragment(int leagueCode) {
		super();
		this.leagueCode = leagueCode;	
		this.savedLeagueCode = 0;
		rounds = new String[]{"Vòng sơ loại","Vòng bảng","Vòng knockout"};
		this.parseByBoth = false;
		this.parseBySeason = false;
		this.parseByBoth = false;
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("currentSavedMonth", monthSpinner.getSelectedItemPosition());
	}


	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.lichthidau_page_main_layout, container, false);
		seasonSpinner = (Spinner) v.findViewById(R.id.LichthidauPage_nam_spinner);	
		monthSpinner = (Spinner) v.findViewById(R.id.LichthidauPage_thang_spinner);
		mainPanel = (LinearLayout) v.findViewById(R.id.LichthidauPage_main_panel);
		loaderLayout = (LinearLayout) inflater.inflate(R.layout.loader_layout, container, false);
		mainScrollView = (ScrollView) v.findViewById(R.id.lichthidauPage_scrollview);
		
		currentMonth = 0;
		currentSeason = 0;
		
		GeneralConfig mConfig = new GeneralConfig(mSharePreferences.getString("GENERAL_CONFIG", ""));
		this.leagueCode = mSharePreferences.getInt("CURRENT_LEAGUE_ID", 1);
		this.currentCLRound = mConfig.get_currentCLRound();
		this.initialMonth = "Tháng " + new DeviceUtil().getCurrentMonth();
		return v;
		
	}
	public void refreshView(){
		new ProcessListContents().execute();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) > 100 && mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != 
				Constant.LICHTHIDAU_CODE) {
			return;
		}
		if (this.savedLeagueCode != 0) {
			this.leagueCode = this.savedLeagueCode;
		}		
		((BaseActivity) getRealActivity()).changeRightImageToRefresh();
		((BaseActivity) getRealActivity()).changeTitle(League.getLeagueName2(mSharePreferences.getInt("CURRENT_LEAGUE_ID", 0)));
		SharedPreferences.Editor   editor = mSharePreferences.edit();
		editor.putString("CURRENT_ACTIVE_CODE", Constant.TAB_LICHTHIDAU);
		editor.commit();
		NetAsync(getView());
		this.parseByBoth = false;
		this.parseByMonth = false;
		this.parseBySeason = false;
		
		seasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setMonthDataList(position);
				setCurrentSeason(parent.getItemAtPosition(position).toString());
				ArrayAdapter<String> monthDataSpinnerAdapter = new ArrayAdapter<String>(getRealActivity(), R.layout.lichthidau_page_spinner_layout,monthList);
				monthDataSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				monthSpinner.setAdapter(monthDataSpinnerAdapter);
				monthSpinner.setSelection(getRealCurrentMonth());
				
				if (!parseBySeason) {
					parseBySeason = true;					
				}else{
					new ProcessListContents().execute();
				}				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub				
			}
		});	
		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setCurrentMonth(parent.getItemAtPosition(position).toString());	
				if (!parseByMonth) {
					parseByMonth = true;				
				}else{
					new ProcessListContents().execute();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.savedLeagueCode = this.leagueCode;
		this.savedMonth = currentMonth;
		this.savedSeason = currentSeason;
	}
	
	
	
	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		
		new ProcessListContents(false){
			
		}.execute();
	}
	private void setSeasonList(){
		this.seasonNameList = new ArrayList<String>();
		for(int i=0; i< seasonList.size(); i++){
			seasonNameList.add(seasonList.get(i).getSeasonName());
			
		}
	}
	
	private void setMonthDataList(int pos){
		this.monthList = new ArrayList<String>();
		if (leagueCode < 6) {
			List<MonthData> mdList = seasonList.get(pos).getMonthData();
			for(int i=0; i< mdList.size(); i++){
				monthList.add(mdList.get(i).monthAsString());
			}
		}else{
			for (String round : rounds) {
				monthList.add(round);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void setCurrentSeason(String currentSeason){
		int i = seasonNameList.indexOf(currentSeason);
		this.currentSeason = seasonList.get(i).getRowId();
	}
	
	@SuppressWarnings("static-access")
	public void setCurrentMonth(String currentMonth){	
		if (leagueCode < 6) {
			int i = monthList.indexOf(currentMonth);
			int j = 0;
			for (int j2 = 0; j2 < seasonList.size(); j2++) {
				if (seasonList.get(j2).getRowId() == currentSeason) {
					j = j2;
					break;
				}
			}
			this.currentMonth = seasonList.get(j).getMonthData().get(i).getMonth();
		}else{
			if (currentMonth.equalsIgnoreCase("Vòng sơ loại")) {
				this.currentMonth = 0;
			}else if(currentMonth.equalsIgnoreCase("Vòng bảng")){
				this.currentMonth = 1;
			}else if(currentMonth.equalsIgnoreCase("Vòng knockout")){
				this.currentMonth = 2;
			}
		}
	}
	
	public int getRealCurrentMonth(){
		if (leagueCode < 6) {
			for (int i = 0; i < monthList.size(); i++) {
				if (monthList.get(i).equals(this.initialMonth)) {
					return i;
				}
			}
		}else{
			return this.currentCLRound; 
		}
		return 0;
	}
	
	

	private class SeasonListContents extends AsyncTask<String, String, SeasonHolder>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}
		
		@Override
		protected SeasonHolder doInBackground(String... params) {
			// TODO Auto-generated method stub
			SeasonHolder holder = new SeasonHolder();
			if (leagueCode < 6) {
				seasonList = new JSONParser(getRealActivity()).parseLeagueSeason("0", leagueCode);
			}else{
				String savedSeasonList = mSharePreferences.getString("XEPHANG_SEASONLIST", "");
				if (savedSeasonList.equals("")) {
					seasonList = new JSONParser(getRealActivity())
					.parseLeagueSeasonWithoutMonth("0", leagueCode);
					saveModel(seasonList, "XEPHANG_SEASONLIST");
				}else{
					seasonList = new JSONParser(getRealActivity())
					.parseLeagueSeasonWithoutMonth_V2(savedSeasonList);
				}
			}
			return holder;
		}
		
		@Override
		protected void onPostExecute(SeasonHolder result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setSeasonList();
			setMonthDataList(0);
			ArrayAdapter<String> seasonSpinnerAdapter = new ArrayAdapter<String>(getRealActivity(), R.layout.lichthidau_page_spinner_layout,seasonNameList);
			seasonSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			seasonSpinner.setAdapter(seasonSpinnerAdapter);
			if (mSharePreferences.getInt("LTD_SS", -1) != -1) {
				seasonSpinner.setSelection(mSharePreferences.getInt("LTD_SS", -1));
			}
			ArrayAdapter<String> monthDataSpinnerAdapter = new ArrayAdapter<String>(getRealActivity(), R.layout.lichthidau_page_spinner_layout,monthList);
			monthDataSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			monthSpinner.setAdapter(monthDataSpinnerAdapter);
			Log.d("tag7","--->" + initialMonth + " - " + currentCLRound + " - " + getRealCurrentMonth());
			monthSpinner.setSelection(getRealCurrentMonth());
			if (currentMonth !=0 || currentSeason !=0) {
				new ProcessListContents().execute();
			}
		}
		
	}
	private class ProcessListContents extends
			AsyncTask<String, String, List<MatchModel>> {

		public Dialog MpDialog;
		public boolean flag;

		
		public ProcessListContents() {
			super();
			// TODO Auto-generated constructor stub
			this.flag = true;
		}

		
		public ProcessListContents(boolean flag) {
			super();
			this.flag = flag;
		}


		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (flag) {
				MpDialog = new ProgressHUD(getRealActivity()).show(getRealActivity(), "", true, false, null);
			}else{
				MpDialog = new ProgressHUD(getRealActivity(),false).show(getRealActivity(), true, false, null);
				((BaseActivity)getRealActivity()).animateRightImage();
			}
			mainPanel.removeAllViews();

		}

		@Override
		protected List<MatchModel> doInBackground(String... args) {
			if (getActivity() != null) {
				mActivity = getActivity();
			}
			List<MatchModel> list = new ArrayList<MatchModel>();
			//list = new JSONParser(mActivity).parseTintuc("0");
			if (leagueCode < 6) {
				list = new JSONParser(mActivity).parseNationLeague("123456", currentSeason, leagueCode, 40654, currentMonth);
			}else{
				list = new JSONParser(mActivity).parseCupLeague("123456", currentSeason, leagueCode, 40654, currentMonth);
			}

			return list;
		}

		@Override
		protected void onPostExecute(final List<MatchModel> list) {
			Rect scrollBounds = new Rect();
			mainScrollView.getHitRect(scrollBounds);
			mainPanel.removeAllViews();
			boolean scrollFlag = false;
			boolean isPrShowing = true;
			LayoutInflater inflater = (LayoutInflater) getRealActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				final MatchModel match = list.get(i);
				RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.lichthidau_page_item, null);
				TextView hostname = (TextView) item.findViewById(R.id.lichthidauPage_host);
				hostname.setText(match.getHome());
				hostname.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.LICHTHIDAUDOIBONGFRAGMENT,
								new ArrayList<Object>(){{
									add(leagueCode);
									add(match.getHomeId());
									add(match.getHome());
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
					}
				});
				TextView guestname = (TextView) item.findViewById(R.id.lichthidauPage_guest);
				guestname.setText(match.getGuest());
				guestname.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//FragmentManager fm = getFragmentManager();
						//FULLSCR_LichthidauDoibongFragment frag = new FULLSCR_LichthidauDoibongFragment(leagueCode, match.getGuestId(), match.getGuest());
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.LICHTHIDAUDOIBONGFRAGMENT,
								new ArrayList<Object>(){{
									add(leagueCode);
									add(match.getGuestId());
									add(match.getGuest());
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
						
					}
				});
				TextView result = (TextView) item.findViewById(R.id.lichthidauPage_finalScore);
				if (match.getMatchStatus() == 0) {
					result.setText("   -   ");
				}else{
					result.setText(" "+ match.getHomeGoal() + "-" + match.getGuestGoal() + " ");
				}
				String[] arrStD = match.getMatchShortStartDate().split("/");
				String day = arrStD[0];
				String month = arrStD[1];
				if (arrStD[0].length() == 1) {
					day = "0"+day;
				}
				if (arrStD[1].length() == 1) {
					month = "0"+month;
				}
 				
				TextView matchTime = (TextView) item.findViewById(R.id.lichthidauPage_time);
				matchTime.setText(day+"/"+month + "\n" + match.getMatchStartTimeWithoutPlus());
				if (i % 2 == 0) {
					item.setBackgroundColor(getResources().getColor(R.color.listitem));
				}
				mainPanel.addView(item);			
				if (!item.getLocalVisibleRect(scrollBounds)) {
					if (match.getMatchStatus() == 0 && scrollFlag == false) {
						viewToScroll = item;
						scrollFlag = true;
					}
				} 
				
				if (i== list.size()-1) {
					MpDialog.dismiss();
					isPrShowing = false;
				}
				ImageView mArrow = (ImageView) item.findViewById(R.id.lichthidauPage_arrow);
				mArrow.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final int matchId = match.getMatchId();
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.MATCHDETAILFRAGMENT,
								new ArrayList<Object>(){{
									add(matchId);
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
						
					}
				});
			}
			if (isPrShowing) {
				MpDialog.dismiss();
			}
			((BaseActivity)getRealActivity()).stopAnimateRightImage();
			focusOnView();

			
			
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
							new SeasonListContents() {
								@Override
								protected void onPostExecute(
										SeasonHolder list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}

	public static class SeasonHolder{
		static List<String> list1;
		static List<String>	list2;
	}
	
	private final void focusOnView(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
               if (viewToScroll != null) {
            	   mainScrollView.smoothScrollTo(0, viewToScroll.getTop());
			}
            }
        });
    }
}
