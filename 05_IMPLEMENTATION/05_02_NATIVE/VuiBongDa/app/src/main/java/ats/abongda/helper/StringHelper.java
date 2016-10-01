package ats.abongda.helper;

import android.content.Context;

import org.joda.time.DateTime;

import java.util.Date;

import ats.abongda.R;

/**
 * Created by NienLe on 13-Aug-16.
 */
public class StringHelper {


    public static String getLeagueName(int leagueId, Context context) {

        if (leagueId > 0 && leagueId <= 5) {
            switch (leagueId) {
                case 1:
                    return "Premier League";

                case 2:
                    return "Primera Liga";

                case 3:
                    return "Serie A";

                case 4:
                    return "Bundesliga";

                case 5:
                    return "League One";
            }
        }
        if (leagueId > 5 && leagueId <= 23) {
            return "Champion League";
        }
        if (leagueId > 23 && leagueId <= 46) {
            return "Europa League";
        }
        if (leagueId > 46 && leagueId <= 62) {
            return "FA cup";
        }
        return "Giải đấu khác";
    }

    public static int getTrueLeagueImageResource(int leagueId) {

        if (leagueId > 0 && leagueId <= 5) {
            switch (leagueId) {
                case 1:
                    return R.drawable.league_pl;

                case 2:
                    return R.drawable.league_lfp;

                case 3:
                    return R.drawable.league_seriea;

                case 4:
                    return R.drawable.league_bundesliga;

                case 5:
                    return R.drawable.league_l1;
            }
        }
        if (leagueId > 5 && leagueId <= 23) {
            return R.drawable.league_cl;
        }
        if (leagueId > 23 && leagueId <= 46) {
            return R.drawable.league_el;
        }
        if (leagueId > 46 && leagueId <= 62) {
            return R.drawable.league_fa;
        }
        return 0;
    }
}
