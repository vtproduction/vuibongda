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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.ats.NIEN.abongda.model.CauthuModel;
import com.ats.NIEN.abongda.model.TintucModel;
import vn.ats.aBongDa.R;

public class FULLSCR_CauthuFragment extends BaseFragment {
	public int mCauthuId;
	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	public CauthuModel model;
	public LinearLayout mainPanel;

	public FULLSCR_CauthuFragment(int mCauthuId) {
		// TODO Auto-generated constructor stub
		super();
		this.mCauthuId = mCauthuId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater
				.inflate(R.layout.cauthu_main_layout, container, false);
		mainPanel = (LinearLayout) v.findViewById(R.id.cauthudetail_main_panel);
		iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(getRealActivity());
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.CAUTHUFRAGMENT) {
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

	private class CauthuInforContent extends
			AsyncTask<String, String, CauthuModel> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected CauthuModel doInBackground(String... params) {
			// TODO Auto-generated method stub
			// ClubInforModel club = new
			// JSONParser(getRealActivity()).parseClubInfor("0", mCauthuId);
			CauthuModel model = new JSONParser(getRealActivity()).parseCauthu(
					"0", mCauthuId);
			return model;
		}

		@Override
		protected void onPostExecute(CauthuModel result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			((BaseActivity) getRealActivity()).changeTitle(result.getName());
			ImageView logo = (ImageView) mainPanel
					.findViewById(R.id.cauthudetail_cauthulogo);
			iMAGELOADER_ImageLoader.DisplayImage(result.getImage().replaceAll(" ", "%20"), logo);

			TextView des = (TextView) mainPanel
					.findViewById(R.id.cauthudetail_cauthudes);
			des.setText(result.getName() + "\nVị trí: " + result.getPosition()
					+ "\nSố áo: " + result.getNumber());

			TextView diachi = (TextView) mainPanel
					.findViewById(R.id.cauthudetail_cauthudiachi);
			diachi.setText(result.getHeight());

			TextView web = (TextView) mainPanel
					.findViewById(R.id.cauthudetail_cauthuweb);
			web.setText(result.getWeigt());

			TextView email = (TextView) mainPanel
					.findViewById(R.id.cauthudetail_cauthuemail);
			email.setText(result.getDateOfBirth());

			TextView tel = (TextView) mainPanel
					.findViewById(R.id.cauthudetail_cauthutel);
			tel.setText(result.getNationality());

			LinearLayout tintuc = (LinearLayout) mainPanel
					.findViewById(R.id.cauthudetail_cauthutintuc);
			LinearLayout tintucItem;
			LayoutInflater inflater = (LayoutInflater) getRealActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			tintuc.removeAllViews();
			List<TintucModel> listTintuc = result.getNews();
			if (listTintuc != null) {
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
							final String htmlContent = "<b>"
									+ tintucObject.getTitle() + "</b></br>"
									+ tintucObject.getContent();
							MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.TINTUCCHITIETFRAGMENT,
									new ArrayList<Object>(){{
										add(htmlContent);
									}});
							((MainActivity)getRealActivity()).switchContent(frag);
						}
					});
				}
			}

		}

	}
	
	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		netAsync();
	}

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
							new CauthuInforContent() {
								@Override
								protected void onPostExecute(
										CauthuModel list) {
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
