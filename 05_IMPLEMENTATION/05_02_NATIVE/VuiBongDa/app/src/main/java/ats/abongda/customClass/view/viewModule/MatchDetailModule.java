package ats.abongda.customClass.view.viewModule;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import ats.abongda.R;
import ats.abongda.activity.MatchDetailActivity;
import ats.abongda.activity.VideoPlayerActivity;
import ats.abongda.config.APILink;
import ats.abongda.helper.ImageUlti;
import ats.abongda.helper.LogUtil;
import ats.abongda.model.Match;
import ats.abongda.model.match.Clip;
import ats.abongda.model.match.InfoList;
import ats.abongda.model.match.MatchDetail;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NienLe on 23-Aug-16.
 */
public class MatchDetailModule extends MatchBaseModule {


    @BindView(R.id.tdpanel1_hostlogo)
    ImageView tdpanel1Hostlogo;
    @BindView(R.id.tdpanel1_guestlogo)
    ImageView tdpanel1Guestlogo;
    @BindView(R.id.tdpanel1_host_ls0)
    ImageView tdpanel1HostLs0;
    @BindView(R.id.tdpanel1_host_ls1)
    ImageView tdpanel1HostLs1;
    @BindView(R.id.tdpanel1_host_ls2)
    ImageView tdpanel1HostLs2;
    @BindView(R.id.tdpanel1_host_ls3)
    ImageView tdpanel1HostLs3;
    @BindView(R.id.tdpanel1_host_ls4)
    ImageView tdpanel1HostLs4;
    @BindView(R.id.tdpanel1_host_vg)
    LinearLayout tdpanel1HostVg;
    @BindView(R.id.tdpanel1_guest_ls0)
    ImageView tdpanel1GuestLs0;
    @BindView(R.id.tdpanel1_guest_ls1)
    ImageView tdpanel1GuestLs1;
    @BindView(R.id.tdpanel1_guest_ls2)
    ImageView tdpanel1GuestLs2;
    @BindView(R.id.tdpanel1_guest_ls3)
    ImageView tdpanel1GuestLs3;
    @BindView(R.id.tdpanel1_guest_ls4)
    ImageView tdpanel1GuestLs4;
    @BindView(R.id.tdpanel1_guest_vg)
    LinearLayout tdpanel1GuestVg;
    @BindView(R.id.tdpanel1_host_name)
    TextView tdpanel1HostName;
    @BindView(R.id.tddd_guest)
    TextView tdddGuest;
    @BindView(R.id.tdpanel1_guest_name)
    TextView tdpanel1GuestName;
    @BindView(R.id.tdpanel1_guest_name2)
    TextView tdpanel1GuestName2;
    @BindView(R.id.tdpanel1_host_score)
    TextView tdpanel1HostScore;
    @BindView(R.id.tdpanel1_isFulltime)
    TextView tdpanel1IsFulltime;
    @BindView(R.id.tdpanel1_guest_score)
    TextView tdpanel1GuestScore;
    @BindView(R.id.tdpanel1_penhome)
    TextView tdpanel1Penhome;
    @BindView(R.id.datcuocpage_soxu)
    TextView datcuocpageSoxu;
    @BindView(R.id.tdpanel1_penguest)
    TextView tdpanel1Penguest;
    @BindView(R.id.tdpanel1_pen)
    LinearLayout tdpanel1Pen;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.tdpanel1_stadium)
    TextView tdpanel1Stadium;
    @BindView(R.id.zzz)
    LinearLayout zzz;
    @BindView(R.id.tdpanel1_bot)
    LinearLayout tdpanel1Bot;
    @BindView(R.id.td_p1_content)
    LinearLayout tdP1Content;
    @BindView(R.id.td_panel1)
    LinearLayout tdPanel1;

    public MatchDetailModule(MatchDetailActivity.ModuleInteractImp callback, WeakReference<Context> context, MatchDetail match) {
        super(callback, context, match);
        this.mainView = LayoutInflater.from(context.get())
                .inflate(R.layout.item_match_panel1, null);
        ButterKnife.bind(this, mainView);
        loadData();

    }



    @Override
    public void loadData() {
        try {
            ImageUlti.loadImage(context.get(), APILink.MEDIALINK + match.get_homeLogo(), tdpanel1Hostlogo);
            ImageUlti.loadImage(context.get(), APILink.MEDIALINK + match.get_guestLogo(), tdpanel1Guestlogo);
            for (int i = 0; i < match.get_homeForm().size(); i++){
                switch (match.get_homeForm().get(i)){
                    case 2:
                        ((ImageView) tdpanel1HostVg.getChildAt(i))
                                .setImageResource(R.drawable.td_icon_bai);
                        break;
                    case 0:
                        ((ImageView) tdpanel1HostVg.getChildAt(i))
                                .setImageResource(R.drawable.td_icon_hoa);
                        break;
                    case 1:
                        ((ImageView) tdpanel1HostVg.getChildAt(i))
                                .setImageResource(R.drawable.td_icon_thang);
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < match.get_guestForm().size(); i++){
                switch (match.get_guestForm().get(i)){
                    case 2:
                        ((ImageView) tdpanel1GuestVg.getChildAt(i))
                                .setImageResource(R.drawable.td_icon_bai);
                        break;
                    case 0:
                        ((ImageView) tdpanel1GuestVg.getChildAt(i))
                                .setImageResource(R.drawable.td_icon_hoa);
                        break;
                    case 1:
                        ((ImageView) tdpanel1GuestVg.getChildAt(i))
                                .setImageResource(R.drawable.td_icon_thang);
                        break;
                    default:
                        break;
                }
            }
            tdpanel1HostName.setText(match.get_home());
            tdpanel1GuestName.setText(match.get_guest());
            if (match.get_matchStatus() != 0){
                tdpanel1HostScore.setText(""+match.get_homeGoal());
                tdpanel1GuestScore.setText(""+match.get_guestGoal());
                tdpanel1IsFulltime.setText(match.get_matchMinute()+"");
                tdpanel1HostScore.setVisibility(View.VISIBLE);
                tdpanel1GuestScore.setVisibility(View.VISIBLE);
                tdpanel1IsFulltime.setVisibility(View.VISIBLE);
            }else {
                tdpanel1HostScore.setVisibility(View.GONE);
                tdpanel1GuestScore.setVisibility(View.GONE);
                tdpanel1IsFulltime.setVisibility(View.GONE);
            }
            if(match.get_homePenaltyShoot() + match.get_guestPenaltyShoot() > 0){
                tdpanel1Penhome.setText(""+match.get_homePenaltyShoot());
                tdpanel1Penguest.setText(""+match.get_guestPenaltyShoot());
            }else {
                tdpanel1Pen.setVisibility(View.GONE);
            }
            tdpanel1Stadium.setText(""+match.get_matchStadium());
            tdpanel1Bot.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(context.get());
            List<Clip> clips = match.get_clips();
            for (int i = 0; i < match.get_info_list().size(); i++) {
                InfoList mfl = match.get_info_list().get(i);
                LinearLayout mdi = (LinearLayout) inflater.inflate(
                        R.layout.item_match_info, null);
                if (mfl.isHomeTurn()) {
                    mdi.findViewById(R.id.tddb_guest).setVisibility(View.INVISIBLE);
                    ((TextView) mdi.findViewById(R.id.tddb_time)).setText(mfl
                            .getStatus());
                    ((TextView) mdi.findViewById(R.id.tddb_host_text)).setText(mfl
                            .getHomeInfo());
                    ((ImageView) mdi.findViewById(R.id.tddb_host_img))
                            .setImageResource(mfl.getMatchDetailImage());
                    final String homeGoalClip = getHostGoalClip(mfl);
                    if (homeGoalClip.length() == 0)
                        mdi.findViewById(R.id.clip_host_img).setVisibility(View.INVISIBLE);
                    else
                        mdi.findViewById(R.id.tddb_host).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onClipClicked(homeGoalClip);
                            }
                        });

                } else {
                    mdi.findViewById(R.id.tddb_host).setVisibility(View.INVISIBLE);
                    ((TextView) mdi.findViewById(R.id.tddb_time)).setText(mfl
                            .getStatus());
                    ((TextView) mdi.findViewById(R.id.tddb_guest_text)).setText(mfl
                            .getGuestInfo());
                    ((ImageView) mdi.findViewById(R.id.tddb_guest_img))
                            .setImageResource(mfl.getMatchDetailImage());
                    final String guestGoalClip = getGuestGoalClip(mfl);
                    if (guestGoalClip.length() == 0)
                        mdi.findViewById(R.id.clip_guest_img).setVisibility(View.INVISIBLE);
                    else
                        mdi.findViewById(R.id.tddb_guest).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onClipClicked(guestGoalClip);
                            }
                        });

                }
                tdpanel1Bot.addView(mdi);
            }
            callback.onDataloaded(true);

        }catch (Exception e){
            LogUtil.e(e);
            callback.onDataloaded(false);

        }
    }

    private void onClipClicked(String url){
        if (url.length() > 0){
            LogUtil.d("CLIP: " + url);
            Intent intent = new Intent(context.get(), VideoPlayerActivity.class);
            intent.putExtra("url", url);
            context.get().startActivity(intent);
        }

    }

    private String getHostGoalClip(InfoList infoList){
        //if (!infoList.getType().equals("01") && !infoList.getType().equals("10")) return  "";
        List<Clip> clips = match.get_clips();
        for (Clip clip : clips){
            if (clip.getClip_description().trim().contains(infoList.getHomeInfo().trim())){
                if (infoList.getStatus().trim().contains(clip.getMatch_minute().trim()))
                    return clip.getClip_link();
            }
        }
        return "";
    }


    private String getGuestGoalClip(InfoList infoList){
        //if (!infoList.getType().equals("01") && !infoList.getType().equals("10")) return  "";
        List<Clip> clips = match.get_clips();
        for (Clip clip : clips){
            if (clip.getClip_description().trim().contains(infoList.getGuestInfo().trim())){
                if (infoList.getStatus().trim().contains(clip.getMatch_minute().trim()))
                    return clip.getClip_link();
            }
        }
        return "";
    }

    @OnClick({R.id.tdpanel1_hostlogo, R.id.tdpanel1_guestlogo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tdpanel1_hostlogo:
                callback.onClicked(match.get_home_id(), null);
                break;
            case R.id.tdpanel1_guestlogo:
                callback.onClicked(match.get_guest_id(), null);
                break;
        }
    }
}
