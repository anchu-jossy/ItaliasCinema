package com.example.ajit.italiascinema.Activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.ajit.italiascinema.Activity.adapter.NotificationsAdapter;
import com.example.ajit.italiascinema.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.recent_recyclerview)
    RecyclerView recentRecyclerview;
    @BindView(R.id.imgview_leftarrow)
    ImageView imgviewLeftarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        NotificationsAdapter notificationsAdapter = new NotificationsAdapter(this);
        recentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        recentRecyclerview.setAdapter(notificationsAdapter);
        getSupportActionBar().hide();
    }

    @OnClick(R.id.imgview_leftarrow)
    public void onViewClicked() {
        onBackPressed();
    }
}
