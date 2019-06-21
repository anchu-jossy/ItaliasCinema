package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.TrendingAdapter;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.TendingDetailsActivity;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.interfaces.CommonInterface;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;

import com.ItaliasCinemas.ajit.Italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrendingFragment extends Fragment implements CommonInterface {


    ArrayList<Info> infoArrayList = new ArrayList<>();

    Unbinder unbinder;
    TrendingAdapter trending1Adapter;
    @BindView(R.id.trending_recyclerview)
    RecyclerView trendingRecyclerview;
    Bundle args;
    @BindView(R.id.progress_loader)
    ProgressBar progressLoader;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = new Bundle();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();

        getTrendingMovies();

    }

    private void getTrendingMovies() {
        if(progressLoader!=null)
        progressLoader.setVisibility(View.VISIBLE);
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getTrendingData(SaveDataClass.getUserID(getContext()), "trending");
        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {
                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {
                    if(progressLoader!=null)
                    progressLoader.setVisibility(View.GONE);
                    infoArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        infoArrayList.add(featureMoviesResponse.getInfo().get(i));
                    }

                    trendingRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    trending1Adapter = new TrendingAdapter(getContext(), TrendingFragment.this, infoArrayList);
                    trendingRecyclerview.setAdapter(trending1Adapter);
                }
            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                if(progressLoader!=null)
                progressLoader.setVisibility(View.GONE);
                Log.d("trendingfailure", t.getMessage());
            }
        });
       /* call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {
                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus()==1) {


                if (infoArrayList != null) {
                    infoArrayList.clear();
                }


                for (int i = 0; i < response.body().getInfo().size(); i++) {
                    Info info = new Info();

                    infoArrayList.add(info);
                    Log.d("Fetaure5", infoArrayList.get(i).getVideoLink() + "" + "");


                }

                trendingRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                trending1Adapter = new TrendingAdapter(getContext(), TrendingFragment.this, infoArrayList);
                trendingRecyclerview.setAdapter(trending1Adapter);
            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });
        return infoArrayList;

    }*/

    }

    @Override
    public void trendingVideoOnclick(Info info) {

        TrendingVideoDetailsFragment trendingVideoDetailsFragment = new TrendingVideoDetailsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("trending fragment");

     /*   args.putParcelable("trendingdata",info);
        trendingVideoDetailsFragment.setArguments(args);
        fragmentTransaction.replace(R.id.container, trendingVideoDetailsFragment);
        fragmentTransaction.commit();*/

        //
        Intent intent = new Intent(getContext(), TendingDetailsActivity.class);
        intent.putExtra("trendingdata", info);
        startActivity(intent);
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


    }


}
