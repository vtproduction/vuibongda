package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 21-Aug-16.
 */
public class ClubInforActivity extends BaseActivity {

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
    @BindView(R.id.clubdetail_clublogo)
    ImageView clubdetailClublogo;
    @BindView(R.id.clubdetail_clubbtn)
    Button clubdetailClubbtn;
    @BindView(R.id.clubdetail_clubdes)
    TextView clubdetailClubdes;
    @BindView(R.id.doihinh_item_pos)
    TextView doihinhItemPos;
    @BindView(R.id.textView100)
    TextView textView100;
    @BindView(R.id.clubdetail_clubdiachi)
    TextView clubdetailClubdiachi;
    @BindView(R.id.textView200)
    TextView textView200;
    @BindView(R.id.clubdetail_clubweb)
    TextView clubdetailClubweb;
    @BindView(R.id.textView300)
    TextView textView300;
    @BindView(R.id.clubdetail_clubemail)
    TextView clubdetailClubemail;
    @BindView(R.id.textView400)
    TextView textView400;
    @BindView(R.id.clubdetail_clubtel)
    TextView clubdetailClubtel;
    @BindView(R.id.tklichsu_date)
    TextView tklichsuDate;
    @BindView(R.id.clubdetail_clubdanhhieu)
    TextView clubdetailClubdanhhieu;
    @BindView(R.id.textView98)
    TextView textView98;
    @BindView(R.id.clubdetail_clubtintuc)
    LinearLayout clubdetailClubtintuc;
    @BindView(R.id.clubdetail_main_panel)
    LinearLayout clubdetailMainPanel;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;

    private int clubId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clubId = getIntent().getIntExtra("clubId", -10);
        if (clubId == -10){
            ToastUtil.showError();
            finish();
            return;
        }
        setContentView(R.layout.activity_club_infor);
        ButterKnife.bind(this);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getClubInfo();
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
        getClubInfo();
    }


    private void getClubInfo(){
        try {
            showNotfound(false);
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            api.getClubInformation(getIMEI(), clubId)
                    .enqueue(new Callback<ClubInfo>() {
                        @Override
                        public void onResponse(Call<ClubInfo> call, Response<ClubInfo> response) {
                            try {
                                ClubInfo info = response.body();
                                setContent(info);
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
                        public void onFailure(Call<ClubInfo> call, Throwable t) {
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


    private void setContent(ClubInfo result){
        try {
            actionBarTitle.setText(result.getName());
            ImageUlti.loadImage(this, result.getLogo(), clubdetailClublogo);
            clubdetailClubdes.setText(result.getName() + "\nThành lập: "
                    + result.getFounded() + "\nSVĐ: " + result.getStadium()
                    + "\nSức chứa: " + result.getCapacity() + "\nHLV: "
                    + result.getManager());
            clubdetailClubdiachi.setText(result.getAddress());
            clubdetailClubweb.setText(result.getWeb());
            clubdetailClubemail.setText(result.getEmail());
            clubdetailClubtel.setText(result.getPhone());
            clubdetailClubdanhhieu.setText(Html.fromHtml(result.getHonours()));
            clubdetailClubtintuc.removeAllViews();
            for (final News news : result.getNews()){
                View tintucItem = LayoutInflater.from(this).inflate(
                        R.layout.item_club_detail_news, null);
                TextView text = (TextView) tintucItem
                        .findViewById(R.id.clubdetail_tintucitem_text);
                text.setText(news.getTitle());
                clubdetailClubtintuc.addView(tintucItem);

                tintucItem.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(ClubInforActivity.this, NewsDetailActivity.class);
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


    @OnClick({R.id.action_bar_leftbtn, R.id.notfound_layout, R.id.clubdetail_clubbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.notfound_layout:
                break;
            case R.id.clubdetail_clubbtn:
                Intent intent = new Intent(this, LineupActivity.class);
                intent.putExtra("clubId", clubId);
                startActivity(intent);
                break;
        }
    }
}
