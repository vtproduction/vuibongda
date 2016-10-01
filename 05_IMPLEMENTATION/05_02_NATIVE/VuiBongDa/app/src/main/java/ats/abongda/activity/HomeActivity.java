package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.base.BaseSlidingActivity2;
import ats.abongda.config.Request;
import ats.abongda.customClass.view.NonSwipeableViewPager;
import ats.abongda.fragment.LiveScoreFragment;
import ats.abongda.fragment.NewsFragment;
import ats.abongda.fragment.Next24hFragment;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.Constant;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserModel;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class HomeActivity extends BaseSlidingActivity2 {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    NonSwipeableViewPager pager;
    @BindViews({R.id.bottom_tab_0, R.id.bottom_tab_1, R.id.bottom_tab_2, R.id.bottom_tab_3})
    RelativeLayout[] bottomTabs;
    @BindView(R.id.bottom_bar)
    LinearLayout bottomBar;
    @BindView(R.id.foreground_menu)
    RelativeLayout foregroundMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        currentPagePos = getIntent().getIntExtra("pos", 1);
        LogUtil.d("REG ID: " + MainApplication.get().getRegId());
        ButterKnife.bind(this);
        setupNarBar();
        //setActionbarRightBtnEnabled(false);
        setActionbarRightBtnEnabled(false);
        fragments.add(NewsFragment.newInstance(getString(R.string.tin_tuc)));
        fragments.add(LiveScoreFragment.newInstance(getString(R.string.live_score)));
        fragments.add(Next24hFragment.newInstance(getString(R.string.next_24h)));
        HomepViewpagerAdapter pagerAdapter = new HomepViewpagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(currentPagePos);
        pager.setOffscreenPageLimit(3);
        bottomTabs[currentPagePos].performClick();
        setActionbarText(getString(R.string.live_score));
        pager.addOnPageChangeListener(onPageChangeListener);
    }

    int currentPagePos;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setActionbarText(fragments.get(position).getTitle(), position > currentPagePos);
            currentPagePos = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onMenuItemClick(int code) {
        Intent intent = new Intent(this, UserActivity.class);
        switch (code) {
            case Constant.TK_HOSO_CODE:
                intent.putExtra("pos", 0);
                startActivity(intent);
                break;
            case Constant.TK_LICHSU_CODE:
                intent.putExtra("pos", 1);
                startActivity(intent);
                break;
            case Constant.TK_DATCUOC_CODE:
                intent.putExtra("pos", 2);
                startActivity(intent);
                break;
            case Constant.TK_HOPTHU_CODE:
                intent = new Intent(this, UserInboxActivity.class);
                startActivity(intent);
                break;
            case Constant.LIVESCORE_CODE:
                bottomTabs[1].performClick();
                break;
            case Constant.TINTUC_CODE:
                bottomTabs[0].performClick();
                break;
            case Constant.NEXT24H_CODE:
                bottomTabs[2].performClick();
                break;
            case Constant.LEAGUE_PL_CODE:
            case Constant.LEAGUE_LFP_CODE:
            case Constant.LEAGUE_SA_CODE:
            case Constant.LEAGUE_BDL_CODE:
            case Constant.LEAGUE_L1_CODE:
            case Constant.LEAGUE_CL_CODE:
            case Constant.LEAGUE_EL_CODE:
                intent = new Intent(this, LeagueActivity.class);
                intent.putExtra("code", code);
                startActivity(intent);
                break;
            case Constant.TOPDAIGIA_CODE:
                intent = new Intent(this, TopRichmanActivity.class);
                startActivity(intent);
                break;
            case Constant.TOPCAOTHU_CODE:
                intent = new Intent(this, TopPlayerActivity.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onNotfoundTextClick() {

    }

    @OnClick({R.id.bottom_tab_0, R.id.bottom_tab_1, R.id.bottom_tab_2, R.id.bottom_tab_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_tab_0:
                onTabClicked(0);
                break;
            case R.id.bottom_tab_1:
                onTabClicked(1);
                break;
            case R.id.bottom_tab_2:
                onTabClicked(2);
                break;
            case R.id.bottom_tab_3:
                if (mApplication.getUserId() == 0) {
                    Intent i = new Intent(this, LoginActivity.class);
                    startActivityForResult(i, Request.LOGIN);
                } else {
                    Intent i = new Intent(this, UserActivity.class);
                    startActivityForResult(i, Request.LOGIN);

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Request.LOGIN:
                if (resultCode == RESULT_OK) {
                    ToastUtil.show(mApplication.getUser().getId() + "");

                }

                break;
        }
    }

    void onTabClicked(int pos) {
        for (View bottomtab : bottomTabs)
            bottomtab.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_tab_unselected_bg));
        bottomTabs[pos].setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_tab_selected_bg));
        pager.setCurrentItem(pos);
    }


    class HomepViewpagerAdapter extends FragmentPagerAdapter {
        public HomepViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (!isTaskRoot()) {
            super.onBackPressed();
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            ToastUtil.show("Nhấn BACK một lần nữa để thoát ứng dụng !");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }
}
