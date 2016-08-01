package vn.ats.aBongDa.activity;

import java.util.ArrayList;
import java.util.List;



import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;
import vn.ats.aBongDa.R.layout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends BaseActivity {
	SharedPreferences sharePreference;
	private LinearLayout tabView;
	private FrameLayout frameView;
	private Boolean mTabStatus = true;

	public MainActivity() {
		super(R.string.app_name);

	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tabView = (LinearLayout) getLayoutInflater().inflate(
				R.layout.menu_live, null);
		this.sharePreference = getSharedPreferences("AUTHENTICATION_FILE_NAME",
				Context.MODE_PRIVATE);


		// set content for main view
		setContentView(R.layout.content_frame);
		this.frameView = (FrameLayout) findViewById(R.id.content_frame);
		((LinearLayout) findViewById(R.id.content_tab)).removeAllViews();
		((LinearLayout) findViewById(R.id.content_tab)).addView(tabView);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set content for menu view
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MAINVIEW_MenuFragment()).commit();

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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// getSupportFragmentManager().putFragment(outState, "mContent",
		// mContent);
	}

	public void hideTabview() {
		Log.d("MACTIVITY", ">> " + mTabStatus);
		if (!mTabStatus) {
			return;
		}
		mTabStatus = false;
		TranslateAnimation animate = new TranslateAnimation(0, 0, 0,
				tabView.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		tabView.startAnimation(animate);
		tabView.setVisibility(View.INVISIBLE);
	}

	public void showTabview() {
		Log.d("MACTIVITY", "> " + mTabStatus);
		if (mTabStatus) {
			return;
		}
		mTabStatus = false;
		TranslateAnimation animate = new TranslateAnimation(0, 0,
				tabView.getHeight(), 0);
		animate.setDuration(500);
		animate.setFillAfter(true);
		tabView.startAnimation(animate);
		tabView.setVisibility(View.VISIBLE);

	}

	

	public void changeTab(int tabPos) {
		View t0, t1, t2, t3;
		t0 = tabView.findViewById(R.id.tab_n0);
		t1 = tabView.findViewById(R.id.tab_n1);
		t2 = tabView.findViewById(R.id.tab_n2);
		t3 = tabView.findViewById(R.id.tab_n3);
		t0.setBackground(getResources().getDrawable(R.drawable.border_menu_2));
		t1.setBackground(getResources().getDrawable(R.drawable.border_menu_2));
		t2.setBackground(getResources().getDrawable(R.drawable.border_menu_2));
		if (t3 != null) {
			t3.setBackground(getResources().getDrawable(
					R.drawable.border_menu_2));
		}
		switch (tabPos) {
		case 0:
			t0.setBackground(getResources().getDrawable(
					R.drawable.border_menu_1));
			break;
		case 1:
			t1.setBackground(getResources().getDrawable(
					R.drawable.border_menu_1));
			break;
		case 2:
			t2.setBackground(getResources().getDrawable(
					R.drawable.border_menu_1));
			break;
		case 3:
			t3.setBackground(getResources().getDrawable(
					R.drawable.border_menu_1));
			break;

		default:
			break;
		}
	}



}
