package com.ats.NIEN.abongda.UTIL;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneralConfig {
	String _announcementInfo;
	int _currentCLRound;
	String _facebookAppId;
	String _facebook_AppRequest_Title;
	String _facebook_Like_Description;
	String _facebook_Like_Image;
	String _facebook_Like_Link;
	String _facebook_Like_Title;
	String _facebook_Share_Description;
	String _facebook_Share_ImageLink;
	String _facebook_Share_Link;
	String _facebook_Share_Name;
	String _facebook_Share_Title;
	String _goalBet;
	String _hotline;
	int  _minimumGoalBetValue;
	int  _minimumNormalBetValue;
	int  _minimumTransferAmount;
	int  _minimumTransferRemainAmount;
	int _showMobileAdvertisement;
	int _transferFee;
	List<SmsConfig> smsConfigList;
	

	public String get_announcementInfo() {
		return _announcementInfo;
	}


	public void set_announcementInfo(String _announcementInfo) {
		this._announcementInfo = _announcementInfo;
	}


	public int get_currentCLRound() {
		return _currentCLRound;
	}


	public void set_currentCLRound(int _currentCLRound) {
		this._currentCLRound = _currentCLRound;
	}


	public String get_facebookAppId() {
		return _facebookAppId;
	}


	public void set_facebookAppId(String _facebookAppId) {
		this._facebookAppId = _facebookAppId;
	}


	public String get_facebook_AppRequest_Title() {
		return _facebook_AppRequest_Title;
	}


	public void set_facebook_AppRequest_Title(
			String _facebook_AppRequest_Title) {
		this._facebook_AppRequest_Title = _facebook_AppRequest_Title;
	}


	public String get_facebook_Like_Description() {
		return _facebook_Like_Description;
	}


	public void set_facebook_Like_Description(
			String _facebook_Like_Description) {
		this._facebook_Like_Description = _facebook_Like_Description;
	}


	public String get_facebook_Like_Image() {
		return _facebook_Like_Image;
	}


	public void set_facebook_Like_Image(
			String _facebook_Like_Image) {
		this._facebook_Like_Image = _facebook_Like_Image;
	}


	public String get_facebook_Like_Link() {
		return _facebook_Like_Link;
	}


	public void set_facebook_Like_Link(
			String _facebook_Like_Link) {
		this._facebook_Like_Link = _facebook_Like_Link;
	}


	public String get_facebook_Like_Title() {
		return _facebook_Like_Title;
	}


	public void set_facebook_Like_Title(
			String _facebook_Like_Title) {
		this._facebook_Like_Title = _facebook_Like_Title;
	}


	public String get_facebook_Share_Description() {
		return _facebook_Share_Description;
	}


	public void set_facebook_Share_Description(
			String _facebook_Share_Description) {
		this._facebook_Share_Description = _facebook_Share_Description;
	}


	public String get_facebook_Share_ImageLink() {
		return _facebook_Share_ImageLink;
	}


	public void set_facebook_Share_ImageLink(
			String _facebook_Share_ImageLink) {
		this._facebook_Share_ImageLink = _facebook_Share_ImageLink;
	}


	public String get_facebook_Share_Link() {
		return _facebook_Share_Link;
	}


	public void set_facebook_Share_Link(
			String _facebook_Share_Link) {
		this._facebook_Share_Link = _facebook_Share_Link;
	}


	public String get_facebook_Share_Name() {
		return _facebook_Share_Name;
	}


	public void set_facebook_Share_Name(
			String _facebook_Share_Name) {
		this._facebook_Share_Name = _facebook_Share_Name;
	}


	public String get_facebook_Share_Title() {
		return _facebook_Share_Title;
	}


	public void set_facebook_Share_Title(
			String _facebook_Share_Title) {
		this._facebook_Share_Title = _facebook_Share_Title;
	}


	public String get_goalBet() {
		return _goalBet;
	}


	public void set_goalBet(String _goalBet) {
		this._goalBet = _goalBet;
	}


	public String get_hotline() {
		return _hotline;
	}


	public void set_hotline(String _hotline) {
		this._hotline = _hotline;
	}


	public int get_minimumGoalBetValue() {
		return _minimumGoalBetValue;
	}


	public void set_minimumGoalBetValue(int _minimumGoalBetValue) {
		this._minimumGoalBetValue = _minimumGoalBetValue;
	}


	public int get_minimumNormalBetValue() {
		return _minimumNormalBetValue;
	}


	public void set_minimumNormalBetValue(int _minimumNormalBetValue) {
		this._minimumNormalBetValue = _minimumNormalBetValue;
	}


	public int get_minimumTransferAmount() {
		return _minimumTransferAmount;
	}


	public void set_minimumTransferAmount(int _minimumTransferAmount) {
		this._minimumTransferAmount = _minimumTransferAmount;
	}


	public int get_minimumTransferRemainAmount() {
		return _minimumTransferRemainAmount;
	}


	public void set_minimumTransferRemainAmount(int _minimumTransferRemainAmount) {
		this._minimumTransferRemainAmount = _minimumTransferRemainAmount;
	}


	public int get_showMobileAdvertisement() {
		return _showMobileAdvertisement;
	}


	public void set_showMobileAdvertisement(int _showMobileAdvertisement) {
		this._showMobileAdvertisement = _showMobileAdvertisement;
	}


	public int get_transferFee() {
		return _transferFee;
	}


	public void set_transferFee(int _transferFee) {
		this._transferFee = _transferFee;
	}


	public List<SmsConfig> getSmsConfigList() {
		return smsConfigList;
	}


	public void setSmsConfigList(List<SmsConfig> smsConfigList) {
		this.smsConfigList = smsConfigList;
	}


	class SmsConfig{
		String _SMSCode;
		String _guide;
		String _serviceCode;
		int _type;
	}


	public GeneralConfig(String str) {
		super();
		// TODO Auto-generated constructor stub
		if (str.equals("")) {
			return;
		}
		try {
			JSONObject o = new JSONObject(str);
			_announcementInfo = o.getString("_announcementInfo");
			_currentCLRound = o.getInt("_currentCLRound");
			 _facebookAppId = o.getString("_facebookAppId");
			 _facebook_AppRequest_Title = o.getString("_facebook_AppRequest_Title");
			 _facebook_Like_Description = o.getString("_facebook_Like_Description");
			 _facebook_Like_Image = o.getString("_facebook_Like_Image");
			 _facebook_Like_Link = o.getString("_facebook_Like_Link");
			 _facebook_Like_Title= o.getString("_facebook_Like_Title");
			 _facebook_Share_Description = o.getString("_facebook_Share_Description");
			 _facebook_Share_ImageLink = o.getString("_facebook_Share_ImageLink");
			 _facebook_Share_Link = o.getString("_facebook_Share_Link");
			 _facebook_Share_Name = o.getString("_facebook_Share_Name");
			 _facebook_Share_Title = o.getString("_facebook_Share_Title");
			 _goalBet = o.getString("_goalBet");
			 _hotline = o.getString("_hotline");
			  _minimumGoalBetValue = o.getInt("_minimumGoalBetValue");
			  _minimumNormalBetValue = o.getInt("_minimumNormalBetValue");
			  _minimumTransferAmount = o.getInt("_minimumTransferAmount");
			  _minimumTransferRemainAmount = o.getInt("_minimumTransferRemainAmount");
			 _showMobileAdvertisement = o.getInt("_showMobileAdvertisement");
			 _transferFee = o.getInt("_transferFee");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
