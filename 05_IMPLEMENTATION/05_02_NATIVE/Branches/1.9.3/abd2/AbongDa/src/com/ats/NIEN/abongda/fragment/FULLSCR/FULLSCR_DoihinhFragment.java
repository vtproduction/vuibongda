package com.ats.NIEN.abongda.fragment.FULLSCR;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.adapter.DoihinhAdapter;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.model.CauthuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FULLSCR_DoihinhFragment extends BaseFragment{
	public int clubId;
	public ListView listView;
	public DoihinhAdapter adapter;
	public String clubName;
	
	public FULLSCR_DoihinhFragment(int clubId, String clubName) {
		super();
		this.clubId = clubId;
		this.clubName = clubName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.doihinh_main_layout, container, false);
		listView = (ListView) v.findViewById(R.id.doihinh_listview);
		
		return v;
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
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.DOIHINHFRAGMENT) {
			return;
		}
		netAsync();
		((BaseActivity) getRealActivity()).changeRightImageToBack();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int cauthuId = adapter.getItem(position).getId();
				MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.CAUTHUFRAGMENT,
						new ArrayList<Object>(){{
							add(cauthuId);
						}});
				((MainActivity)getRealActivity()).switchContent(frag);
				/*FULLSCR_CauthuFragment frag = new FULLSCR_CauthuFragment(cauthuId);
				FragmentManager fm = getFragmentManager();
				fm.beginTransaction().setCustomAnimations(anim.slide_in_left,anim.slide_out_right ).replace(R.id.content_frame, frag).addToBackStack(getTag()).commit();*/
				
			}
		});
	}
	
	
	public Activity getRealActivity(){
		return (getActivity()==null)? mActivity : getActivity();
	}

	private class ProcessListContents extends
			AsyncTask<String, String, List<CauthuModel>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<CauthuModel> doInBackground(String... args) {
			
			List<CauthuModel> list = new ArrayList<CauthuModel>();
			list = new JSONParser(getRealActivity()).parseClubTeam("0", clubId);
			return list;
		}

		@Override
		protected void onPostExecute(final List<CauthuModel> list) {
			adapter = new DoihinhAdapter(getRealActivity(), list);
			listView.setAdapter(adapter);
			((BaseActivity) getRealActivity()).changeTitle(clubName);
			
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
										List<CauthuModel> list) {
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
