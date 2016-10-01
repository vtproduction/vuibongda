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
import java.util.Locale;

import ats.abongda.R;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Club;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 21-Aug-16.
 */
public class LeagueRankingAdapter extends RecyclerView.Adapter<LeagueRankingAdapter.ItemViewHolder>{
    private List<Club> model;
    private Context context;
    private ListItemClickImp callback;

    public LeagueRankingAdapter(List<Club> model, Context context, ListItemClickImp callback) {
        this.model = model;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        try {
            final Club club = model.get(position);
            int clubPos = model.indexOf(club) + 1;
            holder.xephangPageItemPos.setText(/*clubPos < 9 ? " " + clubPos : "" + */clubPos+"");
            ImageUlti.loadImage(context, club.get_logo(), holder.TKHSPhoneCf);
            holder.xephangPageItemName.setText(club.get_club_name());
            holder.xephangPageItemTran.setText(String.format(Locale.ENGLISH,"%d", club.get_total_match()));
            holder.xephangPageItemBtbb.setText(String.format(Locale.ENGLISH,"%d:%d", club.get_home_goal_win() + club
                    .get_guest_goal_win(),club.get_home_goal_lose() + club
                    .get_guest_goal_lose()));
            holder.xephangPageItemHs.setText(String.format(Locale.ENGLISH,"%d", club.get_home_goal_win() + club
                    .get_guest_goal_win() - club.get_home_goal_lose() - club
                    .get_guest_goal_lose()));
            holder.xephangPageItemDiem.setText(String.format(Locale.ENGLISH, "%d", club.get_mark()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(1, club.get_club_id());
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.xephangPageItem_pos)
        TextView xephangPageItemPos;
        @BindView(R.id.TKHS_phone_cf)
        ImageView TKHSPhoneCf;
        @BindView(R.id.xephangPageItem_name)
        TextView xephangPageItemName;
        @BindView(R.id.xephangPageItem_tran)
        TextView xephangPageItemTran;
        @BindView(R.id.xephangPageItem_btbb)
        TextView xephangPageItemBtbb;
        @BindView(R.id.xephangPageItem_hs)
        TextView xephangPageItemHs;
        @BindView(R.id.xephangPageItem_diem)
        TextView xephangPageItemDiem;
        @BindView(R.id.LinearLayout1)
        LinearLayout LinearLayout1;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
