package com.example.ajit.italiascinema.Activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.adapter.RecentAdapter;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        unbinder = ButterKnife.bind(this, view);
        recentRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));

        RecentAdapter recentAdapter = new RecentAdapter(getContext(), infoArrayList);
        recentRecyclerview.setAdapter(recentAdapter);

        recentAdapter.notifyDataSetChanged();
        getFeatureMovies();
        return view;
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
}
