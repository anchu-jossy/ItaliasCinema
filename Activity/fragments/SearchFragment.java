package com.example.ajit.italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajit.italiascinema.Activity.adapter.SearchAdapter;
import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SearchFragment extends Fragment {


    @BindView(R.id.search_recyclerview)
    RecyclerView searchRecyclerview;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        unbinder = ButterKnife.bind(this, view);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchAdapter searchAdapter = new SearchAdapter(getContext());
        searchRecyclerview.setAdapter(searchAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
