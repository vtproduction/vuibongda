package com.ats.NIEN.abongda.fragment.LIVE;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import com.ats.NIEN.abongda.adapter.LIVE_Adapter;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_MatchDetailFragment;
import com.ats.NIEN.abongda.model.LiveModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class LIVE_LiveFragment extends BaseFragment {
	private ProgressHUD pDialog;
	public AnimatedExpandableListView listView;
	public View mlistView;
	public View mNotFoundView, v;
	public LIVE_Adapter adapter;
	public static volatile boolean needToRefresh;
	public LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("LIVE_LiveFragment", "OnCreate called");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.live_page_main_layout, container, false);
		mlistView = (View) inflater.inflate(R.layout.livepage_listview,
				container, false);
		mNotFoundView = (View) inflater.inflate(R.layout.nores_layout,
				container, false);
		needToRefresh = true;
		mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		this.inflater = inflater;
		Log.d("LIVE_LiveFragment", "OnCreateView called");
		
		return v;

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (pDialog != null) {
			pDialog.dismiss();
		}
		Log.d("LIVE_LiveFragment", "OnStop called");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (pDialog != null) {
			pDialog.dismiss();
		}
		Log.d("LIVE_LiveFragment", "OnPause called");
		// needToRefresh = true;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.LIVESCORE_CODE) {
			return;
		}
		Log.d("LIVE_LiveFragment", "OnResume called");
		if (needToRefresh == true) {
			NetAsync(true);
			needToRefresh = false;
		}

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Log.d("LIVE_LiveFragment", "OnStart called");
		needToRefresh = true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		Log.d("LIVE_LiveFragment", "OnDestroy called");
		needToRefresh = false;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
		Log.d("LIVE_LiveFragment", "OnAttach called");
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d("LIVE_LiveFragment", "OnDetach called");
	}

	@Override
	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}

	private class ProcessListContents extends
			AsyncTask<String, String, List<LiveModel>> {
		boolean flag;
		public Dialog MpDialog;
		public ProcessListContents() {
			super();
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
		protected List<LiveModel> doInBackground(String... args) {
			List<LiveModel> list = new ArrayList<LiveModel>();
			list = new JSONParser(getRealActivity()).parseLiveList("0");
			return list;
		}

		@Override
		protected void onPostExecute(List<LiveModel> list) {
			((LinearLayout) v).removeAllViews();
			if (list.size() > 0) {
				((LinearLayout) v).addView(mlistView);
				listView = (AnimatedExpandableListView) mlistView
						.findViewById(R.id.live_page_listview2);
				listView.setFadingEdgeLength(0);
				adapter = new LIVE_Adapter(list, getRealActivity(), listView);
				listView.setAdapter(adapter);
				listView.setGroupIndicator(null);
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					listView.expandGroup(i);
				}
				listView.setOnGroupClickListener(new OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {

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
			//((BaseActivity)getRealActivity()).stopAnimateRightImage();
		}
	}

	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		new ProcessListContents(false).execute();
	}

	public void NetAsync(final boolean isShowHUD) {
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
								protected void onPreExecute() {
									// TODO Auto-generated method stub
									super.onPreExecute();
								};

								@Override
								protected void onPostExecute(
										List<LiveModel> list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();

								}
							}.execute();
						}
					}
				}, isShowHUD).execute();
	}
}
