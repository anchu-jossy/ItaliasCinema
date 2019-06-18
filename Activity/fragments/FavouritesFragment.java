package com.example.ajit.italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.activity.HomenavigationActivity;
import com.example.ajit.italiascinema.Activity.adapter.FavouritesAdapter;
import com.example.ajit.italiascinema.Activity.interfaces.CommonInterface;
import com.example.ajit.italiascinema.Activity.model.FeatureMoviesResponse;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesFragment extends Fragment {


    @BindView(R.id.fav_recyclerview)
    RecyclerView favRecyclerview;
    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;
    ArrayList<Info> infoArrayList = new ArrayList<>();
    CommonInterface commonInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFavouriteData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return view;
    }

    private void getFavouriteData() {

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getFavourites(SaveDataClass.getUserID(getContext()));

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {

                    infoArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        infoArrayList.add(featureMoviesResponse.getInfo().get(i));
                    }


                    FavouritesAdapter favouritesAdapter = new FavouritesAdapter(getContext(), infoArrayList);
                    favRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    favRecyclerview.setAdapter(favouritesAdapter);
                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {
        //commonInterface.backPressFavourite();

        ((HomenavigationActivity)getActivity()).onbackpressOfFavouritesActivity();
    }
}
