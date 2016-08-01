package com.ats.NIEN.abongda.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.DeviceUtil;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_CauthuFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_ClubFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_DangNhapFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_DangkyFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_DatCuocFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_DoiTTFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_DoihinhFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_DoimkFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_HopthuChitietFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_HopthuFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_LichthidauDoibongFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_MatchDetailFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TintucChitietFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TopcaothuFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TopdaigiaFragment;
import com.ats.NIEN.abongda.fragment.LIVE.LIVE_LiveFragment;
import com.ats.NIEN.abongda.fragment.LIVE.LIVE_Next24hFragment;
import com.ats.NIEN.abongda.fragment.LIVE.LIVE_TintucFragment;
import com.ats.NIEN.abongda.fragment.TAIKHOAN.TAIKHOAN_DatcuocFragment;
import com.ats.NIEN.abongda.fragment.TAIKHOAN.TAIKHOAN_HosoFragment;
import com.ats.NIEN.abongda.fragment.TAIKHOAN.TAIKHOAN_LichsuFragment;
import com.ats.NIEN.abongda.fragment.TAIKHOAN.TAIKHOAN_ThongkeFragment;
import com.ats.NIEN.abongda.fragment.TTGD.TTGD_LichthidauFragment;
import com.ats.NIEN.abongda.fragment.TTGD.TTGD_ThongkeFragment;
import com.ats.NIEN.abongda.fragment.TTGD.TTGD_XephangFragment;
import com.ats.NIEN.abongda.model.FullMatchModel;
import com.ats.NIEN.abongda.model.HopthuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;
import com.google.android.gms.internal.ac;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

@SuppressLint("InflateParams")
public class MAINVIEW_MainFragment extends BaseFragment implements
		OnTabChangeListener, Constant {
	public int actionCode;
	public int currentUserId;
	public View mRoot;
	public TabHost mTabHost;
	public int mCurrentTab;
	public Activity mActivity;
	public int mMatchId;
	public List<Object> argsList;

	SharedPreferences prfs;
	public boolean isGoingToSigninAct = false, isFirstTimeCreate = true;
	public BaseFragment currentFragment;

	public enum PageType {
		TYPE_LIVE, TYPE_FULLSCR, TYPE_TTGD, TYPE_TAIKHOAN
	};

	public PageType mPageType;
	BaseFragment[] ttgdList = new BaseFragment[3];

	public void setPageType() {
		Log.d("MAINVIEW_MainFragment Log", "\ncurrent Actioncode: "
				+ this.actionCode);
		switch (this.actionCode) {
		case TINTUC_CODE:
		case LIVESCORE_CODE:
		case NEXT24H_CODE:
			this.mPageType = PageType.TYPE_LIVE;
			break;
		case LEAGUE_BDL_CODE:
		case LEAGUE_CL_CODE:
		case LEAGUE_EL_CODE:
		case LEAGUE_L1_CODE:
		case LEAGUE_LFP_CODE:
		case LEAGUE_PL_CODE:
		case LEAGUE_SA_CODE:
		case LICHTHIDAU_CODE:
		case XEPHANG_CODE:
		case THONGKE_CODE:
			this.mPageType = PageType.TYPE_TTGD;
			break;
		case TK_HOSO_CODE:
		case TK_DATCUOC_CODE:
		case TK_LICHSU_CODE:
		case TK_THONGKE_CODE:
			this.mPageType = PageType.TYPE_TAIKHOAN;
			break;
		default:
			this.mPageType = PageType.TYPE_FULLSCR;
			break;
		}
	}

	public MAINVIEW_MainFragment(int actionCode) {
		super();
		this.actionCode = actionCode;
		setPageType();
		Log.d("MAINVIEW_MainFragment Log: ",
				"INIT Mainview: " + mPageType.toString());
	}

	public MAINVIEW_MainFragment(int actionCode, int matchId) {
		this.actionCode = actionCode;
		this.mMatchId = matchId;
		setPageType();
		Log.d("MAINVIEW_MainFragment Log: ",
				"INIT Mainview: " + mPageType.toString());
	}

	public MAINVIEW_MainFragment(int actionCode, List<Object> argsList) {
		super();
		this.actionCode = actionCode;
		this.argsList = argsList;
		setPageType();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	int dangnhapBackActionCode = 0;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		((BaseActivity)getRealActivity()).showActionBar();
		Log.d("MAINVIEW_MainFragment Log", "resume main view, actioncode: "
				+ actionCode + " - " + isGoingToSigninAct);
		if (actionCode == TINTUC_CODE) {
			((BaseActivity) getRealActivity()).getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		} else {
			((BaseActivity) getRealActivity()).getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
		
		if (mPageType == PageType.TYPE_FULLSCR) {
			((BaseActivity)getRealActivity()).changeRightImageToBack();
		}
		((BaseActivity) getRealActivity()).showRightImage();
		if (mPageType == PageType.TYPE_FULLSCR) {
			FragmentManager fragMan = getFragmentManager();
			FragmentTransaction fragTransaction = fragMan.beginTransaction();
			BaseFragment frag = new BaseFragment();
			switch (actionCode) {
			case DOIMKFRAGMENT:
				frag = new FULLSCR_DoimkFragment();
				break;
			case DANGNHAPFRAGMENT:
				frag = new FULLSCR_DangNhapFragment(dangnhapBackActionCode, argsList);
				break;
			case DANGKYFRAGMENT:
				frag = new FULLSCR_DangkyFragment((Boolean) argsList.get(0));
				break;
			case TINTUCCHITIETFRAGMENT:
				frag = new FULLSCR_TintucChitietFragment(
						(String) argsList.get(0));
				break;
			case CAUTHUFRAGMENT:
				frag = new FULLSCR_CauthuFragment((Integer) argsList.get(0));
				break;
			case CLUBFRAGMENT:
				frag = new FULLSCR_ClubFragment((Integer) argsList.get(0));
				break;
			case DATCUOCFRAGMENT:
				frag = new FULLSCR_DatCuocFragment((Integer) argsList.get(0),
						(FullMatchModel) argsList.get(1));
				break;
			case DOIHINHFRAGMENT:
				frag = new FULLSCR_DoihinhFragment((Integer) argsList.get(0),
						(String) argsList.get(1));
				break;
			case DOITTFRAGMENT:
				frag = new FULLSCR_DoiTTFragment();
				break;
			case HOPTHUCHITIETFRAGMENT:
				frag = new FULLSCR_HopthuChitietFragment(
						(HopthuModel) argsList.get(0));
				break;
			case HOPTHUFRAGMENT:
				frag = new FULLSCR_HopthuFragment();
				break;
			case LICHTHIDAUDOIBONGFRAGMENT:
				frag = new FULLSCR_LichthidauDoibongFragment(
						(Integer) argsList.get(0), (Integer) argsList.get(1),
						(String) argsList.get(2));
				break;
			case MATCHDETAILFRAGMENT:
				frag = new FULLSCR_MatchDetailFragment(
						(Integer) argsList.get(0));
				break;
			case TOPDAIGIA_CODE:
				frag = new FULLSCR_TopdaigiaFragment();
				break;
			case TOPCAOTHU_CODE:
				frag = new FULLSCR_TopcaothuFragment();
				break;
			case TK_HOPTHU_CODE:
				frag = new FULLSCR_HopthuFragment();
				break;

			default:
				break;
			}
			fragTransaction.replace(R.id.mainview_framelayout_full, frag, actionCode + "").commit();
			
		}else
		if (mPageType == PageType.TYPE_TTGD) {
			// ((MainActivity)
			// getActivity()).changeTitle(prfs.getString("CURRENT_LEAGUE_NAME",
			// "Giải đấu khác"));
			mTabHost.setOnTabChangedListener(this);
			switch (actionCode) {
			case XEPHANG_CODE:
				updateTab(TAB_XEPHANG, R.id.mainview_tab_2,
						new TTGD_XephangFragment(this.actionCode), TINTUC_CODE);
				mCurrentTab = 1;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;

			case THONGKE_CODE:
				updateTab(TAB_THONGKE, R.id.mainview_tab_3,
						new TTGD_ThongkeFragment(this.actionCode), NEXT24H_CODE);
				mCurrentTab = 2;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			default:
				updateTab(TAB_LICHTHIDAU, R.id.mainview_tab_1,
						new TTGD_LichthidauFragment(this.actionCode),
						LICHTHIDAU_CODE);
				mCurrentTab = 0;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			}
		} else if (mPageType == PageType.TYPE_LIVE) {
			((BaseActivity) getRealActivity()).changeTitle(TAB_LIVE);
			((BaseActivity) getRealActivity()).changeRightImageToRefresh();
			mTabHost.setOnTabChangedListener(this);
			switch (actionCode) {
			case TINTUC_CODE:
				updateTab(TAB_TINTUC, R.id.mainview_tab_1,
						new LIVE_TintucFragment(), TINTUC_CODE);
				mCurrentTab = 0;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;

			case NEXT24H_CODE:
				updateTab(TAB_24H, R.id.mainview_tab_3,
						new LIVE_Next24hFragment(), NEXT24H_CODE);
				mCurrentTab = 2;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			case TK_HOSO_CODE:
				updateTab(TAB_TAIKHOAN, R.id.mainview_tab_4,
						new TAIKHOAN_HosoFragment(), TK_HOSO_CODE);
				mCurrentTab = 3;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			default:
				updateTab(TAB_LIVE, R.id.mainview_tab_2,
						new LIVE_LiveFragment(), LIVESCORE_CODE);
				mCurrentTab = 1;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			}
		} else if (mPageType == PageType.TYPE_TAIKHOAN) {
			((BaseActivity) getRealActivity()).changeTitle(TAB_TK_HOSO);
			mTabHost.setOnTabChangedListener(this);
			switch (actionCode) {

			case TK_LICHSU_CODE:
				updateTab(TAB_TK_LICHSU, R.id.mainview_tab_2,
						new TAIKHOAN_LichsuFragment(), TK_LICHSU_CODE);
				mCurrentTab = 1;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;

			case TK_DATCUOC_CODE:
				updateTab(TAB_TK_DATCUOC, R.id.mainview_tab_3,
						new TAIKHOAN_DatcuocFragment(), TK_DATCUOC_CODE);
				mCurrentTab = 2;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			case TK_THONGKE_CODE:
				updateTab(TAB_TK_THONGKE, R.id.mainview_tab_4,
						new TAIKHOAN_ThongkeFragment(), TK_THONGKE_CODE);
				mCurrentTab = 3;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			default:
				updateTab(TAB_TK_HOSO, R.id.mainview_tab_1,
						new TAIKHOAN_HosoFragment(), TK_HOSO_CODE);
				mCurrentTab = 0;
				mTabHost.getTabWidget().setCurrentTab(mCurrentTab);
				break;
			}
		} else {
			currentUserId = prfs.getInt("CURRENT_USER_ID", 0);
			/**/
			return;
		}

	}

	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		prfs = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		currentUserId = prfs.getInt("CURRENT_USER_ID", 0);
		SharedPreferences.Editor editor = prfs.edit();
		editor.putInt("CALLBACK_FRAGMENT", actionCode);
		Log.d("TAG12", ">> " + actionCode);
		editor.commit();
		if (mPageType == PageType.TYPE_LIVE && actionCode == TK_HOSO_CODE) {
			actionCode = LIVESCORE_CODE;
		}
		if ((mPageType == PageType.TYPE_TAIKHOAN && currentUserId == 0)||(actionCode == DATCUOCFRAGMENT && currentUserId == 0)
				||(actionCode == HOPTHUFRAGMENT && currentUserId == 0)) {
			if (actionCode == TK_HOSO_CODE) {
				editor.putInt("EOBIETDATTENBIENLAGI", 1);
				editor.commit();
			}
			((BaseActivity)getRealActivity()).hideActionBar();
			dangnhapBackActionCode = actionCode;
			actionCode = DANGNHAPFRAGMENT;
			setPageType();
		}
		
		
		if (mPageType != PageType.TYPE_FULLSCR) {
			mRoot = inflater.inflate(R.layout.main_layout_fragment_tab, null);
			mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
			setupTabs();
			mTabHost.setOnTabChangedListener(this);
		} else {
			mRoot = inflater.inflate(R.layout.main_layout_fragment_notab, container, false);
		}
		return mRoot;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub

		if (isFirstTimeCreate && actionCode == LIVESCORE_CODE) {
			isFirstTimeCreate = false;
			return;
		}

		if (mPageType == PageType.TYPE_TTGD) {

			/*TTGD_LichthidauFragment TTGD_fr1 = (TTGD_LichthidauFragment) getActivity()
					.getSupportFragmentManager().findFragmentByTag(
							TAB_LICHTHIDAU);
			TTGD_XephangFragment TTGD_fr2 = (TTGD_XephangFragment) getActivity()
					.getSupportFragmentManager().findFragmentByTag(TAB_XEPHANG);
			TTGD_ThongkeFragment TTGD_fr3 = (TTGD_ThongkeFragment) getActivity()
					.getSupportFragmentManager().findFragmentByTag(TAB_THONGKE);*/

			if (TAB_LICHTHIDAU.equals(tabId)) {
				/*if (TTGD_fr1 == null) {
					Log.d("MainFragment", "TTGD_fr1 is null");
					TTGD_fr1 = new TTGD_LichthidauFragment(this.actionCode);
				}*/
				updateTab(tabId, R.id.mainview_tab_1, new TTGD_LichthidauFragment(this.actionCode), LICHTHIDAU_CODE);
				mCurrentTab = 0;
				this.actionCode = LICHTHIDAU_CODE;
				return;
			} else if (TAB_XEPHANG.equals(tabId)) {
				/*if (TTGD_fr2 == null) {
					Log.d("MainFragment", "TTGD_fr2 is null");
					TTGD_fr2 = new TTGD_XephangFragment(actionCode);
				}*/
				updateTab(tabId, R.id.mainview_tab_2, new TTGD_XephangFragment(actionCode), XEPHANG_CODE);
				mCurrentTab = 1;
				this.actionCode = XEPHANG_CODE;
				return;
			} else if (TAB_THONGKE.equals(tabId)) {
				/*if (TTGD_fr3 == null) {
					Log.d("MainFragment", "TTGD_fr3 is null");
					TTGD_fr3 = new TTGD_ThongkeFragment(actionCode);
				}*/
				updateTab(tabId, R.id.mainview_tab_3, new TTGD_ThongkeFragment(actionCode), THONGKE_CODE);
				mCurrentTab = 2;
				this.actionCode = THONGKE_CODE;
				return;
			}
		} else if (mPageType == PageType.TYPE_LIVE) {
			if (TAB_TINTUC.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_1,
						new LIVE_TintucFragment(), TINTUC_CODE);
				mCurrentTab = 0;
				this.actionCode = TINTUC_CODE;
				return;
			} else if (TAB_LIVE.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_2, new LIVE_LiveFragment(),
						LIVESCORE_CODE);
				mCurrentTab = 1;
				this.actionCode = LIVESCORE_CODE;
				return;
			} else if (TAB_24H.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_3,
						new LIVE_Next24hFragment(), NEXT24H_CODE);
				mCurrentTab = 2;
				this.actionCode = NEXT24H_CODE;
				return;
			} else if (TAB_TAIKHOAN.equals(tabId)) {
				
				MAINVIEW_MainFragment fragment = new MAINVIEW_MainFragment(
						TK_HOSO_CODE);
				((MainActivity) getRealActivity()).switchContent(fragment);
				this.actionCode = TK_HOSO_CODE;
				return;
			}
		} else {
			if (TAB_TK_HOSO.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_1,
						new TAIKHOAN_HosoFragment(), TK_HOSO_CODE);
				mCurrentTab = 0;
				this.actionCode = TK_HOSO_CODE;
				return;
			} else if (TAB_TK_LICHSU.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_2,
						new TAIKHOAN_LichsuFragment(), TK_LICHSU_CODE);
				mCurrentTab = 1;
				this.actionCode = TK_LICHSU_CODE;
				return;
			} else if (TAB_TK_DATCUOC.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_3,
						new TAIKHOAN_DatcuocFragment(), TK_DATCUOC_CODE);
				mCurrentTab = 2;
				this.actionCode = TK_DATCUOC_CODE;
				return;
			} else if (TAB_TK_THONGKE.equals(tabId)) {
				updateTab(tabId, R.id.mainview_tab_4,
						new TAIKHOAN_ThongkeFragment(),
						Constant.TK_THONGKE_CODE);
				mCurrentTab = 3;
				this.actionCode = TK_THONGKE_CODE;
				return;
			}
		}
	}

	private TabSpec newTab(String tag, String labelId, int imageId,
			int tabContentId) {

		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tabbar_item,
				(ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.doitt_btn)).setText(labelId);
		((ImageView) indicator.findViewById(R.id.nn)).setImageResource(imageId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	private void setTabWidth() {
		int numberOfTab = mTabHost.getTabWidget().getTabCount();
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int dpWidth = new DeviceUtil(getActivity()).getDeviceWidth();
		if (numberOfTab > 0) {
			for (int i = 0; i < numberOfTab; i++) {
				View tab = mTabHost.getTabWidget().getChildAt(i);
				tab.setLayoutParams(new LinearLayout.LayoutParams((int) dpWidth
						/ numberOfTab, LayoutParams.WRAP_CONTENT));
			}
		}
	}

	private void setupTabs() {
		mTabHost.setup(); // important!
		if (mPageType == PageType.TYPE_TTGD) {
			mTabHost.addTab(newTab(TAB_LICHTHIDAU, "Lịch thi đấu",
					R.drawable.lichthidau, R.id.mainview_tab_1));
			mTabHost.addTab(newTab(TAB_XEPHANG, "Xếp hạng", R.drawable.xephang,
					R.id.mainview_tab_2));
			mTabHost.addTab(newTab(TAB_THONGKE, "Thống kê", R.drawable.thongke,
					R.id.mainview_tab_3));
			mCurrentTab = 0;
		} else if (mPageType == PageType.TYPE_LIVE) {
			mTabHost.addTab(newTab(TAB_TINTUC, "Tin tức",
					R.drawable.tab_live_1_selector, R.id.mainview_tab_1));
			mTabHost.addTab(newTab(TAB_LIVE, "Live",
					R.drawable.abongda_bigball, R.id.mainview_tab_2));
			mTabHost.addTab(newTab(TAB_24H, "24h tới", R.drawable.menuitem_24h,
					R.id.mainview_tab_3));
			mTabHost.addTab(newTab(TAB_TAIKHOAN, "Tài khoản",
					R.drawable.menuitem_tk, R.id.mainview_tab_4));
			mCurrentTab = 2;
		} else {
			mTabHost.addTab(newTab(TAB_TK_HOSO, "Hồ sơ",
					R.drawable.menuitem_hs, R.id.mainview_tab_1));
			mTabHost.addTab(newTab(TAB_TK_LICHSU, "Lịch sử",
					R.drawable.account_history, R.id.mainview_tab_2));
			mTabHost.addTab(newTab(TAB_TK_DATCUOC, "Đặt cược",
					R.drawable.menuitem_dc, R.id.mainview_tab_3));
			mTabHost.addTab(newTab(TAB_TK_THONGKE, "Thống kê",
					R.drawable.menuitem_gd, R.id.mainview_tab_4));
			mCurrentTab = 0;
		}
		setTabWidth();
	}

	private void updateTab(String tabId, int placeholder,
			BaseFragment fragment, int activeCode) {
		Log.d("meomeo", "TabId: " + tabId);
		SharedPreferences.Editor editor = prfs.edit();
		editor.putInt("CALLBACK_FRAGMENT", activeCode);
		editor.commit();
		
		((BaseActivity)getRealActivity()).isClosingApp = false;
		mTabHost.setCurrentTabByTag(tabId);
		FragmentManager fm = getFragmentManager();
		if (activeCode == TINTUC_CODE) {
			((BaseActivity) getRealActivity()).getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		} else {
			((BaseActivity) getRealActivity()).getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
		((BaseActivity) getRealActivity()).changeTitle(tabId);
		
		
		//fm.beginTransaction().replace(placeholder, fragment, activeCode+"").commit();
		if (activeCode == TK_HOSO_CODE && mPageType == PageType.TYPE_TAIKHOAN) {
			fm.beginTransaction().replace(R.id.mainview_tab_1, fragment, activeCode+"").commit();
			return;
		}
		else if (fm.findFragmentByTag(activeCode+"") == null || !fm.findFragmentByTag(activeCode+"").getView().isShown()) {
			fm.beginTransaction().replace(placeholder, fragment, activeCode+"").commit();
			this.currentFragment = fragment;
			Log.d("TAG11", "new: " + tabId);
		} else {
			Log.d("TAG11", "> show: " + tabId);
			this.currentFragment = (BaseFragment) fm.findFragmentByTag(activeCode+"");
			fm.beginTransaction().show(fm.findFragmentByTag(activeCode+""));
		}
	}

	public void callBacktoFragment() {
		switch (mPageType) {
		case TYPE_TTGD:
			currentFragment.refreshContent();
			break;
		case TYPE_LIVE:
			currentFragment.refreshContent();
			break;
		case TYPE_TAIKHOAN:
			onResume();
			break;
		case TYPE_FULLSCR:
			onResume();
			break;
		default:
			break;
		}

	}
}
