package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.adapter.LineupAdapter;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Player;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 21-Aug-16.
 */
public class LineupActivity extends BaseActivity {


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.recycer)
    RecyclerView recycer;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    private int clubId;
    private LineupAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clubId = getIntent().getIntExtra("clubId",-1);
        if (clubId == -1){
            ToastUtil.showError();
            finish();
            return;
        }
        setContentView(R.layout.activity_lineup);
        ButterKnife.bind(this);
        actionBarTitle.setText(R.string.doi_hinh);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        recycer.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }


    private void getData(){
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        try {
            showNotfound(false);
            api.getTeamPlayerInformation(getIMEI(), clubId)
                    .enqueue(new Callback<List<Player>>() {
                        @Override
                        public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                            if (response.body() == null || response.body().size() == 0){
                                showNotfound(true);
                            }else {
                                adapter = new LineupAdapter(response.body(), LineupActivity.this, new ListItemClickImp() {
                                    @Override
                                    public void onListItemClicked(int code, Object data) {
                                        if (code == 0){
                                            Intent intent = new Intent(LineupActivity.this, PlayerDetailActivity.class);
                                            intent.putExtra("playerId", (Integer)data);
                                            startActivity(intent);
                                        }
                                    }
                                });
                                recycer.setAdapter(adapter);
                                showNotfound(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Player>> call, Throwable t) {
                            LogUtil.e(t);
                            ToastUtil.showNetWorkError(t);
                        }
                    });
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


    private void showNotfound(boolean notfound){
        notfoundLayout.setVisibility(notfound ? View.VISIBLE : View.GONE);
        recycer.setVisibility(notfound ? View.GONE : View.VISIBLE);
    }

    @OnClick({R.id.action_bar_leftbtn, R.id.notfound_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.notfound_layout:
                break;
        }
    }
}
