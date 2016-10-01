package ats.abongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.StringHelper;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Match;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 18-Aug-16.
 */
public class LeagueClubMatchtimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Match> matchList;
    private Context context;
    private ListItemClickImp callback;
    public static final int TYPE_ITEM_ODD = 0;
    public static final int TYPE_ITEM_EVEN = 1;

    public LeagueClubMatchtimeAdapter(List<Match> matchList, Context context, ListItemClickImp callback) {
        this.matchList = matchList;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM_EVEN:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_club_match_time_even, parent, false));
            case TYPE_ITEM_ODD:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_club_match_time_odd, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            final Match match = matchList.get(position);
            ItemViewHolder item = (ItemViewHolder) holder;
            if (match.get_matchStatus() == 0) {
                item.lichthidauPageDBFinalScore.setText("   -   ");
            } else {
                item.lichthidauPageDBFinalScore.setText(" " + match.get_homeGoal() + "-" + match.get_guestGoal() + " ");
            }
            item.lichthidauPageDBTime.setText(match.getMatchShortStartDateAndTime().replace(" ", "\n"));
            item.lichthidauPageDBHost.setText(match.get_home());
            item.lichthidauPageDBGuest.setText(match.get_guest());
            ImageUlti.loadImage(context, StringHelper.getTrueLeagueImageResource(match.get_league_row_id()), item.lichthidauPageDBDoibongLogo);
            item.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(-1, match.get_matchID());
                }
            });
            item.lichthidauPageDBHost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(match.get_home_id(), match.get_home());
                }
            });
            item.lichthidauPageDBGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(match.get_guest_id(), match.get_guest());
                }
            });
        } catch (Exception e) {
            LogUtil.e(e);
        }

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_ITEM_EVEN : TYPE_ITEM_ODD;
    }

    /*public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.choose_year_text)
        TextView chooseYearText;
        @BindView(R.id.choose_year_btn)
        RelativeLayout chooseYearBtn;
        @BindView(R.id.choose_month_text)
        TextView chooseMonthText;
        @BindView(R.id.choose_month_btn)
        RelativeLayout chooseMonthBtn;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.choose_year_btn, R.id.choose_month_btn})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.choose_year_btn:
                    break;
                case R.id.choose_month_btn:
                    break;
            }
        }
    }*/

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.lichthidauPageDB_time)
        TextView lichthidauPageDBTime;
        @BindView(R.id.lichthidauPageDBDoibong_logo)
        ImageView lichthidauPageDBDoibongLogo;
        @BindView(R.id.lichthidauPageDB_host)
        TextView lichthidauPageDBHost;
        @BindView(R.id.lichthidauPageDB_finalScore)
        TextView lichthidauPageDBFinalScore;
        @BindView(R.id.lichthidauPageDB_guest)
        TextView lichthidauPageDBGuest;
        @BindView(R.id.lichthidauPageDB_indicator)
        ImageView lichthidauPageDBIndicator;
        @BindView(R.id.LinearLayout1)
        LinearLayout LinearLayout1;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
