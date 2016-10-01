package ats.abongda.model;

/**
 * Created by NienLe on 13-Aug-16.
 */
public class UserBet {
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

    public int getBet_result() {
        return bet_result;
    }

    public void setBet_result(int bet_result) {
        this.bet_result = bet_result;
    }

    public int getBet_result_value() {
        return bet_result_value;
    }

    public void setBet_result_value(int bet_result_value) {
        this.bet_result_value = bet_result_value;
    }

    public String getGuest_club() {
        return guest_club;
    }

    public void setGuest_club(String guest_club) {
        this.guest_club = guest_club;
    }

    public int getGuest_club_goal() {
        return guest_club_goal;
    }

    public void setGuest_club_goal(int guest_club_goal) {
        this.guest_club_goal = guest_club_goal;
    }

    public int getGuest_penalty_shoot() {
        return guest_penalty_shoot;
    }

    public void setGuest_penalty_shoot(int guest_penalty_shoot) {
        this.guest_penalty_shoot = guest_penalty_shoot;
    }

    public String getHome_club() {
        return home_club;
    }

    public void setHome_club(String home_club) {
        this.home_club = home_club;
    }

    public int getHome_club_goal() {
        return home_club_goal;
    }

    public void setHome_club_goal(int home_club_goal) {
        this.home_club_goal = home_club_goal;
    }

    public int getHome_penalty_shoot() {
        return home_penalty_shoot;
    }

    public void setHome_penalty_shoot(int home_penalty_shoot) {
        this.home_penalty_shoot = home_penalty_shoot;
    }

    public int getLeague_row_id() {
        return league_row_id;
    }

    public void setLeague_row_id(int league_row_id) {
        this.league_row_id = league_row_id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getMatch_status() {
        return match_status;
    }

    public void setMatch_status(int match_status) {
        this.match_status = match_status;
    }

    public String getRegister_datetime() {
        return register_datetime;
    }

    public void setRegister_datetime(String register_datetime) {
        this.register_datetime = register_datetime;
    }

    public int getUser_bet_value() {
        return user_bet_value;
    }

    public void setUser_bet_value(int user_bet_value) {
        this.user_bet_value = user_bet_value;
    }
}
