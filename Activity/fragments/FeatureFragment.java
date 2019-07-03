package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.CenterZoomLayoutManager;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.FeatureAdapterForVideo;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.FeatureAdapterForWatchHistory;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.interfaces.DotsIndicatorDecoration;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeatureFragment extends Fragment {


    @BindView(R.id.picker)
    DiscreteScrollView picker;

    Unbinder unbinder;

    @BindView(R.id.tv_watch_history)
    TextView tvWatchHistory;

    ArrayList<Info> infoArrayList = new ArrayList<>();
    ArrayList<Info> infoHistoryArrayList = new ArrayList<>();
    ArrayList<Info> allInfoArrayList = new ArrayList<>();
    @BindView(R.id.video_recyclerview)
    RecyclerView videoRecyclerview;
    ArrayList<Bitmap> bitmapArrayList;
    @BindView(R.id.progress_loader)
    ProgressBar progressLoader;


    public FeatureFragment() {
        // Required empty public constructor
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
Log.d("oncreate","jhihh");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feature, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.d("sizeoflist123","userid"+ SaveDataClass.getUserID(getContext()));
        progressLoader.setVisibility(View.GONE);
        getFeatureMovies();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        try {


            // new DownloadImage("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4").execute();

        } catch (Throwable throwable) {

            Log.d("Throwable", throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    private void setVideoAdapter(ArrayList<Info> infoArrayList)

    {

        FeatureAdapterForVideo featureAdapterForVideo = new FeatureAdapterForVideo(getContext(), infoArrayList);
        CenterZoomLayoutManager linearLayoutManager =
                new CenterZoomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        if (linearLayoutManager != null)

            videoRecyclerview.setLayoutManager(linearLayoutManager);
        videoRecyclerview.setAdapter(featureAdapterForVideo);

        videoRecyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });
        videoRecyclerview.setHasFixedSize(true);
        videoRecyclerview.addItemDecoration(new DotsIndicatorDecoration(10, 3 * 4, 100, Color.parseColor("#FFFFFF"), Color.parseColor("#8B0000")));
        featureAdapterForVideo.notifyDataSetChanged();
    }

    private void setImagePicker(ArrayList<Info> infoArrayList) {

        for (int i = 0; i < infoArrayList.size(); i++) {
            FeatureAdapterForWatchHistory featureAdapterForWatchHistory = new FeatureAdapterForWatchHistory(getContext(), infoArrayList);
            picker.setAdapter(featureAdapterForWatchHistory);
            picker.setFocusable(true);
            picker.setOffscreenItems(infoArrayList.size()); //Reserve extra space equal to (childSize * count) on each side of the view
            picker.setOverScrollEnabled(true);
            picker.setItemTransformer(new ScaleTransformer.Builder()
                    .setMaxScale(1.00f)
                    .setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                    .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                    .build());
        }
    }


    private void getFeatureMovies() {

        progressLoader.setVisibility(View.VISIBLE);

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getFeatureMovies(SaveDataClass.getUserID(getContext()), "feature");

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {

                progressLoader.setVisibility(View.GONE);
                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {

                    infoArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        infoArrayList.add(featureMoviesResponse.getInfo().get(i));
                        Log.d("sizeoflist123", SaveDataClass.getUserID(getContext()));
                    }
                    getWatchHistoryData();

                    setVideoAdapter(infoArrayList);


                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
                progressLoader.setVisibility(View.GONE);
            }
        });

    }

    private void getWatchHistoryData() {
        if (progressLoader != null)
            progressLoader.setVisibility(View.VISIBLE);
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getWatchHistory("64");

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {
                    if (progressLoader != null)
                        progressLoader.setVisibility(View.GONE);
                    infoHistoryArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        infoHistoryArrayList.add(featureMoviesResponse.getInfo().get(i));
                    }

                    if (infoHistoryArrayList.size() == 0) {
                        tvWatchHistory.setText("All Movies");
                        setImagePicker(getSearchData());
                    } else if (infoArrayList.size() != 0) {
                        tvWatchHistory.setText("Watch history");
                        setImagePicker(infoHistoryArrayList);
                    }

                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                if (progressLoader != null)
                    progressLoader.setVisibility(View.GONE);
                Log.d("Fetaure1", t.getMessage());
            }
        });

    }


    private ArrayList<Info> getSearchData() {

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getSearchData(SaveDataClass.getUserID(getContext()));

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {
                    if (progressLoader != null)
                        progressLoader.setVisibility(View.GONE);
                    allInfoArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        allInfoArrayList.add(featureMoviesResponse.getInfo().get(i));
                        Log.d("Alldataarray", allInfoArrayList.get(0).getThumbnails());
                    }


                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
                if (progressLoader != null)
                    progressLoader.setVisibility(View.GONE);
            }
        });
        return infoArrayList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

/*
    class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
/// method for creating thumpnail in android

        String videoUrl;

        public DownloadImage(String videoUrl) {

            this.videoUrl = videoUrl;
            Log.d("bipmap2", videoUrl + "");
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                bitmap = retriveVideoFrameFromVideo(videoUrl);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bitmapArrayList = new ArrayList<>();
            bitmapArrayList.add(bitmap);
            setVideoAdapter(bitmapArrayList);
            Log.d("bipmap2", bitmap + "");
            super.onPostExecute(bitmap);
        }
    }*/
}
