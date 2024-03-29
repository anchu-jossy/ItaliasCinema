package com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
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
import android.widget.Toast;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.VideoActivity;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.bumptech.glide.Glide;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.LoginActivity;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;

import com.ItaliasCinemas.ajit.Italiascinema.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder> {

    Context context;
    ArrayList<Info> infoArrayList;

    ProgressDialog mProgressDialog;
    SaveDataClass saveDataClass;
    Info info;

    public LatestAdapter(Context context, ArrayList<Info> infoArrayList) {
        this.context = context;
        this.infoArrayList = infoArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latest_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.imageViewThumpnail.setVisibility(View.VISIBLE);

        Glide.with(context).load(infoArrayList.get(i).getThumbnails()).into(viewHolder.imageViewThumpnail);
        viewHolder.imageViewForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = infoArrayList.get(i).getVideoLink();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                context.startActivity(Intent.createChooser(share, "Share"));
            }
        });
        viewHolder.movieName.setText(infoArrayList.get(i).getMovieName());
        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*   viewHolder.btnPlay.setVisibility(View.GONE);*/
                //  viewHolder.videoview.setVisibility(View.VISIBLE);
                //    viewHolder.imageViewThumpnail.setVisibility(View.GONE);
                SaveDataClass.getInstance().setSetIndexFrom("latest");
                Intent intent = new Intent(context, VideoActivity.class);
                info = infoArrayList.get(i);
                intent.putExtra("latestdata", info);
                context.startActivity(intent);
                //  setVideo(viewHolder);
            }
        });
      /*  viewHolder.btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.btnPlay.setVisibility(View.VISIBLE);
            //    viewHolder.btnPause.setVisibility(View.GONE);

                viewHolder.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.pause();
                    }
                });
            }
        });*/
        viewHolder.imageViewArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataClass.getInstance().setSetIndexForDownloading("Latest");

                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("LatestVideodata", infoArrayList.get(i));

                Log.d("latestdata", infoArrayList.get(i).getVideoLink());
                context.startActivity(intent);

                /*if (!SaveDataClass.getInstance().getUsername().equals("") && !SaveDataClass.getInstance().getPassword().equals("")) {

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

                            final LatestAdapter.DownloadTask downloadTask = new LatestAdapter.DownloadTask(context);
                            downloadTask.execute("http://www.storiesinflight.com/js_videosub/jellies.mp4");

                            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    downloadTask.cancel(true); //cancel the task
                                }
                            });


                        } else {

                            Log.v("permission", "Permission is revoked");
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                            mProgressDialog = new ProgressDialog(context);
                            mProgressDialog.setMessage("Downloading Movie File");
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
                            final LatestAdapter.DownloadTask downloadTask = new LatestAdapter.DownloadTask(context);
                            downloadTask.execute("http://www.storiesinflight.com/js_videosub/jellies.mp4");

                            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    downloadTask.cancel(true); //cancel the task
                                }
                            });

                        }
                    } else { //permission is automatically granted on sdk<23 upon installation
                        Log.v("permission", "Permission is granted");

                    }

                } else {


                }*/


            }
        });

    }

//    private void setVideo(ViewHolder view) {
//
//
//        MediaController mediacontroller = new MediaController(context);
//        mediacontroller.setAnchorView(view.videoview);
//        Uri uri1 = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
//        view.videoview.setMediaController(mediacontroller);
//
//
//        view.videoview.setVideoURI(uri1);
//        view.videoview.requestFocus();
//        view.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            public void onPrepared(MediaPlayer mp) {
//                view.videoview.start();
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
// create a File object for the parent directory
                File dir = new File(Environment.getExternalStorageDirectory() + "/ItaliasCinema/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
// have the object build the directory structure, if needed.

                final File file = new File(Environment.getExternalStorageDirectory(), "/ItaliasCinema/jellies.mp4");


                if (!file.exists()) {
                    // file does not exist, create it
                    file.createNewFile();
                }


                input = connection.getInputStream();
                output = new FileOutputStream(file);
                Log.d("Downloaderror", file.getAbsolutePath() + "");
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        /*@BindView(R.id.videoview)
        VideoView videoview;*/
        @BindView(R.id.imageView_forward)
        ImageButton imageViewForward;
        @BindView(R.id.imageView_arrow_down)
        ImageButton imageViewArrowDown;
        @BindView(R.id.youtubePlayerViewer)
        FrameLayout youtubePlayerViewer;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.btn_play)
        ImageButton btnPlay;
        @BindView(R.id.imageView_thumpnail)
        ImageView imageViewThumpnail;

        /* @BindView(R.id.btn_pause)
         ImageButton btnPause;
 */
        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
