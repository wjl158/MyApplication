package com.example.admin.myapplication.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.admin.myapplication.adapter.MyPagerAdapter2;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

import java.util.ArrayList;

public class ViewPager1Activity extends BaseActivity {
    private ViewPager vpager_two;
    private ArrayList<View> aList;
    private ArrayList<String> sList;
    private MyPagerAdapter2 mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager1);
        vpager_two = (ViewPager) findViewById(R.id.vpager_two);
        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.page_help_four,null,false));
        aList.add(li.inflate(R.layout.activity_main,null,false));
        aList.add(li.inflate(R.layout.activity_spinner,null,false));
        sList = new ArrayList<String>();
        sList.add("橘黄");
        sList.add("淡黄");
        sList.add("浅棕");
        mAdapter = new MyPagerAdapter2(aList,sList);
        vpager_two.setAdapter(mAdapter);
    }
}
