package com.example.admin.myapplication.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.view.DividerGridItemDecoration;
import com.example.admin.myapplication.view.DividerItemDecoration;
import com.example.admin.myapplication.view.DividerItemDecoration1;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<Uri> mDatas;
    private HomeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化
        Fresco.initialize(this);

        setContentView(R.layout.activity_recycler_view_demo);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        //这里可以切换成grid和line
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,        StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,        StaggeredGridLayoutManager.VERTICAL));

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this, R.drawable.shape_divideritem));


        Button btnAdd = (Button)findViewById(R.id.btn_activity_recyler_view_demo_add);
        Button btnDel = (Button)findViewById(R.id.btn_activity_recyler_view_demo_del);
        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
    }

    protected void initData()
    {
        mDatas = new ArrayList<Uri>();
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js1));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js2));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js3));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js4));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js5));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js6));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js7));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js8));

        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js1));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js2));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js3));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js4));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js5));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js6));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js7));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js8));

        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js1));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js2));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js3));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js4));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js5));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js6));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js7));
        mDatas.add(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js8));
//        for (int i = '1'; i < '7'; i++)
//        {
//            mDatas.add("" + (char) i);
//        }
//        mDatas.add(R.drawable.js1);
//        mDatas.add(R.drawable.js2);
//        mDatas.add(R.drawable.js3);
//        mDatas.add(R.drawable.js4);
//        mDatas.add(R.drawable.js5);
//        mDatas.add(R.drawable.js6);
//        mDatas.add(R.drawable.js7);

//        mDatas.add(R.drawable.a);
//        mDatas.add(R.drawable.b);
//        mDatas.add(R.drawable.c);
//        mDatas.add(R.drawable.d);
//        mDatas.add(R.drawable.e);
//        mDatas.add(R.drawable.f);
//        mDatas.add(R.drawable.g);
//        mDatas.add(R.drawable.h);
//        mDatas.add(R.drawable.l);
//
//        mDatas.add(R.drawable.a);
//        mDatas.add(R.drawable.b);
//        mDatas.add(R.drawable.c);
//        mDatas.add(R.drawable.d);
//        mDatas.add(R.drawable.e);
//        mDatas.add(R.drawable.f);
//        mDatas.add(R.drawable.g);
//        mDatas.add(R.drawable.h);
//        mDatas.add(R.drawable.l);



    }

    //适配器
    public class  HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            //1、创建一个ViewHolder 把自己定义的View传递进去
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    RecyclerViewDemoActivity.this).inflate(R.layout.item_recylerview, parent,
                    false));
            return holder;
        }


        //3、这里设置每一个View
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            //随机设置Item的高度 (数据类型)(最小值+Math.random()*(最大值-最小值+1))
            ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
            lp.height = (int)(200+Math.random()*(400-200+1));
//            holder.tv.setImageResource(mDatas.get(position));
            holder.tv.setImageURI(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        //ViewHolder  View持有人
        class MyViewHolder extends RecyclerView.ViewHolder
        {

//            ImageButton tv;
            SimpleDraweeView tv;

            public MyViewHolder(View view)
            {
                super(view);

                //2、在创建ViewHolder的时候顺便取出View里面的控件，方便以后调用
                tv = (SimpleDraweeView) view.findViewById(R.id.id_num);
            }
        }


        public void addData(int position) {
            mDatas.add(position, Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js8));
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_activity_recyler_view_demo_add:
                mAdapter.addData(1);
                break;
            case R.id.btn_activity_recyler_view_demo_del:
                mAdapter.removeData(1);
                break;
            default:
                break;
        }

    }



}
