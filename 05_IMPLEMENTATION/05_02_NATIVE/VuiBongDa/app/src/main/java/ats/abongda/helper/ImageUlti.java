package ats.abongda.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import ats.abongda.R;

/**
 * Created by NienLe on 07-Aug-16.
 */
public class ImageUlti {

    public static void loadImage(Context context, String url, ImageView target){
        try {
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(target);
        }catch (Exception e){
            LogUtil.e(e);
            if (target != null) target.setImageResource(R.drawable.no_image);
        }
    }

    public static void loadImage(Context context, int id, ImageView target){
        try {
            Picasso.with(context)
                    .load(id)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(target);
        }catch (Exception e){
            LogUtil.e(e);
            if (target != null) target.setImageResource(R.drawable.no_image);
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoUrl)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoUrl, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoUrl);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            LogUtil.e(e);

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
