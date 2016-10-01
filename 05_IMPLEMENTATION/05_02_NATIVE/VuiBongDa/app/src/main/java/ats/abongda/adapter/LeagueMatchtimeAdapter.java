package ats.abongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Match;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NienLe on 18-Aug-16.
 */
public class LeagueMatchtimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Match> matchList;
    private Context context;
    private ListItemClickImp callback;
    public static final int TYPE_ITEM_ODD = 0;
    public static final int TYPE_ITEM_EVEN = 1;

    public LeagueMatchtimeAdapter(List<Match> matchList, Context context, ListItemClickImp callback) {
        this.matchList = matchList;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ITEM_EVEN:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_matchtime_even, parent, false));
            case TYPE_ITEM_ODD:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_matchtime_odd, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            final Match match = matchList.get(position);
            ItemViewHolder item = (ItemViewHolder)holder;
            if (match.get_matchStatus() == 0) {
                item.lichthidauPageFinalScore.setText("   -   ");
            }else{
                item.lichthidauPageFinalScore.setText(" "+ match.get_homeGoal() + "-" + match.get_guestGoal() + " ");
            }
            item.lichthidauPageTime.setText(match.getMatchShortStartDateAndTime().replace(" ","\n"));
            item.lichthidauPageHost.setText(match.get_home());
            item.lichthidauPageGuest.setText(match.get_guest());
            item.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(-1, match.get_matchID());
                }
            });
            item.lichthidauPageHost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(match.get_home_id(), match.get_home());
                }
            });
            item.lichthidauPageGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(match.get_guest_id(), match.get_guest());
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_ITEM_EVEN :TYPE_ITEM_ODD;
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

        @BindView(R.id.lichthidauPage_finalScore)
        TextView lichthidauPageFinalScore;
        @BindView(R.id.lichthidauPage_host)
        TextView lichthidauPageHost;
        @BindView(R.id.lichthidauPage_guest)
        TextView lichthidauPageGuest;
        @BindView(R.id.lichthidauPage_time)
        TextView lichthidauPageTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
