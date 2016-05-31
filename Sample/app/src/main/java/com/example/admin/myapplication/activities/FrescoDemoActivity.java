package com.example.admin.myapplication.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class FrescoDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化
        Fresco.initialize(this);


        setContentView(R.layout.activity_fresco_demo);

        Uri uri = Uri.parse("http://imgsrc.baidu.com/forum/w%3D580/sign=5a86f5d1ce177f3e1034fc0540ce3bb9/f11745039245d6885f71d5c3a3c27d1ed31b2454.jpg");
        SimpleDraweeView draweeView1 = (SimpleDraweeView) findViewById(R.id.sdv_activity_fresco_demo_img1);
        draweeView1.setImageURI(uri);

        //加载本地资源图片
        SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.sdv_activity_fresco_demo_img2);
        draweeView2.setImageURI(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js8));
    }
}
