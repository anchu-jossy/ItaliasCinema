package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

final class EncryptionClass {

    static String encryptedMsg;
    static String KEY_ALIAS_NAME = "Key";
    static PrivateKey privateKey;
    static PublicKey publicKey;
    static String TAG = "EncryptionClass";

    public static void encryprt(String message) {


        try {
            encryptedMsg = AESCrypt.encrypt(String.valueOf(publicKey), message);
            Log.d(TAG, "encryprt: " + encryptedMsg);
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }

    public static boolean generateKey() {
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
            Log.d(TAG, "generateKey: "+keyPair.getPublic()+"");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void loadKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS_NAME, null);
            publicKey = keyStore.getCertificate(KEY_ALIAS_NAME).getPublicKey();
            Log.d(TAG, "loadKey: "+privateKey+" "+publicKey);
        } catch (Exception e) {
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
}