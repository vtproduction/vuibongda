package ats.abongda.activity;

import android.net.Uri;
import android.os.Bundle;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;

import ats.abongda.R;
import ats.abongda.activity.base.BaseActivity;
import ats.abongda.helper.LogUtil;
import ats.abongda.helper.ToastUtil;

/**
 * Created by NienLe on 17-Sep-16.
 */

public class VideoPlayerActivity extends BaseActivity implements EasyVideoCallback {
    private EasyVideoPlayer player;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_video_player);

        // Grabs a reference to the player view
        player = (EasyVideoPlayer) findViewById(R.id.player);
        player.setHideControlsOnPlay(true);
        player.setAutoFullscreen(true);
        player.setAutoPlay(true);
        player.setCallback(this);

        player.setSource(Uri.parse(url));

    }


    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        player.pause();
    }

    // Methods for the implemented EasyVideoCallback

    @Override
    public void onPreparing(EasyVideoPlayer player) {
        // TODO handle if needed
        showProgressDialog();
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        // TODO handle
        hideProgressDialog();
    }

    @Override
    public void onBuffering(int percent) {
        // TODO handle if needed
    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        // TODO handle
        LogUtil.e(e);
        ToastUtil.showError();
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        // TODO handle if needed
        //player.start();
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {
        // TODO handle if used
    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {
        // TODO handle if used
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {
        // TODO handle if needed
    }

    @Override
    public void onPaused(EasyVideoPlayer player) {
        // TODO handle if needed
    }
}
