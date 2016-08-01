package vn.ats.aBongDa.activity;

import vn.ats.aBongDa.R;
import vn.ats.aBongDa.util.DeviceUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
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
		//mFrag = new BaseFragment(ActionCode.LIVESCORE, null);
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
				//new DeviceUtil(mContext).getAllSharePref();

			}
		});
		mActionBarRefreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				

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
		actionBar.hide();
	}
	
	public void showActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.show();
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




}

