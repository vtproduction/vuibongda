package ats.abongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.Player;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 21-Aug-16.
 */
public class LineupAdapter extends RecyclerView.Adapter<LineupAdapter.ItemViewHolder>{
    private List<Player> model;
    private Context context;
    private ListItemClickImp callback;

    public LineupAdapter(List<Player> model, Context context, ListItemClickImp callback) {
        this.model = model;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()
            ).inflate(R.layout.item_lineup, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        try {
            final Player player = model.get(position);
            holder.doihinhItemName.setText(player.getName());
            holder.doihinhItemPos.setText(player.getPosition());
            ImageUlti.loadImage(context, player.getImage(), holder.TKHSEmailCf);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onListItemClicked(0, player.getId());
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
        @BindView(R.id.doihinh_item_indicator)
        ImageView doihinhItemIndicator;
        @BindView(R.id.doihinh_item_pos)
        TextView doihinhItemPos;
        @BindView(R.id.TKHS_email_cf)
        ImageView TKHSEmailCf;
        @BindView(R.id.doihinh_item_name)
        TextView doihinhItemName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
