package com.ats.NIEN.abongda.fragment.LIVE;

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
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;

import com.ats.NIEN.abongda.UTIL.AnimatedExpandableListView;
import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.adapter.NEXT24H_Adapter;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_MatchDetailFragment;

import com.ats.NIEN.abongda.model.MatchModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class LIVE_Next24hFragment extends BaseFragment {
	private ProgressDialog pDialog;
	public AnimatedExpandableListView listView;
	public NEXT24H_Adapter adapter;
	public View v, mListView, mNotFoundView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater
				.inflate(R.layout.next24_page_main_layout, container, false);
		mListView = (View) inflater.inflate(R.layout.next_24h_page_listview,
				container, false);
		mNotFoundView = (View) inflater.inflate(R.layout.nores_layout,
				container, false);
		mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		return v;

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.NEXT24H_CODE) {
			return;
		}
		netAsync();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
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

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (flag) {
				//MpDialog = new ProgressHUD(getRealActivity()).show(getRealActivity(), "", true, false, null);
			}else{
				MpDialog = new ProgressHUD(getRealActivity(),false).show(getRealActivity(), true, false, null);
				((BaseActivity)getRealActivity()).animateRightImage();
			}
			
		}
		

		@Override
		protected List<MatchModel> doInBackground(String... args) {
			List<MatchModel> list = new ArrayList<MatchModel>();
			list = new JSONParser(getRealActivity())
					.parseNext24List(mSharePreferences.getInt(
							"CURRENT_USER_ID", 0));
			return list;
		}

		@Override
		protected void onPostExecute(final List<MatchModel> list) {
			((LinearLayout) v).removeAllViews();
			if (list.size() > 0) {
				((LinearLayout) v).addView(mListView);
				listView = (AnimatedExpandableListView) mListView
						.findViewById(R.id.live_page_listview3);

				listView.setFadingEdgeLength(0);
				adapter = new NEXT24H_Adapter(list, getRealActivity(), listView);
				listView.setAdapter(adapter);
				listView.setGroupIndicator(null);
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					listView.expandGroup(i);
				}
				listView.setOnGroupClickListener(new OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						// We call collapseGroupWithAnimation(int) and
						// expandGroupWithAnimation(int) to animate group
						// expansion/collapse.

						if (listView.isGroupExpanded(groupPosition)) {
							listView.collapseGroupWithAnimation(groupPosition);
						} else {
							listView.expandGroupWithAnimation(groupPosition);
						}
						return true;
					}

				});
				listView.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// TODO Auto-generated method stub
						final int matchId = adapter.getChild(groupPosition,
								childPosition).getMatchId();
						@SuppressWarnings("serial")
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.MATCHDETAILFRAGMENT,
								new ArrayList<Object>(){{
									add(matchId);
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
						return true;
					}
				});
			} else {
				((LinearLayout) v).addView(mNotFoundView);
			}
			if (!flag) {
				this.MpDialog.dismiss();
				((BaseActivity)getRealActivity()).stopAnimateRightImage();
			}

		}
	}

	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		new ProcessListContents(false).execute();
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
										List<MatchModel> list) {
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
