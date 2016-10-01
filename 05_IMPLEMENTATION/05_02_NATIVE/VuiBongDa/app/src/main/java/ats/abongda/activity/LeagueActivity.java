package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.base.BaseSlidingActivity2;
import ats.abongda.customClass.view.NonSwipeableViewPager;
import ats.abongda.fragment.LeagueDetail_MatchTimeFragment;
import ats.abongda.fragment.LeagueDetail_RankingFragment;
import ats.abongda.fragment.LeagueDetail_StatisticFragment;
import ats.abongda.fragment.LiveScoreFragment;
import ats.abongda.fragment.NewsFragment;
import ats.abongda.fragment.Next24hFragment;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.Constant;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.StringHelper;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.LeagueSeason;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 17-Aug-16.
 */
public class LeagueActivity extends BaseSlidingActivity2 {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindViews({R.id.bottom_tab_0,R.id.bottom_tab_1,R.id.bottom_tab_2})
    RelativeLayout[] bottomTabs;
    @BindView(R.id.bottom_bar)
    LinearLayout bottomBar;
    @BindView(R.id.foreground_menu)
    RelativeLayout foregroundMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    int currentPagePos;
    private List<BaseFragment> fragments = new ArrayList<>();
    private int leagueId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_detail);
        currentPagePos = 0;
        this.leagueId = getIntent().getIntExtra("code", -1);
        if (leagueId == -1){
            ToastUtil.showError();
            finish();
        }
        ButterKnife.bind(this);
        setupNarBar();
        //setActionbarRightBtnEnabled(false);
        setActionbarRightBtnEnabled(false);
        fragments.add(LeagueDetail_MatchTimeFragment.newInstance(leagueId,getString(R.string.lich_thi_dau)));
        fragments.add(LeagueDetail_RankingFragment.newInstance(leagueId,getString(R.string.xep_hang)));
        fragments.add(LeagueDetail_StatisticFragment.newInstance(leagueId, getString(R.string.thong_ke)));
        ViewpagerAdapter pagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(currentPagePos);
        pager.setOffscreenPageLimit(3);
        onTabClicked(0);
        setActionbarText(StringHelper.getLeagueName(leagueId, this));
        pager.addOnPageChangeListener(onPageChangeListener);

        //loadData();
        getLeagueSeason();

    }

    private void loadData(List<LeagueSeason> leagueSeasonList){
        ((LeagueDetail_MatchTimeFragment)fragments.get(0)).onDataLoaded(leagueId, leagueSeasonList);
        ((LeagueDetail_RankingFragment)fragments.get(1)).onDataLoaded(leagueId, leagueSeasonList);
        ((LeagueDetail_StatisticFragment)fragments.get(2)).onDataLoaded(leagueId, leagueSeasonList);

    }


    private void getLeagueSeason(){
        showProgressDialog();
        try {
            api.getLeagueSeason(getIMEI(), leagueId)
                    .enqueue(new Callback<List<LeagueSeason>>() {
                        @Override
                        public void onResponse(Call<List<LeagueSeason>> call, Response<List<LeagueSeason>> response) {
                            loadData(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<LeagueSeason>> call, Throwable t) {
                            LogUtil.e(t);
                            ToastUtil.showNetWorkError(t);
                        }
                    });
        }catch (Exception e){
            LogUtil.e(e);
        }finally {
            hideProgressDialog();
        }
    }



    void onTabClicked(int pos){
        for (View bottomtab : bottomTabs)
            bottomtab.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_tab_unselected_bg));
        bottomTabs[pos].setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_tab_selected_bg));
        pager.setCurrentItem(pos);
    }

    void updateTab(int pos){
        for (View bottomtab : bottomTabs)
            bottomtab.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_tab_unselected_bg));
        bottomTabs[pos].setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_tab_selected_bg));
    }

    @Override
    protected void onMenuItemClick(int code) {
        Intent intent = new Intent(this, HomeActivity.class);
        switch (code){
            case Constant.TINTUC_CODE:
                intent.putExtra("pos",0);
                startActivity(intent);
                finish();
                break;
            case Constant.LIVESCORE_CODE:
                intent.putExtra("pos",1);
                startActivity(intent);
                finish();
                break;
            case  Constant.NEXT24H_CODE:
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("pos",2);
                startActivity(intent);
                finish();
                break;
            case  Constant.TK_HOPTHU_CODE:
                intent = new Intent(this, UserInboxActivity.class);
                startActivity(intent);
                break;
            case Constant.TK_LICHSU_CODE:
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("pos",1);
                startActivity(intent);
                finish();
                break;
            case Constant.TK_HOSO_CODE:
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("pos",0);
                startActivity(intent);
                finish();
                break;
            case Constant.TK_DATCUOC_CODE:
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("pos",2);
                startActivity(intent);
                finish();
                break;
            case Constant.LEAGUE_PL_CODE:
            case Constant.LEAGUE_LFP_CODE:
            case Constant.LEAGUE_SA_CODE:
            case Constant.LEAGUE_BDL_CODE:
            case Constant.LEAGUE_L1_CODE:
            case Constant.LEAGUE_CL_CODE:
            case Constant.LEAGUE_EL_CODE:
                if (code == leagueId) return;
                //leagueId = code;
                //setActionbarText(StringHelper.getLeagueName(leagueId, this));
                //loadData();
                //getLeagueSeason();
                intent = new Intent(this, LeagueActivity.class);
                intent.putExtra("code",code);
                startActivity(intent);
                finish();
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

    @OnClick({R.id.bottom_tab_0, R.id.bottom_tab_1, R.id.bottom_tab_2})
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
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPagePos = position;
            updateTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    class ViewpagerAdapter extends FragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager fm) {
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

}
