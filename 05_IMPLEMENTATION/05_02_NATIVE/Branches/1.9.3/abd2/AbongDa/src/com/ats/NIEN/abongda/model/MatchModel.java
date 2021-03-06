package com.ats.NIEN.abongda.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("NewApi")
public class MatchModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1563333769535363250L;
	private String home;
	private String guest;
	private int homeGoal;
	private int guestGoal;
	private int homePenaltyShoot;
	private int guestPenaltyShoot;
	private String matchMinute;
	private String matchDateString;
	private int matchStatus;
	private int matchId;
	private Date matchDate;
	private boolean isHeaderNeeded;
	private String tvChanel;
	private int guestId;
	private int homeId;
	private int isBet;
	private int isHot;
	public boolean isHeaderNeeded() {
		return isHeaderNeeded;
	}
	public void setHeaderNeeded(boolean isHeaderNeeded) {
		this.isHeaderNeeded = isHeaderNeeded;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getGuest() {
		return guest;
	}
	public void setGuest(String guest) {
		this.guest = guest;
	}
	public int getHomeGoal() {
		return homeGoal;
	}
	public void setHomeGoal(int homeGoal) {
		this.homeGoal = homeGoal;
	}
	public int getGuestGoal() {
		return guestGoal;
	}
	public void setGuestGoal(int guestGoal) {
		this.guestGoal = guestGoal;
	}
	public int getHomePenaltyShoot() {
		return homePenaltyShoot;
	}
	public void setHomePenaltyShoot(int homePenaltyShoot) {
		this.homePenaltyShoot = homePenaltyShoot;
	}
	public int getGuestPenaltyShoot() {
		return guestPenaltyShoot;
	}
	public void setGuestPenaltyShoot(int guestPenaltyShoot) {
		this.guestPenaltyShoot = guestPenaltyShoot;
	}
	public String getMatchMinute() {
		return matchMinute;
	}
	public void setMatchMinute(String matchMinute) {
		this.matchMinute = matchMinute;
	}
	public int getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(int matchStatus) {
		this.matchStatus = matchStatus;
	}
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
	    long timestamp = Long.parseLong(matchDate.substring(matchDate.indexOf('(')+1, matchDate.indexOf('+')));
	    Date date = new Date(timestamp);
		Date dateGMT = new Date(date.getTime());
	    this.matchDate = dateGMT;
	}	
	
	public Date getOldMatchDate(String matchDate){
		long timestamp = Long.parseLong(matchDate.substring(matchDate.indexOf('(')+1, matchDate.indexOf('+')));
	    return new Date(timestamp);
	}
	public MatchModel(String home, String guest, int homeGoal, int guestGoal,
			int homePenaltyShoot, int guestPenaltyShoot, String matchMinute,
			int matchStatus, int matchId, String matchDate, int homeId, int guestId, int isBet, int isHot) {
		super();
		this.home = home;
		this.guest = guest;
		this.homeGoal = homeGoal;
		this.guestGoal = guestGoal;
		this.homePenaltyShoot = homePenaltyShoot;
		this.guestPenaltyShoot = guestPenaltyShoot;
		this.matchMinute = matchMinute;
		this.matchStatus = matchStatus;
		this.matchId = matchId;
		setMatchDate(matchDate);
		this.matchDateString = matchDate;
		setHeaderNeeded(false);
		this.homeId = homeId;
		this.guestId = guestId;
		this.isBet = isBet;
		this.isHot = isHot;

	}
	private int leagueRowId;
	
	
	
	@SuppressLint("SimpleDateFormat")
	public String getMatchStartTime(){
		DateFormat formatter = new SimpleDateFormat("HH");
		int mHour = Integer.parseInt(formatter.format(getOldMatchDate(matchDateString))) + 7;
		if (mHour >= 24) {
			mHour = mHour - 24;
		}
		formatter = new SimpleDateFormat("mm");
		if (mHour >= 10) {
			return  mHour+":"+formatter.format(this.getMatchDate());
		}
		return "0" + mHour+":"+formatter.format(this.getMatchDate());
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getMatchStartTimeWithoutPlus(){
		DateFormat formatter = new SimpleDateFormat("HH");
		int mHour = Integer.parseInt(formatter.format(getOldMatchDate(matchDateString)));
		if (mHour < 0) {
			mHour = 24 + mHour;
		}
		formatter = new SimpleDateFormat("mm");
		if (mHour >= 10) {
			return  mHour+":"+formatter.format(this.getMatchDate());
		}
		return "0" + mHour+":"+formatter.format(this.getMatchDate());
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public String getMatchShortStartDate(){
		DateFormat formatter = new SimpleDateFormat("dd/MM");
		return  formatter.format(this.getMatchDate());		
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getMatchStartDate(){
		DateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
		return  formatter.format(this.getMatchDate());		
	}
	@SuppressLint("SimpleDateFormat")
	public String getMatchStartDateShortYear(){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		return  formatter.format(this.getMatchDate());		
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getMatchStartDayInWeekEn(){
		DateFormat formatter = new SimpleDateFormat("EEE");
		return  formatter.format(this.getMatchDate());		
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getMatchStartDayInWeekVi(){
		DateFormat formatter = new SimpleDateFormat("EEE", Locale.ENGLISH);
		String dayInWeek = formatter.format(this.getMatchDate()) ;
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
	public String getMatchDateString() {
		return matchDateString;
	}
	public void setMatchDateString(String matchDateString) {
		this.matchDateString = matchDateString;
	}
	public int getLeagueRowId() {
		return leagueRowId;
	}
	public void setLeagueRowId(int leagueRowId) {
		this.leagueRowId = leagueRowId;
	}


	public int getLeagueLogoFromMatch(){
		int leagueId = getLeagueRowId();
		if(leagueId <= 5){
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
		if(leagueId > 5 && leagueId <= 23){
			return R.drawable.league_cl;
		}
		if(leagueId > 23 && leagueId <= 46){
			return R.drawable.league_el;
		}
		if(leagueId > 46 && leagueId <= 62){
			return R.drawable.league_fa;
		}
		return R.drawable.league_other;
	}
	public String getTvChanel() {
		return tvChanel;
	}
	public void setTvChanel(String tvChanel) {
		this.tvChanel = tvChanel;
	}
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public int getHomeId() {
		return homeId;
	}
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}
	public int getIsBet() {
		return isBet;
	}
	public void setIsBet(int isBet) {
		this.isBet = isBet;
	}
	public int getIsHot() {
		return isHot;
	}
	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}
	public MatchModel(int Id, String home, String guest, int homeGoal, int guestGoal,
			int homePenaltyShoot, int guestPenaltyShoot,
			String matchDateString, int leagueRowId) {
		super();
		this.matchId = Id;
		this.home = home;
		this.guest = guest;
		this.homeGoal = homeGoal;
		this.guestGoal = guestGoal;
		this.homePenaltyShoot = homePenaltyShoot;
		this.guestPenaltyShoot = guestPenaltyShoot;
		this.matchDateString = matchDateString;
		this.leagueRowId = leagueRowId;
		setMatchDate(matchDateString);
	}
	public MatchModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MatchModel(String matchDateString) {
		super();
		this.matchDateString = matchDateString;
		setMatchDate(this.matchDateString);
	}
	
	
	
	
}
