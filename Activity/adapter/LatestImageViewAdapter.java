package com.example.ajit.italiascinema.Activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ajit.italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LatestImageViewAdapter extends RecyclerView.Adapter<LatestImageViewAdapter.ViewHolder> {
    ArrayList<Bitmap> bitmapArrayList;
   Context context;
    public LatestImageViewAdapter(Context context, ArrayList<Bitmap> bitmapArrayList) {
        this.bitmapArrayList = bitmapArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.latest_imageview_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("bipmapdata",bitmapArrayList.get(0).toString());
        Glide.with(context).load(bitmapArrayList.get(0)).into(viewHolder.profileImage);
    }

    @Override
    public int getItemCount() {
        return bitmapArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        @BindView(R.id.profile_image)
        CircleImageView profileImage;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
