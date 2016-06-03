package com.example.admin.myapplication.activities;

import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.MyAdapter;
import com.example.admin.myapplication.fragmet.FragmentTwo;
import com.example.admin.myapplication.model.Item;

import java.util.ArrayList;

public class DrawerlayoutActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private DrawerLayout drawer_layout;
    private ListView list_left_drawer;
    private ListView list_left_drawer1;
    private ArrayList<Item> menuLists;
    private MyAdapter<Item> myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);

        drawer_layout = (DrawerLayout) findViewById(R.id.ly_activity_drawerlayout_main);
        list_left_drawer = (ListView) findViewById(R.id.lv_activity_drawerlayout_list);  //左边
        list_left_drawer1 = (ListView) findViewById(R.id.lv_activity_drawerlayout_list1); //右边

        menuLists = new ArrayList<Item>();
        menuLists.add(new Item(R.drawable.ic_my_01,"实时信息"));
        menuLists.add(new Item(R.drawable.ic_my_01,"提醒通知"));
        menuLists.add(new Item(R.drawable.ic_my_01,"活动路线"));
        menuLists.add(new Item(R.drawable.ic_my_01,"相关设置"));
        myAdapter = new MyAdapter<Item>(menuLists,R.layout.list_item) {
            @Override
            public void bindView(ViewHolder holder, Item obj) {
                holder.setImageResource(R.id.imgtou,obj.getiId());
                holder.setText(R.id.name, obj.getiName());
            }
        };
        list_left_drawer.setAdapter(myAdapter);
        list_left_drawer1.setAdapter(myAdapter);
        list_left_drawer.setOnItemClickListener(this);
        list_left_drawer1.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTwo contentFragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString("value", menuLists.get(position).getiName());
        contentFragment.setArguments(args);
        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.ly_activity_drawerlayout_frame,contentFragment).commit();
        fm.beginTransaction().add(R.id.ly_activity_drawerlayout_frame,contentFragment).commit();
        drawer_layout.closeDrawer(list_left_drawer);
        drawer_layout.closeDrawer(list_left_drawer1);
    }

    public void show(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_activity_drawerlayout_right:
                drawer_layout.openDrawer(GravityCompat.END);
                break;
            case R.id.btn_activity_drawerlayout_left:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
        }

    }
}
