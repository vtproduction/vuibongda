function ChangeToResultPageDetail(leagueId)
{
    localStorage.leagueId = leagueId;
    var currentPage = $.mobile.activePage.attr('id');

    if ((currentPage != "national_match_result_page") && (currentPage != "cup_match_result_page")
		&& (currentPage != "ranking_cup_detail_page") && (currentPage != "ranking_national_detail_page")
		&& (currentPage != "player_statistics_page"))
	{
        $.mobile.changePage((leagueId <= 5) ? "#national_match_result_page" : "#cup_match_result_page");
    }
    else
	{
        currentPage = currentPage.replace(((leagueId <= 5) ? "cup" : "national"), "national");
        currentPage = currentPage.replace(((leagueId > 5) ? "national" : "cup"), "cup");

        $.mobile.changePage("#" + currentPage);
        $.mobile.activePage.trigger("pageshow");
    }
}

$(document).on("pageinit", "#national_match_result_page", function(event)
{
    localStorage.nationalResult = 0;

    var $nationalSeason = $("#nationalSeason");
    var $nationalMonth = $("#nationalMonth");
               
    $nationalSeason.change(function()
	{
        var data;

        try {
            data = JSON.parse(localStorage.seasonData);
        }
        catch (err) { };
                                
        if (typeof data != "undefined")
		{
            var selectedMonth = $nationalMonth.val();
            var htmlStr = "";

            for (var i = 0; i < data.length; i++)
			{
                if (data[i].row_id == $nationalSeason.val())
				{
                    var dat = data[i].MonthData;
                    for (var i = 0; i < dat.length; i++)
					{
                        if (dat[i].Month == selectedMonth) {
                            htmlStr += "<option selected=\"selected\" value='" + dat[i].Month + "'>Tháng " + dat[i].Month + "</option>";
						}
                        else {
                            htmlStr += "<option value='" + dat[i].Month + "'>Tháng " + dat[i].Month + "</option>";
						}
                    }
                }
			}
			
            $nationalMonth.html(htmlStr).selectmenu("refresh");
        }

        localStorage.nationalSeason = $nationalSeason.val();
        LoadNationalResult($nationalSeason.val(), localStorage.leagueId, $nationalMonth.val());
    });

    $nationalMonth.change(function()
	{
        localStorage.nationalMonth = $nationalMonth.val();
        LoadNationalResult($nationalSeason.val(), localStorage.leagueId, $nationalMonth.val());
    });
});

$(document).on("pageinit", "#cup_match_result_page", function(event)
{
    localStorage.cupResult = 0;

    var $cupSeason = $("#cupSeason");
    var $roundType = $("#roundType");
               
    $cupSeason.change(function()
	{
		var cupSeasonValue = $cupSeason.val();
        localStorage.cupSeason = cupSeasonValue;
        LoadCupResult(cupSeasonValue, localStorage.leagueId, $roundType.val());
    });

    $roundType.change(function()
	{
		var roundTypeValue = $roundType.val();
        localStorage.roundType = roundTypeValue;
        LoadCupResult($cupSeason.val(), localStorage.leagueId, roundTypeValue);
    });
});

$(document).on("pageshow", "#national_match_result_page", function(event)
{
	$("#national_match_result_header_title").html(LeagueName[localStorage.leagueId - 1]);
	LoadNationalMatchResult();
});

$(document).on("pageshow", "#cup_match_result_page", function(event)
{
	$("#cup_match_result_header_title").html(LeagueName[(localStorage.leagueId - 1) > 6 ? 6 : (localStorage.leagueId - 1)]);
	LoadCupMatchResult();
});

function LoadNationalMatchResult()
{
    $.mobile.loading("show");
    var season_row_id;
    console.log(localStorage.seasonUrl + localStorage.leagueId)
    var jqxhr = $.ajax({
        url: localStorage.seasonUrl + localStorage.leagueId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            //Sort data by season
            console.log(localStorage.seasonUrl + localStorage.leagueId);
            data.sort(function(a, b) {
                return (b.season_name > a.season_name);
            })

            localStorage.seasonData = JSON.stringify(data);

            var htmlStr = "";
            var monthData;

            for (var i = 0; i < data.length; i++)
			{
                if (i == 0) {
                    monthData = data[i].MonthData;
				}

                if (localStorage.nationalResult == 0)
				{
                    if (data[i].IsCurrent == 1)
					{
                        htmlStr += "<option selected=\"selected\" value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
                        monthData = data[i].MonthData;
                    }
                    else {
                        htmlStr += "<option value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
					}
                }
                else
				{
                    if (data[i].row_id == localStorage.nationalSeason)
					{
                        htmlStr += "<option selected=\"selected\" value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
                        monthData = data[i].MonthData;
                    }
                    else {
                        htmlStr += "<option value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
					}
                }
            }
			
			var nationalSeason = $("#nationalSeason");
            nationalSeason.html(htmlStr).selectmenu("refresh");

            htmlStr = "";

            for (var i = 0; i < monthData.length; i++)
			{
                if (localStorage.nationalResult == 0)
				{
                    if (monthData[i].IsCurrent == 1) {
                        htmlStr += "<option selected=\"selected\" value='" + monthData[i].Month + "'>Tháng " + monthData[i].Month + "</option>";
					}
                    else {
                        htmlStr += "<option value='" + monthData[i].Month + "'>Tháng " + monthData[i].Month + "</option>";
					}
                }
                else
				{
                    if (monthData[i].Month == localStorage.nationalMonth) {
                        htmlStr += "<option selected=\"selected\" value='" + monthData[i].Month + "'>Tháng " + monthData[i].Month + "</option>";
					}
                    else {
                        htmlStr += "<option value='" + monthData[i].Month + "'>Tháng " + monthData[i].Month + "</option>";
					}
                }
            }
			
			var nationalMonth = $("#nationalMonth");
            nationalMonth.html(htmlStr).selectmenu("refresh");

            localStorage.nationalResult = 0;
            localStorage.nationalSeason = nationalSeason.val();
            localStorage.nationalMonth = nationalMonth.val();
            LoadNationalResult(nationalSeason.val(), localStorage.leagueId, nationalMonth.val());
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadNationalResult(season_row_id, league_row_id, month)
{
    $.mobile.loading("show");
    var $national_match_result_content = $("#national_match_result_content");
    
    $national_match_result_content.html("");
    
    var htmlStr = "";
    var tmp = null;
    var d = null;
    var isFirst = true;
    var index = 0;
    var str = localStorage.nationalMatchResultUrl + "season_row_id=" + season_row_id + "&league_row_id=" + league_row_id + "&month=" + month + "&userId=" + ((localStorage.userId == undefined) ? 0 : localStorage.userId);
    console.log(str);
    $.ajax({

		url: localStorage.nationalMatchResultUrl + "season_row_id=" + season_row_id + "&league_row_id=" + league_row_id + "&month=" + month + "&userId=" + ((localStorage.userId == undefined) ? 0 : localStorage.userId),
		type: "GET",
		cache: false,
		dataType: "jsonp",
		success: function(data)
		{
			for (var i = 0; i < data.length; i++)
			{
				tmp = d;
				d = ExtractDate(data[i]._matchDatetime);
           
				var betHtml = ((data[i]._isBet == 1) ? " bet-match" : "");
				if (data[i]._matchStatus == 1 || data[i]._matchStatus == 4 || data[i]._matchStatus == 5 || data[i]._matchStatus == 6)
				{
					if (i % 2 == 0)
					{
						htmlStr += "<div class='match-result-even-row" + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div>" + "<a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
					else
					{
						htmlStr += "<div class='match-result-odd-row" + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div>" + "<a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
				}
				else
				{
					var anchor = (isFirst == true) ? "anchor" : "";
					if (isFirst == true) {
						index = i;
					}
           
					isFirst = false;
					if (i % 2 == 0)
					{
						htmlStr += "<div class='match-result-even-row " + anchor + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='split-without-score'>-</span></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
					else
					{
						htmlStr += "<div class='match-result-odd-row " + anchor + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='split-without-score'>-</span></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
				}
           
				if (htmlStr != "") {
					htmlStr += "<div style='border-top: 1px #DDDDDD solid'></div>";
				}
           
				$national_match_result_content.html(htmlStr);
			}
		},
	   complete: function(jqXHR, status)
	   {
			if (isFirst == false && index != 0)
			{
				var target = $("#national_match_result_page .anchor");
				var anchor = target.offset().top;
				anchor = anchor - target.outerHeight(true) - target.innerHeight();
				setTimeout(function() {
					$.mobile.silentScroll(anchor);
				}, 500);
			}
	   
			$.mobile.loading("hide");
	   }
	});
}

function LoadCupMatchResult()
{
    $.mobile.loading("show");
    var season_row_id;

    var jqxhr = $.ajax({
        url: localStorage.seasonUrl + localStorage.leagueId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            var htmlStr = "";
            for (var i = 0; i < data.length; i++)
			{
                if (localStorage.cupResult == 0)
				{
                    if (data[i].IsCurrent == 1) {
                        htmlStr += "<option selected=\"selected\" value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
					}
                    else {
                        htmlStr += "<option value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
					}
                }
                else
				{
                    if (data[i].row_id == localStorage.cupSeason) {
                        htmlStr += "<option selected=\"selected\" value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
					}
                    else {
                        htmlStr += "<option value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
					}
                }
            }

            localStorage.cupResult = 0;
            var cupSeason = $("#cupSeason");

            cupSeason.html(htmlStr).selectmenu("refresh");

            LoadCupResult(cupSeason.val(), localStorage.leagueId, $("#roundType").val());
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadCupResult(season_row_id, league_row_id, roundType)
{
    $.mobile.loading("show");

    var htmlStr = "";
    var tmp = null;
    var d = null;
    var resultStr;
    var isFirst = true;
    var index = 0;

    var jqxhr = $.ajax({
        url: localStorage.cupMatchResultUrl + "season_row_id=" + season_row_id + "&league_row_id=" + league_row_id + "&roundType=" + roundType + "&userId=" + ((localStorage.userId == undefined) ? 0 : localStorage.userId),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            for (var i = 0; i < data.length; i++)
			{
                tmp = d;
                d = ExtractDate(data[i]._matchDatetime);
                var betHtml = ((data[i]._isBet == 1) ? " bet-match" : "");

                if (data[i]._homePenaltyShoot != 0 && data[i]._guestPenaltyShoot != 0)
				{
                    if (data[i]._homePenaltyShoot > data[i]._guestPenaltyShoot) {
                        resultStr = "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "<span class='penalty'>(*)</span></a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div>";
                    }
					else {
                        resultStr = "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "<span class='penalty'>(*)</span></a></div>";
					}
				}
                else {
                    resultStr = "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div>";
				}
					
                if (data[i]._matchStatus == 1 || data[i]._matchStatus == 4 || data[i]._matchStatus == 5 || data[i]._matchStatus == 6)
				{
                    if (i % 2 == 0)
					{
                        htmlStr += "<div class='match-result-even-row" + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ resultStr + "<a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
                    }
					else
					{
                        htmlStr += "<div class='match-result-odd-row" + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ resultStr + "<a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
				}
                else
				{
                    var anchor = (isFirst == true) ? "anchor" : "";
                    if (isFirst == true) {
                        index = i;
                    }

                    isFirst = false;
                    if (i % 2 == 0)
					{
                        htmlStr += "<div class='match-result-even-row " + anchor + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='split-without-score'>-</span></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
                    }
					else
					{
                        htmlStr += "<div class='match-result-odd-row " + anchor + betHtml + "'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ "<div class='home-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='split-without-score'>-</span></a><div class='guest-club-name'><a href='javascript:ChangeToClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a style='padding-left: 10px; padding-bottom: 15px;' href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
				}
            }

            if (htmlStr != "") {
                htmlStr += "<div style='border-top: 1px #DDDDDD solid'></div>";
			}

            $("#cup_match_result_content").html(htmlStr);
        },
        complete: function(jqXHR, status)
		{
            if ((isFirst == false) && (index != 0))
			{
                var target = $("#cup_match_result_page .anchor");
                var anchor = target.offset().top;
				
                anchor = anchor - target.outerHeight(true) - target.innerHeight();
                setTimeout(function() {
                    $.mobile.silentScroll(anchor)
                }, 500);
            }

            $.mobile.loading("hide");
        }
    });
}

$(document).on("pageinit", "#club_match_result_page", function(event)
{
    var $clubSeason = $("#clubSeason");
    
    $clubSeason.change(function()
	{
		var value = $clubSeason.val();
        localStorage.season_row_id = value;
        LoadClubResult(value, localStorage.club_row_id);
    });
});

function ChangeToClubMatchResultPage(club_row_id, season_row_id, club_name)
{
    localStorage.club_row_id = club_row_id;
    localStorage.season_row_id = season_row_id;
    localStorage.club_name = club_name;
    $.mobile.changePage("#club_match_result_page");
}

function ReloadClubMatchResultPage(club_row_id, season_row_id, club_name)
{
    localStorage.club_row_id = club_row_id;
    localStorage.season_row_id = season_row_id;
    localStorage.club_name = club_name;
    $("#club_match_result_header_title").html(localStorage.club_name);
    LoadClubMatchResult();
}

$(document).on("pageshow", "#club_match_result_page", function(event)
{
    $("#club_match_result_header_title").html(localStorage.club_name);
    LoadClubMatchResult();
});

function LoadClubMatchResult()
{
    $.mobile.loading("show");

    var htmlStr = "";
    var jqxhr = $.ajax({
        url: localStorage.seasonUrl + "6",
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            //Sort data by season
            data.sort(function(a, b) {
                return (b.season_name > a.season_name);
            })

            var htmlStr = "";
            for (var i = 0; i < data.length; i++)
			{
                if (data[i].row_id == localStorage.season_row_id) {
                    htmlStr += "<option selected=\"selected\" value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
				}
                else {
                    htmlStr += "<option value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
				}
            }
			
			var clubSeason = $("#clubSeason");
            clubSeason.html(htmlStr).selectmenu("refresh");

            LoadClubResult(clubSeason.val(), localStorage.club_row_id);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadClubResult(season_row_id, club_row_id)
{
    localStorage.nationalResult = 1;
    localStorage.cupResult = 1;
    $.mobile.loading("show");

    var htmlStr = "";
    var tmp = null;
    var d = null;
    var imgStr;
    var resultStr;

    var jqxhr = $.ajax({
        url: localStorage.matchOfClubBySeason + "&season_row_id=" + season_row_id + "&club_row_id=" + club_row_id,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            for (var i = 0; i < data.length; i++)
			{
                tmp = d;
                d = ExtractDate(data[i]._matchDatetime);
                var betHtml = ((data[i]._isBet == 1) ? " bet-match" : "");

                if (data[i]._homePenaltyShoot != 0 && data[i]._guestPenaltyShoot != 0)
				{
                    if (data[i]._homePenaltyShoot > data[i]._guestPenaltyShoot) {
                        resultStr = "<div class='home-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "<span class='penalty'>(*)</span></a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div>";
                    }
					else {
                        resultStr = "<div class='home-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "<span class='penalty'>(*)</span></a></div>";
					}
				}
                else {
                    resultStr = "<div class='home-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><div class='home-club-goal'>" + (data[i]._homeGoal) + "</div><span class='split'>-</span><div class='guest-club-goal'>" + (data[i]._guestGoal) + "</div></a><div class='guest-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div>";
				}
				
                if (data[i]._league_row_id < 6) {
                    imgStr = "<img src='images/league/league_" + data[i]._league_row_id + "x32.png' class='league-icon'/>";
				}
                else if (data[i]._league_row_id >= 7 && data[i]._league_row_id <= 23) {
                    imgStr = "<img src='images/league/league_20x32.png' class='league-icon'/>";
				}
                else if (data[i]._league_row_id >= 25 && data[i]._league_row_id <= 46) {
                    imgStr = "<img src='images/league/league_42x32.png' class='league-icon'/>";
				}
                else if (data[i]._league_row_id >= 56 && data[i]._league_row_id <= 62) {
                    imgStr = "<img src='images/league/league_57x32.png' class='league-icon' />"
				}
                else {
                    imgStr = "<img src='images/league/league_0x32.png' class='league-icon' />";
				}

                if (data[i]._matchStatus == 1 || data[i]._matchStatus == 4 || data[i]._matchStatus == 5 || data[i]._matchStatus == 6)
				{
                    if (i % 2 == 0)
					{
                        htmlStr += "<div class='match-result-even-row'><div class='match-time'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ imgStr
							+ resultStr + "<a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
                    }
					else
					{
                        htmlStr += "<div class='match-result-odd-row'><div class='match-time'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ imgStr
							+ resultStr + "<a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
				}
                else
				{
                    if (i % 2 == 0)
					{
                        htmlStr += "<div class='match-result-even-row'><div class='match-time'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ imgStr
							+ "<div class='home-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='split-without-score'>-</span></a><div class='guest-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
                    }
					else
					{
                        htmlStr += "<div class='match-result-odd-row'><div class='match-time'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>"
							+ imgStr
							+ "<div class='home-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._home_id + ", " + season_row_id + ", \"" + data[i]._home + "\")'>" + data[i]._home + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='split-without-score'>-</span></a><div class='guest-club-name'><a href='javascript:ReloadClubMatchResultPage(" + data[i]._guest_id + ", " + season_row_id + ", \"" + data[i]._guest + "\")'>" + data[i]._guest + "</a></div><a href='javascript:ChangeToMatchDetailPage(" + data[i]._matchID + ")'><span class='detail-icon'></span></a></div>";
					}
				}
            }

            if (htmlStr != "") {
                htmlStr += "<div style='border-top: 1px #DDDDDD solid'></div>";
			}

            $("#club_match_result_content").html(htmlStr);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}