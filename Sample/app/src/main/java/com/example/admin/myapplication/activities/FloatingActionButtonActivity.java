package com.example.admin.myapplication.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.admin.myapplication.R;

public class FloatingActionButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_action_button);
    }

    public void floatbtnClick(View v)
    {
        //包裹在CoordinatorLayout里的Snackbar和FloatingActionButton的位置会自动协调
        Snackbar.make(findViewById(R.id.lv_activity_floating_action_button_root), "点击了按钮", Snackbar.LENGTH_SHORT).show();
    }
}
