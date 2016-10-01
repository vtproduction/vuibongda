package ats.abongda;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ats.abongda.activity.base.BaseActivity;
import ats.abongda.helper.LogUtil;
import ats.abongda.model.GeneralConfiguration;
import ats.abongda.model.UserModel;

/**
 * Created by NienLe on 02-Aug-16.
 */
public class MainApplication extends Application {
    public static MainApplication instance;
    public static BaseActivity mBaseActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static MainApplication get(){
        return instance;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static void setInstance(MainApplication instance) {
        MainApplication.instance = instance;
    }

    public static BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    public static void setBaseActivity(BaseActivity mBaseActivity) {
        MainApplication.mBaseActivity = mBaseActivity;
    }


    public UserModel getUser(){
        try {
            Gson gson = new Gson();
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            String userString = preferences.getString("u","");
            if (userString.equals("")) return null;
            return gson.fromJson(userString, UserModel.class);
        }catch (Exception e){
            LogUtil.e(e);
            return null;
        }
    }

    public void setUser(UserModel user){
        try {
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            if (user == null){
                preferences.edit().putString("u", "").apply();
                return;
            }
            Gson gson = new Gson();
            String userString = gson.toJson(user);
            preferences.edit().putString("u", userString).apply();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public void setConfiguration(GeneralConfiguration configuration){
        try {
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            if (configuration == null){
                preferences.edit().putString("c", "").apply();
                return;
            }
            Gson gson = new Gson();
            String configString = gson.toJson(configuration);
            preferences.edit().putString("c", configString).apply();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public GeneralConfiguration getConfiguration(){
        try {
            Gson gson = new Gson();
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            String configString = preferences.getString("c","");
            if (configString.equals("")) return null;
            return gson.fromJson(configString, GeneralConfiguration.class);
        }catch (Exception e){
            LogUtil.e(e);
            return null;
        }
    }

    public String getRegId(){
        try {
            Gson gson = new Gson();
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            String configString = preferences.getString("r","");
            return configString;
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }

    public void setRegId(String regId){
        try {
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            if (regId == null){
                preferences.edit().putString("r", "").apply();
                return;
            }
            preferences.edit().putString("r", regId).apply();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public int getAppVersion(){
        try {
            Gson gson = new Gson();
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            int appVersion = preferences.getInt("av",0);
            return appVersion;
        }catch (Exception e){
            LogUtil.e(e);
            return Integer.MIN_VALUE;
        }
    }

    public void setAppVersion(int appVersion){
        try {
            SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
            preferences.edit().putString("av", "").apply();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public int getUserId(){
        UserModel model = getUser();
        return model == null ? 0 : model.getId();
    }


}
