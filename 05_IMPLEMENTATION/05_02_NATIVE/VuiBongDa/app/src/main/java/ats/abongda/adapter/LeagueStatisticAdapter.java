package ats.abongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ats.abongda.R;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Club;
import ats.abongda.model.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 21-Aug-16.
 */
public class LeagueStatisticAdapter extends RecyclerView.Adapter<LeagueStatisticAdapter.ItemViewHolder> {
    private List<Player> model;
    private Context context;
    private ListItemClickImp callback;


    public LeagueStatisticAdapter(List<Player> model, Context context, ListItemClickImp callback) {
        this.model = model;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league_statistic, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        try {
            Player player = model.get(position);
            int clubPos = model.indexOf(player) + 1;
            holder.thongkePageItemStt.setText(/*clubPos < 9 ? " " + clubPos : "" + */clubPos + "");
            ImageUlti.loadImage(context, player.getClubLogo(), holder.thongkePageItemLogo);
            holder.thongkePageItemName.setText(player.getName());
            holder.thongkePageItemBt.setText(String.format(Locale.ENGLISH, "%d", player.getGoal()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.thongkePage_item_stt)
        TextView thongkePageItemStt;
        @BindView(R.id.thongkePage_item_logo)
        ImageView thongkePageItemLogo;
        @BindView(R.id.thongkePage_item_name)
        TextView thongkePageItemName;
        @BindView(R.id.thongkePage_item_bt)
        TextView thongkePageItemBt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
