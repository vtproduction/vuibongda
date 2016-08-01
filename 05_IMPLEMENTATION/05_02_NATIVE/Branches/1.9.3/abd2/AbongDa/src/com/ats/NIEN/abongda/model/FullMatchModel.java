package com.ats.NIEN.abongda.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class FullMatchModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _campaign_introduction;
	private String _draw1x2;
	private String _guest;
	private List<Integer> _guestForm;
	private int _guestGoal;
	private String _guestLogo;
	private int _guestPenaltyShoot;
	private String _guestWin1x2;
	private String _guestWinHandicap;
	private int _guest_id;
	private String _handicap;
	private String _home;
	private List<Integer> _homeForm;
	private int _homeGoal;
	private String _homeLogo;
	private int _homePenaltyShoot;
	private String _homeWin1x2;
	private String _homeWinHandicap;
	private int _home_id;
	private List<MatchDetailModel> _info_list;
	private int _isBet;
	private int _isHot;
	private int _league_row_id;
	private List<CauthuModel> _lineup_list;
	private String _matchDatetime;
	private int _matchID;
	private String _matchMinute;
	private String _matchStadium;
	private int _matchStatus;
	private String _tvChannel;
	private List<MatchModel> _matchArray;
	public String get_campaign_introduction() {
		return _campaign_introduction;
	}
	public void set_campaign_introduction(String _campaign_introduction) {
		this._campaign_introduction = _campaign_introduction;
	}
	public String get_draw1x2() {
		return _draw1x2;
	}
	public void set_draw1x2(String _draw1x2) {
		this._draw1x2 = _draw1x2;
	}
	public String get_guest() {
		return _guest;
	}
	public void set_guest(String _guest) {
		this._guest = _guest;
	}
	public List<Integer> get_guestForm() {
		return _guestForm;
	}
	public void set_guestForm(List<Integer> _guestForm) {
		this._guestForm = new ArrayList<Integer>();
		this._guestForm.addAll(_guestForm);
	}
	public int get_guestGoal() {
		return _guestGoal;
	}
	public void set_guestGoal(int _guestGoal) {
		this._guestGoal = _guestGoal;
	}
	public String get_guestLogo() {
		return _guestLogo;
	}
	public void set_guestLogo(String _guestLogo) {
		this._guestLogo = _guestLogo;
	}
	public int get_guestPenaltyShoot() {
		return _guestPenaltyShoot;
	}
	public void set_guestPenaltyShoot(int _guestPenaltyShoot) {
		this._guestPenaltyShoot = _guestPenaltyShoot;
	}
	public String get_guestWin1x2() {
		return _guestWin1x2;
	}
	public void set_guestWin1x2(String _guestWin1x2) {
		this._guestWin1x2 = _guestWin1x2;
	}
	public String get_guestWinHandicap() {
		return _guestWinHandicap;
	}
	public void set_guestWinHandicap(String _guestWinHandicap) {
		this._guestWinHandicap = _guestWinHandicap;
	}
	public int get_guest_id() {
		return _guest_id;
	}
	public void set_guest_id(int _guest_id) {
		this._guest_id = _guest_id;
	}
	public String get_handicap() {
		return _handicap;
	}
	public void set_handicap(String _handicap) {
		this._handicap = _handicap;
	}
	public String get_home() {
		return _home;
	}
	public void set_home(String _home) {
		this._home = _home;
	}
	public List<Integer> get_homeForm() {
		return _homeForm;
	}
	public void set_homeForm(List<Integer> _homeForm) {
		this._homeForm = new ArrayList<Integer>();
		this._homeForm.addAll(_homeForm);
	}
	public int get_homeGoal() {
		return _homeGoal;
	}
	public void set_homeGoal(int _homeGoal) {
		this._homeGoal = _homeGoal;
	}
	public String get_homeLogo() {
		return _homeLogo;
	}
	public void set_homeLogo(String _homeLogo) {
		this._homeLogo = _homeLogo;
	}
	public int get_homePenaltyShoot() {
		return _homePenaltyShoot;
	}
	public void set_homePenaltyShoot(int _homePenaltyShoot) {
		this._homePenaltyShoot = _homePenaltyShoot;
	}
	public String get_homeWin1x2() {
		return _homeWin1x2;
	}
	public void set_homeWin1x2(String _homeWin1x2) {
		this._homeWin1x2 = _homeWin1x2;
	}
	public String get_homeWinHandicap() {
		return _homeWinHandicap;
	}
	public void set_homeWinHandicap(String _homeWinHandicap) {
		this._homeWinHandicap = _homeWinHandicap;
	}
	public int get_home_id() {
		return _home_id;
	}
	public void set_home_id(int _home_id) {
		this._home_id = _home_id;
	}
	public List<MatchDetailModel> get_info_list() {
		return _info_list;
	}
	public void set_info_list(List<MatchDetailModel> _info_list) {
		this._info_list = new ArrayList<MatchDetailModel>();
		this._info_list.addAll(_info_list);
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
	public int get_league_row_id() {
		return _league_row_id;
	}
	public void set_league_row_id(int _league_row_id) {
		this._league_row_id = _league_row_id;
	}
	public List<CauthuModel> get_lineup_list() {
		return _lineup_list;
	}
	public void set_lineup_list(List<CauthuModel> _lineup_list) {
		this._lineup_list = new ArrayList<CauthuModel>();
		this._lineup_list.addAll(_lineup_list);
	}
	public String get_matchDatetime() {
		return _matchDatetime;
	}
	public void set_matchDatetime(String _matchDatetime) {
		this._matchDatetime = _matchDatetime;
	}
	public int get_matchID() {
		return _matchID;
	}
	public void set_matchID(int _matchID) {
		this._matchID = _matchID;
	}
	public String get_matchMinute() {
		return _matchMinute;
	}
	public void set_matchMinute(String _matchMinute) {
		this._matchMinute = _matchMinute;
	}
	public String get_matchStadium() {
		return _matchStadium;
	}
	public void set_matchStadium(String _matchStadium) {
		this._matchStadium = _matchStadium;
	}
	public int get_matchStatus() {
		return _matchStatus;
	}
	public void set_matchStatus(int _matchStatus) {
		this._matchStatus = _matchStatus;
	}
	public String get_tvChannel() {
		return _tvChannel;
	}
	public void set_tvChannel(String _tvChannel) {
		this._tvChannel = _tvChannel;
	}
	public List<MatchModel> get_matchArray() {
		return _matchArray;
	}
	public void set_matchArray(List<MatchModel> _matchArray) {
		this._matchArray = new ArrayList<MatchModel>();
		this._matchArray.addAll(_matchArray);
	}
	public FullMatchModel(String _campaign_introduction, String _draw1x2,
			String _guest, List<Integer> _guestForm, int _guestGoal,
			String _guestLogo, int _guestPenaltyShoot, String _guestWin1x2,
			String _guestWinHandicap, int _guest_id, String _handicap,
			String _home, List<Integer> _homeForm, int _homeGoal,
			String _homeLogo, int _homePenaltyShoot, String _homeWin1x2,
			String _homeWinHandicap, int _home_id,
			List<MatchDetailModel> _info_list, int _isBet, int _isHot,
			int _league_row_id, List<CauthuModel> _lineup_list,
			String _matchDatetime, int _matchID, String _matchMinute,
			String _matchStadium, int _matchStatus, String _tvChannel,
			List<MatchModel> _matchArray) {
		super();
		this._campaign_introduction = _campaign_introduction;
		this._draw1x2 = _draw1x2;
		this._guest = _guest;
		this._guestForm = _guestForm;
		this._guestGoal = _guestGoal;
		this._guestLogo = _guestLogo;
		this._guestPenaltyShoot = _guestPenaltyShoot;
		this._guestWin1x2 = _guestWin1x2;
		this._guestWinHandicap = _guestWinHandicap;
		this._guest_id = _guest_id;
		this._handicap = _handicap;
		this._home = _home;
		this._homeForm = _homeForm;
		this._homeGoal = _homeGoal;
		this._homeLogo = _homeLogo;
		this._homePenaltyShoot = _homePenaltyShoot;
		this._homeWin1x2 = _homeWin1x2;
		this._homeWinHandicap = _homeWinHandicap;
		this._home_id = _home_id;
		this._info_list = _info_list;
		this._isBet = _isBet;
		this._isHot = _isHot;
		this._league_row_id = _league_row_id;
		this._lineup_list = _lineup_list;
		this._matchDatetime = _matchDatetime;
		this._matchID = _matchID;
		this._matchMinute = _matchMinute;
		this._matchStadium = _matchStadium;
		this._matchStatus = _matchStatus;
		this._tvChannel = _tvChannel;
		this._matchArray = _matchArray;
	}
	public FullMatchModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
