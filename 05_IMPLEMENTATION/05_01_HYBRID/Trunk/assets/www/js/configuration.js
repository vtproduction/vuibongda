var LeagueName = ["Premier League", "Primera Liga", "Serie A", "Bundesliga", "Ligue One", "Champions League", "Europa League", "FA Cup", "Giải quốc tế"];
var weekday = ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"];
var GCMProjectId = "419677538915";

var ABongDaAppType = 1;	// 1: Android; 2: iOS
var ABongDaAppPartner = "6x16"; // 6x16 | appota
var ABongDaAppVersionCode = 19;
var clientUserObject = null; // Contain user account information

var announcementUpdateTime = new Date();

$(document).on("mobileinit", function()
{
	$.mobile.defaultPageTransition = "none";
    $.defaultDialogTransition = "none";
    $.mobile.transitionFallbacks.slideout = "none";
    $.mobile.ajaxEnabled = "true";
    $.mobile.orientationChangeEnabled = "false";
    $.mobile.zoom.enable(false);
});

window.addEventListener("load", function() {
	new FastClick(document.body);
}, false);
						
$(document).ready(function ()
{
	$("body").append("<div class='ui-loader-background'></div>");	
	
	$(":jqmData(role=page):not(#change_password_page, #signup_page, #update_guest_page)").each(function (index, element)
	{
        $(this).children(":jqmData(role=header)").prepend("<a href=\"#nav_bar_" + index + "\" data-role=\"none\"><span class=\"icon_bars\"></span></a>");

        // Add menu side bar
        var nav_bar 
			= "<div data-role='panel' data-position='left' data-display='push' id='nav_bar_" + index + "' class='main_nav_bar' data-theme='a' data-position='fixed'>"
				+ "<ul data-role='listview' data-theme='a'>"
					+ "<li><span class='aBongDa_logo'></span><span class='version_header'>ABongDa 1.9</span></li>"
				+ "</ul>"
				+ "<div class='clearer'></div>"
				+ "<ul class='main_menu'>"
					+ "<li>"
						+ "<a href='#' onclick='$(this).parent().toggleClass(\"expand\")'><img src='images/account-25.png' /><div class='menu-text'>Tài khoản</div><span class='expand_icon'></span></a>"
						+ "<ul data-role='none'>"
							+ "<li class='li_match'><a href='#' onclick='javascript:return changeToSigninPage(\"#account_page\");' data-rel='close'><img src='images/profile.png' class='ui-li-icon' /><div class='menu-text'>Hồ sơ</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='changeToSigninPage(\"#account_history_page\");' data-rel='close'><img src='images/account_history.png' class='ui-li-icon' /><div class='menu-text'>Lịch sử tài khoản</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='changeToSigninPage(\"#bet_history_page\");' data-rel='close'><img src='images/bet.png' class='ui-li-icon' /><div class='menu-text'>Lịch sử đặt cược</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='changeToSigninPage(\"#message_page\");' data-rel='close'><img src='images/email-25.png' class='ui-li-icon' /><div class='menu-text'>Hộp thư</div></a></li>"
						+ "</ul>"
					+ "</li>"			
					+ "<li><a href='soccer.html#fixture_page'><img src='images/live-25.png' data-rel='close'/><div class='menu-text'>Live score</div></a></li>"
					+ "<li><a href='soccer.html#news_page'><img src='images/news_25.png' data-rel='close'/><div class='menu-text'>Tin tức</div></a></li>"
					+ "<li>"
						+ "<a href='#' onclick='$(this).parent().toggleClass(\"expand\")'><img src='images/cup_info.png' /><div class='menu-text'>Thông tin giải đấu</div><span class='expand_icon'></span></a>"
						+ "<ul data-role='none'>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(1)' data-rel='close'><img src='images/league/league_1x25.png' class='ui-li-icon' /><div class='menu-text'>Premier League</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(2)' data-rel='close'><img src='images/league/league_2x25.png' class='ui-li-icon' /><div class='menu-text'>Primera Liga</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(3)' data-rel='close'><img src='images/league/league_3x25.png' class='ui-li-icon' /><div class='menu-text'>Serie A</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(4)' data-rel='close'><img src='images/league/league_4x25.png' class='ui-li-icon' /><div class='menu-text'>Bundesliga</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(5)' data-rel='close'><img src='images/league/league_5x25_invert.png' class='ui-li-icon' /><div class='menu-text'>Ligue One</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(6)' data-rel='close'><img src='images/league/league_20x25_invert.png' class='ui-li-icon' /><div class='menu-text'>Champions League</div></a></li>"
							+ "<li class='li_match'><a href='#' onclick='ChangeToResultPageDetail(24)' data-rel='close'><img src='images/league/league_42x25.png' class='ui-li-icon' /><div class='menu-text'>Europa League</div></a></li>"
						+ "</ul>"
					+ "</li>"			
					+ "<li><a href='soccer.html#next24h_page'><img src='images/next24h.png' data-rel='close'/><div class='menu-text'>Đặt cược 24h tới</div></a></li>"
					+ "<li>"
						+ "<a href='#' onclick='$(this).parent().toggleClass(\"expand\")'><img src='images/ranking-25.png' /><div class='menu-text'>Bảng xếp hạng</div><span class='expand_icon'></span></a>"
						+ "<ul data-role='none'>"
							+ "<li class='li_match'><a href='soccer.html#top_account_page'><img src='images/ranking-25-2.png' data-rel='close'/><div class='menu-text'>Top đại gia</div></a></li>"
							+ "<li class='li_match'><a href='soccer.html#top_bet_result_page'><img src='images/ranking-25-3.png' data-rel='close'/><div class='menu-text'>Top cao thủ</div></a></li>"
						+ "</ul>"
					+ "</li>"
				+ "</ul>"
			+ "</div>";
        
		$(this).append(nav_bar);
    });
	
    localStorage.minimumTransferValue = 300;
    localStorage.minimumRemainTransferValue = 500;
    localStorage.transferFee = 0.1;

    if (typeof localStorage.username != "undefined" && localStorage.username != "" && localStorage.username != null && typeof localStorage.userId != "undefined" && localStorage.userId != 0)
	{
        clientUserObject = new userObj();
        clientUserObject.setUserName(localStorage.userName);
    }
	
    var jqxhr = $.ajax({
        url: localStorage.generalConfigurationUrl,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (data)
		{
            // Update marquee title
			var marquee = $(".marquee-title");
			
            if (data._announcementInfo != null)
			{		
                var htmlMarquee = "<a href=''><marquee>" + data._announcementInfo.Content + "</marquee></a>";
                marquee.html(htmlMarquee);
                marquee.show();
                marquee.click(function () {
                    window.open(data._announcementInfo.Link, "_system");
                });
            } else {
                marquee.hide();
            }

            localStorage.goalBet = data._goalBet;
            localStorage.minimumGoalBetValue = data._minimumGoalBetValue;
            localStorage.minimumNormalBetValue = data._minimumNormalBetValue;
            localStorage.showMobileAdvertisement = data._showMobileAdvertisement;       

			$("#lblTransferXuFee").html(data._transferFee * 100);
            localStorage.minimumRemainTransferValue = data._minimumTransferRemainAmount;

            var lblMinCoinPanel = $("#lblMinimumXuRequiredPanel");
			
            if (data._minimumTransferAmount > 0)
			{
                localStorage.minimumTransferValue = data._minimumTransferAmount;
                $("#lblMinimumXuRequired").html(data._minimumTransferAmount);
                lblMinCoinPanel.show();
            }
            else {
                lblMinCoinPanel.hide();
			}

            $(".hot_line_support").html(data._hotline);

            var cbRoundType = $("#roundType");
            cbRoundType[0].selectedIndex = data._currentCLRound;
            cbRoundType.selectmenu();
            cbRoundType.selectmenu("refresh");
        },
        complete: function (jqXHR, status) { }
    });
});

function GetImei()
{
    var query = location.href.substring((location.href.indexOf('?') + 1), location.href.length);
	
    if (location.href.indexOf('?') < 0) {
        query = "";
	}
    querysplit = query.split('&');
    query = new Array();
	
    for (var i = 0; i < querysplit.length; i++)
	{
        var namevalue = querysplit[i].split('=');
        namevalue[1] = namevalue[1].replace(/\+/g, ' ');
        query[namevalue[0]] = unescape(namevalue[1]);
    }

    var DeviceIMEI = query["imei"];
    localStorage.imei = DeviceIMEI;
}

function InitialStorage()
{
    var ServiceUrl = "http://api.tructiep.xyz/ajaxservice.svc/"
	//var ServiceUrl = "http://183.91.7.242:2824/AjaxService.svc/";
    //var ServiceUrl = "http://localhost:27375/AjaxService.svc/";
	
    localStorage.imgUrl = "http://cdn.ats.vn/upload/football/club/";
	
    localStorage.checkDeviceExpiredUrl = ServiceUrl + "CheckDeviceExpired?imei=";
    localStorage.newsUrl = ServiceUrl + "GetNews?imei=" + localStorage.imei;
    localStorage.standingUrl = ServiceUrl + "GetStanding?imei=" + localStorage.imei + "&LeagueId=";
    localStorage.seasonStandingUrl = ServiceUrl + "GetSeasonStanding?imei=" + localStorage.imei + "&LeagueId=";
    localStorage.groupStandingUrl = ServiceUrl + "GetGroupStanding?imei=" + localStorage.imei + "&LeagueId=";
    localStorage.seasonGroupStandingUrl = ServiceUrl + "GetSeasonGroupStanding?imei=" + localStorage.imei + "&LeagueId=";
    localStorage.fixtureUrl = ServiceUrl + "GetLeagueMatchSchedule_v2?imei=" + localStorage.imei;   // Service since version 1.5
    localStorage.matchUrl = ServiceUrl + "GetAllMatchInformation?imei=" + localStorage.imei + "&matchId=";
    localStorage.clubUrl = ServiceUrl + "GetClubInformation?imei=" + localStorage.imei + "&clubId=";
    localStorage.teamUrl = ServiceUrl + "GetTeamPlayerInformation?imei=" + localStorage.imei + "&clubId=";
    localStorage.playerUrl = ServiceUrl + "GetPlayerInformation?imei=" + localStorage.imei + "&playerId=";
    localStorage.seasonUrl = ServiceUrl + "GetSeason?imei=" + localStorage.imei + "&leagueId=";
    localStorage.nationalMatchResultUrl = ServiceUrl + "GetNationalMatchResult?imei=" + localStorage.imei + "&";
    localStorage.cupMatchResultUrl = ServiceUrl + "GetCupMatchResult?imei=" + localStorage.imei + "&";
    localStorage.matchOfClubBySeason = ServiceUrl + "GetMatchOfClubBySeason?imei=" + localStorage.imei;
    localStorage.confrontationHistoryUrl = ServiceUrl + "GetConfrontationHistory?matchId=";
    localStorage.betUrl = ServiceUrl + "SaveUserBetWithDetailResult?MatchId=";
    localStorage.playerStandingStatistics = ServiceUrl + "PlayerStatisticBySeason?imei=" + localStorage.imei + "&SeasonId=";
    localStorage.next24h = ServiceUrl + "GetLeagueMatchScheduleInNext24h?imei=" + localStorage.imei;
    localStorage.betHistory = ServiceUrl + "GetUserBetResult?";
	localStorage.betStatisticUrl = ServiceUrl + "GetUserBetStatistic?userId=";

    localStorage.registerPushnotificationRegIDURL = ServiceUrl + "RegisterRegistrationID?imei=" + localStorage.imei + "&regId=";
    localStorage.saveMatchNotificationRequeset = ServiceUrl + "SaveNotificationRequest?RegistrationId=";

    localStorage.signupUrl = ServiceUrl + "SignUp";
    localStorage.signinUrl = ServiceUrl + "SignInWithGetAccount?identification=" + localStorage.imei;
    localStorage.changePasswordUrl = ServiceUrl + "ChangePassword";
    localStorage.getUserInfoUrl = ServiceUrl + "GetUserInfo";
    //localStorage.changeUserInfoUrl = ServiceUrl + "ChangeUserInfo";
    localStorage.changeUserInfoUrl = ServiceUrl + "ChangeUserInfo_12"; //version 1.2
    localStorage.transferCoinUrl = ServiceUrl + "TransferXu";
    localStorage.verifyEmailUrl = ServiceUrl + "VerifyEmail";
    //localStorage.topAccountUrl = ServiceUrl + "GetTopHighestAcount";
    localStorage.listUserBet = ServiceUrl + "ListUserBet?userId=";
    localStorage.getRankingTableUrl = ServiceUrl + "GetRankingTable?username=";
	localStorage.getBetResultRankingTableUrl = ServiceUrl + "GetTopBetResult?user_id=";
    localStorage.guestInfo = ServiceUrl + "GetGuestAccountInfo?deviceId=";
    localStorage.guestUpdate = ServiceUrl + "UpdateGuestAccount";
    
    localStorage.announcementUpdateUrl = ServiceUrl + "GetAnnouncementUpdate";
    localStorage.getAdInformation = ServiceUrl + "GetAdInformation";
    
    localStorage.expiredObject = "";
    localStorage.newsData = "";
    localStorage.groupTableData = "";
    localStorage.top = 0;
    localStorage.topRankValue = 10;

    if (ABongDaAppPartner == "appota")
	{
        if (ABongDaAppType == 2) { // iOS
            localStorage.generalConfigurationUrl = ServiceUrl + "GetGeneralConfiguration?partner=AppotaIOS";
        }
        else {
            localStorage.generalConfigurationUrl = ServiceUrl + "GetGeneralConfiguration?partner=AppotaAndroid";
        }
    }
    else {
        localStorage.generalConfigurationUrl = ServiceUrl + "GetGeneralConfiguration?partner=6x16";
    }

    localStorage.policyUrl = ServiceUrl + "PolicyContent?policyType=";
    localStorage.userAccountLogUrl = ServiceUrl + "GetUserAccountLog?";
    localStorage.GetUserMessageUrl = ServiceUrl + "GetUserMessage?";
    localStorage.UpdateUserMessageReadStatusUrl = ServiceUrl + "UpdateUserMessageReadStatus?id=";
    localStorage.GetTotalUnreadMessageUrl = ServiceUrl + "GetTotalUnreadMessage?username=";
    localStorage.isNeedReloadMessageBox = 1;

    localStorage.CheckForUpdateUrl = ServiceUrl + "CheckForUpdate?imei=" + localStorage.imei + "&appType=" + ABongDaAppType + "&partner=" + ABongDaAppPartner + "&versionCode=" + ABongDaAppVersionCode;
    localStorage.SearchUserUrl = ServiceUrl + "SearchUsers";
}

var IsCheckForUpdate = 0;

function CheckForUpdate()
{
    if (IsCheckForUpdate == 0)
    {
        IsCheckForUpdate = 1;
        var jqxhr = $.ajax({
            url: localStorage.CheckForUpdateUrl,
            type: "POST",
            cache: false,
            dataType: "jsonp",
            success: function (data)
			{
                var uMesssageBox = $("#updateMessageBox");
                var uButton = $(".updateButton");
                var cUpdateBtn = $(".closeUpdateButton");

                if (data.versionNumber > 0 && ABongDaAppVersionCode > 0 && data.versionNumber > ABongDaAppVersionCode)
				{
                    $(".popupBoxContent").html(data.introductionContent);
                    uMesssageBox.show();
                    uMesssageBox.popup();

                    uButton.unbind("click");
                    uButton.click(function ()
					{
                        window.open(data.updateLink, "_system");
                        uMesssageBox.popup("close");
                    });

                    cUpdateBtn.unbind("click");
                    cUpdateBtn.click(function () {
                        uMesssageBox.popup("close");
                    });

                    $(".closePopup").button();

                    uMesssageBox.popup("open");
                }
            },
            complete: function (jqXHR, status) { }
        });
    }
}