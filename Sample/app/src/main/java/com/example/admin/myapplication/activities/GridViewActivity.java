package com.example.admin.myapplication.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.model.Icon;
import com.example.admin.myapplication.adapter.MyAdapter;
import com.example.admin.myapplication.R;

import java.util.ArrayList;

public class GridViewActivity extends BaseActivity {
    private Context mContext;
    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Icon> mData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        mContext = GridViewActivity.this;
        grid_photo = (GridView) findViewById(R.id.grid_photo);

        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.a, "图标1"));
        mData.add(new Icon(R.drawable.q, "图标2"));
        mData.add(new Icon(R.drawable.g, "图标3"));
        mData.add(new Icon(R.drawable.loading_12, "图标4"));
        mData.add(new Icon(R.drawable.a, "图标5"));
        mData.add(new Icon(R.drawable.loading_13, "图标6"));
        mData.add(new Icon(R.drawable.a, "图标7"));

        mAdapter = new MyAdapter<Icon>(mData, R.layout.item_grid_icon) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.Grid_img_icon, obj.getiId());
                holder.setText(R.id.Grid_txt_icon, obj.getiName());
            }
        };

        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
