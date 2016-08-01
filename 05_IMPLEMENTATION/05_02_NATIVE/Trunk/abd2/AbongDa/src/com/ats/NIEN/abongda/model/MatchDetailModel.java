package com.ats.NIEN.abongda.model;

import java.io.Serializable;

import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class MatchDetailModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Extra;
	private String GuestInfo;
	private String HomeInfo;
	private int Id;
	private String Score;
	private String Status;
	private String Type;
	public String getExtra() {
		return Extra;
	}
	public void setExtra(String extra) {
		Extra = extra;
	}
	public String getGuestInfo() {
		return GuestInfo;
	}
	public void setGuestInfo(String guestInfo) {
		GuestInfo = guestInfo;
	}
	public String getHomeInfo() {
		return HomeInfo;
	}
	public void setHomeInfo(String homeInfo) {
		HomeInfo = homeInfo;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public MatchDetailModel(String extra, String guestInfo, String homeInfo, int id,
			String score, String status, String type) {
		super();
		Extra = extra;
		GuestInfo = guestInfo;
		HomeInfo = homeInfo;
		Id = id;
		Score = score;
		Status = status;
		Type = type;
	}
	
	public int getMatchDetailImage(){
		if (this.Type.equalsIgnoreCase("01") || this.Type.equalsIgnoreCase("10")) {
			return R.drawable.football_icon;
		}else if (this.Type.equalsIgnoreCase("02") || this.Type.equalsIgnoreCase("20")) {
			return R.drawable.yellow_card;
		}else if (this.Type.equalsIgnoreCase("03") || this.Type.equalsIgnoreCase("30")) {
			return R.drawable.red_card;
		}else if (this.Type.equalsIgnoreCase("04") || this.Type.equalsIgnoreCase("40")) {
			return R.drawable.double_yellow_card;
		}else if (this.Type.equalsIgnoreCase("05") || this.Type.equalsIgnoreCase("50")) {
			return R.drawable.pen_failed;
		}
		return 0;
	}
	
	public boolean isHomeTurn(){
		return this.HomeInfo.length() > this.GuestInfo.length();
	}
	
}
