package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TendingDetailsActivity extends AppCompatActivity {


    Info info;
    int count;

    @BindView(R.id.imageView_thumpnail)
    ImageView imageViewThumpnail;
    @BindView(R.id.btn_downloads)
    Button btnDownloads;
    @BindView(R.id.ll_download)
    LinearLayout llDownload;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.btn_favourites)
    Button btnFavourites;
    @BindView(R.id.ll_favourites)
    LinearLayout llFavourites;
    @BindView(R.id.ll_video_function)
    LinearLayout llVideoFunction;
    @BindView(R.id.btn_play)
    ImageButton btnPlay;
    @BindView(R.id.youtubePlayerViewer)
    FrameLayout youtubePlayerViewer;
    @BindView(R.id.tv_filmname)
    TextView tvFilmname;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_starcost)
    TextView tvStarcost;
    @BindView(R.id.right_tv_starcost)
    TextView rightTvStarcost;
    @BindView(R.id.ll_starcast)
    LinearLayout llStarcast;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.right_tv_director)
    TextView rightTvDirector;
    @BindView(R.id.ll_directer)
    LinearLayout llDirecter;
    @BindView(R.id.tv_comedy)
    TextView tvComedy;
    @BindView(R.id.right_tv_comedy)
    TextView rightTvComedy;
    @BindView(R.id.ll_genre)
    LinearLayout llGenre;
    @BindView(R.id.tv_released_date)
    TextView tvReleasedDate;
    @BindView(R.id.right_tv_released_date)
    TextView rightTvReleasedDate;
    @BindView(R.id.ll_released_date)
    LinearLayout llReleasedDate;
    @BindView(R.id.tv_time_duration)
    TextView tvTimeDuration;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.viewline2)
    View viewline2;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_details_data)
    TextView tvDetailsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tending_detailsfragment);
        ButterKnife.bind(this);
        info = getIntent().getExtras().getParcelable("trendingdata");
        Log.d("tendingdetails", "onCreate: " + info.getThumbnails());
        setData(info);
    }

    private void setData(Info info) {
        tvFilmname.setText(info.getMovieName());
        rightTvStarcost.setText(info.getStarCast());
        rightTvDirector.setText(info.getDirector());
        rightTvComedy.setText(info.getGenre());
        rightTvReleasedDate.setText(info.getDate() + "");
        tvDetailsData.setText(info.getDetails());

        tvTimeDuration.setText(info.getDurationTime());
        ratingBar.setNumStars(Integer.valueOf(info.getRating()));

        Glide.with(TendingDetailsActivity.this).load("http://redex.info/italian_movie/thumbnails/download.jpeg").into(imageViewThumpnail);
    }

    public boolean isStoragePermissionGranted(Info info) {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("TrendingVideoData", info);
        Log.d("Fetaure1", info.getVideoLink());
        SaveDataClass.getInstance().setSetIndexForDownloading("Trending");

        startActivity(intent);

        //  }

        return false;
    }


    private void setFavouriteApi(Info info) {


        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.setFavourites(SaveDataClass.getUserID(TendingDetailsActivity.this), info.getMovieId());

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {
                    Toast.makeText(TendingDetailsActivity.this, "Added as Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    /*  Toast.makeText(getActivity(),response.body().get, Toast.LENGTH_SHORT).show();*/
                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });

    }

    @OnClick({R.id.btn_downloads, R.id.btn_share, R.id.btn_favourites, R.id.btn_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_downloads:
                isStoragePermissionGranted(info);
                break;
            case R.id.btn_share:
                String message = info.getVideoLink();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share"));
                break;
            case R.id.btn_favourites:
                count++;
                if (count % 2 == 0) {
                    Drawable top = getResources().getDrawable(R.drawable.grey_favorite);
                    btnFavourites.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                } else {
                    Drawable top = getResources().getDrawable(R.drawable.res_favourites);
                    btnFavourites.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    setFavouriteApi(info);
                }
                break;
            case R.id.btn_play:
                SaveDataClass.getInstance().setSetIndexFrom("trending");
                Intent intent = new Intent(TendingDetailsActivity.this, VideoActivity.class);
                intent.putExtra("trendingvideodata", info);
                startActivity(intent);
                break;
        }
    }

}
