package com.ats.NIEN.abongda.model;

public class BetModel {
	public int bet_result;
	public int bet_result_value;
	public String guest_club;
	public int guest_club_goal;
	public int guest_penalty_shoot;
	public String home_club;
	public int home_club_goal;
	public int home_penalty_shoot;
	public int league_row_id;
	public int matchId;
	public int match_status;
	public String register_datetime;
	public int user_bet_value;
	public BetModel(int bet_result, int bet_result_value, String guest_club,
			int guest_club_goal, int guest_penalty_shoot, String home_club,
			int home_club_goal, int home_penalty_shoot, int league_row_id,
			int matchId, int match_status, String register_datetime,
			int user_bet_value) {
		super();
		this.bet_result = bet_result;
		this.bet_result_value = bet_result_value;
		this.guest_club = guest_club;
		this.guest_club_goal = guest_club_goal;
		this.guest_penalty_shoot = guest_penalty_shoot;
		this.home_club = home_club;
		this.home_club_goal = home_club_goal;
		this.home_penalty_shoot = home_penalty_shoot;
		this.league_row_id = league_row_id;
		this.matchId = matchId;
		this.match_status = match_status;
		this.register_datetime = register_datetime;
		this.user_bet_value = user_bet_value;
	}
	
	
	
}
