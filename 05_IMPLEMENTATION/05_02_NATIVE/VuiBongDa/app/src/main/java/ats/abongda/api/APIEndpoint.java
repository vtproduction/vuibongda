package ats.abongda.api;

import com.google.gson.JsonElement;

import java.util.List;

import ats.abongda.model.AppUpdate;
import ats.abongda.model.Club;
import ats.abongda.model.ClubInfo;
import ats.abongda.model.GeneralConfiguration;
import ats.abongda.model.GroupStanding;
import ats.abongda.model.LeagueSeason;
import ats.abongda.model.LiveModel;
import ats.abongda.model.Match;
import ats.abongda.model.News;
import ats.abongda.model.Next24hModel;
import ats.abongda.model.Player;
import ats.abongda.model.UserAccountLog;
import ats.abongda.model.UserBet;
import ats.abongda.model.UserInbox;
import ats.abongda.model.UserModel;
import ats.abongda.model.match.MatchDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NienLe on 12-Jul-16.
 */
public interface APIEndpoint {

    @GET("CheckForUpdate")
    Call<AppUpdate> checkForUpdate(@Query("imei") String imei, @Query("partner") String partner, @Query("appType") int appType, @Query("versionCode") int versionCode);

    @GET("GetGeneralConfiguration")
    Call<GeneralConfiguration> getGeneralConfiguration(@Query("partner") String partner);

    @GET("getnews")
    Call<List<News>> getNews(@Query("imei") String imei);

    @GET("GetUserInfo")
    Call<UserModel> getUserInfo(@Query("user_id") int user_id);

    @GET("SignInWithGetAccount")
    Call<UserModel> signin(@Query("identification") String identification, @Query("username") String username
            ,@Query("password") String password);

    @GET("SignUp")
    Call<UserModel> signup(@Query("identification") String identification, @Query("username") String username
            ,@Query("password") String password,@Query("is_signup") int is_signup);

    @GET("GetUserBetStatistic")
    Call<UserModel> getUserStatistic(@Query("userId") int userId);

    @GET("GetLeagueMatchSchedule_v2")
    Call<List<LiveModel>> getLive(@Query("imei") String imei);

    @GET("GetLeagueMatchScheduleInNext24h")
    Call<List<Match>> getNext24hMatch(@Query("imei") String imei, @Query("user_row_id") int user_row_id);

    @GET("ChangeUserInfo_12")
    Call<Integer> changeUserInfo(@Query("user_id") int user_id,
                                 @Query("fullname") String fullname,
                                 @Query("phone") String phone,
                                 @Query("address") String address,
                                 @Query("email") String email);
    @GET("VerifyEmail")
    Call<Integer> verifyUserEmail(@Query("userId") int userId, @Query("userEmail") String userEmail);

    @GET("ChangePassword")
    Call<Integer> changePassword(@Query("user_id") int user_id, @Query("old_password") String old_password, @Query("new_password") String new_password);

    @GET("GetSeason")
    Call<List<LeagueSeason>> getLeagueSeason(@Query("imei") String imei, @Query("leagueid") int leagueid);

    @GET("GetNationalMatchResult")
    Call<List<Match>> getNationalMatchResult(
            @Query("imei") String imei,
            @Query("season_row_id") int season_row_id,
            @Query("league_row_id") int league_row_id,
            @Query("month") int month,
            @Query("userId") int userId
    );

    @GET("GetCupMatchResult")
    Call<List<Match>> getCupMatchResult(
            @Query("imei") String imei,
            @Query("season_row_id") int season_row_id,
            @Query("league_row_id") int league_row_id,
            @Query("roundType") int roundType,
            @Query("userId") int userId
    );

    @GET("UpdateUserMessageReadStatus")
    Call<Object> updateUserMessageReadStatus(@Query("id") int messageId);

    @GET("GetUserMessage")
    Call<List<UserInbox>> getUserMessage(@Query("username") String username, @Query("start") int start);

    @GET("GetSeasonGroupStanding")
    Call<List<GroupStanding>> getSeasonGroupStanding(@Query("imei") String imei, @Query("LeagueId") int leagueId, @Query("SeasonId") int seasonId);

    @GET("GetSeasonStanding")
    Call<List<Club>> getSeasonStanding(@Query("imei") String imei, @Query("LeagueId") int leagueId, @Query("SeasonId") int seasonId);

    @GET("PlayerStatisticBySeason")
    Call<List<Player>> getPlayerStatisticBySeason(
            @Query("imei") String imei,
            @Query("SeasonId") int SeasonId,
            @Query("LeagueId") int LeagueId,
            @Query("StatisticType") int StatisticType
    );

    @GET("GetAllMatchInformation")
    Call<JsonElement> getMatchFullDetail(@Query("imei") String imei, @Query("matchId") int matchId);

    @GET("GetClubInformation")
    Call<ClubInfo> getClubInformation(@Query("imei") String imei, @Query("clubId") int clubId);

    @GET("GetTeamPlayerInformation")
    Call<List<Player>> getTeamPlayerInformation(@Query("imei") String imei, @Query("clubId") int clubId);

    @GET("GetPlayerInformation")
    Call<Player> getPlayerInformation(@Query("imei") String imei, @Query("playerId") int playerId);

    @GET("GetUserAccountLog")
    Call<List<UserAccountLog>> getUserAccountLog(@Query("username") String username, @Query("number") int number);

    @GET("GetUserBetResult")
    Call<List<UserBet>> getUserBetHistory(@Query("userId") int userId, @Query("from") int from);

    @GET("GetMatchOfClubBySeason")
    Call<List<Match>> getMatchOfClubBySeason(@Query("imei") String imei, @Query("season_row_id") int season_row_id, @Query("club_row_id") int club_row_id);

    @GET("SaveUserBetWithDetailResult")
    Call<Integer> perfomRatioBet(
            @Query("MatchId") int MatchId,
            @Query("RegistrationId") String RegistrationId,
            @Query("UserId") int UserId,
            @Query("BetType") int BetType,
            @Query("BetValue") String BetValue,
            @Query("UserBetString") String UserBetString,
            @Query("Handicap") String Handicap,
            @Query("BetHomeGoal") int BetHomeGoal,
            @Query("BetGuestGoal") int BetGuestGoal,
            @Query("SameUserBet") int SameUserBet);

    @GET("SaveUserBetWithDetailResult")
    Call<Integer> perfomWinnerBet(
            @Query("MatchId") int MatchId,
            @Query("RegistrationId") String RegistrationId,
            @Query("UserId") int UserId,
            @Query("BetType") int BetType,
            @Query("BetValue") String BetValue,
            @Query("UserBetString") String UserBetString,
            @Query("Handicap") String Handicap);

    @GET("GetRankingTable")
    Call<List<UserModel>> getRankingTable(@Query("username") String username);

    @GET("GetTopBetResult")
    Call<List<UserModel>> getTopBetResult(@Query("user_id") int userId, @Query("filter_type") int filter_type);

    @GET("SaveNotificationRequest")
    Call<String> requestNotification(@Query("RegistrationId") String RegistrationId, @Query("MatchId") int MatchId);
}
