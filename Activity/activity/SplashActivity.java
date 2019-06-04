package com.example.ajit.italiascinema.Activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.ajit.italiascinema.Activity.savedata.SaveDataClass;
import com.example.ajit.italiascinema.R;

public class SplashActivity extends AppCompatActivity {
    SaveDataClass saveDataClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       /* StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              saveDataClass = new SaveDataClass(SplashActivity.this);



             //   prefs= getSharedPreferences("ItaliasCinema", MODE_PRIVATE);
                String username =   saveDataClass.getStr("username");
                String password =  saveDataClass.getStr("password");
                if (!username.equals("") && !password.equals("")) {


                    Intent intent = new Intent(SplashActivity.this, HomenavigationActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(SplashActivity.this, HomenavigationActivity.class);
                    startActivity(intent);

                }

                finish();
            }
        }, 2500);
    }
}
