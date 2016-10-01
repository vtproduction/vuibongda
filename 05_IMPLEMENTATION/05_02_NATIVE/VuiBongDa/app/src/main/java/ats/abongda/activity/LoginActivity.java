package ats.abongda.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.helper.KeyboardUtil;
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
 * Created by NienLe on 07-Aug-16.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.signinDialog_header)
    TextView signinDialogHeader;
    @BindView(R.id.SigninPanel_cancelBtn)
    ImageView SigninPanelCancelBtn;
    @BindView(R.id.SigninPanel_username)
    EditText SigninPanelUsername;
    @BindView(R.id.SigninPanel_password)
    EditText SigninPanelPassword;
    @BindView(R.id.signinDialog_content)
    TextView signinDialogContent;
    @BindView(R.id.SigninPanel_signinbtn)
    LinearLayout SigninPanelSigninbtn;
    @BindView(R.id.signinDialog_btnText)
    TextView signinDialogBtnText;
    @BindView(R.id.SigninPanel_signupbtn)
    LinearLayout SigninPanelSignupbtn;
    @BindView(R.id.SigninPanel)
    LinearLayout SigninPanel;
    @BindView(R.id.tklichsu_stt)
    TextView tklichsuStt;
    @BindView(R.id.SignupPanel_cancelBtn)
    ImageView SignupPanelCancelBtn;
    @BindView(R.id.SignupPanel_username)
    EditText SignupPanelUsername;
    @BindView(R.id.SignupPanel_password)
    EditText SignupPanelPassword;
    @BindView(R.id.SignupPanel_passwordcmf)
    EditText SignupPanelPasswordcmf;
    @BindView(R.id.tklichsu_date)
    TextView tklichsuDate;
    @BindView(R.id.SignupPanel_signupbtn)
    LinearLayout SignupPanelSignupbtn;
    @BindView(R.id.SignupPanel)
    LinearLayout SignupPanel;

    private boolean isInSigninTab = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication.setBaseActivity(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        actionBarTitle.setText(R.string.dang_nhap);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.GONE);
    }

    @OnClick({R.id.action_bar_leftbtn, R.id.SigninPanel_signinbtn, R.id.SigninPanel_signupbtn, R.id.SignupPanel_signupbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.SigninPanel_signinbtn:
                Signin();
                break;
            case R.id.SigninPanel_signupbtn:
                switchPanel();
                break;
            case R.id.SignupPanel_signupbtn:
                SignUp();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (isInSigninTab){
            setResult(RESULT_CANCELED);
            finish();
        }
        else
            switchPanel();
    }

    private boolean validateSigninInput(String username, String password){
        return  true;
    }

    private boolean validateSignupInput(String username, String password, String passwordCmf){
        MaterialDialog.Builder errorDialog = new MaterialDialog.Builder(this);
        errorDialog.title(R.string.loi)
                .positiveText(R.string.dong)
                .cancelable(false);
        if (username == null || username.length() < 6){
            errorDialog.content(R.string.error_tdn).show();
            return false;
        }
        if (username.contains(" ")){
            errorDialog.content(R.string.error_tdn2).show();
            return false;
        }
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

    private void SignUp(){
        try{
            KeyboardUtil.hideSoftKeyboard(this);
            final String username = SignupPanelUsername.getText().toString();
            final String password = SignupPanelPassword.getText().toString();
            String passwordCmf = SignupPanelPasswordcmf.getText().toString();
            if (!validateSignupInput(username,password,passwordCmf)) return;
            showProgressDialog();
            api.signup(getIMEI(), username, password, 0)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            hideProgressDialog();
                            try {
                                UserModel userModel = response.body();
                                if (userModel.getId() != 0){
                                    ToastUtil.show(getString(R.string.dktk));
                                    SigninPanelUsername.setText(username);
                                    SigninPanelPassword.setText(password);
                                    switchPanel();
                                }else {
                                    SigninPanelUsername.getText().clear();
                                    SignupPanelPassword.getText().clear();
                                    SignupPanelPasswordcmf.getText().clear();
                                    showError(getString(R.string.tdntt));
                                }
                            }catch (Exception e){
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            hideProgressDialog();
                            ToastUtil.showError();
                        }
                    });
        }catch (Exception e){
            LogUtil.e(e);
        }

    }

    private void Signin(){
        try {
            KeyboardUtil.hideSoftKeyboard(this);
            final String username = SigninPanelUsername.getText().toString();
            final String password = SigninPanelPassword.getText().toString();
            if (!validateSigninInput(username, password)) return;
            showProgressDialog();
            api.signin(getIMEI(), username, password)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                            try {
                                UserModel userModel = response.body();
                                LogUtil.d("id: " + userModel.getId());
                                if (userModel.getId() != 0){
                                    getUser(userModel.getId());
                                }else {
                                    hideProgressDialog();
                                    showError(getString(R.string.tdnhmkkcx));
                                }
                            }catch (Exception e){
                                hideProgressDialog();
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            hideProgressDialog();
                            ToastUtil.showError();
                        }
                    });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private void getUser(final int userid){
        api.getUserInfo(userid)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        try {
                            UserModel userModel = response.body();
                            userModel.setId(userid);
                            userModel.setName(SigninPanelUsername.getText().toString());
                            mApplication.setUser(userModel);
                            LoginActivity.this.setResult(RESULT_OK);
                            finish();
                            /*if (userModel.getId() != 0){

                            }else {
                                hideProgressDialog();
                                showError(getString(R.string.tdnhmkkcx));
                            }*/
                        }catch (Exception e){
                            hideProgressDialog();
                            LogUtil.e(e);
                            ToastUtil.showError();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        hideProgressDialog();
                        ToastUtil.showError();
                    }
                });
    }



    private void switchPanel(){
        if (isInSigninTab){
            actionBarTitle.setText(R.string.dang_ky);
            YoYo.with(Techniques.SlideOutLeft)
                    .duration(200)
                    .playOn(SigninPanel);
            YoYo.with(Techniques.SlideInRight)
                    .duration(200)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            SignupPanel.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).playOn(SignupPanel);
        }else{
            actionBarTitle.setText(R.string.dang_nhap);
            YoYo.with(Techniques.SlideOutRight)
                    .duration(200)
                    .playOn(SignupPanel);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            SigninPanel.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).playOn(SigninPanel);
        }
        isInSigninTab = !isInSigninTab;
    }
}
