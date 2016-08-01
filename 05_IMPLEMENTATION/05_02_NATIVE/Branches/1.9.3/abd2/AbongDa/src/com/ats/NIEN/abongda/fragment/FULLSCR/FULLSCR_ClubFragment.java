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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.model.ClubInforModel;
import com.ats.NIEN.abongda.model.TintucModel;
import vn.ats.aBongDa.R;

public class FULLSCR_ClubFragment extends BaseFragment {
	public int mClubId;
	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	public ClubInforModel model;
	public LinearLayout mainPanel;
	public String clubName;

	public FULLSCR_ClubFragment(int mClubId) {
		super();
		this.mClubId = mClubId;
		this.model = new ClubInforModel();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.clubdetail_main_layout, container,
				false);
		mainPanel = (LinearLayout) v.findViewById(R.id.clubdetail_main_panel);
		iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(getRealActivity());
		Button btn = (Button) v.findViewById(R.id.clubdetail_clubbtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*FULLSCR_DoihinhFragment frag = new FULLSCR_DoihinhFragment(mClubId, clubName);
				FragmentManager fm = getFragmentManager();
				fm.beginTransaction()
						.setCustomAnimations(anim.slide_in_left,
								anim.slide_out_right)
						.replace(R.id.content_frame, frag)
						.addToBackStack(getTag()).commit();*/
				MAINVIEW_MainFragment mf = new MAINVIEW_MainFragment(Constant.DOIHINHFRAGMENT,
						new ArrayList<Object>(){{
							add(mClubId);
							add(clubName);
						}});
				((MainActivity)getRealActivity()).switchContent(mf);

			}
		});
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.CLUBFRAGMENT) {
			return;
		}
		netAsync();

		((BaseActivity) getRealActivity()).changeRightImageToBack();

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

	private class ClubInforContent extends
			AsyncTask<String, String, ClubInforModel> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ClubInforModel doInBackground(String... params) {
			// TODO Auto-generated method stub
			ClubInforModel club = new JSONParser(getRealActivity())
					.parseClubInfor("0", mClubId);
			return club;
		}

		@Override
		protected void onPostExecute(ClubInforModel result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			ImageView logo = (ImageView) mainPanel
					.findViewById(R.id.clubdetail_clublogo);
			iMAGELOADER_ImageLoader.DisplayImage(result.getLogo(), logo);
			clubName = result.getNane();
			TextView des = (TextView) mainPanel
					.findViewById(R.id.clubdetail_clubdes);
			des.setText(result.getNane() + "\nThành lập: "
					+ result.getFounded() + "\nSVĐ: " + result.getStadium()
					+ "\nSức chứa: " + result.getCapacity() + "\nHLV: "
					+ result.getManager());

			TextView diachi = (TextView) mainPanel
					.findViewById(R.id.clubdetail_clubdiachi);
			diachi.setText(result.getAddress());

			TextView web = (TextView) mainPanel
					.findViewById(R.id.clubdetail_clubweb);
			web.setText(result.getWeb());

			TextView email = (TextView) mainPanel
					.findViewById(R.id.clubdetail_clubemail);
			email.setText(result.getEmail());

			TextView tel = (TextView) mainPanel
					.findViewById(R.id.clubdetail_clubtel);
			tel.setText(result.getPhone());

			TextView honours = (TextView) mainPanel
					.findViewById(R.id.clubdetail_clubdanhhieu);
			honours.setText(Html.fromHtml(result.getHonours()));

			LinearLayout tintuc = (LinearLayout) mainPanel
					.findViewById(R.id.clubdetail_clubtintuc);
			LinearLayout tintucItem;
			LayoutInflater inflater = (LayoutInflater) getRealActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			tintuc.removeAllViews();
			List<TintucModel> listTintuc = result.getNews();
			for (int i = 0; i < listTintuc.size(); i++) {
				final TintucModel tintucObject = listTintuc.get(i);
				tintucItem = (LinearLayout) inflater.inflate(
						R.layout.clubdetail_tintuc_item, null);
				TextView text = (TextView) tintucItem
						.findViewById(R.id.clubdetail_tintucitem_text);
				text.setText(tintucObject.getTitle());
				tintuc.addView(tintucItem);

				tintucItem.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final String htmlContent = "<b>" + tintucObject.getTitle()
								+ "</b></br>" + tintucObject.getContent();
						MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.TINTUCCHITIETFRAGMENT,
								new ArrayList<Object>(){{
									add(htmlContent);
								}});
						((MainActivity)getRealActivity()).switchContent(frag);
					}
				});
			}
			((BaseActivity) getRealActivity()).changeTitle(result.getNane());

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
							new ClubInforContent() {
								@Override
								protected void onPostExecute(
										ClubInforModel list) {
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
