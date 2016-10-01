package ats.abongda.activity.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ats.abongda.DummyFragment;
import ats.abongda.R;
import ats.abongda.fragment.DrawerFragment;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.DeviceUlti;
import ats.abongda.helper.LogUtil;

/**
 * Created by NienLe on 02-Aug-16.
 */
public abstract class BaseSlidingActivity extends BaseActivity {
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected BaseFragment contentFragment, drawerFragment;
    ImageView actionbarLeftBtn, actionbarRightBtn;
    TextView actionbarText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_sliding);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawerLayout = (DrawerLayout) inflater.inflate(R.layout.decor, null);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);

        FrameLayout container = (FrameLayout) mDrawerLayout.findViewById(R.id.base_content);
        container.addView(child);
        decor.addView(mDrawerLayout);

        drawerFragment  = getDrawerLayout();
        getSupportFragmentManager().beginTransaction().add(R.id.base_nav, drawerFragment).commit();

        setupDrawer();
        setupActionbar();

        contentFragment = getContentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.base_content, contentFragment).commit();
    }

    public abstract BaseFragment getContentFragment();
    public abstract DrawerFragment getDrawerLayout();



    private void setupActionbar(){
        try{
            ActionBar actionBar = getSupportActionBar();
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
            actionbarText = (TextView)v.findViewById(R.id.action_bar_title);
            actionbarLeftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDrawer();
                }
            });
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
        actionbarLeftBtn.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }
    protected void setActionbarRightBtnEnabled(boolean enabled){
        actionbarRightBtn.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }

    protected void openDrawer(){
        try {
            mDrawerLayout.openDrawer(GravityCompat.START, true);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    protected void closeDrawer(){
        try {
            mDrawerLayout.closeDrawer(GravityCompat.START, true);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }










    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
