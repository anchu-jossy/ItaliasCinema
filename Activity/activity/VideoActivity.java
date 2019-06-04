package com.example.ajit.italiascinema.Activity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.ajit.italiascinema.Activity.interfaces.CommonInterface;
import com.example.ajit.italiascinema.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.exoplayer2.ExoPlaybackException.TYPE_RENDERER;
import static com.google.android.exoplayer2.ExoPlaybackException.TYPE_SOURCE;
import static com.google.android.exoplayer2.ExoPlaybackException.TYPE_UNEXPECTED;

public class VideoActivity extends AppCompatActivity implements Player.EventListener, CommonInterface {
    static long pausePosition;
    public volatile boolean isAddsPlayed;
    CountDownTimer cTimer = null;
    @BindView(R.id.youtubePlayerViewer)
    FrameLayout youtubePlayerViewer;

    SimpleExoPlayerView player;
    MediaSource source;
    Timer timer;
    private SimpleExoPlayer Exoplayer;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.d("callback123", "cll");
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        Log.d("tag123", "oncreate");


        initializeVideoPlayer();



    }

    private void timerTask() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pausePosition = Exoplayer.getCurrentPosition() / 1000;
                Log.d("Timertask", "explayerposition = " + Exoplayer.getCurrentPosition() / 1000 + "");
                if (pausePosition == 3) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Log.d("tag123", "explayerposition = " + Exoplayer.getCurrentPosition() / 1000 + "");

                            isAddsPlayed = true;
                            Exoplayer.setPlayWhenReady(false);
                        }
                    });
                }

            }
        }, 0, 1000);

    }


    private void initializeVideoPlayer() {

        player = findViewById(R.id.player);
        Log.d("tag123", "oninitilaizeplayer");
        ClippingMediaSource clippingMediaSource;

        try {
            String videoUrl = "";
            Uri.fromFile(new File("/sdcard/cats.jpg"));
           // Uri subtitleUri = getSubtitleFile(R.raw.jellies);
            Uri subtitleUri=Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            Exoplayer = ExoPlayerFactory.newSimpleInstance(VideoActivity.this, trackSelector);

            Uri videoUri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.mp4");


            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exo_player");

            Format textFormat = Format.createTextSampleFormat(
                    null, // An identifier for the track. May be null.
                    MimeTypes.APPLICATION_SUBRIP, // The mime type. Must be set correctly.
                    C.SELECTION_FLAG_DEFAULT, // Selection flags for the track.
                    null);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            MediaSource subtitleSource = new SingleSampleMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(subtitleUri, textFormat, C.TIME_UNSET);

            source=new MergingMediaSource(videoSource,subtitleSource);
            Exoplayer.prepare(source);
            player.setPlayer(Exoplayer);
            player.setKeepScreenOn(true);

            Exoplayer.setPlayWhenReady(true);
            Exoplayer.addListener(VideoActivity.this);


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();

        Log.d("tag123", "onpause  of activity   " + isAddsPlayed);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(isAddsPlayed==true){
            isAddsPlayed = false;
        }
       else if(isAddsPlayed==false){
            timerTask();
        }


        Log.d("tag123", "onresume of actibvty  " + isAddsPlayed);
        Exoplayer.setPlayWhenReady(true);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag123", "onstart");
    }













    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        Log.d("tag123", "timeline" + timeline + "");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
            trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.d("tag123", "timeline" + isLoading + "");
    }


    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {


        if (playWhenReady && playbackState == Player.STATE_READY) {
            // media actually playing
        } else if (playWhenReady) {

        } else {

            if (isAddsPlayed == true) {

                Log.d("tag123", "inside playerststechanged: " + isAddsPlayed);
                startActivity(new Intent(VideoActivity.this, AdsActivity.class));
            } else {
                Log.d("tag123", "inside playerststechanged: " + isAddsPlayed);
            }
        }
    }


    @Override
    public void onRepeatModeChanged(int repeatMode) {
        Log.d("AAAAAAAAAAAAA", "STATE_READY: ");

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        Log.d("AAAAAAAAAAAAA", "onShuffleModeEnabledChanged: ");

    }


    @Override
    public void onPositionDiscontinuity(int reason) {
        Log.d("pausestate123", "onPositionDiscontinuity: pause");

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.d("pausestate123", "onseek: pause");
    }

    @Override
    public void onSeekProcessed() {
        Log.d("tag123", "timeline" + "onSeekProcessed" + "");

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        switch (error.type) {
            case TYPE_RENDERER:
                // error occurred in a Renderer
                Log.d("errorinexo", "An error occurred. Type RENDERER: " + error.getRendererException().toString());
                break;

            case TYPE_SOURCE:
                // error occurred loading data from a MediaSource.
                Log.d("errorinexo", "An error occurred. Type SOURCE: " + error.getSourceException().toString());
                break;

            case TYPE_UNEXPECTED:
                // error was an unexpected RuntimeException.
                Log.d("errorinexo", "An error occurred. Type UNEXPECTED: " + error.getUnexpectedException().toString());
                break;

            default:
                Log.d("errorinexo", "An error occurred. Type OTHER ERROR.");
                break;
        }

    }

}
