package com.example.ajit.italiascinema.Activity.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.ajit.italiascinema.Activity.Api.ItaliaApi;
import com.example.ajit.italiascinema.Activity.Api.RetrofitClientInstance;
import com.example.ajit.italiascinema.Activity.activity.LoginActivity;
import com.example.ajit.italiascinema.Activity.activity.VideoActivity;
import com.example.ajit.italiascinema.Activity.model.FeatureMoviesResponse;
import com.example.ajit.italiascinema.Activity.model.Info;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingVideoDetailsFragment extends Fragment {

    ProgressDialog mProgressDialog;

    int count;
    @BindView(R.id.tv_favourites)
    TextView tvFavourites;
    Unbinder unbinder;
    SaveDataClass saveDataClass;
    @BindView(R.id.videoview)
    VideoView videoview;
    @BindView(R.id.imageView_thumpnail)
    ImageView imageViewThumpnail;
    @BindView(R.id.ll_download)
    LinearLayout llDownload;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.ll_favourites)
    LinearLayout llFavourites;
    @BindView(R.id.ll_video_function)
    LinearLayout llVideoFunction;
    @BindView(R.id.btn_play)
    ImageButton btnPlay;
    @BindView(R.id.youtubePlayerViewer)
    FrameLayout youtubePlayerViewer;
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
    @BindView(R.id.ll_genre)
    LinearLayout llGenre;
    @BindView(R.id.tv_released_date)
    TextView tvReleasedDate;
    @BindView(R.id.right_tv_released_date)
    TextView rightTvReleasedDate;
    @BindView(R.id.ll_released_date)
    LinearLayout llReleasedDate;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.viewline2)
    View viewline2;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_details_data)
    TextView tvDetailsData;
    Info info;
    @BindView(R.id.tv_time_duration)
    TextView tvTimeDuration;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setData(Info info) {
        tvFilmname.setText(info.getMovieName());
        rightTvStarcost.setText(info.getStarCast());
        rightTvDirector.setText(info.getDirector());
        rightTvComedy.setText(info.getGenre());
        rightTvReleasedDate.setText(info.getDate());
        tvDetailsData.setText(info.getDetails());
        tvTimeDuration.setText("TimeDuration is   " + info.getDurationTime());
        ratingBar.setNumStars(Integer.parseInt(info.getRating()));
        Glide.with(getContext()).load(info.getThumbnails()).into(imageViewThumpnail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_treending_video_details, container, false);

        unbinder = ButterKnife.bind(this, view);
        Bundle args = getArguments();


        if (args != null) {
            info = args.getParcelable("trendingdata");
            Log.w("infodata", info.getDirector());
            setData(info);

        } else {
            Log.w("GetIncidencia", "Arguments expected, but missing");
        }
        return view;
    }

    @OnClick({R.id.ll_download, R.id.ll_share, R.id.ll_favourites, R.id.btn_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_download:
                isStoragePermissionGranted(info);
                break;
            case R.id.ll_share:
                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share"));
                break;
            case R.id.ll_favourites:
                count++;
                if (count % 2 == 0) {
                    Drawable top = getResources().getDrawable(R.drawable.grey_favorite);
                    tvFavourites.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                } else {
                    Drawable top = getResources().getDrawable(R.drawable.res_favourites);
                    tvFavourites.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    setFavouriteApi();
                }
                break;
            case R.id.btn_play:
                SaveDataClass.getInstance().setSetIndexFrom("trending");
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("trendingvideodata", info);
                startActivity(intent);
                break;
        }
    }

    private void setFavouriteApi() {


        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.setFavourites(SaveDataClass.getUserID(getContext()), info.getMovieId());

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {
                    Toast.makeText(getActivity(), "Added as Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    /*  Toast.makeText(getActivity(),response.body().get, Toast.LENGTH_SHORT).show();*/
                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("Fetaure1", t.getMessage());
            }
        });

    }

    public boolean isStoragePermissionGranted(Info info) {

        //String username = SaveDataClass.getInstance().getUsername();
        //   String password = saveDataClass.getInstance().getPassword();
    /*    if (!SaveDataClass.getInstance().getUsername().equals("") && !SaveDataClass.getInstance().getPassword().equals("")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v("permission", "Permission is granted");
                    // instantiate it within the onCreate method
                    mProgressDialog = new ProgressDialog(getContext());
                    mProgressDialog.setMessage("Downloading Movie File");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired

                    DownloadTask downloadTask = new DownloadTask(getContext());
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
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    mProgressDialog = new ProgressDialog(getContext());
                    mProgressDialog.setMessage("Downloading Movie File");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
                    DownloadTask downloadTask = new DownloadTask(getContext());
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
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("TrendingVideoData",info);

        SaveDataClass.getInstance().setSetIndexForDownloading("Trending");

        startActivity(intent);

        //  }

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

}
