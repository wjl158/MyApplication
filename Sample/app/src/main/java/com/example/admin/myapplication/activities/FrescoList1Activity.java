package com.example.admin.myapplication.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.MyAdapter;
import com.example.admin.myapplication.tools.FrescoConfigConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;

public class FrescoList1Activity extends AppCompatActivity {

    public ArrayList<MyData> mDatas;
    public BaseAdapter myAdadpter;

    public class MyData{
        public int  rID;

        public MyData() {
        }

        public MyData( int rID) {
            this.rID = rID;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_list1);


        ListView lv = (ListView)findViewById(R.id.lv_activity_fresco_list1_main);

        mDatas = new ArrayList<MyData>();
        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));

        mDatas.add(new MyData(R.drawable.js1));
        mDatas.add(new MyData(R.drawable.js2));
        mDatas.add(new MyData(R.drawable.js3));
        mDatas.add(new MyData(R.drawable.js4));
        mDatas.add(new MyData(R.drawable.js5));
        mDatas.add(new MyData(R.drawable.js6));
        mDatas.add(new MyData(R.drawable.js7));
        mDatas.add(new MyData(R.drawable.js8));





        myAdadpter = new MyAdapter<MyData>(mDatas,R.layout.item_fresco_list1) {
            @Override
            public void bindView(ViewHolder holder, MyData obj) {

                holder.setImageResource(R.id.img_item_fresco_list1_main,obj.rID);


            }
        };

        lv.setAdapter(myAdadpter);
    }
}
