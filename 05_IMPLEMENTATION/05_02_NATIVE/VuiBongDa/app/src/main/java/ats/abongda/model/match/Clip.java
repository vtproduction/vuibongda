package ats.abongda.model.match;

import org.parceler.Parcel;

/**
 * Created by NienLe on 16-Sep-16.
 */
@Parcel
public class Clip {
    public String Clip_description;
    public String Clip_link;
    public String Match_minute;

    public String getClip_description() {
        return Clip_description;
    }

    public void setClip_description(String clip_description) {
        Clip_description = clip_description;
    }

    public String getClip_link() {
        return Clip_link;
    }

    public void setClip_link(String clip_link) {
        Clip_link = clip_link;
    }

    public String getMatch_minute() {
        return Match_minute;
    }

    public void setMatch_minute(String match_minute) {
        Match_minute = match_minute;
    }
}
