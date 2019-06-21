package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.ItaliasCinemas.ajit.Italiascinema.R;

import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.PlayStoreListener;

import javax.annotation.Nonnull;

public class CheckoutApplication extends Application {

    @Nonnull
    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @Nonnull
        @Override
        public String getPublicKey() {
            // encrypted public key of the app. Plain version can be found in Google Play's Developer
            // Console in Service & APIs section under "YOUR LICENSE KEY FOR THIS APPLICATION" title.
            // A naive encryption algorithm is used to "protect" the key. See more about key protection
            // here: https://developer.android.com/google/play/billing/billing_best_practices.html#key
           return  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmHMe0EylOvsz+imzShqRR1fQ2MhOmvY/SCjgxDAD0PX1NKMt0H4PGf1KnucbX/De37g5FOXh8otJnQGdlSPQ4lcEn81JIBjfwQNnlIbIxrCGOMY7QsTvGsyQO+4HGsCNxyBEMlu7hOgCayfkePyQ1filqWWHSju/TDgVxwpxGr0fXuA9YcWaLv4df46I9y9QbJoE/nFeRw+20qhDY97N/SrWcNlfmZaQOpTcj38/Fnr+ErBvoHXmEqfkX4fOY+FOldQjQjT4+X9Wu4kEJ2V6zWo9xtjVsKbhCZpM11x4tBHt9lqJn2kSto7MPKx+d9tSXRARos8qx6expG29M8IQ7wIDAQAB";
          //  return Encryption.decrypt(s, "se.solovyev@gmail.com");
        }
    });

    /**
     * Returns an instance of {@link CheckoutApplication} attached to the passed activity.
     */
    public static CheckoutApplication get(Activity activity) {
        return (CheckoutApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBilling.addPlayStoreListener(new PlayStoreListener() {
            @Override
            public void onPurchasesChanged() {
                Toast.makeText(CheckoutApplication.this, R.string.purchases_changed, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nonnull
    public Billing getBilling() {
        return mBilling;
    }
}
