package ats.abongda.activity.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.afollestad.materialdialogs.MaterialDialog;

import ats.abongda.R;
import ats.abongda.customClass.view.slidingview.MenuSliding;
import ats.abongda.helper.DeviceUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.MenuItemClickImp;

/**
 * Created by NienLe on 06-Aug-16.
 */
public abstract class BaseSlidingActivity2 extends BaseActivity {

    protected Toolbar mToolbar;
    protected NavigationView mNavBar;
    protected DrawerLayout mDrawerLayout;
    protected TabLayout mTabLayout;
    protected LinearLayout mLayoutTab;
    public MenuSliding mMenuSlide;
    public RelativeLayout mForegroundMenu;
    protected SwipeRefreshLayout mRefresh;
    protected AppBarLayout mAppBarLayout;
    protected ImageView actionbarLeftBtn;
    protected ImageView actionbarRightBtn;
    protected TextView actionbarText;
    //protected TextSwitcher actionbarTextSwitcher;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        if (mAppBarLayout != null) {
            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                }
            });
        }
        getActionBarToolbar();
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(null);
            mToolbar.setContentInsetsAbsolute(0,0);
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
            params.setScrollFlags(0);
            setupActionBar();
        }

    }


    protected Toolbar getActionBarToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }


    private void setupActionBar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.base_app_color));
        mToolbar.setTitle(getTitle());
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeButtonEnabled(false);
        //mToolbar.setNavigationIcon(R.drawable.menu_icon);
    }

    @Override
    public void onBackPressed() {
        if (mMenuSlide.isOpened())
            mMenuSlide.closeLayer(true);
        else
            super.onBackPressed();
    }

    protected void setupNarBar() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) return;
        mMenuSlide = new MenuSliding(this, new MenuItemClickImp() {
            @Override
            public void onMenuItemClicked(int code) {
                mMenuSlide.closeLayer(true);
                onMenuItemClick(code);
            }
        });
        mMenuSlide.setActivity(this);
        mForegroundMenu = (RelativeLayout) findViewById(R.id.foreground_menu);
        mDrawerLayout.addView(mMenuSlide);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.custom_actionbar, null);
            actionBar.setCustomView(v);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionbarLeftBtn = (ImageView)v.findViewById(R.id.action_bar_leftbtn);
            actionbarRightBtn = (ImageView)v.findViewById(R.id.action_bar_rightbtn);
            actionbarText = (TextView) v.findViewById(R.id.action_bar_title);
            /*actionbarTextSwitcher = (TextSwitcher) v.findViewById(R.id.action_bar_title);
            actionbarTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView () {
                    TextView tv = new TextView(mApplication.getBaseContext());
                    tv.setMaxLines(1);
                    tv.setTextSize(getResources().getDimension(R.dimen.text_largest));
                    tv.setTextColor(getResources().getColor(R.color.white_color));
                    return tv;
                }
            });*/
            actionbarLeftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMenuSlide.openLayer(true);
                }
            });
        }
    }

    protected abstract void onMenuItemClick(int code);
    protected abstract void onNotfoundTextClick();


    protected void setActionbarText(String text, boolean slideLTR){
        try{
            actionbarText.setText(text);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    protected void setActionbarText(String text){
        try{
            actionbarText.setText(text);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    protected void setActionbarLeftBtnEnabled(boolean enabled){
        actionbarLeftBtn.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }
    protected void setActionbarRightBtnEnabled(boolean enabled){
        actionbarRightBtn.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    protected void setupNarBar(boolean isShowMenu) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) return;
        if(isShowMenu) {
            mMenuSlide = new MenuSliding(this);
            mMenuSlide.setActivity(this);
            mDrawerLayout.addView(mMenuSlide);
        }
        mForegroundMenu = (RelativeLayout) findViewById(R.id.foreground_menu);
        //mNavBar = (NavigationView) findViewById(R.id.nav_view);
        if (mNavBar == null) return;
        else {
            //mMenu = new MenuModel(mNavBar, this);
        }
    }
}
