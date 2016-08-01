package com.ats.NIEN.abongda.fragment.FULLSCR;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.R.anim;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.CustomAlertDialog;
import com.ats.NIEN.abongda.UTIL.DeviceUtil;
import com.ats.NIEN.abongda.UTIL.ExpandCollapseAnimation;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.model.CauthuModel;
import com.ats.NIEN.abongda.model.FullMatchModel;
import com.ats.NIEN.abongda.model.MatchDetailModel;
import com.ats.NIEN.abongda.model.MatchModel;
import vn.ats.aBongDa.R;

public class FULLSCR_MatchDetailFragment extends BaseFragment implements OnClickListener, OnRefreshListener {
	public int matchId;
	public int flag2, flag3, flag4;
	public View mHeaderP2, mHeaderP3, mHeaderP4, mHeaderP5;
	public View mContentP1, mContentP2, mContentP3, mContentP4, mContentP5;
	public View mdienBienLayout, mLichsuLayout, mMainLayout, mHolder, mContainer,v;

	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	public FullMatchModel model;
	public SwipeRefreshLayout swipeLayout;
	

	public FULLSCR_MatchDetailFragment(int matchId) {
		super();
		this.matchId = matchId;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContainer = inflater.inflate(R.layout.trandau_container, container,
				false);
		v = inflater.inflate(R.layout.trandau_main_layout, container,
				false);
		mHolder = inflater.inflate(R.layout.trandau_holder, container, false);
		
		mMainLayout = v.findViewById(R.id.match_scrollview);
		mdienBienLayout = inflater.inflate(R.layout.trandau_dienbien_item,
				container, false);
		mLichsuLayout = inflater.inflate(R.layout.trandau_doidau_item,
				container, false);
		mHeaderP2 = mMainLayout.findViewById(R.id.tdpanel2_header);
		mHeaderP3 = mMainLayout.findViewById(R.id.tdpanel3_header);
		mHeaderP4 = mMainLayout.findViewById(R.id.tdpanel4_header);
		mHeaderP5 = mMainLayout.findViewById(R.id.tdpanel5_header);
		mContentP1 = mMainLayout.findViewById(R.id.td_panel1);
		mContentP2 = mMainLayout.findViewById(R.id.tdpanel2_content);
		mContentP3 = mMainLayout.findViewById(R.id.tdpanel3_content);
		mContentP4 = mMainLayout.findViewById(R.id.tdpanel4_content);
		mContentP5 = mMainLayout.findViewById(R.id.tdpanel5_content);
		this.iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(getRealActivity());

		mHeaderP2.findViewById(R.id.tdpanel2_header_img).setOnClickListener(this);
		mHeaderP3.findViewById(R.id.tdpanel3_header_img).setOnClickListener(this);
		mHeaderP4.findViewById(R.id.tdpanel4_header_img).setOnClickListener(this);
		mHeaderP5.findViewById(R.id.tdpanel5_header_img).setOnClickListener(this);
		mHeaderP2.findViewById(R.id.tdpanel2_header_img).setTag(R.drawable.td_plus_icon);
		mHeaderP3.findViewById(R.id.tdpanel3_header_img).setTag(R.drawable.td_plus_icon);
		mHeaderP4.findViewById(R.id.tdpanel4_header_img).setTag(R.drawable.td_plus_icon);
		mHeaderP5.findViewById(R.id.tdpanel5_header_img).setTag(R.drawable.td_plus_icon);
		mHeaderP2.setOnClickListener(this);
		mHeaderP3.setOnClickListener(this);
		mHeaderP4.setOnClickListener(this);
		mHeaderP5.setOnClickListener(this);
		this.mSharePreferences = getRealActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		((BaseActivity) getRealActivity()).changeRightImageToBack();
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(R.color.base_app_color, 
                R.color.l0, 
                R.color.l1, 
                R.color.l2);
		return mContainer;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		Log.d("TAG11", ">> " + mSharePreferences.getInt("CALLBACK_FRAGMENT", 0));
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.MATCHDETAILFRAGMENT) {
			return;
		}
		((BaseActivity) getRealActivity()).changeTitle("Trận đấu");
		netAsync();
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences.Editor edit = this.mSharePreferences.edit();
		edit.putBoolean("IS_MATCH_AHEAD", false);
		edit.commit();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SharedPreferences.Editor edit = this.mSharePreferences.edit();
		edit.putBoolean("IS_MATCH_AHEAD", false);
		edit.commit();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		SharedPreferences.Editor edit = this.mSharePreferences.edit();
		edit.putBoolean("IS_MATCH_AHEAD", false);
		edit.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tdpanel2_header_img:
			expanseColapseView((ImageView)v, mContentP2);
			break;
		case R.id.tdpanel2_header:
			expanseColapseView((ImageView) mHeaderP2.findViewById(R.id.tdpanel2_header_img), mContentP2);
			break;
		case R.id.tdpanel3_header_img:
			expanseColapseView((ImageView)v, mContentP3);
			break;
		case R.id.tdpanel3_header:
			expanseColapseView((ImageView) mHeaderP3.findViewById(R.id.tdpanel3_header_img), mContentP3);
			break;
		case R.id.tdpanel4_header_img:
			expanseColapseView((ImageView)v, mContentP4);
			break;
		case R.id.tdpanel4_header:
			expanseColapseView((ImageView) mHeaderP4.findViewById(R.id.tdpanel4_header_img), mContentP4);
			break;
		case R.id.tdpanel5_header_img:
			expanseColapseView((ImageView)v, mContentP5);
			break;
		case R.id.tdpanel5_header:
			expanseColapseView((ImageView) mHeaderP5.findViewById(R.id.tdpanel5_header_img), mContentP5);
			break;
		case R.id.tdpanel2_dc_1:
			perfomBet(4);
			break;
		case R.id.tdpanel2_dc_3:
			perfomBet(6);
			break;
		case R.id.tdpanel2_dc_4:
			perfomBet(1);
			break;
		case R.id.tdpanel2_dc_5:
			perfomBet(2);
			break;
		case R.id.tdpanel2_dc_6:
			perfomBet(3);
			break;
		case R.id.tdpanel2_dc_7:
			perfomBet(7);
			break;
		case R.id.tdpanel3_receivenotif:
			perfomNotif();
		default:
			break;
		}
	}
	@SuppressWarnings("deprecation")
	private void perfomNotif(){
		String receiveNotifString = new JSONParser(getRealActivity())
			.performNotif(this.mSharePreferences.getString("registration_id", ""), matchId+"");
		if (receiveNotifString.equals("1")) {
			new CustomAlertDialog(getRealActivity(), "Thông báo",
					"Bạn sẽ nhận được thông báo về \nkết quả trận đấu này khi kết thúc!", "OK"){
				
			}.show();
			((LinearLayout) mContentP3.findViewById(R.id.tdpanel3_receivenotif))
			.setOnClickListener(null);
			((LinearLayout) mContentP3.findViewById(R.id.tdpanel3_receivenotif))
			.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
		} else {
			new CustomAlertDialog(getRealActivity(), "Lỗi",
					"Có lỗi xảy ra\nVui lòng thử lại sau !", "OK").show();
		}
	}
	private void perfomBet(final int betType){
		int userId = this.mSharePreferences.getInt("CURRENT_USER_ID", 0);
		SharedPreferences.Editor edit = this.mSharePreferences.edit();
		edit.putInt("MATCHID", matchId);
		edit.commit();
		MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.DATCUOCFRAGMENT,
				new ArrayList<Object>(){{
					add(betType);
					add(model);
				}});
		((MainActivity)getRealActivity()).switchContent(frag);
		/*if (userId != 0) {
			
		}else{
			MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.DANGNHAPFRAGMENT,
					new ArrayList<Object>(){{
						add(betType);
						add(model);
					}});
			((MainActivity)getRealActivity()).switchContent(frag);
		}*/
		/*if (userId == 0) {
			Intent i = new Intent(getRealActivity(), DangnhapActivity.class);
			i.putExtra("CURRENT_MATCH_ID", matchId);
			i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
			startActivity(i);
		}else{

			MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.DATCUOCFRAGMENT,
					new ArrayList<Object>(){{
						add(betType);
						add(model);
					}});
			((MainActivity)getRealActivity()).switchContent(frag);
		}	*/
	}
	public void expanseColapseView(ImageView v1, View v2){
		ExpandCollapseAnimation animation = null;
		if ((Integer)v1.getTag() == R.drawable.td_plus_icon) {
			v1.setImageResource(R.drawable.td_minus_icon);
			v1.setTag(R.drawable.td_minus_icon);
			animation = new ExpandCollapseAnimation(v2, 300, 1);
		}else{
			v1.setImageResource(R.drawable.td_plus_icon);
			v1.setTag(R.drawable.td_plus_icon);
			animation = new ExpandCollapseAnimation(v2, 300, 0);
		}
		v2.startAnimation(animation);
	}

	private class MatchDetailAsync extends
			AsyncTask<String, String, FullMatchModel> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			((ViewGroup)mContainer).removeAllViews();
			((ViewGroup)mContainer).addView(mHolder);
		}

		@Override
		protected FullMatchModel doInBackground(String... params) {
			// TODO Auto-generated method stub
			// ClubInforModel club = new
			// JSONParser(getRealActivity()).parseClubInfor("0", mCauthuId);
			model = new JSONParser(getRealActivity()).getMatchInfo("12345",
					matchId);
			return model;
		}

		@Override
		protected void onPostExecute(FullMatchModel m) {
			// TODO Auto-generated method stub
			super.onPostExecute(m);
			if (isAdded()) {
				setPanel1Content(m);
				setPanel2Content(m);
				setPanel3Content(m);
				setPanel4Content(m);
				setPanel5Content(m);
				
				((ViewGroup)mContainer).removeAllViews();
				((ViewGroup)mContainer).addView(v);
				new DeviceUtil(getRealActivity()).getAllSharePref();
			}
		}

	}

	private void setPanel1Content(FullMatchModel m) {
		// //////////////////////////////////////////////////////////////////
		iMAGELOADER_ImageLoader.DisplayImage(
				"http://cdn.ats.vn/upload/football/club/" + m.get_homeLogo(),
				(ImageView) mContentP1.findViewById(R.id.tdpanel1_hostlogo));
		iMAGELOADER_ImageLoader.DisplayImage(
				"http://cdn.ats.vn/upload/football/club/" + m.get_guestLogo(),
				(ImageView) mContentP1.findViewById(R.id.tdpanel1_guestlogo));
		mContentP1.findViewById(R.id.tdpanel1_hostlogo).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*FragmentManager fm = getFragmentManager();
						FULLSCR_ClubFragment frag = new FULLSCR_ClubFragment(model
								.get_home_id());
						((BaseActivity) getRealActivity())
								.changeRightImageToBack();
						fm.beginTransaction()
								.setCustomAnimations(anim.slide_in_left,
										anim.slide_out_right)
								.replace(R.id.content_frame, frag)
								.addToBackStack(getTag()).commit();*/
						MAINVIEW_MainFragment mf = new MAINVIEW_MainFragment(Constant.CLUBFRAGMENT,
								new ArrayList<Object>(){{
									add(model.get_home_id());
								}});
						((MainActivity)getRealActivity()).switchContent(mf);
					}
				});
		mContentP1.findViewById(R.id.tdpanel1_guestlogo).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*FragmentManager fm = getFragmentManager();
						FULLSCR_ClubFragment frag = new FULLSCR_ClubFragment(model
								.get_guest_id());
						((BaseActivity) getRealActivity())
								.changeRightImageToBack();
						fm.beginTransaction()
								.setCustomAnimations(anim.slide_in_left,
										anim.slide_out_right)
								.replace(R.id.content_frame, frag)
								.addToBackStack(getTag()).commit();*/
						MAINVIEW_MainFragment mf = new MAINVIEW_MainFragment(Constant.CLUBFRAGMENT,
								new ArrayList<Object>(){{
									add(model.get_guest_id());
								}});
						((MainActivity)getRealActivity()).switchContent(mf);

					}
				});

		// //////////////////////////////////////////////////////////////////
		
		ViewGroup hVg = (ViewGroup) mContentP1
				.findViewById(R.id.tdpanel1_host_vg);
		for (int i = 0; i < m.get_homeForm().size(); i++) {
			switch (m.get_homeForm().get(i)) {
			case 2:
				((ImageView) hVg.getChildAt(i))
						.setImageResource(R.drawable.td_icon_bai);
				break;
			case 0:
				((ImageView) hVg.getChildAt(i))
						.setImageResource(R.drawable.td_icon_hoa);
				break;
			case 1:
				((ImageView) hVg.getChildAt(i))
						.setImageResource(R.drawable.td_icon_thang);
				break;
			default:
				break;
			}
		}
		ViewGroup gVg = (ViewGroup) mContentP1
				.findViewById(R.id.tdpanel1_guest_vg);
		for (int i = 0; i < m.get_guestForm().size(); i++) {
			switch (m.get_guestForm().get(i)) {
			case 2:
				((ImageView) gVg.getChildAt(i))
						.setImageResource(R.drawable.td_icon_bai);
				break;
			case 0:
				((ImageView) gVg.getChildAt(i))
						.setImageResource(R.drawable.td_icon_hoa);
				break;
			case 1:
				((ImageView) gVg.getChildAt(i))
						.setImageResource(R.drawable.td_icon_thang);
				break;
			default:
				break;
			}
		}

		// //////////////////////////////////////////////////////////////////
		((TextView) mContentP1.findViewById(R.id.tdpanel1_host_name)).setText(m
				.get_home());
		((TextView) mContentP1.findViewById(R.id.tdpanel1_guest_name))
				.setText(m.get_guest());
		((TextView) mContentP1.findViewById(R.id.tdpanel1_host_score))
				.setText(m.get_homeGoal() + "");
		((TextView) mContentP1.findViewById(R.id.tdpanel1_guest_score))
				.setText(m.get_guestGoal() + "");
		((TextView) mContentP1.findViewById(R.id.tdpanel1_isFulltime))
				.setText(m.get_matchMinute() + "");
		if (m.get_homePenaltyShoot() + m.get_guestPenaltyShoot() > 0) {
			((TextView) mContentP1.findViewById(R.id.tdpanel1_penhome))
					.setText(m.get_homePenaltyShoot() + "");
			((TextView) mContentP1.findViewById(R.id.tdpanel1_penguest))
					.setText(m.get_guestPenaltyShoot() + "");
		} else {
			mContentP1.findViewById(R.id.tdpanel1_pen).setVisibility(View.GONE);
		}
		((TextView) mContentP1.findViewById(R.id.tdpanel1_stadium)).setText(m
				.get_matchStadium());

		// //////////////////////////////////////////////////////////////////
		LinearLayout botLayout = (LinearLayout) mContentP1
				.findViewById(R.id.tdpanel1_bot);
		botLayout.removeAllViews();
		LayoutInflater inflater = (LayoutInflater) getRealActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < m.get_info_list().size(); i++) {
			MatchDetailModel mfl = m.get_info_list().get(i);
			LinearLayout mdi = (LinearLayout) inflater.inflate(
					R.layout.trandau_dienbien_item, null);
			if (mfl.isHomeTurn()) {
				mdi.findViewById(R.id.tddb_guest).setVisibility(View.INVISIBLE);
				((TextView) mdi.findViewById(R.id.tddb_time)).setText(mfl
						.getStatus());
				((TextView) mdi.findViewById(R.id.tddb_host_text)).setText(mfl
						.getHomeInfo());
				((ImageView) mdi.findViewById(R.id.tddb_host_img))
						.setImageResource(mfl.getMatchDetailImage());
			} else {
				mdi.findViewById(R.id.tddb_host).setVisibility(View.INVISIBLE);
				((TextView) mdi.findViewById(R.id.tddb_time)).setText(mfl
						.getStatus());
				((TextView) mdi.findViewById(R.id.tddb_guest_text)).setText(mfl
						.getGuestInfo());
				((ImageView) mdi.findViewById(R.id.tddb_guest_img))
						.setImageResource(mfl.getMatchDetailImage());
			}
			botLayout.addView(mdi);
		}
		Log.d("MATCH", "info list: " + m.get_info_list().size());
	}

	@SuppressWarnings("deprecation")
	private void setPanel2Content(FullMatchModel m) {

		LinearLayout dc1 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_1);
		LinearLayout dc2 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_2);
		LinearLayout dc3 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_3);
		LinearLayout dc4 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_4);
		LinearLayout dc5 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_5);
		LinearLayout dc6 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_6);
		LinearLayout dc7 = (LinearLayout) mContentP2
				.findViewById(R.id.tdpanel2_dc_7);
		if (m.get_matchStatus() == 1 || m.get_matchStatus() > 2) {
			// ////////////////////////////////////////////////////////////////////////
			dc1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
			dc2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_deact_f));
			dc3.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
			dc4.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
			dc5.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
			dc6.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
			dc7.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));

			dc1.setOnClickListener(null);
			dc3.setOnClickListener(null);
			dc4.setOnClickListener(null);
			dc5.setOnClickListener(null);
			dc6.setOnClickListener(null);
			dc7.setOnClickListener(null);
		} else {
			((TextView) mHeaderP2
				.findViewById(R.id.tdpanel2_header_text)).setText("Tỉ lệ cược (nhấn để đặt cược)");
			dc1.setOnClickListener(this);
			dc3.setOnClickListener(this);
			dc4.setOnClickListener(this);
			dc5.setOnClickListener(this);
			dc6.setOnClickListener(this);
			dc7.setOnClickListener(this);
		}
		// ////////////////////////////////////////////////////////////////////////
		String t1 = (m.get_homeWinHandicap()==null || m.get_homeWinHandicap().equals("null"))?"":m.get_homeWinHandicap();
		((TextView) mContentP2.findViewById(R.id.tdpanel2_dc_1text))
				.setText("Chủ nhà\n" + t1);
		if (t1.equalsIgnoreCase("")) {
			dc1.setOnClickListener(null);
			dc1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
		}
		String t2 = (m.get_handicap()==null || m.get_handicap().equals("null"))?"":m.get_handicap();
		((TextView) mContentP2.findViewById(R.id.tdpanel2_dc_2text))
				.setText("Chấp\n" + t2);
		if (t2.equalsIgnoreCase("")) {
			dc2.setOnClickListener(null);
			dc2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_deact_f));
		}
		String t3 = (m.get_guestWinHandicap()==null || m.get_guestWinHandicap().equals("null"))?"":m.get_guestWinHandicap();
		((TextView) mContentP2.findViewById(R.id.tdpanel2_dc_3text))
				.setText("Khách\n" + t3);
		if (t3.equalsIgnoreCase("")) {
			dc3.setOnClickListener(null);
			dc3.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
		}
		String t4 = (m.get_homeWin1x2()==null || m.get_homeWin1x2().equals("null"))?"":m.get_homeWin1x2();
		((TextView) mContentP2.findViewById(R.id.tdpanel2_dc_4text))
				.setText("Chủ nhà\n" + t4);
		if (t4.equalsIgnoreCase("")) {
			dc4.setOnClickListener(null);
			dc4.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
		}
		String t5 = (m.get_draw1x2()==null || m.get_draw1x2().equals("null"))?"":m.get_draw1x2();
		((TextView) mContentP2.findViewById(R.id.tdpanel2_dc_5text))
				.setText("Hòa\n" + t5);
		if (t5.equalsIgnoreCase("")) {
			dc5.setOnClickListener(null);
			dc5.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
		}
		String t6 = (m.get_guestWin1x2()==null || m.get_guestWin1x2().equals("null"))?"":m.get_guestWin1x2();
		((TextView) mContentP2.findViewById(R.id.tdpanel2_dc_6text))
				.setText("Khách\n" + t6);
		if (t6.equalsIgnoreCase("")) {
			dc6.setOnClickListener(null);
			dc6.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.td_tlc_act_f));
		}
	}

	@SuppressWarnings("deprecation")
	private void setPanel3Content(FullMatchModel m) {
		((TextView) mContentP3.findViewById(R.id.tdpanel3_channel)).setText(m
				.get_tvChannel());
		if (m.get_tvChannel().length() == 0) {
			((TextView) mContentP3.findViewById(R.id.tdpanel3_channel)).setVisibility(View.GONE);
		}
		if (m.get_matchStatus() == 1 || m.get_matchStatus() > 2) {
			((LinearLayout) mContentP3.findViewById(R.id.tdpanel3_receivenotif))
					.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.td_tlc_act_f));
			((LinearLayout) mContentP3.findViewById(R.id.tdpanel3_receivenotif))
					.setOnClickListener(null);
			((TextView) mContentP3.findViewById(R.id.tdpanel3_channel))
					.setVisibility(View.GONE);
		} else {
			((LinearLayout) mContentP3.findViewById(R.id.tdpanel3_receivenotif))
					.setOnClickListener(this);
		}
	}

	private void setPanel4Content(FullMatchModel m) {

		if (m.get_lineup_list().size() < 1) {
			mHeaderP4.setVisibility(View.GONE);
			mContentP4.setVisibility(View.GONE);
		} else {
			LinearLayout hostOffL = (LinearLayout) mContentP4
					.findViewById(R.id.tdpanel4_lineup_hostOff);
			LinearLayout guestOffL = (LinearLayout) mContentP4
					.findViewById(R.id.tdpanel4_lineup_guestOff);
			LinearLayout hostL = (LinearLayout) mContentP4
					.findViewById(R.id.tdpanel4_lineup_host);
			LinearLayout guestL = (LinearLayout) mContentP4
					.findViewById(R.id.tdpanel4_lineup_guest);
			hostL.removeAllViews();
			hostOffL.removeAllViews();
			guestL.removeAllViews();
			guestOffL.removeAllViews();
			for (int i = 0; i < m.get_lineup_list().size(); i++) {
				CauthuModel c = m.get_lineup_list().get(i);

				if (c.getClubId() == m.get_home_id() && c.getRole() == 2) {
					hostL.addView(setLineUpList(c, 3));
				} else if (c.getClubId() == m.get_home_id() && c.getRole() == 1) {
					hostOffL.addView(setLineUpList(c, 1));
				} else if (c.getClubId() == m.get_guest_id()
						&& c.getRole() == 2) {
					guestL.addView(setLineUpList(c, 4));
				} else if (c.getClubId() == m.get_guest_id()
						&& c.getRole() == 1) {
					guestOffL.addView(setLineUpList(c, 2));
				}
			}

		}
	}

	private View setLineUpList(CauthuModel m, int flag) {
		LayoutInflater inflater = (LayoutInflater) getRealActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 LinearLayout item;
		 TextView tv;
		 ImageSpan is;
		 SpannableString text;
		 if (m.getTimeIn() > 0) {
			 is = new ImageSpan(getRealActivity(), R.drawable.substitution_in);	
		 }else{
			 is = new ImageSpan(getRealActivity(), R.drawable.substitution_out);	
		 }
		 if (flag == 1 || flag == 3) {
			item = (LinearLayout) inflater.inflate(R.layout.lineuplist_layout, null);
			tv = (TextView) item.findViewById(R.id.lup_text);
			if (m.getTimeIn() > 0) {
				text = new SpannableString(Html.fromHtml(m.getName() + " " + "<font color=#5FABD6>("+m.getTimeIn()+")</font>")); 
				text.setSpan(is, m.getName().length(), 1 + m.getName().length(), 0);
			}else if (m.getTimeOut() > 0){
				text = new SpannableString(Html.fromHtml(m.getName() + " " + "<font color=#5FABD6>("+m.getTimeOut()+")</font>")); 
				text.setSpan(is, m.getName().length(), 1 + m.getName().length(), 0);
			}else{
				text = new SpannableString(Html.fromHtml(m.getName() + " ")); 
				//text.setSpan(is, m.getName().length(), 1 + m.getName().length(), 0);
			}
			 
		 }else{
			item = (LinearLayout) inflater.inflate(R.layout.lineuplist_g_layout, null);
			tv = (TextView) item.findViewById(R.id.lup_g_text);
			if (m.getTimeIn() > 0) {
				text = new SpannableString(Html.fromHtml("<font color=#5FABD6>"+"-("+m.getTimeIn()+"')</font>" + " " + m.getName())); 
				text.setSpan(is, 0, 1, 0);
			}else if (m.getTimeOut() > 0) {
				text = new SpannableString(Html.fromHtml("<font color=#5FABD6>"+"-("+m.getTimeOut()+"')</font>" + " " + m.getName())); 
				text.setSpan(is, 0, 1, 0);
			}else{
				text = new SpannableString(Html.fromHtml(" " + m.getName())); 
				//text.setSpan(is, 0, 1, 0);
			}
		 }
		 
		 //ImageSpan is = new ImageSpan(getRealActivity(), R.drawable.substitution_in);
		 tv.setText(text);
		 return item;
		 
	}

	private void setPanel5Content(FullMatchModel m) {
		LayoutInflater inflater = (LayoutInflater) getRealActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		((LinearLayout) mContentP5).removeAllViews();
		for (final MatchModel n : m.get_matchArray()) {
			LinearLayout item = (LinearLayout) inflater.inflate(R.layout.trandau_doidau_item, null);
			((TextView)item.findViewById(R.id.tddd_ngaythang)).setText(n.getMatchStartDateShortYear()+"\n"+n.getMatchStartTimeWithoutPlus()+":00");
			//((TextView)item.findViewById(R.id.tddd_ngaythang)).setText(n.getMatchDateString());
			((ImageView)item.findViewById(R.id.tddd_league)).setImageResource(n.getLeagueLogoFromMatch());
			((TextView)item.findViewById(R.id.tddd_host)).setText(n.getHome());
			((TextView)item.findViewById(R.id.tddd_guest)).setText(n.getGuest());
			((TextView)item.findViewById(R.id.tddd_score)).setText(n.getHomeGoal()+"-"+n.getGuestGoal());
			((LinearLayout) mContentP5).addView(item);
			item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.MATCHDETAILFRAGMENT,
							new ArrayList<Object>(){{
								add(n.getMatchId());
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
		new InternetChecking(new WeakReference<Context>(getRealActivity()),
				new InternetCheckingCallback() {

					@Override
					public void onCheckComplete(boolean result,
							final Dialog progressDialog) {
						// TODO Auto-generated method stub
						if (!result) {
							progressDialog.dismiss();
						} else {
							new MatchDetailAsync() {
								@Override
								protected void onPostExecute(FullMatchModel m) {
									// TODO Auto-generated method stub
									super.onPostExecute(m);
									progressDialog.dismiss();
								}
							}.execute();
						}
					}
				}).execute();
	}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
		new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                
            }
        },0);
		netAsync();
	}

}
