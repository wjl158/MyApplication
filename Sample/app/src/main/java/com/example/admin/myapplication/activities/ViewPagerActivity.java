package com.example.admin.myapplication.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.admin.myapplication.adapter.MyPagerAdapter;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

import java.util.ArrayList;

public class ViewPagerActivity extends BaseActivity {
    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        vpager_one = (ViewPager) findViewById(R.id.MyViewPager);

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.page_help_four,null,false));
        aList.add(li.inflate(R.layout.activity_main,null,false));
        aList.add(li.inflate(R.layout.activity_spinner,null,false));
        mAdapter = new MyPagerAdapter(aList);
        vpager_one.setAdapter(mAdapter);
    }
}
