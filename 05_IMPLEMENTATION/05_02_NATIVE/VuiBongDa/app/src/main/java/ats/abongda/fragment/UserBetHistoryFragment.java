package ats.abongda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.StringHelper;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.Match;
import ats.abongda.model.UserAccountLog;
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
public class UserBetHistoryFragment extends BaseFragment {


    private static final int[] OFFSET = {0, -1, -2,-6,-29};
    @BindView(R.id.history_spinner_text)
    TextView historySpinnerText;
    @BindView(R.id.taikhoanlichsuPage_spinner)
    RelativeLayout taikhoanlichsuPageSpinner;
    @BindView(R.id.content_container)
    LinearLayout contentContainer;
    @BindView(R.id.contentScrollView)
    ScrollView contentScrollView;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swiper;
    private int currentOffset;
    private UserModel userModel;

    public static UserBetHistoryFragment newInstance(String title) {
        UserBetHistoryFragment f = new UserBetHistoryFragment();
        f.title = title;
        f.currentOffset = OFFSET[0];
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_bet_history, container, false);
        userModel = application.getUser();
        ButterKnife.bind(this, v);
        historySpinnerText.setText(getContext().getResources()
                .getStringArray(R.array.user_bet_history)[0]);
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
            api.getUserBetHistory(userModel.getId(), currentOffset)
                    .enqueue(new Callback<List<UserBet>>() {
                        @Override
                        public void onResponse(Call<List<UserBet>> call, Response<List<UserBet>> response) {
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
                                    RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.item_user_bet_history, null);
                                    final UserBet model = response.body().get(i);
                                    ImageView logo = (ImageView) item.findViewById(R.id.tkdc_child_logo);
                                    logo.setImageResource(StringHelper.getTrueLeagueImageResource(model.league_row_id));
                                    final Match match = new Match();
                                    match.set_matchDatetime(model.register_datetime);

                                    //((TextView) item.findViewById(R.id.topcaothuPage_stt)).setText(""+(i+1));
                                    ((TextView) item.findViewById(R.id.tkdc_child_host)).setText(model.home_club);
                                    ((TextView) item.findViewById(R.id.tkdc_child_host_goal)).setText(model.home_club_goal + "");
                                    ((TextView) item.findViewById(R.id.tkdc_child_guest)).setText(model.guest_club);
                                    ((TextView) item.findViewById(R.id.tkdc_child_guest_goal)).setText(model.guest_club_goal + "");
                                    ((TextView) item.findViewById(R.id.tkdc_child_matchTime)).setText(match.getMatchShortStartDate()+"\n"+match.getMatchStartTimeWithoutPlus());
                                    switch (model.bet_result) {
                                        case -1:
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setText(
                                                    "Kết quả: thua " + (model.bet_result_value*-1) + " xu"
                                            );
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setTextColor(getContext().getResources().getColor(R.color.redcolor));
                                            break;
                                        case 1:
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setText(
                                                    "Kết quả: thắng " + model.bet_result_value + " xu"
                                            );
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setTextColor(getContext().getResources().getColor(R.color.mygreencolor));
                                            break;
                                        case -2:
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setText(
                                                    "Số xu đặt: " + model.user_bet_value + " xu"
                                            );
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setTextColor(getContext().getResources().getColor(R.color.base_app_color));
                                            break;
                                        default:
                                            ((TextView) item.findViewById(R.id.tkdc_child_tvChanel)).setVisibility(View.INVISIBLE);
                                            break;
                                    }
                                    item.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            /*MAINVIEW_MainFragment  mf=  new MAINVIEW_MainFragment(Constant.MATCHDETAILFRAGMENT,new ArrayList<Object>(){{
                                                add(model.matchId);
                                            }});
                                            ((MainActivity)getContext()).switchContent(mf);*/

                                            Intent matchIntent = new Intent(getContext(), MatchDetailActivity.class);
                                            matchIntent.putExtra("matchId",model.getMatchId());
                                            startActivity(matchIntent);

                                        }
                                    });
                                    contentContainer.addView(item);

                                }
                            }catch (Exception e){
                                LogUtil.e(e);
                                ToastUtil.showError();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserBet>> call, Throwable t) {
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
        }catch (Exception e){
            LogUtil.e(e);
            ToastUtil.showError();
        }
    }

    @OnClick(R.id.taikhoanlichsuPage_spinner)
    public void onClick() {
        try {
            new MaterialDialog.Builder(getContext())
                    .items(R.array.user_bet_history)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            historySpinnerText.setText(text.toString());
                            currentOffset = OFFSET[which];
                            loadContent();
                        }
                    })
                    .show();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }
}
