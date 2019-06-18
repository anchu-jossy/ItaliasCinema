package com.example.ajit.italiascinema.Activity.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context context;
    ArrayList<Info> infoArrayList;


    public NotificationsAdapter(Context context, ArrayList<Info> infoArrayList) {
        this.context = context;
        this.infoArrayList = infoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notifications, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(infoArrayList.get(i).getThumbnails()).into(viewHolder.movieImage);
        viewHolder.tvMovieName.setText(infoArrayList.get(i).getMovieName());
       viewHolder.tvStatus.setText(infoArrayList.get(i).getReleaseStatus());
        viewHolder.tvTime.setText(infoArrayList.get(i).getDurationTime());
    }


    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_image)
        CircleImageView movieImage;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        @Override
        public void onClick(View v) {

        }
    }
}
