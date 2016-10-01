package ats.abongda.customClass.view.viewModule;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.model.Player;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 25-Aug-16.
 */
public class MatchLineupModule extends MatchBaseModule {
    @BindView(R.id.tdpanel4_lineup_hostOff)
    LinearLayout tdpanel4LineupHostOff;
    @BindView(R.id.tdpanel4_lineup_guestOff)
    LinearLayout tdpanel4LineupGuestOff;
    @BindView(R.id.lup_text)
    TextView lupText;
    @BindView(R.id.tdpanel4_lineup_host)
    LinearLayout tdpanel4LineupHost;
    @BindView(R.id.tdpanel4_lineup_guest)
    LinearLayout tdpanel4LineupGuest;
    @BindView(R.id.tdpanel4_content)
    LinearLayout tdpanel4Content;

    public MatchLineupModule(MatchDetailActivity.ModuleInteractImp callback, WeakReference<Context> context, MatchDetail match) {
        super(callback, context, match);
        this.mainView = LayoutInflater.from(context.get())
                .inflate(R.layout.item_match_panel4, null);
        ButterKnife.bind(this, mainView);
        loadData();
    }

    @Override
    public void loadData() {
        if (match.get_lineup_list().size() < 1){
            tdpanel4Content.setVisibility(View.GONE);
            return;
        }
        tdpanel4LineupHost.removeAllViews();
        tdpanel4LineupHostOff.removeAllViews();
        tdpanel4LineupGuest.removeAllViews();
        tdpanel4LineupGuestOff.removeAllViews();
        for (int i = 0; i < match.get_lineup_list().size(); i++) {
            Player c = match.get_lineup_list().get(i);

            if (c.getClubId() == match.get_home_id() && c.getRole() == 2) {
                tdpanel4LineupHost.addView(setLineUpList(c, 3));
            } else if (c.getClubId() == match.get_home_id() && c.getRole() == 1) {
                tdpanel4LineupHostOff.addView(setLineUpList(c, 1));
            } else if (c.getClubId() == match.get_guest_id()
                    && c.getRole() == 2) {
                tdpanel4LineupGuest.addView(setLineUpList(c, 4));
            } else if (c.getClubId() == match.get_guest_id()
                    && c.getRole() == 1) {
                tdpanel4LineupGuestOff.addView(setLineUpList(c, 2));
            }
        }
    }


    private View setLineUpList(Player m, int flag) {
        LayoutInflater inflater = LayoutInflater.from(context.get());
        LinearLayout item;
        TextView tv;
        ImageSpan is;
        SpannableString text;
        if (m.getTimeIn() > 0) {
            is = new ImageSpan(context.get(), R.drawable.substitution_in);
        } else {
            is = new ImageSpan(context.get(), R.drawable.substitution_out);
        }
        if (flag == 1 || flag == 3) {
            item = (LinearLayout) inflater.inflate(R.layout.item_match_lineuplist_layout, null);
            tv = (TextView) item.findViewById(R.id.lup_text);
            if (m.getTimeIn() > 0) {
                text = new SpannableString(Html.fromHtml(m.getName() + " " + "<font color=#5FABD6>(" + m.getTimeIn() + ")</font>"));
                text.setSpan(is, m.getName().length(), 1 + m.getName().length(), 0);
            } else if (m.getTimeOut() > 0) {
                text = new SpannableString(Html.fromHtml(m.getName() + " " + "<font color=#5FABD6>(" + m.getTimeOut() + ")</font>"));
                text.setSpan(is, m.getName().length(), 1 + m.getName().length(), 0);
            } else {
                text = new SpannableString(Html.fromHtml(m.getName() + " "));
                //text.setSpan(is, m.getName().length(), 1 + m.getName().length(), 0);
            }

        } else {
            item = (LinearLayout) inflater.inflate(R.layout.item_match_lineuplist_g_layout, null);
            tv = (TextView) item.findViewById(R.id.lup_g_text);
            if (m.getTimeIn() > 0) {
                text = new SpannableString(Html.fromHtml("<font color=#5FABD6>" + "-(" + m.getTimeIn() + "')</font>" + " " + m.getName()));
                text.setSpan(is, 0, 1, 0);
            } else if (m.getTimeOut() > 0) {
                text = new SpannableString(Html.fromHtml("<font color=#5FABD6>" + "-(" + m.getTimeOut() + "')</font>" + " " + m.getName()));
                text.setSpan(is, 0, 1, 0);
            } else {
                text = new SpannableString(Html.fromHtml(" " + m.getName()));
                //text.setSpan(is, 0, 1, 0);
            }
        }

        //ImageSpan is = new ImageSpan(context.get(), R.drawable.substitution_in);
        tv.setText(text);
        return item;

    }
}
