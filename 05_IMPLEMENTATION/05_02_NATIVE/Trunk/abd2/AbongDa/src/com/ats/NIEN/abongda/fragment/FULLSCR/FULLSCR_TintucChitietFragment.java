package com.ats.NIEN.abongda.fragment.FULLSCR;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class FULLSCR_TintucChitietFragment extends BaseFragment{
	public String htmlContent;
	public WebView mWebView;

	public FULLSCR_TintucChitietFragment(String htmlContent) {
		super();
		this.htmlContent = htmlContent;
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "InflateParams" })
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((BaseActivity) getActivity()).changeRightImageToBack();
		View layout = inflater.inflate(R.layout.tintuc_chitiet, null);
		mWebView = (WebView) layout.findViewById(R.id.tintuc_webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient());
		htmlContent = htmlContent.replaceAll("<img", "<img style='width:100% !important'");
		mWebView.loadDataWithBaseURL(null,"<div style='text-align: left'>"+htmlContent+"</div>","text/html","utf-8", null);
		return layout;
	}
}
