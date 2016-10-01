package ats.abongda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserBet;
import ats.abongda.model.UserModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 10-Aug-16.
 */
public class UserStatisticFragment extends BaseFragment {

    @BindView(R.id.tktk_btnlefttext)
    TextView tktkBtnlefttext;
    @BindView(R.id.tktk_btnleft)
    LinearLayout tktkBtnleft;
    @BindView(R.id.tktk_btnrighttext)
    TextView tktkBtnrighttext;
    @BindView(R.id.tktk_btnright)
    LinearLayout tktkBtnright;
    @BindView(R.id.tktk_btn)
    LinearLayout tktkBtn;
    @BindView(R.id.tktk_text1left)
    TextView tktkText1left;
    @BindView(R.id.tktk_text1right)
    TextView tktkText1right;
    @BindView(R.id.tktk_text2left)
    TextView tktkText2left;
    @BindView(R.id.tktk_text2right)
    TextView tktkText2right;
    @BindView(R.id.tktk_text3left)
    TextView tktkText3left;
    @BindView(R.id.tktk_text3right)
    TextView tktkText3right;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swiper;
    private UserModel userModel;

    public static UserStatisticFragment newInstance(String title) {
        UserStatisticFragment f = new UserStatisticFragment();
        f.title = title;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_statistic, container, false);
        userModel = application.getUser();
        ButterKnife.bind(this, v);
        loadContent();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContent();
            }
        });

        return v;
    }

    @Override
    public void loadContent() {
        try {
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            api.getUserStatistic(userModel.getId())
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });


                            try {
                                userModel = response.body();
                                setLeftContent();
                            } catch (Exception e) {
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LogUtil.e(t);
                            ToastUtil.showError();
                        }
                    });
        } catch (Exception e) {
            LogUtil.e(e);
            ToastUtil.showError();
        }
    }


    @OnClick({R.id.tktk_btnleft, R.id.tktk_btnright})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tktk_btnleft:
                setLeftContent();
                break;
            case R.id.tktk_btnright:
                setRightContent();
                break;
        }
    }

    private void setLeftContent(){
        tktkBtn.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.greenbtn));
        tktkBtnlefttext.setTextColor(getResources().getColor(R.color.white_color));
        tktkBtnrighttext.setTextColor(getResources().getColor(R.color.base_app_color));
        tktkText1left.setText(R.string.so_cua_thang);
        tktkText2left.setText(R.string.so_cua_dat);
        tktkText3left.setText(R.string.hieu_suat);

        tktkText1right.setText("" + userModel.getWonMatchScore());
        tktkText2right.setText("" + userModel.getTotalMatchScore());
        if (userModel.getTotalMatchScore() > 0) {
            float percent = (userModel.getWonMatchScore() * 100.0f)
                    / userModel.getTotalMatchScore();
            tktkText3right.setText(Math.round(percent) + " %");
        } else {
            tktkText3right.setText("");
        }
    }

    private void setRightContent(){
        tktkBtn.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.greenbtnrv));
        tktkBtnlefttext.setTextColor(getResources().getColor(R.color.base_app_color));
        tktkBtnrighttext.setTextColor(getResources().getColor(R.color.white_color));
        tktkText1left.setText(R.string.so_xu_thang);
        tktkText2left.setText(R.string.so_xu_dat);
        tktkText3left.setText(R.string.hieu_suat);

        tktkText1right.setText("" + userModel.getWonCoinScore());
        tktkText2right.setText("" + userModel.getTotalCoinScore());
        if (userModel.getTotalMatchScore() > 0) {
            float percent = (userModel.getWonCoinScore() * 100.0f)
                    / userModel.getTotalCoinScore();
            tktkText3right.setText(Math.round(percent) + " %");
        } else {
            tktkText3right.setText("");
        }
    }
}
