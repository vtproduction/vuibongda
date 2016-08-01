package com.ats.NIEN.abongda.activity;

import java.util.ArrayList;

import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.DeviceUtil;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

@SuppressLint({ "InflateParams", "NewApi" })
public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected Fragment mFrag;
	public Context mContext;
	private TextView mActionBarText;
	private ImageView mActionBarMenuBtn;
	private ImageView mActionBarRefreshBtn;
	public static SharedPreferences pref;
	public static int currentActionCode;
	public View actionBarView;
	public boolean isClosingApp;
	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
		isClosingApp = false;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		setTitle(mTitleRes);
		mContext = this;
		pref = getSharedPreferences("AUTHENTICATION_FILE_NAME",
				Context.MODE_PRIVATE);

		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		mFrag = new MAINVIEW_MenuFragment();
		ft.replace(R.id.menu_frame, mFrag);
		ft.commit();

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(10);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset((int) (new DeviceUtil(this).getDeviceWidth() * 0.2));
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

		// add custom title bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("");
		actionBar.setLogo(null);
		this.actionBarView = getLayoutInflater().inflate(R.layout.action_bar,
				null);
		mActionBarMenuBtn = (ImageView) actionBarView
				.findViewById(R.id.actionbar_menu_btn);
		mActionBarRefreshBtn = (ImageView) actionBarView
				.findViewById(R.id.menusubitem_img);
		mActionBarRefreshBtn.setTag(R.drawable.refresh_icon);
		mActionBarText = (TextView) actionBarView
				.findViewById(R.id.menusubitem_text);
		mActionBarMenuBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSlidingMenu().showMenu();
				FragmentManager fm = BaseActivity.this.getSupportFragmentManager();
				for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
				   //Log.d("backstack", "Found fragment: " + fm.getBackStackEntryAt(entry).getName());
				}
				new DeviceUtil(mContext).getAllSharePref();

			}
		});
		mActionBarRefreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Integer.parseInt(mActionBarRefreshBtn.getTag().toString()) == R.drawable.refresh_icon) {
					currentActionCode = pref.getInt("CURRENT_ACTIVE_TAB",
							Constant.NOACTION_CODE);
					((MAINVIEW_MainFragment)getSupportFragmentManager().findFragmentById(R.id.content_frame)).callBacktoFragment();
					
				} else if (Integer.parseInt(mActionBarRefreshBtn.getTag()
						.toString()) == R.drawable.icon_back) {
					changeRightImage();
					onBackPressed();
				} else if (Integer.parseInt(mActionBarRefreshBtn.getTag()
						.toString()) == R.drawable.icons_gear) {
					final CustomDialogFragment fDialog = new CustomDialogFragment();

					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					fDialog.show(ft, "setting dialog");
				}

			}
		});

		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_bg));
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setSlidingActionBarEnabled(true);

	}
	public void hideActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_dark_bg));
		getSlidingMenu().setSlidingEnabled(false);
		mActionBarMenuBtn.setVisibility(View.INVISIBLE);
		mActionBarRefreshBtn.setVisibility(View.INVISIBLE);
		mActionBarText.setVisibility(View.INVISIBLE);
		actionBarView.setBackgroundColor(getResources().getColor(R.color.mydarktheme));
		actionBar.setCustomView(actionBarView);
	}
	
	public void showActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_bg));
		getSlidingMenu().setSlidingEnabled(true);
		mActionBarMenuBtn.setVisibility(View.VISIBLE);
		mActionBarRefreshBtn.setVisibility(View.VISIBLE);
		mActionBarText.setVisibility(View.VISIBLE);
		
		actionBarView.setBackgroundColor(getResources().getColor(R.color.base_app_color));
		actionBar.setCustomView(actionBarView);
	}

	public void changeTitle(String title) {
		mActionBarText.setText(title);
	}

	public void hideRightImage() {
		mActionBarRefreshBtn.setVisibility(View.INVISIBLE);
	}

	public void showRightImage() {
		mActionBarRefreshBtn.setVisibility(View.VISIBLE);
	}

	public String getRightTitle() {
		return mActionBarText.getText().toString();
	}

	public void changeRightImage() {

	}

	public void changeRightImageToRefresh() {
		mActionBarRefreshBtn.setImageResource(R.drawable.refresh_icon);
		mActionBarRefreshBtn.setTag(R.drawable.refresh_icon);
		showRightImage();
	}

	public void changeRightImageToBack() {
		mActionBarRefreshBtn.setImageResource(R.drawable.icon_back);
		mActionBarRefreshBtn.setTag(R.drawable.icon_back);
		showRightImage();
	}

	public void changeRightImageToSetting() {
		mActionBarRefreshBtn.setImageResource(R.drawable.icons_gear);
		mActionBarRefreshBtn.setTag(R.drawable.icons_gear);
		showRightImage();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
				if (isClosingApp) {
					SharedPreferences.Editor editor = pref.edit();
					editor.putInt("CALLBACK_FRAGMENT", Constant.LIVESCORE_CODE);
					editor.commit();
					this.finish();
					return false;
				}else{
					isClosingApp = true;
					Toast.makeText(mContext, "Ấn BACK một lần nữa để thoát ứng dụng !", Toast.LENGTH_SHORT).show();
					return false;
				}
			} else {
				getSupportFragmentManager().popBackStack();		
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public ArrayList<Fragment> getAllFragments() {
	    ArrayList<Fragment> lista = new ArrayList<Fragment>();

	    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
	        try {
	            fragment.getTag();
	            lista.add(fragment);
	        } catch (NullPointerException e) {

	        }
	    }

	    return lista;

	}
	
	public void backFromSignin(){
		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			SharedPreferences.Editor editor = pref.edit();
			editor.commit();
			this.finish();
		} else {
			getSupportFragmentManager().popBackStack();
			getSupportFragmentManager().popBackStack();
			
		}
	}
	
	public void DangXuat(){
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("CURRENT_USER_ID", 0);
		editor.putString("CURRENT_USER_DATA", "");
		editor.putString("CURRENT_USER_NAME", "");
		editor.commit();
		currentActionCode = Constant.LIVESCORE_CODE;
		MAINVIEW_MainFragment mv = new MAINVIEW_MainFragment(currentActionCode);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mv)
				.addToBackStack(getRightTitle()).commit();
		
	}
	AnimationDrawable mSpinner;
	public void animateRightImage(){
		if (Integer.parseInt(mActionBarRefreshBtn.getTag().toString()) != R.drawable.refresh_icon) {
			return;
		}
		if (!this.mActionBarRefreshBtn.isShown()) {
			return;
		}
		this.mActionBarRefreshBtn.setImageResource(R.drawable.blankimg);
		this.mActionBarRefreshBtn.setBackgroundResource(R.anim.spinner);
		mSpinner = (AnimationDrawable) mActionBarRefreshBtn.getBackground();
		mSpinner.start();
	}
	
	public void stopAnimateRightImage(){
		//this.mActionBarRefreshBtn.clearAnimation();
		if (mSpinner != null) {
			mSpinner.stop();
		}
		this.mActionBarRefreshBtn.setBackgroundResource(R.drawable.blankimg);
		this.mActionBarRefreshBtn.setImageResource(R.drawable.refresh_icon);
	}
	
	public void switchContent(Fragment fragment){
		this.isClosingApp = false;
		getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(anim.slide_in_left,anim.slide_out_right, anim.slide_in_right, anim.slide_out_left ).replace(R.id.content_frame, fragment).addToBackStack(fragment.getTag()).commit();
		getSlidingMenu().showContent();
	}
	public class CustomDialogFragment extends DialogFragment implements
			android.view.View.OnClickListener {
		public View opt1, opt2, opt3, opt4;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.setting_option_layout,
					container, false);
			getDialog().getWindow().setGravity(
					Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			getDialog().setCanceledOnTouchOutside(true);
			WindowManager.LayoutParams p = getDialog().getWindow()
					.getAttributes();
			p.width = LayoutParams.MATCH_PARENT;
			p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
			p.x = 200;
			getDialog().getWindow().setAttributes(p);

			opt1 = v.findViewById(R.id.settingOption_dmk);
			opt2 = v.findViewById(R.id.settingOption_dttcn);
			opt3 = v.findViewById(R.id.settingOption_ht);
			opt4 = v.findViewById(R.id.settingOption_dx);

			opt1.setOnClickListener(this);
			opt2.setOnClickListener(this);
			opt3.setOnClickListener(this);
			opt4.setOnClickListener(this);
			return v;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Dialog dialog = super.onCreateDialog(savedInstanceState);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			return dialog;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dismiss();
			MAINVIEW_MainFragment frag = null;
			switch (v.getId()) {
			case R.id.settingOption_dmk:
				frag = new MAINVIEW_MainFragment(Constant.DOIMKFRAGMENT,
						new ArrayList<Object>());
				switchContent(frag);
				break;
			case R.id.settingOption_dttcn:
				frag = new MAINVIEW_MainFragment(Constant.DOITTFRAGMENT,
						new ArrayList<Object>());
				switchContent(frag);
				break;
			case R.id.settingOption_ht:
				frag = new MAINVIEW_MainFragment(Constant.HOPTHUFRAGMENT,
						new ArrayList<Object>());
				switchContent(frag);
				break;
			case R.id.settingOption_dx:
				changeRightImageToRefresh();
				DangXuat();
				break;

			default:
				break;
			}
		}
	}
}
