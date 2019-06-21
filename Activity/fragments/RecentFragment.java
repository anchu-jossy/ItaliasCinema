package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.RecentAdapter;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecentFragment extends Fragment {


    @BindView(R.id.recent_recyclerview)
    RecyclerView recentRecyclerview;
    Unbinder unbinder;
    ArrayList<Info> infoArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getRecentMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        unbinder = ButterKnife.bind(this, view);
        recentRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));


        // getFeatureMovies();
        return view;
    }

    private void getRecentMovies() {


        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getRecentData("44", "recent");
        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {
                infoArrayList = new ArrayList<>();
                if (response.body().getStatus() == 1) {
                    if (infoArrayList != null) {
                        infoArrayList.clear();


                        for (int i = 0; i < response.body().getInfo().size(); i++) {

                            Info info = response.body().getInfo().get(i);
                            infoArrayList.add(info);
                            Log.d("Fetaure123", infoArrayList.get(i).getVideoLink() + "" + "");


                        }
                        RecentAdapter recentAdapter = new RecentAdapter(getContext(), infoArrayList);
                        recentRecyclerview.setAdapter(recentAdapter);

                        recentAdapter.notifyDataSetChanged();

                    }
                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
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
}
