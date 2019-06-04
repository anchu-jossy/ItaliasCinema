package com.example.ajit.italiascinema.Activity.fragments;

import android.content.Context;
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

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.adapter.TrendingAdapter;
import com.example.ajit.italiascinema.Activity.interfaces.CommonInterface;
import com.example.ajit.italiascinema.Activity.model.FeatureMovies;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrendingFragment extends Fragment implements CommonInterface {


    ArrayList<Info> infoArrayList;

    Unbinder unbinder;
    TrendingAdapter trending1Adapter;
    @BindView(R.id.trending_recyclerview)
    RecyclerView trendingRecyclerview;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        infoArrayList = new ArrayList<>();
        getFeatureMovies();
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
                    Log.d("Fetaure5", infoArrayList.get(i).getVideoLink() + "" + "");


                }

                trendingRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                trending1Adapter = new TrendingAdapter(getContext(), TrendingFragment.this, infoArrayList);
                trendingRecyclerview.setAdapter(trending1Adapter);
            }

            @Override
            public void onFailure(Call<FeatureMovies> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });
        return infoArrayList;

    }


    @Override
    public void trendingVideoOnclick() {

        TrendingVideoDetailsFragment trendingVideoDetailsFragment = new TrendingVideoDetailsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("trending fragment");
        fragmentTransaction.replace(R.id.container, trendingVideoDetailsFragment);
        fragmentTransaction.commit();
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
