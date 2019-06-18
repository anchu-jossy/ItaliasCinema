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
import android.widget.Toast;

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.interfaces.CommonInterface;
import com.example.ajit.italiascinema.Activity.model.Add;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.Activity.model.SetWatchHistoryResponse;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.exoplayer2.ExoPlaybackException.TYPE_RENDERER;
import static com.google.android.exoplayer2.ExoPlaybackException.TYPE_SOURCE;
import static com.google.android.exoplayer2.ExoPlaybackException.TYPE_UNEXPECTED;

public class VideoActivity extends AppCompatActivity implements Player.EventListener, CommonInterface {
    static long pausePosition;
    public volatile boolean isAddsPlayed;
    public volatile int count = 0;
    CountDownTimer cTimer = null;
    @BindView(R.id.youtubePlayerViewer)
    FrameLayout youtubePlayerViewer;
    SimpleExoPlayerView player;
    MediaSource source;
    Timer timer;
    List<Add> addArrayList = new ArrayList<>();
    Info info;
    HashMap<Long, String> map = new HashMap<>();
    private SimpleExoPlayer Exoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setWatchhistory();

        if (SaveDataClass.getInstance().getSetIndexFrom().equals("feature")) {
            if (getIntent().getExtras() != null) {
                info = getIntent().getExtras().getParcelable("featurevideoarray");
                if (info.getAdds() != null) {

                    addArrayList.addAll(info.getAdds());
                    for (int i = 0; i < addArrayList.size(); i++) {
                        map.put(Long.valueOf(addArrayList.get(i).getStartTime()), addArrayList.get(i).getVideoUrl());
                    }
                    Log.d("infodata", map.keySet() + "");
                }

                initializeVideoPlayer(info);
            }
        } else if (SaveDataClass.getInstance().getSetIndexFrom().equals("trending")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("trendingvideodata");
                if (info.getAdds() != null) {

                    addArrayList.addAll(info.getAdds());
                    for (int i = 0; i < addArrayList.size(); i++) {
                        map.put(Long.valueOf(addArrayList.get(i).getStartTime()), addArrayList.get(i).getVideoUrl());
                    }
                    Log.d("infodata", map.keySet() + "");
                }
                Log.d("infoArrayList", info.getVideoLink() + "");
                initializeVideoPlayer(info);
            }
        } else if (SaveDataClass.getInstance().getSetIndexFrom().equals("latest")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("latestdata");
                if (info.getAdds() != null) {

                    addArrayList.addAll(info.getAdds());
                    for (int i = 0; i < addArrayList.size(); i++) {
                        map.put(Long.valueOf(addArrayList.get(i).getStartTime()), addArrayList.get(i).getVideoUrl());
                    }
                    Log.d("infodata", map.keySet() + "");
                }
                initializeVideoPlayer(info);
            }
        } else if (SaveDataClass.getInstance().getSetIndexFrom().equals("recent")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("recentdata");
                if (info.getAdds() != null) {

                    addArrayList.addAll(info.getAdds());
                    for (int i = 0; i < addArrayList.size(); i++) {
                        map.put(Long.valueOf(addArrayList.get(i).getStartTime()), addArrayList.get(i).getVideoUrl());
                    }
                    Log.d("infodata", map.keySet() + "");
                }
                Log.d("infoArrayList", info.getVideoLink() + "");
                initializeVideoPlayer(info);
            }
        }
    }

    private void timerTask(HashMap<Long, String> map) {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pausePosition = Exoplayer.getCurrentPosition()/1000;

                Log.d("timerTask", "run: pausePosition = " + pausePosition);
                Log.d("timerTask", "run: map.get(pausePosition) = " + map.get(pausePosition));

                String videoUrl = map.get(pausePosition);
                if(videoUrl != null) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            isAddsPlayed = true;
                            Exoplayer.setPlayWhenReady(false);
                        }

                    });
                }

            }


        }, 0, 1000);
        // count++;
    }


    private void initializeVideoPlayer(Info info) {

        player = findViewById(R.id.player);
        Log.d("tag123", "oninitilaizeplayer");


        try {

            Uri subtitleUri = Uri.parse(info.getSubtitle());

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            Exoplayer = ExoPlayerFactory.newSimpleInstance(VideoActivity.this, trackSelector);

            Uri videoUri = Uri.parse(info.getVideoLink());


            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exo_player");

            Format textFormat = Format.createTextSampleFormat(
                    null, // An identifier for the track. May be null.
                    MimeTypes.APPLICATION_SUBRIP, // The mime type. Must be set correctly.
                    C.SELECTION_FLAG_DEFAULT, // Selection flags for the track.
                    null);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            MediaSource subtitleSource = new SingleSampleMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(subtitleUri, textFormat, C.TIME_UNSET);

            source = new MergingMediaSource(videoSource, subtitleSource);
            Exoplayer.prepare(source);
            player.setPlayer(Exoplayer);
            player.setKeepScreenOn(true);


            Exoplayer.addListener(VideoActivity.this);


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer.purge();
        Log.d("tag123", "onpause  of activity   " + isAddsPlayed);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (SaveDataClass.getInstance().getSetIndexFrom().equals("feature")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("featurevideoarray");
                Log.d("resumeinfoArrayList", info.getVideoLink() + "");
                if (isAddsPlayed == true) {

                    timerTask(map);
                    // isAddsPlayed=false;
                } else if (isAddsPlayed == false) {
                    timerTask(map);
                }

            }
        } else if (SaveDataClass.getInstance().getSetIndexFrom().equals("trending")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("trendingvideodata");
                Log.d("resumeinfoArrayList", info.getVideoLink() + "");
                if (isAddsPlayed == true) {
                    timerTask(map);
                } else if (isAddsPlayed == false) {
                    timerTask(map);
                }

            }
        } else if (SaveDataClass.getInstance().getSetIndexFrom().equals("latest")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("latestdata");
                if (isAddsPlayed == true) {
                    timerTask(map);
                } else if (isAddsPlayed == false) {
                    timerTask(map);
                }

            }
        } else if (SaveDataClass.getInstance().getSetIndexFrom().equals("recent")) {
            if (getIntent().getExtras() != null) {
                Info info = getIntent().getExtras().getParcelable("recentdata");
                Log.d("resumeinfoArrayList", info.getVideoLink() + "");
                if (isAddsPlayed == true) {
                    timerTask(map);
                } else if (isAddsPlayed == false) {
                    timerTask(map);
                }

            }
        }


        Exoplayer.setPlayWhenReady(true);
        Log.d("tag123", "onresume of actibvty  " + isAddsPlayed);

    }

    public void setWatchhistory() {
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);


        Call<SetWatchHistoryResponse> call = italiaApi.setWatchHistory("7", "2");
        call.enqueue(new Callback<SetWatchHistoryResponse>() {
            @Override
            public void onResponse(Call<SetWatchHistoryResponse> call, Response<SetWatchHistoryResponse> response) {
                SetWatchHistoryResponse setWatchHistoryResponse = response.body();
                if (setWatchHistoryResponse.getStatus() == 1) {
                    Toast.makeText(VideoActivity.this, "Movie added in watch history", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SetWatchHistoryResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag123", "onstart");
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {


        if (playWhenReady && playbackState == Player.STATE_READY) {
            // media actually playing
        } else if (playWhenReady) {

        } else {

            if (isAddsPlayed == true) {

                Log.d("tag123", "inside playerststechanged: " + isAddsPlayed);

                String videoUrl = map.get(pausePosition);
                if(videoUrl != null) {
                    map.remove(pausePosition);

                    isAddsPlayed = true;
                    Exoplayer.setPlayWhenReady(false);

                    Intent intent = new Intent(VideoActivity.this, AdsActivity.class);
                    intent.putExtra("ADDURL",videoUrl);
                    startActivity(intent);

                }



            } else {
                Log.d("tag123", "inside playerststechanged: " + isAddsPlayed);
            }
        }
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
        Log.d("tag123", "timeline" + "onSeekProcessed" + Exoplayer.getCurrentPosition() + "");

        // onPause();
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
