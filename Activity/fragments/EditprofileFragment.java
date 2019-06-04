package com.example.ajit.italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditprofileFragment extends Fragment {


    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Profile");


        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {

        getActivity().onBackPressed();
    }
}
