package ats.abongda.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NienLe on 06-Aug-16.
 */
@Parcel
public class LeagueSeason {
    private Integer IsCurrent;
    private List<MonthData> MonthData = new ArrayList<MonthData>();
    private Integer row_id;
    private String season_name;

    public Integer getIsCurrent() {
        return IsCurrent;
    }

    public void setIsCurrent(Integer IsCurrent) {
        this.IsCurrent = IsCurrent;
    }

    public List<LeagueSeason.MonthData> getMonthData() {
        return MonthData;
    }

    public void setMonthData(List<LeagueSeason.MonthData> monthData) {
        MonthData = monthData;
    }

    public Integer getRow_id() {
        return row_id;
    }

    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    @Parcel
    public static class MonthData {

        private Integer IsCurrent;
        private Integer Month;
        private String Name;
        private Integer Year;

        /**
         *
         * @return
         * The IsCurrent
         */
        public Integer getIsCurrent() {
            return IsCurrent;
        }

        /**
         *
         * @param IsCurrent
         * The IsCurrent
         */
        public void setIsCurrent(Integer IsCurrent) {
            this.IsCurrent = IsCurrent;
        }

        /**
         *
         * @return
         * The Month
         */
        public Integer getMonth() {
            return Month;
        }

        /**
         *
         * @param Month
         * The Month
         */
        public void setMonth(Integer Month) {
            this.Month = Month;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return Name;
        }

        /**
         *
         * @param Name
         * The Name
         */
        public void setName(String Name) {
            this.Name = Name;
        }

        /**
         *
         * @return
         * The Year
         */
        public Integer getYear() {
            return Year;
        }

        /**
         *
         * @param Year
         * The Year
         */
        public void setYear(Integer Year) {
            this.Year = Year;
        }

    }
}
