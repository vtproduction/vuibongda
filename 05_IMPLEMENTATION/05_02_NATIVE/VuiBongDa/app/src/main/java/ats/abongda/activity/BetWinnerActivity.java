package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
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
public class BetWinnerActivity extends BaseActivity {


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
    @BindView(R.id.datcuocpage_hostimg)
    ImageView datcuocpageHostimg;
    @BindView(R.id.datcuocpage_hostname)
    TextView datcuocpageHostname;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.datcuocpage_guestimg)
    ImageView datcuocpageGuestimg;
    @BindView(R.id.datcuocpage_guestname)
    TextView datcuocpageGuestname;
    @BindView(R.id.TextView13)
    TextView TextView13;
    @BindView(R.id.datcuocpage_cuadat)
    TextView datcuocpageCuadat;
    @BindView(R.id.TextView09)
    TextView TextView09;
    @BindView(R.id.datcuocpage_loaitile)
    TextView datcuocpageLoaitile;
    @BindView(R.id.TextView07)
    TextView TextView07;
    @BindView(R.id.datcuocpage_tiledat)
    TextView datcuocpageTiledat;
    @BindView(R.id.TextView05)
    TextView TextView05;
    @BindView(R.id.datcuocpage_taikhoan)
    TextView datcuocpageTaikhoan;
    @BindView(R.id.TextView03)
    TextView TextView03;
    @BindView(R.id.datcuocpage_soxu)
    TextView datcuocpageSoxu;
    @BindView(R.id.TextView11)
    TextView TextView11;
    @BindView(R.id.datcuocpage_soxudat)
    EditText datcuocpageSoxudat;
    @BindView(R.id.datcuocpage_datcuocbtn)
    Button datcuocpageDatcuocbtn;
    @BindView(R.id.datcuocpage_dattattaybtn)
    Button datcuocpageDattattaybtn;
    private MatchDetail matchDetail;
    private UserModel userModel;
    private int betType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winerbet);
        ButterKnife.bind(this);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarTitle.setText(getString(R.string.dat_cuoc));
        this.matchDetail = Parcels.unwrap(getIntent().getParcelableExtra("match"));
        this.betType = getIntent().getIntExtra("betType", -10);
        if (matchDetail == null || betType == -10) {
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
            ImageUlti.loadImage(this, APILink.MEDIALINK + matchDetail.get_homeLogo(), datcuocpageHostimg);
            ImageUlti.loadImage(this, APILink.MEDIALINK + matchDetail.get_guestLogo(), datcuocpageGuestimg);
            datcuocpageHostname.setText(matchDetail.get_home());
            datcuocpageGuestname.setText(matchDetail.get_guest());
            datcuocpageTaikhoan.setText(userModel.getName());
            datcuocpageSoxu.setText(userModel.getAccount() + getString(R.string.xu));
            switch (betType) {
                case 1:
                    datcuocpageCuadat.setText(matchDetail.get_home() + " thắng");
                    datcuocpageLoaitile.setText("Châu Âu");
                    datcuocpageTiledat.setText(matchDetail
                            .get_homeWin1x2());
                    break;
                case 2:
                    datcuocpageCuadat.setText("Hai đội hòa");
                    datcuocpageLoaitile.setText("Châu Âu");
                    datcuocpageTiledat.setText(matchDetail
                            .get_draw1x2());
                    break;
                case 3:
                    datcuocpageCuadat.setText(matchDetail.get_guest() + " thắng");
                    datcuocpageLoaitile.setText("Châu Âu");
                    datcuocpageTiledat.setText(matchDetail
                            .get_guestWin1x2());
                    break;
                case 4:
                    datcuocpageCuadat.setText(matchDetail.get_home() + " thắng");
                    datcuocpageLoaitile.setText("Châu Á (" + matchDetail.get_handicap() + ")");
                    datcuocpageTiledat.setText(matchDetail
                            .get_homeWinHandicap());
                    break;
                case 6:
                    datcuocpageCuadat.setText(matchDetail.get_home() + " thắng");
                    datcuocpageLoaitile.setText("Châu Á (" + matchDetail.get_handicap() + ")");
                    datcuocpageTiledat.setText(matchDetail
                            .get_guestWinHandicap());
                    break;

                default:
                    break;
            }
            datcuocpageSoxudat.setText("0");
            datcuocpageSoxudat.setFilters(new InputFilter[]{new InputFilterMinMax(0, (int)userModel.getAccount())});
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private long  coin = 0;
    private void bet(){
        if (coin < MainApplication.get().getConfiguration().get_minimumNormalBetValue()){
            showError(getString(R.string.bpdtt) + MainApplication.get().getConfiguration().get_minimumNormalBetValue() + " " + getString(R.string.xu) + "!");
            return;
        }
        if (coin > userModel.getAccount()){
            ToastUtil.showError();
            return;
        }
        showProgressDialog();
        api.perfomWinnerBet(matchDetail.get_matchID(),
                "undefined",
                userModel.getId(),
                betType,
                coin+"",
                datcuocpageTiledat.getText().toString(),
                matchDetail.get_handicap()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                hideProgressDialog();
                try {
                    if (response.body() == 1){
                        showCustomDialog(
                                "Đặt cược thành công!",
                                "Bạn đã đặt cược " + coin + "xu với kết quả: " + getCuaDat(),
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


    @OnClick({R.id.action_bar_leftbtn, R.id.datcuocpage_datcuocbtn, R.id.datcuocpage_dattattaybtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.datcuocpage_datcuocbtn:
                coin = Integer.parseInt(datcuocpageSoxudat.getText().toString());
                showCustomDialog("Đặt cược",
                        "Bạn có chắc chắn đặt " + coin + " cho kết quả: " + getCuaDat() + " chứ?",
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
            case R.id.datcuocpage_dattattaybtn:
                showCustomDialog("Đặt tất tay",
                        "Bạn có chắc chắn đặt toàn bộ xu của mình cho tỉ số " + getCuaDat() + " chứ?",
                        "Đặt tất tay!", "Hủy",
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

    public String getCuaDat() {
        switch (betType) {
            case 1:
                return matchDetail.get_home() + " thắng";
            case 2:
                return "Hai đội hòa";
            case 3:
                return matchDetail.get_guest() + " thắng";

            case 4:
                return matchDetail.get_home() + " thắng";

            case 6:
                return matchDetail.get_guest() + " thắng";

            default:
                return "";
        }
    }
}
