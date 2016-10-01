package ats.abongda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import ats.abongda.R;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.UserAccountLog;
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
public class UserHistoryFragment extends BaseFragment {


    @BindView(R.id.history_spinner_text)
    TextView historySpinnerText;
    @BindView(R.id.taikhoanlichsuPage_spinner)
    RelativeLayout taikhoanlichsuPageSpinner;
    @BindView(R.id.tklichsu_stt)
    TextView tklichsuStt;
    @BindView(R.id.tklichsu_date)
    TextView tklichsuDate;
    @BindView(R.id.taikhoanlichsuPage_item_stt)
    TextView taikhoanlichsuPageItemStt;
    @BindView(R.id.taikhoanlichsuPage_item_name)
    TextView taikhoanlichsuPageItemName;
    @BindView(R.id.taikhoanlichsuPage_item_bt)
    TextView taikhoanlichsuPageItemBt;
    @BindView(R.id.content_container)
    LinearLayout contentContainer;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swiper;
    private static final int[] ITEMPERPAGE = {10, 20, 50, 100};
    @BindView(R.id.contentScrollView)
    ScrollView contentScrollView;
    private int currentItemPerpage;
    private UserModel userModel;

    public static UserHistoryFragment newInstance(String title) {
        UserHistoryFragment f = new UserHistoryFragment();
        f.title = title;
        f.currentItemPerpage = ITEMPERPAGE[0];
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_history, container, false);
        userModel = application.getUser();
        ButterKnife.bind(this, v);
        historySpinnerText.setText(getContext().getResources()
                .getStringArray(R.array.user_purchase_history)[0]);
        loadContent();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContent();
            }
        });
        contentScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = contentScrollView.getScrollY();
                if (scrollY == 0) swiper.setEnabled(true);
                else swiper.setEnabled(false);

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
            api.getUserAccountLog(userModel.getName(), currentItemPerpage)
                    .enqueue(new Callback<List<UserAccountLog>>() {
                        @Override
                        public void onResponse(Call<List<UserAccountLog>> call, Response<List<UserAccountLog>> response) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            LayoutInflater inflater = LayoutInflater.from(getContext());
                            contentContainer.removeAllViews();
                            try {
                                for (int i = 0; i < response.body().size(); i++) {
                                    UserAccountLog model = response.body().get(i);
                                    LinearLayout item = (LinearLayout) inflater.inflate(
                                            R.layout.item_user_account_log, null);
                                    TextView stt = (TextView) item.findViewById(R.id.tklichsu_stt);
                                    stt.setText("" + (i + 1));
                                    TextView date = (TextView) item
                                            .findViewById(R.id.tklichsu_date);
                                    date.setText(model.getRegisterDatetime());
                                    TextView thaydoi = (TextView) item
                                            .findViewById(R.id.tklichsu_thaydoi);
                                    if (model.getLogType() < 0) {
                                        thaydoi.setText("-" + model.getChange() + "");
                                        thaydoi.setTextColor(getResources().getColor(R.color.myredcolor));
                                    } else {
                                        thaydoi.setText(model.getChange() + "");
                                        thaydoi.setTextColor(getResources().getColor(R.color.mygreencolor));
                                    }

                                    TextView sauthaydoi = (TextView) item
                                            .findViewById(R.id.tklichsu_sauthaydoi);
                                    sauthaydoi.setTextColor(getResources().getColor(R.color.base_app_color));
                                    sauthaydoi.setText(model.getAfterChange() + "");
                                    TextView noidung = (TextView) item
                                            .findViewById(R.id.tklichsu_noidung);
                                    noidung.setText(model.getDescription());
                                    contentContainer.addView(item);
                                }
                            } catch (Exception e) {
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserAccountLog>> call, Throwable t) {
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

    @OnClick(R.id.taikhoanlichsuPage_spinner)
    public void onClick() {
        try {
            new MaterialDialog.Builder(getContext())
                    .items(R.array.user_purchase_history)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            historySpinnerText.setText(text.toString());
                            currentItemPerpage = ITEMPERPAGE[which];
                            loadContent();
                        }
                    })
                    .show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
