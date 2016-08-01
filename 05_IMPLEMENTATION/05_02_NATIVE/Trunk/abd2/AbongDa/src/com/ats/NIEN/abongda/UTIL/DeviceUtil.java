package com.ats.NIEN.abongda.UTIL;

import java.util.Calendar;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class DeviceUtil {
	public Context context;

	public DeviceUtil(Context context) {
		super();
		this.context = context;
	}

	public DeviceUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	public int getDeviceHeight() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		try {
			display.getSize(size);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			size.x = display.getWidth();
			size.y = display.getHeight();
		}
		return size.y;
	}

	@SuppressWarnings("deprecation")
	public int getDeviceWidth() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		try {
			display.getSize(size);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			size.x = display.getWidth();
			size.y = display.getHeight();
		}
		return size.x;
	}

	public void getAllSharePref() {
		SharedPreferences prefs = context.getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		Map<String, ?> keys = prefs.getAll();

		for (Map.Entry<String, ?> entry : keys.entrySet()) {
			Log.d("SPRS: ", entry.getKey() + " : "
					+ entry.getValue().toString());
		}
	}
	
	public int getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH)+ 1;
		return month;
	}
	
	public String getPhoneNumber(){
		TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String mPhoneNumber = tMgr.getLine1Number();
		Log.d("DU", mPhoneNumber);
		return mPhoneNumber;
	}
}
