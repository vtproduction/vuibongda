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
import ats.abongda.fragment.DrawerFragment;
import ats.abongda.helper.LogUtil;
import ats.abongda.model.UserInbox;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 14-Aug-16.
 */
public class UserInboxAdapter extends RecyclerView.Adapter<UserInboxAdapter.Holder>{
    private List<UserInbox> model;
    private Context context;
    private final int ITEM_UNREAD = 0;
    private final int ITEM_READED = 1;
    private ItemClickedImp callback;

    public UserInboxAdapter(List<UserInbox> model, Context context, ItemClickedImp callback) {
        this.model = model;
        this.context = context;
        this.callback = callback;
    }

    public List<UserInbox> getModel() {
        return model;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(viewType == ITEM_READED ? R.layout.item_inbox_read : R.layout.item_inbox_unread, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        try {
            final UserInbox inbox = model.get(position);
            holder.hopthuTextDate.setText(inbox.getRegisterDatetime());
            holder.hopthuTextTitle.setText(inbox.getTitle());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onItemClicked(inbox);
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

    @Override
    public int getItemViewType(int position) {
        try{
            return model.get(position).getStatus() == 1 ? ITEM_READED : ITEM_UNREAD;
        }catch (Exception e){
            return ITEM_UNREAD;
        }
    }

    public interface ItemClickedImp{
        void onItemClicked(UserInbox inbox);
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.hopthu_textDate)
        TextView hopthuTextDate;
        @BindView(R.id.hopthu_textTitle)
        TextView hopthuTextTitle;
        @BindView(R.id.RelativeLayout1)
        RelativeLayout container;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
