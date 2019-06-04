package com.example.ajit.italiascinema.Activity.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajit.italiascinema.Activity.fragments.ChangePasswordFragment;
import com.example.ajit.italiascinema.Activity.network.Connection;
import com.example.ajit.italiascinema.Activity.network.ResponseCallback;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

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
    SaveDataClass saveDataClass;

    String username = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUnderLine();
        String username =   saveDataClass.getStr("username");
        String password =  saveDataClass.getStr("password");

        edtUsername.setText(username);
        edtPassword.setText(password);
    }

    private void setUnderLine() {
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

//    private void doneButtonClick() {
//
//        username = edtUsername.getText().toString();
//        password = edtPassword.getText().toString();
//
//        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);
//        Call<RegisterResponse> call = italiaApi.login(username, password);
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//
//                saveDataClass = new SaveDataClass(LoginActivity.this);
//                saveDataClass.setStr("username", username);
//                saveDataClass.setStr("password", password);
//                startActivity(new Intent(LoginActivity.this, HomenavigationActivity.class));
//                finish();
//                Log.d("Response1", response.message());
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Log.d("Response2", t.getMessage());
//            }
//
//
//        });
//    }

    private  void doneButtonClick(){


        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        Connection.with(this, "http://redex.info/italian_movie/index.php/Api/"+"user_login?"+"email="+username+"&"+"password="+password)
                .setMethod(Connection.Method.POST)
                .performNetworkCall(new ResponseCallback() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            saveDataClass = new SaveDataClass(LoginActivity.this);
                            saveDataClass.setStr("username", username);
                            saveDataClass.setStr("password", password);
                            startActivity(new Intent(LoginActivity.this, HomenavigationActivity.class));
                             finish();

                        } else
                            Toast.makeText(LoginActivity.this,"Some thing happing wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(LoginActivity.this, "Some thing happing wrong", Toast.LENGTH_SHORT).show();
                    }
                }, true);
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
}
