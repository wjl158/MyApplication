package com.example.admin.myapplication.activities;

import android.database.Cursor;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.content.CursorLoader;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCursorAdapterDemo extends BaseActivity {
//    private ListView Lv;

    private String[] names = new String[]{"B神", "基神", "曹神"};
    private String[] says = new String[]{"无形被黑，最为致命", "大神好厉害~", "我将带头日狗~"};
//    private int[] imgIds = new int[]{R.mipmap.head_icon1, R.mipmap.head_icon2, R.mipmap.head_icon3};
    private int[] imgIds = new int[]{R.drawable.q, R.drawable.a, R.drawable.loading_12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_cursor_adapter_demo);
//
//        Lv = (ListView)findViewById(R.id.listView);
//        Cursor cursor = getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
//        //CursorLoader mycursor = new CursorLoader();
//
//        ListAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1,
//                cursor,
//                new String[]{Contacts.People.NAME},
//                new int[]{android.R.id.text1});
//
//        Lv.setAdapter(listAdapter);



        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("touxiang", imgIds[i]);
            showitem.put("name", names[i]);
            showitem.put("says", says[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

    }

}
