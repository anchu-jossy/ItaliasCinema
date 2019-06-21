package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ItaliasCinemas.ajit.Italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdsActivity extends AppCompatActivity {

    @BindView(R.id.videoview)
    VideoView videoview;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        String addurl=getIntent().getStringExtra("ADDURL");
        Log.d("tag123", addurl);
        mediaController = new MediaController(AdsActivity.this);
        mediaController.setAnchorView(videoview);
        Uri uri1 = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        videoview.setMediaController(null);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoview.setVideoURI(uri1);
        videoview.requestFocus();
        videoview.start();
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("tag123", "finish in adsactivity");

                // setResult(RESULT_CANCELED);
                finish();

                // finish();
            }
        });

    }

}
