package ats.abongda.customClass.view.viewModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.helper.ToastUtil;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NienLe on 25-Aug-16.
 */
public class MatchTvshowModule extends MatchBaseModule {

    @BindView(R.id.tdpanel3_channel)
    TextView tdpanel3Channel;
    @BindView(R.id.datcuocpage_loaitile)
    TextView datcuocpageLoaitile;
    @BindView(R.id.tdpanel3_receivenotif)
    LinearLayout tdpanel3Receivenotif;
    @BindView(R.id.tdpanel3_content)
    LinearLayout tdpanel3Content;

    public MatchTvshowModule(MatchDetailActivity.ModuleInteractImp callback, WeakReference<Context> context, MatchDetail match) {
        super(callback, context, match);
        this.mainView = LayoutInflater.from(context.get())
                .inflate(R.layout.item_match_panel3, null);
        ButterKnife.bind(this, mainView);
        loadData();
    }

    @Override
    public void loadData() {
        if (match.get_matchStatus() == 1 || match.get_matchStatus() > 2) {
            tdpanel3Receivenotif.setBackgroundDrawable(context.get().getResources().getDrawable(
                            R.drawable.td_tlc_act_f));
            tdpanel3Channel.setVisibility(View.GONE);

        } else {
            tdpanel3Channel.setText(match.get_tvChannel());
            if (match.get_tvChannel().length() == 0)
                tdpanel3Channel.setVisibility(View.GONE);
        }

        /*if (match.get_matchStatus() == 0){

        }*/
    }

    public void dispatchClickEvent(){
        this.tdpanel3Receivenotif.setOnClickListener(null);
    }

    @OnClick(R.id.tdpanel3_receivenotif)
    public void onClick() {
        if (match.get_matchStatus() == 1 || match.get_matchStatus() > 2){
            ToastUtil.show(context.get().getString(R.string.tddkt));
        }else {
            callback.onClicked(0, null);
        }
    }
}
