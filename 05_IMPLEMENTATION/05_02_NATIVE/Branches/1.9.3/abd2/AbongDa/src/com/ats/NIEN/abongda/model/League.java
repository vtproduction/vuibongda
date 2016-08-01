package com.ats.NIEN.abongda.model;


import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class League {
	private int leagueId;
	private String leagueName;
	private int leagueType;

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName() {
		int leagueId = getLeagueId();
		if (leagueId > 0 && leagueId <= 5) {
			switch (getLeagueId()) {
			case 1:
				this.leagueName = "Premier League";

			case 2:
				this.leagueName = "Primera Liga";

			case 3:
				this.leagueName = "Serie A";

			case 4:
				this.leagueName = "Bundesliga";

			case 5:
				this.leagueName = "League 1";
			}
		}
		else if (leagueId > 5 && leagueId <= 23) {
			this.leagueName = "Champion League";
		}
		else if (leagueId > 23 && leagueId <= 46) {
			this.leagueName = "Europa League";
		}
		else if (leagueId > 46 && leagueId <= 62) {
			this.leagueName = "FA";
		}
		else{
			this.leagueName = "Giải đấu khác";
		}
	}
	
	public String getApiLeagueName() {
		int leagueId = getLeagueId();
		if (leagueId > 0 && leagueId <= 5) {
			switch (getLeagueId()) {
			case 1:
				return "English Premier League";

			case 2:
				return "Spanish Primera Liga";

			case 3:
				return "Italian Serie A";

			case 4:
				return "German Bundesliga";

			case 5:
				return "French Ligue 1";
			}
		}
		else if (leagueId > 5 && leagueId <= 23) {
			return "Champion League";
		}
		else if (leagueId > 23 && leagueId <= 46) {
			return "Europa League";
		}
		else if (leagueId > 46 && leagueId <= 62) {
			return "FA Cup";
		}
		else{
			return "Giao hữu quốc tế";
		}
		return "";
	}

	public static String getLeagueName2(int leagueId){
		if (leagueId > 0 && leagueId <= 5) {
			switch (leagueId) {
			case 1:
				return "Premier League";

			case 2:
				return "Primera Liga";

			case 3:
				return "Serie A";

			case 4:
				return "Bundesliga";

			case 5:
				return "League 1";
			}
		}
		else if (leagueId > 5 && leagueId <= 23) {
			return "Champion League";
		}
		else if (leagueId > 23 && leagueId <= 46) {
			return "Europa League";
		}
		else if (leagueId > 46 && leagueId <= 62) {
			return "FA";
		}
		else{
			return "Giải đấu khác";
		}
		return "";
	}
	public int getLeagueType() {
		return leagueType;
	}

	public void setLeagueType(int leagueType) {
		this.leagueType = leagueType;
	}

	public League(int leagueId) {
		super();
		this.leagueId = leagueId;
		setLeagueName();
	}

	
	public League() {
		super();
	}

	public int getTrueLeagueImageResource() {
		if (getLeagueId() > 0 && getLeagueId() <= 5) {
			switch (getLeagueId()) {
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
		return 0;
	}
	

}
