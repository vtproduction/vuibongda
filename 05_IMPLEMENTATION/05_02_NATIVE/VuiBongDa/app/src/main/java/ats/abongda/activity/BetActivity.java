package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcels;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.config.APILink;
import ats.abongda.config.Request;
import ats.abongda.customClass.InputFilterMinMax;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.KeyboardUtil;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserModel;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 26-Aug-16.
 */
public class BetActivity extends BaseActivity {


    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.datcuocpage2_hostimg)
    ImageView datcuocpage2Hostimg;
    @BindView(R.id.datcuocpage2_hostname)
    TextView datcuocpage2Hostname;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.datcuocpage2_guestimg)
    ImageView datcuocpage2Guestimg;
    @BindView(R.id.datcuocpage2_guestname)
    TextView datcuocpage2Guestname;
    @BindView(R.id.datcuocpage2_spinner1text)
    TextView datcuocpage2Spinner1text;
    @BindView(R.id.datcuocpage2_spinner1)
    LinearLayout datcuocpage2Spinner1;
    @BindView(R.id.datcuocpage2_spinner2text)
    TextView datcuocpage2Spinner2text;
    @BindView(R.id.datcuocpage2_spinner2)
    LinearLayout datcuocpage2Spinner2;
    @BindView(R.id.TextView07)
    TextView TextView07;
    @BindView(R.id.datcuocpage2_tiledat)
    TextView datcuocpage2Tiledat;
    @BindView(R.id.TextView05)
    TextView TextView05;
    @BindView(R.id.datcuocpage2_taikhoan)
    TextView datcuocpage2Taikhoan;
    @BindView(R.id.TextView03)
    TextView TextView03;
    @BindView(R.id.datcuocpage2_soxu)
    TextView datcuocpage2Soxu;
    @BindView(R.id.TextView11)
    TextView TextView11;
    @BindView(R.id.datcuocpage2_soxudat)
    EditText datcuocpage2Soxudat;
    @BindView(R.id.datcuocpage2_datcuocbtn)
    Button datcuocpage2Datcuocbtn;
    @BindView(R.id.datcuocpage2_dattattaybtn)
    Button datcuocpage2Dattattaybtn;
    private MatchDetail matchDetail;
    private UserModel userModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        ButterKnife.bind(this);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarTitle.setText(getString(R.string.dat_cuoc));
        this.matchDetail = Parcels.unwrap(getIntent().getParcelableExtra("match"));
        this.betType = getIntent().getIntExtra("betType",-10);
        if (matchDetail == null || betType == -10){
            ToastUtil.showError();
            finish();
            return;
        }
        userModel = MainApplication.get().getUser();
        if (userModel == null){
            ToastUtil.show("Bạn cần đăng nhập để thực hiện thao tác này!");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, Request.LOGIN);
            return;
        }
        getUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Request.LOGIN:
                if (resultCode == RESULT_OK){
                    userModel = MainApplication.get().getUser();
                    getUser();
                }else {
                    ToastUtil.show("Bạn cần đăng nhập để thực hiện thao tác này!");
                    finish();
                }
        }
    }

    private void getUser(){
        showProgressDialog();
        api.getUserInfo(userModel.getId())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        try {
                            userModel.setAccount(response.body().getAccount());
                            MainApplication.get().setUser(userModel);
                            setData();
                        }catch (Exception e){
                            LogUtil.e(e);
                            ToastUtil.showNetWorkError(e);
                        }finally {
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        hideProgressDialog();
                        LogUtil.e(t);
                        ToastUtil.showNetWorkError(t);
                    }
                });
    }
    private void setData(){
        try {
            ImageUlti.loadImage(this, APILink.MEDIALINK + matchDetail.get_homeLogo(), datcuocpage2Hostimg);
            ImageUlti.loadImage(this, APILink.MEDIALINK + matchDetail.get_guestLogo(), datcuocpage2Guestimg);
            datcuocpage2Hostname.setText(matchDetail.get_home());
            datcuocpage2Guestname.setText(matchDetail.get_guest());
            datcuocpage2Tiledat.setText("" + MainApplication.get().getConfiguration().get_goalBet());
            datcuocpage2Taikhoan.setText(userModel.getName());
            datcuocpage2Soxu.setText(userModel.getAccount() + getString(R.string.xu));
            datcuocpage2Soxudat.setText("0");
            datcuocpage2Soxudat.setFilters(new InputFilter[]{new InputFilterMinMax(0, (int)userModel.getAccount())});
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private int homeGoal = 0, guestGoal = 0, betType = 0;
    private long  coin = 0;
    private void bet(){
        if (coin < MainApplication.get().getConfiguration().get_minimumGoalBetValue()){
            showError(getString(R.string.bpdtt) + MainApplication.get().getConfiguration().get_minimumGoalBetValue() + " " + getString(R.string.xu) + "!");
            return;
        }
        if (coin > userModel.getAccount()){
            ToastUtil.showError();
            return;
        }
        showProgressDialog();
        api.perfomRatioBet(matchDetail.get_matchID(),
                "undefined",
                userModel.getId(),
                betType,
                datcuocpage2Soxudat.getText().toString(),
                datcuocpage2Tiledat.getText().toString(),
                matchDetail.get_handicap(),
                homeGoal,
                guestGoal,0).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {
                    hideProgressDialog();
                    if (response.body() == 1){
                        showCustomDialog(
                                "Đặt cược thành công!",
                                "Bạn đã đặt cược " + coin + "xu với tỉ số " + matchDetail.get_home()
                                        + " " + homeGoal + " - "
                                        + guestGoal + " " + matchDetail.get_guest(),
                                "Đóng",
                                new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                }
                        );
                        getUser();
                    }else {
                        ToastUtil.showError();
                    }
                }catch (Exception e){
                    LogUtil.e(e);
                    ToastUtil.showError();
                }finally {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                LogUtil.e(t);
                ToastUtil.showNetWorkError(t);
                hideProgressDialog();
            }
        });

    }


    @OnClick({R.id.action_bar_leftbtn, R.id.datcuocpage2_spinner1, R.id.datcuocpage2_spinner2, R.id.datcuocpage2_datcuocbtn, R.id.datcuocpage2_dattattaybtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.datcuocpage2_spinner1:
                new MaterialDialog.Builder(this)
                        .items(getResources().getStringArray(R.array.score))
                        .cancelable(false)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                homeGoal = which;
                                datcuocpage2Spinner1text.setText("" + which);
                            }
                        }).show();
                break;
            case R.id.datcuocpage2_spinner2:
                new MaterialDialog.Builder(this)
                        .items(getResources().getStringArray(R.array.score))
                        .cancelable(false)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                guestGoal = which;
                                datcuocpage2Spinner2text.setText("" + which);
                            }
                        }).show();
                break;
            case R.id.datcuocpage2_datcuocbtn:
                coin = Integer.parseInt(datcuocpage2Soxudat.getText().toString());
                showCustomDialog("Đặt cược",
                        "Bạn có chắc chắn đặt " + coin + " xu cho tỉ số " + matchDetail.get_home() + " "
                                + homeGoal + " - " + guestGoal + " " + matchDetail.get_guest() + " chứ?",
                        "Đặt cược","Hủy",
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                bet();
                            }
                        },
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });
                break;
            case R.id.datcuocpage2_dattattaybtn:
                showCustomDialog("Đặt tất tay",
                        "Bạn có chắc chắn đặt toàn bộ xu của mình cho tỉ số " + matchDetail.get_home() + " "
                                + homeGoal + " - " + guestGoal + " " + matchDetail.get_guest() + " chứ?",
                        "Đặt tất tay!","Hủy",
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                coin = userModel.getAccount();
                                bet();
                            }
                        },
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });
                break;
        }
    }
}
