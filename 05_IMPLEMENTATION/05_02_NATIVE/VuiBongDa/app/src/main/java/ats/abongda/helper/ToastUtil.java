package ats.abongda.helper;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ats.abongda.MainApplication;
import ats.abongda.R;

/**
 * Created by NienLe on 11-Jul-16.
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(String text) {
        if (MainApplication.get() == null) return;
        toast = Toast.makeText(MainApplication.get(), text, Toast.LENGTH_SHORT);
        //NIENLM: centered the text inside toast.
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        if (toast != null && toast.getView() != null && !toast.getView().isShown())
            toast.show();
    }

    public static void show(int textId) {
        if (MainApplication.get() == null) return;
        toast = Toast.makeText(MainApplication.get(), MainApplication.get().getString(textId), Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        if (toast != null && toast.getView() != null && !toast.getView().isShown())
            toast.show();
    }
    public static void showError() {
        if (MainApplication.get() == null) return;
        toast = Toast.makeText(MainApplication.get(), MainApplication.get().getString(R.string.dacoloixayra), Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        if (toast != null && toast.getView() != null && !toast.getView().isShown())
            toast.show();
    }

    public static void showNetWorkError(Throwable t){
        if (MainApplication.get() == null) return;
        if (t instanceof IOException){
            toast = Toast.makeText(MainApplication.get(), MainApplication.get().getString(R.string.khongcoinernet), Toast.LENGTH_SHORT);
        }else{
            toast = Toast.makeText(MainApplication.get(), MainApplication.get().getString(R.string.dacoloixayra), Toast.LENGTH_SHORT);
        }
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        if (toast != null && toast.getView() != null && !toast.getView().isShown())
            toast.show();
    }

    public static void dismiss() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
