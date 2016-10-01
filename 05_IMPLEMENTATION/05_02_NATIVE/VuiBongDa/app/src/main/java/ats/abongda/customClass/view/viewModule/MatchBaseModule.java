package ats.abongda.customClass.view.viewModule;

import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;

import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.model.Match;
import ats.abongda.model.match.MatchDetail;

/**
 * Created by NienLe on 23-Aug-16.
 */
public abstract class MatchBaseModule {
    protected MatchDetailActivity.ModuleInteractImp callback;
    protected WeakReference<Context> context;
    protected MatchDetail match;
    protected View mainView;


    public MatchBaseModule(MatchDetailActivity.ModuleInteractImp callback, WeakReference<Context> context, MatchDetail match) {
        this.callback = callback;
        this.context = context;
        this.match = match;
    }

    public View getMainView() {
        return mainView;
    }

    public abstract void loadData();
}
