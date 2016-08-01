package com.ats.NIEN.abongda.UTIL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;

@SuppressLint("NewApi")
public class NetworkUtil{
	public NetworkUtil(){

	}
	
	public String doGetRequest(String url){
		StrictMode.setThreadPolicy(ThreadPolicy.LAX);
		HttpGet httpget = new HttpGet(url);
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
	    try {
			return new DefaultHttpClient().execute(httpget, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "";

	}
	
	public String doPostRequest(List<NameValuePair> params, String url) {
		
		try {
			StrictMode.setThreadPolicy(ThreadPolicy.LAX);
			// step 2: create object connect to target url
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if (params != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			}
			// step 3: execute request to server and handle
			ResponseHandler<String> httpResponse = new BasicResponseHandler();
			String data = httpClient.execute(httpPost, httpResponse);
			Log.d("LOG", "finish fetching from server, return string: " + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("LOG", "finish fetching from server, return null string: ");
		return "";
	}
}