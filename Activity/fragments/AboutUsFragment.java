package com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.HomenavigationActivity;
import com.ItaliasCinemas.ajit.Italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutUsFragment extends Fragment {

    @BindView(R.id.about_us_toolbar)
    Toolbar aboutUsToolbar;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.rl_heading)
    RelativeLayout rlHeading;
    @BindView(R.id.rl_body)
    RelativeLayout rlBody;
    Unbinder unbinder;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.activity_about_us, container, false);

        ButterKnife.bind(this, view);

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {
        ((HomenavigationActivity)getActivity()).onbackpressOfFavouritesActivity();

    }
}
