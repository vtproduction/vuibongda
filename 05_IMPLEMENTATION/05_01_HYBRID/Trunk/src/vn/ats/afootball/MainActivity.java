package vn.ats.afootball;

import org.apache.cordova.DroidGap;

import vn.ats.aBongDa.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class MainActivity extends DroidGap {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		//Check network connection
		if (!this.hasConnection())
		{
			//Show alert message to exit app
			this.showMessageDialog("Không có kết nối mạng", "Cảnh báo");
		}
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String imei  = telephonyManager.getDeviceId();
		
		super.onCreate(savedInstanceState);
		
		/*
		SharedPreferences settings = getSharedPreferences("localStorage", 0);
	    if(!settings.getBoolean("isShortcutCreated", false))
		{
			SharedPreferences.Editor editor = settings.edit();
		    editor.putBoolean("isShortcutCreated", true);
		    editor.commit();
			addShortcut();
		}*/
		super.root.setBackgroundColor(Color.BLACK);
		super.setIntegerProperty("splashscreen", R.drawable.splash);
		super.loadUrl("file:///android_asset/www/soccer.html?imei="+imei, 60000);
		//super.loadUrl("file:///android_asset/www/index.html");
	}
	
	/*
	private void addShortcut() {
		Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
		shortcutIntent.setAction(Intent.ACTION_MAIN);
		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		getApplicationContext().sendBroadcast(addIntent);
    }
    */

	private boolean hasConnection()
	{
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		 NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		 if (wifiNetwork != null && wifiNetwork.isConnected()){
			 return true;
		 }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	}

	private void showMessageDialog(String message, String title)
	{
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setButton("Thoát", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		alert.show();
	}
}
