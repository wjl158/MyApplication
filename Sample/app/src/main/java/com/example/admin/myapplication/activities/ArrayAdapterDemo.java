package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterDemo extends BaseActivity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter_demo);
//        lv=(ListView)findViewById(R.id.arrayadapterdemolistview);
//        lv.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_expandable_list_item_1, getData()));

//////////////////////////////////方法一///////////////////////////////////////////////
//        //要显示的数据
//        String[] strs = {"基神","B神","翔神","曹神","J神"};
//        //创建ArrayAdapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this,R.layout.simple_item_textview,strs);


/////////////////////////////////方法二////////////////////////////////////////////////
//        List<String> data = new ArrayList<String>();
//        data.add("基神");
//        data.add("B神");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this,R.layout.simple_item_textview,data);
//
//
//
/////////////////////////////////方法三///////////////////////////////////////////
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.myarray,R.layout.simple_item_textview );


//        <ListView
//        android:id="@id/list_test"
//        android:layout_height="match_parent"
//        android:layout_width="match_parent"
//        android:entries="@array/myarray"/>               //或者设置改属性

/////////////////////////////////////////////////////////////////////////////////

        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
         lv = (ListView) findViewById(R.id.arrayadapterdemolistview);
        lv.setAdapter(adapter);
    }
//    public List<String> getData(){
//        List<String> data = new ArrayList<String>();
//        data.add("测试数据1");
//        data.add("测试数据2");
//        data.add("测试数据3");
//        data.add("测试数据4");
//
//        return data;
//
//
//    }

}
