package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.base.BaseSlidingActivity2;
import ats.abongda.config.Request;
import ats.abongda.customClass.view.NonSwipeableViewPager;
import ats.abongda.fragment.LiveScoreFragment;
import ats.abongda.fragment.NewsFragment;
import ats.abongda.fragment.Next24hFragment;
import ats.abongda.fragment.UserBetHistoryFragment;
import ats.abongda.fragment.UserHistoryFragment;
import ats.abongda.fragment.UserProfileFragment;
import ats.abongda.fragment.UserStatisticFragment;
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
 * Created by NienLe on 10-Aug-16.
 */
public class UserActivity extends BaseSlidingActivity2 {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindViews({R.id.bottom_tab_0,R.id.bottom_tab_1,R.id.bottom_tab_2,R.id.bottom_tab_3})
    RelativeLayout[] bottomTabs;
    @BindView(R.id.bottom_bar)
    LinearLayout bottomBar;
    @BindView(R.id.foreground_menu)
    RelativeLayout foregroundMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private List<BaseFragment> fragments = new ArrayList<>();
    private UserModel userModel;
    UserViewpagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = mApplication.getUser();
        if (userModel == null){
            ToastUtil.show(getString(R.string.bcdndxndn));
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
            return;
        }
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        currentPagePos = getIntent().getIntExtra("pos", 0);
        setupNarBar();
        //setActionbarRightBtnEnabled(false);

        fragments.add(UserProfileFragment.newInstance(userModel.getId(), getString(R.string.ho_so)));
        fragments.add(UserHistoryFragment.newInstance(getString(R.string.lich_su)));
        fragments.add(UserBetHistoryFragment.newInstance(getString(R.string.dat_cuoc)));
        fragments.add(UserStatisticFragment.newInstance(getString(R.string.thong_ke)));
        pagerAdapter = new UserViewpagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(currentPagePos);
        onTabClicked(currentPagePos);
        pager.setOffscreenPageLimit(3);
        setActionbarText(getString(R.string.ho_so));
        pager.addOnPageChangeListener(onPageChangeListener);
        actionbarRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuIconClicked();
            }
        });

    }


    int currentPagePos;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setActionbarText(fragments.get(position).getTitle(), position < currentPagePos);
            currentPagePos = position;
            updateTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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
                intent.putExtra("pos",2);
                startActivity(intent);
                finish();
                break;
            case  Constant.TK_HOPTHU_CODE:
                intent = new Intent(this, UserInboxActivity.class);
                startActivity(intent);
                break;
            case Constant.TK_LICHSU_CODE:
                bottomTabs[1].performClick();
                break;
            case Constant.TK_HOSO_CODE:
                bottomTabs[0].performClick();
                break;
            case Constant.TK_DATCUOC_CODE:
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

    @OnClick({ R.id.bottom_tab_0, R.id.bottom_tab_1, R.id.bottom_tab_2, R.id.bottom_tab_3})
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
                onTabClicked(3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Request.LOGIN:
                if (resultCode == RESULT_OK){
                    ToastUtil.show("Đăng nhập thành công!");
                }
            case Request.CHANGE_USER_INFO:
                if (resultCode == RESULT_OK){
                    ((UserProfileFragment)pagerAdapter.getItem(0)).loadContent();
                }
                break;
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


    class UserViewpagerAdapter extends FragmentPagerAdapter {
        public UserViewpagerAdapter(FragmentManager fm) {
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


    private void onMenuIconClicked(){
        try {
            new MaterialDialog.Builder(this)
                    .items(R.array.user_menu)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            onMenu(which);
                        }
                    })
                    .show();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }



    private void onMenu(int pos){
        Intent intent;
        switch (pos){
            case 0:
                intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                intent = new Intent(this, ChangeUserInformationActivity.class);
                startActivityForResult(intent, Request.CHANGE_USER_INFO);
                break;
            case 2:
                intent = new Intent(this, UserInboxActivity.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                mApplication.setUser(null);
                ToastUtil.show("Bạn đã đăng xuất!");
                /*intent = new Intent(this, HomeActivity.class);
                startActivity(intent);*/
                finish();
                break;

        }
    }
}
