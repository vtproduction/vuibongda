package com.ats.NIEN.abongda.fragment.TTGD;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.TTGD.TTGD_LichthidauFragment.SeasonHolder;
import com.ats.NIEN.abongda.model.CauthuModel;
import com.ats.NIEN.abongda.model.League;
import com.ats.NIEN.abongda.model.LeagueSeason;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class TTGD_ThongkeFragment extends BaseFragment{
	public int leagueCode;
	public int currentSeason;
	public List<LeagueSeason> seasonList;
	public List<String> seasonNameList;

	public List<CauthuModel> list;
	public Spinner seasonSpinner, groupSpinner;
	public LinearLayout mainPanel;
	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	
	
	public TTGD_ThongkeFragment(int leagueCode) {
		super();
		this.leagueCode = leagueCode;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.thongke_page_main_layout, container,
				false);
		seasonSpinner = (Spinner) v.findViewById(R.id.thongkePage_spinner);

		mainPanel = (LinearLayout) v.findViewById(R.id.thongkePage_main_layout);
		currentSeason = 0;
		mSharePreferences = getRealActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		this.leagueCode = mSharePreferences.getInt("CURRENT_LEAGUE_ID", 1);
		this.iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(getRealActivity());
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
				&& mSharePreferences.getInt("CALLBACK_FRAGMENT", 0)!= Constant.THONGKE_CODE) {
			return;
		}
		netAsync();
		getBaseActivity().changeRightImageToRefresh();
		getBaseActivity().changeTitle(League.getLeagueName2(mSharePreferences.getInt("CURRENT_LEAGUE_ID", 0)));
		mEditor.putString("CURRENT_ACTIVE_CODE", Constant.TAB_THONGKE);
		mEditor.commit();
		
		seasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub			
				setCurrentSeason(parent.getItemAtPosition(position).toString());
				new ProcessListContents().execute();			
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





	private void setSeasonList(){
		this.seasonNameList = new ArrayList<String>();
		for(int i=0; i< seasonList.size(); i++){
			seasonNameList.add(seasonList.get(i).getSeasonName());
		}
	}
	public void setCurrentSeason(String currentSeason){
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
			if (mSharePreferences.getInt("TK_SS", -1) != -1) {
				seasonSpinner.setSelection(mSharePreferences.getInt("TK_SS", 0));
			}
			

			new ProcessListContents().execute();
		}

	}

	
	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		new ProcessListContents(false).execute();
	}
	private class ProcessListContents extends
			AsyncTask<String, String, List<CauthuModel>> {

		public Dialog MpDialog;
		public boolean flag;
		
		public ProcessListContents() {
			super();
			this.flag = true;
		}

		public ProcessListContents(boolean b) {
			// TODO Auto-generated constructor stub
			super();
			this.flag = b;
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
		protected List<CauthuModel> doInBackground(String... args) {
			if (getActivity() != null) {
				mActivity = getActivity();
			}
			List<CauthuModel> list = new ArrayList<CauthuModel>();
			if (currentSeason != 0) {
				list = new JSONParser(getRealActivity()).parsePlayerList("0", currentSeason, leagueCode, 1);
			}

			return list;
		}

		@Override
		protected void onPostExecute(final List<CauthuModel> list) {
			boolean isPrShowing = true;
			mainPanel.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getRealActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			for (int i = 0; i < list.size(); i++) {
				CauthuModel cauthu = list.get(i);
				LinearLayout item = (LinearLayout) inflater.inflate(
						R.layout.thongke_page_item, null);
				TextView stt = (TextView) item.findViewById(R.id.thongkePage_item_stt);
				stt.setText(""+(i+1));
				ImageView logo = (ImageView) item.findViewById(R.id.thongkePage_item_logo);
				iMAGELOADER_ImageLoader.DisplayImage(cauthu.getClubLogo(), logo);
				TextView name = (TextView) item.findViewById(R.id.thongkePage_item_name);
				name.setText(cauthu.getName());
				TextView tran = (TextView) item.findViewById(R.id.thongkePage_item_bt);
				tran.setText(cauthu.getGoal()+"");
				mainPanel.addView(item);
				if (i== list.size()-1 || list.size()==0) {
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
	
	
	
	
	
}
