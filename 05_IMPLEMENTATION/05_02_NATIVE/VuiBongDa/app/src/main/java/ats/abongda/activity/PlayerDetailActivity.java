package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.ClubInfo;
import ats.abongda.model.News;
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
public class PlayerDetailActivity extends BaseActivity {


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
    @BindView(R.id.cauthudetail_cauthulogo)
    ImageView cauthudetailCauthulogo;
    @BindView(R.id.cauthudetail_cauthudes)
    TextView cauthudetailCauthudes;
    @BindView(R.id.doihinh_item_pos)
    TextView doihinhItemPos;
    @BindView(R.id.textView100)
    TextView textView100;
    @BindView(R.id.cauthudetail_cauthudiachi)
    TextView cauthudetailCauthudiachi;
    @BindView(R.id.textView200)
    TextView textView200;
    @BindView(R.id.cauthudetail_cauthuweb)
    TextView cauthudetailCauthuweb;
    @BindView(R.id.textView300)
    TextView textView300;
    @BindView(R.id.cauthudetail_cauthuemail)
    TextView cauthudetailCauthuemail;
    @BindView(R.id.textView400)
    TextView textView400;
    @BindView(R.id.cauthudetail_cauthutel)
    TextView cauthudetailCauthutel;
    @BindView(R.id.textView98)
    TextView textView98;
    @BindView(R.id.cauthudetail_cauthutintuc)
    LinearLayout cauthudetailCauthutintuc;
    @BindView(R.id.cauthudetail_main_panel)
    LinearLayout cauthudetailMainPanel;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    private int playerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerId = getIntent().getIntExtra("playerId", -1);
        if (playerId == -1) {
            ToastUtil.showError();
            finish();
            return;
        }
        setContentView(R.layout.activity_player_detail);
        ButterKnife.bind(this);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPlayerInfo();
            }
        });
        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollview.getScrollY();
                if(scrollY == 0) swiper.setEnabled(true);
                else swiper.setEnabled(false);

            }
        });

        getPlayerInfo();
    }

    private void getPlayerInfo(){
        try {
            showNotfound(false);
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            api.getPlayerInformation(getIMEI(), playerId)
                    .enqueue(new Callback<Player>() {
                        @Override
                        public void onResponse(Call<Player> call, Response<Player> response) {
                            try {

                                setContent(response.body());
                                showNotfound(false);
                            }catch (Exception e){
                                LogUtil.e(e);
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
                        public void onFailure(Call<Player> call, Throwable t) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LogUtil.e(t);
                            ToastUtil.showNetWorkError(t);
                            showNotfound(true);
                        }
                    });

        }catch (Exception e){
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(false);
                }
            });
            LogUtil.e(e);
            showNotfound(true);
        }
    }

    private void setContent(Player result){
        try{
            actionBarTitle.setText(result.getName());
            ImageUlti.loadImage(this, result.getImage(), cauthudetailCauthulogo);
            cauthudetailCauthudes.setText(result.getName() + "\nVị trí: " + result.getPosition()
                    + "\nSố áo: " + result.getNumber());
            cauthudetailCauthudiachi.setText(result.getHeight());
            cauthudetailCauthuweb.setText(result.getWeigt());
            cauthudetailCauthuemail.setText(result.getDateOfBirth());
            cauthudetailCauthutel.setText(result.getNationality());
            cauthudetailCauthutintuc.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);
            for (final News news : result.getNews()){
                View tintucItem = LayoutInflater.from(this).inflate(
                        R.layout.item_club_detail_news, null);
                TextView text = (TextView) tintucItem
                        .findViewById(R.id.clubdetail_tintucitem_text);
                text.setText(news.getTitle());
                cauthudetailCauthutintuc.addView(tintucItem);

                tintucItem.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(PlayerDetailActivity.this, NewsDetailActivity.class);
                        i.putExtra("title", news.getTitle());
                        i.putExtra("content", news.getContent());
                        startActivity(i);
                    }
                });
            }
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private void showNotfound(boolean notfound){
        notfoundLayout.setVisibility(notfound ? View.VISIBLE :View.GONE);
        scrollview.setVisibility(notfound ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.action_bar_leftbtn)
    public void onClick() {
        onBackPressed();
    }
}
