package ats.abongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.adapter.UserInboxAdapter;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.helper.AnimationUtil;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserInbox;
import ats.abongda.model.UserModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 14-Aug-16.
 */
public class UserInboxActivity extends BaseActivity {

    @BindView(R.id.textContainer)
    ScrollView textContainer;
    @BindView(R.id.text)
    TextView inboxContent;
    @BindView(R.id.action_bar_leftbtn)
    ImageView actionBarLeftbtn;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.action_bar_rightbtn)
    ImageView actionBarRightbtn;
    @BindView(R.id.recycer)
    RecyclerView recycer;
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    UserInboxAdapter adapter;
    UserModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inbox);
        ButterKnife.bind(this);
        model = mApplication.getUser();
        if (model == null){
            ToastUtil.showError();
            //finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarTitle.setText(R.string.hop_thu);
        recycer.setLayoutManager(new LinearLayoutManager(this));
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInbox();
            }
        });
        getInbox();
    }

    @OnClick({R.id.action_bar_leftbtn, R.id.notfound_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bar_leftbtn:
                onBackPressed();
                break;
            case R.id.notfound_layout:

                break;
        }
    }

    boolean isInList;
    @Override
    public void onBackPressed() {
        if (!isInList){
            AnimationUtil.switchView(recycer,textContainer, true);
            actionBarTitle.setText(R.string.hop_thu);
            getInbox();
        }else{
            super.onBackPressed();
        }
    }

    private void getInbox(){
        try {
            isInList = true;
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            notfoundLayout.setVisibility(View.GONE);
            recycer.setVisibility(View.VISIBLE);
            api.getUserMessage(model.getName(), 0)
                    .enqueue(new Callback<List<UserInbox>>() {
                        @Override
                        public void onResponse(Call<List<UserInbox>> call, Response<List<UserInbox>> response) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            try {
                                List<UserInbox> userInboxes = response.body();
                                adapter = new UserInboxAdapter(userInboxes, UserInboxActivity.this, new UserInboxAdapter.ItemClickedImp() {
                                    @Override
                                    public void onItemClicked(UserInbox inbox) {
                                        openMessage(inbox);
                                    }
                                });
                                recycer.setAdapter(adapter);

                            }catch (Exception e){
                                swiper.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swiper.setRefreshing(false);
                                    }
                                });
                                LogUtil.e(e);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserInbox>> call, Throwable t) {
                            LogUtil.e(t);
                            ToastUtil.showNetWorkError(t);
                        }
                    });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    private void openMessage(UserInbox inbox){
        isInList =false;
        inboxContent.setText(inbox.getMessage());
        actionBarTitle.setText(inbox.getTitle());
        AnimationUtil.switchView(textContainer, recycer, false);
        api.updateUserMessageReadStatus(inbox.getId()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
