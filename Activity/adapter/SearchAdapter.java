package com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
Info info;
    Context context;
    ArrayList<Info> infoArrayList = new ArrayList<>();

    public SearchAdapter(Context context, ArrayList<Info> infoArrayList) {
        this.context = context;
        this.infoArrayList = infoArrayList;

        Log.d("infoArraysearch",infoArrayList.size()+"");
    }
    public void updateList(ArrayList<Info> infoArrayList){
        this.infoArrayList = infoArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_search, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
      //  info=infoArrayList.get(i);
        Glide.with(context).load(infoArrayList.get(i).getThumbnails()).into(viewHolder.profileImage);
        viewHolder.tvMoviesName.setText(infoArrayList.get(i).getMovieName());
        viewHolder.tvActorsName.setText(infoArrayList.get(i).getStarCast());
    }


    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.profile_image)
        CircleImageView profileImage;
        @BindView(R.id.rll_profile_image)
        LinearLayout rllProfileImage;
        @BindView(R.id.tv_movies_name)
        TextView tvMoviesName;
        @BindView(R.id.tv_actors_name)
        TextView tvActorsName;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
