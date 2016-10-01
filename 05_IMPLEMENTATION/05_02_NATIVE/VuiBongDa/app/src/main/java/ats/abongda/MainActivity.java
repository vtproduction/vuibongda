package ats.abongda;


import android.os.Bundle;
import android.support.annotation.Nullable;

import ats.abongda.activity.base.BaseSlidingActivity;
import ats.abongda.fragment.DrawerFragment;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;

public class MainActivity extends BaseSlidingActivity implements DrawerFragment.ItemClickedImp{


    @Override
    public BaseFragment getContentFragment() {
        return DummyFragment.newInstance();
    }

    @Override
    public DrawerFragment getDrawerLayout() {
        return DrawerFragment.newInstance(this);
    }

    @Override
    public void onItemClicked(int code) {
        closeDrawer();
        LogUtil.d("> " + code);
        ((DummyFragment)contentFragment).changeText("" + code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

