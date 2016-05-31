package com.example.admin.myapplication.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.model.Data;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.UpdateListViewAdapter;

import java.util.LinkedList;
import java.util.List;

public class UpdateListView extends BaseActivity {
    private ListView list_one;
    private UpdateListViewAdapter mAdapter = null;
    private List<Data> mData = null;
    private Context mContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list_view);

        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add(new Data(R.drawable.g, "給点吧~~~~~~~~~"));
            }
        });

        Button btnInsert1 = (Button)findViewById(R.id.btnInsert1);
        btnInsert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add(2, new Data(R.drawable.g, "給点吧~~~~~~~~~"));
            }
        });

        mContext = UpdateListView.this;
        bindViews();
        mData = new LinkedList<Data>();
//        mData.add(new Data(R.drawable.g, "给点吧"));

        mAdapter = new UpdateListViewAdapter((LinkedList<Data>) mData,mContext);
        list_one.setAdapter(mAdapter);
    }
    private void bindViews(){
        list_one = (ListView) findViewById(R.id.list_one);
    }

}
