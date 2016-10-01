package ats.abongda.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ats.abongda.MainApplication;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.api.APIEndpoint;
import ats.abongda.api.APIService;

/**
 * Created by NienLe on 02-Aug-16.
 */
public abstract class BaseFragment extends Fragment{
    protected Context context;
    protected MainApplication application;
    protected BaseActivity baseActivity;
    protected String title;
    protected APIEndpoint api = APIService.build();
    public boolean isContentLoaded = false;

    public String getTitle() {
        return title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = MainApplication.get();
        this.baseActivity = application.getBaseActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Context getContext() {
        return (super.getContext() == null) ? context : super.getContext();
    }

    public abstract void loadContent();
}
