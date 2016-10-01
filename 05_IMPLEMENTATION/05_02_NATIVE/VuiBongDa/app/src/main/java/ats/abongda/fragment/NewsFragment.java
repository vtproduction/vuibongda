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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.NewsDetailActivity;
import ats.abongda.adapter.NewsAdapter;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.AnimationUtil;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.News;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class NewsFragment extends BaseFragment implements ListItemClickImp {
    @BindView(R.id.recycer)
    RecyclerView recycer;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    private List<News> newsList;
    private NewsAdapter adapter;

    public static NewsFragment newInstance(String title) {
        NewsFragment f = new NewsFragment();
        f.title = title;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        ButterKnife.bind(this, v);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycer.setLayoutManager(linearLayoutManager);
        getNews();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
            }
        });
        return v;
    }


    private void getNews() {
        try {
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            recycer.setAdapter(null);
            api.getNews(baseActivity.getIMEI())
                    .enqueue(new Callback<List<News>>() {
                        @Override
                        public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                            swiper.setRefreshing(false);
                            List<News> data = response.body();
                            if (data == null || data.size() == 0) {
                                recycer.setVisibility(View.GONE);
                                notfoundLayout.setVisibility(View.VISIBLE);
                            } else {
                                recycer.setVisibility(View.VISIBLE);
                                notfoundLayout.setVisibility(View.GONE);
                                adapter = new NewsAdapter(getContext(), data, NewsFragment.this);
                                recycer.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<News>> call, Throwable t) {
                            swiper.setRefreshing(false);

                        }
                    });

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void onListItemClicked(int code, Object data) {
        //LogUtil.d("code: " + code + ", data: ");
        try {
            News news = (News) data;
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("title", news.getTitle());
            i.putExtra("content", news.getContent());
            startActivity(i);

        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @OnClick(R.id.notfound_text)
    public void onClick() {

    }

    @Override
    public void loadContent() {

    }


    /*public boolean inWeb = false;
    private void loadWebContent(News news){
        try {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient());
            String htmlContent = news.getContent().replaceAll("<img", "<img style='width:100% !important'")
                    .replaceAll("cdn.ats.vn/upload/football","cdn.tructiep.xyz/football");
            mWebView.loadDataWithBaseURL(null,"<div style='text-align: left'>"+htmlContent+"</div>","text/html","utf-8", null);
            openWeb();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private void openWeb(){
        inWeb = true;
        AnimationUtil.switchView(mWebView, recycer, false);
    }

    public void closeWeb(){
        inWeb = false;
        AnimationUtil.switchView(recycer, mWebView, true);
    }*/


}
