package com.example.ajit.italiascinema.Activity.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.adapter.LatestAdapter;
import com.example.ajit.italiascinema.Activity.adapter.LatestImageViewAdapter;
import com.example.ajit.italiascinema.Activity.model.FeatureMovies;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestFragment extends Fragment {


    @BindView(R.id.latest_recyclerview)
    RecyclerView latestRecyclerview;
    Unbinder unbinder;
    LatestAdapter latestAdapter;
    @BindView(R.id.latest_horizontal_recyclerview)
    RecyclerView latestHorizontalRecyclerview;
    LatestImageViewAdapter latestImageViewAdapter;
    ArrayList<Info> infoArrayList;
    ArrayList<Bitmap> bipmapArrayList;

    public static Fragment newInstance(String s, String s1) {
        LatestFragment fragment = new LatestFragment();

        return fragment;
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
            //   mediaMetadataRetriever.setDataSource(videoPath);
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
        getFeatureMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        unbinder = ButterKnife.bind(this, view);



        return view;
    }

    private void setAdapter(ArrayList<Info> infoArrayList, ArrayList<Bitmap> bipmapArrayList) {

        if(latestRecyclerview!=null){
            latestRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            latestAdapter = new LatestAdapter(getContext(), infoArrayList, bipmapArrayList);
            latestRecyclerview.setAdapter(latestAdapter);
        }

    }

    private void getFeatureMovies() {
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMovies> call = italiaApi.getFeatureMovies();
        call.enqueue(new Callback<FeatureMovies>() {
            @Override
            public void onResponse(Call<FeatureMovies> call, Response<FeatureMovies> response) {
                infoArrayList = new ArrayList<>();

                if (infoArrayList != null) {
                    infoArrayList.clear();
                }


                for (int i = 0; i < response.body().getInfo().size(); i++) {
                    Info info = new Info();
                    info = response.body().getInfo().get(i);
                    infoArrayList.add(info);
                    Log.d("Fetaure123", infoArrayList.get(i).getVideoLink() + "" + "");


                }

                try {
                    new DownloadImage("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4").execute();


                    Log.d("Bitmap2", infoArrayList.get(0).getVideoLink() + "");
                } catch (Throwable throwable) {

                    Log.d("Throwable", throwable.getMessage());
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FeatureMovies> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });

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

        ImageView imageView;
        String videoUrl;

        public DownloadImage(String videoUrl) {

            this.videoUrl = videoUrl;

        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                bitmap = retriveVideoFrameFromVideo(videoUrl);
                Log.d("bipmap21", bitmap + "");
                ;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bipmapArrayList = new ArrayList<>();
            bipmapArrayList.add(bitmap);
            Log.d("bipmap2", bitmap + "");
            if(latestHorizontalRecyclerview!=null) {
                latestHorizontalRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                latestImageViewAdapter = new LatestImageViewAdapter(getContext(), bipmapArrayList);

                latestHorizontalRecyclerview.setAdapter(latestImageViewAdapter);
            }
                setAdapter(infoArrayList, bipmapArrayList);


            super.onPostExecute(bitmap);
        }
    }
}
