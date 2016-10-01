package ats.abongda.model;

import android.annotation.SuppressLint;

import org.parceler.Parcel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ats.abongda.R;
import ats.abongda.helper.LogUtil;

/**
 * Created by NienLe on 06-Aug-16.
 */
@Parcel
public class Match {
    private String _home;
    private String _guest;
    private int _homeGoal;
    private int _guestGoal;
    private int _homePenaltyShoot;
    private int _guestPenaltyShoot;
    private String _matchMinute;
    private int _matchStatus;
    private int _matchID;
    private String _matchDatetime;
    private boolean isHeaderNeeded;
    private String _tvChanel;
    private int _guest_id;
    private int _home_id;
    private int _isBet;
    private int _isHot;
    private int _league_row_id;



    public String get_home() {
        return _home;
    }

    public void set_home(String _home) {
        this._home = _home;
    }

    public String get_guest() {
        return _guest;
    }

    public void set_guest(String _guest) {
        this._guest = _guest;
    }

    public int get_homeGoal() {
        return _homeGoal;
    }

    public void set_homeGoal(int _homeGoal) {
        this._homeGoal = _homeGoal;
    }

    public int get_guestGoal() {
        return _guestGoal;
    }

    public void set_guestGoal(int _guestGoal) {
        this._guestGoal = _guestGoal;
    }

    public int get_homePenaltyShoot() {
        return _homePenaltyShoot;
    }

    public void set_homePenaltyShoot(int _homePenaltyShoot) {
        this._homePenaltyShoot = _homePenaltyShoot;
    }

    public int get_guestPenaltyShoot() {
        return _guestPenaltyShoot;
    }

    public void set_guestPenaltyShoot(int _guestPenaltyShoot) {
        this._guestPenaltyShoot = _guestPenaltyShoot;
    }

    public String get_matchMinute() {
        return _matchMinute;
    }

    public void set_matchMinute(String _matchMinute) {
        this._matchMinute = _matchMinute;
    }

    public int get_matchStatus() {
        return _matchStatus;
    }

    public void set_matchStatus(int _matchStatus) {
        this._matchStatus = _matchStatus;
    }

    public int get_matchID() {
        return _matchID;
    }

    public void set_matchID(int _matchID) {
        this._matchID = _matchID;
    }

    public String get_matchDatetime() {
        return _matchDatetime;
    }

    public void set_matchDatetime(String _matchDatetime) {
        this._matchDatetime = _matchDatetime;
    }

    public boolean isHeaderNeeded() {
        return isHeaderNeeded;
    }

    public void setHeaderNeeded(boolean headerNeeded) {
        isHeaderNeeded = headerNeeded;
    }

    public String get_tvChanel() {
        return _tvChanel;
    }

    public void set_tvChanel(String _tvChanel) {
        this._tvChanel = _tvChanel;
    }

    public int get_guest_id() {
        return _guest_id;
    }

    public void set_guest_id(int _guest_id) {
        this._guest_id = _guest_id;
    }

    public int get_home_id() {
        return _home_id;
    }

    public void set_home_id(int _home_id) {
        this._home_id = _home_id;
    }

    public int get_isBet() {
        return _isBet;
    }

    public void set_isBet(int _isBet) {
        this._isBet = _isBet;
    }

    public int get_isHot() {
        return _isHot;
    }

    public void set_isHot(int _isHot) {
        this._isHot = _isHot;
    }



    public Date getOldMatchDate(String matchDate){
        long timestamp = Long.parseLong(matchDate);
        return new Date(timestamp);
    }
    @SuppressLint("SimpleDateFormat")
    public String getMatchStartTime(){
        DateFormat formatter = new SimpleDateFormat("HH");
        int mHour = Integer.parseInt(formatter.format(getOldMatchDate(getDate().getDate() +""))) + 7;
        if (mHour >= 24) {
            mHour = mHour - 24;
        }
        formatter = new SimpleDateFormat("mm");
        if (mHour >= 10) {
            return  mHour+":"+formatter.format(getDate());
        }
        return "0" + mHour+":"+formatter.format(getDate());
    }

    @SuppressLint("SimpleDateFormat")
    public String getMatchStartTimeWithoutPlus(){
        DateFormat formatter = new SimpleDateFormat("HH");
        int mHour = Integer.parseInt(formatter.format(getOldMatchDate(getDate().getTime()+"")));
        if (mHour < 0) {
            mHour = 24 + mHour;
        }
        formatter = new SimpleDateFormat("mm");
        if (mHour >= 10) {
            return  mHour+":"+formatter.format(getDate());
        }
        return "0" + mHour+":"+formatter.format(getDate());
    }


    @SuppressLint("SimpleDateFormat")
    public String getMatchShortStartDate(){
        DateFormat formatter = new SimpleDateFormat("dd/MM");
        return  formatter.format(getDate());
    }

    @SuppressLint("SimpleDateFormat")
    public String getMatchShortStartDateAndTime(){
        DateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        return  formatter.format(getDate());
    }

    @SuppressLint("SimpleDateFormat")
    public String getMatchStartDate(){
        return this._matchDatetime;
    }
    @SuppressLint("SimpleDateFormat")
    public String getMatchStartDateShortYear(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return  formatter.format(getDate());
    }

    @SuppressLint("SimpleDateFormat")
    public String getMatchStartDayInWeekEn(){
        Date date =getDate();
        DateFormat formatter = new SimpleDateFormat("EEE");
        return  formatter.format(date);
    }

    public String getMatchFormattedStartDate(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return  formatter.format(getDate());
    }

    @SuppressLint("SimpleDateFormat")
    public String getMatchStartDayInWeekVi(){
        Date date =getDate();
        DateFormat formatter = new SimpleDateFormat("EEE", Locale.ENGLISH);
        String dayInWeek = formatter.format(date) ;
        if (dayInWeek.equals("Mon")) {
            return "Thứ hai";
        }
        if (dayInWeek.equals("Tue")) {
            return "Thứ ba";
        }
        if (dayInWeek.equals("Wed")) {
            return "Thứ tư";
        }
        if (dayInWeek.equals("Thu")) {
            return "Thứ năm";
        }
        if (dayInWeek.equals("Fri")) {
            return "Thứ sáu";
        }
        if (dayInWeek.equals("Sat")) {
            return "Thứ bảy";
        }
        if (dayInWeek.equals("Sun")) {
            return "Chủ nhật";
        }
        return "";
    }

    public int get_league_row_id() {
        return _league_row_id;
    }

    public void set_league_row_id(int _league_row_id) {
        this._league_row_id = _league_row_id;
    }
    public int getLeagueLogoFromMatch() {
        int leagueId = get_league_row_id();
        if (leagueId <= 5) {
            switch (leagueId) {
                case 1:
                    return R.drawable.league_pl;
                case 2:
                    return R.drawable.league_lfp;
                case 3:
                    return R.drawable.league_seriea;
                case 4:
                    return R.drawable.league_bundesliga;
                case 5:
                    return R.drawable.league_l1;

                default:
                    return R.drawable.league_other;
            }
        }
        if (leagueId > 5 && leagueId <= 23) {
            return R.drawable.league_cl;
        }
        if (leagueId > 23 && leagueId <= 46) {
            return R.drawable.league_el;
        }
        if (leagueId > 46 && leagueId <= 62) {
            return R.drawable.league_fa;
        }
        return R.drawable.league_other;
    }


    public Date getDate() {
        long timestamp = Long.parseLong(this._matchDatetime
                .substring(this._matchDatetime.indexOf('(')+1, this._matchDatetime.indexOf('+')));
        Date date = new Date(timestamp);
        Date dateGMT = new Date(date.getTime());
        return dateGMT;
    }
}


