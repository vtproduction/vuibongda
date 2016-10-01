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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.LeagueClubMatchTimeActivity;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.adapter.LeagueMatchtimeAdapter;
import ats.abongda.customClass.LinearLayoutManagerWithSmoothScroller;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.customClass.view.OffsetDecoration;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.LeagueSeason;
import ats.abongda.model.Match;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 17-Aug-16.
 */
public class LeagueDetail_MatchTimeFragment extends BaseFragment implements ListItemClickImp {

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
    LeagueMatchtimeAdapter adapter;

    public static LeagueDetail_MatchTimeFragment newInstance(int leagueId, String title) {
        LeagueDetail_MatchTimeFragment f = new LeagueDetail_MatchTimeFragment();
        f.title = title;
        f.currentLeagueId = leagueId;
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_league_matchtime, container, false);
        ButterKnife.bind(this, v);
        recycer.setLayoutManager(new LinearLayoutManager(getContext()));
        recycer.setHasFixedSize(true);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeagueMatchTime();
            }
        });
        //getLeagueSeason();
        return v;
    }

    @Override
    public void loadContent() {

    }

    @Override
    public void onListItemClicked(int code, Object data) {
        switch (code){
            case -1:
                Intent matchIntent = new Intent(getContext(), MatchDetailActivity.class);
                matchIntent.putExtra("matchId",(Integer)data);
                startActivity(matchIntent);
                break;
            default:
                Intent intent = new Intent(getContext(), LeagueClubMatchTimeActivity.class);
                intent.putExtra("clubId",code);
                intent.putExtra("clubName",(String) data);
                intent.putExtra("leagueSeasonList", Parcels.wrap(leagueSeasonList));
                startActivity(intent);
                break;
        }
    }

    public void onDataLoaded(int leagueId, List<LeagueSeason> leagueSeasonList){
        this.currentLeagueId = leagueId;
        this.leagueSeasonList = leagueSeasonList;
        this.isInNationalCup = currentLeagueId < 6;
        for (LeagueSeason season : leagueSeasonList){
            if (season.getIsCurrent() == 1){
                currentMonthPos = leagueSeasonList.indexOf(season);
                currentSeasonId = season.getRow_id();
                setMonthList(season);
                if (isInNationalCup){
                    for (LeagueSeason.MonthData data : season.getMonthData())
                        if (data.getIsCurrent() == 1){
                            currentMonthId = data.getMonth();
                            chooseMonthText.setText(monthList.get(season.getMonthData().indexOf(data)));
                            break;
                        }
                }else {
                    currentMonthId = MainApplication.get().getConfiguration() == null ?
                            0 : MainApplication.get().getConfiguration().get_currentCLRound();
                    chooseMonthText.setText(monthList.get(currentMonthId));
                }
                break;
            }
        }

        setSeasonList();
        chooseYearText.setText(seasonList.get(currentSeasonPos));
        getLeagueMatchTime();
    }

    int currentSeasonId = 0, currentMonthId = 0, currentLeagueId;
    public List<LeagueSeason> leagueSeasonList = new ArrayList<>();
    private boolean isInNationalCup = true;
    /*public void getLeagueSeason() {
        isInNationalCup = currentLeagueId < 6;
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        api.getLeagueSeason(baseActivity.getIMEI(), currentLeagueId)
                .enqueue(new Callback<List<LeagueSeason>>() {
                    @Override
                    public void onResponse(Call<List<LeagueSeason>> call, Response<List<LeagueSeason>> response) {
                        try {
                            leagueSeasonList = response.body();
                            for (LeagueSeason season : leagueSeasonList){
                                if (season.getIsCurrent() == 1){
                                    currentMonthPos = leagueSeasonList.indexOf(season);
                                    currentSeasonId = season.getRow_id();
                                    setMonthList(season);
                                    if (isInNationalCup){
                                        for (LeagueSeason.MonthData data : season.getMonthData())
                                            if (data.getIsCurrent() == 1){
                                                currentMonthId = data.getMonth();
                                                chooseMonthText.setText(monthList.get(season.getMonthData().indexOf(data)));
                                                break;
                                            }
                                    }else {
                                        currentMonthId = MainApplication.get().getConfiguration() == null ?
                                                0 : MainApplication.get().getConfiguration().getCurrentCLRound();
                                        chooseMonthText.setText(monthList.get(currentMonthId));
                                    }
                                    break;
                                }
                            }

                            setSeasonList();
                            chooseYearText.setText(seasonList.get(currentSeasonPos));
                            getLeagueMatchTime();

                        } catch (Exception e) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LogUtil.e(e);
                            ToastUtil.showError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LeagueSeason>> call, Throwable t) {
                        swiper.post(new Runnable() {
                            @Override
                            public void run() {
                                swiper.setRefreshing(false);
                            }
                        });
                        LogUtil.e(t);
                        ToastUtil.showNetWorkError(t);
                    }
                });
    }*/

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

    public void setMonthList(LeagueSeason leagueSeason) {
        try {
            monthList.clear();
            if (isInNationalCup){
                for (LeagueSeason.MonthData data : leagueSeason.getMonthData())
                    monthList.add(getString(R.string.thang) + " " + data.getMonth() + "/" + data.getYear());
            }else {
                for (String roundType : getResources().getStringArray(R.array.rounds))
                    monthList.add(roundType);
            }
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public void getLeagueMatchTime() {
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        if (currentLeagueId < 6){
            showNotfoundLayout(false);
            api.getNationalMatchResult(baseActivity.getIMEI(), currentSeasonId, currentLeagueId, currentMonthId, MainApplication.get().getUserId())
                    .enqueue(new Callback<List<Match>>() {
                        @Override
                        public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                            try{
                                if (response.body() == null || response.body().size() == 0){
                                    showNotfoundLayout(true);
                                }else {
                                    showNotfoundLayout(false);
                                }
                                adapter = new LeagueMatchtimeAdapter(response.body(), getContext(), LeagueDetail_MatchTimeFragment.this);
                                recycer.setAdapter(adapter);
                                int posToScroll = 0;
                                for (int i = 0; i < response.body().size(); i++){
                                    if (response.body().get(i).get_matchStatus() == 0){
                                        posToScroll = i;
                                        break;
                                    }
                                }
                                if (posToScroll != 0)
                                    ((LinearLayoutManager)recycer.getLayoutManager()).
                                            scrollToPositionWithOffset(posToScroll, 20);
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
                        public void onFailure(Call<List<Match>> call, Throwable t) {
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
            api.getCupMatchResult(baseActivity.getIMEI(), currentSeasonId, currentLeagueId, currentMonthId, MainApplication.get().getUserId())
                    .enqueue(new Callback<List<Match>>() {
                        @Override
                        public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                            try{
                                if (response.body() == null || response.body().size() == 0){
                                    showNotfoundLayout(true);
                                }else {
                                    showNotfoundLayout(false);
                                }
                                adapter = new LeagueMatchtimeAdapter(response.body(), getContext(), LeagueDetail_MatchTimeFragment.this);
                                recycer.setAdapter(adapter);
                                int posToScroll = 0;
                                for (int i = 0; i < response.body().size(); i++){
                                    if (response.body().get(i).get_matchStatus() == 0){
                                        posToScroll = i;
                                        break;
                                    }
                                }
                                if (posToScroll != 0)
                                    ((LinearLayoutManager)recycer.getLayoutManager()).
                                            scrollToPositionWithOffset(posToScroll, 20);
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
                        public void onFailure(Call<List<Match>> call, Throwable t) {
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
                                chooseMonthText.setText(monthList.get(0));
                                setMonthList(leagueSeasonList.get(which));
                                if (isInNationalCup){
                                    currentMonthId  = leagueSeasonList.get(0).getMonthData().get(0).getMonth();
                                    chooseMonthText.setText(monthList.get(0));

                                }else{
                                    currentMonthId = 0;
                                    chooseMonthText.setText(monthList.get(currentMonthId));
                                }

                                getLeagueMatchTime();
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
                                if (isInNationalCup){
                                    currentMonthId  = leagueSeasonList.get(currentSeasonPos).getMonthData().get(which).getMonth();
                                }else{
                                    currentMonthId = which;
                                }
                                getLeagueMatchTime();
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
