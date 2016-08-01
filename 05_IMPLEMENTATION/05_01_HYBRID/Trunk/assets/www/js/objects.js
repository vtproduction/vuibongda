//  Bet info

function betObj(bet, matchId, betType, betValue, matchHandicap, betHomeGoal, betGuestGoal, betResult, betResultValue)
{
    this.bet = bet;
    this.matchId = matchId;
    this.betType = betType;
    this.betValue = betValue;
    this.betHomeGoal = betHomeGoal;
    this.betGuestGoal = betGuestGoal;
    this.matchHandicap = matchHandicap;
    this.betStatus = '5';
    this.betResult = typeof betResult !== 'undefined' ? betResult : 0;
    this.betResultValue = typeof betResultValue !== 'undefined' ? betResultValue : 0;
}

betObj.prototype =
{
    getBet: function() {
        return this.bet;
    },
	
    setBet: function(bet) {
        this.bet = bet;
    },
	
    getMatchId: function() {
        return this.matchId;
    },
	
    setMatchId: function(matchId) {
        this.matchId = matchId;
    },
	
    getBetType: function() {
        return this.betType;
    },
	
    setBetType: function(betType) {
        this.betType = betType;
    },
	
    getBetValue: function() {
        return this.betValue;
    },
	
    setBetValue: function(betValue) {
        this.betValue = betValue;
    },
	
    getHandicap: function() {
        return this.matchHandicap;
    },
	
    setHandicap: function(handicap) {
        this.matchHandicap = handicap;
    },
	
    getBetHomeGoal: function() {
        return this.betHomeGoal;
    },
	
    setBetHomeGoal: function(betHomeGoal) {
        this.betHomeGoal = betHomeGoal;
    },
	
    getBetGuestGoal: function() {
        return this.betGuestGoal;
    },
	
    setBetGuestGoal: function(betGuestGoal) {
        this.betGuestGoal = betGuestGoal;
    },
	
    getBetStatus: function() {
        return this.betStatus;
    },
	
    setBetStatus: function(status) {
        this.betStatus = status;
    },
	
    getBetResult: function() {
        return this.betResult;
    },
	
    setBetResult: function(betResult) {
        this.betResult = betResult;
    },
	
    getBetResultValue: function() {
        return this.betResultValue;
    },
	
    setBetResultValue: function(betResultValue) {
        this.betResultValue = betResultValue;
    }
}



//  User's information

function userObj(userName, fullName, address, coin, phone, email)
{
    this.userName = userName;
    this.fullName = fullName;
    this.address = address;
    this.coin = coin;
    this.phone = phone;
    this.email = email;
    this.betList = new Array();
}

userObj.prototype =
{
    getUserName: function() {
        return this.userName;
    },
	
    setUserName: function(userName) {
        this.userName = userName;
    },
	
    getFullName: function() {
        return this.fullName;
    },
	
    setFullName: function(fullName) {
        this.fullName = fullName;
    },
	
    getAddress: function() {
        return this.address;
    },
	
    setAddress: function(address) {
        this.address = address;
    },
	
    getCoin: function() {
        return this.coin;
    },
	
    setCoin: function(coin) {
        this.coin = coin;
    },
	
    getPhone: function() {
        return this.phone;
    },
	
    setPhone: function(phone) {
        this.phone = phone;
    },
	
    getEmail: function() {
        return this.email;
    },
	
    setEmail: function(email) {
        this.email = email;
    },

    getBetList: function () {
        return this.betList;
    },

    setBetList: function (betList) {
        if (Object.prototype.toString.call(betList) === "[object Array]") {
            this.betList = betList;
        }
    },

    pushBet: function (obj) {
        this.betList.push(obj);
    },

    flush: function () {
        this.userName = "";
        this.fullName = "";
        this.address = "";
        this.coin = 0;
        this.phone = "";
        this.email = "";
        this.betList = new Array();
    }
}

// Data handler

function dataObj() {
    this.db = window.openDatabase("ABongda", "1.0", "ABongda DB", 1000000);
}

function ensureTableExists(tx){
    tx.executeSql('CREATE TABLE IF NOT EXISTS UserInfo (id INTEGER PRIMARY KEY, userName)');
};

function PreLoadUserInformation()
{
    var jqxhr = $.ajax({
		url: localStorage.getUserInfoUrl + "?user_id=" + localStorage.userId,
		type: "POST",
		cache: false,
		dataType: "jsonp",
		success: function(user)
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
		},
		complete: function(jqXHR, status) { }
	});
};

dataObj.prototype =
{
    loadDB: function (successCB)
	{
        this.db.transaction(
            function (tx)
			{
                ensureTableExists(tx);
                tx.executeSql('SELECT * FROM UserInfo', [],
                // Fetching data
                    function (tx, result)
					{
                        var len = result.rows.length;
                        if (len > 0)
						{
                            localStorage.userId = result.rows.item(0).id;
                            localStorage.username = result.rows.item(0).userName;
							PreLoadUserInformation();
                            if (typeof successCB !== "undefined") successCB();
                        }
                    }
                );
            }
        );
    },

    updateDB: function (userId, userName)
	{
        this.db.transaction(
            function (tx)
			{
                ensureTableExists(tx);
                var sql = 'INSERT INTO UserInfo (id, userName) VALUES (' + userId + ',' + '"' + userName + '")';
                tx.executeSql(sql);
            }
        );
    },

    deleteEntry: function (id)
	{
        this.db.transaction(
            function (tx)
			{
                ensureTableExists(tx);
                tx.executeSql('DELETE FROM UserInfo WHERE id=' + id);
            }
        );
    }
}

function socialObj(title, image, caption, link, description)
{
    this.title = title;
    this.image = image;
    this.caption = caption;
    this.link = link;
    this.description = description;
}

socialObj.prototype =
{
    setTitle: function(title) {
        this.title = title;
    },
	
    getTitle: function () {
        return this.title;
    },
    
    setImage: function(image) {
        this.image = image;
    },
    getImage: function () {
        return this.image;
    },
    
    setLink: function(link) {
        this.link = link;
    },
    getLink: function () {
        return this.link;
    },
    
    setCaption: function(caption) {
        this.title = caption;
    },
    getCaption: function () {
        return this.caption;
    },
    
    setDescription: function(description) {
        this.description = description;
    },
    getDescription: function () {
        return this.description;
    }
}