package com.ats.NIEN.abongda.UTIL;

public interface Constant {

	public static final int MENUGROUP_CODE = 332871; 
	public static final int NOACTION_CODE = 445588;
	//code phan Live score
	public static final int LIVESCORE_CODE = 118773;
	public static final int TINTUC_CODE = 524876;
	public static final int NEXT24H_CODE = 635748;
	public static final int GIAITHUONG_CODE = 617028;
	//code phan tai khoan
	public static final int TK_HOSO_CODE = 593000;
	public static final int TK_LICHSU_CODE = 634102;
	public static final int TK_DATCUOC_CODE = 964102;
	public static final int TK_THONGKE_CODE = 451777;
	public static final int TK_HOPTHU_CODE = 451778;
	//code phan league
	public static final int LEAGUE_PL_CODE = 1; //premier league
	public static final int LEAGUE_LFP_CODE = 2; //primera liga
	public static final int LEAGUE_SA_CODE = 3; //serie A
	public static final int LEAGUE_BDL_CODE = 4; //bundesliga
	public static final int LEAGUE_L1_CODE = 5; //League 1
	public static final int LEAGUE_CL_CODE = 6; //champion league
	public static final int LEAGUE_EL_CODE = 24; //europa league
	public static final int LEAGUE_FA_CODE = 47; //europa league
	//code phan bang xep hang
	public static final int TOPDAIGIA_CODE = 112000;
	public static final int TOPCAOTHU_CODE = 113000;
	//code phan thong tin giai dau
	public static final int LICHTHIDAU_CODE = 233198;
	public static final int XEPHANG_CODE = 725186;
	public static final int THONGKE_CODE = 297170;
	
	public static final int CAUTHUFRAGMENT = 532000;
	public static final int CLUBFRAGMENT = 532001;
	public static final int DATCUOCFRAGMENT = 532002;
	public static final int DOIHINHFRAGMENT = 532003;
	public static final int DOITTFRAGMENT = 532004;
	public static final int HOPTHUCHITIETFRAGMENT = 532005;
	public static final int HOPTHUFRAGMENT = 532006;
	public static final int LICHTHIDAUDOIBONGFRAGMENT = 532007;
	public static final int MATCHDETAILFRAGMENT = 532008;
	public static final int TOPCAOTHUFRAGMENT = 532009;
	public static final int TOPDAIGIAFRAGMENT = 532010;
	public static final int TINTUCCHITIETFRAGMENT = 532011;
	public static final int DANGNHAPFRAGMENT = 532012;
	public static final int DANGKYFRAGMENT = 532013;
	public static final int DOIMKFRAGMENT = 532014;
	
	//code phan tab id//
	public static final String TAB_TINTUC = "Tin tức";
	public static final String TAB_LIVE = "Live score";
	public static final String TAB_24H = "24h tới";
	public static final String TAB_TAIKHOAN = "Tài khoản";
	public static final String TAB_LICHTHIDAU = "Lịch thi đấu";
	public static final String TAB_XEPHANG = "Xếp hạng"; //
	public static final String TAB_THONGKE = "Thống kê";
	public static final String TAB_TK_HOSO = "Tài khoản";
	public static final String TAB_TK_LICHSU = "Lịch sử";
	public static final String TAB_TK_DATCUOC = "Đặt cược";
	public static final String TAB_TK_THONGKE = "Thống kê";
	
	//code phan dat cuoc
	public static final int DATCUOC_CODE = 285408;
	//code phan tran dau
	public static final int MATCHDETAIL_CODE = 4286740;
	//code phan tran dau
	//////////////////
	
	public static final String SERVER_APP_ID =  "419677538915";
			
	
	//////////////////////////API LINK//////////////////////////////
	public static final String SERVER_URL = "http://api.bongda.bz:4404/ajaxservice.svc/";
	public static final String URL_TINTUC = SERVER_URL + "getnews?imei=";
	public static final String URL_LIVE = SERVER_URL + "GetLeagueMatchSchedule_v2?imei=";
	public static final String URL_NEXT24H = SERVER_URL + "GetLeagueMatchScheduleInNext24h?imei=[1]&user_row_id=[2]";
	public static final String URL_GETLEAGUESEASON = SERVER_URL + "GetSeason?imei=[replaceimei]&leagueid=[replaceleagueid]";
	public static final String URL_NATIONCUP = 
			SERVER_URL + "GetNationalMatchResult?imei=[1]&season_row_id=[2]&league_row_id=[3]&month=[4]&userId=[5]";
	public static final String URL_CUP = 
			SERVER_URL + "GetCupMatchResult?imei=[1]&season_row_id=[2]&league_row_id=[3]&roundType=[4]&userId=[5]";
	public static final String URL_SEASONSTANDING = 
			SERVER_URL + "GetSeasonStanding?imei=[1]&LeagueId=[2]&SeasonId=[3]";
	public static final String URL_SEASONGROUPSTANDING = 
			SERVER_URL + "GetSeasonGroupStanding?imei=[1]&LeagueId=[2]&SeasonId=[3]";
	public static final String URL_PLAYERLIST = 
			SERVER_URL + "PlayerStatisticBySeason?imei=[1]&SeasonId=[2]&LeagueId=[3]&StatisticType=[4]";
	public static final String URL_CLUBINFOR = 
			SERVER_URL + "GetClubInformation?imei=[1]&clubId=[2]";
	public static final String URL_CLUBTEAM = 
			SERVER_URL + "GetTeamPlayerInformation?imei=[1]&clubId=[2]";
	public static final String URL_CAUTHU = 
			SERVER_URL + "GetPlayerInformation?imei=[1]&playerId=[2]";
	public static final String URL_LTDCLUB =
			SERVER_URL + "GetMatchOfClubBySeason?imei=[1]&season_row_id=[2]&club_row_id=[3]";
	public static final String URL_TOPDAIGIA =
			SERVER_URL + "GetRankingTable?username=[1]";
	public static final String URL_TOPCAOTHU =
			SERVER_URL + "GetTopBetResult?user_id=[1]&filter_type=[2]";
	public static final String URL_SIGNIN = 
			SERVER_URL + "SignInWithGetAccount?identification=[1]&username=[2]&password=[3]";
	public static final String URL_GETUSER = 
			SERVER_URL + "GetUserInfo?user_id=[1]";
	public static final String URL_GETALLMESSAGE = 
			SERVER_URL + "GetUserMessage?username=[1]&start=[2]";
	public static final String URL_SETMESSREADED = 
			SERVER_URL + "UpdateUserMessageReadStatus?id=[1]";
	public static final String URL_LICHSUTK = 
			SERVER_URL + "GetUserAccountLog?username=[1]&number=[2]";
	public static final String URL_BETSTATIC = 
			SERVER_URL + "GetUserBetStatistic?userId=[1]";
	public static final String URL_BETRESULT = 
			SERVER_URL + "GetUserBetResult?userId=[1]&from=[2]";
	public static final String URL_CHANGEPASS = 
			SERVER_URL + "ChangePassword?user_id=[1]&old_password=[2]&new_password=[3]";
	public static final String URL_CHANGEUSERINFO =
			SERVER_URL + "ChangeUserInfo_12?user_id=[1]&fullname=[2]&phone=[4]&address=[3]&email=[5]";
	public static final String URL_GETMATCHDETAIL = 
			SERVER_URL + "GetAllMatchInformation?imei=[1]&matchId=[2]";
	public static final String URL_BET = 
			SERVER_URL + "SaveUserBetWithDetailResult?MatchId=[1]&RegistrationId=[2]&UserId=[3]&BetType=[4]&BetValue=[5]&UserBetString=[6]&Handicap=[7]";
	public static final String URL_BET2 = 
			SERVER_URL + "SaveUserBetWithDetailResult?MatchId=[1]&RegistrationId=[2]&UserId=[3]&BetType=[4]&BetValue=[5]&UserBetString=[6]&Handicap=[7]&BetHomeGoal=[8]&BetGuestGoal=[9]&SameUserBet=[10]";
	public static final String URL_RECEIVENOTIF = 
			SERVER_URL + "SaveNotificationRequest?RegistrationId=[1]&MatchId=[2]";
	public static final String URL_GENERALCONFIG = 
			SERVER_URL + "GetGeneralConfiguration?partner=";
	public static final String URL_SEND_REG_ID = 
			SERVER_URL + "RegisterRegistrationID?imei=[1]&regId=[2]";
	public static final String URL_GET_BET_RESULT = 
			SERVER_URL + "GetUserBetResult?userId=[1]&from=[2]";
	public static final String URL_VERIFY_EMAIL =
			SERVER_URL + "VerifyEmail?userId=[1]&userEmail=[2]";
	public static final String URL_REG_USER = 
			SERVER_URL + "SignUp?identification=[1]&username=[2]&password=[3]&is_signup=0";
}
