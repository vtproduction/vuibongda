(function(cordova)
{
    var cordovaRef = window.PhoneGap || window.Cordova || window.cordova;

    function PushNotification() {}

    // Call this to register for push notifications. Content of [options] depends on whether we are working with APNS (iOS) or GCM (Android)
    PushNotification.prototype.register = function(successCallback, errorCallback, options) {
        cordovaRef.exec(successCallback, errorCallback, "PushPlugin", "register", [options]);
    };

    // Call this to unregister for push notifications
    PushNotification.prototype.unregister = function(successCallback, errorCallback) {
        cordovaRef.exec(successCallback, errorCallback, "PushPlugin", "unregister", []);
    };

    // Call this to set the application icon badge
    PushNotification.prototype.setApplicationIconBadgeNumber = function(successCallback, badge) {
        cordovaRef.exec(successCallback, successCallback, "PushPlugin", "setApplicationIconBadgeNumber", [{badge: badge}]);
    };

    cordova.addConstructor(function()
	{
        if (!window.plugins) {
            window.plugins = {};
		}
		
        window.plugins.pushNotification = new PushNotification();
    });

})(window.cordova || window.Cordova || window.PhoneGap);


var pushNotification;

function onDeviceReady() {
	// Hide splash screen
	navigator.splashscreen.hide();
	
	// Register push notification
	try 
	{ 
		pushNotification = window.plugins.pushNotification;
		if (device.platform == 'android' || device.platform == 'Android') {
			pushNotification.register(successHandler, errorHandler, {"senderID":GCMProjectId,"ecb":"onNotificationGCM"});
		} else {
			pushNotification.register(tokenHandler, errorHandler, {"badge":"true","sound":"true","alert":"true","ecb":"onNotificationAPN"});
		}
	}
	catch(err) { }
	
	//Get device id for ios and check for update
	if (ABongDaAppType == 2)
	{
		localStorage.imei = '';
    
		deviceId = window.plugins.deviceId;
		deviceId.getAdIdentifier(function(adIdentiferValue)
		{
			localStorage.imei = adIdentiferValue;
			InitialStorage();
			CheckForUpdate();
		});
	}
	else { // Check for android update
		CheckForUpdate();
	}

	// Load bet list cache data
	try
	{
	    var sqlObject = new dataObj();
	    sqlObject.loadDB();

        /* Load account information */
        SetAccountTypeInfo();
	} 
    catch (err) { }
}


// handle APNS notifications for iOS
function onNotificationAPN(e)
{
    if (e.foreground)
	{
        if (e.sound)
		{
            var snd = new Media(e.sound);
            snd.play();
        }

        if (e.alert) {
            navigator.notification.alert(e.alert);
        }
    }

    if (e.badge) {
        pushNotification.setApplicationIconBadgeNumber(successHandler, e.badge);
    }
}


// handle GCM notifications for Android
function onNotificationGCM(e)
{
    switch (e.event)
	{
        case "registered":
            if (e.regid.length > 0)
			{
                localStorage.registrationId = e.regid;
                var jqxhr = $.ajax(
				{
                    url: localStorage.registerPushnotificationRegIDURL + e.regid,
                    type: "GET",
                    dataType: "json"
                });

            }
            break;

        case "message":
            // if this flag is set, this notification happened while we were in the foreground.
            // you might want to play a sound to get the user's attention, throw up a dialog, etc.
            if (e.foreground)
			{
                // if the notification contains a soundname, play it.
                var my_media = new Media("/android_asset/www/" + e.soundname);
                my_media.play();
            }
            break;
    }
}

// token handler for ios
function tokenHandler (result)
{
	localStorage.registrationId = result;
	var jqxhr = $.ajax(
	{			
		url: localStorage.registerPushnotificationRegIDURL + result,
		type:"GET",
		dataType: "json"
	});
}

function successHandler (result) {}

function errorHandler (error) {}

document.addEventListener('deviceready', onDeviceReady, true);