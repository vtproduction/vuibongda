package ats.abongda.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import ats.abongda.helper.LogUtil;

/**
 * Created by NienLe on 06-Aug-16.
 */

@Generated("org.jsonschema2pojo")
public class GeneralConfiguration {

    private List<SMSConfig> _SMSConfig = new ArrayList<SMSConfig>();
    private Object _announcementInfo;
    private Integer _currentCLRound;
    private String _facebookAppId;
    private String _facebook_AppRequest_Title;
    private String _facebook_Like_Description;
    private String _facebook_Like_Image;
    private String _facebook_Like_Link;
    private String _facebook_Like_Title;
    private String _facebook_Share_Description;
    private String _facebook_Share_ImageLink;
    private String _facebook_Share_Link;
    private String _facebook_Share_Name;
    private String _facebook_Share_Title;
    private String _goalBet;
    private String _hotline;
    private Integer _minimumGoalBetValue;
    private Integer _minimumNormalBetValue;
    private Integer _minimumTransferAmount;
    private Integer _minimumTransferRemainAmount;
    private Integer _showMobileAdvertisement;
    private Float _transferFee;

    public List<SMSConfig> get_SMSConfig() {
        return _SMSConfig;
    }

    public void set_SMSConfig(List<SMSConfig> _SMSConfig) {
        this._SMSConfig = _SMSConfig;
    }

    public Object get_announcementInfo() {
        return _announcementInfo;
    }

    public void set_announcementInfo(Object _announcementInfo) {
        this._announcementInfo = _announcementInfo;
    }

    public Integer get_currentCLRound() {
        return _currentCLRound;
    }

    public void set_currentCLRound(Integer _currentCLRound) {
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

    public void set_facebook_AppRequest_Title(String _facebook_AppRequest_Title) {
        this._facebook_AppRequest_Title = _facebook_AppRequest_Title;
    }

    public String get_facebook_Like_Description() {
        return _facebook_Like_Description;
    }

    public void set_facebook_Like_Description(String _facebook_Like_Description) {
        this._facebook_Like_Description = _facebook_Like_Description;
    }

    public String get_facebook_Like_Image() {
        return _facebook_Like_Image;
    }

    public void set_facebook_Like_Image(String _facebook_Like_Image) {
        this._facebook_Like_Image = _facebook_Like_Image;
    }

    public String get_facebook_Like_Link() {
        return _facebook_Like_Link;
    }

    public void set_facebook_Like_Link(String _facebook_Like_Link) {
        this._facebook_Like_Link = _facebook_Like_Link;
    }

    public String get_facebook_Like_Title() {
        return _facebook_Like_Title;
    }

    public void set_facebook_Like_Title(String _facebook_Like_Title) {
        this._facebook_Like_Title = _facebook_Like_Title;
    }

    public String get_facebook_Share_Description() {
        return _facebook_Share_Description;
    }

    public void set_facebook_Share_Description(String _facebook_Share_Description) {
        this._facebook_Share_Description = _facebook_Share_Description;
    }

    public String get_facebook_Share_ImageLink() {
        return _facebook_Share_ImageLink;
    }

    public void set_facebook_Share_ImageLink(String _facebook_Share_ImageLink) {
        this._facebook_Share_ImageLink = _facebook_Share_ImageLink;
    }

    public String get_facebook_Share_Link() {
        return _facebook_Share_Link;
    }

    public void set_facebook_Share_Link(String _facebook_Share_Link) {
        this._facebook_Share_Link = _facebook_Share_Link;
    }

    public String get_facebook_Share_Name() {
        return _facebook_Share_Name;
    }

    public void set_facebook_Share_Name(String _facebook_Share_Name) {
        this._facebook_Share_Name = _facebook_Share_Name;
    }

    public String get_facebook_Share_Title() {
        return _facebook_Share_Title;
    }

    public void set_facebook_Share_Title(String _facebook_Share_Title) {
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

    public Integer get_minimumGoalBetValue() {
        return _minimumGoalBetValue;
    }

    public void set_minimumGoalBetValue(Integer _minimumGoalBetValue) {
        this._minimumGoalBetValue = _minimumGoalBetValue;
    }

    public Integer get_minimumNormalBetValue() {
        return _minimumNormalBetValue;
    }

    public void set_minimumNormalBetValue(Integer _minimumNormalBetValue) {
        this._minimumNormalBetValue = _minimumNormalBetValue;
    }

    public Integer get_minimumTransferAmount() {
        return _minimumTransferAmount;
    }

    public void set_minimumTransferAmount(Integer _minimumTransferAmount) {
        this._minimumTransferAmount = _minimumTransferAmount;
    }

    public Integer get_minimumTransferRemainAmount() {
        return _minimumTransferRemainAmount;
    }

    public void set_minimumTransferRemainAmount(Integer _minimumTransferRemainAmount) {
        this._minimumTransferRemainAmount = _minimumTransferRemainAmount;
    }

    public Integer get_showMobileAdvertisement() {
        return _showMobileAdvertisement;
    }

    public void set_showMobileAdvertisement(Integer _showMobileAdvertisement) {
        this._showMobileAdvertisement = _showMobileAdvertisement;
    }

    public Float get_transferFee() {
        return _transferFee;
    }

    public void set_transferFee(Float _transferFee) {
        this._transferFee = _transferFee;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static GeneralConfiguration build(String json){
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, GeneralConfiguration.class);
        }catch (Exception e){
            LogUtil.e(e);
            return null;
        }
    }



    public class SMSConfig {

        private String _SMSCode;
        private String _guide;
        private String _serviceCode;
        private Integer _type;

        public String get_SMSCode() {
            return _SMSCode;
        }

        public void set_SMSCode(String _SMSCode) {
            this._SMSCode = _SMSCode;
        }

        public String get_guide() {
            return _guide;
        }

        public void set_guide(String _guide) {
            this._guide = _guide;
        }

        public String get_serviceCode() {
            return _serviceCode;
        }

        public void set_serviceCode(String _serviceCode) {
            this._serviceCode = _serviceCode;
        }

        public Integer get_type() {
            return _type;
        }

        public void set_type(Integer _type) {
            this._type = _type;
        }
    }
}

