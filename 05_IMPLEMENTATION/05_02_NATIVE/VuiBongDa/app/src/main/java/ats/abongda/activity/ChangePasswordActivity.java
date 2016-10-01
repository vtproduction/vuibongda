package ats.abongda.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.api.APIEndpoint;
import ats.abongda.api.APIService;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 13-Aug-16.
 */
public class ChangePasswordActivity extends BaseActivity {


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.changePassDialog_header)
    TextView changePassDialogHeader;
    @BindView(R.id.changePassPanel_cancelBtn)
    ImageView changePassPanelCancelBtn;
    @BindView(R.id.changePassPanel_pass)
    EditText changePassPanelPass;
    @BindView(R.id.changePassPanel_newpass)
    EditText changePassPanelNewpass;
    @BindView(R.id.changePassPanel_newpasscf)
    EditText changePassPanelNewpasscf;
    @BindView(R.id.changePassDialog_content)
    TextView changePassDialogContent;
    @BindView(R.id.changePassPanel_changePassbtn)
    LinearLayout changePassPanelChangePassbtn;
    @BindView(R.id.changePassPanel)
    LinearLayout changePassPanel;

    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication.setBaseActivity(this);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        actionBarTitle.setText(R.string.doi_mat_khau);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.GONE);
        this.userModel = mApplication.getUser();
        if (userModel == null || userModel.getId() < 0){
            ToastUtil.showError();
            finish();
        }
    }

    @OnClick({R.id.action_bar_leftbtn, R.id.changePassPanel_changePassbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                finish();
                break;
            case R.id.changePassPanel_changePassbtn:
                String oldPassword = changePassPanelPass.getText().toString();
                String newPassword = changePassPanelNewpass.getText().toString();
                String newPasswordCmf = changePassPanelNewpasscf.getText().toString();
                changePassword(oldPassword, newPassword, newPasswordCmf);
                break;
        }
    }

    private boolean validatePassword(String password, String passwordCmf){
        MaterialDialog.Builder errorDialog = new MaterialDialog.Builder(this);
        errorDialog.title(R.string.loi)
                .positiveText(R.string.dong)
                .cancelable(false);
        if (password == null || password.length() < 6){
            errorDialog.content(R.string.error_mk).show();
            return false;
        }
        if (!password.equals(passwordCmf)){
            errorDialog.content(R.string.error_mk2).show();
            return false;
        }
        return true;
    }

    private void changePassword(String oldPassword, String newPassword, String newPasswordCmf){
        if (!validatePassword(newPassword,newPasswordCmf)) return;
        showProgressDialog();
        APIService.build().changePassword(userModel.getId(), oldPassword, newPassword)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        hideProgressDialog();
                        try {
                            int responseCode = response.body();
                            if (responseCode == userModel.getId()){
                                ToastUtil.show(getString(R.string.dmktc));
                                finish();
                            }else {
                                ToastUtil.show(getString(R.string.mkckcx));
                            }
                        }catch (Exception e){
                            LogUtil.e(e);
                            ToastUtil.showError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        LogUtil.e(t);
                        ToastUtil.showError();
                    }
                });
    }
}
