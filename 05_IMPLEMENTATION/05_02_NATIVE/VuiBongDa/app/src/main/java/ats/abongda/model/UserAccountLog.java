package ats.abongda.model;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class UserAccountLog {

    private long afterChange;
    private long beforeChange;
    private long change;
    private String description;
    private int logType;
    private String registerDatetime;

    public long getAfterChange() {
        return afterChange;
    }

    public void setAfterChange(long afterChange) {
        this.afterChange = afterChange;
    }

    public long getBeforeChange() {
        return beforeChange;
    }

    public void setBeforeChange(long beforeChange) {
        this.beforeChange = beforeChange;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getRegisterDatetime() {
        return registerDatetime;
    }

    public void setRegisterDatetime(String registerDatetime) {
        this.registerDatetime = registerDatetime;
    }
}
