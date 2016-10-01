package ats.abongda.helper;

import android.util.Log;

/**
 * Created by NienLe on 11-Jul-16.
 */
public class LogUtil {
    /*
     * Used to identify the source of a log message. It usually identifies the
     * class or activity where the log call occurs
     */
    public static String LOGTAG = "abongdaLogtag";

    /*
     * Is sent a VERBOSE log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LOGV = true;

    /*
     * Is sent a DEBUG log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LogD = true;

    /*
     * Is sent a ERROR log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LogE = true;

    /*
     * Is sent a INFO log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LOGI = true;

    /*
     * Is sent a WARN log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LOGW = true;

    /**
     * Send a VERBOSE log message.
     *
     * @param log : The message you would like logged.
     */
    public static void v(String log) {
        if (LOGV) {
            Log.v(LOGTAG, log);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param log : The message you would like logged.
     * @param tr  : An exception to log
     */
    public static void v(String log, Throwable tr) {
        if (LOGV) {
            Log.v(LOGTAG, log, tr);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param log The message you would like logged.
     */
    public static void d(String log) {
        if (LogD) {
            Log.d(LOGTAG, log);
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param log       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void d(String log, Throwable throwable) {
        if (LogD) {
            Log.d(LOGTAG, log, throwable);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param log The message you would like logged.
     */
    public static void e(String log) {
        if (LogE) {
            Log.e(LOGTAG, log);
        }
    }

    public static void e(Throwable e) {
        if (LogE) {
            Log.e(LOGTAG, Log.getStackTraceString(e));
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param log The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String log, Throwable tr) {
        if (LogE) {
            Log.e(LOGTAG, log, tr);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param log The message you would like logged.
     */
    public static void i(String log) {
        if (LOGI) {
            Log.i(LOGTAG, log);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param log The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String log, Throwable tr) {
        if (LOGI) {
            Log.i(LOGTAG, log, tr);
        }
    }

    /**
     * Send a WARN log message
     *
     * @param log The message you would like logged.
     */
    public static void w(String log) {
        if (LOGW) {
            Log.w(LOGTAG, log);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param log The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String log, Throwable tr) {
        if (LOGW) {
            Log.w(LOGTAG, log, tr);
        }
    }


    /************
     * WTHLOG
     ************/
    public static final String WTHLOG = "ohgodplease";
    public static final boolean WTH = true;

    public static void wth(String log) {
        if (WTH) {
            Log.d(WTHLOG, log);
        }
    }


}
