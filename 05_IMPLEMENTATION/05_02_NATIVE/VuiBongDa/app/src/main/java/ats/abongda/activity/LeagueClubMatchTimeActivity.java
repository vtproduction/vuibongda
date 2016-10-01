package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.adapter.LeagueClubMatchtimeAdapter;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
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
 * Created by NienLe on 21-Aug-16.
 */
public class LeagueClubMatchTimeActivity extends BaseActivity implements ListItemClickImp{


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.choose_year_text)
    TextView chooseYearText;
    @BindView(R.id.choose_year_btn)
    RelativeLayout chooseYearBtn;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.recycer)
    RecyclerView recycer;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    private int clubId;
    private String clubName;
    private List<LeagueSeason> leagueSeasonList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clubId = getIntent().getIntExtra("clubId", -1);
        clubName = getIntent().getStringExtra("clubName");
        leagueSeasonList = Parcels.unwrap(getIntent().getParcelableExtra("leagueSeasonList"));
        if (clubId == -1) {
            ToastUtil.showError();
            finish();
            return;
        }
        setContentView(R.layout.activity_league_club_match_time);

        ButterKnife.bind(this);
        recycer.setLayoutManager(new LinearLayoutManager(this));
        actionBarTitle.setText(clubName);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        getData();
    }

    @OnClick({R.id.action_bar_leftbtn, R.id.choose_year_btn, R.id.notfound_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.choose_year_btn:
                new MaterialDialog.Builder(this)
                        .items(seasonList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                currentSeasonPos = which;
                                currentSeasonId = leagueSeasonList.get(which).getRow_id();
                                chooseYearText.setText(seasonList.get(which));
                                getMatches();
                            }
                        }).show();
                break;
            case R.id.notfound_layout:
                break;
        }
    }

    int currentSeasonId = 0, currentSeasonPos = 0;
    private void getData(){
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        try {
            for (LeagueSeason season : leagueSeasonList){
                if (season.getIsCurrent() == 1){
                    currentSeasonId = season.getRow_id();
                    currentSeasonPos= leagueSeasonList.indexOf(season);
                }
            }

            setSeasonList();
            chooseYearText.setText(seasonList.get(currentSeasonPos));
            getMatches();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private void getMatches(){
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        api.getMatchOfClubBySeason(getIMEI(), currentSeasonId, clubId)
                .enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                        swiper.post(new Runnable() {
                            @Override
                            public void run() {
                                swiper.setRefreshing(false);
                            }
                        });
                        if (response.body() == null || response.body().size() == 0){
                            showNotfoundLayout(true);
                        }else {
                            showNotfoundLayout(false);
                        }

                        adapter = new LeagueClubMatchtimeAdapter(response.body(), LeagueClubMatchTimeActivity.this,
                                LeagueClubMatchTimeActivity.this);
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
                    }

                    @Override
                    public void onFailure(Call<List<Match>> call, Throwable t) {
                        swiper.post(new Runnable() {
                            @Override
                            public void run() {
                                swiper.setRefreshing(false);
                            }
                        });
                    }
                });
    }

    @Override
    public void onListItemClicked(int code, Object data) {
        switch (code){
            case -1:
                Intent matchIntent = new Intent(this, MatchDetailActivity.class);
                matchIntent.putExtra("matchId",(Integer)data);
                startActivity(matchIntent);
                break;
            default:
                if (code == clubId) return;
                Intent intent = new Intent(this, LeagueClubMatchTimeActivity.class);
                intent.putExtra("clubId",code);
                intent.putExtra("clubName",(String) data);
                intent.putExtra("leagueSeasonList", Parcels.wrap(leagueSeasonList));
                startActivity(intent);
                break;
        }
    }

    private LeagueClubMatchtimeAdapter adapter;
    public void showNotfoundLayout(boolean notfound){
        notfoundLayout.setVisibility(notfound ? View.VISIBLE : View.GONE);
        recycer.setVisibility(notfound ? View.GONE : View.VISIBLE);
    }

    private List<String> seasonList = new ArrayList<>();

    public void setSeasonList() {
        try {
            seasonList.clear();
            for (LeagueSeason season : leagueSeasonList)
                seasonList.add(season.getSeason_name());
        }catch (Exception e){
            LogUtil.e(e);
        }
    }
}
