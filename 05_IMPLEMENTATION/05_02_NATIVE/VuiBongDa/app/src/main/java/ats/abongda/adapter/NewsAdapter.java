package ats.abongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

import ats.abongda.R;
import ats.abongda.customClass.view.CustomNewsHeaderContent;
import ats.abongda.fragment.DrawerFragment;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.News;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 07-Aug-16.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News> models;
    private ListItemClickImp callback;
    private static final int ITEM_HEADER = 0;
    private static final int ITEM_NORMAL = 1;

    public NewsAdapter(Context context, List<News> models, ListItemClickImp callback) {
        LogUtil.d("size: " + models.size());
        this.context = context;
        this.models = models;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (viewType == ITEM_HEADER) ? new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_header, null))
                : new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            try {
                final News news = models.get(position + 1);
                ImageUlti.loadImage(context, news.getImageLink(), itemViewHolder.tintucListitemThumbimg);
                itemViewHolder.tintucListitemTitle.setText(news.getTitle());
                itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onListItemClicked(0, news);
                    }
                });
            }catch (Exception e){
                LogUtil.e(e);
            }
        }else{
            for (final News news : models){
                CustomNewsHeaderContent item = new CustomNewsHeaderContent(context);
                item.setModel(news);
                item.image(news.getImageLink())
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                callback.onListItemClicked(0, news);
                            }
                        });
                ((HeaderViewHolder)holder).slider.addSlider(item);
            }
        }
    }

    @Override
    public int getItemCount() {
        return models.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0)? ITEM_HEADER : ITEM_NORMAL;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tintuc_listitem_thumbimg)
        ImageView tintucListitemThumbimg;
        @BindView(R.id.tintuc_listitem_title)
        TextView tintucListitemTitle;
        @BindView(R.id.tintuc_listitem_indicator)
        ImageView tintucListitemIndicator;
        @BindView(R.id.tintuc_child_layout)
        LinearLayout tintucChildLayout;
        View view;
        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.slider)
        SliderLayout slider;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
