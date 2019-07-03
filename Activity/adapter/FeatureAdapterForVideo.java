package com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.LoginActivity;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.VideoActivity;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Add;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeatureAdapterForVideo extends RecyclerView.Adapter<FeatureAdapterForVideo.ViewHolder> {
    Context context;
    ArrayList<Info> infoarraylist;

    ProgressDialog mProgressDialog;
    SaveDataClass saveDataClass;
    HashMap<String, ArrayList<Add>> map = new HashMap<>();

    public FeatureAdapterForVideo(Context context, ArrayList<Info> infoarraylist) {
        this.infoarraylist = infoarraylist;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_layout_for_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(infoarraylist.get(i).getThumbnails()).into(viewHolder.imageViewThumpnail);
        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
               // map.put(infoarraylist.get(i).getVideoLink(), (ArrayList<Add>) infoarraylist.get(0).getAdds());
                intent.putExtra("featurevideoarray", infoarraylist.get(i));
                //Log.d("positionof", map.size() + "  " + map.keySet());
                SaveDataClass.getInstance().setSetIndexFrom("feature");
                context.startActivity(intent);


              /*  viewHolder.imageViewThumpnail.setVisibility(View.GONE);
                viewHolder.videoview.setVisibility(View.VISIBLE);
                setVideo(viewHolder);*/
            }
        });
        viewHolder.imageViewArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStoragePermissionGranted(infoarraylist.get(i));

            }
        });
        viewHolder.imageViewForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = infoarraylist.get(i).getVideoLink();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                context.startActivity(Intent.createChooser(share, "Share"));
            }
        });
    }

    public boolean isStoragePermissionGranted(Info info) {
        SaveDataClass.getInstance().setSetIndexForDownloading("Feature");
/*   String username = saveDataClass.getInstance().getUsername();
        String password = saveDataClass.getInstance().getPassword();*/
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("FeatureData", info);
        SaveDataClass.getInstance().setInfo(info);
        context.startActivity(intent);

       /* if (!username.equals("") && !password.equals("")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v("permission", "Permission is granted");
                    // instantiate it within the onCreate method
                    mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setMessage("Downloading Movie File");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired

                    final DownloadTask downloadTask = new DownloadTask(context);
                    downloadTask.execute("http://www.storiesinflight.com/js_videosub/jellies.mp4");

                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            downloadTask.cancel(true); //cancel the task
                        }
                    });

                    return true;
                } else {

                    Log.v("permission", "Permission is revoked");
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setMessage("Downloading Movie File");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
                    final DownloadTask downloadTask = new DownloadTask(context);
                    downloadTask.execute("http://www.storiesinflight.com/js_videosub/jellies.mp4");

                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            downloadTask.cancel(true); //cancel the task
                        }
                    });
                    return false;
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("permission", "Permission is granted");
                return true;
            }

        } else {*/


        return false;
    }


    @Override
    public int getItemCount() {
        return infoarraylist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        @BindView(R.id.imageView_thumpnail)
        ImageView imageViewThumpnail;
        @BindView(R.id.imageView_forward)
        ImageButton imageViewForward;
        @BindView(R.id.imageView_arrow_down)
        ImageButton imageViewArrowDown;
        @BindView(R.id.btn_play)
        ImageButton btnPlay;
        @BindView(R.id.youtubePlayerViewer)
        FrameLayout youtubePlayerViewer;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
