package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.activity.LoginActivity;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.RegisterOtpResponse;
import com.ItaliasCinemas.ajit.Italiascinema.R;
import com.goodiebag.pinview.Pinview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationOtpActivity extends AppCompatActivity {

    @BindView(R.id.tv_enter_otp)
    TextView tvEnterOtp;
    @BindView(R.id.tv_otp_send)
    TextView tvOtpSend;
    @BindView(R.id.pinview)
    Pinview pinview;
    @BindView(R.id.btn_resend)
    Button btnResend;
    @BindView(R.id.btn_done)
    Button btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_otp);
        ButterKnife.bind(this);
        pinview.setValue("7203");
    }

    public void onViewClicked() {


        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<RegisterOtpResponse> call = italiaApi.registrationOtp("7", "7203");
        call.enqueue(new Callback<RegisterOtpResponse>() {
            @Override
            public void onResponse(Call<RegisterOtpResponse> call, Response<RegisterOtpResponse> response) {
                if(!response.body().equals(null))
                Log.d("success", response.message() + "");
                startActivity(new Intent(RegistrationOtpActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<RegisterOtpResponse> call, Throwable t) {
                Log.d("FAiloure", t + "");
            }
        });


    }



    @OnClick({R.id.btn_done, R.id.btn_resend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                onViewClicked();
                Log.d("pinvalue", pinview.getValue() + "");

                break;
            case R.id.btn_resend:
                break;
        }
    }


}
