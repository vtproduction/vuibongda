function ExtractDate(d)
{
    var date = d.substring(6);
    date = date.replace('/', '');
    date = date.replace(')', '');
    return new Date(parseInt(date));
}

function AddDot(number)
{
    number += "";
    var x = number;
    var rgx = /(\d+)(\d{3})/;

    while (rgx.test(x)) {
        x = x.replace(rgx, '$1' + '.' + '$2');
    }

    return x;
}

function isNumber(o) {
    return !isNaN(o - 0) && o !== null && o !== "" && o !== false;
}

function ShowMessageBox(title, content)
{
    $(".popupBoxHeader").html(title);
    $(".popupBoxContent").html(content);
	
    var messageBox = $("#messageBox");
    messageBox.popup();
    $(".closePopup").button();
    messageBox.show();
    messageBox.popup("open");
}

function ShowConfirmBox(title, content, yes, no)
{
    var confirmBox = $("#confirmBox");
    var yesBtn = $(".yesButton");
    var noBtn = $(".noButton");
	
    $(".popupBoxHeader").html(title);
    $(".popupBoxContent").html(content);
	
    confirmBox.show();
    confirmBox.popup({history: false});

    yesBtn.unbind("click");
    yesBtn.click(function()
	{
        yes();
        confirmBox.popup("close");
    });

    noBtn.unbind("click");
    noBtn.click(function(e)
	{
        no();
        confirmBox.popup("close");
    });

    $(".closePopup").button();
    confirmBox.popup("open");

    return false;
}

function Ellipsify(str)
{
    if ($("#emailVerify").children(".img-verified").size() > 0)
	{
        if (str != null && str.length > 18 && str.substring(18, str.length).length > 3) {
            return (str.substring(0, 18) + "...");
        }
        else {
            return str;
        }
    }
    else if (str != null && str.length > 18 && str.substring(18, str.length).length > 3) {
        return (str.substring(0, 18) + "...");
    }
    else {
        return str;
    }
}