package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ViewFlipper;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class MyFlipper1Activity extends BaseActivity {
    private ViewFlipper vflp_help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_flipper1);

        vflp_help = (ViewFlipper) findViewById(R.id.MyviewFlipper0);
        vflp_help.startFlipping();
    }
}
