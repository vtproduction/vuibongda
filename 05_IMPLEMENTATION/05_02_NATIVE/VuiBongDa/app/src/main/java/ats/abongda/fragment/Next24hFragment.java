package ats.abongda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.adapter.LiveScoreAdapter;
import ats.abongda.adapter.Next24hAdapter;
import ats.abongda.customClass.AnimatedExpandableListView;
import ats.abongda.customClass.view.NonInterceptedViewPagerSwipeRefreshLayout;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.model.LiveModel;
import ats.abongda.model.Match;
import ats.abongda.model.Next24hModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NienLe on 07-Aug-16.
 */
public class Next24hFragment extends BaseFragment{
    @BindView(R.id.notfound_text)
    TextView notfoundText;
    @BindView(R.id.notfound_layout)
    LinearLayout notfoundLayout;
    @BindView(R.id.swiper)
    NonInterceptedViewPagerSwipeRefreshLayout swiper;
    @BindView(R.id.recycer)
    AnimatedExpandableListView listview;

    Next24hAdapter adapter;
    public static Next24hFragment newInstance(String title){
        Next24hFragment f = new Next24hFragment();
        f.title = title;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_livescore, container, false);
        ButterKnife.bind(this, v);
        notfoundText.setText(R.string.khong_co_tran_dau_trong_2_ngay_toi);
        listview.setFadingEdgeLength(0);
        listview.setGroupIndicator(null);
        getData();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int topRowVerticalPosition =
                        (listview == null || listview.getChildCount() == 0) ?
                                0 : listview.getChildAt(0).getTop();
                swiper.setEnabled(i == 0 && topRowVerticalPosition >= 0);
            }
        });
        return v;
    }

    @OnClick({R.id.notfound_text, R.id.notfound_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notfound_text:
                getData();
                break;
        }
    }

    private void getData() {
        try {
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(true);
                }
            });
            listview.setVisibility(View.VISIBLE);
            notfoundLayout.setVisibility(View.GONE);
            api.getNext24hMatch(baseActivity.getIMEI(), application.getUserId())
                    .enqueue(new Callback<List<Match>>() {
                        @Override
                        public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                            List<Match> data = response.body();
                            if (data == null || data.size() == 0) {
                                listview.setVisibility(View.GONE);
                                notfoundLayout.setVisibility(View.VISIBLE);
                            } else {
                                listview.setVisibility(View.VISIBLE);
                                notfoundLayout.setVisibility(View.GONE);
                                adapter = new Next24hAdapter(data, getContext(), listview);
                                listview.setAdapter(adapter);
                                for (int i = 0; i < adapter.getGroupCount(); i++) {
                                    listview.expandGroup(i);
                                }
                                listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                    @Override
                                    public boolean onGroupClick(ExpandableListView parent,
                                                                View v, int groupPosition, long id) {

                                        if (listview.isGroupExpanded(groupPosition)) {
                                            listview.collapseGroupWithAnimation(groupPosition);
                                        } else {
                                            listview.expandGroupWithAnimation(groupPosition);
                                        }
                                        return true;
                                    }
                                });

                                listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView parent,
                                                                View v, int groupPosition, int childPosition,
                                                                long id) {
                                        // TODO Auto-generated method stub
                                        final int matchId = adapter.getChild(groupPosition,
                                                childPosition).get_matchID();
                                        Intent matchIntent = new Intent(getContext(), MatchDetailActivity.class);
                                        matchIntent.putExtra("matchId",matchId);
                                        startActivity(matchIntent);
                                        return true;
                                    }

                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Match>> call, Throwable t) {
                            swiper.post(new Runnable() {
                                @Override
                                public void run() {
                                    swiper.setRefreshing(false);
                                }
                            });
                        }
                    });

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void loadContent() {

    }
}
