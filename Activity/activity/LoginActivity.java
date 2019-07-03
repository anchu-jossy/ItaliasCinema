package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.ChangePasswordFragment;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.interfaces.CommonInterface;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.LoginResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.Purchase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements CommonInterface {

    @BindView(R.id.Login)
    TextView Login;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.img_app)
    ImageView imgApp;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_emailid)
    EditText edtEmailid;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.rl_reg)
    RelativeLayout rlReg;
    @BindView(R.id.login_container)
    RelativeLayout loginContainer;
    String username;
    String password;
    String username1;
    String password1;
    String emailid;
    ProgressDialog mProgressDialog;
    Info info;
    HashMap<String, String> Stringhashmap = new HashMap<>();
    Inventory mInventory;
    String TAG = "PAYMENTACTIVITY";
    private ActivityCheckout mCheckout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUnderLine();
        edtUsername.setText("ttt");
        edtPassword.setText("123");
        if (!SaveDataClass.getUserPassword(LoginActivity.this).equals(null) && (!SaveDataClass.getUserPassword(LoginActivity.this).equals(null))) {
            username1 = SaveDataClass.getUserName(LoginActivity.this);
            password1 = SaveDataClass.getUserPassword(LoginActivity.this);
            edtUsername.setText(username1);
            edtPassword.setText(password1);

            final Billing billing = MyApplication.get(LoginActivity.this).getBilling();
            ;
            mCheckout = Checkout.forActivity(this, billing);
            mCheckout.start();

            mCheckout.createPurchaseFlow(new PurchaseListener());


        }


    }

    private void setUnderLine() {
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


    private void doneButtonClick() {

        username = SaveDataClass.getUserName(LoginActivity.this);
        password = SaveDataClass.getUserPassword(LoginActivity.this);

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);


        Call<LoginResponse> call = italiaApi.login("ttt", "123");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();


                if (loginResponse.getStatus() == 1) {


                    if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Feature")) {
                        info = getIntent().getExtras().getParcelable("FeatureData");
                        Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                        Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                        downloadingVideo(info);
                    }
                   else if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Trending")) {
                            info = getIntent().getExtras().getParcelable("TrendingVideoData");
                            Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                            Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                            downloadingVideo(info);
                        } else if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Latest")) {
                            info = getIntent().getExtras().getParcelable("LatestVideodata");
                            Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                            Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                            downloadingVideo(info);
                        } else if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Recent")) {
                            info = getIntent().getExtras().getParcelable("RecentVideodata");
                            Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                            Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                            downloadingVideo(info);
                        }

                    /*mCheckout.whenReady(new Checkout.EmptyListener() {
                        @Override
                        public void onReady(BillingRequests requests) {
                            requests.purchase(ProductTypes.IN_APP, "italias_123", null, mCheckout.getPurchaseFlow());
                        }
                    });*/
                    }

                } /*else
                    mCheckout.whenReady(new Checkout.EmptyListener() {
                        @Override
                        public void onReady(BillingRequests requests) {
                            requests.purchase(ProductTypes.IN_APP, "italias_123", SaveDataClass.getUserID(LoginActivity.this), mCheckout.getPurchaseFlow());
                        }
                    });*/
                //  startActivity(new Intent(LoginActivity.this, HomenavigationActivity.class));





            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Response2", t.getMessage());

                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckout.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, data.getExtras() + "", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onActivityResult: " + data.getExtras().toString());

    }

    private boolean downloadingVideo(Info info) {

        Log.d("getvideolink1", info.getVideoLink());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission", "Permission is granted");
                // instantiate it within the onCreate method
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setMessage("Downloading Movie File");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);

                Log.v("getvideolink12", info.getVideoLink());
                final DownloadTask downloadTask = new DownloadTask(LoginActivity.this);
                downloadTask.execute(info.getVideoLink());


                //   downloadTask.execute("http://www.storiesinflight.com/js_videosub/jellies.mp4");
                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true); //cancel the task
                    }
                });

                return true;
            } else {

                Log.v("permission", "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setMessage("Downloading Movie File");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
                final DownloadTask downloadTask = new DownloadTask(LoginActivity.this);
                downloadTask.execute(info.getVideoLink());

                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true); //cancel the task
                    }
                });
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission", "Permission is  automatically granted on sdk<23 upon installation");

            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Downloading Movie File");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);

            Log.v("getvideolink12", info.getVideoLink());
            final DownloadTask downloadTask = new DownloadTask(LoginActivity.this);
            downloadTask.execute(info.getVideoLink());


            //   downloadTask.execute("http://www.storiesinflight.com/js_videosub/jellies.mp4");
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true); //cancel the task
                }
            });
            return true;
        }


    }

    private void forgotPasswordClick() {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_container, changePasswordFragment).commit();

    }

    @OnClick({R.id.tv_forgot_password, R.id.btn_done, R.id.tv_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_password:
                forgotPasswordClick();
                break;
            case R.id.btn_done:
                doneButtonClick();
                break;
            case R.id.tv_sign_up:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
        }
    }

    private void setAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyDialogTheme);
        builder.setMessage("Do you really want to continue downloading?");
        AlertDialog dialog = builder.create();
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LoginActivity.this, HomenavigationActivity.class));
            }
        }).show();

    }

    /*private class DownloadTask extends AsyncTask<String, Integer, String> {

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

                final File file = new File(Environment.getExternalStorageDirectory(), "/ItaliasCinema/videos.mp4");


                if (!file.exists()) {
                    // file does not exist, create it
                    file.createNewFile();
                }


                input = connection.getInputStream();

                output = new FileOutputStream(file);
                if(output!=null)
                    output.flush();

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
                    Log.d("Downloaderror", fileLength + "");
                }
            } catch (Exception e) {


                Log.d("Exception1",e+"");
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        Log.d("output12",output+"");

                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {

                    Log.d("Exception2",ignored+"");
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
            startActivity(new Intent(LoginActivity.this,HomenavigationActivity.class));


        }

    }*/

    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            // here you can process the loaded purchase
            Log.d(TAG, "purchase success");


            if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Feature")) {
                info = getIntent().getExtras().getParcelable("FeatureData");
                Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                downloadingVideo(info);

            } else if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Trending")) {
                info = getIntent().getExtras().getParcelable("TrendingVideoData");
                Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                downloadingVideo(info);
            } else if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Latest")) {
                info = getIntent().getExtras().getParcelable("LatestVideodata");
                Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                downloadingVideo(info);
            } else if (SaveDataClass.getInstance().getSetIndexForDownloading().equals("Recent")) {
                info = getIntent().getExtras().getParcelable("RecentVideodata");
                Stringhashmap.put(info.getVideoLink(), info.getSubtitle());
                Log.d("LoginActivityurl", Stringhashmap.keySet() + "");
                downloadingVideo(info);
            }


            //
            Toast.makeText(LoginActivity.this, purchase.orderId + purchase.data + "", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onSuccess: " + purchase.data);
        }

        @Override
        public void onError(int response, Exception e) {
            // handle errors here
            Log.d(TAG, "purchase exception:-" + e + "");
            Toast.makeText(LoginActivity.this, "purchase exception:-" + e + "", Toast.LENGTH_SHORT).show();
        }
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

                final File file = new File(Environment.getExternalStorageDirectory(), "/ItaliasCinema/" + info.getMovieName() + ".mp4");


                if (!file.exists()) {
                    // file does not exist, create it
                    file.createNewFile();
                }


                input = connection.getInputStream();

                output = new FileOutputStream(file);
                if (output != null)
                    output.flush();

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
                    Log.d("Downloaderror", fileLength + "");
                }
            } catch (Exception e) {


                Log.d("Exception1", e + "");
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        Log.d("output12", output + "");

                    output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {

                    Log.d("Exception2", ignored + "");
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
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                DownloadSrtTask downloadSrtTask = new DownloadSrtTask(LoginActivity.this);
                downloadSrtTask.execute(Stringhashmap.get(info.getVideoLink()));


            }

            setAlertDialog();

        }

    }

    private class DownloadSrtTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadSrtTask(Context context) {
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


                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                File dir = new File(Environment.getExternalStorageDirectory() + "/ItaliasCinema/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }


                final File file = new File(Environment.getExternalStorageDirectory(), "/ItaliasCinema/" + info.getMovieName() + ".Srt");


                if (!file.exists()) {
                    // file does not exist, create it
                    file.createNewFile();
                }


                input = connection.getInputStream();

                output = new FileOutputStream(file);
                if (output != null)
                    output.flush();

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
                  /*  // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength))*/
                    ;
                    output.write(data, 0, count);
                    Log.d("SrtDownloaderror", fileLength + "");
                }
            } catch (Exception e) {


                Log.d("SrtException1", e + "");
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {

                    Log.d("SrtException2", ignored + "");
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
                Toast.makeText(context, "SrtDownload error: " + result, Toast.LENGTH_LONG).show();

            else
                Toast.makeText(context, "SrtFile downloaded", Toast.LENGTH_SHORT).show();


        }

    }
}
