package com.example.admin.myapplication.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.MyPagerAdapter;
import com.example.admin.myapplication.base.BaseActivity;

import java.util.ArrayList;

public class TabLayoutActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);


//        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("详情界面");

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
//
//        for(int i=0;i<3;i++)
//            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
//        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab3"));

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(TabLayoutActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        mViewPager = (ViewPager) findViewById(R.id.vp_activity_tab_layout_vp);
        // 设置ViewPager的数据等
        setupViewPager();
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("虚竹");
        tabLayout.getTabAt(1).setText("乔峰");
        tabLayout.getTabAt(2).setText("段誉");
    }

    public void setupViewPager(){
        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.page_help_four,null,false));
        aList.add(li.inflate(R.layout.activity_main,null,false));
        aList.add(li.inflate(R.layout.activity_spinner,null,false));
        mAdapter = new MyPagerAdapter(aList);
        mViewPager.setAdapter(mAdapter);
    }
}
