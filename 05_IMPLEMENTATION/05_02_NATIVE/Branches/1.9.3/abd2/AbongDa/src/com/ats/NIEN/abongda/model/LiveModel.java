package com.ats.NIEN.abongda.model;

import java.util.ArrayList;
import java.util.List;

public class LiveModel {
	private int leagueId;
	private String leagueName;
	private List<MatchModel> match;
	public int getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public List<MatchModel> getMatch() {
		return match;
	}
	public void setMatch(List<MatchModel> match) {
		this.match = match;
	}
	public LiveModel(int leagueId, String leagueName, List<MatchModel> match) {
		super();
		this.leagueId = leagueId;
		this.leagueName = leagueName;
		this.match = new ArrayList<MatchModel>();
		this.match.addAll(match);
	}
	
}
