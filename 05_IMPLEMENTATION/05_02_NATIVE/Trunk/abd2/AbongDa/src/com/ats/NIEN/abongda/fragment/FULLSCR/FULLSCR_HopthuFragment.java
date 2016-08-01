package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.adapter.HopthuAdapter;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.model.HopthuModel;
import vn.ats.aBongDa.R;

public class FULLSCR_HopthuFragment extends BaseFragment {
	public ListView mListView;
	public HopthuAdapter mAdapter;
	public int currentUserId;
	public String currentUserName;
	public List<HopthuModel> models;
	public ProgressHUD mDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater
				.inflate(R.layout.hopthu_main_layout, container, false);
		mListView = (ListView) v.findViewById(R.id.hopthu_listivew);
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.HOPTHUFRAGMENT) {
			return;
		}
		((BaseActivity) getRealActivity()).changeRightImageToBack();
		mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		currentUserName = mSharePreferences.getString("CURRENT_USER_NAME", "");
		NetAsync(getView());
	}

	

	private class ProcessListContents extends
			AsyncTask<String, String, List<HopthuModel>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<HopthuModel> doInBackground(String... args) {

			models = new ArrayList<HopthuModel>();
			models = (new JSONParser(getRealActivity())).parseAllMessage(
					currentUserName, 0);
			return models;
		}

		@Override
		protected void onPostExecute(final List<HopthuModel> list) {
			mAdapter = new HopthuAdapter(getRealActivity(), 0, list);
			mListView.setAdapter(mAdapter);
			((BaseActivity) getRealActivity()).changeTitle("Hộp thư");
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					final HopthuModel model = mAdapter.getItem(position);
					new setMessReaded(model.getId()+"").execute();
					@SuppressWarnings("serial")
					MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.HOPTHUCHITIETFRAGMENT,
							new ArrayList<Object>(){{
								add(model);
							}});
					((MainActivity)getRealActivity()).switchContent(frag);

				}
			});
			
		}
	}

	@Override
	public void netAsync() {
		// TODO Auto-generated method stub
		super.netAsync();
		NetAsync(getView());
	}
	
	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		netAsync();
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
										List<HopthuModel> list) {
									// TODO Auto-generated method stub
									super.onPostExecute(list);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}

	private class setMessReaded extends
			AsyncTask<Void, Void, Void> {
		private String userId;
		
		
		public setMessReaded(String userId) {
			super();
			this.userId = userId;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//mDialog = ProgressHUD.show(getRealActivity(), "", true, false, null);
			
		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			new JSONParser(getRealActivity()).setMessReaded(userId);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//mDialog.dismiss();
			
		}

		
	}
}
