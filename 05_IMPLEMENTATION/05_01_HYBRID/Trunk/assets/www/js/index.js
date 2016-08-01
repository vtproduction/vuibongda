// Deprecated since version 1.5
function LoadUserBetList()
{
    if (typeof localStorage.userId != "undefined" && localStorage.userId != 0) // Check to load uesr bet history
	{
        var jqxhr = $.ajax({
            url: localStorage.listUserBet + localStorage.userId,
            type: "POST",
            cache: false,
            dataType: "jsonp",
            success: function(data)
			{
                if (data != null && data.length > 0)
				{
                    if (clientUserObject == null) {
                        clientUserObject = new userObj();
                    }
                    clientUserObject.setBetList(data);
                }
            },
            complete: function(jqXHR, status) { }
        });
    }
}



/* ===================== News page ====================== */

function LoadNewsData() {
    $.mobile.loading("show");
    $listNews = $("#list-news");

    var jqxhr = $.ajax({
        url: localStorage.newsUrl,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            $listNews.html("");
            $listNews.listview("refresh");

            try {
                $(".flexslider").flexslider("pause");
            } catch (err) { }

            $("#slideshow_panel").html("<div class='flexslider'><ul class='slides' id='flexslider_slides'></ul></div>");
            var $flexslide_slides = $("#flexslider_slides");
            $flexslide_slides.html("");
	    
			var html1 = "", html2 = "";
	    
            for (var i = 0; i < data.length; i++)
			{
                html1 += "<li><a href='javascript:LoadNewsDetail(" + i + ")'><img src='" + data[i].ImageLink + "' class='feed-image'/><h2 class='feed-title'>" + data[i].Title + "</h2></a></li>";
                html2 += "<li><a href='javascript:LoadNewsDetail(" + i + ")'><img src='" + data[i].ImageLink + "' /><p class='flex-caption'>" + data[i].Title + "</p></a></li>";
            }
	    
			$listNews.html(html1);
			$flexslide_slides.html(html2);
	    
            $listNews.listview("refresh");
	    
            $(".flexslider").flexslider({
                animation: "slide",
                slideshowSpeed: 4500,
                directionNav: false,
                controlNav: true,
                touchSwipe: true,
                pauseOnAction: false,
                controlsContainer: ".carousel"
            });

            localStorage.newsData = JSON.stringify(data);
        },
        complete: function() {
            $.mobile.loading("hide");
        }
    });
}

$(document).on("pageshow", "#news_page", function(event) {
    LoadNewsData();
});

/* ===================== End News page ====================== */



/* ===================== News detail page ====================== */

function LoadNewsDetail(i)
{
    localStorage.newsDetailItem = i;
    $.mobile.changePage("#news_detail");
}

$(document).on("pageshow", "#news_detail", function(event)
{
    try
	{
        var data = JSON.parse(localStorage.newsData);

        $("#header-title").html(Ellipsify(data[localStorage.newsDetailItem].Title));

        $("#news_content_detail").html("<h4>" + data[localStorage.newsDetailItem].Title + "</h4>");
        $("#news_content_detail").append(data[localStorage.newsDetailItem].Content);

        localStorage.CurrentNewsTitle = data[localStorage.newsDetailItem].Title;
        localStorage.CurrentNewsImage = data[localStorage.newsDetailItem].ImageLink;
        localStorage.CurrentNewsDescription = data[localStorage.newsDetailItem].Description;
    }
    catch (err) { }
});

/* ===================== End News detail page ====================== */



/* ===================== Ranking table page ====================== */

function ChangeToRankingPageDetail(leagueId)
{
    localStorage.leagueId = leagueId;

    if (leagueId <= 5)
	{
        if ($.mobile.activePage.attr("id") == "ranking_national_detail_page") {
            $.mobile.activePage.trigger("pageshow");
		}
        else {
            $.mobile.changePage("#ranking_national_detail_page");
		}
    }
    else
	{
        if ($.mobile.activePage.attr("id") == "ranking_cup_detail_page") {
            $.mobile.activePage.trigger("pageshow");
		}
        else {
            $.mobile.changePage("#ranking_cup_detail_page");
		}
    }
}

$(document).on("pageinit", "#ranking_national_detail_page", function(event)
{
    $("#select-standing-league-season").change(function()
	{
        var seasonId = localStorage.seasonId = this.options[this.selectedIndex].value;
        LoadNationalSeasonRankingTable(seasonId);
    });
});

$(document).on("pageinit", "#ranking_cup_detail_page", function(event)
{
    $("#select-season-standing-cup").change(function()
	{
        var seasonId = this.options[this.selectedIndex].value;
        localStorage.seasonId = seasonId;
        LoadSeasonCupRankingTable(0, seasonId);
    });
});

$(document).on("pageshow", "#ranking_national_detail_page", function(event)
{
    $("#national_detail_header_title").html(LeagueName[localStorage.leagueId - 1]);
    SeasonList("select-standing-league-season");
    LoadNationalRankingTable();
});

$(document).on("pageshow", "#ranking_cup_detail_page", function(event)
{
    $("#cup_detail_header_title").html(LeagueName[(localStorage.leagueId - 1) > 6 ? 6 : (localStorage.leagueId - 1)]);
    SeasonList("select-season-standing-cup");
    LoadCupRankingTable(0);
});

function SeasonList(select_menu, callback)
{
    $.mobile.loading("show");
    var season_row_id;
	var current_season = 0;
	
    var jqxhr = $.ajax({
        url: localStorage.seasonUrl + localStorage.leagueId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            data.sort(function(a, b) {
                return (b.season_name > a.season_name);
            })

            if (data.length > 0) {
                localStorage.seasonId = data[0].row_id;
            }

            var htmlStr = "";
            for (var i = 0; i < data.length; i++) {
				if (data[i].IsCurrent == 1) current_season = data[i].row_id;
                htmlStr += "<option value='" + data[i].row_id + "'>" + data[i].season_name + "</option>";
            }

            var selector = "#" + select_menu;
            $(selector).html(htmlStr);
            $(selector).selectmenu("refresh");

            htmlStr = "";
        },
        complete: function(jqXHR, status)
		{
            $.mobile.loading("hide");
			if (typeof callback !== "undefined") {
				callback(current_season);
			}
        }
    });
}

function LoadNationalRankingTable()
{
    var $national_ranking_table_panel = $("#national_ranking_table_panel");
    $national_ranking_table_panel.html("");
    $.mobile.loading("show");
	
    var jqxhr = $.ajax({
        url: localStorage.standingUrl + localStorage.leagueId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            var htmlStr = "";
            for (var i = 0; i < data.length; i++)
			{
                htmlStr = htmlStr + "<tr>" +
					"<td>" + (i + 1) + "</td>" +
					"<td><img src=\"" + data[i]._logo + "\" alt=\"\" style=\"vertical-align: middle; float:left; padding-right:5px; width:30px\" />" + "<a style=\"color:#666;\" href=\"javascript:ChangeToClubDetailPage(" + data[i]._club_id + ")\">" + data[i]._club_name + "</a></td>" +
					"<td style='text-align:center;'>" + data[i]._total_match + "</td>" +
					"<td style='text-align:center;'>" + (parseInt(data[i]._home_goal_win) + parseInt(data[i]._guest_goal_win)) + ":" + (parseInt(data[i]._home_goal_lose) + parseInt(data[i]._guest_goal_lose)) + "</td>" +
					"<td style='text-align:center;'>" + data[i]._goal_win_lose + "</td>" +
					"<td style='text-align:center;'>" + data[i]._mark + "</td>" +
				"</tr>";
            }

            $national_ranking_table_panel.html(htmlStr);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadNationalSeasonRankingTable(seasonId)
{
    var $national_ranking_table_panel = $("#national_ranking_table_panel");
    $national_ranking_table_panel.html("");
    $.mobile.loading("show");
	
    var jqxhr = $.ajax({
        url: localStorage.seasonStandingUrl + localStorage.leagueId + "&SeasonId=" + seasonId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            var htmlStr = "";
            for (var i = 0; i < data.length; i++)
			{
                htmlStr = htmlStr + "<tr>" +
					"<td>" + (i + 1) + "</td>" +
					"<td> <img src=\"" + data[i]._logo + "\" alt=\"\" style=\"vertical-align: middle; float:left;padding-right:5px; width:30px\" />" + "<a style=\"color:#666;\" href=\"javascript:ChangeToClubDetailPage(" + data[i]._club_id + ")\">" + data[i]._club_name + "</a></td>" +
					"<td style='text-align:center;'>" + data[i]._total_match + "</td>" +
					"<td style='text-align:center;'>" + (parseInt(data[i]._home_goal_win) + parseInt(data[i]._guest_goal_win)) + ":" + (parseInt(data[i]._home_goal_lose) + parseInt(data[i]._guest_goal_lose)) + "</td>" +
					"<td style='text-align:center;'>" + data[i]._goal_win_lose + "</td>" +
					"<td style='text-align:center;'>" + data[i]._mark + "</td>" +
				"</tr>";
            }

            $national_ranking_table_panel.html(htmlStr);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadCupRankingTable(group)
{
    var $select_group = $("#select-group");
    $select_group.find("option").remove();
    $select_group.selectmenu("refresh");
	
    var $cup_ranking_table_panel = $("#cup_ranking_table_panel");
    $cup_ranking_table_panel.html("");

    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.groupStandingUrl + localStorage.leagueId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            localStorage.groupTableData = JSON.stringify(data);
            for (var i = 0; i < data.length; i++) {
                $select_group.append(new Option(data[i]._League_Name, i));
            }

            $("#select-group option[value=" + group + "]").attr("selected", "selected");
            $select_group.selectmenu("refresh");
	    
            ShowCupTableRanking(group);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function LoadSeasonCupRankingTable(group, seasonId)
{
    var $select_group = $("#select-group");
    $select_group.find("option").remove();
    $select_group.selectmenu("refresh");
	
    var $cup_ranking_table_panel = $("#cup_ranking_table_panel");
    $cup_ranking_table_panel.html("");

    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.seasonGroupStandingUrl + localStorage.leagueId + "&SeasonId=" + seasonId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            localStorage.groupTableData = JSON.stringify(data);

            for (var i = 0; i < data.length; i++) {
                $select_group.append(new Option(data[i]._League_Name, i));
            }

            $("#select-group option[value=" + group + "]").attr("selected", "selected");
            $select_group.selectmenu("refresh");

            ShowCupTableRanking(group);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

function ShowCupTableRanking(group)
{
    try
	{
        $("#ranking_table_cup_refresh_btn").attr("href", "javascript:LoadSeasonCupRankingTable(" + group + ", " + localStorage.seasonId + ")");
        var $cup_ranking_table_panel = $("#cup_ranking_table_panel");
		$cup_ranking_table_panel.html("");

        var dat = JSON.parse(localStorage.groupTableData);
        var data = dat[group]._LStanding;
        var htmlStr = "";
		
        for (var i = 0; i < data.length; i++)
		{
            htmlStr = htmlStr + "<tr>" +
				"<td>" + (i + 1) + "</td>" +
				"<td> <img src=\"" + data[i]._logo + "\" alt=\"\" style=\"vertical-align: middle; height:30px\" />&nbsp;&nbsp;&nbsp;" + "<a style=\"color:#666;\" href=\"javascript:ChangeToClubDetailPage(" + data[i]._club_id + ")\">" + data[i]._club_name + "</a></td>" +
				"<td style='text-align:center;'>" + data[i]._total_match + "</td>" +
				"<td style='text-align:center;'>" + (parseInt(data[i]._home_goal_win) + parseInt(data[i]._guest_goal_win)) + ":" + (parseInt(data[i]._home_goal_lose) + parseInt(data[i]._guest_goal_lose)) + "</td>" +
				"<td style='text-align:center;'>" + data[i]._goal_win_lose + "</td>" +
				"<td style='text-align:center;'>" + data[i]._mark + "</td>" +
			"</tr>";
        }

        $cup_ranking_table_panel.html(htmlStr);
    }
    catch (err) { }
}

/* ===================== End Ranking page ================ */



/* ===================== Fixture page ================ */

$(document).on("pageshow", "#fixture_page", function(event)
{
	try
	{
		localStorage.expiredObject = "";
		CheckExpiredDate(function()
		{
			var expiredObject = JSON.parse(localStorage.expiredObject);
	
			if (expiredObject.status == 0)
			{
				$.mobile.changePage("#extend_expiration_page");
				$("#notificationMessage").html("Thiết bị của bạn đã hết hạn sử dụng, vui lòng gia hạn để sử dụng được đầy đủ các chức năng.");
				ShowExpirationTimeOnAccountPage("Hết hạn");
			}
			else if (expiredObject.status == 1)
			{
				if (expiredObject.leftDay <= 3) {
					$.notify("Thiết bị còn " + expiredObject.leftDay + " ngày sử dụng", { className: "info", clickToHide: true, globalPosition: 'bottom right',});
				}
				ShowExpirationTimeOnAccountPage(expiredObject.leftDay + " ngày");
				LoadFixture();
			}
			else {
				LoadFixture();
			}
	    
			$('#expirationTime').html("");
		});
    }
    catch (e)
	{
		LoadFixture();
		$("#expirationTime").html("");
    }
});


function LoadFixture()
{
    var $fixture_content_panel = $("#fixture_content_panel");
    $fixture_content_panel.html("");

    $.mobile.loading("show");
    $.ajax({
        url: localStorage.fixtureUrl + "&userId=" + ((localStorage.userId == undefined) ? 0 : localStorage.userId),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            var sortLeague = new Array();

            for (var i = 0; i < data.length; i++)
			{
                var tmp = null;
                var d = null;
                var leagueDiv = MakeLeague(data[i]._leagueID);

                for (var j = 0; j < data[i]._matches.length; j++)
				{
                    tmp = d;
                    d = ExtractDate(data[i]._matches[j]._matchDatetime);
                    var betHtml = (data[i]._matches[j]._isHot == 1) ? "class='campaign-hot-match'" : ((data[i]._matches[j]._isBet == 1) ? "class='bet-match'" : "");

                    /* Group the matches in one day */
                    if ((tmp) && ((d.getDate() - tmp.getDate() == 0) && (d.getMonth() - tmp.getMonth() == 0) && (d.getFullYear() - tmp.getFullYear() == 0))){
                        listDivider = "";
                    }
                    else
					{
                        var listDivider = "<li data-role='list-divider'" + "league-id='" + data[i]._leagueID + "'>"
							+ "<span class='fixture-day'>" + weekday[d.getDay()] + "</span>" + d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear()
							+ "</li>";
                    }

                    var isShowResult = data[i]._matches[j]._matchStatus == 1 || data[i]._matches[j]._matchStatus == 4 || data[i]._matches[j]._matchStatus == 5 || data[i]._matches[j]._matchStatus == 6;

                    if (isShowResult && (data[i]._matches[j]._homePenaltyShoot > 0 || data[i]._matches[j]._guestPenaltyShoot > 0))
					{
                        if (data[i]._matches[j]._homePenaltyShoot > data[i]._matches[j]._guestPenaltyShoot) {
                            data[i]._matches[j]._home = data[i]._matches[j]._home + "<span class='fixture-winner'> *</span>";
						}
                        else if (data[i]._matches[j]._homePenaltyShoot < data[i]._matches[j]._guestPenaltyShoot) {
                            data[i]._matches[j]._guest = data[i]._matches[j]._guest + "<span class='fixture-winner'> *</span>";
						}

                        data[i]._matches[j]._matchMinute = "PEN";
                    }

                    var listView = "<li data-icon='false' " + betHtml + ">" +
						"<a href=\"javascript:ChangeToMatchDetailPage(" + data[i]._matches[j]._matchID + ")\">" +
						"<span class='fixture-time'>" + d.getHours() + ":" + ((d.getMinutes() < 10 ? "0" : "") + d.getMinutes()) + "</span>" +
						"<div class='fixture-match'>" +
						"<div class='match-container'>" +
						"<p><span class=\"match_team\">" + data[i]._matches[j]._home + "</span><span class='match-score'>" + (!isShowResult ? "-" : data[i]._matches[j]._homeGoal) + "</span></p>" +
						"<p class='guest_club'><span class=\"match_team\">" + data[i]._matches[j]._guest + "</span><span class='match-score'>" + (!isShowResult ? "-" : data[i]._matches[j]._guestGoal) + "</span></p>" +
						"</div>" +
						"</div>" +
						"<span class='match-play'>" + data[i]._matches[j]._matchMinute + "</span>" +
						"</a>" +
					"</li>";

                    $(leagueDiv.children["1"]).append(listDivider + listView);
                }
				
                sortLeague.push(leagueDiv);
            }

			var length = $(sortLeague).length;
			for (var i=0; i<length; i++) {
				$(sortLeague[i]).appendTo($fixture_content_panel);
			}

            $(".league_collapsible").collapsible();
            $("#fixture_page ul.league_collapsible_item").listview();
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        },
        error: function(data) {
            ShowMessageBox("Cảnh báo", "Tải nội dung về không thành công! Xin vui lòng kiểm tra lại kết nối mạng!");
        },
        timeout: 20000  // Timeout after 20 seconds
    });
}

function MakeLeague(leagueId)
{
    var div = document.createElement("div");
	
    $(div).attr(
	{
        "data-role": "collapsible", "data-collapsed": "false", "data-theme": "b", "data-content-theme": "b",
        "data-inset": "false", "data-collapsed-icon": "arrow-r", "data-expanded-icon": "arrow-d", "data-iconpos": "right",
        "class": "league_collapsible"
    });

    var h6 = document.createElement("h6");	// Create h6 tag
    var img = document.createElement("img");	//Create img tag
    var colorTag = document.createElement("div");

    if (leagueId <= 5) {
        img.src = "images/league/league_" + leagueId + "x32.png";
	}
    else if (leagueId <= 23) {
        img.src = "images/league/league_20x32.png";
	}
    else if (leagueId <= 46) {
        img.src = "images/league/league_42x32.png";
	}
    else if (leagueId <= 62) {
        img.src = "images/league/league_57x32.png";
	}
    else {
        img.src = "images/league/league_0x32.png";
	}

    $(img).addClass("ui-league-img");
    $(colorTag).addClass("colorTag");
    $(colorTag).addClass("colorTag-" + leagueId);
    $(h6).append(colorTag);
    $(h6).append(img);	//Append img to h6 element

    if (leagueId <= 5) {
        $(h6).append(LeagueName[leagueId - 1]);	//League Name
	}
    else if (leagueId <= 23) {
        $(h6).append(LeagueName[5]);	//League Name
	}
    else if (leagueId <= 46) {
        $(h6).append(LeagueName[6]);    // Europa League
	}
    else if (leagueId <= 62) {
        $(h6).append(LeagueName[7]);	//FA Cup Name
	}
    else {
        $(h6).append(LeagueName[LeagueName.length-1]);	// Other leagues
	}

    $(h6).appendTo(div);

    var ul = document.createElement("ul");
    $(ul).attr(
	{
        "data-role": "listview", "data-inset": "false", "data-divider-theme": "d", "data-theme": "c",
        "id": "list-league-" + leagueId,
        "class": "league_collapsible_item"
    });	// Initial attribute to ul tag

    $(ul).appendTo(div);
    return div;
}

function MakeLeague24h(index)
{
    var div = document.createElement("div");
    $(div).attr(
	{
        "data-role": "collapsible", "data-collapsed": "false", "data-theme": "b", "data-content-theme": "b",
        "data-inset": "false", "data-collapsed-icon": "arrow-r", "data-expanded-icon": "arrow-d", "data-iconpos": "right",
        "class": "league_collapsible"
    });

    var h6 = document.createElement("h6");	// Create h6 tag
    $(h6).addClass("next-24h-page-p");
	
    var colorTag = document.createElement("div");
    $(colorTag).addClass("colorTag");
    $(colorTag).addClass("colorTag-" + index);
    $(h6).append(colorTag);
	
    if (index == 0) {
        $(h6).append("Hôm nay");
	}
    else {
        $(h6).append("Ngày mai");
	}
	
    $(h6).appendTo(div);

    var ul = document.createElement("ul");
    $(ul).attr(
	{
        "data-role": "listview", "data-inset": "false", "data-divider-theme": "d", "data-theme": "c",
        "class": "league_collapsible_item"
    });	// Initial attribute to ul tag

    $(ul).appendTo(div);
    return div;
}

/* ===================== End Fixture page ================ */



/* ===================== 24h page ================ */

$(document).on("pageshow", "#next24h_page", function(event)
{
    try
	{
		localStorage.expiredObject = "";
		CheckExpiredDate(function()
		{
			var expiredObject = JSON.parse(localStorage.expiredObject);
	
			if (expiredObject.status == 0)
			{
				$.mobile.changePage("#extend_expiration_page");
				$("#notificationMessage").html("Thiết bị của bạn đã hết hạn sử dụng, vui lòng gia hạn để sử dụng được đầy đủ các chức năng.");
				ShowExpirationTimeOnAccountPage("Hết hạn");
			}
			else if (expiredObject.status == 1)
			{
				if (expiredObject.leftDay <= 3) {
					$.notify("Thiết bị còn " + expiredObject.leftDay + " ngày sử dụng", { className: "info", clickToHide: true, globalPosition: 'bottom right',});
				}
				LoadNext24h();
				ShowExpirationTimeOnAccountPage(expiredObject.leftDay + " ngày");
			}
			else
			{
				LoadNext24h();
				$("#expirationTime").html("");
			}
		});
    }
    catch (e)
	{
		LoadNext24h();
		$("#expirationTime").html("");
    }
});

function LoadNext24h()
{
    var $24h_content_panel = $("#24h_content_panel");
    $24h_content_panel.html("");
	
    if (typeof (localStorage.userId) == "undefined") {
        localStorage.userId = 0;
	}

    $.mobile.loading("show");
    $.ajax({
        url: localStorage.next24h + "&user_row_id=" + localStorage.userId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            var sortLeague = new Array();

            for (var i = 0; i < 2; i++)
			{
                var tmp = null;
                var d = null;
                var flag = 0;
                var leagueDiv = MakeLeague24h(i);
                var date = new Date();
                date.setDate(date.getDate() + i);

                for (var j = 0; j < data.length; j++)
				{
                    tmp = d;
                    d = ExtractDate(data[j]._matchDatetime);
					var betHtml = (data[j]._isHot == 1) ? "class='campaign-hot-match'" : ((data[j]._isBet == 1) ? "class='bet-match'" : "");

                    if (d.getDay() == date.getDay())
					{
                        var leagueIcon = "";
						
                        if (data[j]._league_row_id <= 5) {
                            leagueIcon = data[j]._league_row_id;
						}
                        else if (data[j]._league_row_id <= 23) {
                            leagueIcon = "20"; // Champion league
						}
                        else if (data[j]._league_row_id <= 46) {
                            leagueIcon = "42"; // Europa league
						}
						else if (data[j]._league_row_id <= 62 && data[j]._league_row_id >= 55) {
							leagueIcon = "57"; //FA Cup
						}
						else {
							leagueIcon = "0"; // International
						}
			
                        var listView = "<li " + betHtml + ">" +
							"<a href=\"javascript:ChangeToMatchDetailPage(" + data[j]._matchID + ")\">" +
								"<div class='match-result-even-row'><div class='match-time-without-league-icon'>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>" +
									"<img src='images/league/league_" + leagueIcon + "x32.png' class='league-icon'/>" +
									"<div class='home-club-name'>" + data[j]._home + "</div><span class='split'>-</span><div class='guest-club-name'>" + data[j]._guest + "</div></div>" +
								"<div class='tvChannel'></div>" +
							"</a>" +
						"</li>";
							
                        flag = 1;
                        $(leagueDiv.children["1"]).append(listView);
                    }
                }

                if (flag == 1) {
                    sortLeague.push(leagueDiv);
				}
            }

			var length = $(sortLeague).length;
			for (var i = 0; i<length; i++) {
				$(sortLeague[i]).appendTo($24h_content_panel);
			}

            $(".league_collapsible").collapsible();
            $("#next24h_page ul.league_collapsible_item").listview();
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        },
        error: function(data) {
            ShowMessageBox("Cảnh báo", "Tải nội dung về không thành công! Xin vui lòng kiểm tra lại kết nối mạng!");
        },
        timeout: 20000  // Timeout after 15 seconds
    });
}

/* ===================== End 24h page ================ */



/* ===================== Statistics table page ====================== */

$(document).on("pageinit", "#player_statistics_page", function(event)
{
    $("#select-standing-player-season").change(function()
	{
        var seasonId = this.options[this.selectedIndex].value;
        localStorage.seasonId = seasonId;
        LoadStatisticTable(seasonId);
    });
});

$(document).on("pageshow", "#player_statistics_page", function(event)
{
    var satisticLeagueId = (((localStorage.leagueId - 1) > 6) ? 6 : (localStorage.leagueId - 1));

    // Cup
    if (satisticLeagueId >= 5)
	{
        $("a#statistic-to-match-result").attr("href", "#cup_match_result_page");
        $("a#statistic-to-ranking").attr("href", "#ranking_cup_detail_page");
    }
    // National
    else
	{
        $("a#statistic-to-match-result").attr("href", "#national_match_result_page");
        $("a#statistic-to-ranking").attr("href", "#ranking_national_detail_page");
    }

    $("#statistics_header").html(LeagueName[satisticLeagueId]);
    SeasonList("select-standing-player-season", LoadStatisticTable);
});

function LoadStatisticTable(seasonId)
{
    var $statistic_table_body = $("#statistic_table_body");
    $statistic_table_body.html("");
    $.mobile.loading("show");

    $.ajax({
        url: localStorage.playerStandingStatistics + seasonId + "&LeagueId=" + localStorage.leagueId + "&StatisticType=1",
        type: "GET",
        dataType: "jsonp",
        success: function(data)
		{
            for (var i = 0; data != null && i < data.length; i++)
			{
                var htmlStr = "<tr> \
					<td style='text-align:center;'>" + (i + 1) + "</td> \
					<td style='text-align:center;'><img style='vertical-align: middle; padding-right:5px; width:30px' alt='' src='" + data[i].ClubLogo + "'></img></td> \
					<td >" + data[i].Name + "</td> \
					<td style='text-align:center;'>" + data[i].Goal + "</td> \
				</tr>";

                $statistic_table_body.append(htmlStr);
            }
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}

/* ===================== End statistics table page ====================== */



/* ===================== Club detail page ================ */

$(document).on("pageshow", "#club_detail_page", function(event) {
    $("#club-list-info").listview("refresh");
});

function ChangeToClubDetailPage(id)
{
    localStorage.clubId = id;
    $.mobile.loading("show");

    /* Refresh previous data */
    $("#club_detail_page .club-info .ui-block-a img").remove();
	
    var $detail_club_name = $("#detail_club_name");
    $detail_club_name.html("");
    
	var $detail_club_founded = $("#detail_club_founded");
    $detail_club_founded.html("");
	
    var $detail_club_stadium = $("#detail_club_stadium");
    $detail_club_stadium.html("");
	
    var $detail_club_capacity = $("#detail_club_capacity");
    $detail_club_capacity.html("");
	
    var $detail_club_manager = $("#detail_club_manager");
    $detail_club_manager.html("");
	
    var $detail_club_address = $("#detail_club_address");
    $detail_club_address.html("");
	
    var $detail_club_web = $("#detail_club_web");
    $detail_club_web.html("");
	
    var $detail_club_email = $("#detail_club_email");
    $detail_club_email.html("");
	
    var $detail_club_phone = $("#detail_club_phone");
    $detail_club_phone.html("");
	
    var $detail_club_honours = $("#detail_club_honours");
    $detail_club_honours.html("");
	
    var $club_list_info = $("#club-list-info");
    $club_list_info.html("");

    var jqxhr = $.ajax({
        url: localStorage.clubUrl + localStorage.clubId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            /* Save club info */
            localStorage.clubLogoSrc = data.Logo;
            localStorage.clubName = data.Name;

            /* Display club detail */
            $("a#team_player_link").prop("href", "javascript:ChangeToListTeamPlayerPage(" + data.Id + ")");
            $("a #team_player_link").button("refresh");

            var img = document.createElement("img"); //Create img tag
            img.src = data.Logo;

            $("#club_detail_page .club-info .ui-block-a").prepend(img);

            $detail_club_name.html(data.Name);
            $("#club_detail_page").children("div[data-role='header']").children("h1").html(data.Name);

            $detail_club_founded.html((data.Founded > 0) ? data.Founded : "");
            $detail_club_stadium.html(data.Stadium);
            $detail_club_capacity.html((data.Capacity > 0) ? data.Capacity : "");
            $detail_club_manager.html(data.Manager);
            $detail_club_honours.html(data.Honours);
            $detail_club_address.html(data.Address);

            var webLink = data.Web;
            $detail_club_web.html(webLink);

            var mailTo = data.Email;
            $detail_club_email.html(mailTo);

            var telLink = data.Phone;
            $detail_club_phone.html(telLink);

            // List club related news
            if (data.News.length > 0)
			{
                var headDivider = "<li data-role=\"list-divider\">Tin tức liên quan</li>";
                $club_list_info.append(headDivider);

                for (var i = 0; i < data.News.length; i++) {
                    $club_list_info.append("<li><a href='javascript:LoadNewsDetail(" + i + ")'><h2 class='feed-title'>" + data.News[i].Title + "</h2></a></li>");
                }

                localStorage.newsData = JSON.stringify(data.News);
            }
        },
        complete: function(jqXHR, status)
		{
            // Switch to detail page
            $.mobile.changePage("#club_detail_page");
            $.mobile.loading("hide");
        }
    });
}

function LoadClubDetail()
{
    $.mobile.loading("show");

    $("#club_detail_page .club-info .ui-block-a img").remove();
	
    var $detail_club_name = $("#detail_club_name");
    $detail_club_name.html("");
	
    var $detail_club_founded = $("#detail_club_founded");
    $detail_club_founded.html("");
	
    var $detail_club_stadium = $("#detail_club_stadium");
    $detail_club_stadium.html("");
	
    var $detail_club_capacity = $("#detail_club_capacity");
    $detail_club_capacity.html("");
	
    var $detail_club_manager = $("#detail_club_manager");
    $detail_club_manager.html("");
	
    var $detail_club_address = $("#detail_club_address");
    $detail_club_address.html("");
	
    var $detail_club_web = $("#detail_club_web");
    $detail_club_web.html("");
	
    var $detail_club_email = $("#detail_club_email");
    $detail_club_email.html("");
	
    var $detail_club_phone = $("#detail_club_phone");
    $detail_club_phone.html("");
	
    var $detail_club_honours = $("#detail_club_honours");
    $detail_club_honours.html("");

    var $club_list_info = $("#club-list-info");
    $club_list_info.html("");

    var jqxhr = $.ajax({
        url: localStorage.clubUrl + localStorage.clubId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            /* Save club info */
            localStorage.clubLogoSrc = data.Logo;
            localStorage.clubName = data.Name;

            /* Display club detail */
            $("a#team_player_link").prop("href", "javascript:ChangeToListTeamPlayerPage(" + data.Id + ")");
            $("a #team_player_link").button("refresh");

            var img = document.createElement("img"); //Create img tag
            img.src = data.Logo;

            $("#club_detail_page .club-info .ui-block-a").prepend(img);

            $detail_club_name.html(data.Name);
            $("#club_detail_page").children("div[data-role='header']").children("h1").html(data.Name);

            $detail_club_founded.html((data.Founded > 0) ? data.Founded : "");
            $detail_club_stadium.html(data.Stadium);
            $detail_club_capacity.html((data.Capacity > 0) ? data.Capacity : "");
            $detail_club_manager.html(data.Manager);
            $detail_club_honours.html(data.Honours);
            $detail_club_address.html(data.Address);

            var webLink = data.Web;
            $detail_club_web.html(webLink);

            var mailTo = data.Email;
            $detail_club_email.html(mailTo);

            var telLink = data.Phone;
            $detail_club_phone.html(telLink);

            // List club related news
            if (data.News.length > 0)
			{
                var headDivider = "<li data-role=\"list-divider\">Tin tức liên quan</li>";
                $club_list_info.append(headDivider);

                for (var i = 0; i < data.News.length; i++) {
                    $club_list_info.append("<li><a href='javascript:LoadNewsDetail(" + i + ")'><h2 class='feed-title'>" + data.News[i].Title + "</h2></a></li>");
                }

                localStorage.newsData = JSON.stringify(data.News);
            }
        },
        complete: function(jqXHR, status)
		{
            // Switch to detail page
            $(".club-contact ul[data-role='listview']").listview("refresh");
            $.mobile.loading("hide");
        }
    });
}

/* ===================== End Club detail page ================ */



/* ===================== List Team Player page ================ */

$(document).on("pageshow", "#team_player_list_page", function(event) {
    $("#team_player_list_page").children("div[data-role='header']").children("h1").html(localStorage.clubName);
});

$(document).on("pagehide", "#team_player_list_page", function(event) {
    $("#team_player_list_page").children("div[data-role='header']").children("h1").html("");
});

function ChangeToListTeamPlayerPage(id)
{
    localStorage.clubId = id;
    $.mobile.loading("show");

    /* Refresh Page */
    var listTeam = $("#team_player_list_page ul[data-role='listview']");
    listTeam.empty();

    $("#club-title").html("");
    $("#team_player_list_page .club-info img").remove();

    var jqxhr = $.ajax({
        url: localStorage.teamUrl + localStorage.clubId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            /* Display list team detail */
            try
			{
                for (var i = 0; i < data.length; i++)
				{
                    //Initialize tag object
                    var liTag = document.createElement("li");
                    $(liTag).attr("data-playerid", data[i].Id);

                    var aTag = document.createElement("a");
                    var img = document.createElement("img");
                    var h2Tag = document.createElement("h2");
                    var pTag = document.createElement("p");

                    //Handle data
                    img.src = data[i].Image;
                    $(h2Tag).html(data[i].Name);
                    $(pTag).html(data[i].Position);

                    //Add to DOM
                    $(aTag).append(img);
                    $(aTag).append(h2Tag);
                    $(aTag).append(pTag);

                    $(aTag).appendTo(liTag);
                    $(liTag).appendTo(listTeam);
                }
				
                listTeam.listview("refresh");
            }
            catch (err) { }
        },
        complete: function(jqXHR, status)
		{
            // Switch to detail page
            $("#team_player_list_page ul[data-role='listview'] li").on("tap", tapToPlayerDetail);

            function tapToPlayerDetail(event)
			{
                var playerId = $(event.currentTarget).attr("data-playerid");
                ChangeToPlayerDetailPage(playerId);
            }

            $.mobile.changePage("#team_player_list_page");
            $.mobile.loading("hide");
        }
    });
}

function LoadListTeamPlayer()
{
    $.mobile.loading("show");

    /* Refresh Page */
    var listTeam = $("#team_player_list_page ul[data-role='listview']");
    listTeam.empty();

    $("#club-title").html("");
    $("#team_player_list_page .club-info img").remove();

    var jqxhr = $.ajax({
        url: localStorage.teamUrl + localStorage.clubId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            /* Display list team detail */
            try
			{
                for (var i = 0; i < data.length; i++)
				{
                    //Initialize tag object
                    var liTag = document.createElement("li");
                    $(liTag).attr("data-playerid", data[i].Id);

                    var aTag = document.createElement("a");
                    var img = document.createElement("img");
                    var h2Tag = document.createElement("h2");
                    var pTag = document.createElement("p");

                    //Handle data
                    img.src = data[i].Image;
                    $(h2Tag).html(data[i].Name);
                    $(pTag).html(data[i].Position);

                    //Add to DOM
                    $(aTag).append(img);
                    $(aTag).append(h2Tag);
                    $(aTag).append(pTag);

                    $(aTag).appendTo(liTag);
                    $(liTag).appendTo(listTeam);
                }
				
                listTeam.listview("refresh");
            }
            catch (err) { }
        },
        complete: function(jqXHR, status)
		{
            $("#team_player_list_page ul[data-role='listview'] li").on("tap", tapToPlayerDetail);

            function tapToPlayerDetail(event)
			{
                var playerId = $(event.currentTarget).attr("data-playerid");
                ChangeToPlayerDetailPage(playerId);
            }

            $.mobile.loading("hide");
        }
    });
}

/* ===================== End List Team Player page ================ */



/* ===================== Player Detail page ================ */

$(document).on("pageshow", "#player_detail_page", function(event) {
    $("#player-news").listview("refresh");
});

function ChangeToPlayerDetailPage(id)
{
    localStorage.playerId = id;
    $.mobile.loading("show");

    var newsList = $("#player-news");

    $("#player-image img").remove();

    var $player_name = $("#detail_player_name");
    $player_name.html("");
	
    var $player_position = $("#detail_player_position");
    $player_position.html("");
	
    var $player_number = $("#detail_player_number");
    $player_number.html("");
	
    var $player_height = $("#detail_player_height");
    $player_height.html("");
	
    var $player_weight = $("#detail_player_weight");
    $player_weight.html("");
	
    var $player_birthday = $("#detail_player_date");
    $player_birthday.html("");
	
    var $player_nationality = $("#detail_player_nationality");
    $player_nationality.html("");

    newsList.html("");

    var jqxhr = $.ajax({
        url: localStorage.playerUrl + localStorage.playerId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            // Display player detail
            var player_img = document.createElement("img");
            player_img.src = data.Image;
            $("#player-image").append(player_img);

            $("#player_detail_page").children("div[data-role='header']").children("h1").html(data.Name);
            $player_name.html(data.Name);
            $player_position.html(data.Position);
            $player_number.html(data.Number);
            $player_height.html(data.Height);
            $player_weight.html(data.Weight);
            $player_birthday.html(data.DateOfBirth);
            $player_nationality.html(data.Nationality);

            // List player related news
            var headDivider = "<li data-role=\"list-divider\">Tin tức liên quan</li>";
            if (data.News.length > 0)
			{
                for (var i = 0; i < data.News.length; i++) {
                    headDivider += "<li><a href='javascript:LoadNewsDetail(" + i + ")'><h2 class='feed-title'>" + data.News[i].Title + "</h2></a></li>";
                }
		
				newsList.html(headDivider);
                localStorage.newsData = JSON.stringify(data.News);
            }
        },
        complete: function(jqXHR, status)
		{
            // Switch to detail page
            $.mobile.changePage("#player_detail_page");
            $.mobile.loading("hide");
        }
    });
}

function LoadPlayerDetail()
{
    $.mobile.loading("show");

    var newsList = $("#player-news");

    /* Refresh previous data */
    $("#player-image img").remove();

    var $player_name = $("#detail_player_name");
    $player_name.html("");
	
    var $player_position = $("#detail_player_position");
    $player_position.html("");
	
    var $player_number = $("#detail_player_number");
    $player_number.html("");
	
    var $player_height = $("#detail_player_height");
    $player_height.html("");
	
    var $player_weight = $("#detail_player_weight");
    $player_weight.html("");
	
    var $player_birthday = $("#detail_player_date");
    $player_birthday.html("");
	
    var $player_nationality = $("#detail_player_nationality");
    $player_nationality.html("");

    newsList.html("");

    var jqxhr = $.ajax({
        url: localStorage.playerUrl + localStorage.playerId,
        type: "GET",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            // Display player detail
            var player_img = document.createElement("img");
            player_img.src = data.Image;
            $("#player-image").append(player_img);

            $("#detail_player_name").html(data.Name);
            $("#player_detail_page").children("div[data-role='header']").children("h1").html(data.Name);
            $player_name.html(data.Name);
            $player_position.html(data.Position);
            $player_number.html(data.Number);
            $player_height.html(data.Height);
            $player_weight.html(data.Weight);
            $player_birthday.html(data.DateOfBirth);
            $player_nationality.html(data.Nationality);

            // List player related news
            var headDivider = "<li data-role=\"list-divider\">Tin tức liên quan</li>";
            if (data.News.length > 0)
			{
                for (var i = 0; i < data.News.length; i++) {
                    headDivider += "<li><a href='javascript:LoadNewsDetail(" + i + ")'><h2 class='feed-title'>" + data.News[i].Title + "</h2></a></li>";
                }
		
				newsList.html(headDivider);
                localStorage.newsData = JSON.stringify(data.News);
            }
        },
        complete: function(jqXHR, status)
		{
            // Switch to detail page
            $(".player-index ul[data-role='listview']").listview('refresh');
            $.mobile.loading("hide");
        }
    });
}

/* ===================== End Player Detail page ================ */



//=========================Signin page=========================

function changeToSigninPage(destination)
{
    if (!(typeof localStorage.userId != "undefined" && localStorage.userId != 0 && localStorage.userId != null)) {         
        $.mobile.changePage("#signin_page", { transition: "none"} );
	}
	else {
		$.mobile.changePage(destination, { transition: "none"} );
	}
}

function isSignined()
{
    if (typeof localStorage.username != "undefined" && localStorage.username != null && localStorage.username != "") {
        return 1;
	}
    else if (localStorage.signInShow == 1)
	{
        localStorage.signInShow = 0;
        return 2;
    }
    else
	{
        $.mobile.changePage("#signin_page");
        return 3;
    }
}

$(document).on("pageinit", "#signin_page", function(event)
{
    $("#txtUsernameSignin").keyup(function() {
        validateSignin();
    });
	
    $("#txtPasswordSignin").keyup(function() {
        validateSignin();
    });
});

$(document).on("pagebeforeshow", "#signin_page", function(event)
{
    $("#txtUsernameSignin").val("");
    $("#txtPasswordSignin").val("");
    $("#btnSignin").attr("disabled", "disabled").button("refresh");
});

$(document).on("pageshow", "#signin_page", function(event) {
    localStorage.signInShow = 1;
});

function validateSignin()
{
    if (jQuery.trim($("#txtUsernameSignin").val()).length > 0 && $("#txtPasswordSignin").val().length > 0) {
        $("#btnSignin").removeAttr("disabled").button("refresh");
    }
    else {
        $("#btnSignin").attr("disabled", "disabled").button("refresh");
    }
}

function signin()
{
    $.mobile.loading("show");

    var $username = $("#txtUsernameSignin").val();

    var jqxhr = $.ajax({
        url: localStorage.signinUrl + "&username=" + $username + "&password=" + $("#txtPasswordSignin").val(),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (user)
		{
            if (user == null || user.Id == 0) {
                ShowMessageBox("Đăng nhập", "Đăng nhập thất bại!");
			}
            else
			{
                localStorage.username = $username;
                localStorage.userId = user.Id;
                localStorage.userAccount = user.Account;
				
                if (localStorage.visitBetPage == 1) {
                    $.mobile.changePage("#bet_page");
                }
                else if (localStorage.visitBetByScorePage == 1) {
                    $.mobile.changePage("#bet_score_page");
                }
                else {
                    $.mobile.changePage("#account_page");
				}

                var dataObject = new dataObj();
                dataObject.updateDB(user.Id, localStorage.username);
                dataObject.loadDB();
            }
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//=========================Signup page=========================

$(document).on("pageinit", "#signup_page", function (event)
{
    var txtUsernameSignup = $("#txtUsernameSignup");
    var txtPasswordSignup = $("#txtPasswordSignup");
    var txtConfirmPassword = $("#txtConfirmPassword");
    var btnSignup = $("#btnSignup");

    $("#txtUsernameSignup").keyup(function () {
        validateSignup(txtUsernameSignup,txtPasswordSignup, txtConfirmPassword, btnSignup );
    });

    $("#txtPasswordSignup").keyup(function () {
        validateSignup(txtUsernameSignup,txtPasswordSignup, txtConfirmPassword, btnSignup );
    });

    $("#txtConfirmPassword").keyup(function () {
        validateSignup(txtUsernameSignup,txtPasswordSignup, txtConfirmPassword, btnSignup );
    });
});

$(document).on("pagebeforeshow", "#signup_page", function(event)
{
    $("#txtUsernameSignup").val("");
    $("#txtPasswordSignup").val("");
    $("#txtConfirmPassword").val("");
    $("#btnSignup").attr("disabled", "disabled").button("refresh");

    if (localStorage.isSignup == 1) {
        $("#divSignupAlert").html("* Tạo nhiều tài khoản trên cùng một máy sẽ không được thưởng xu!");
    }
});

function validateSignup(userName, password, confirmPassword, signupButton)
{
    if (userName.val().length != 0 && password.val().length != 0 && confirmPassword.val().length != 0) {
        signupButton.removeAttr("disabled").button("refresh");
    }
    else {
        signupButton.attr("disabled", "disabled").button("refresh");
    }
}

function signup()
{
    var $username = $("#txtUsernameSignup").val();
    
    if ($username.length < 4)
	{
        ShowMessageBox("Đăng kí", "Tên đăng nhập phải có ít nhất 4 kí tự!");
        return;
    }

    var patt = /^[a-zA-Z0-9_]*$/;
    if (!patt.test($username))
	{
        ShowMessageBox("Đăng kí", "Tên đăng nhập chỉ được phép có chữ cái, số và dấu gạch dưới!");
        return;
    }

    var $password = $("#txtPasswordSignup").val();
    
    if ($password != $("#txtConfirmPassword").val())
	{
        ShowMessageBox("Đăng kí", "Xác nhận mật khẩu không trùng khớp!");
        return;
    }

    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.signupUrl + "?identification=" + localStorage.imei + "&username=" + $username + "&password=" + $password + "&is_signup=" + localStorage.isSignup,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function (user)
		{
            if (user.Id == null) {
                ShowMessageBox("Đăng kí", "Xảy ra lỗi trong quá trình đăng kí. Xin vui lòng thử lại!");
			}
            else if (user.Id == 0) {
                ShowMessageBox("Đăng kí", "Tên đăng nhập đã được sử dụng!");
			}
            else
			{
                localStorage.username = $username;
                localStorage.userId = user.Id;
                localStorage.userAccount = user.Account;
				
                if (localStorage.visitBetPage == 1) {
                    $.mobile.changePage("#bet_page");
                }
                else if (localStorage.visitBetByScorePage == 1) {
                    $.mobile.changePage("#bet_score_page");
                }
                else {
                    $.mobile.changePage("#account_page");
                }

            }
        },
        complete: function (jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//=========================Account page=========================

$(document).on("pageinit", "#account_page", function(event)
{
    $("#popupEmailValidate").on("popupafteropen", function(event, ui)
	{
        var email = (clientUserObject == null) ? "" : clientUserObject.getEmail();
        $("#txtValidateEmail").val(email);
    });
});

$(document).on("pageshow", "#account_page", function (event)
{
    var isLogIn = isSignined();

    if (isLogIn == 1)
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
                localStorage.userPhone = user.Phone;
                localStorage.isEmailVerified = user.IsEmailVerified;
                localStorage.isPhoneVerified = user.IsPhoneVerified;

                if (clientUserObject == null) {
                    clientUserObject = new userObj(localStorage.username, user.Fullname, user.Address, user.Account, user.Phone, user.Email);
                }
				else
				{
                    clientUserObject.setUserName(localStorage.username);
                    clientUserObject.setFullName(user.Fullname);
                    clientUserObject.setAddress(user.Address);
                    clientUserObject.setCoin(user.Account);
                    clientUserObject.setPhone(user.Phone);
                    clientUserObject.setEmail(user.Email);
                }

                if (localStorage.isEmailVerified == 1)
				{
                    $("a#emailVerify").attr("href", "#");
                    $("a#emailVerify span").removeClass("img-unverified").addClass("img-verified");
                }
                else
				{
                    $("a#emailVerify").attr("href", "#popupEmailValidate");
                    $("a#emailVerify span").removeClass("img-verified").addClass("img-unverified");
                }

                if (localStorage.isPhoneVerified == 1)
				{
                    $("a#phoneVerify").attr("href", "#");
                    $("a#phoneVerify span").removeClass("img-unverified").addClass("img-verified");
                }
                else
				{
                    $("a#phoneVerify").attr("href", "#popupMobileValidate");
                    $("a#phoneVerify span").removeClass("img-verified").addClass("img-unverified");
                }

                $("#username").html(localStorage.username);
                $("#fullname").html(user.Fullname);
                $("#address").html(user.Address);
                $("#phone").html(user.Phone);
                $("#account").html("<span class='account_money'>" + AddDot(user.Account) + "</span>  xu");
                $("#email").html(Ellipsify(user.Email)); // reduce email length if too long
            },
            complete: function (jqXHR, status) {
                $.mobile.loading("hide");
            }
        });
    }
    else if (isLogIn == 2) {
        history.back();
	}

    ShowUnreadMessageNotification();
});

function ShowUnreadMessageNotification()
{
    var jqxhr = $.ajax({
        url: localStorage.GetTotalUnreadMessageUrl + localStorage.username,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            if (data !== null)
			{
                if (data > 0) {
                    $(".badge").show().text(data);
                }
                else {
                    $(".badge").hide().text("");
                }
            }
            else {
                $(".badge").hide().text("");
            }
        },
        error: function(jqXHR, status, errorThrown) {
            $(".badge").hide().text("");
        },
        complete: function(jqXHR, status) { }
    });
}

function signout()
{
    var dataObject = new dataObj();
    dataObject.deleteEntry(localStorage.userId);

    if (clientUserObject != null){
        clientUserObject.flush();
    }

    localStorage.userId = 0;
    localStorage.username = "";
    localStorage.signInShow = 0;
    localStorage.visitBetPage = 0;
    localStorage.visitBetByScorePage = 0;

    $.mobile.changePage("#fixture_page");
}



/* ===================== Bet history page ================ */

$(document).on("pageinit", "#bet_history_page", function(event)
{
	$("#fromDateTime").change(function() {
        LoadBetHistory();
    });
});

$(document).on("pageshow", "#bet_history_page", function(event) {
    LoadBetHistory();
});

function LoadBetHistory()
{
    var $bet_history_panel = $("#bet_history_panel");
    
    $bet_history_panel.html("");
	$("#bet_result_table").html("");

    $.mobile.loading("show");
    $.ajax({
        url: localStorage.betHistory + "userId=" + localStorage.userId + "&from=" + $("#fromDateTime").val(),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
			var d = null;
			var html = "";
		
			var ul = document.createElement("ul");
			$(ul).attr(
			{
				"data-role": "listview", "data-inset": "false", "data-theme": "c",
				"class": "bet_history_listview"
			});

			for (var j = 0; j < data.length; j++)
			{
				var leagueIcon = "";
						
				if (data[j].league_row_id <= 5) {
					leagueIcon = data[j].league_row_id;
				}
				else if (data[j].league_row_id <= 23) {
					leagueIcon = "20"; // Champion league
				}
				else if (data[j].leagueIcon <= 42 && data[j].leagueIcon >= 25) {
					leagueIcon = "42"; // Europa league
				}
                else if (data[j].leagueIcon <= 62 && data[j].leagueIcon >= 56) {
                    leagueIcon = "57";
				}
                else {
                    leagueIcon = "0";
				}
					
				d = ExtractDate(data[j].register_datetime);

				if (data[j].match_status == 1 || data[j].match_status == 4 || data[j].match_status == 6)
				{
					html += "<li data-icon='false'>" +
						"<a href=\"javascript:ChangeToMatchDetailPage(" + data[j].matchId + ")\">" +
						"<div class='match-result-even-row'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>" +
						"<img src='images/league/league_" + leagueIcon + "x32.png' class='league-icon'/>";
						
					if (data[j].home_penalty_shoot != 0 && data[j].guest_penalty_shoot != 0)
					{
						if (data[j].home_penalty_shoot > data[j].guest_penalty_shoot) {
							html += "<div class='home-club-name'><span class='penalty'>(*) </span>" + data[j].home_club + "</div><div class='home-club-goal'>" + data[j].home_club_goal + "</div><span class='split'>&nbsp;-&nbsp;</span><div class='guest-club-goal'>" + data[j].guest_club_goal + "</div><div class='guest-club-name'>" + data[j].guest_club + "</div></div>";
						}
						else {
							html += "<div class='home-club-name'>" + data[j].home_club + "</div><div class='home-club-goal'>" + data[j].home_club_goal + "</div><span class='split'>&nbsp;-&nbsp;</span><div class='guest-club-goal'>" + data[j].guest_club_goal + "</div><div class='guest-club-name'>" + data[j].guest_club + "<span class='penalty'> (*)</span></div></div>";
						}
					}
					else {
						html += "<div class='home-club-name'>" + data[j].home_club + "</div><div class='home-club-goal'>" + data[j].home_club_goal + "</div><span class='split'>&nbsp;-&nbsp;</span><div class='guest-club-goal'>" + data[j].guest_club_goal + "</div><div class='guest-club-name'>" + data[j].guest_club + "</div></div>";
					}
					
					html += "<div class='betResult' style='color:" + (data[j].bet_result == 1 ? "green" : data[j].bet_result == -1 ? "red" : "blue") + "'>" + (data[j].bet_result == -2 ? ("Số xu đặt: " + data[j].user_bet_value + " xu") : ((data[j].bet_result == 1 ? "Kết quả: Thắng " : data[j].bet_result == -1 ? "Kết quả: Thua " : "Kết quả: Hòa ") + AddDot(Math.abs(data[j].bet_result_value)) + " xu")) + "</div>" +
						"</a>" +
						"</li>";
				}
				else if (data[j].match_status == 5)
				{
					html += "<li data-icon='false'>" +
						"<a href=\"javascript:ChangeToMatchDetailPage(" + data[j].matchId + ")\">" +
						"<div class='match-result-even-row'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>" +
						"<img src='images/league/league_" + leagueIcon + "x32.png' class='league-icon'/>" +
						"<div class='home-club-name'>" + data[j].home_club + "</div><div class='home-club-goal'>" + data[j].home_club_goal + "</div><span class='split'>&nbsp;-&nbsp;</span><div class='guest-club-goal'>" + data[j].guest_club_goal + "</div><div class='guest-club-name'>" + data[j].guest_club + "</div></div>" +
						"<div class='betResult' style='color:blue;'>Số xu đặt: " + data[j].user_bet_value  + " xu</div>" +
						"</a>" +
					"</li>";
				}
				else
				{
					html += "<li data-icon='false'>" +
						"<a href=\"javascript:ChangeToMatchDetailPage(" + data[j].matchId + ")\">" +
						"<div class='match-result-even-row'><div class='match-time-without-league-icon'>" + (d.getDate() < 10 ? "0" : "") + d.getDate() + "/" + (d.getMonth() + 1 < 10 ? "0" : "") + (d.getMonth() + 1) + "<br/>" + (d.getHours() < 10 ? "0" : "") + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" : "") + d.getMinutes() + "</div>" +
						"<img src='images/league/league_" + leagueIcon + "x32.png' class='league-icon'/>" +
						"<div class='home-club-name' style='width: 34%'>" + data[j].home_club + "</div><span class='split'>-</span><div class='guest-club-name' style='width: 34%'>" + data[j].guest_club + "</div></div>" +
						"<div class='betResult' style='color:blue;'>Số xu đặt: " + data[j].user_bet_value  + " xu</div>" +
						"</a>" +
					"</li>";
				}
			}
			
			$(ul).html(html);
			$(ul).appendTo($bet_history_panel);
			$(".bet_history_listview").listview();
		},
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



/* ===================== Bet statistic page ================ */

$(document).on("pageinit", "#statistic_page", function(event)
{
	$("#radio-choice-h-2a").change(function()
	{
        var html = "<table>"
			+ "<tr>"
				+ "<td style='text-align:right; width:40%;'>Số cửa thắng</td>"
				+ "<td style='width:60%; padding-left:30px;'>" + AddDot(localStorage.WonMatchScore) + "</td>"
			+ "</tr>"
			+ "<tr>"
				+ "<td style='text-align:right; width:40%;'>Số cửa đặt</td>"
				+ "<td style='width:60%; padding-left:30px;'>" + AddDot(localStorage.TotalMatchScore) + "</td>"
			+ "</tr>"
			+ "<tr>"
				+ "<td style='text-align:right; width:40%;'>Hiệu suất</td>"
				+ "<td style='width:60%; padding-left:30px;'>" + Math.round(localStorage.WonMatchScore / localStorage.TotalMatchScore * 100) + " %</td>"
			+ "</tr>"
		+ "</table>";
		
		$("#bet_statistic").html(html);
    });
	
	$("#radio-choice-h-2b").change(function()
	{
        var html = "<table>"
			+ "<tr>"
				+ "<td style='text-align:right; width:40%;'>Số xu thắng</td>"
				+ "<td style='width:60%; padding-left:30px;'>" + AddDot(localStorage.WonCoinScore) + "</td>"
			+ "</tr>"
			+ "<tr>"
				+ "<td style='text-align:right; width:40%;'>Số xu đặt</td>"
				+ "<td style='width:60%; padding-left:30px;'>" + AddDot(localStorage.TotalCoinScore) + "</td>"
			+ "</tr>"
			+ "<tr>"
				+ "<td style='text-align:right; width:40%;'>Hiệu suất</td>"
				+ "<td style='width:60%; padding-left:30px;'>" + Math.round(localStorage.WonCoinScore / localStorage.TotalCoinScore * 100) + " %</td>"
			+ "</tr>"
		+ "</table>";
		
		$("#bet_statistic").html(html);
    });
});

$(document).on("pageshow", "#statistic_page", function(event)
{
    $("#bet_statistic").html("");

    $.mobile.loading("show");
    $.ajax({
        url: localStorage.betStatisticUrl +  localStorage.userId,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
			localStorage.WonMatchScore = data.WonMatchScore;
			localStorage.TotalMatchScore = data.TotalMatchScore;
			localStorage.WonCoinScore = data.WonCoinScore;
			localStorage.TotalCoinScore = data.TotalCoinScore;
		
			var html = "<table>"
				+ "<tr>"
					+ "<td style='text-align:right; width:40%;'>Số cửa thắng</td>"
					+ "<td style='width:60%; padding-left:30px;'>" + AddDot(data.WonMatchScore) + "</td>"
				+ "</tr>"
				+ "<tr>"
					+ "<td style='text-align:right; width:40%;'>Số cửa đặt</td>"
					+ "<td style='width:60%; padding-left:30px;'>" + AddDot(data.TotalMatchScore) + "</td>"
				+ "</tr>"
				+ "<tr>"
					+ "<td style='text-align:right; width:40%;'>Hiệu suất</td>"
					+ "<td style='width:60%; padding-left:30px;'>" + Math.round(data.WonMatchScore / data.TotalMatchScore * 100) + " %</td>"
				+ "</tr>"
			+ "</table>";
			
			$("#bet_statistic").html(html);
		},
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
});



//=========================Change password page=========================

$(document).on("pagebeforeshow", "#change_password_page", function(event)
{
    $("#txtOldPassword").val("");
    $("#txtNewPassword").val("");
    $("#txtConfirmNewPassword").val("");
    $("#btnChangePassword").attr("disabled", "disabled").button("refresh");
    $("#divChangePasswordError").html("");
});

$(document).on("pageinit", "#change_password_page", function(event)
{
    $("#txtOldPassword").keyup(function() {
        validateChangePassword();
    });
	
    $("#txtNewPassword").keyup(function() {
        validateChangePassword();
    });
	
    $("#txtConfirmNewPassword").keyup(function() {
        validateChangePassword();
    });
});

function validateChangePassword()
{
    if ($("#txtOldPassword").val().length != 0 && $("#txtNewPassword").val().length != 0 && $("#txtConfirmNewPassword").val().length != 0) {
        $("#btnChangePassword").removeAttr("disabled").button("refresh");
    }
    else {
        $("#btnChangePassword").attr("disabled", "disabled").button("refresh");
    }
}

function changePassword()
{
    var $password = $("#txtNewPassword").val();
    
    if ($("#txtNewPassword").val() != $("#txtConfirmNewPassword").val())
	{
        ShowMessageBox("Đổi mật khẩu", "Xác nhận mật khẩu không trùng khớp!");
        return;
    }

    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.changePasswordUrl + "?user_id=" + localStorage.userId + "&old_password=" + $("#txtOldPassword").val() + "&new_password=" + $password,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(result)
		{
            if (result == 0) {
                ShowMessageBox("Đổi mật khẩu", "Mật khẩu cũ không trùng khớp!");
			}
            else if (result == -1) {
                ShowMessageBox("Đổi mật khẩu", "Xác nhận mật khẩu không trùng khớp!");
			}
            else {
                $.mobile.changePage("#account_page");
			}
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//=========================Change information page=========================

$(document).on("pagebeforeshow", "#change_info_page", function(event)
{
    $("#txtNewFullname").val(clientUserObject.getFullName());
    $("#txtNewAddress").val(clientUserObject.getAddress());
    $("#txtNewPhone").val(clientUserObject.getPhone());
    $("#txtNewEmail").val(clientUserObject.getEmail());
});

function changeInfo()
{
    var $email = $("#txtNewEmail").val();
    
    if (!ValidateEmail($email) && $email.length > 0)
	{
        ShowMessageBox("Lỗi", "Email không hợp lệ");
        return;
    }

    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.changeUserInfoUrl + "?user_id=" + localStorage.userId + "&fullname=" + $("#txtNewFullname").val() + "&phone=" + $("#txtNewPhone").val() + "&address=" + $("#txtNewAddress").val() + "&email=" + $email,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(result)
		{
            if (result == -1) {
                $("#change_info_error").html("Cập nhật thông tin không thành công! Xin vui lòng thử lại!");
			}
            else {
                $.mobile.changePage("#account_page");
			}
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//=========================Top account page=========================

$(document).on("pageshow", "#top_account_page", function(event)
{
    $.mobile.loading("show");
	
    var jqxhr = $.ajax({
        url: localStorage.getRankingTableUrl + localStorage.username,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data) {
            var htmlStr = "", flag = 0;

            for (var i = 0; i < localStorage.topRankValue; i++)
			{
                htmlStr = htmlStr + "<tr><td style='text-align: center; padding-left: 10px;'><div style='height: 25px; width: 25px; background-image: url(images/sortnum.png); background-repeat: no-repeat; background-position: 0px " + i * -25 + "px;'>&nbsp;</div></td>";

                if (typeof localStorage.username == "undefined" || (localStorage.username.toLowerCase() != data[i].Name.toLowerCase())) {
                    htmlStr += "<td style='text-align: center;'>" + data[i].Name + "</td><td style='text-align: center; color: CornflowerBlue;'>" + AddDot(data[i].Account) + "</td></tr>";
                }
                else
				{
                    htmlStr += "<td style='text-align: center; color: red;font-size:115%;'>" + data[i].Name + "</td><td style='text-align: center; color: red;font-size:115%;'>" + AddDot(data[i].Account) + "</td></tr>";
                    flag = 1;
                }
            }

            if (typeof localStorage.userId != "undefined" && localStorage.userId != 0 && flag == 0)
			{
                htmlStr += '<tr><td></td><td>...<td></tr>';
                htmlStr += "<tr><td style='text-align: center; padding-left: 10px;'><div style='text-align: center; color:red;'>" + data[i].Rank + "</div></td><td style='text-align: center; color: red;font-size:115%'>" + data[i].Name + "</td><td style='text-align: center; color: red;font-size:115%;'>" + AddDot(localStorage.userAccount) + "</td></tr>";
            }

            $("#top_account_panel").html(htmlStr);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
});



//=========================Top bet result page=========================

$(document).on("pageinit", "#top_bet_result_page", function(event)
{
	$("#top_bet_result_filter").change(function() {
        loadTopBetResult();
    });
});

$(document).on("pageshow", "#top_bet_result_page", function(event) {
    loadTopBetResult();
});

function loadTopBetResult()
{
	var userId;
	if (typeof localStorage.userId == "undefined") {
		userId = 0;
	}
	else {
		userId = localStorage.userId;
	}
	
	$.mobile.loading("show");
	
    var jqxhr = $.ajax({
        url: localStorage.getBetResultRankingTableUrl + userId + "&filter_type=" + $("#top_bet_result_filter").val(),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
			var htmlStr = "";
			if (data != null)
			{
				var flag = 0;

				for (var i = 0; i < localStorage.topRankValue; i++)
				{
					htmlStr = htmlStr + "<tr><td style='text-align: center; padding-left: 10px;'><div style='height: 25px; width: 25px; background-image: url(images/sortnum.png); background-repeat: no-repeat; background-position: 0px " + i * -25 + "px;'>&nbsp;</div></td>";

					if (typeof localStorage.username == "undefined" || (localStorage.username.toLowerCase() != data[i].Name.toLowerCase())) {
						htmlStr += "<td style='text-align: center;'>" + data[i].Name + "</td><td style='text-align: center; color: green;'>" + data[i].WonMatchScore + "</td><td style='text-align: center; color: red;'>" +data[i].LostMatchScore + "</td><td style='text-align: center; color: CornflowerBlue;'>" +data[i].DifferenceMatchScore + "</td></tr>";
					}
					else
					{
						htmlStr += "<td style='text-align: center; color: red;font-size:115%;'>" + data[i].Name + "</td><td style='text-align: center; color: green;font-size:115%;'>" + data[i].WonMatchScore + "</td><td style='text-align: center; color: red;font-size:115%;'>" + data[i].LostMatchScore + "</td><td style='text-align: center; color: CornflowerBlue;font-size:115%;'>" + data[i].DifferenceMatchScore + "</td></tr>";
						flag = 1;
					}
				}

				if (typeof localStorage.userId != "undefined" && localStorage.userId != 0 && flag == 0)
				{
					htmlStr += "<tr><td></td><td>...<td></tr>";
					htmlStr += "<tr><td style='text-align: center; padding-left: 10px;'><div style='text-align: center; color:red;'>" + data[data.length - 1].Rank + "</div></td><td style='text-align: center; color: red;font-size:115%'>" + data[data.length - 1].Name + "</td><td style='text-align: center; color: green;font-size:115%;'>" + data[data.length - 1].WonMatchScore + "</td><td style='text-align: center; color: red;font-size:115%;'>" + data[data.length - 1].LostMatchScore + "</td><td style='text-align: center; color: CornflowerBlue;font-size:115%;'>" + data[data.length - 1].DifferenceMatchScore + "</td></tr>";
				}
			}
			
			$("#top_bet_result_panel").html(htmlStr);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//========================= Account history page=========================

$(document).on("pageinit", "#account_history_page", function(event)
{
    $("#cbHistoryOption").change(function() {
        LoadAccountHistory();
    });
});

$(document).on("pagebeforeshow", "#account_history_page", function(event)
{
    var isLogIn = isSignined();
    if (isLogIn == 2) {
        history.back();
	}
});

$(document).on("pageshow", "#account_history_page", function(event) {
    LoadAccountHistory();
});

function LoadAccountHistory()
{
    $.mobile.loading("show");

    var jqxhr = $.ajax({
        url: localStorage.userAccountLogUrl + "username=" + localStorage.username + "&number=" + $("#cbHistoryOption").val(),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
			var $account_history_panel = $("#account_history_panel");
			var htmlStr = "";
            $account_history_panel.html("");
			
            for (var i = 0; data != null && i < data.length; i++)
			{
                if (data[i].logType < 0) {
                    data[i].change = -data[i].change;
				}

                htmlStr += "<tr> \
					<td style='text-align:right;'>" + (i + 1) + "</td> \
					<td style='text-align:center;'>" + data[i].registerDatetime + "</td> \
					<td style='text-align:right;'>" + data[i].change + "</td> \
					<td style='text-align:right; padding-right: 10px'>" + data[i].afterChange + "</td> \
					<td style='text-align:left; padding-left: 10px'>" + data[i].description + "</td> \
				</tr>";
            }
	    
			$account_history_panel.html(htmlStr);
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//========================= Account message page=========================

$(document).on("pageinit", "#message_page", function(event)
{
    $(document).on("scrollstop", "#inbox_panel", function(event)
	{
        var scrollHeight = $(document).scrollTop();
        var contentHeight = $(event.currentTarget).height();
        if (((contentHeight - scrollHeight) < 520)) {
            LoadInbox($("#inbox_panel").children("li").size());
        }
    });
});

$(document).on("pagebeforeshow", "#message_page", function(event)
{
    var isLogIn = isSignined();
    if (isLogIn == 2) {
        history.back();
	}
});

$(document).on("pageshow", "#message_page", function(event)
{
    if (localStorage.isNeedReloadMessageBox == 1) {
        LoadInbox(0);
	}

    localStorage.isNeedReloadMessageBox = 1;
    ShowUnreadMessageNotification();
});

function LoadInbox(start)
{
    if (start == 0) {
        $.mobile.loading("show");
	}

    var jqxhr = $.ajax({
        url: localStorage.GetUserMessageUrl + "username=" + localStorage.username + "&start=" + start,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
			var $inbox_panel = $("#inbox_panel");
	    
            if (start == 0)
			{
                localStorage.setItem("Messages", JSON.stringify(data));
                $inbox_panel.html("");
            }
            else
			{
                var dat = JSON.parse(localStorage.getItem("Messages"));

                for (var i = 0; data != null && i < data.length; i++) {
                    dat.push(data[i]);
				}

                localStorage.setItem("Messages", JSON.stringify(dat));
            }

			var htmlStr = "";
	    
            for (var i = 0; data != null && i < data.length; i++)
			{
                var cl = "";
                if (data[i].status == 5) {
                    cl = "class='new'";
				}
					
                htmlStr += "<li>\
					<a href=\"#\" onclick='LoadInboxItem(this, " + data[i].id + ")' " + cl + "> \
					<span class=\"date\">" + data[i].registerDatetime + "</span>\
					<span class=\"title\">" + data[i].title + "</span>\
					</a>\
				</li>";
            }

            $inbox_panel.html(htmlStr).listview("refresh");
        },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
}



//========================= Message detail page=========================

function LoadInboxItem(obj, id)
{
    if ($(obj).hasClass("new")) {
        $(obj).removeClass("new");
	}

    localStorage.inboxItem = id;
    $.mobile.changePage("#message_detail_page");
}

$(document).on("pagebeforeshow", "#message_detail_page", function(event)
{
    var isLogIn = isSignined();
	
    if (isLogIn == 2) {
        history.back();
	}
    else
	{
        var data = JSON.parse(localStorage.getItem("Messages"));
		
        for (var i = 0; data != null && i < data.length; i++)
		{
            if (data[i].id == localStorage.inboxItem)
			{
                $("#message_detail_title").html(data[i].title);
                var messageContentArray = data[i].message.split(' ');
                var messageContent = "<div class='clearer'>&nbsp;</div><span class='value'>";
				
                for (var j = 0; j < messageContentArray.length; j++)
				{
                    if (isNumber(messageContentArray[j])) {
                        messageContentArray[j] = "<span class='account_money'>" + AddDot(messageContentArray[j]) + "</span>";
                    }
                    messageContent += ' ' + messageContentArray[j];
                }
				
                if (data[i].status < 0) {
                    messageContent += "</span> <img src=''>";
                }

                $("#message_detail_panel").html(messageContent);
                $("#hdInboxItem").val(data[i].id);

                localStorage.isNeedReloadMessageBox = 0;
                break;
            }
        }
    }
});

$(document).on("pageshow", "#message_detail_page", function(event)
{
    var jqxhr = $.ajax({
        url: localStorage.UpdateUserMessageReadStatusUrl + $("#hdInboxItem").val(),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data) { },
        complete: function(jqXHR, status) {
            $.mobile.loading("hide");
        }
    });
});



/* ===================== Transfer Coins Page ====================== */

$(document).on("pageinit", "#coin_transfer_page", function(event)
{
    $("#txtAmount").keyup(function() {
        ValidateCoinTransfer();
    });

    var $receiver = $("#txtReceiver");
	
	$receiver.keyup(function()
	{
        $receiver.autocomplete(
		{
            focus: function(event, ui)
			{
                $receiver.val(ui.item.value);
                return false;
            },
            select: function(event, ui)
			{
                $receiver.val(ui.item.value);
                return false;
            },
            source: function(request, response)
			{
                $.ajax({
                    url: localStorage.SearchUserUrl + "?keyword=" + $receiver.val(),
                    dataType: "jsonp",
                    success: function(data)
					{
                        response(
							$.map(data, function(item)
							{
								return {
									label: item.Name,
									value: item.Name,
									name: item.Fullname
								}
						}));
                    },
                    error: function(data) { }
                });
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $("<li>").append("<a><span>" + item.label + "</span></a>").appendTo(ul);
        };
    });
});

$(document).on("pageshow", "#coin_transfer_page", function(event)
{
    $("#txtReceiver").val("");
    $("#txtAmount").val("");
    $("#btnTransferCoin").attr("disabled", "disabled").button("refresh");
});

function ValidateCoinTransfer()
{
    var patt = /^[0-9]*$/;
    var amount = $("#txtAmount").val();

    if (amount.length != 0 && !isNaN(amount) && Number(amount) <= localStorage.userAccount && localStorage.userAccount > 0 && patt.test(amount) && Number(amount) > 0) {
        $("#btnTransferCoin").removeAttr("disabled").button("refresh");
    }
    else {
        $("#btnTransferCoin").attr("disabled", "disabled").button("refresh");
    }
}

function SubmitCoinTransfer()
{
    var amount = $("#txtAmount").val();
	
    if (Number(localStorage.userAccount) < Number(amount) * (localStorage.transferFee + 1) + Number(localStorage.minimumRemainTransferValue))
	{
        ShowConfirmBox("Thông báo", "Bạn phải còn tối thiểu " + localStorage.minimumRemainTransferValue + " xu sau khi tặng! Bạn có muốn nạp thêm xu ?", function() {
			$.mobile.changePage('#charge_page');
		}, function() { });
		
        return;
    }
    if (Number(amount) < Number(localStorage.minimumTransferValue))
	{
        ShowMessageBox("Thông báo", "Số xu tặng nhỏ nhất phải là " + localStorage.minimumTransferValue + " xu!");
        return;
    }

    ShowConfirmBox("Xác nhận", "Bạn có muốn thực hiện giao dịch? (Phí chuyển là " + localStorage.transferFee * 100 + "%)", TransferCoin, function() {});
}

function TransferCoin()
{
    $.mobile.loading("show");
    var receiver = $("#txtReceiver");
    var amount = $("#txtAmount");

    var jqxhr = $.ajax({
        url: localStorage.transferCoinUrl + "?senderId=" + localStorage.userId + "&receiverUsername=" + receiver.val() + "&amount=" + amount.val(),
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            if (data == -1) {
                ShowMessageBox("Thông báo", "Tài khoản đích không tồn tại");
			}
            if (data > 0)
			{
                ShowMessageBox("Thông báo", "Bạn đã chuyển thành công " + amount + " xu cho tài khoản " + receiver + "!");
                receiver.val("");
                amount.val("");
                $("#btnTransferCoin").attr("disabled", "disabled").button("refresh");
            }
            else if (data == -2) {
                ShowMessageBox("Thông báo", "Người nhận " + receiver.val() + " không tồn tại");
            }
            else if (data == -3) {
                ShowMessageBox("Thông báo", "Các tài khoản hoạt động trên cùng 1 thiết bị không được tặng xu cho nhau");
            }
            else if (data == -5)
			{
                ShowConfirmBox("Thông báo", "Tài khoản xu của bạn không đủ để thực hiện giao dịch bạn có muốn nạp thêm xu ?", function() {
                    $.mobile.changePage("#charge_page");
                }, function() { });
            }
            else {
                ShowMessageBox("Thông báo", "Xảy ra sự cố khi thực hiện chuyển xu! Xin hãy thử lại hoặc liên hệ để được hỗ trợ");
            }

            var jqxhr2 = $.ajax({
                url: localStorage.getUserInfoUrl + "?user_id=" + localStorage.userId,
                type: "POST",
                cache: false,
                dataType: "jsonp",
                success: function(user) {
                    localStorage.userAccount = user.Account;
                }
            });
        },
        complete: function(data) {
            $.mobile.loading("hide");
        }
    });
}



/* ===================== Verifying Function ====================== */

function ValidateEmail(email)
{
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function VerifyEmail()
{
    $.mobile.changePage("#account_page");
	
    var $txtValidateEmail = $("#txtValidateEmail").val();
	
    if (!ValidateEmail($txtValidateEmail))
	{
        ShowMessageBox("Lỗi", "Email không hợp lệ");
        return;
    }

    $.mobile.loading("show");
    var jqxhr = $.ajax({
        url: localStorage.verifyEmailUrl + "?userId=" + localStorage.userId + "&userEmail=" + $txtValidateEmail,
        type: "POST",
        cache: false,
        dataType: "jsonp",
        success: function(data)
		{
            if (data == 1)
			{
                ShowMessageBox("Thông báo", "Vui lòng kiểm tra hòm thư Email của bạn để tiếp tục");
                clientUserObject.setEmail($("#txtValidateEmail").val());
            }
            else if (data == -1) {
                ShowMessageBox("Lỗi", "Xảy ra lỗi trong quá trình thực thi, vui lòng thử lại!");
			}
            else if (data == -2) {
                ShowMessageBox("Lỗi", "Sai định dạng Email");
			}
            else if (data == -3) {
                ShowMessageBox("Thông báo", "Tài khoản đã xác thực Email");
			}
            else if (data == -4) {
                ShowMessageBox("Thông báo", "Email đã dùng để xác thực tài khoản");
			}
        },
        error: function(data) {
            ShowMessageBox("Lỗi", "Xảy ra lỗi trong quá trình thực thi, vui lòng thử lại!");
        },
        complete: function(data) {
            $.mobile.loading("hide");
        }
    });
}

function verifyPhone()
{
    ShowSMSComposer(localStorage._verifyServiceCode, localStorage._verifySMSCode);
    $("#popupMobileValidate").popup("close");
}