package com.ats.NIEN.abongda.UTIL;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class InternetChecking extends AsyncTask<String, String, Boolean>{
	public WeakReference<Context> mContext;
	public ProgressHUD pDialog;
	public AsyncTask<Object, Object, Object> mAsyncTask;
	private InternetCheckingCallback callback;
	private boolean isShowHUD;
//	private Class<AsyncTask> asyncTaskClass;
	
	public InternetChecking(WeakReference<Context> mContext, InternetCheckingCallback callback, boolean isShowHUD) {
		super();
		this.isShowHUD = isShowHUD;
		this.mContext = mContext;
//		this.mAsyncTask = mAsyncTask;
		this.callback = callback != null ? callback : new InternetCheckingCallback() {

			@Override
			public void onCheckComplete(boolean result, Dialog progressDialog) {
				// TODO Auto-generated method stub
				
			}
			
			
		};
	}
	public InternetChecking(WeakReference<Context> mContext, InternetCheckingCallback callback) {
		super();
		this.isShowHUD = true;
		this.mContext = mContext;
//		this.mAsyncTask = mAsyncTask;
		this.callback = callback != null ? callback : new InternetCheckingCallback() {

			@Override
			public void onCheckComplete(boolean result, Dialog progressDialog) {
				// TODO Auto-generated method stub
				
			}
			
			
		};
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (isShowHUD) {
			pDialog = ProgressHUD.show(mContext.get(), "", true, false, null);
		}
		
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub

		ConnectivityManager cm = (ConnectivityManager) mContext.get()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			try {
				URL url = new URL("http://www.google.com.vn");
				HttpURLConnection urlc = (HttpURLConnection) url
						.openConnection();
				urlc.setConnectTimeout(3000);
				urlc.connect();
				if (urlc.getResponseCode() == 200) {
					return true;
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d("internet checking", "Mes: " + result);
		if (!result) {
			final CustomAlertDialog mDialog = new CustomAlertDialog(
					mContext.get(), "Lỗi", "Không có kết nối internet", "OK");
			mDialog.show();
		}
		callback.onCheckComplete(result, pDialog);
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	public interface InternetCheckingCallback {
		public void onCheckComplete(boolean result, Dialog progressDialog);

	}
}
