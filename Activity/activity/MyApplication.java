package com.example.ajit.italiascinema.Activity.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.ajit.italiascinema.R;
import com.scottyab.aescrypt.AESCrypt;

import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.PlayStoreListener;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import static com.example.ajit.italiascinema.Activity.activity.EncryptionClass.decrypt;
import static com.example.ajit.italiascinema.Activity.activity.EncryptionClass.encryprt;
import static com.example.ajit.italiascinema.Activity.activity.EncryptionClass.generateKey;
import static com.example.ajit.italiascinema.Activity.activity.EncryptionClass.loadKey;


public class MyApplication extends Application {



    static String encryptedMsg;
    static String KEY_ALIAS_NAME = "Key";
    static PrivateKey privateKey;
    static PublicKey publicKey;
    static String TAG = "MyApplicationclass";


    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        mBilling.addPlayStoreListener(new PlayStoreListener() {
            @Override
            public void onPurchasesChanged() {
                Toast.makeText(MyApplication.this, R.string.purchases_changed, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    @Nonnull
    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Nonnull
        @Override
        public String getPublicKey() {
            // encrypted public key of the app. Plain version can be found in Google Play's Developer
            // Console in Service & APIs section under "YOUR LICENSE KEY FOR THIS APPLICATION" title.
            // A naive encryption algorithm is used to "protect" the key. See more about key protection
            // here: https://developer.android.com/google/play/billing/billing_best_practices.html#key

            generateKey();
            loadKey(MyApplication.getAppContext());
            encryprt("haielo");
            decrypt();
            return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmHMe0EylOvsz+imzShqRR1fQ2MhOmvY/SCjgxDAD0PX1NKMt0H4PGf1KnucbX/De37g5FOXh8otJnQGdlSPQ4lcEn81JIBjfwQNnlIbIxrCGOMY7QsTvGsyQO+4HGsCNxyBEMlu7hOgCayfkePyQ1filqWWHSju/TDgVxwpxGr0fXuA9YcWaLv4df46I9y9QbJoE/nFeRw+20qhDY97N/SrWcNlfmZaQOpTcj38/Fnr+ErBvoHXmEqfkX4fOY+FOldQjQjT4+X9Wu4kEJ2V6zWo9xtjVsKbhCZpM11x4tBHt9lqJn2kSto7MPKx+d9tSXRARos8qx6expG29M8IQ7wIDAQAB";

        }
    });

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean generateKey() {
        try {
            KeyPairGenerator keyPairGenerator =KeyPairGenerator.getInstance(
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
            Toast.makeText(getAppContext(), "123", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "generateKey: "+keyPair.getPrivate()+"   "+keyPair.getPublic()+"");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "generateKey: "+e+"");
            return false;
        }
    }
    public void loadKey(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS_NAME, null);
            publicKey = keyStore.getCertificate(KEY_ALIAS_NAME).getPublicKey();
            Log.d(TAG, "loadKey: "+privateKey+" "+publicKey+"");
        } catch (Exception e) {
        }
    }

    public static void encryprt(String message) {


        try {
            encryptedMsg = AESCrypt.encrypt(String.valueOf(publicKey), message);
            Log.d(TAG, "encryprt: " + encryptedMsg);
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }

    static void decrypt() {
        try {
            String messageAfterDecrypt = AESCrypt.decrypt(String.valueOf(publicKey), encryptedMsg);
            Log.d(TAG, "decrypt: "+messageAfterDecrypt);
        } catch (GeneralSecurityException e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
    }
    public static MyApplication get(Activity activity) {
        return (MyApplication) activity.getApplication();
    }



    @Nonnull
    public Billing getBilling() {
        return mBilling;
    }
}
