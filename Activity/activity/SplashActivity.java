package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.RegisterResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    static String KEY_ALIAS_NAME = "Key";
    String TAG = "ItaliasCinema000";
    String myAndroidDeviceId;
    SecretKey keyAesKeystore;
    String encryptedMsg;
    private TelephonyManager mTelephonyManager;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       /* StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());*/
        if (SaveDataClass.getUserName(SplashActivity.this) != null && SaveDataClass.getUserPassword(SplashActivity.this) != null) {

         /*   new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    *//*  Already registered*//*

                    //   prefs= getSharedPreferences("ItaliasCinema", MODE_PRIVATE);


                    finish();
                }
            }, 2500);*/

           startActivity(new Intent(SplashActivity.this, HomenavigationActivity.class));
        } else {
            checkPermission();
        }


    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "checkPermission: sdk>m");

            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "checkPermission: requesting permission");
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "checkPermission: sdk>0");
                    getDeviceImei();
                }
            }
        } else {
            Log.d(TAG, "checkPermission: sdk<m");
            getDeviceImei();
            //  startActivity(new Intent(SplashActivity.this,LoginActivity.class)) ;
        }
    }

    public boolean generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyPairGenerator.initialize(
                        new KeyGenParameterSpec.Builder(
                                KEY_ALIAS_NAME,
                                KeyProperties.PURPOSE_DECRYPT)
                                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                                .build());
            }
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            Log.d(TAG, "generateKey: " + keyPair.getPrivate() + "   " + keyPair.getPublic() + "");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void loadKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS_NAME, null);
            publicKey = keyStore.getCertificate(KEY_ALIAS_NAME).getPublicKey();
            Log.d(TAG, "loadKey: " + privateKey + " " + publicKey + "");
        } catch (Exception e) {
        }
    }

    void encryprt() {
        String message = "hai it's me";
        try {
            encryptedMsg = AESCrypt.encrypt(String.valueOf(publicKey), message);
            Toast.makeText(this, "ENcrypt: " + encryptedMsg, Toast.LENGTH_SHORT).show();
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }


    void decrypt() {
        try {
            String messageAfterDecrypt = AESCrypt.decrypt(String.valueOf(publicKey), encryptedMsg);
            Toast.makeText(this, "DEcrypt: " + messageAfterDecrypt, Toast.LENGTH_SHORT).show();
        } catch (GeneralSecurityException e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {


        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        } else {
            checkPermission();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private void getDeviceImei() {
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager.getImei() != null) {
            myAndroidDeviceId = mTelephonyManager.getImei();

            getImei(myAndroidDeviceId);
            Log.d("msg123", "DeviceImei " + myAndroidDeviceId);
        }
    }

    private void getImei(String imei) {

        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);


        Call<RegisterResponse> call = italiaApi.getImei(imei);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getStatus() == 1) {
                    Toast.makeText(SplashActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + registerResponse.getPassword() + "" + registerResponse.getUsername());
                    SaveDataClass.setUserName(SplashActivity.this, registerResponse.getUsername());
                    SaveDataClass.setUserPassword(SplashActivity.this, registerResponse.getPassword());
                    SaveDataClass.setUserID(SplashActivity.this, registerResponse.getUserid());
                    Intent intent = new Intent(SplashActivity.this, HomenavigationActivity.class);
                    startActivity(intent);

                } else if (registerResponse.getStatus() == 0) {
                    Log.d(TAG, "onFailure: " + registerResponse.getStatus());
                    Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });


    }
}
