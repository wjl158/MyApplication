package com.example.admin.myapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.admin.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

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
