package ats.abongda.customClass.view.viewModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NienLe on 25-Aug-16.
 */
public class MatchBetRatioModule extends MatchBaseModule {

    @BindView(R.id.TextView10)
    TextView TextView10;
    @BindView(R.id.tdpanel2_dc_1text)
    TextView tdpanel2Dc1text;
    @BindView(R.id.tdpanel2_dc_1)
    LinearLayout tdpanel2Dc1;
    @BindView(R.id.tdpanel2_dc_2text)
    TextView tdpanel2Dc2text;
    @BindView(R.id.tdpanel2_dc_2)
    LinearLayout tdpanel2Dc2;
    @BindView(R.id.tdpanel2_dc_3text)
    TextView tdpanel2Dc3text;
    @BindView(R.id.tdpanel2_dc_3)
    LinearLayout tdpanel2Dc3;
    @BindView(R.id.datcuocpage_taikhoan)
    TextView datcuocpageTaikhoan;
    @BindView(R.id.tdpanel2_dc_4text)
    TextView tdpanel2Dc4text;
    @BindView(R.id.tdpanel2_dc_4)
    LinearLayout tdpanel2Dc4;
    @BindView(R.id.tdpanel2_dc_5text)
    TextView tdpanel2Dc5text;
    @BindView(R.id.tdpanel2_dc_5)
    LinearLayout tdpanel2Dc5;
    @BindView(R.id.tdpanel2_dc_6text)
    TextView tdpanel2Dc6text;
    @BindView(R.id.tdpanel2_dc_6)
    LinearLayout tdpanel2Dc6;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.tdpanel2_dc_7)
    LinearLayout tdpanel2Dc7;
    @BindView(R.id.tdpanel2_content)
    LinearLayout tdpanel2Content;
    boolean canbet = true;
    boolean[] canbets = new boolean[]{true, true, true, true, true, true};

    public MatchBetRatioModule(MatchDetailActivity.ModuleInteractImp callback, WeakReference<Context> context, MatchDetail match) {
        super(callback, context, match);
        this.mainView = LayoutInflater.from(context.get())
                .inflate(R.layout.item_match_panel2, null);
        ButterKnife.bind(this, mainView);
        loadData();
    }

    @Override
    public void loadData() {
        canbet = !(match.get_matchStatus() == 1 || match.get_matchStatus() > 2);
        if (!canbet){
            tdpanel2Dc1.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
            tdpanel2Dc2.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_deact_f));
            tdpanel2Dc3.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
            tdpanel2Dc4.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
            tdpanel2Dc5.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
            tdpanel2Dc6.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
            tdpanel2Dc7.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
        }
        String t1 = (match.get_homeWinHandicap()==null || match.get_homeWinHandicap().equals("null"))?"":match.get_homeWinHandicap();
        tdpanel2Dc1text.setText(context.get().getString(R.string.chu_nha) + t1);
        if (t1.equalsIgnoreCase("")) {
            canbets[0] = false;
            tdpanel2Dc1.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
        }
        /*if (match.get_homeWinHandicap() == null)
            tdpanel2Dc1.setVisibility(View.INVISIBLE);
        else
            tdpanel2Dc1.setVisibility(View.VISIBLE);*/
        String t2 = (match.get_handicap()==null || match.get_handicap().equals("null"))?"":match.get_handicap();
        tdpanel2Dc2text.setText("Chấp\n" + t2);
        if (t2.equalsIgnoreCase("")) {
            canbets[1] = false;
            tdpanel2Dc2.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_deact_f));
        }
        String t3 = (match.get_guestWinHandicap()==null || match.get_guestWinHandicap().equals("null"))?"":match.get_guestWinHandicap();
        tdpanel2Dc3text.setText("Khách\n" + t3);
        if (t3.equalsIgnoreCase("")) {
            canbets[2] = false;
            tdpanel2Dc3.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
        }
        String t4 = (match.get_homeWin1x2()==null || match.get_homeWin1x2().equals("null"))?"":match.get_homeWin1x2();
        tdpanel2Dc4text.setText("Chủ nhà\n" + t4);
        if (t4.equalsIgnoreCase("")) {
            canbets[3] = false;
            tdpanel2Dc4.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
        }
        String t5 = (match.get_draw1x2()==null || match.get_draw1x2().equals("null"))?"":match.get_draw1x2();
        tdpanel2Dc5text.setText("Hòa\n" + t5);
        if (t5.equalsIgnoreCase("")) {
            canbets[4] = false;
            tdpanel2Dc5.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
        }
        String t6 = (match.get_guestWin1x2()==null || match.get_guestWin1x2().equals("null"))?"":match.get_guestWin1x2();
        tdpanel2Dc6text.setText("Khách\n" + t6);
        if (t6.equalsIgnoreCase("")) {
            canbets[5] = false;
            tdpanel2Dc6.setBackgroundDrawable(context.get().getResources().getDrawable(
                    R.drawable.td_tlc_act_f));
        }


    }




    @OnClick({R.id.tdpanel2_dc_1, R.id.tdpanel2_dc_2, R.id.tdpanel2_dc_3, R.id.tdpanel2_dc_4, R.id.tdpanel2_dc_5, R.id.tdpanel2_dc_6, R.id.tdpanel2_dc_7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tdpanel2_dc_1:
                if (canbet && canbets[0])
                    callback.onClicked(4, null);
                break;
            case R.id.tdpanel2_dc_2:
                /*if (canbet && canbets[1])
                    callback.onClicked(2, null);*/
                break;
            case R.id.tdpanel2_dc_3:
                if (canbet && canbets[2])
                    callback.onClicked(6, null);
                break;
            case R.id.tdpanel2_dc_4:
                if (canbet && canbets[3])
                    callback.onClicked(1, null);
                break;
            case R.id.tdpanel2_dc_5:
                if (canbet && canbets[4])
                    callback.onClicked(2, null);
                break;
            case R.id.tdpanel2_dc_6:
                if (canbet && canbets[5])
                    callback.onClicked(3, null);
                break;
            case R.id.tdpanel2_dc_7:
                if (canbet)
                    callback.onClicked(7, null);
                break;
        }
    }
}
