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
public class ChangeUserInformationActivity extends BaseActivity {


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.tdpanel1_stadium)
    TextView tdpanel1Stadium;
    @BindView(R.id.doitt_ten)
    EditText doittTen;
    @BindView(R.id.tdpanel1_isFulltime)
    TextView tdpanel1IsFulltime;
    @BindView(R.id.doitt_diachi)
    EditText doittDiachi;
    @BindView(R.id.tdpanel1_guest_score)
    TextView tdpanel1GuestScore;
    @BindView(R.id.doitt_sdt)
    EditText doittSdt;
    @BindView(R.id.tddb_time)
    TextView tddbTime;
    @BindView(R.id.doitt_email)
    EditText doittEmail;
    @BindView(R.id.eee)
    TextView eee;
    @BindView(R.id.doitt_btn)
    LinearLayout doittBtn;
    @BindView(R.id.doitt_mainpanel)
    LinearLayout doittMainpanel;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication.setBaseActivity(this);
        setContentView(R.layout.activity_change_infor);
        ButterKnife.bind(this);
        actionBarTitle.setText(R.string.cap_nhat_thong_tin_ca_nhan);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.GONE);
        this.userModel = mApplication.getUser();
        if (userModel == null || userModel.getId() < 0) {
            ToastUtil.showError();
            finish();
        }
        setInitialContent();
    }

    private void setInitialContent(){
        try{
            doittTen.setText(userModel.getFullname());
            doittDiachi.setText(userModel.getAddress());
            doittSdt.setText(userModel.getPhone());
            doittEmail.setText(userModel.getEmail());
        }catch (Exception e){
            LogUtil.e(e);
        }
    }


    private boolean validateInput(String name, String address, String phone, String email) {
        MaterialDialog.Builder errorDialog = new MaterialDialog.Builder(this);
        errorDialog.title(R.string.loi)
                .positiveText(R.string.dong)
                .cancelable(false);
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorDialog.content(R.string.emkhl).show();
            return false;
        }

        return true;
    }

    private void changeUserInformation(final String name, final String address, final String phone, final String email) {
        if (!validateInput(name, address, phone, email)) return;
        showProgressDialog();
        APIService.build().changeUserInfo(userModel.getId(), name, phone, address, email)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        hideProgressDialog();
                        try {
                            int responseCode = response.body();
                            if (responseCode == userModel.getId()) {
                                ToastUtil.show(getString(R.string.tdtttk));
                                userModel.setFullname(name);
                                userModel.setPhone(phone);
                                userModel.setEmail(email);
                                userModel.setAddress(address);
                                mApplication.setUser(userModel);
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                ToastUtil.showError();
                            }
                        } catch (Exception e) {
                            LogUtil.e(e);
                            ToastUtil.showNetWorkError(e);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        LogUtil.e(t);
                        ToastUtil.showNetWorkError(t);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick({R.id.action_bar_leftbtn, R.id.doitt_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.doitt_btn:
                String name = doittTen.getText().toString();
                String email = doittEmail.getText().toString();
                String address = doittDiachi.getText().toString();
                String phone = doittSdt.getText().toString();
                changeUserInformation(name, address, phone, email);
                break;
        }
    }
}
