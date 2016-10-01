package ats.abongda.fragment;

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

import ats.abongda.R;
import ats.abongda.adapter.LeagueRankingAdapter;
import ats.abongda.adapter.LeagueStatisticAdapter;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Club;
import ats.abongda.model.LeagueSeason;
import ats.abongda.model.Player;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 17-Aug-16.
 */
public class LeagueDetail_StatisticFragment extends BaseFragment implements ListItemClickImp {


    int currentSeasonId = 0, currentLeagueId, currentSeasonPos;
    @BindView(R.id.choose_year_text)
    TextView chooseYearText;
    @BindView(R.id.choose_year_btn)
    RelativeLayout chooseYearBtn;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.thongkePage_item_stt)
    TextView thongkePageItemStt;
    @BindView(R.id.thongkePage_item_name)
    TextView thongkePageItemName;
    @BindView(R.id.thongkePage_item_bt)
    TextView thongkePageItemBt;
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.recycer)
    RecyclerView recycer;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    private LeagueStatisticAdapter adapter;

    public static LeagueDetail_StatisticFragment newInstance(int leagueId, String title) {
        LeagueDetail_StatisticFragment f = new LeagueDetail_StatisticFragment();
        f.title = title;
        f.currentLeagueId = leagueId;
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_league_statistic, container, false);
        ButterKnife.bind(this, v);
        recycer.setLayoutManager(new LinearLayoutManager(getContext()));
        recycer.setHasFixedSize(true);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeagueStatistic();
            }
        });
        return v;
    }

    @Override
    public void loadContent() {

    }

    @Override
    public void onListItemClicked(int code, Object data) {

    }

    private List<LeagueSeason> leagueSeasonList = new ArrayList<>();
    public void onDataLoaded(int leagueId, List<LeagueSeason> leagueSeasonList){
        this.currentLeagueId  = leagueId;
        this.leagueSeasonList = leagueSeasonList;
        for (LeagueSeason season : leagueSeasonList){
            if (season.getIsCurrent() == 1){
                currentSeasonPos = leagueSeasonList.indexOf(season);
                currentSeasonId = season.getRow_id();
                break;
            }
        }
        setSeasonList();
        chooseYearText.setText(seasonList.get(currentSeasonPos));
        getLeagueStatistic();
    }

    public void setSeasonList() {
        try {
            seasonList.clear();
            for (LeagueSeason season : leagueSeasonList)
                seasonList.add(season.getSeason_name());
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private List<String> seasonList = new ArrayList<>();
    public void getLeagueStatistic(){
        try {
            showNotfoundLayout(false);
            api.getPlayerStatisticBySeason(baseActivity.getIMEI(), currentSeasonId, currentLeagueId, 1)
                    .enqueue(new Callback<List<Player>>() {
                        @Override
                        public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                            try{
                                if (response.body() == null || response.body().size() == 0){
                                    showNotfoundLayout(true);
                                }else {
                                    showNotfoundLayout(false);
                                }
                                adapter = new LeagueStatisticAdapter(response.body(), getContext(), LeagueDetail_StatisticFragment.this);
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
                        public void onFailure(Call<List<Player>> call, Throwable t) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LogUtil.e("~~~~");
                            LogUtil.e(t);
                            showNotfoundLayout(true);
                        }
                    });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @OnClick({R.id.choose_year_btn, R.id.notfound_layout})
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
                                getLeagueStatistic();
                            }
                        }).show();
                break;
        }
    }

    public void showNotfoundLayout(boolean notfound){
        notfoundLayout.setVisibility(notfound ? View.VISIBLE : View.GONE);
        recycer.setVisibility(notfound ? View.GONE : View.VISIBLE);
    }
}
