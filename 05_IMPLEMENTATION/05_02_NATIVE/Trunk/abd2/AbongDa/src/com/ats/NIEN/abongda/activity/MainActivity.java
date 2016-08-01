package com.ats.NIEN.abongda.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MenuFragment;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity{
	SharedPreferences sharePreference;
	private Fragment mContent;
	public MainActivity(){
		super(R.string.app_name);

	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		int callbackActivity = Constant.LIVESCORE_CODE;
		final Bundle b = getIntent().getExtras();
		
		if (b!=null) {
			callbackActivity = b.getInt("callback_act");
			final int matchCode = b.getInt("match_code");
		}
		Log.d("BA Log", "callbackActivity: " + callbackActivity);
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)		
			if (callbackActivity == Constant.MATCHDETAILFRAGMENT) {
				mContent = new MAINVIEW_MainFragment(callbackActivity, new ArrayList<Object>(){{
					add(b.getInt("match_code"));
				}});
			}else{
				mContent = new MAINVIEW_MainFragment(callbackActivity);
			}
		//set content for main view
		setContentView(R.layout.content_frame);	
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, mContent)
			.commit();
		
		//set content for menu view
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.menu_frame, new MAINVIEW_MenuFragment())
			.commit();

	}
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("BA Log", "resume main act");
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		//getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(MAINVIEW_MainFragment fragment){
		isClosingApp = false;
		mContent = fragment;
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
		.setCustomAnimations(anim.slide_in_left,anim.slide_out_right, anim.slide_out_left, anim.slide_in_right ).replace(R.id.content_frame, fragment, fragment.actionCode+"").addToBackStack(mContent.getTag()).commit();
		getSlidingMenu().showContent();
	}
	
	public void switchContentWithoutBackstack(MAINVIEW_MainFragment fragment){
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(anim.slide_in_left,anim.slide_out_right, anim.slide_in_right, anim.slide_out_left ).replace(R.id.content_frame, fragment,fragment.actionCode+"").commit();
		getSlidingMenu().showContent();
	}



}
