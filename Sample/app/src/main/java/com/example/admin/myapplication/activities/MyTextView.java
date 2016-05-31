package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class MyTextView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);


        TextView tv = (TextView)findViewById(R.id.tvTengWangGe);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
