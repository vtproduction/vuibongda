package ats.abongda.customClass.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

import ats.abongda.R;
import ats.abongda.helper.ImageUlti;
import ats.abongda.model.News;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 07-Aug-16.
 */
public class CustomNewsHeaderContent extends BaseSliderView {
    @BindView(R.id.tintuc_largeimage_img)
    ImageView tintucLargeimageImg;
    @BindView(R.id.tintuc_largeimage_text)
    TextView tintucLargeimageText;

    @Override
    public View getView() {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_news_header_banner, null);
        ButterKnife.bind(this, v);
        ImageUlti.loadImage(context,model.getImageLink(), tintucLargeimageImg);
        tintucLargeimageText.setText(model.getTitle());
        bindEventAndShow(v, tintucLargeimageImg);
        return v;
    }

    private Context context;
    private News model;

    public CustomNewsHeaderContent(Context context) {
        super(context);
        this.context = context;
    }

    public void setModel(News model) {
        this.model = model;
    }

    public News getModel() {
        return model;
    }
}
