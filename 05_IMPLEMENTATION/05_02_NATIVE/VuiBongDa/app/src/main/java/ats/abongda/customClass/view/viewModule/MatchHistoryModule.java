package ats.abongda.customClass.view.viewModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.model.Match;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 25-Aug-16.
 */
public class MatchHistoryModule extends MatchBaseModule {
    @BindView(R.id.tdpanel5_content)
    LinearLayout tdpanel5Content;

    public MatchHistoryModule(MatchDetailActivity.ModuleInteractImp callback, WeakReference<Context> context, MatchDetail match) {
        super(callback, context, match);
        this.mainView = LayoutInflater.from(context.get())
                .inflate(R.layout.item_match_panel5, null);
        ButterKnife.bind(this, mainView);
        loadData();
    }

    @Override
    public void loadData() {
        LayoutInflater inflater = LayoutInflater.from(context.get());
        tdpanel5Content.removeAllViews();
        for (final Match n : match.get_matchArray()) {
            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.item_match_history, null);
            ((TextView)item.findViewById(R.id.tddd_ngaythang)).setText(n.getMatchStartDateShortYear()+"\n"+n.getMatchStartTimeWithoutPlus()+":00");
            //((TextView)item.findViewById(R.id.tddd_ngaythang)).setText(n.getMatchDateString());
            ((ImageView)item.findViewById(R.id.tddd_league)).setImageResource(n.getLeagueLogoFromMatch());
            ((TextView)item.findViewById(R.id.tddd_host)).setText(n.get_home());
            ((TextView)item.findViewById(R.id.tddd_guest)).setText(n.get_guest());
            ((TextView)item.findViewById(R.id.tddd_score)).setText(n.get_homeGoal()+"-"+n.get_guestGoal());
            tdpanel5Content.addView(item);
            item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    callback.onClicked(n.get_matchID(), null);
                }
            });
        }
    }
}
