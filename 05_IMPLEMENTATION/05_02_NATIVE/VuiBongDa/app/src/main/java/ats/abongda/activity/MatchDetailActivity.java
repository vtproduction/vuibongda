package ats.abongda.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.parceler.Parcels;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.customClass.AccentRemover;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.customClass.view.viewModule.MatchBetRatioModule;
import ats.abongda.customClass.view.viewModule.MatchDetailModule;
import ats.abongda.customClass.view.viewModule.MatchHistoryModule;
import ats.abongda.customClass.view.viewModule.MatchLineupModule;
import ats.abongda.customClass.view.viewModule.MatchTvshowModule;
import ats.abongda.helper.DeviceUlti;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.Match;
import ats.abongda.model.match.Clip;
import ats.abongda.model.match.InfoList;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 23-Aug-16.
 */
public class MatchDetailActivity extends BaseActivity {


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
    @BindViews({R.id.panel_1, R.id.panel_1_5, R.id.panel_2, R.id.panel_3, R.id.panel_4, R.id.panel_5})
    LinearLayout[] panels;
    @BindViews({R.id.tab_ind_1_5, R.id.tab_ind_2, R.id.tab_ind_3, R.id.tab_ind_4, R.id.tab_ind_5})
    ImageView[] tabInds;
    @BindViews({R.id.tab_1_5, R.id.tab_2, R.id.tab_3, R.id.tab_4, R.id.tab_5})
    RelativeLayout[] tabs;
    @BindView(R.id.mainlayout)
    LinearLayout mainlayout;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    @BindView(R.id.clip_pager)
    ViewPager clipPager;
    private int matchId;
    private MatchDetailModule matchDetailModule;
    private MatchBetRatioModule matchBetRatioModule;
    private MatchTvshowModule matchTvshowModule;
    private MatchLineupModule matchLineupModule;
    private MatchHistoryModule matchHistoryModule;
    public static final int MATCHDETAILMODULE_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchId = getIntent().getIntExtra("matchId", -1);
        //matchId = 19604;
        if (matchId == -1) {
            ToastUtil.showError();
            finish();
            return;
        }
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        actionBarTitle.setText(R.string.tran_dau);
        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollview.getScrollY();
                if (scrollY == 0) swiper.setEnabled(true);
                else swiper.setEnabled(false);

            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMatch(false);
            }
        });
        getMatch(true);
    }


    private void onTabClicked(int pos) {
        try {
            int panelVisibleState = panels[pos + 1].getVisibility();
            if (panelVisibleState == View.VISIBLE) {
                panels[pos + 1].setVisibility(View.GONE);
                tabInds[pos].setImageResource(R.drawable.ic_action_expand);
            } else {
                panels[pos + 1].setVisibility(View.VISIBLE);
                tabInds[pos].setImageResource(R.drawable.ic_action_collapse);
            }

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    class HeaderViewPagerAdapter extends android.support.v4.view.PagerAdapter {
        private int mPageCount;
        private List<Clip> imageList = new ArrayList<>();

        public HeaderViewPagerAdapter(List<Clip> imageList) {
            this.imageList = imageList;
            this.mPageCount = imageList.size();
        }

        @Override
        public int getCount() {
            return mPageCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == (View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View v = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_match_clip, container, false);
            container.addView(v);
            ImageView imageView = (ImageView)v.findViewById(R.id.image) ;
            imageView.setImageBitmap(ImageUlti.retriveVideoFrameFromVideo(imageList.get(position).getClip_link()));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MatchDetailActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("url", imageList.get(position).getClip_link());
                    startActivity(intent);
                }
            });
            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            //must be overridden else throws exception as not overridden.
            // Log.d("Tag", collection.getChildCount()+"");
            collection.removeView((View) view);
        }


    }


    @OnClick({R.id.action_bar_leftbtn, R.id.notfound_layout, R.id.tab_1_5, R.id.tab_2, R.id.tab_3, R.id.tab_4, R.id.tab_5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.notfound_layout:

                break;
            case R.id.tab_1_5:
                onTabClicked(0);
                break;
            case R.id.tab_2:
                onTabClicked(1);
                break;
            case R.id.tab_3:
                onTabClicked(2);
                break;
            case R.id.tab_4:
                onTabClicked(3);
                break;
            case R.id.tab_5:
                onTabClicked(4);
                break;
        }
    }

    public static interface ModuleInteractImp {
        void onDataloaded(boolean success);

        void onClicked(int code, Object data);
    }

    private void getMatch(boolean firstLoad) {
        if (firstLoad)
            showProgressDialog();
        else
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
        api.getMatchFullDetail(getIMEI(), matchId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            showData(response.body());
                        } catch (Exception e) {

                            LogUtil.e(e);

                            ToastUtil.showError();
                        } finally {
                            hideProgressDialog();
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        hideProgressDialog();
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
    }

    MatchDetail matchDetail;

    private void showData(JsonElement detail) {
        Gson gson = new Gson();
        matchDetail = gson.fromJson(detail.getAsJsonArray().get(0), MatchDetail.class);
        matchDetail.set_matchArray(new ArrayList<Match>());
        List<Clip> clips = getMatchVideos();
        HeaderViewPagerAdapter adapter = new HeaderViewPagerAdapter(clips);
        clipPager.setClipToPadding(false);
        clipPager.setPadding(DeviceUlti.dpToPx(20), 0, DeviceUlti.dpToPx(20), 0);
        clipPager.setPageMargin(DeviceUlti.dpToPx(10));
        clipPager.setAdapter(adapter);
        for (int i = 1; i < detail.getAsJsonArray().size(); i++) {
            Match match = gson.fromJson(detail.getAsJsonArray().get(i), Match.class);
            if (match != null)
                matchDetail.get_matchArray().add(match);
        }
        if (matchDetailModule == null)
            matchDetailModule = new MatchDetailModule(new ModuleInteractImp() {
                @Override
                public void onDataloaded(boolean success) {

                }

                @Override
                public void onClicked(int code, Object data) {
                    Intent intent = new Intent(MatchDetailActivity.this, ClubInforActivity.class);
                    intent.putExtra("clubId", code);
                    startActivity(intent);
                }
            }, new WeakReference<Context>(this), matchDetail);
        if (matchBetRatioModule == null)
            matchBetRatioModule = new MatchBetRatioModule(new ModuleInteractImp() {
                @Override
                public void onDataloaded(boolean success) {

                }

                @Override
                public void onClicked(int code, Object data) {
                    performBet(code, data);
                }
            }, new WeakReference<Context>(this), matchDetail);

        if (matchTvshowModule == null)
            matchTvshowModule = new MatchTvshowModule(new ModuleInteractImp() {
                @Override
                public void onDataloaded(boolean success) {

                }

                @Override
                public void onClicked(int code, Object data) {
                    registerTVshow();
                }
            }, new WeakReference<Context>(this), matchDetail);
        if (matchLineupModule == null)
            matchLineupModule = new MatchLineupModule(new ModuleInteractImp() {
                @Override
                public void onDataloaded(boolean success) {

                }

                @Override
                public void onClicked(int code, Object data) {

                }
            }, new WeakReference<Context>(this), matchDetail);
        if (matchHistoryModule == null)
            matchHistoryModule = new MatchHistoryModule(new ModuleInteractImp() {
                @Override
                public void onDataloaded(boolean success) {

                }

                @Override
                public void onClicked(int code, Object data) {
                    Intent intent = new Intent(MatchDetailActivity.this, MatchDetailActivity.class);
                    intent.putExtra("matchId", code);
                    startActivity(intent);
                }
            }, new WeakReference<Context>(this), matchDetail);


        panels[0].removeAllViews();
        panels[0].addView(matchDetailModule.getMainView());
        panels[2].removeAllViews();
        panels[2].addView(matchBetRatioModule.getMainView());
        panels[3].removeAllViews();
        panels[3].addView(matchTvshowModule.getMainView());
        panels[4].removeAllViews();
        panels[4].addView(matchLineupModule.getMainView());
        panels[5].removeAllViews();
        panels[5].addView(matchHistoryModule.getMainView());


        if (matchDetail.get_matchStatus() == 1 || matchDetail.get_matchStatus() > 2) {
            panels[3].setVisibility(View.GONE);
            tabs[2].setVisibility(View.GONE);
        } else {
            panels[3].setVisibility(View.VISIBLE);
            tabs[2].setVisibility(View.VISIBLE);
        }

        if (matchDetail.get_lineup_list() == null || matchDetail.get_lineup_list().size() == 0) {
            panels[4].setVisibility(View.GONE);
            tabs[3].setVisibility(View.GONE);
        } else {
            panels[4].setVisibility(View.VISIBLE);
            tabs[3].setVisibility(View.VISIBLE);
        }
    }

    public List<Clip> getMatchVideos() {
        List<Clip> videos = new ArrayList<>();
        List<Clip> clips = new ArrayList<>();
        clips.addAll(matchDetail.get_clips());
        for (Clip clip : matchDetail.get_clips()) {
            String clipDes = AccentRemover.removeAccent(clip.Clip_description);
            String clipMin = AccentRemover.removeAccent(clip.getMatch_minute());
            for (InfoList infoList : matchDetail.get_info_list()) {
                String infoDes = infoList.getGuestInfo().length() == 0 ? AccentRemover.removeAccent(infoList.getHomeInfo())
                        : AccentRemover.removeAccent(infoList.getGuestInfo());
                String infoStatus = AccentRemover.removeAccent(infoList.getStatus());
                if (clipMin.contains(infoStatus) || infoStatus.contains(clipMin))
                    if (clipDes.contains(infoDes) || infoDes.contains(clipDes)) {
                        videos.add(clip);
                    }


            }
        }
        clips.removeAll(videos);
        return matchDetail.get_clips(); // TODO: 30-Sep-16 CHANGE LATER !!!!
    }


    private void performBet(int code, Object data) {
        Intent intent;
        if (code == 7) {
            intent = new Intent(this, BetActivity.class);
            intent.putExtra("betType", code);
            intent.putExtra("match", Parcels.wrap(matchDetail));
            startActivity(intent);
        } else {
            intent = new Intent(this, BetWinnerActivity.class);
            intent.putExtra("betType", code);
            intent.putExtra("match", Parcels.wrap(matchDetail));
            startActivity(intent);
        }
    }


    private void registerTVshow() {
        showProgressDialog();
        api.requestNotification(MainApplication.get().getRegId(), matchId)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        hideProgressDialog();
                        if (response.body().equals("1")) {
                            ToastUtil.show("Bạn sẽ nhận được thông báo về kết quả trận đấu này khi kết thúc!");
                            panels[2].setVisibility(View.GONE);
                            tabs[1].setVisibility(View.GONE);
                        } else {
                            ToastUtil.showError();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        hideProgressDialog();
                        LogUtil.e(t);
                        ToastUtil.showNetWorkError(t);
                    }
                });
    }
}
