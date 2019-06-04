package com.example.ajit.italiascinema.Activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ajit.italiascinema.Activity.adapter.FavouritesAdapter;
import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavouritesFragment extends Fragment {


    @BindView(R.id.fav_recyclerview)
    RecyclerView favRecyclerview;
    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        FavouritesAdapter favouritesAdapter = new FavouritesAdapter(getContext());
        favRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        favRecyclerview.setAdapter(favouritesAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {

      getActivity().onBackPressed();

    }
}
