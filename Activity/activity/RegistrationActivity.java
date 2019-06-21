package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.RegisterResponse;

import com.ItaliasCinemas.ajit.Italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.img_app)
    ImageView imgApp;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_emailid)
    EditText edtEmailid;
    @BindView(R.id.edt_mobileno)
    EditText edtMobileno;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_renter_password)
    EditText edtRenterPassword;
    @BindView(R.id.rl_reg)
    RelativeLayout rlReg;
    String phno = "";
    String username = "";
    String email = "";
    String password = "";
    String gender = "";
    SaveDataClass saveDataClass;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    String TAG = "ItaliasCinema";
    String myAndroidDeviceId;
    private TelephonyManager mTelephonyManager;
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        btnDone.setOnClickListener(this);
        //getDeviceImei();
        // getText();
        checkPermission();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getDeviceImei();
                }
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getDeviceImei();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private String getDeviceImei() {
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager.getImei() != null) {
            myAndroidDeviceId = mTelephonyManager.getImei();


            Log.d("msg123", "DeviceImei " + myAndroidDeviceId);
        }
        return myAndroidDeviceId;
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getText() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBtnClicked() {

        phno = edtMobileno.getText().toString();
        username = edtUsername.getText().toString();
        email = edtEmailid.getText().toString();
        password = edtPassword.getText().toString();
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<RegisterResponse> call = italiaApi.registration(phno, email, username, password,getDeviceImei());
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (phno.equals("") || username.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {


                        RegisterResponse registerResponse = response.body();
                        int status = registerResponse.getStatus();
                        Log.d("Responsesucess", call + "        " + registerResponse.getStatus() + "");
                        if (status == 0) {
                           Toast.makeText(RegistrationActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            SaveDataClass.setEmailId(RegistrationActivity.this, response.body().getEmailid());
                            SaveDataClass.setUserID(RegistrationActivity.this, response.body().getUserid());
                            SaveDataClass.setUserName(RegistrationActivity.this, response.body().getUsername());
                            SaveDataClass.setUserPassword(RegistrationActivity.this, response.body().getPassword());
                            Log.d(" SaveDataClass.", SaveDataClass.getEmailId(RegistrationActivity.this));
                            Intent intent = new Intent(RegistrationActivity.this, HomenavigationActivity.class);
                            startActivity(intent);
                            finish();

                        }


                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("Response", call + "        " + t + "");
            }

        });


    }

    @Override
    public void onBackPressed() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                onBtnClicked();

        }
    }



/*
    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        onBtnClicked();
    }*/
}