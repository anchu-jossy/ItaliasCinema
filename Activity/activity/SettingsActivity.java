package com.example.ajit.italiascinema.Activity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ajit.italiascinema.Activity.fragments.ChangePasswordFragment;
import com.example.ajit.italiascinema.Activity.fragments.ClearDialogFragment;
import com.example.ajit.italiascinema.Activity.fragments.DownloadsQuality;
import com.example.ajit.italiascinema.Activity.fragments.EditprofileFragment;
import com.example.ajit.italiascinema.Activity.fragments.LanguagePreferenceFragment;
import com.example.ajit.italiascinema.Activity.fragments.TrendingVideoDetailsFragment;
import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.tv_account_settings)
    TextView tvAccountSettings;
    @BindView(R.id.edit_profile)
    TextView editProfile;
    @BindView(R.id.change_password)
    TextView changePassword;
    @BindView(R.id.rl_account_settings)
    RelativeLayout rlAccountSettings;
    @BindView(R.id.viewline_1)
    View viewline1;
    @BindView(R.id.tv_downloads)
    TextView tvDownloads;
    @BindView(R.id.tv_downloads_quality)
    TextView tvDownloadsQuality;
    @BindView(R.id.tv_downloads_wifi)
    TextView tvDownloadsWifi;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.rl_downloads)
    RelativeLayout rlDownloads;
    @BindView(R.id.viewline_2)
    View viewline2;
    @BindView(R.id.tv_language_preference)
    TextView tvLanguagePreference;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.language)
    RelativeLayout language;
    @BindView(R.id.rl_settings_container)
    RelativeLayout rlSettingsContainer;
    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

    }


    @OnClick({R.id.edit_profile, R.id.tv_downloads_quality, R.id.tv_clear, R.id.change_password,R.id.tv_language_preference})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_profile:
                EditprofileFragment editprofileFragment = new EditprofileFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.rl_settings_container, editprofileFragment);
                transaction.commit();

                break;
            case R.id.tv_downloads_quality:

                DownloadsQuality downloadsQuality = new DownloadsQuality();
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.rl_settings_container, downloadsQuality);
                transaction1.commit();
                break;
            case R.id.tv_clear:

                ClearDialogFragment clearDialogFragment = new ClearDialogFragment();
                clearDialogFragment.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.change_password:

                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.rl_settings_container, changePasswordFragment);
                transaction2.commit();
                break;
            case R.id.tv_language_preference:
                LanguagePreferenceFragment languagePreferenceFragment=new LanguagePreferenceFragment();
                FragmentTransaction transaction3= getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.rl_settings_container, languagePreferenceFragment);
                transaction3.commit();
                break;
        }
    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {
        onBackPressed();
    }
}
