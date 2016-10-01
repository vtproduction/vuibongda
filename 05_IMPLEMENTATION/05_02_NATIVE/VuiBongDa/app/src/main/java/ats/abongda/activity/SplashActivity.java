package ats.abongda.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.config.Config;
import ats.abongda.helper.LogUtil;
import ats.abongda.model.AppUpdate;
import ats.abongda.model.GeneralConfiguration;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class SplashActivity extends BaseActivity {



    @BindView(R.id.splash_main_logo)
    ImageView splashMainLogo;
    GoogleCloudMessaging gcm;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String regid, message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication.setBaseActivity(this);
        setContentView(R.layout.activity_flash_screen);
        ButterKnife.bind(this);

        /*try {
            Bitmap bitmap = retriveVideoFrameFromVideo("http://video.24h.com.vn/upload/3-2016/videoclip/2016-09-18/1474133325-leicester_burnley.mp4");
            splashMainLogo.setImageBitmap(bitmap);
        }catch (Exception e){
            LogUtil.e(e);
        }*/
        getGeneralConfiguration();


    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            LogUtil.e(e);

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 12:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    isPermissionGranted = true;
                }else{
                    isPermissionGranted = false;
                }
                //checkForUpdate();
                if (MainApplication.get().getRegId().equals("")) {
                    if (checkPlayServices()) {
                        gcm = GoogleCloudMessaging.getInstance(this);
                        regid = getRegistrationId();
                        if (!regid.isEmpty()) {
                            LogUtil.d("GCMess: regId: " + regid);
                        } else {
                            registerInBackground();
                        }
                        checkForUpdate();
                    }else{
                        checkForUpdate();
                    }
                }else{
                    checkForUpdate();
                }
                break;

            default:
                break;
        }
    }

    private void checkForUpdate(){
        api.checkForUpdate(getIMEI(), Config.PARTER, Config.APP_TYPE, getAppVersion())
                .enqueue(new Callback<AppUpdate>() {
                    @Override
                    public void onResponse(Call<AppUpdate> call, Response<AppUpdate> response) {
                        try {
                            final AppUpdate update = response.body();
                            if (update.getVersionNumber() == 0)
                                getGeneralConfiguration();
                            else {
                                showCustomDialog(getString(R.string.cap_nhat),
                                        update.getIntroductionContent(),
                                        getString(R.string.cap_nhat),
                                        getString(R.string.de_sau),
                                        new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(update.getUpdateLink())));
                                                finish();
                                            }
                                        },
                                        new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                getGeneralConfiguration();
                                            }
                                        });
                            }
                        }catch (Exception e){
                            LogUtil.e(e);
                            getGeneralConfiguration();
                        }
                    }

                    @Override
                    public void onFailure(Call<AppUpdate> call, Throwable t) {
                        getGeneralConfiguration();
                    }
                });

    }


    private void getGeneralConfiguration(){
        api.getGeneralConfiguration(Config.PARTER)
                .enqueue(new Callback<GeneralConfiguration>() {
                    @Override
                    public void onResponse(Call<GeneralConfiguration> call, Response<GeneralConfiguration> response) {
                        try {
                            GeneralConfiguration configuration = response.body();
                            //mPreference.edit().putString("configuration", configuration.toString()).apply();
                            MainApplication.get().setConfiguration(configuration);
                        }catch (Exception e){
                            LogUtil.e(e);
                        }finally {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    changeActivity(HomeActivity.class);
                                    finish();
                                }
                            }, Config.SPLASH_TIMEOUT);
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralConfiguration> call, Throwable t) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeActivity(HomeActivity.class);
                                finish();
                            }
                        }, Config.SPLASH_TIMEOUT);
                    }
                });
    }

    private int getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    private String getRegistrationId() {

        String registrationId = MainApplication.get().getRegId();
        if (registrationId.isEmpty()) {
            LogUtil.d("Registration not found.");
            return "";
        }
        int registeredVersion = MainApplication.get().getAppVersion();
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            LogUtil.d("App version changed.");
            return "";
        }
        return registrationId;
    }


    private void storeRegistrationId( String regId) {
        // final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion();
        LogUtil.d("Saving regId on app version " + appVersion);
        /*SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();*/
        MainApplication.get().setRegId(regId);
        MainApplication.get().setAppVersion(appVersion);
        //new JSONParser(context).sendRegId(regId);
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void registerInBackground() {
        new AsyncTask() {
            @Override
            protected String doInBackground(Object... params) {
                // TODO Auto-generated method stub
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(SplashActivity.this);
                    }

                    regid = gcm.register(Config.SERVER_APP_ID);
                    msg = "Device registered, registration ID=" + regid;
                    sendRegistrationIdToBackend();
                    storeRegistrationId(regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @SuppressWarnings("unused")
            protected void onPostExecute(String msg) {
                LogUtil.d("GCMess: RegId: " + msg);
                checkForUpdate();
            };
        }.execute(null, null, null);

    }

    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();

            } else {
                LogUtil.d("GCMess: This device is not supported.");
                // finish();
            }
            return false;
        } else {
            LogUtil.d("GCMess: This device is supported.");
            return true;
        }
    }
}
