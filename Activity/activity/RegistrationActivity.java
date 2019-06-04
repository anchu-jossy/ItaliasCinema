package com.example.ajit.italiascinema.Activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajit.italiascinema.Activity.network.Connection;
import com.example.ajit.italiascinema.Activity.network.ResponseCallback;
import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    String email= "";
    String password= "";
    String gender="";
    SaveDataClass saveDataClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        btnDone.setOnClickListener(this);
       // getText();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // getText();
    }

    private void getText(){
    phno = edtMobileno.getText().toString();
    username=edtUsername.getText().toString();
    email=edtEmailid.getText().toString();
    password=edtPassword.getText().toString();
}

//    public void onViewClicked() {
//
//        phno = String.valueOf(edtMobileno.getText());
//        username= String.valueOf(edtUsername.getText());
//        email= String.valueOf(edtEmailid.getText());
//        password= String.valueOf(edtPassword.getText());
//
//        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);
//
//        Call<RegisterResponse> call = italiaApi.registration(phno,username,email,password,"male");
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
////                try {
////                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
////                   String status = String.valueOf(jsonObject.get("status"));
////                   if(status.equals("0")){
////                       Toast.makeText(RegistrationActivity.this,"Already Register",Toast.LENGTH_SHORT);
////                   }else{
////
////                       saveDataClass = new SaveDataClass(RegistrationActivity.this);
////                       saveDataClass.setStr("username", username);
////                       saveDataClass.setStr("password", password);
////
////                       Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
////                       startActivity(intent);
////                       finish();
////                   }
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//
//                saveDataClass = new SaveDataClass(RegistrationActivity.this);
//                saveDataClass.setStr("username", username);
//                saveDataClass.setStr("password", password);
//
//                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Log.d("Response","failure");
//            }
//
//        });
//
//
//}

    public void onViewClicked() {

        phno = String.valueOf(edtMobileno.getText());
        username= String.valueOf(edtUsername.getText());
        email= String.valueOf(edtEmailid.getText());
        password= String.valueOf(edtPassword.getText());
        Connection.with(this, "http://redex.info/italian_movie/index.php/Api/" + "user_registration")
                .setMethod(Connection.Method.POST)
                .addParameter("phno", phno)
                .addParameter("username", username)
                .addParameter("email", email)
                .addParameter("password", password)
                .addParameter("gender","male")
                .performNetworkCall(new ResponseCallback() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            saveDataClass = new SaveDataClass(RegistrationActivity.this);
                            saveDataClass.setStr("username", email);
                            saveDataClass.setStr("password", password);
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();

                        } else
                            Toast.makeText(RegistrationActivity.this, "Some thing happing wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(RegistrationActivity.this, "Some thing happing wrong", Toast.LENGTH_SHORT).show();
                    }
                }, true);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_done :
              onViewClicked();
        }

    }
}