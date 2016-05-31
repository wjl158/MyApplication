package com.example.admin.myapplication.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.model.Animal;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.AnimalAdapter;

import java.util.LinkedList;
import java.util.List;

public class BaseAdapterDemo extends BaseActivity {
    private List<Animal> mData = null;
    private Context mContext;
    private AnimalAdapter mAdapter = null;
    private ListView list_animal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter_demo);

        mContext = BaseAdapterDemo.this;
        list_animal = (ListView) findViewById(R.id.lvBase);

        //动态加载顶部View和底部View
        final LayoutInflater inflater = LayoutInflater.from(this);
        View headView = inflater.inflate(R.layout.view_header, null, false);
        View footView = inflater.inflate(R.layout.view_footer, null, false);

        mData = new LinkedList<Animal>();
        mData.add(new Animal("狗说", "你是狗么?", R.drawable.a));
        mData.add(new Animal("牛说", "你是牛么?", R.drawable.q));
        mData.add(new Animal("鸭说", "你是鸭么?", R.drawable.loading_12));
        mData.add(new Animal("鱼说", "你是鱼么?", R.drawable.loading_13));
        mData.add(new Animal("马说", "你是马么?", R.drawable.a));
        mAdapter = new AnimalAdapter((LinkedList<Animal>) mData, mContext);

        //添加表头和表尾需要写在setAdapter方法调用之前！！！
        list_animal.addHeaderView(headView);
        list_animal.addFooterView(footView);

        list_animal.setAdapter(mAdapter);

        list_animal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,"你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
