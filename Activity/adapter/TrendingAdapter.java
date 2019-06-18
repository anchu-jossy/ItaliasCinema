package com.example.ajit.italiascinema.Activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ajit.italiascinema.Activity.fragments.TrendingFragment;
import com.example.ajit.italiascinema.Activity.fragments.TrendingVideoDetailsFragment;
import com.example.ajit.italiascinema.Activity.model.FeatureMoviesResponse;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {
    TrendingFragment trendingFragment;

    ArrayList<Info> arrayList;
    Context context;

    private LayoutInflater mInflater;

    public TrendingAdapter(Context context, TrendingFragment trendingFragment, ArrayList<Info> infoArrayList) {
        this.context = context;
        this.trendingFragment = trendingFragment;
        this.arrayList = infoArrayList;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trending_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {


       holder.rightTvDirector.setText(arrayList.get(i).getDirector());
        holder.rightTvComedy.setText(arrayList.get(i).getGenre());
        holder.rightTvStarcost.setText(arrayList.get(i).getStarCast());
        holder.tvFilmname.setText(arrayList.get(i).getMovieName());
        Glide.with(context).load(arrayList.get(i).getThumbnails()).into(holder.imageview);

        holder.trendingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                trendingFragment.trendingVideoOnclick(arrayList.get(i));
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageview)
        ImageView imageview;
        @BindView(R.id.trending_cardview)
        LinearLayout trendingCardview;
        @BindView(R.id.tv_filmname)
        TextView tvFilmname;
        @BindView(R.id.tv_starcost)
        TextView tvStarcost;
        @BindView(R.id.right_tv_starcost)
        TextView rightTvStarcost;
        @BindView(R.id.ll_starcast)
        LinearLayout llStarcast;
        @BindView(R.id.tv_director)
        TextView tvDirector;
        @BindView(R.id.right_tv_director)
        TextView rightTvDirector;
        @BindView(R.id.ll_directer)
        LinearLayout llDirecter;
        @BindView(R.id.tv_comedy)
        TextView tvComedy;
        @BindView(R.id.right_tv_comedy)
        TextView rightTvComedy;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}

// convenience method for getting data at click position

