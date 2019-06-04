package com.example.ajit.italiascinema.Activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeatureAdapterForWatchHistory extends RecyclerView.Adapter<FeatureAdapterForWatchHistory.ViewHolder> {
    String imagepath;
    ArrayList<Info> infoArrayList;
    Context context;


    public FeatureAdapterForWatchHistory(Context context, ArrayList<Info> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_layout_for_watch_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Glide.with(context).load(infoArrayList.get(i).getThumbnails()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        @BindView(R.id.imageView)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);


            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
