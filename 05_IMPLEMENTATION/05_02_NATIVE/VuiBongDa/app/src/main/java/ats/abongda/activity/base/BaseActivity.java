package ats.abongda.activity.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.api.APIEndpoint;
import ats.abongda.api.APIService;
import ats.abongda.helper.LogUtil;
import ats.abongda.model.UserModel;

/**
 * Created by NienLe on 02-Aug-16.
 */
public class BaseActivity extends AppCompatActivity {
    protected MainApplication mApplication;
    protected BaseActivity mActivity;
    protected MaterialDialog mProgressDialog;
    protected static APIEndpoint api = APIService.build();
    protected SharedPreferences mPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mApplication = MainApplication.get();
        this.mActivity = this;
        this.mApplication.setBaseActivity(this);
        mPreference = getSharedPreferences("config", MODE_PRIVATE);
    }

    public void changeActivity(Class activity){
        Intent i = new Intent(mApplication.getBaseActivity(), activity);
        startActivity(i);
    }



    protected boolean isPermissionGranted = true;
    public String getIMEI() {
        if (isPermissionGranted){
            try {
                TelephonyManager telephonyManager = (TelephonyManager)
                        getSystemService(Context.TELEPHONY_SERVICE);
                return telephonyManager.getDeviceId();
            }catch (Exception e){
                LogUtil.e(e);
                return  "0x00";
            }
        }else {
            return  "0x00";
        }
    }
    public void hideProgressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void showProgressDialog() {
        try {
            mProgressDialog = new MaterialDialog.Builder(this)
                    .title(getString(R.string.vui_long_cho))
                    .content(R.string.dang_tai_noi_dung)
                    .cancelable(false)
                    .widgetColor(getResources().getColor(R.color.colorPrimary))
                    .progress(true, 0).build();
            mProgressDialog.show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void showGeneralError(){
        /*mProgressDialog = */new MaterialDialog.Builder(this)
                .title(R.string.loi)
                .content(R.string.da_co_loi_xay_ra_vui_long_thu_lai_sau)
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .positiveText(R.string.dong)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
        /*if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();*/
    }

    public void showError(String content){
        /*mProgressDialog =*/ new MaterialDialog.Builder(this)
                .title(R.string.loi)
                .content(content)
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .positiveText(R.string.dong)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
        /*if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();*/
    }

    public void showCustomDialog(String title, String content, String posText,
                                 String negText, MaterialDialog.SingleButtonCallback posCallback,
                                 MaterialDialog.SingleButtonCallback negCallback){
        new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(title)
                .content(content)
                .positiveText(posText)
                .negativeText(negText)
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .negativeColor(getResources().getColor(R.color.colorAccent))
                .onPositive(posCallback)
                .onNegative(negCallback).show();
        /*if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();*/
    }

    public void showCustomDialog(String title, String content, String posText,
                                 MaterialDialog.SingleButtonCallback posCallback){
        new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(title)
                .content(content)
                .positiveText(posText)
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .onPositive(posCallback).show();
        /*if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();*/
    }
}
