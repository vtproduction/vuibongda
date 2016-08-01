package com.ats.NIEN.abongda.model;

import java.util.List;


public class Next24hModel {
	private String day;
	private List<MatchModel> matchList;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<MatchModel> getMatchList() {
		return matchList;
	}
	public void setMatchList(List<MatchModel> matchList) {
		this.matchList = matchList;
	}
	public Next24hModel(String day, List<MatchModel> matchList) {
		super();
		this.day = day;
		this.matchList = matchList;
	}
	public Next24hModel(String day) {
		super();
		this.day = day;
		
	}
	
	
	
}
