package com.example.ajit.italiascinema.Activity.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
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

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.CenterZoomLayoutManager;
import com.example.ajit.italiascinema.Activity.adapter.FeatureAdapterForVideo;
import com.example.ajit.italiascinema.Activity.adapter.FeatureAdapterForWatchHistory;
import com.example.ajit.italiascinema.Activity.interfaces.DotsIndicatorDecoration;
import com.example.ajit.italiascinema.Activity.model.FeatureMovies;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.R;
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

    ArrayList<Info> infoArrayList;

    @BindView(R.id.video_recyclerview)
    RecyclerView videoRecyclerview;
    ArrayList<Bitmap> bitmapArrayList;


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
        infoArrayList = new ArrayList<>();

        getFeatureMovies();

        try {
            new DownloadImage("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4").execute();
            Log.d("Bitmap2", infoArrayList.get(0).getVideoLink() + "");
        } catch (Throwable throwable) {

            Log.d("Throwable", throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feature, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    private void setVideoAdapter(ArrayList<Bitmap> bitmapArrayList)

    {

        FeatureAdapterForVideo featureAdapterForVideo = new FeatureAdapterForVideo(getContext(), bitmapArrayList);
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

    private ArrayList<Info> getFeatureMovies() {
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMovies> call = italiaApi.getFeatureMovies();
        call.enqueue(new Callback<FeatureMovies>() {
            @Override
            public void onResponse(Call<FeatureMovies> call, Response<FeatureMovies> response) {


                if (infoArrayList != null) {
                    infoArrayList.clear();
                }


                for (int i = 0; i < response.body().getInfo().size(); i++) {
                    Info info = new Info();
                    info = response.body().getInfo().get(i);
                    infoArrayList.add(info);
                    Log.d("Fetaure123", infoArrayList.get(i).getVideoLink() + "" + "");
                    setImagePicker(infoArrayList);
                    // setSliderViews(infoArrayList);

                }


            }

            @Override
            public void onFailure(Call<FeatureMovies> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
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
    }
}
