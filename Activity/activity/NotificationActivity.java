package com.ItaliasCinemas.ajit.Italiascinema.Activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;


import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.ItaliaApi;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata.SaveDataClass;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.Api.RetrofitClientInstance;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter.NotificationsAdapter;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;

import com.ItaliasCinemas.ajit.Italiascinema.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.recent_recyclerview)
    RecyclerView recentRecyclerview;
    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;
    ArrayList<Info> infoArrayList = new ArrayList<>();
    NotificationsAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getNotificationData();
    }

    private void getNotificationData() {
        Log.d("notificationerror12345", SaveDataClass.getUserID(NotificationActivity.this)+"");
        ItaliaApi italiaApi = RetrofitClientInstance.getRetrofitInstance().create(ItaliaApi.class);

        Call<FeatureMoviesResponse> call = italiaApi.getNotification(SaveDataClass.getUserID(NotificationActivity.this));

        call.enqueue(new Callback<FeatureMoviesResponse>() {
            @Override
            public void onResponse(Call<FeatureMoviesResponse> call, Response<FeatureMoviesResponse> response) {


                FeatureMoviesResponse featureMoviesResponse = response.body();

                if (featureMoviesResponse.getStatus() == 1) {
                    Log.d("notificationerror123", "");
                    infoArrayList.clear();
                    for (int i = 0; i < featureMoviesResponse.getInfo().size(); i++) {
                        infoArrayList.add(featureMoviesResponse.getInfo().get(i));
                    }
                    notificationsAdapter = new NotificationsAdapter(NotificationActivity.this,infoArrayList);
                    recentRecyclerview.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));

                    recentRecyclerview.setAdapter(notificationsAdapter);

                }

            }

            @Override
            public void onFailure(Call<FeatureMoviesResponse> call, Throwable t) {
                Log.d("notificationerror", t.getMessage());
            }
        });

    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {

        onBackPressed();
    }
}
