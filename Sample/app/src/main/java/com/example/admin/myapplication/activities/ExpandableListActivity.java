package com.example.admin.myapplication.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.model.Group;
import com.example.admin.myapplication.model.Item;
import com.example.admin.myapplication.adapter.MyBaseExpandableListAdapter;
import com.example.admin.myapplication.R;

import java.util.ArrayList;

public class ExpandableListActivity extends BaseActivity {
    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        mContext = ExpandableListActivity.this;
        exlist_lol = (ExpandableListView) findViewById(R.id.exlist_lol);


        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("AD"));
        gData.add(new Group("AP"));
        gData.add(new Group("TANK"));

        lData = new ArrayList<Item>();

        //AD组
        lData.add(new Item(R.drawable.a,"剑圣"));
        lData.add(new Item(R.drawable.g,"德莱文"));
        lData.add(new Item(R.drawable.a,"男枪"));
        lData.add(new Item(R.drawable.q,"韦鲁斯"));
        iData.add(lData);
        //AP组
        lData = new ArrayList<Item>();
        lData.add(new Item(R.drawable.a, "提莫"));
        lData.add(new Item(R.drawable.g, "安妮"));
        lData.add(new Item(R.drawable.q, "天使"));
        lData.add(new Item(R.drawable.loading_12, "泽拉斯"));
        lData.add(new Item(R.drawable.loading_12, "狐狸"));
        iData.add(lData);
        //TANK组
        lData = new ArrayList<Item>();
        lData.add(new Item(R.drawable.q, "诺手"));
        lData.add(new Item(R.drawable.a, "德邦"));
        lData.add(new Item(R.drawable.g, "奥拉夫"));
        lData.add(new Item(R.drawable.loading_13, "龙女"));
        lData.add(new Item(R.drawable.loading_12, "狗熊"));
        iData.add(lData);

        myAdapter = new MyBaseExpandableListAdapter(gData,iData,mContext);
        exlist_lol.setAdapter(myAdapter);

        //为列表设置点击事件
        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }
}
