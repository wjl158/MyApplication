package com.example.admin.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.myapplication.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        ImageView view = (ImageView) findViewById(R.id.img_activity_welcome_imgMain);
//        view.setImageResource(R.drawable.welcome);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(WelcomeActivity.this, MainNavActivity.class));
//                finish();
//            }
//        },3000);


        Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(l->{
            startActivity(new Intent(WelcomeActivity.this, MainNavActivity.class));
            finish();
            return null;
        }).subscribe();
//
//        Observable.interval(2, 2, TimeUnit.SECONDS)


    }


}
