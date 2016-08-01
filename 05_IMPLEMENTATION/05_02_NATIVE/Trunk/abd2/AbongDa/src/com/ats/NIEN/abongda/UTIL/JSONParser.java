package com.ats.NIEN.abongda.UTIL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ats.NIEN.abongda.UTIL.NetworkUtil;
import com.ats.NIEN.abongda.model.BetModel;
import com.ats.NIEN.abongda.model.CauthuModel;
import com.ats.NIEN.abongda.model.ClubInforModel;
import com.ats.NIEN.abongda.model.ClubModel;
import com.ats.NIEN.abongda.model.FullMatchModel;
import com.ats.NIEN.abongda.model.GroupStandingModel;
import com.ats.NIEN.abongda.model.HopthuModel;
import com.ats.NIEN.abongda.model.LeagueSeason;
import com.ats.NIEN.abongda.model.LiveModel;
import com.ats.NIEN.abongda.model.MatchDetailModel;
import com.ats.NIEN.abongda.model.MatchModel;
import com.ats.NIEN.abongda.model.MonthData;
import com.ats.NIEN.abongda.model.TK_LichsuModel;
import com.ats.NIEN.abongda.model.TintucModel;
import com.ats.NIEN.abongda.model.UpdateAppModel;
import com.ats.NIEN.abongda.model.UserModel;

public class JSONParser {
	private Context context;
	 public SharedPreferences sPreferences;
	 public String deviceId,regId;
	 public NetworkUtil mNetworkUtil;
	public JSONParser(Context context) {
		super();
		this.setContext(context);
		sPreferences = context.getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		this.deviceId = sPreferences.getString("DEVICE_ID", "");
		this.regId = sPreferences.getString("registration_id", "");
		this.mNetworkUtil = new NetworkUtil();
	}

	public JSONParser() {
		super();
	}

	public String getRawResult(String url, String[] args){
		String result = "";
		for (int i = 0; i < args.length; i++) {
			url = url.replace("["+(i+1)+"]", args[i]);
		}
		url = url.replaceAll(" ", "%20");
		result = mNetworkUtil.doGetRequest(url);
		return result;
	}
	
	
	public List<TintucModel> parseTintuc(String deviceimei) {
		List<TintucModel> list = new ArrayList<TintucModel>();
		
		String result = mNetworkUtil.doGetRequest(Constant.URL_TINTUC + this.deviceId);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				TintucModel tintuc = new TintucModel(obj.getString("Content"),
						obj.getString("Description"), Integer.parseInt(obj
								.getString("Id")), obj.getString("ImageLink"),
						Integer.parseInt(obj.getString("IsHot")),
						obj.getString("Title"));
				list.add(tintuc);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice",
				"parseTintuc " + Constant.URL_TINTUC + " : " + list.size() + "");
		return list;
	}

	public List<MatchModel> parseNext24List(int userId) {
		List<MatchModel> list = new ArrayList<MatchModel>();
		
		String url = Constant.URL_NEXT24H.replace("[1]", this.deviceId)
				.replace("[2]", ""+userId);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject matchObj = jsonArray.getJSONObject(i);
				MatchModel model = new MatchModel(matchObj.getString("_home"),
						matchObj.getString("_guest"),
						matchObj.getInt("_homeGoal"),
						matchObj.getInt("_guestGoal"),
						matchObj.getInt("_homePenaltyShoot"),
						matchObj.getInt("_guestPenaltyShoot"),
						matchObj.getString("_matchMinute"),
						matchObj.getInt("_matchStatus"),
						matchObj.getInt("_matchID"),
						matchObj.getString("_matchDatetime"),
						matchObj.getInt("_home_id"),
						matchObj.getInt("_guest_id"),
						matchObj.getInt("_isBet"), matchObj.getInt("_isHot"));
				model.setLeagueRowId(matchObj.getInt("_league_row_id"));
				model.setTvChanel(matchObj.getString("_tvChannel"));
				list.add(model);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseNext24List " + url + " : "
				+ list.size() + "");
		return list;
	}

	public List<ClubModel> parseSeasonStandingList(String deviceImei,
			int leagueId, int seasonId) {
		List<ClubModel> list = new ArrayList<ClubModel>();
		
		String url = Constant.URL_SEASONSTANDING.replace("[1]", this.deviceId)
				.replace("[2]", "" + leagueId).replace("[3]", "" + seasonId);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				ClubModel club = new ClubModel(obj.getInt("_club_id"),
						obj.getString("_club_name"),
						obj.getInt("_goal_win_lose"),
						obj.getInt("_guest_draw"),
						obj.getInt("_guest_goal_lose"),
						obj.getInt("_guest_goal_win"),
						obj.getInt("_guest_lose"), obj.getInt("_guest_win"),
						obj.getInt("_home_draw"),
						obj.getInt("_home_goal_lose"),
						obj.getInt("_home_goal_win"), obj.getInt("_home_lose"),
						obj.getInt("_home_win"), obj.getString("_logo"),
						obj.getInt("_mark"), obj.getInt("_total_match"));
				list.add(club);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice",
				"parseSeasonStandingList " + url + " : " + list.size() + "");
		return list;
	}

	public List<MatchModel> parseNationLeague(String deviceImei,
			int seasonRowId, int leagueRowId, int userId, int month) {
		List<MatchModel> list = new ArrayList<MatchModel>();
		
		String url = Constant.URL_NATIONCUP.replace("[1]", this.deviceId)
				.replace("[2]", "" + seasonRowId)
				.replace("[3]", "" + leagueRowId).replace("[4]", "" + month)
				.replace("[5]", "" + userId);

		String result = mNetworkUtil.doGetRequest(url);

		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject matchObj = jsonArray.getJSONObject(j);
				MatchModel mMatchModel = new MatchModel(
						matchObj.getString("_home"),
						matchObj.getString("_guest"),
						matchObj.getInt("_homeGoal"),
						matchObj.getInt("_guestGoal"),
						matchObj.getInt("_homePenaltyShoot"),
						matchObj.getInt("_guestPenaltyShoot"),
						matchObj.getString("_matchMinute"),
						matchObj.getInt("_matchStatus"),
						matchObj.getInt("_matchID"),
						matchObj.getString("_matchDatetime"),
						matchObj.getInt("_home_id"),
						matchObj.getInt("_guest_id"),
						matchObj.getInt("_isBet"), matchObj.getInt("_isHot"));
				list.add(mMatchModel);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseNationLeague " + url + " : " + list.size()
				+ "");
		return list;
	}

	public List<MatchModel> parseCupLeague(String deviceImei, int seasonRowId,
			int leagueRowId, int userId, int roundType) {
		List<MatchModel> list = new ArrayList<MatchModel>();
		
		String url = Constant.URL_CUP.replace("[1]", this.deviceId)
				.replace("[2]", "" + seasonRowId)
				.replace("[3]", "" + leagueRowId)
				.replace("[4]", "" + roundType).replace("[5]", "" + userId);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject matchObj = jsonArray.getJSONObject(j);
				MatchModel mMatchModel = new MatchModel(
						matchObj.getString("_home"),
						matchObj.getString("_guest"),
						matchObj.getInt("_homeGoal"),
						matchObj.getInt("_guestGoal"),
						matchObj.getInt("_homePenaltyShoot"),
						matchObj.getInt("_guestPenaltyShoot"),
						matchObj.getString("_matchMinute"),
						matchObj.getInt("_matchStatus"),
						matchObj.getInt("_matchID"),
						matchObj.getString("_matchDatetime"),
						matchObj.getInt("_home_id"),
						matchObj.getInt("_guest_id"),
						matchObj.getInt("_isBet"), matchObj.getInt("_isHot"));
				list.add(mMatchModel);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseCupLeague " + url + " : " + list.size() + "");
		return list;
	}

	public List<LeagueSeason> parseLeagueSeason(String deviceImei, int leagueId) {
		List<LeagueSeason> list = new ArrayList<LeagueSeason>();
		
		String url = Constant.URL_GETLEAGUESEASON.replace("[replaceimei]",
				this.deviceId).replace("[replaceleagueid]", "" + leagueId);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		JSONArray monthDataArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				monthDataArray = new JSONArray(obj.getString("MonthData"));
				List<MonthData> monthDataList = new ArrayList<MonthData>();
				for (int j = 0; j < monthDataArray.length(); j++) {
					JSONObject monthDataObj = monthDataArray.getJSONObject(j);

					MonthData monthData = new MonthData(
							monthDataObj.getInt("IsCurrent"),
							monthDataObj.getInt("Month"),
							monthDataObj.getString("Name"),
							monthDataObj.getInt("Year"));
					monthDataList.add(monthData);
				}
				LeagueSeason ls = new LeagueSeason(obj.getInt("IsCurrent"),
						monthDataList, obj.getInt("row_id"),
						obj.getString("season_name"));
				list.add(ls);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseLeagueSeason " + url + " : " + list.size()
				+ "");
		return list;
	}

	public List<LeagueSeason> parseLeagueSeasonWithoutMonth(String deviceImei,
			int leagueId) {
		List<LeagueSeason> list = new ArrayList<LeagueSeason>();
		
		String url = Constant.URL_GETLEAGUESEASON.replace("[replaceimei]",
				this.deviceId).replace("[replaceleagueid]", "" + leagueId);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				LeagueSeason ls = new LeagueSeason(obj.getInt("IsCurrent"),

				obj.getInt("row_id"), obj.getString("season_name"));
				list.add(ls);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseLeagueSeasonWithoutMonth " + url + " : "
				+ list.size() + "");
		return list;
	}
	
	public List<LeagueSeason> parseLeagueSeasonWithoutMonth_V2(String result) {
		List<LeagueSeason> list = new ArrayList<LeagueSeason>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				LeagueSeason ls = new LeagueSeason(
						obj.getInt("isCurrent"),
						obj.getInt("rowId"), 
						obj.getString("seasonName"));
				list.add(ls);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseLeagueSeasonWithoutMonth_v2 " + " : "
				+ list.size() + " : ");
		return list;
	}
	
	
	
	

	public List<LiveModel> parseLiveList(String deviceIds) {
		List<LiveModel> list = new ArrayList<LiveModel>();
		
		String result = mNetworkUtil.doGetRequest(Constant.URL_LIVE + this.deviceId);
		JSONArray jsonArray;
		JSONArray matchArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				matchArray = new JSONArray(obj.getString("_matches"));
				List<MatchModel> matchList = new ArrayList<MatchModel>();
				for (int j = 0; j < matchArray.length(); j++) {
					JSONObject matchObj = matchArray.getJSONObject(j);
					MatchModel mMatchModel = new MatchModel(
							matchObj.getString("_home"),
							matchObj.getString("_guest"),
							matchObj.getInt("_homeGoal"),
							matchObj.getInt("_guestGoal"),
							matchObj.getInt("_homePenaltyShoot"),
							matchObj.getInt("_guestPenaltyShoot"),
							matchObj.getString("_matchMinute"),
							matchObj.getInt("_matchStatus"),
							matchObj.getInt("_matchID"),
							matchObj.getString("_matchDatetime"),
							matchObj.getInt("_home_id"),
							matchObj.getInt("_guest_id"),
							matchObj.getInt("_isBet"),
							matchObj.getInt("_isHot"));
					matchList.add(mMatchModel);
				}
				LiveModel mLiveModel = new LiveModel(obj.getInt("_leagueID"),
						obj.getString("_leagueName"), matchList);
				list.add(mLiveModel);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice",
				"parseLiveList " + Constant.URL_LIVE + " : " + list.size() + "");
		return list;
	}

	public List<CauthuModel> parsePlayerList(String deviceImei, int seasonId,
			int leagueId, int statisticType) {
		List<CauthuModel> list = new ArrayList<CauthuModel>();
		String url = Constant.URL_PLAYERLIST.replace("[1]", this.deviceId)
				.replace("[2]", seasonId + "").replace("[3]", leagueId + "")
				.replace("[4]", statisticType + "");
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				CauthuModel model = new CauthuModel(obj.getString("ClubLogo"),
						obj.getInt("Goal"), obj.getString("Name"));
				list.add(model);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parsePlayerList " + url + " : " + list.size() + "");
		return list;
	}

	public ClubInforModel parseClubInfor(String deviceImei, int clubId) {
		ClubInforModel model = new ClubInforModel();
		String url = Constant.URL_CLUBINFOR.replace("[1]", this.deviceId).replace(
				"[2]", "" + clubId);
		
		String result = mNetworkUtil.doGetRequest(url);
		List<TintucModel> listTintuc = new ArrayList<TintucModel>();

		try {

			JSONObject obj = new JSONObject(result);
			listTintuc = new ArrayList<TintucModel>();
			JSONArray listTintucJsonArray = new JSONArray(obj.getString("News"));
			for (int j = 0; j < listTintucJsonArray.length(); j++) {
				JSONObject tintucObj = listTintucJsonArray.getJSONObject(j);
				TintucModel tintuc = new TintucModel(
						tintucObj.getString("Content"),
						tintucObj.getString("Description"),
						Integer.parseInt(tintucObj.getString("Id")),
						tintucObj.getString("ImageLink"),
						Integer.parseInt(tintucObj.getString("IsHot")),
						tintucObj.getString("Title"));
				listTintuc.add(tintuc);
			}
			model = new ClubInforModel(obj.getString("Address"),
					obj.getInt("Capacity"), obj.getString("Email"),
					obj.getInt("Founded"), obj.getString("Honours"),
					obj.getInt("Id"), obj.getString("Logo"),
					obj.getString("Manager"), obj.getString("Name"),
					listTintuc, obj.getString("Phone"),
					obj.getString("ShortName"), obj.getString("Stadium"),
					obj.getString("Web"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseClubInfor " + url + " : ");
		return model;
	}

	public List<CauthuModel> parseClubTeam(String deviceImei, int clubId) {
		List<CauthuModel> list = new ArrayList<CauthuModel>();
		String url = Constant.URL_CLUBTEAM.replace("[1]", this.deviceId).replace(
				"[2]", "" + clubId);
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				CauthuModel model = new CauthuModel(obj.getInt("Id"),
						obj.getString("Image"), obj.getString("Name"),
						obj.getString("Position"));
				list.add(model);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("webservice", "parseClubTeam " + url + " : " + list.size() + "");
		return list;
	}

	public CauthuModel parseCauthu(String deviceImei, int cauthuId) {
		CauthuModel model = new CauthuModel();
		String url = Constant.URL_CAUTHU.replace("[1]", this.deviceId).replace(
				"[2]", cauthuId + "");
		
		String result = mNetworkUtil.doGetRequest(url);
		try {
			JSONObject obj = new JSONObject(result);
			List<TintucModel> news = new ArrayList<TintucModel>();
			JSONArray newsArray = new JSONArray(obj.getString("News"));
			for (int i = 0; i < newsArray.length(); i++) {
				JSONObject newsObj = newsArray.getJSONObject(i);
				news.add(new TintucModel(newsObj.getString("Content"), newsObj
						.getString("Description"), newsObj.getInt("Id"),
						newsObj.getString("ImageLink"),
						newsObj.getInt("IsHot"), newsObj.getString("Title")));
			}
			model = new CauthuModel(obj.getString("Birthday"),
					obj.getInt("ClubId"), obj.getString("ClubLogo"),
					obj.getString("DateOfBirth"), obj.getInt("Goal"),
					obj.getString("Height"), obj.getInt("Id"),
					obj.getString("Image"), obj.getInt("Match"),
					obj.getString("Name"), obj.getString("Nationality"), news,
					obj.getInt("Number"), obj.getString("Position"),
					obj.getInt("RedCard"), obj.getString("Weight"),
					obj.getInt("YellowCard"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("webservice", "parseCauthu " + url + " : ");
		return model;
	}

	public List<MatchModel> parseMatchListByClub(String deviceImei,
			int seasonId, int clubId) {
		List<MatchModel> list = new ArrayList<MatchModel>();
		String url = Constant.URL_LTDCLUB.replace("[1]", this.deviceId)
				.replace("[2]", seasonId + "").replace("[3]", clubId + "");
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject matchObj = jsonArray.getJSONObject(j);
				MatchModel mMatchModel = new MatchModel(
						matchObj.getString("_home"),
						matchObj.getString("_guest"),
						matchObj.getInt("_homeGoal"),
						matchObj.getInt("_guestGoal"),
						matchObj.getInt("_homePenaltyShoot"),
						matchObj.getInt("_guestPenaltyShoot"),
						matchObj.getString("_matchMinute"),
						matchObj.getInt("_matchStatus"),
						matchObj.getInt("_matchID"),
						matchObj.getString("_matchDatetime"),
						matchObj.getInt("_home_id"),
						matchObj.getInt("_guest_id"),
						matchObj.getInt("_isBet"), matchObj.getInt("_isHot"), matchObj.getInt("_league_row_id"));
				list.add(mMatchModel);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseMatchListByClub " + url + " : " + list.size()
				+ "");
		return list;
	}

	public List<GroupStandingModel> parseGroupStanding(String deviceImei,
			int leagueId, int SeasonId) {
		List<GroupStandingModel> list = new ArrayList<GroupStandingModel>();
		String url = Constant.URL_SEASONGROUPSTANDING
				.replace("[1]", this.deviceId).replace("[2]", "" + leagueId)
				.replace("[3]", "" + SeasonId);
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		List<ClubModel> clubList = new ArrayList<ClubModel>();
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject mObj = jsonArray.getJSONObject(i);
				JSONArray clubArray = new JSONArray(
						mObj.getString("_LStanding"));
				clubList = new ArrayList<ClubModel>();
				for (int j = 0; j < clubArray.length(); j++) {
					JSONObject obj = clubArray.getJSONObject(j);
					ClubModel club = new ClubModel(obj.getInt("_club_id"),
							obj.getString("_club_name"),
							obj.getInt("_goal_win_lose"),
							obj.getInt("_guest_draw"),
							obj.getInt("_guest_goal_lose"),
							obj.getInt("_guest_goal_win"),
							obj.getInt("_guest_lose"),
							obj.getInt("_guest_win"), obj.getInt("_home_draw"),
							obj.getInt("_home_goal_lose"),
							obj.getInt("_home_goal_win"),
							obj.getInt("_home_lose"), obj.getInt("_home_win"),
							obj.getString("_logo"), obj.getInt("_mark"),
							obj.getInt("_total_match"));
					clubList.add(club);
				}
				list.add(new GroupStandingModel(clubList, mObj
						.getString("_League_Name")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseGroupStanding " + url + " : " + list.size()
				+ "");
		return list;
	}
	
	public List<GroupStandingModel> parseGroupStanding_v2(String result) {
		List<GroupStandingModel> list = new ArrayList<GroupStandingModel>();
		JSONArray jsonArray;
		List<ClubModel> clubList = new ArrayList<ClubModel>();
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject mObj = jsonArray.getJSONObject(i);
				JSONArray clubArray = new JSONArray(
						mObj.getString("_LStanding"));
				clubList = new ArrayList<ClubModel>();
				for (int j = 0; j < clubArray.length(); j++) {
					JSONObject obj = clubArray.getJSONObject(j);
					ClubModel club = new ClubModel(obj.getInt("_club_id"),
							obj.getString("_club_name"),
							obj.getInt("_goal_win_lose"),
							obj.getInt("_guest_draw"),
							obj.getInt("_guest_goal_lose"),
							obj.getInt("_guest_goal_win"),
							obj.getInt("_guest_lose"),
							obj.getInt("_guest_win"), obj.getInt("_home_draw"),
							obj.getInt("_home_goal_lose"),
							obj.getInt("_home_goal_win"),
							obj.getInt("_home_lose"), obj.getInt("_home_win"),
							obj.getString("_logo"), obj.getInt("_mark"),
							obj.getInt("_total_match"));
					clubList.add(club);
				}
				list.add(new GroupStandingModel(clubList, mObj
						.getString("_League_Name")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseGroupStanding2 "
				+ "");
		return list;
	}

	public List<UserModel> parseTopdaigia(String username) {
		List<UserModel> list = new ArrayList<UserModel>();
		String url = Constant.URL_TOPDAIGIA.replace("[1]", username);
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.add(new UserModel(obj.getLong("Account"), obj
						.getString("Name"), obj.getInt("Rank")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseTopdaigia " + url + " : " + list.size() + "");
		return list;
	}

	public List<UserModel> parseTopcaothu(int userId, int type) {
		List<UserModel> list = new ArrayList<UserModel>();
		String url = Constant.URL_TOPCAOTHU.replace("[1]", "" + userId)
				.replace("[2]", "" + type);
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.add(new UserModel(obj.getInt("DifferenceMatchScore"), obj
						.getInt("LostMatchScore"), obj.getString("Name"), obj
						.getInt("WonMatchScore"), obj.getInt("Rank")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseTopcaothu " + url + " : " + list.size() + "");
		return list;
	}

	public int performSignin(String identical, String username,
			String userPassword) {
		int userId = 0;
		String url = Constant.URL_SIGNIN.replace("[1]", this.deviceId)
				.replace("[2]", "" + username).replace("[3]", userPassword);
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONObject o;
		try {
			o = new JSONObject(result);
			/*
			 * model = new UserModel( o.getLong("Account"),
			 * o.getString("Address"), o.getString("Avatar"),
			 * o.getString("BetResult"), o.getInt("ClientId"),
			 * o.getInt("DifferenceMatchScore"), o.getString("Email"),
			 * o.getString("Fullname"), o.getInt("Id"),
			 * o.getInt("IsEmailVerified"), o.getInt("IsPhoneVerified"),
			 * o.getInt("LostMatchScore"), o.getString("Name"),
			 * o.getString("Password"), o.getString("Phone") );
			 */
			// model = new UserModel(o.getInt("Id"));
			userId = o.getInt("Id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "performSignin " + url + " : " + userId + "");
		return userId;
	}

	public UserModel getUser(int userId) {
		String url = Constant.URL_GETUSER.replace("[1]", "" + userId);
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONObject o;
		UserModel model = new UserModel();
		try {
			o = new JSONObject(result);
			model = new UserModel(o.getLong("Account"), o.getString("Address"),
					o.getString("Avatar"), o.getString("BetResult"),
					o.getInt("ClientId"), o.getInt("DifferenceMatchScore"),
					o.getString("Email"), o.getString("Fullname"),
					o.getInt("Id"), o.getInt("IsEmailVerified"),
					o.getInt("IsPhoneVerified"), o.getInt("LostMatchScore"),
					o.getString("Name"), o.getString("Password"),
					o.getString("Phone"), o.getInt("Rank"),
					o.getString("RegisterDateTime"), o.getInt("Status"),
					o.getInt("TotalCoinScore"), o.getInt("TotalMatchScore"),
					o.getLong("WonCoinScore"), o.getInt("WonMatchScore"));
			// model = new UserModel(o.getInt("Id"));
			userId = o.getInt("Id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "getUser " + url + " : " + model.getName() + "");
		return model;
	}

	public List<HopthuModel> parseAllMessage(String userName, int startAt) {
		List<HopthuModel> list = new ArrayList<HopthuModel>();
		
		String url = Constant.URL_GETALLMESSAGE.replace("[1]", userName)
				.replace("[2]", "" + startAt);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				HopthuModel model = new HopthuModel(obj.getLong("id"),
						obj.getString("message"),
						obj.getString("registerDatetime"),
						obj.getInt("status"), obj.getString("title"));
				list.add(model);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseAllMessage " + url + " : " + list.size() + "");
		return list;
	}

	public void setMessReaded(String messId) {
		
		String url = Constant.URL_SETMESSREADED.replace("[1]", messId);
		mNetworkUtil.doGetRequest(url);
		Log.d("webservice", "setMessReaded " + url);
	}

	public List<TK_LichsuModel> parseAccHistoryLog(String userName, int number) {
		List<TK_LichsuModel> list = new ArrayList<TK_LichsuModel>();
		
		String url = Constant.URL_LICHSUTK.replace("[1]", userName).replace(
				"[2]", "" + number);
		String result = mNetworkUtil.doGetRequest(url);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				TK_LichsuModel model = new TK_LichsuModel(
						obj.getLong("afterChange"),
						obj.getLong("beforeChange"), obj.getLong("change"),
						obj.getString("description"), obj.getInt("logType"),
						obj.getString("registerDatetime"));
				list.add(model);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseAccHistoryLog " + url + " : " + list.size()
				+ "");
		return list;
	}

	public UserModel parseBetStatic(String userId) {
		UserModel model = new UserModel();
		
		String url = Constant.URL_BETSTATIC.replace("[1]", userId);
		String result = mNetworkUtil.doGetRequest(url);
		try {
			JSONObject obj = new JSONObject(result);
			model = new UserModel(obj.getInt("TotalCoinScore"),
					obj.getInt("TotalMatchScore"), obj.getLong("WonCoinScore"),
					obj.getInt("WonMatchScore"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "parseAccHistoryLog " + url + " : ");
		return model;
	}

	public boolean changeUserPassword(String userId, String oldPassword,
			String newPassword) {
		String url = Constant.URL_CHANGEPASS.replace("[1]", userId)
				.replace("[2]", oldPassword).replace("[3]", newPassword);
		
		String result = mNetworkUtil.doGetRequest(url);
		Log.d("webservice", "changeUserPassword " + url + " : " + result);
		if (result.equalsIgnoreCase(userId)) {
			return true;
		}

		return false;
	}

	public boolean changeUserInfo(String userId, String fullName,
			String Address, String Phone, String Email) {
		String url = Constant.URL_CHANGEUSERINFO.replace("[1]", userId)
				.replace("[2]", fullName.replace(" ", "%20"))
				.replace("[3]", Address.replace(" ", "%20"))
				.replace("[4]", Phone.replace(" ", "%20"))
				.replace("[5]", Email.replace(" ", "%20"));
		
		String result = mNetworkUtil.doGetRequest(url);
		if (result.equals("-1")) {
			return false;
		}
		return true;
	}

	private List<MatchDetailModel> createMatchDetail(String s) {
		List<MatchDetailModel> models = new ArrayList<MatchDetailModel>();
		try {
			JSONArray a = new JSONArray(s);
			for (int i = 0; i < a.length(); i++) {
				JSONObject o = a.getJSONObject(i);
				MatchDetailModel m = new MatchDetailModel(o.getString("Extra"),
						o.getString("GuestInfo"), o.getString("HomeInfo"),
						o.getInt("Id"), o.getString("Score"),
						o.getString("Status"), o.getString("Type"));
				models.add(m);
			}

			return models;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return models;
	}

	private List<Integer> createClubForm(String s) {
		List<Integer> l = new ArrayList<Integer>();
		try {
			JSONArray a = new JSONArray(s);
			for (int i = 0; i < a.length(); i++) {
				l.add(Integer.parseInt(a.get(i).toString()));
			}

			return l;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l;
	}

	private List<CauthuModel> createLineUpList(String s) {
		List<CauthuModel> l = new ArrayList<CauthuModel>();
		try {
			JSONArray a = new JSONArray(s);
			for (int i = 0; i < a.length(); i++) {
				JSONObject o = a.getJSONObject(i);
				CauthuModel m = new CauthuModel(o.getInt("ClubId"),
						o.getInt("Goal"), o.getString("Name"),
						o.getInt("Number"), o.getInt("RedCard"),
						o.getInt("YellowCard"), o.getInt("Role"),
						o.getInt("TimeIn"), o.getInt("TimeOut"));
				l.add(m);

			}

			return l;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l;
	}

	private MatchModel createHistoryMatch(JSONObject o) {
		MatchModel m = new MatchModel();
		try {
			m = new MatchModel(o.getInt("_matchID"), o.getString("_home"), o.getString("_guest"),
					o.getInt("_homeGoal"), o.getInt("_guestGoal"),
					o.getInt("_homePenaltyShoot"),
					o.getInt("_guestPenaltyShoot"),
					o.getString("_matchDatetime"), o.getInt("_league_row_id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}

	public FullMatchModel getMatchInfo(String deviceId, int matchId) {
		FullMatchModel model = new FullMatchModel();

		String url = Constant.URL_GETMATCHDETAIL.replace("[1]", this.deviceId)
				.replace("[2]", matchId + "");
		
		String result = mNetworkUtil.doGetRequest(url);

		try {
			JSONArray a = new JSONArray(result);
			JSONObject obj = a.getJSONObject(0);
			List<MatchDetailModel> infoList = createMatchDetail(obj
					.getString("_info_list"));
			List<Integer> guestForm = createClubForm(obj
					.getString("_guestForm"));
			List<Integer> homeForm = createClubForm(obj.getString("_homeForm"));
			List<CauthuModel> lineupList = createLineUpList(obj
					.getString("_lineup_list"));
			List<MatchModel> matchList = new ArrayList<MatchModel>();
			for (int i = 1; i < a.length(); i++) {
				matchList.add(createHistoryMatch(a.getJSONObject(i)));
			}
			
			model = new FullMatchModel(
					obj.getString("_campaign_introduction"),
					obj.getString("_draw1x2"),
					obj.getString("_guest"),
					guestForm,
					obj.getInt("_guestGoal"),
					obj.getString("_guestLogo"),
					obj.getInt("_guestPenaltyShoot"),
					obj.getString("_guestWin1x2"),
					obj.getString("_guestWinHandicap"),
					obj.getInt("_guest_id"),
					obj.getString("_handicap"),
					obj.getString("_home"),
					homeForm,
					obj.getInt("_homeGoal"),
					obj.getString("_homeLogo"),
					obj.getInt("_homePenaltyShoot"),
					obj.getString("_homeWin1x2"),
					obj.getString("_homeWinHandicap"),
					obj.getInt("_home_id"),
					infoList,
					obj.getInt("_isBet"),
					obj.getInt("_isHot"),
					obj.getInt("_league_row_id"),
					lineupList,
					obj.getString("_matchDatetime"),
					obj.getInt("_matchID"),
					obj.getString("_matchMinute"),
					obj.getString("_matchStadium"),
					obj.getInt("_matchStatus"),
					obj.getString("_tvChannel"),
					matchList);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "getMatchDetail:  " + url + " : ");
		return model;
	}
	
	public String performBet(String arg1,String arg2,String arg3,String arg4,String arg5,String arg6,String arg7){
		String url = Constant.URL_BET
				.replace("[1]", arg1)
				.replace("[2]", arg2)
				.replace("[3]", arg3)
				.replace("[4]", arg4)
				.replace("[5]", arg5)
				.replace("[6]", arg6)
				.replace("[7]", arg7)
				.replace(" ", "%20");
		
		String result = mNetworkUtil.doGetRequest(url);
		Log.d("webservice", "BET:  " + url + " : ");
		return result;
	}
	
	public String performBet2(String arg1,String arg2,String arg3,
			String arg4,String arg5,String arg6,String arg7, String arg8, String arg9, String arg10){
		String url = Constant.URL_BET2
				.replace("[1]", arg1)
				.replace("[2]", arg2)
				.replace("[3]", arg3)
				.replace("[4]", arg4)
				.replace("[5]", arg5)
				.replace("[6]", arg6)
				.replace("[7]", arg7)
				.replace("[8]", arg8)
				.replace("[9]", arg9)
				.replace("[10]", arg10)
				.replace(" ", "%20");
		
		String result = mNetworkUtil.doGetRequest(url);
		Log.d("webservice", "BET:  " + url + " : ");
		return result;
	}
	
	public String performNotif(String arg1,String arg2){
		String url = Constant.URL_RECEIVENOTIF
				.replace("[1]", arg1)
				.replace("[2]", arg2)
				.replace(" ", "%20");
		
		String result = mNetworkUtil.doGetRequest(url);
		Log.d("webservice", "RECEIVE NOTIF:  " + url + " : ");
		return result;
	}
	
	
	public JSONObject getGeneralConfig(String partner){
		String url = Constant.URL_GENERALCONFIG + partner;
		
		String result = mNetworkUtil.doGetRequest(url);
		JSONObject o = null;
		try {
			o = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "BET2:  " + url + " : ");
		return o;
	}
	
	public String getGeneralConfigString(String partner){
		String url = Constant.URL_GENERALCONFIG + partner;
		
		String result = mNetworkUtil.doGetRequest(url);
		return result;
	}
	
	public UserModel performSignUp(String userName, String password){
		String url = Constant.URL_REG_USER
				.replace("[1]", deviceId)
				.replace("[2]", userName)
				.replace("[3]", password);
		UserModel um = new UserModel();
		String result = mNetworkUtil.doGetRequest(url);
		JSONObject o = null;
		try {
			o = new JSONObject(result);
			um.setId(o.getInt("Id"));
			um.setAccount(o.getInt("Account"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "performSignUp:  " + url + " : " + um.getId());
		return um;
	}
	
	public void sendRegId(){
		String url = Constant.URL_SEND_REG_ID.replace("[1]", this.deviceId)
				.replace("[2]", this.regId);
		String result = mNetworkUtil.doGetRequest(url);
		Log.d("webservice", "SEND REGID:  " + url + " : " + result);
	}
	
	public List<BetModel> getBetResult(String userId, String from){
		List<BetModel> list = new ArrayList<BetModel>();
		String url = Constant.URL_GET_BET_RESULT
				.replace("[1]", userId)
				.replace("[2]", from);
		String result = mNetworkUtil.doGetRequest(url);
		
		try {
			JSONArray a = new JSONArray(result);
			for (int i = 0; i < a.length(); i++) {
				JSONObject o = a.getJSONObject(i);
				list.add(new BetModel(o.getInt("bet_result"),
						o.getInt("bet_result_value"),
						o.getString("guest_club"),
						o.getInt("guest_club_goal"),
						o.getInt("guest_penalty_shoot"),
						o.getString("home_club"),
						o.getInt("home_club_goal"),
						o.getInt("home_penalty_shoot"),
						o.getInt("league_row_id"),
						o.getInt("matchId"),
						o.getInt("matchId"),
						o.getString("register_datetime"),
						o.getInt("user_bet_value")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("webservice", "getBetResult:  " + url + " : " + list.size());
		return list;
	}
	
	public UpdateAppModel checkAppUpdate(int versionCode){
		String url = Constant.URL_CHECK_UPDATE.replace("[1]", this.deviceId)
				.replace("[2]", "1")
				.replace("[3]", "6x16")
				.replace("[4]", ""+versionCode);
		String result = mNetworkUtil.doGetRequest(url);
		UpdateAppModel model = new UpdateAppModel();
		try {
			JSONObject o = new JSONObject(result);
			model = new UpdateAppModel(o.getString("introductionContent"), o.getString("updateLink"), o.getInt("versionNumber"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.d("webservice", "checkForUpdate:  " + url + " : " + model.getVersionNumber());
		return model;
	}

	
	public int verifyEmail(int userId,String userEmail){
		int result = 0;
		String url = Constant.URL_VERIFY_EMAIL
				.replace("[1]", userId+"")
				.replace("[2]", userEmail)
				.replace(" ", "%20");
		try {
			result = Integer.parseInt(mNetworkUtil.doGetRequest(url));
		} catch (Exception e) {
			// TODO: handle exception
			result = -100;
		}
		Log.d("webservice", "verifyEmail:  " + url + " : " + result);
		return result;
	}
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
