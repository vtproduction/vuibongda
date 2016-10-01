
package ats.abongda.model.match;

import org.parceler.Parcel;

import javax.annotation.Generated;

import ats.abongda.R;

@Generated("org.jsonschema2pojo")
@Parcel
public class InfoList {

    private String Extra;
    private String GuestInfo;
    private String HomeInfo;
    private Integer Id;
    private String Score;
    private String Status;
    private String Type;

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    public String getGuestInfo() {
        return GuestInfo;
    }

    public void setGuestInfo(String guestInfo) {
        GuestInfo = guestInfo;
    }

    public String getHomeInfo() {
        return HomeInfo;
    }

    public void setHomeInfo(String homeInfo) {
        HomeInfo = homeInfo;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isHomeTurn(){
        return this.HomeInfo.length() > this.GuestInfo.length();
    }

    public int getMatchDetailImage(){
        if (this.Type.equalsIgnoreCase("01") || this.Type.equalsIgnoreCase("10")) {
            return R.drawable.football_icon;
        }else if (this.Type.equalsIgnoreCase("02") || this.Type.equalsIgnoreCase("20")) {
            return R.drawable.yellow_card;
        }else if (this.Type.equalsIgnoreCase("03") || this.Type.equalsIgnoreCase("30")) {
            return R.drawable.red_card;
        }else if (this.Type.equalsIgnoreCase("04") || this.Type.equalsIgnoreCase("40")) {
            return R.drawable.double_yellow_card;
        }else if (this.Type.equalsIgnoreCase("05") || this.Type.equalsIgnoreCase("50")) {
            return R.drawable.pen_failed;
        }
        return 0;
    }
}
