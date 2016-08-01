var formArray = ["H", "T", "B"];
var formClass = ["draw-form", "win-form", "lose-form"];
var betClub = ["Khách", "Chủ nhà", "Hòa"];
var winCoinIcon = " <img src=\"./images/btup.gif\">";
var loseCoinIcon = " <img src=\"./images/btdown.gif\">";

$(document).on("pageshow", "#match_detail_page", function(event)
{
    $("#match_detail_page ul[data-role='listview']").listview("refresh");
    $("#btnNotification").button("refresh");
});

function filterMatch(matchId)
{
    var result = new Array();
    if (clientUserObject != null)
	{
        for (var i = 0; i < clientUserObject.betList.length; i++)
		{
            var obj = clientUserObject.betList[i];
            if (obj.matchId == matchId) {
                result.push(obj);
            }
        }
    }
    return result;
}

function displayCurrentForm(homeForm, guestForm, appendTarget)
{
    var block_a = document.createElement("div");
    var block_b = document.createElement("div");
    var block_c = document.createElement("div");

    // Adding home club's form
    $(block_a).addClass("ui-block-a");
    $(block_a).addClass("match-display");
	
    for (var i = 0; i < homeForm.length; i++)
	{
        var f = homeForm[i];
        var span = document.createElement("span");
		
        $(span).addClass(formClass[f]);
        $(span).addClass("club-form");
        $(span).append(formArray[f]);
        $(span).appendTo(block_a);
    }
	
    $(block_a).appendTo(appendTarget);

    $(block_b).addClass("ui-block-b");
    $(block_b).appendTo(appendTarget);

    // Adding guest club's form
    $(block_c).addClass("ui-block-c");
    $(block_c).addClass("match-display");
	
    for (var i = 0; i < guestForm.length; i++)
	{
        var f = guestForm[i];
        var span = document.createElement("span");
		
        $(span).addClass(formClass[f]);
        $(span).addClass("club-form");
        $(span).append(formArray[f]);
        $(span).appendTo(block_c);
    }
	
    $(block_c).appendTo(appendTarget);
}

// Deprecated since version 1.5
function displayBetHistory(appendTarget)
{
    // Remove previous history
    $(".bet-history-anchor").remove();

    var betList = filterMatch(localStorage.matchId);
    var asianList = new Array();
    var euroList = new Array();
    var scoreList = new Array();

    /* Filter odd type */
    for (var i = 0; i < betList.length; i++)
	{
        var o = betList[i];

        if (o.betType < 4) { // euro odd
            euroList.push(o);
        } else if (o.betType < 7) {  // asian odd
            asianList.push(o);
        } else {    // score odd
            scoreList.push(o);
        }
    }

    if (asianList.length > 0 || euroList.length > 0)
	{
        var titleHtml = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\"> \
				<div class=\"ui-block-a \"><div></div></div> \
				<div class=\"ui-block-b list-h\"><span>Đội</span></div> \
				<div class=\"ui-block-c list-h\"><div>Tỉ lệ</div></div> \
				<div class=\"ui-block-d list-h\"><div>Xu</div></div> \
			</div> \
		</li>";
        appendTarget.append(titleHtml);
    }

    /* Asian Odd */
    if (asianList.length > 0)
	{
        var o = asianList[0];
        var betResultValue = o.betResultValue;
        var betResultClass = "";
        var betResultIcon = "";

        if (betResultValue > 0)
		{
            betResultClass = "win-coin";
            betResultIcon = winCoinIcon;
        } else if (betResultValue < 0)
		{
            betResultClass = "lose-coin";
            betResultIcon = loseCoinIcon;
        }

        var html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a list-t\"><div>Kèo châu Á</div></div>\
				<div class=\"ui-block-b tips-t\"><div>" + betClub[o.betType % 3] + "</div></div>\
				<div class=\"ui-block-c tips-t\"><div>" + o.bet + "<br/>" + "<span class=\"handicap-h\">" + "(" + o.matchHandicap + ")" + "</span>" + "</div></div>\
				<div class=\"ui-block-d tips-t\"><div>" + o.betValue + "<br/>" + "<span class=" + betResultClass + ">" + ((betResultValue != 0) ? betResultValue : "") + betResultIcon + "</span>" + "</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);
    }

    for (var i = 1; i < asianList.length; i++)
	{
        var o = asianList[i];
        var betResultValue = o.betResultValue;
        var betResultClass = "";
        var betResultIcon = "";

        if (betResultValue > 0)
		{
            betResultClass = "win-coin";
            betResultIcon = winCoinIcon;
        } else if (betResultValue < 0)
		{
            betResultClass = "lose-coin";
            betResultIcon = loseCoinIcon;
        }

        var html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a\"><div></div></div>\
				<div class=\"ui-block-b tips-t\"><div>" + betClub[o.betType % 3] + "</div></div>\
				<div class=\"ui-block-c tips-t\"><div>" + o.bet + "<br/>" + "<span class=\"handicap-h\">" + "(" + o.matchHandicap + ")" + "</span>" + "</div></div>\
				<div class=\"ui-block-d tips-t\"><div>" + o.betValue + "<br/>" + "<span class=" + betResultClass + ">" + ((betResultValue != 0) ? betResultValue : "") + betResultIcon + "</span>" + "</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);
    }

    /* Euro Odd */
    if (euroList.length > 0)
	{
        var o = euroList[0];
        var betResultValue = o.betResultValue;
        var betResultClass = "";
        var betResultIcon = "";

        if (betResultValue > 0)
		{
            betResultClass = "win-coin";
            betResultIcon = winCoinIcon;
        }
		else if (betResultValue < 0)
		{
            betResultClass = "lose-coin";
            betResultIcon = loseCoinIcon;
        }

        var html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a list-t\"><div>Kèo châu Âu</div></div>\
				<div class=\"ui-block-b tips-t\"><div>" + betClub[o.betType % 3] + "</div></div>\
				<div class=\"ui-block-c tips-t\"><div>" + o.bet + "</div></div>\
				<div class=\"ui-block-d tips-t\"><div>" + o.betValue + "<br/>" + "<span class=" + betResultClass + ">" + ((betResultValue != 0) ? betResultValue : "") + betResultIcon + "</span>" + "</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);
    }

    for (var i = 1; i < euroList.length; i++)
	{
        var o = euroList[i];
        var betResultValue = o.betResultValue;
        var betResultClass = "";
        var betResultIcon = "";

        if (betResultValue > 0)
		{
            betResultClass = "win-coin";
            betResultIcon = winCoinIcon;
        }
		else if (betResultValue < 0)
		{
            betResultClass = "lose-coin";
            betResultIcon = loseCoinIcon;
        }

        var html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a\"><div></div></div>\
				<div class=\"ui-block-b tips-t\"><div>" + betClub[o.betType % 3] + "</div></div>\
				<div class=\"ui-block-c tips-t\"><div>" + o.bet + "</div></div>\
				<div class=\"ui-block-d tips-t\"><div>" + o.betValue + "<br/>" + "<span class=" + betResultClass + ">" + ((betResultValue != 0) ? betResultValue : "") + betResultIcon + "</span>" + "</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);
    }

    /* Score Odd */
    if (scoreList.length > 0)
	{
        var html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a \"><div></div></div>\
				<div class=\"ui-block-b list-h\"><span>Chủ nhà</span></div>\
				<div class=\"ui-block-c list-h\"><div>Khách</div></div>\
				<div class=\"ui-block-d list-h\"><div>Xu</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);

        var o = scoreList[0];
        var betResultValue = o.betResultValue;
        var betResultClass = "";
        var betResultIcon = "";

        if (betResultValue > 0)
		{
            betResultClass = "win-coin";
            betResultIcon = winCoinIcon;
        }
		else if (betResultValue < 0)
		{
            betResultClass = "lose-coin";
            betResultIcon = loseCoinIcon;
        }

        html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a list-t\"><div>Kèo tỉ số</div></div>\
				<div class=\"ui-block-b tips-t\"><div>" + o.betHomeGoal + "</div></div>\
				<div class=\"ui-block-c tips-t\"><div>" + o.betGuestGoal + "</div></div>\
				<div class=\"ui-block-d tips-t\"><div>" + o.betValue + "<br/>" + "<span class=" + betResultClass + ">" + ((betResultValue != 0) ? betResultValue : "") + betResultIcon + "</span>" + "</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);
    }

    for (var i = 1; i < scoreList.length; i++)
	{
        var o = scoreList[i];
        var html = "<li class=\"tips bet-history-anchor\"> \
			<div class=\"ui-grid-c\">\
				<div class=\"ui-block-a\"><div></div></div>\
				<div class=\"ui-block-b tips-t\"><div>" + o.betHomeGoal + "</div></div>\
				<div class=\"ui-block-c tips-t\"><div>" + o.betGuestGoal + "</div></div>\
				<div class=\"ui-block-d tips-t\"><div>" + o.betValue + "<br/>" + "<span class=" + betResultClass + ">" + ((betResultValue != 0) ? betResultValue : "") + betResultIcon + "</span>" + "</div></div>\
			</div>\
		</li>";
		
        appendTarget.append(html);
    }
}

function onSuccessLoadDetailPage(data)
{
    // Init image icon
    var goalIcon = "./images/football-icon.png";
    var yellowIcon = "./images/yellow_card.png";
    var doubleYellowIcon = "./images/double_yellow_card.png";
    var redIcon = "./images/red_card.png";
    var failedIcon = "./images/pen_failed.png";

    // Display match detail
    var li = document.createElement("li");
    $(li).addClass("static-li");
    $(li).css("text-align", "center");
    var grid = document.createElement("div");

    var block_a_0 = document.createElement("div");
    var block_b_0 = document.createElement("div");
    var block_c_0 = document.createElement("div");

    var block_a_1 = document.createElement("div");
    var block_b_1 = document.createElement("div");
    var block_c_1 = document.createElement("div");

    var block_a_2 = document.createElement("div");
    var block_b_2 = document.createElement("div");
    var block_c_2 = document.createElement("div");

    var block_a_3 = document.createElement("div");
    var block_b_3 = document.createElement("div");
    var block_c_3 = document.createElement("div");

    /* Add attributes */
    $(grid).addClass("ui-grid-b");
    $(grid).attr("id", "match-contain");

    /* Display Match detail */
    $(block_a_0).addClass("ui-block-a");
    $(block_a_1).addClass("match-display");
    $(block_a_0).append("<a href='javascript:ChangeToClubDetailPage(" + data[0]._home_id + ");'><img src='" + localStorage.imgUrl + data[0]._homeLogo + "' /></a>");
    $(block_a_0).appendTo(grid);
    $(block_b_0).addClass("ui-block-b");
    $(block_b_0).appendTo(grid);
    $(block_c_0).addClass("ui-block-c");
    $(block_c_1).addClass("match-display");
    $(block_c_0).append("<a href='javascript:ChangeToClubDetailPage(" + data[0]._guest_id + ");'><img src='" + localStorage.imgUrl + data[0]._guestLogo + "' /></a>");
    $(block_c_0).appendTo(grid);

    /* Display club current form */
    displayCurrentForm(data[0]._homeForm, data[0]._guestForm, grid);

    $(block_a_1).addClass("ui-block-a");
    $(block_a_1).addClass("match-display");
    $(block_a_1).append("<a href='javascript:ChangeToClubDetailPage(" + data[0]._home_id + ");'>" + data[0]._home + "</a>");
    $(block_a_1).appendTo(grid);
    $(block_b_1).addClass("ui-block-b");
    $(block_b_1).append("-");
    $(block_b_1).appendTo(grid);
    $(block_c_1).addClass("ui-block-c");
    $(block_c_1).addClass("match-display");
    $(block_c_1).append("<a href='javascript:ChangeToClubDetailPage(" + data[0]._guest_id + ");'>" + data[0]._guest + "</a>");
    $(block_c_1).appendTo(grid);

    // Display score
    var isShowResult = data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6;
    var homeGoal = (!isShowResult ? "-" : data[0]._homeGoal);
    var guestGoal = (!isShowResult ? "-" : data[0]._guestGoal);

    $(block_a_2).addClass("ui-block-a");
    $(block_a_2).addClass("score");
    $(block_a_2).append(homeGoal);

    $(block_b_2).addClass("ui-block-b");
    $(block_b_2).addClass("time");
    $(block_b_2).append(data[0]._matchMinute);

    $(block_c_2).addClass("ui-block-c");
    $(block_c_2).addClass("score");
    $(block_c_2).append(guestGoal);

    $(block_a_2).appendTo(grid);
    $(block_b_2).appendTo(grid);
    $(block_c_2).appendTo(grid);

    if ((data[0]._guestPenaltyShoot > 0) || (data[0]._homePenaltyShoot > 0))
	{
        $(block_a_3).addClass("ui-block-a");
        $(block_a_3).addClass("pen");
        $(block_a_3).append(data[0]._homePenaltyShoot);

        $(block_b_3).addClass("ui-block-b");
        $(block_b_3).addClass("time");
        $(block_b_3).append("PEN");

        $(block_c_3).addClass("ui-block-c");
        $(block_c_3).addClass("pen");
        $(block_c_3).append(data[0]._guestPenaltyShoot);

        $(block_a_3).appendTo(grid);
        $(block_b_3).appendTo(grid);
        $(block_c_3).appendTo(grid);
    }

    $(li).append(grid);
    
    var $list_match = $("#list-match");
    $list_match.prepend(li);

    // Stadium display
    $("#stadium").append(data[0]._matchStadium);

    // Real time info
    for (var i = 0; i < data[0]._info_list.length; i++)
	{
        var homeIcon = "";
        var guestIcon = "";

        /* Checking info type */
        var type = data[0]._info_list[i].Type;

        switch (type)
		{
            //Home Icon  
            case "10":
                homeIcon = "<img src='" + goalIcon + "'/>";
                break;

            case "20":
                homeIcon = "<img src='" + yellowIcon + "'/>";
                break;

            case "30":
                homeIcon = "<img src='" + redIcon + "'/>";
                break;

            case "40":
                homeIcon = "<img src='" + doubleYellowIcon + "'/>";
                break;

            case "50":
                homeIcon = "<img src='" + failedIcon + "'/>";
                break;

                //Guest Icon  
            case "01":
                guestIcon = "<img src='" + goalIcon + "'/>";
                break;

            case "02":
                guestIcon = "<img src='" + yellowIcon + "'/>";
                break;

            case "03":
                guestIcon = "<img src='" + redIcon + "'/>";
                break;

            case "04":
                guestIcon = "<img src='" + doubleYellowIcon + "'/>";
                break;

            case "05":
                guestIcon = "<img src='" + failedIcon + "'/>";
                break;

            default:
                break;
        }

        var info_html = "<li class='match-real-time'>" +
			"<div class='ui-grid-d'>" +
			"<div class='ui-block-a'>" + homeIcon + "</div>" +
			"<div class='ui-block-b'>" + data[0]._info_list[i].HomeInfo + "</div>" +
			"<div class='ui-block-c'>" + data[0]._info_list[i].Status + "</div>" +
			"<div class='ui-block-d'>" + data[0]._info_list[i].GuestInfo + "</div>" +
			"<div class='ui-block-e'>" + guestIcon + "</div>" +
		"</div></li>";

        $list_match.append(info_html);
    }

    localStorage.homeClub = data[0]._home;
    localStorage.homeLogo = data[0]._homeLogo;
    localStorage.guestClub = data[0]._guest;
    localStorage.guestLogo = data[0]._guestLogo;
    localStorage.handicap = data[0]._handicap;

    // Asian tip
    var li = document.createElement("li");
    var grid_a = document.createElement("div");
    var block_b = document.createElement("div");
	
    $(li).addClass("tips");
    $(grid_a).addClass("ui-grid-a");
    $(block_b).addClass("ui-block-b");
	
    if (data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6 || data[0]._homeWinHandicap == null || data[0]._handicap == null || data[0]._guestWinHandicap == null)
	{
        $(block_b).append("<div>"
			+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWinHandicap == null ? '' : data[0]._homeWinHandicap) + "</div></a>"
			+ "<div class='betButton ui-disabled ui-btn-inline ui-btn-corner-all' style='border: 1px #a1a1a1 solid; font-weight: normal !important; color: #a1a1a1 !important; background-color: white;'><div>Chấp</div><div>" + (data[0]._handicap == null ? '' : data[0]._handicap) + "</div></div>"
			+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWinHandicap == null ? '' : data[0]._guestWinHandicap) + "</div></a>"
			+ "</div>");
			
        $("#bet_guide").hide();
    }
    else
	{
        $(block_b).append("<div>"
			+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(4, " + (data[0]._homeWinHandicap == null ? '' : data[0]._homeWinHandicap) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWinHandicap == null ? '' : data[0]._homeWinHandicap) + "</div></a>"
			+ "<div class='betButton ui-btn-inline ui-btn-corner-all' style='border: 1px #a1a1a1 solid; font-weight: normal !important; color: #a1a1a1 !important; background-color: white;'><div>Chấp</div><div>" + (data[0]._handicap == null ? '' : data[0]._handicap) + "</div></div>"
			+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(6, " + (data[0]._guestWinHandicap == null ? '' : data[0]._guestWinHandicap) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWinHandicap == null ? '' : data[0]._guestWinHandicap) + "</div></a>"
			+ "</div>");
			
        $("#btnNotification").removeAttr("disabled");
        $("#bet_guide").show();
    }

    var retrievedObject = localStorage.getItem("notificationMatch");
	
    if (retrievedObject != null)
	{
        var notificationMatch = JSON.parse(retrievedObject);
        var i;
        for (i = 0; i < notificationMatch.length; i++)
		{
            if (notificationMatch[i] == localStorage.matchId) {
                $("#btnNotification").attr("disabled", "disabled");
			}
		}
    }

    $(grid_a).append("<div class='ui-block-a'>Tỉ lệ châu Á</div>");
    $(grid_a).append(block_b);
    $(li).append(grid_a);
    $("#list-tips").append(li);

    // european tip
    li = document.createElement("li");
    grid_a = document.createElement("div");
    block_b = document.createElement("div");
	
    $(li).addClass("tips");
    $(grid_a).addClass("ui-grid-a");
    $(block_b).addClass("ui-block-b");
	
    if (data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6 || data[0]._homeWin1x2 == null || data[0]._draw1x2 == null || data[0]._guestWin1x2 == null)
	{
        $(block_b).append("<div>"
			+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWin1x2 == null ? '' : data[0]._homeWin1x2) + "</div></a>"
			+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Hòa</div><div>" + (data[0]._draw1x2 == null ? '' : data[0]._draw1x2) + "</div></a>"
			+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWin1x2 == null ? '' : data[0]._guestWin1x2) + "</div></a>"
			+ "</div>");
    }
    else
	{
        $(block_b).append("<div>"
			+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(1, " + (data[0]._homeWin1x2 == null ? '' : data[0]._homeWin1x2) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWin1x2 == null ? '' : data[0]._homeWin1x2) + "</div></a>"
			+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(2, " + (data[0]._draw1x2 == null ? '' : data[0]._draw1x2) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Hòa</div><div>" + (data[0]._draw1x2 == null ? '' : data[0]._draw1x2) + "</div></a>"
			+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(3, " + (data[0]._guestWin1x2 == null ? '' : data[0]._guestWin1x2) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWin1x2 == null ? '' : data[0]._guestWin1x2) + "</div></a>"
			+ "</div>");
    }

    $(grid_a).append("<div class='ui-block-a'>Tỉ lệ châu Âu</div>");
    $(grid_a).append(block_b);
    $(li).append(grid_a);
    $("#list-tips").append(li);

    li = document.createElement("li");
	
    if (data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6)
	{
        $(li).append("<div style='margin-top:10px;' class='match-div-p'><a class='ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-up-e' href='#' data-theme='e' data-mini='true' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><span class='ui-btn-inner'><span class='ui-btn-text'>Đặt cược tỉ số</span></span></a></div>");
        $("#btnNotification").attr("disabled", "disabled");
    }
    else
	{
		if (data[0]._isHot == 1) {
			$(li).append("<div style='margin-top:10px;' class='match-div-p'><a class='ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-up-e' href='javascript:changeToBetByScorePage();' data-theme='e' data-mini='true' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><span class='ui-btn-inner'><span class='ui-btn-text' style='color: red; font-weight: bolder'>Đặt cược tỉ số và tham gia trận cầu hot</span></span></a></div>");
		}
		else {
			$(li).append("<div style='margin-top:10px;'class='match-div-p'><a class='ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-up-e' href='javascript:changeToBetByScorePage();' data-theme='e' data-mini='true' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><span class='ui-btn-inner'><span class='ui-btn-text'>Đặt cược tỉ số</span></span></a></div>");
		}
	}
	
    $("#list-tips").append(li);

    // TV Channel
    $("#channel-pannel").html(data[0]._tvChannel);

    // Linup Info
    LoadLineupInfo(data[0]._lineup_list, data[0]._home_id, data[0]._guest_id);

    // Toggle same number of user bet
    if (data[0]._isHot == 0){
        $(".hot-bet").hide();
    } else
	{
		$("#campain-hot-match-introduction").html(data[0]._campaign_introduction);
        $(".hot-bet").show();
    }

    // Switch to detail page
    $.mobile.changePage("#match_detail_page");

    LoadConfrontHistory(data);
}

function ChangeToMatchDetailPage(id)
{
    localStorage.nationalResult = 1;
    localStorage.cupResult = 1;
    localStorage.matchId = id;
    $.mobile.loading("show");

    /* Clear previous data */
    $("#channel-pannel").html("");
    $("#list-tips").html("");
    $("#stadium").html("");
    $("#list-match li").not(document.getElementById("stadium-li")).remove();

    var jqxhr = $.ajax({
        url: localStorage.matchUrl + localStorage.matchId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data) {
            onSuccessLoadDetailPage(data);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadMatchDetail()
{
    $.mobile.loading("show");

    // Init image icon
    var goalIcon = "./images/football-icon.png";
    var yellowIcon = "./images/yellow_card.png";
    var doubleYellowIcon = "./images/double_yellow_card.png";
    var redIcon = "./images/red_card.png";
    var failedIcon = "./images/pen_failed.png";

    /* Clear previous data */
    $("#list-tips").html("");
    $("#list-match li").not(document.getElementsByClassName("static-li")).remove();

    $(".score").remove();
    $(".time").remove();
    $(".pen").remove();

    var jqxhr = $.ajax({
        url: localStorage.matchUrl + localStorage.matchId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function (data)
		{
            var grid = $("#match-contain");

            // Display match detail
            var block_a_2 = document.createElement("div");
            var block_b_2 = document.createElement("div");
            var block_c_2 = document.createElement("div");

            var block_a_3 = document.createElement("div");
            var block_b_3 = document.createElement("div");
            var block_c_3 = document.createElement("div");

            // Display score
            var isShowResult = data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6;
            var homeGoal = (!isShowResult ? "-" : data[0]._homeGoal);
            var guestGoal = (!isShowResult ? "-" : data[0]._guestGoal);

            $(block_a_2).addClass("ui-block-a");
            $(block_a_2).addClass("score");
            $(block_a_2).append(homeGoal);

            $(block_b_2).addClass("ui-block-b");
            $(block_b_2).addClass("time");
            $(block_b_2).append(data[0]._matchMinute);

            $(block_c_2).addClass("ui-block-c");
            $(block_c_2).addClass("score");
            $(block_c_2).append(guestGoal);

            $(block_a_2).appendTo(grid);
            $(block_b_2).appendTo(grid);
            $(block_c_2).appendTo(grid);

            if ((data[0]._guestPenaltyShoot > 0) || (data[0]._homePenaltyShoot > 0))
			{
                $(block_a_3).addClass("ui-block-a");
                $(block_a_3).append(data[0]._homePenaltyShoot);

                $(block_b_3).addClass("ui-block-b");
                $(block_b_3).addClass("time");
                $(block_b_3).append("PEN");

                $(block_c_3).addClass("ui-block-c");
                $(block_c_3).append(data[0]._guestPenaltyShoot);

                $(block_a_3).appendTo(grid);
                $(block_b_3).appendTo(grid);
                $(block_c_3).appendTo(grid);
            }

            // Stadium display
            $("#stadium").html(data[0]._matchStadium);

            // Real time info
            var info_html = "";
            for (var i = 0; i < data[0]._info_list.length; i++)
			{
                var homeIcon = "";
                var guestIcon = "";

                /* Checking info type */
                var type = data[0]._info_list[i].Type;

                switch (type)
				{
                    //Home Icon 
                    case "10":
                        homeIcon = "<img src='" + goalIcon + "'/>";
                        break;

                    case "20":
                        homeIcon = "<img src='" + yellowIcon + "'/>";
                        break;

                    case "30":
                        homeIcon = "<img src='" + redIcon + "'/>";
                        break;

                    case "40":
                        homeIcon = "<img src='" + doubleYellowIcon + "'/>";
                        break;

                    case "50":
                        homeIcon = "<img src='" + failedIcon + "'/>";
                        break;

                    //Guest Icon 
                    case "01":
                        guestIcon = "<img src='" + goalIcon + "'/>";
                        break;

                    case "02":
                        guestIcon = "<img src='" + yellowIcon + "'/>";
                        break;

                    case "03":
                        guestIcon = "<img src='" + redIcon + "'/>";
                        break;

                    case "04":
                        guestIcon = "<img src='" + doubleYellowIcon + "'/>";
                        break;

                    case "05":
                        guestIcon = "<img src='" + failedIcon + "'/>";
                        break;

                    default:
                        break;
                }

                info_html += "<li class='match-real-time'>" +
					"<div class='ui-grid-d'>" +
					"<div class='ui-block-a'>" + homeIcon + "</div>" +
					"div class='ui-block-b'>" + data[0]._info_list[i].HomeInfo + "</div>" +
					"<div class='ui-block-c'>" + data[0]._info_list[i].Status + "</div>" +
					"<div class='ui-block-d'>" + data[0]._info_list[i].GuestInfo + "</div>" +
					"<div class='ui-block-e'>" + guestIcon + "</div>" +
				"</div></li>";
            }
			
            $("#list-match").append(info_html);

            // Asian tip
            var li = document.createElement("li");
            var grid_a = document.createElement("div");
            var block_b = document.createElement("div");

            $(li).addClass("tips");
            $(grid_a).addClass("ui-grid-a");
            $(block_b).addClass("ui-block-b");

            if (data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6 || data[0]._homeWinHandicap == null || data[0]._handicap == null || data[0]._guestWinHandicap == null)
			{
                $(block_b).append("<div>"
					+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWinHandicap == null ? '' : data[0]._homeWinHandicap) + "</div></a>"
					+ "<div class='betButton ui-disabled ui-btn-inline ui-btn-corner-all' style='border: 1px #a1a1a1 solid; font-weight: normal !important; color: #a1a1a1 !important; background-color: white;'><div>Chấp</div><div>" + (data[0]._handicap == null ? '' : data[0]._handicap) + "</div></div>"
					+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWinHandicap == null ? '' : data[0]._guestWinHandicap) + "</div></a>"
					+ "</div>");
					
                $("#bet_guide").hide();
            }
            else
			{
                $(block_b).append("<div>"
					+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(4, " + (data[0]._homeWinHandicap == null ? '' : data[0]._homeWinHandicap) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWinHandicap == null ? '' : data[0]._homeWinHandicap) + "</div></a>"
					+ "<div class='betButton ui-btn-inline ui-btn-corner-all' style='border: 1px #a1a1a1 solid; font-weight: normal !important; color: #a1a1a1 !important; background-color: white;'><div>Chấp</div><div>" + (data[0]._handicap == null ? '' : data[0]._handicap) + "</div></div>"
					+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(6, " + (data[0]._guestWinHandicap == null ? '' : data[0]._guestWinHandicap) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWinHandicap == null ? '' : data[0]._guestWinHandicap) + "</div></a>"
					+ "</div>");
					
                $("#btnNotification").removeAttr("disabled");
                $("#bet_guide").show();
            }

            var retrievedObject = localStorage.getItem("notificationMatch");
			
            if (retrievedObject != null)
			{
                var notificationMatch = JSON.parse(retrievedObject);
                var i;
                for (i = 0; i < notificationMatch.length; i++) {
                    if (notificationMatch[i] == localStorage.matchId) {
                        $("#btnNotification").attr("disabled", "disabled");
					}
				}
            }

            $(grid_a).append("<div class='ui-block-a'>Tỉ lệ châu Á</div>");
            $(grid_a).append(block_b);
            $(li).append(grid_a);
            $("#list-tips").append(li);

            // european tip
            li = document.createElement("li");
            grid_a = document.createElement("div");
            block_b = document.createElement("div");

            $(li).addClass("tips");
            $(grid_a).addClass("ui-grid-a");
            $(block_b).addClass("ui-block-b");

            if (data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6 || data[0]._homeWin1x2 == null || data[0]._draw1x2 == null || data[0]._guestWin1x2 == null)
			{
                $(block_b).append("<div>"
					+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWin1x2 == null ? '' : data[0]._homeWin1x2) + "</div></a>"
					+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Hòa</div><div>" + (data[0]._draw1x2 == null ? '' : data[0]._draw1x2) + "</div></a>"
					+ "<a class='betButton ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='#' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWin1x2 == null ? '' : data[0]._guestWin1x2) + "</div></a>"
					+ "</div>");
            }
            else
			{
                $(block_b).append("<div>"
					+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(1, " + (data[0]._homeWin1x2 == null ? '' : data[0]._homeWin1x2) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Chủ nhà</div><div>" + (data[0]._homeWin1x2 == null ? '' : data[0]._homeWin1x2) + "</div></a>"
					+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(2, " + (data[0]._draw1x2 == null ? '' : data[0]._draw1x2) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Hòa</div><div>" + (data[0]._draw1x2 == null ? '' : data[0]._draw1x2) + "</div></a>"
					+ "<a class='betButton ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-inline ui-btn-up-e' data-inline='true' data-mini='true' href='javascript:changeToBetPage(3, " + (data[0]._guestWin1x2 == null ? '' : data[0]._guestWin1x2) + ");' data-theme='e' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><div>Khách</div><div>" + (data[0]._guestWin1x2 == null ? '' : data[0]._guestWin1x2) + "</div></a>"
					+ "</div>");
            }

            $(grid_a).append("<div class='ui-block-a'>Tỉ lệ châu Âu</div>");
            $(grid_a).append(block_b);
            $(li).append(grid_a);
            $("#list-tips").append(li);

            li = document.createElement("li");
			
            if (data[0]._matchStatus == 1 || data[0]._matchStatus == 4 || data[0]._matchStatus == 5 || data[0]._matchStatus == 6)
			{
                $(li).append("<div style='margin-top:10px;' class='match-div-p'><a class='ui-disabled ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-up-e' href='#' data-theme='e' data-mini='true' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><span class='ui-btn-inner'><span class='ui-btn-text'>Đặt cược tỉ số</span></span></a></div>");
                $("#btnNotification").attr("disabled", "disabled");
            } else {
                $(li).append("<div style='margin-top:10px;' class='match-div-p'><a class='ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-up-e' href='javascript:changeToBetByScorePage();' data-theme='e' data-mini='true' data-role='button' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span'><span class='ui-btn-inner'><span class='ui-btn-text'>Đặt cược tỉ số</span></span></a></div>");
            }
			
			$("#list-tips").append(li);

            // Bet History
            //displayBetHistory($('#list-tips'));

            // TV Channel
            $("#channel-pannel").html(data[0]._tvChannel);

            // Lineup Info
            LoadLineupInfo(data[0]._lineup_list, data[0]._home_id, data[0]._guest_id);

            // Toggle same number of user bet
            if (data[0]._is_hot == 0) {
                $(".hot-bet").hide();
            } else {
                $(".hot-bet").show();
            }

            LoadConfrontHistory(data);
        },
        complete: function (jqXHR, status)
		{
            $("#match_detail_page ul[data-role='listview']").listview('refresh');
            $("#btnNotification").button("refresh");
            $.mobile.loading("hide");
        }
    });
}

function getNotification()
{
    var jqxhr = $.ajax({
        url: localStorage.saveMatchNotificationRequeset + localStorage.registrationId + "&MatchId=" + localStorage.matchId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (data)
		{
            if (data == 1)
			{
                ShowMessageBox("Thông báo", "Bạn sẽ nhận được thông báo về kết quả trận đấu này khi kết thúc!");

                var retrievedObject = localStorage.getItem("notificationMatch");
				
                if (retrievedObject == null) {
                    var notificationMatch = new Array();
				}
                else {
                    var notificationMatch = JSON.parse(retrievedObject);
				}
				
                notificationMatch[notificationMatch.length] = localStorage.matchId;
                localStorage.setItem("notificationMatch", JSON.stringify(notificationMatch));

                var btnNotification = $("#btnNotification");
                btnNotification.attr("disabled", "disabled").button("refresh");
            }
            else if (data == 0) {
                ShowMessageBox("Thông báo", "Xảy ra lỗi! Xin vui lòng thử lại!");
			}
            else {
                ShowMessageBox("Thông báo", data);
			}
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function useGuestAccount()
{
    if ((typeof localStorage.guest_userId != "undefined") && (localStorage.guest_userId != 0) && (typeof localStorage.guest_username != "undefined") && (localStorage.guest_username != ""))
    {
        localStorage.userId = localStorage.guest_userId;
        localStorage.username = localStorage.guest_username;
        localStorage.userAccount = localStorage.guest_coin;
        $.mobile.changePage("#bet_page");
    }
}

function changeToBetPage(user_bet_type, user_bet)
{
    localStorage.user_bet_type = user_bet_type;
    localStorage.user_bet = user_bet;

    if (typeof localStorage.userId != "undefined" && localStorage.userId != 0) {
		$.mobile.changePage("#bet_page");
	}
    else
	{
        localStorage.visitBetPage = 1;
		$.mobile.changePage("#signin_page");
    }
}

function changeToBetByScorePage()
{
    if (typeof localStorage.userId != "undefined" && localStorage.userId != 0) {
        $.mobile.changePage("#bet_score_page");
	}
    else
	{
        localStorage.visitBetByScorePage = 1;
        $.mobile.changePage("#signin_page");
    }
}

$(document).on("pageinit", "#bet_page", function(event)
{
    $("#txtUserBetValue").keyup(function() {
        validateBet();
    });
});

$(document).on("pageinit", "#bet_score_page", function(event)
{
    $("#txtUserBetScoreValue").keyup(function() {
        validateBetByScore();
    });
});

$(document).on("pagebeforeshow", "#bet_page", function (event)
{
    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.getUserInfoUrl + "?user_id=" + localStorage.userId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (user)
		{
            localStorage.userAccount = user.Account;

            $("#txtUserBetValue").val("");
            var btnBet = $("#btnBet");
            var btnBetAll = $("#btnBetAll");
            btnBet.attr("disabled", "disabled").button("refresh");

            if (localStorage.userAccount <= 0) {
                btnBetAll.attr("disabled", "disabled").button("refresh");
            }
            else {
                btnBetAll.removeAttr("disabled").button("refresh");
            }

			$("#userAccountName").html(localStorage.username);

            $("#homeClubName").html(localStorage.homeClub);
            $("#homeClubLogo").html("<img src='" + localStorage.imgUrl + localStorage.homeLogo + "' width='60px'/>");
            $("#guestClubName").html(localStorage.guestClub);
            $("#guestClubLogo").html("<img src='" + localStorage.imgUrl + localStorage.guestLogo + "' width='60px'/>");

            if (localStorage.user_bet_type == 1 || localStorage.user_bet_type == 2 || localStorage.user_bet_type == 3) {
                $("#betType").html("Châu Âu");
			}
            else if (localStorage.user_bet_type == 4 || localStorage.user_bet_type == 6) {
                $("#betType").html("Châu Á (" + localStorage.handicap + ")");
			}

            if (localStorage.user_bet_type == 1 || localStorage.user_bet_type == 4) {
                $("#userChoice").html(localStorage.homeClub + " thắng");
			}
            else if (localStorage.user_bet_type == 3 || localStorage.user_bet_type == 6) {
                $("#userChoice").html(localStorage.guestClub + " thắng");
			}
            else if (localStorage.user_bet_type == 2) {
                $("#userChoice").html("Hai đội hòa");
			}

            $("#userBet").html(localStorage.user_bet);
            $("#userAccount").html(AddDot(localStorage.userAccount) + " xu");

            localStorage.visitBetPage = 0;
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
});

$(document).on("pagebeforeshow", "#bet_score_page", function (event)
{
    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.getUserInfoUrl + "?user_id=" + localStorage.userId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (user)
		{
            localStorage.userAccount = user.Account;

            $("#txtUserBetScoreValue").val("");
            $("#txtUserSameBet").val("");
            var btnBetScore = $("#btnBetScore");
            var btnBetScoreAll = $("#btnBetScoreAll");
            btnBetScore.attr("disabled", "disabled").button("refresh");

            if (localStorage.userAccount <= 0) {
                btnBetScoreAll.attr("disabled", "disabled").button("refresh");
            }
            else {
                btnBetScoreAll.removeAttr("disabled").button("refresh");
            }

            var html = "<option value='0'>0</option>";
			
            for (var i = 1; i < 11; i++) {
                html += "<option value='" + i + "'>" + i + "</option>";
            }

			$("#userAccountNameScore").html(localStorage.username);
			
            $("#homeScoreGoal, #guestScoreGoal").html(html).selectmenu("refresh");

            $("#homeClubNameScore").html(localStorage.homeClub);
            $("#homeClubLogoScore").html("<img src='" + localStorage.imgUrl + localStorage.homeLogo + "' width='60px'/>");
            $("#guestClubNameScore").html(localStorage.guestClub);
            $("#guestClubLogoScore").html("<img src='" + localStorage.imgUrl + localStorage.guestLogo + "' width='60px'/>");

            $("#userAccountScore").html(AddDot(localStorage.userAccount) + " xu");
            $("#betScoreValue").html(localStorage.goalBet);

            localStorage.visitBetByScorePage = 0;
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
});

function validateBet()
{
    var patt = /^[0-9]*$/;
    var userBetValue = $("#txtUserBetValue").val();
    var btnBet = $("#btnBet");
	
    if (userBetValue.length != 0 && !isNaN(Number(userBetValue)) && Number(userBetValue) <= localStorage.userAccount && localStorage.userAccount > 0 && patt.test(userBetValue) && Number(userBetValue) > 0) {
        btnBet.removeAttr("disabled").button("refresh");
    }
    else {
        btnBet.attr("disabled", "disabled").button("refresh");
    }
}

// Deprecated since version 1.5
function updateUserBetHistory(bet, matchId, betType, betValue, matchHandicap, betHomeGoal, betGuestGoal)
{
    var newBet = new betObj(bet, matchId, betType, betValue, matchHandicap, betHomeGoal, betGuestGoal);
    if (clientUserObject != null) {
        clientUserObject.pushBet(newBet);
    }
}

function bet()
{
    if (Number($("#txtUserBetValue").val()) < localStorage.minimumNormalBetValue && Number(localStorage.userAccount) < localStorage.minimumNormalBetValue)
	{
        ShowConfirmBox("Thông báo", "Bạn phải đặt cược tối thiểu " + localStorage.minimumNormalBetValue + " xu! Bạn có muốn nạp thêm xu?", function() {
            $.mobile.changePage('#charge_page');
        }, function() { });
		
        return;
    }
    else if (Number($("#txtUserBetValue").val()) < localStorage.minimumNormalBetValue && Number(localStorage.userAccount) >= localStorage.minimumNormalBetValue)
	{
        ShowMessageBox("Thông báo", "Bạn phải đặt cược tối thiểu " + localStorage.minimumNormalBetValue + " xu!");
        return;
    }

    var jqxhr = $.ajax({
        url: localStorage.betUrl + localStorage.matchId + "&RegistrationId=" + localStorage.registrationId + "&UserId=" + localStorage.userId + "&BetType=" + localStorage.user_bet_type + "&BetValue=" + $("#txtUserBetValue").val() + "&UserBetString=" + localStorage.user_bet + "&Handicap=" + localStorage.handicap,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (result)
		{
            if (result == 1)
			{
                if (localStorage.user_bet_type == 1 || localStorage.user_bet_type == 4) {
                    message = localStorage.homeClub + " thắng";
				}
                else if (localStorage.user_bet_type == 3 || localStorage.user_bet_type == 6) {
                    message = localStorage.guestClub + " thắng";
				}
                else if (localStorage.user_bet_type == 2) {
                    message = "hai đội hòa";
				}

                ShowMessageBox("Đặt cược", "Bạn đã đặt cược thành công " + $("#txtUserBetValue").val() + " xu cho " + message + "!");
                localStorage.userAccount -= $("#txtUserBetValue").val();

                $("#userAccount").html(localStorage.userAccount + " xu");
                $("#txtUserBetValue").val("");
				
                var btnBet = $("#btnBet");
                btnBet.attr("disabled", "disabled");
                btnBet.button("refresh");
            }
            else if (result == -1) {
                ShowMessageBox("Đặt cược", "Đã hết thời gian đặt cược cho trận đấu!");
			}
            else if (result == -2) {
                ShowMessageBox("Đặt cược", "Tài khoản của bạn không đủ tiền!");
			}
            else {
                ShowMessageBox("Đặt cược", "Quá trình đặt cược xảy ra lỗi! Xin vui lòng thử lại!");
			}
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function betAll()
{
    var jqxhr = $.ajax({
        url: localStorage.betUrl + localStorage.matchId + "&RegistrationId=" + localStorage.registrationId + "&UserId=" + localStorage.userId + "&BetType=" + localStorage.user_bet_type + "&BetValue=" + $("#txtUserBetValue").val() + "&UserBetString=" + localStorage.user_bet + "&Handicap=" + localStorage.handicap,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (result)
		{
            if (result == 1)
			{
                if (localStorage.user_bet_type == 1 || localStorage.user_bet_type == 4) {
                    message = localStorage.homeClub + " thắng";
				}
                else if (localStorage.user_bet_type == 3 || localStorage.user_bet_type == 6) {
                    message = localStorage.guestClub + " thắng";
				}
                else if (localStorage.user_bet_type == 2) {
                    message = "hai đội hòa";
				}

                ShowMessageBox("Đặt cược", "Bạn đã đặt cược thành công " + $("#txtUserBetValue").val() + " xu cho " + message + "!");

                localStorage.userAccount -= $("#txtUserBetValue").val();

                $("#userAccount").html(localStorage.userAccount + " xu");
                $("#txtUserBetValue").val("");
				
                var btnBet = $("#btnBet");
                var btnBetAll = $("#btnBetAll");
                btnBet.attr("disabled", "disabled").button("refresh");
                btnBetAll.attr("disabled", "disabled").button("refresh");
            }
            else if (result == -1) {
                ShowMessageBox("Đặt cược", "Đã hết thời gian đặt cược cho trận đấu!");
			}
            else if (result == -2) {
                ShowMessageBox("Đặt cược", "Tài khoản của bạn không đủ tiền!");
			}
            else {
                ShowMessageBox("Đặt cược", "Quá trình đặt cược xảy ra lỗi! Xin vui lòng thử lại!");
			}
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function validateBetByScore()
{
    var patt = /^[0-9]*$/;

    var betScoreValue = $("#txtUserBetScoreValue").val();
    var homeGoalValue = $("#homeScoreGoal").val();
    var guestGoalValue = $("#guestScoreGoal").val();
    var btnBetScore = $("#btnBetScore");
	
    if (betScoreValue.length != 0 && !isNaN(Number(betScoreValue)) && Number(betScoreValue) <= localStorage.userAccount
            && localStorage.userAccount > 0 && Number(betScoreValue) > 0
            && homeGoalValue.length != 0 && !isNaN(Number(homeGoalValue)) && Number(homeGoalValue) >= 0
            && guestGoalValue.length != 0 && !isNaN(Number(guestGoalValue)) && Number(guestGoalValue) >= 0
            && patt.test(homeGoalValue) && patt.test(guestGoalValue) && patt.test(betScoreValue))
	{
        btnBetScore.removeAttr("disabled").button("refresh");
    }
    else {
        btnBetScore.attr("disabled", "disabled").button("refresh");
    }
}

function betByScore()
{
    if (Number($("#txtUserBetScoreValue").val()) < localStorage.minimumGoalBetValue && Number(localStorage.userAccount) < localStorage.minimumGoalBetValue)
	{
        ShowConfirmBox("Thông báo", "Bạn phải đặt cược tối thiểu " + localStorage.minimumGoalBetValue + " xu! Bạn có muốn nạp thêm xu?", function() {
            $.mobile.changePage('#charge_page');
        }, function() { });
		
        return;
    }
    else if (Number($("#txtUserBetScoreValue").val()) < localStorage.minimumGoalBetValue && Number(localStorage.userAccount) >= localStorage.minimumGoalBetValue)
	{
        ShowMessageBox("Thông báo", "Bạn phải đặt cược tối thiểu " + localStorage.minimumGoalBetValue + " xu!");
        return;
    }
    else if (Number($("#txtUserSameBet").val()) < 0)
	{
        ShowMessageBox("Thông báo", "Số người có cùng dự đoán không hợp lệ!");
        return;
    }

    var jqxhr = $.ajax({
        url: localStorage.betUrl + localStorage.matchId + "&RegistrationId=" + localStorage.registrationId + "&UserId=" + localStorage.userId + "&BetType=7&BetValue=" + $("#txtUserBetScoreValue").val() + "&UserBetString=" + localStorage.goalBet + "&Handicap=" + localStorage.handicap + "&BetHomeGoal=" + $("#homeScoreGoal").val() + "&BetGuestGoal=" + $("#guestScoreGoal").val() + "&SameUserBet=" + ($("#txtUserSameBet").val() == "" ? 0 : $("#txtUserSameBet").val()),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (result)
		{
            if (result == 1)
			{
                ShowMessageBox("Đặt cược", "Bạn đã đặt cược thành công " + $("#txtUserBetScoreValue").val() + " xu với tỉ số " + localStorage.homeClub + " " + $("#homeScoreGoal").val() + " - " + $("#guestScoreGoal").val() + " " + localStorage.guestClub + "!");

                localStorage.userAccount -= $("#txtUserBetScoreValue").val();

                $("#userAccountScore").html(localStorage.userAccount + " xu");
                $("#txtUserBetScoreValue").val("");
                $("#txtUserSameBet").val("");
				
                var btnBetScore = $("#btnBetScore");
                btnBetScore.attr("disabled", "disabled").button("refresh");

                var html = "<option value='0'>0</option>";
                for (var i = 1; i < 11; i++) {
                    html += "<option value='" + i + "'>" + i + "</option>";
                }

                $("#homeScoreGoal, #guestScoreGoal").html(html).selectmenu("refresh");
            }
            else if (result == -1) {
                ShowMessageBox("Đặt cược", "Đã hết thời gian đặt cược cho trận đấu!");
			}
            else if (result == -2) {
                ShowMessageBox("Đặt cược", "Tài khoản của bạn không đủ tiền!");
			}
            else {
                ShowMessageBox("Đặt cược", "Quá trình đặt cược xảy ra lỗi! Xin vui lòng thử lại!");
			}
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function betByScoreAll()
{
    if (Number($("#txtUserSameBet").val()) < 0)
	{
        ShowMessageBox("Thông báo", "Số người có cùng dự đoán không hợp lệ!");
        return;
    }

    var jqxhr = $.ajax({
        url: localStorage.betUrl + localStorage.matchId + "&RegistrationId=" + localStorage.registrationId + "&UserId=" + localStorage.userId + "&BetType=7&BetValue=" + $("#txtUserBetScoreValue").val() + "&UserBetString=" + localStorage.goalBet + "&Handicap=" + localStorage.handicap + "&BetHomeGoal=" + $("#homeScoreGoal").val() + "&BetGuestGoal=" + $("#guestScoreGoal").val() + "&SameUserBet=" + ($("#txtUserSameBet").val() == "" ? 0 : $("#txtUserSameBet").val()),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (result)
		{
            if (result == 1)
			{
                var uBetScoreValue = $("#txtUserBetScoreValue");
                var btnBetScore = $("#btnBetScore");
                var btnBetScoreAll = $("#btnBetScoreAll");

                ShowMessageBox("Đặt cược", "Bạn đã đặt cược thành công " + uBetScoreValue.val() + " xu với tỉ số " + localStorage.homeClub + " " + $("#homeScoreGoal").val() + " - " + $("#guestScoreGoal").val() + " " + localStorage.guestClub + "!");

                localStorage.userAccount -= uBetScoreValue.val();

                $("#userAccountScore").html(localStorage.userAccount + " xu");
                uBetScoreValue.val("");
                $("#txtUserSameBet").val("");
				
                btnBetScore.attr("disabled", "disabled").button("refresh");
                btnBetScoreAll.attr("disabled", "disabled").button("refresh");

                var html = "<option value='0'>0</option>";
                for (var i = 1; i < 11; i++) {
                    html += "<option value='" + i + "'>" + i + "</option>";
                }

                $("#homeScoreGoal, #guestScoreGoal").html(html).selectmenu("refresh");
            }
            else if (result == -1) {
                ShowMessageBox("Đặt cược", "Đã hết thời gian đặt cược cho trận đấu!");
			}
            else if (result == -2) {
                ShowMessageBox("Đặt cược", "Tài khoản của bạn không đủ tiền!");
			}
            else {
                ShowMessageBox("Đặt cược", "Quá trình đặt cược xảy ra lỗi! Xin vui lòng thử lại!");
			}
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadConfrontHistory(data)
{
    // Clear previous data
    $("#list-confront-history").html("");

    var htmlStr = "";
    for (var i = 1; i < data.length; i++)
	{
        var date = ExtractDate(data[i]._matchDatetime);
        var leagueId = parseInt(data[i]._league_row_id);

        if (leagueId <= 5) {
            leagueId = leagueId;
        }
		else if ((5 < leagueId) && (leagueId < 24)) {
            leagueId = 20;
        }
		else if ((24 <= leagueId) && (leagueId <= 46)) {
            leagueId = 42;
        }
		else if ((56 <= leagueId) && (leagueId <= 62)){
            leagueId = 57;
        }
		else {
            leagueId = 0;
        }

        var imgSrc = "./images/league/league_" + leagueId + "x32.png";

        htmlStr += "<li><a href='javascript:changeToMatchDetailPageInHistory(" + data[i]._matchID + ")'> \
			<div class=\"ui-grid-d\"> \
			<div class=\"ui-block-a\">" + ((date.getDate() < 10 ? "0" : "") + date.getDate()) + "/" + (((date.getMonth() + 1) < 10 ? "0" : "") + (date.getMonth() + 1)) + "/" + (date.getFullYear().toString().substring(2, 4)) + "<br />" + ((date.getHours() < 10 ? "0" : "") + date.getHours()) + ":" + ((date.getMinutes() < 10 ? "0" : "") + date.getMinutes()) + ":00" + "</div> \
			<div class=\"ui-block-b\"><img src=" + imgSrc + " /></div> \
			<div class=\"ui-block-c\">" + data[i]._home + "</div> \
			<div class=\"ui-block-d\">" + data[i]._homeGoal + " - " + data[i]._guestGoal + "</div> \
			<div class=\"ui-block-e\">" + data[i]._guest + "</div> \
			</div> \
			</a></li>";
    }
	
    var listConfront = $("#list-confront-history");
    listConfront.append(htmlStr).listview('refresh');

    $("#match_detail_page ul[data-role='listview']").listview('refresh');
	
    // Toggle confrontation list
    if (data.length <= 1) {
        $("#confrontory-pannel").hide();
    } else {
        $("#confrontory-pannel").show();
    }

    $.mobile.loading("hide");
}

function LoadLineupInfo(data, homeId, guestId)
{
    // Clear previous data
    $("#lineup-home").html("");
    $("#lineup-guest").html("");
    $("#sub-home").html("");
    $("#sub-guest").html("");

    var substitution_in = "<img src=\"./images/substitution_in.png\">";
    var substitution_out = "<img src=\"./images/substitution_out.png\">";

    for (var i = 0; i < data.length; i++)
    {
        if (data[i].ClubId == homeId)
        {
            var pSubstitute = "";
            if (data[i].TimeIn != 0) {
				pSubstitute = " " + substitution_in + "<span class=\"sub-time\">" + "(" + data[i].TimeIn  +"')</span> ";
			}
            else if (data[i].TimeOut != 0) {
				pSubstitute = " " + substitution_out + "<span class=\"sub-time\">" + "(" + data[i].TimeOut  +"')</span> ";
			}

            var pNumberStr = "<div class=\"ui-block-a lineup-number\">" + data[i].Number + "</div>";
            var pNameStr = "<div class=\"ui-block-b lineup-name\">" + data[i].Name + pSubstitute + "</div>";

            var htmlStr = pNumberStr + pNameStr;

            switch(data[i].Role)
            {
                case 1: // Start
                    $("#lineup-home").append(htmlStr);
                    break;
					
                case 2: // Substitute
                    $("#sub-home").append(htmlStr);
                    break;
					
                case 3: // Unavailable
                    break;
					
                default:
                    break;
            }
        }
        else if (data[i].ClubId == guestId)
        {
            var pSubstitute = "";
            if (data[i].TimeIn != 0) {
				pSubstitute = substitution_in + "<span class=\"sub-time\">" + "(" + data[i].TimeIn  +"')</span> ";
			}
            else if (data[i].TimeOut != 0) {
				pSubstitute = substitution_out + "<span class=\"sub-time\">" + "(" + data[i].TimeOut  +"')</span> ";
			}

            var pNumberStr = "<div class=\"ui-block-b lineup-number\">" + data[i].Number  + "</div>";
            var pNameStr = "<div class=\"ui-block-a lineup-name\">" + pSubstitute + data[i].Name + "</div>";

            var htmlStr = pNameStr + pNumberStr;

            switch(data[i].Role)
            {
                case 1: // Start
                    $("#lineup-guest").append(htmlStr);
                    break;
					
                case 2: // Substitute
                    $("#sub-guest").append(htmlStr);
                    break;
					
                case 3: // Unavailable
                    break;
					
                default:
                    break;
            }
        }
    }

    // Hide panel incase no data
    if (data.length == 0) {
        $("#lineup-panel").hide();
    } else {
        $("#lineup-panel").show();
    }
}

var matchArray;

function changeToMatchDetailPageInHistory(matchId)
{
    if (localStorage.top == 0) {
        matchArray = new Array();
    }
	
    matchArray[parseInt(localStorage.top)] = localStorage.matchId;
    localStorage.top = parseInt(localStorage.top) + 1;
    ChangeToMatchDetailPage(matchId);
}

function backFromMatchDetailPage()
{
	if (localStorage.top == 0) {
        history.back();
	}
    else
	{
        localStorage.top = parseInt(localStorage.top) - 1;
        ChangeToMatchDetailPage(matchArray[parseInt(localStorage.top)]);
    }
}