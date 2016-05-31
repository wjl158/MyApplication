package com.example.admin.myapplication.activities;

import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class DrawbleDemo1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawble_demo1);

//        BitmapDrawable bitDrawable = new BitmapDrawable(bitmap);
//        bitDrawable.setDither(true);
//        bitDrawable.setTileModeXY(Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
    }
}
