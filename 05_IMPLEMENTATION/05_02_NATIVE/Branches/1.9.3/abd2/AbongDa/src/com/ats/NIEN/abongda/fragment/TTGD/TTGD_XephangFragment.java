package com.ats.NIEN.abongda.fragment.TTGD;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_ClubFragment;
import com.ats.NIEN.abongda.fragment.TTGD.TTGD_LichthidauFragment.SeasonHolder;
import com.ats.NIEN.abongda.model.ClubModel;
import com.ats.NIEN.abongda.model.GroupStandingModel;
import com.ats.NIEN.abongda.model.League;
import com.ats.NIEN.abongda.model.LeagueSeason;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class TTGD_XephangFragment extends BaseFragment {
	public int leagueCode;
	public int currentSeason, currentGroup;
	public List<LeagueSeason> seasonList;
	public List<String> seasonNameList;
	public List<String> groupNameList;
	public List<ClubModel> list;
	public Spinner seasonSpinner, groupSpinner;
	public LinearLayout mainPanel;
	public IMAGELOADER_ImageLoader mImageLoader;
	public List<GroupStandingModel> groupStandingList;

	public TTGD_XephangFragment(int leagueCode) {
		super();
		this.leagueCode = leagueCode;
		Log.d("tag8", "league Code: " + this.leagueCode);
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
		View v = inflater.inflate(R.layout.xephang_page_main_layout, container,
				false);
		seasonSpinner = (Spinner) v.findViewById(R.id.xephangPage_main_spinner);
		groupSpinner = (Spinner) v.findViewById(R.id.xephangPage_groupSpinner);
		mainPanel = (LinearLayout) v.findViewById(R.id.xephangPage_main_layout);
		currentSeason = 0;
		currentGroup = 0;
		
		mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		this.leagueCode = mSharePreferences.getInt("CURRENT_LEAGUE_ID", 1);
		if (leagueCode < 5) {
			groupSpinner.setVisibility(View.GONE);
		} else {
			groupSpinner.setVisibility(View.VISIBLE);
		}
		this.mImageLoader = new IMAGELOADER_ImageLoader(getRealActivity());
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
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) > 100
				&& mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.XEPHANG_CODE) {
			return;
		}
		netAsync();
		SharedPreferences prfs = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		((BaseActivity) getRealActivity()).changeRightImageToRefresh();
		((BaseActivity) getRealActivity()).changeTitle(League.getLeagueName2(mSharePreferences.getInt("CURRENT_LEAGUE_ID", 0)));
		mEditor = prfs.edit();
		mEditor.putString("CURRENT_ACTIVE_CODE", Constant.TAB_XEPHANG);
		mEditor.commit();
		seasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setCurrentSeason(parent.getItemAtPosition(position).toString());

				if (leagueCode < 6) {
					new ProcessListContents().execute();
				}else{
					new ProcessListGroupContents().execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		groupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				currentGroup = position;
				Log.d("tag8", "currentGroup: " + currentGroup);
				new ProcessListGroupContents().execute();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

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




	private void setSeasonList() {
		this.seasonNameList = new ArrayList<String>();
		for (int i = 0; i < seasonList.size(); i++) {
			seasonNameList.add(seasonList.get(i).getSeasonName());
		}
	}

	public void setCurrentSeason(String currentSeason) {
		int i = seasonNameList.indexOf(currentSeason);
		this.currentSeason = seasonList.get(i).getRowId();
	}

	private class SeasonListContents extends
			AsyncTask<String, String, SeasonHolder> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected SeasonHolder doInBackground(String... params) {
			// TODO Auto-generated method stub
			SeasonHolder holder = new SeasonHolder();
			String savedSeasonList = mSharePreferences.getString("XEPHANG_SEASONLIST", "");
			if (savedSeasonList.equals("")) {
				seasonList = new JSONParser(getRealActivity())
				.parseLeagueSeasonWithoutMonth("0", leagueCode);
				saveModel(seasonList, "XEPHANG_SEASONLIST");
			}else{
				seasonList = new JSONParser(getRealActivity())
				.parseLeagueSeasonWithoutMonth_V2(savedSeasonList);
			}
			
			setSeasonList();
			return holder;
		}

		@Override
		protected void onPostExecute(SeasonHolder result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			ArrayAdapter<String> seasonSpinnerAdapter = new ArrayAdapter<String>(
					getRealActivity(), R.layout.lichthidau_page_spinner_layout,
					seasonNameList);
			seasonSpinnerAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			seasonSpinner.setAdapter(seasonSpinnerAdapter);
			if (mSharePreferences.getInt("XH_SS", -1) != -1) {
				seasonSpinner.setSelection(mSharePreferences.getInt("XH_SS", 0));
			}
			if (leagueCode > 5) {
				groupNameList = new ArrayList<String>();
				groupStandingList = (new JSONParser(getRealActivity()))
						.parseGroupStanding("12345", leagueCode, currentSeason);
				for (GroupStandingModel group : groupStandingList) {
					groupNameList.add(group.get_League_Name());
				}
				ArrayAdapter<String> groupSpinnerAdapter = new ArrayAdapter<String>(
						getRealActivity(),
						R.layout.lichthidau_page_spinner_layout, groupNameList);
				groupSpinnerAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				groupSpinner.setAdapter(groupSpinnerAdapter);
				if (mSharePreferences.getInt("XH_SG", -1) != -1) {
					groupSpinner.setSelection(mSharePreferences.getInt("XH_SG", 0));
				}
			}
			if (leagueCode < 6) {
				new ProcessListContents().execute();
			} else {
				new ProcessListGroupContents().execute();
			}

		}

	}

	private class ProcessListContents extends
			AsyncTask<String, String, List<ClubModel>> {
		public Dialog MpDialog;
		boolean flag = true;
		
		
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
				MpDialog = new ProgressHUD(getRealActivity(), false).show(getRealActivity(), true, false, null);
				((BaseActivity)getRealActivity()).animateRightImage();
			}
			mainPanel.removeAllViews();
		}

		@Override
		protected List<ClubModel> doInBackground(String... args) {
			
			List<ClubModel> list = new ArrayList<ClubModel>();
			// list = new JSONParser(mActivity).parseTintuc("0");
			if (currentSeason!= 0) {
				list = new JSONParser(getRealActivity()).parseSeasonStandingList(
						"0", leagueCode, currentSeason);
			}
			
			return list;
		}

		@Override
		protected void onPostExecute(final List<ClubModel> list) {
			boolean isPrShowing = true;
			mainPanel.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getRealActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				final ClubModel club = list.get(i);
				LinearLayout item = (LinearLayout) inflater.inflate(
						R.layout.xephang_page_item, null);
				TextView stt = (TextView) item
						.findViewById(R.id.xephangPageItem_pos);
				if (i >= 9) {
					stt.setText("" + (i + 1));
				} else {
					stt.setText(" " + (i + 1));
				}

				ImageView logo = (ImageView) item
						.findViewById(R.id.TKHS_phone_cf);
				mImageLoader.DisplayImage(club.get_logo(), logo);
				TextView name = (TextView) item
						.findViewById(R.id.xephangPageItem_name);
				name.setText(club.get_club_name());
				name.setTag("clubname");
				name.setClickable(true);
				name.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.d("tag7", "item clicked !");
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.CLUBFRAGMENT,
								new ArrayList<Object>(){{
									add(club.get_club_id());
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
					}
				});
				TextView tran = (TextView) item
						.findViewById(R.id.xephangPageItem_tran);
				tran.setText(club.get_total_match() + "");
				TextView btbb = (TextView) item
						.findViewById(R.id.xephangPageItem_btbb);
				btbb.setText((club.get_home_goal_win() + club
						.get_guest_goal_win())
						+ ":"
						+ (club.get_home_goal_lose() + club
								.get_guest_goal_lose()));
				TextView hs = (TextView) item
						.findViewById(R.id.xephangPageItem_hs);
				hs.setText(""
						+ (club.get_home_goal_win() + club.get_guest_goal_win()
								- club.get_home_goal_lose() - club
									.get_guest_goal_lose()));
				TextView diem = (TextView) item
						.findViewById(R.id.xephangPageItem_diem);
				diem.setText("" + club.get_mark());
				mainPanel.addView(item);
				if (i == list.size() - 1 || list.size() == 0) {
					MpDialog.dismiss();
					isPrShowing = false;
					
				}
			}
			if (isPrShowing) {
				MpDialog.dismiss();
			}
			((BaseActivity)getRealActivity()).stopAnimateRightImage();
			
		}
	}

	private class ProcessListGroupContents extends
			AsyncTask<String, String, List<ClubModel>> {

		public Dialog MpDialog;
		boolean flag;
		
		
		public ProcessListGroupContents() {
			super();
			// TODO Auto-generated constructor stub
			this.flag = true;
		}
		
		

		public ProcessListGroupContents(boolean flag) {
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
		protected List<ClubModel> doInBackground(String... args) {
			if (currentSeason!=0) {
				groupStandingList = (new JSONParser(getRealActivity()))
						.parseGroupStanding("12345", leagueCode, currentSeason);
			}
			list = groupStandingList.get(currentGroup).get_LStanding();
			
			return list;
		}

		@Override
		protected void onPostExecute(final List<ClubModel> list) {
			boolean isPrShowing = true;
			mainPanel.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getRealActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				final ClubModel club = list.get(i);
				LinearLayout item = (LinearLayout) inflater.inflate(
						R.layout.xephang_page_item, null);
				TextView stt = (TextView) item
						.findViewById(R.id.xephangPageItem_pos);
				if (i >= 9) {
					stt.setText("" + (i + 1));
				} else {
					stt.setText(" " + (i + 1));
				}

				ImageView logo = (ImageView) item
						.findViewById(R.id.TKHS_phone_cf);
				mImageLoader.DisplayImage(club.get_logo(), logo);
				TextView name = (TextView) item
						.findViewById(R.id.xephangPageItem_name);
				name.setText(club.get_club_name());
				name.setTag("clubname");
				name.setClickable(true);
				name.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.d("tag7", "item clicked !");
						FragmentManager fm = getFragmentManager();
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.CLUBFRAGMENT,
								new ArrayList<Object>(){{
									add(club.get_club_id());
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
					}
				});
				TextView tran = (TextView) item
						.findViewById(R.id.xephangPageItem_tran);
				tran.setText(club.get_total_match() + "");
				TextView btbb = (TextView) item
						.findViewById(R.id.xephangPageItem_btbb);
				btbb.setText((club.get_home_goal_win() + club
						.get_guest_goal_win())
						+ ":"
						+ (club.get_home_goal_lose() + club
								.get_guest_goal_lose()));
				TextView hs = (TextView) item
						.findViewById(R.id.xephangPageItem_hs);
				hs.setText(""
						+ (club.get_home_goal_win() + club.get_guest_goal_win()
								- club.get_home_goal_lose() - club
									.get_guest_goal_lose()));
				TextView diem = (TextView) item
						.findViewById(R.id.xephangPageItem_diem);
				diem.setText("" + club.get_mark());
				mainPanel.addView(item);
				if (i == list.size() - 1 || list.size()==0) {
					MpDialog.dismiss();
					isPrShowing = false;
				}
			}
			if (isPrShowing) {
				MpDialog.dismiss();
			}
			((BaseActivity)getRealActivity()).stopAnimateRightImage();
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
	
	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		if (leagueCode < 6) {
			new ProcessListContents(false).execute();
		} else {
			new ProcessListGroupContents(false).execute();
		}
	}

}
