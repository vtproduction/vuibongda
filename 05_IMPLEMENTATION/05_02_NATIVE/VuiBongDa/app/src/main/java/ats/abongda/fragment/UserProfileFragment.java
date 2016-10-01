package ats.abongda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ats.abongda.R;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.DeviceUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 10-Aug-16.
 */
public class UserProfileFragment extends BaseFragment {
    public int currentUserId;
    public String currentUserEmail;
    public View mainView;
    private UserModel userModel;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swiper;
    @BindView(R.id.TKHS_coin_refill_btn)
    ImageView coinRefillBtn;

    public static UserProfileFragment newInstance(int currentUserId, String title) {
        UserProfileFragment f = new UserProfileFragment();
        f.currentUserId = currentUserId;
        f.title = title;
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mainView = inflater.inflate(R.layout.fragment_user_profile, container,
                false);
        ButterKnife.bind(this, mainView);
        loadContent();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContent();
            }
        });
        coinRefillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        return mainView;

    }


    @Override
    public void loadContent() {
        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        api.getUserInfo(currentUserId).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                swiper.post(new Runnable() {
                    @Override
                    public void run() {
                        swiper.setRefreshing(false);
                    }
                });
                try {
                    if (response.code() == 200){
                        UserModel model = response.body();
                        userModel = model;
                        UserModel savedUserModel = application.getUser();
                        if (savedUserModel != null){
                            userModel.setId(currentUserId);
                            userModel.setName(savedUserModel.getName());
                        }
                        ((TextView) mainView.findViewById(R.id.TKHS_name))
                                .setText(model.getName());
                        ((TextView) mainView.findViewById(R.id.TKHS_fullname))
                                .setText((model.getFullname() == null) ? "" : model
                                        .getFullname());
                        ((TextView) mainView.findViewById(R.id.TKHS_coin)).setText(model
                                .getAccount() + " xu");
                        ((TextView) mainView.findViewById(R.id.TKHS_address))
                                .setText((model.getAddress() == null) ? "" : model
                                        .getAddress());
                        ((TextView) mainView.findViewById(R.id.TKHS_phone)).setText((model
                                .getPhone() == null) ? DeviceUlti
                                .getPhoneNumber(getContext()) : model.getPhone());
                        ((TextView) mainView.findViewById(R.id.TKHS_email)).setText((model
                                .getEmail() == null) ? "" : model.getEmail());
                        currentUserEmail = (model.getEmail() == null) ? "" : model
                                .getEmail();
                        if (model.getIsPhoneVerified() == 0) {
                            ((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
                                    .setImageResource(R.drawable.iconxacthucsms);
                        } else {
                            ((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
                                    .setImageResource(R.drawable.badge_tick);
                            ((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
                                    .setTag("ok");
                            ((ImageView) mainView.findViewById(R.id.TKHS_phone_cf))
                                    .getLayoutParams().width = ((ImageView) mainView
                                    .findViewById(R.id.TKHS_phone_cf)).getLayoutParams().height;

                        }

                        if (model.getIsEmailVerified() == 0) {
                            ((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
                                    .setImageResource(R.drawable.iconxacthucemail);
                        } else {
                            ((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
                                    .setImageResource(R.drawable.badge_tick);
                            ((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
                                    .setTag("ok");
                            ((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
                                    .getLayoutParams().width = ((ImageView) mainView
                                    .findViewById(R.id.TKHS_email_cf)).getLayoutParams().height;

                        }
                        ((ImageView) mainView.findViewById(R.id.TKHS_email_cf))
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        if ((v.getTag()==null)||(!v.getTag().equals("ok"))) {
                                            showPhoneAlertDialog();
                                        }

                                    }
                                });


                        ((ImageView) mainView.findViewById(R.id.TKHS_phone_cf)).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                if ((v.getTag()==null)||(!v.getTag().equals("ok"))) {
                                    openMessageApp();
                                }

                            }
                        });
                    }else{

                    }
                }catch (Exception e){
                    LogUtil.e(e);
                    ToastUtil.showError();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                LogUtil.e(t);
                ToastUtil.showError();
                swiper.post(new Runnable() {
                    @Override
                    public void run() {
                        swiper.setRefreshing(false);
                    }
                });
            }
        });
    }
    private void openMessageApp() {
        try{
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "7042");
            smsIntent.putExtra(
                    "sms_body",
                    "abd phone "
                            + userModel.getName());
            startActivity(smsIntent);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private void showPhoneAlertDialog(){
        try{
            new MaterialDialog.Builder(getContext())
                    .title("Tài khoản xác thực email sẽ được nhận 200 xu !")
                    .content("Xác nhận email")
                    .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    .input("Nhập email của bạn", userModel.getEmail(), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            // Do something
                            LogUtil.d(input.toString());
                        }
                    })
                    .show();
        }catch (Exception e){
            ToastUtil.showError();
            LogUtil.e(e);
        }
    }
}
