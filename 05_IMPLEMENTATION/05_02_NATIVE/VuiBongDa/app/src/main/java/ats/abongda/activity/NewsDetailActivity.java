package ats.abongda.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NienLe on 16-Aug-16.
 */
public class NewsDetailActivity extends BaseActivity {


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        actionBarTitle.setText(title);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        String htmlContent = content.replaceAll("<img", "<img style='width:100% !important'")
                .replaceAll("cdn.ats.vn/upload/football","cdn.tructiep.xyz/football");
        mWebView.loadDataWithBaseURL(null,"<div style='text-align: left'>"+htmlContent+"</div>","text/html","utf-8", null);

    }

    @OnClick(R.id.action_bar_leftbtn)
    public void onClick() {
        onBackPressed();
    }
}
