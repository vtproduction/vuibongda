package ats.abongda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.ClubInforActivity;
import ats.abongda.adapter.LeagueMatchtimeAdapter;
import ats.abongda.adapter.LeagueRankingAdapter;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Club;
import ats.abongda.model.GroupStanding;
import ats.abongda.model.LeagueSeason;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 17-Aug-16.
 */
public class LeagueDetail_RankingFragment extends BaseFragment implements ListItemClickImp {
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.recycer)
    RecyclerView recycer;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    @BindView(R.id.choose_year_text)
    TextView chooseYearText;
    @BindView(R.id.choose_year_btn)
    RelativeLayout chooseYearBtn;
    @BindView(R.id.choose_month_text)
    TextView chooseMonthText;
    @BindView(R.id.choose_month_btn)
    RelativeLayout chooseMonthBtn;
    @BindView(R.id.header)
    LinearLayout header;
    LeagueRankingAdapter adapter;
    public static LeagueDetail_RankingFragment newInstance(int leagueId, String title) {
        LeagueDetail_RankingFragment f = new LeagueDetail_RankingFragment();
        f.title = title;
        f.currentLeagueId = leagueId;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_league_ranking2, container, false);
        ButterKnife.bind(this, v);
        recycer.setLayoutManager(new LinearLayoutManager(getContext()));
        recycer.setHasFixedSize(true);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeagueRanking();
            }
        });
        return v;
    }

    @Override
    public void loadContent() {

    }

    @Override
    public void onListItemClicked(int code, Object data) {
        switch (code){
            case 1:
                Intent intent = new Intent(getContext(), ClubInforActivity.class);
                intent.putExtra("clubId", (Integer)data);
                startActivity(intent);
                break;
        }
    }

    private List<LeagueSeason> leagueSeasonList = new ArrayList<>();
    public void onDataLoaded(int leagueId, List<LeagueSeason> leagueSeasonList){
        this.currentLeagueId  = leagueId;
        this.leagueSeasonList = leagueSeasonList;
        this.isInNationalCup = currentLeagueId < 6;
        if (currentLeagueId < 6){
            chooseMonthBtn.setVisibility(View.GONE);
        }else {
            chooseMonthBtn.setVisibility(View.VISIBLE);
        }
        for (LeagueSeason season : leagueSeasonList){
            if (season.getIsCurrent() == 1){
                currentMonthPos = leagueSeasonList.indexOf(season);
                currentSeasonId = season.getRow_id();
                if (isInNationalCup){

                }else {
                    currentMonthId = 0;
                }
                break;
            }
        }
        setSeasonList();
        chooseYearText.setText(seasonList.get(currentSeasonPos));
        getLeagueRanking();
    }

    int currentSeasonId = 0, currentMonthId = 0, currentLeagueId;
    private boolean isInNationalCup = true;

    private List<String> seasonList = new ArrayList<>();
    private List<String> monthList = new ArrayList<>();

    public void setSeasonList() {
        try {
            seasonList.clear();
            for (LeagueSeason season : leagueSeasonList)
                seasonList.add(season.getSeason_name());
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public void setMonthList() {
        try {
            monthList.clear();
            for (GroupStanding groupStanding : groupStandingList){
                monthList.add(groupStanding.get_League_Name());
            }
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private List<GroupStanding> groupStandingList = new ArrayList<>();
    private void getLeagueRanking(){
        showNotfoundLayout(false);
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        if (currentLeagueId < 6){
            api.getSeasonStanding(baseActivity.getIMEI(), currentLeagueId, currentSeasonId)
                    .enqueue(new Callback<List<Club>>() {
                        @Override
                        public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
                            try{
                                if (response.body() == null || response.body().size() == 0){
                                    showNotfoundLayout(true);
                                }else {
                                    showNotfoundLayout(false);
                                }
                                adapter = new LeagueRankingAdapter(response.body(), getContext(), LeagueDetail_RankingFragment.this);
                                recycer.setAdapter(adapter);
                            }catch (Exception e){
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }finally {
                                swiper.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swiper.setRefreshing(false);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Club>> call, Throwable t) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LogUtil.e(t);
                            /*ToastUtil.showNetWorkError(t);*/
                            showNotfoundLayout(true);
                        }
                    });
        }else {
            api.getSeasonGroupStanding(baseActivity.getIMEI(), currentLeagueId, currentSeasonId)
                    .enqueue(new Callback<List<GroupStanding>>() {
                        @Override
                        public void onResponse(Call<List<GroupStanding>> call, Response<List<GroupStanding>> response) {
                            try{
                                if (response.body().get(0).get_LStanding() == null || response.body().get(0).get_LStanding().size() == 0){
                                    showNotfoundLayout(true);
                                }else {
                                    showNotfoundLayout(false);
                                }
                                groupStandingList = response.body();
                                adapter = new LeagueRankingAdapter(response.body().get(0).get_LStanding(), getContext(), LeagueDetail_RankingFragment.this);
                                recycer.setAdapter(adapter);
                                setMonthList();
                                if (monthList.size() > 0)
                                    chooseMonthText.setText(monthList.get(0));
                            }catch (Exception e){
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }finally {
                                swiper.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swiper.setRefreshing(false);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<GroupStanding>> call, Throwable t) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LogUtil.e(t);
                            /*ToastUtil.showNetWorkError(t);*/
                            showNotfoundLayout(true);
                        }
                    });
        }
    }


    int currentSeasonPos, currentMonthPos;
    @OnClick({R.id.choose_year_btn, R.id.choose_month_btn, R.id.notfound_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_year_btn:
                new MaterialDialog.Builder(getContext())
                        .items(seasonList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                currentSeasonPos = which;
                                currentSeasonId = leagueSeasonList.get(which).getRow_id();
                                chooseYearText.setText(seasonList.get(which));
                                if (isInNationalCup){
                                    currentMonthId  = leagueSeasonList.get(0).getMonthData().get(0).getMonth();
                                }else{
                                    currentMonthId = 0;
                                    chooseMonthText.setText(monthList.get(currentMonthId));
                                }
                                getLeagueRanking();
                            }
                        }).show();
                break;
            case R.id.choose_month_btn:
                new MaterialDialog.Builder(getContext())
                        .items(monthList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                chooseMonthText.setText(monthList.get(which));
                                List<Club> clubList = groupStandingList.get(which).get_LStanding();
                                if (clubList == null || clubList.size() == 0){
                                    showNotfoundLayout(true);
                                }else {
                                    showNotfoundLayout(false);
                                }
                                adapter = new LeagueRankingAdapter(clubList, getContext(), LeagueDetail_RankingFragment.this);
                                recycer.setAdapter(adapter);
                            }
                        }).show();
                break;
            case R.id.notfound_layout:
                break;
        }
    }

    public void showNotfoundLayout(boolean notfound){
        notfoundLayout.setVisibility(notfound ? View.VISIBLE : View.GONE);
        recycer.setVisibility(notfound ? View.GONE : View.VISIBLE);
    }
}
