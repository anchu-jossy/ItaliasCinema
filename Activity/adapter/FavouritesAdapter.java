package com.example.ajit.italiascinema.Activity.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    Context context;
    ArrayList<Info> infoArrayList;


    public FavouritesAdapter(Context context, ArrayList<Info> infoArrayList) {
        this.context = context;
        this.infoArrayList = infoArrayList;

        Log.d("favouritearray",infoArrayList.size()+"");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_favourites, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(infoArrayList.get(i).getThumbnails()).into(viewHolder.imageView);
        viewHolder.movieName.setText(infoArrayList.get(i).getMovieName());

    }


    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.movie_name)
        TextView movieName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        @Override
        public void onClick(View v) {

        }
    }
}
