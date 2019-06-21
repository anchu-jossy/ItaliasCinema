package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.ItaliasCinemas.ajit.Italiascinema.R;
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


public class MyApplication extends Application {


    static String encryptedMsg;
    static String KEY_ALIAS_NAME = "Key";
    static PrivateKey privateKey;
    static PublicKey publicKey;
    static String TAG = "MyApplicationclass";


    private static Context context;
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
            return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0scoAGvTlEOLpMkZrxr3NORBIzDx7X27yvcsuCc34RRAdWt+X1v93s5Cw8XZkFIozvhHwscpCcuZdRvTiUEJkA09sIqiCC4LWfUpo/OEGC7NOY5Crfxd6RX+e4tGh5g0DhN428VitEA/eBFmQ5iDWVXWBZDkXoCEHBD4rvFhE73A9tPgKHldLrrgJzb6kCtrH5hGC7KwmAUrj/ITt0Lx/MLmPXBMCLwqGIudJiCFxwjX06febKSodn/Zr3HKjfkbY7kvGsZJEsooCRpo2lzPQs4LrzKNwqK4X2QnT+ch1mdDkEVILzhyoVDKqMnr2k0FL5EzZ7MO4XSvCA7CQflzRQIDAQAB";


        }
    });

    public static Context getAppContext() {
        return MyApplication.context;
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
            Log.d(TAG, "decrypt: " + messageAfterDecrypt);
        } catch (GeneralSecurityException e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
    }

    public static MyApplication get(Activity activity) {
        return (MyApplication) activity.getApplication();
    }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
            Toast.makeText(getAppContext(), "123", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "generateKey: " + keyPair.getPrivate() + "   " + keyPair.getPublic() + "");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "generateKey: " + e + "");
            return false;
        }
    }

    public void loadKey(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS_NAME, null);
            publicKey = keyStore.getCertificate(KEY_ALIAS_NAME).getPublicKey();
            Log.d(TAG, "loadKey: " + privateKey + " " + publicKey + "");
        } catch (Exception e) {
        }
    }

    @Nonnull
    public Billing getBilling() {
        return mBilling;
    }
}
