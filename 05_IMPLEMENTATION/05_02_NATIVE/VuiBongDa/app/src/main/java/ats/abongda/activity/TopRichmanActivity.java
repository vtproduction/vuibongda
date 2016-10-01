package ats.abongda.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
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
 * Created by NienLe on 27-Aug-16.
 */
public class TopRichmanActivity extends BaseActivity {

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
    @BindView(R.id.topdaigiaPage_name)
    TextView topdaigiaPageName;
    @BindView(R.id.topdaigiaPage_sotien)
    TextView topdaigiaPageSotien;
    @BindView(R.id.topdaigiaPage_stt)
    TextView topdaigiaPageStt;
    @BindView(R.id.topdaigiaPage_main_layout)
    LinearLayout topdaigiaPageMainLayout;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_richman);
        ButterKnife.bind(this);
        actionBarLeftbtn.setImageResource(R.drawable.ic_action_arrow_left);
        actionBarRightbtn.setVisibility(View.INVISIBLE);
        actionBarTitle.setText(R.string.top_dai_gia);
        userModel = MainApplication.get().getUser();
        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollview.getScrollY();
                if(scrollY == 0) swiper.setEnabled(true);
                else swiper.setEnabled(false);

            }
        });
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        loadData();
    }


    private void loadData(){
        try {
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            api.getRankingTable(userModel == null ? "" : userModel.getName())
                    .enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            try {
                                setContent(response.body());
                            }catch (Exception e){
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }finally {
                                swiper.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swiper.setRefreshing(false);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {
                            LogUtil.e(t);
                            ToastUtil.showNetWorkError(t);
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                        }

                    });
        }catch (Exception e){
            LogUtil.e(e);
            ToastUtil.showError();
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(false);
                }
            });
        }
    }

    private void setContent(List<UserModel> models){
        topdaigiaPageMainLayout.removeAllViews();
        int numOfResult = models.size();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < models.size(); i++) {
            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_1, null);
            UserModel model = models.get(i);
            ImageView logo = (ImageView) item.findViewById(R.id.topdaigiaPage_img);

            switch (i) {
                case 0:
                    logo.setImageResource(R.drawable.sortnum1);
                    break;
                case 1:
                case 2:
                    logo.setImageResource(R.drawable.sortnum2);
                    break;
                default:
                    logo.setImageResource(R.drawable.sortnum4);
                    break;
            }
            ((TextView) item.findViewById(R.id.topdaigiaPage_stt)).setText(""+(i+1));
            ((TextView) item.findViewById(R.id.topdaigiaPage_name)).setText(model.getName());
            ((TextView) item.findViewById(R.id.topdaigiaPage_sotien)).setText(getRoundOffValue(model.getAccount()));
            if (userModel == null) {
                if (i < models.size() - 1) {
                    topdaigiaPageMainLayout.addView(item);
                }
            }else{
                if (i < models.size()-1) {
                    topdaigiaPageMainLayout.addView(item);
                }else{
                    LinearLayout gap = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_2, null);
                    topdaigiaPageMainLayout.addView(gap);
                    LinearLayout lastItem = (LinearLayout) inflater.inflate(R.layout.topdaigia_item_3, null);
                    ((TextView) lastItem.findViewById(R.id.topdaigiaPage_stt_2)).setText(""+model.getRank());
                    ((TextView) lastItem.findViewById(R.id.topdaigiaPage_name_2)).setText(model.getName());
                    ((TextView) lastItem.findViewById(R.id.topdaigiaPage_xu_2)).setText(""+userModel.getAccount());
                    topdaigiaPageMainLayout.addView(lastItem);
                }
            }

            if (i == models.size() - 1 || models.size() == 0) {
                //pDialog.dismiss();

            }
        }
    }

    public static String getRoundOffValue(double value){

        DecimalFormat df = new DecimalFormat("##,###,###,###,###");
        return df.format(value);
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
}
