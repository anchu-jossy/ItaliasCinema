package com.example.ajit.italiascinema.Activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.ajit.italiascinema.R;
import com.goodiebag.pinview.Pinview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationOtpActivity extends AppCompatActivity {

    @BindView(R.id.tv_enter_otp)
    TextView tvEnterOtp;
    @BindView(R.id.tv_otp_send)
    TextView tvOtpSend;
    @BindView(R.id.pinview)
    Pinview pinview;
    @BindView(R.id.btn_resend)
    Button btnResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_otp);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_resend)
    public void onViewClicked() {


        startActivity(new Intent(RegistrationOtpActivity.this,HomenavigationActivity.class));
    }
}
