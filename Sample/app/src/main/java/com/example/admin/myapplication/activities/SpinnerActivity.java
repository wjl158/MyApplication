package com.example.admin.myapplication.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.model.Hero;
import com.example.admin.myapplication.adapter.MyAdapter;
import com.example.admin.myapplication.R;

import java.util.ArrayList;

public class SpinnerActivity extends BaseActivity {
    private Spinner spin_one;
    private Spinner spin_two;
    private Context mContext;
    //判断是否为刚进去时触发onItemSelected的标志
    private boolean one_selected = false;
    private boolean two_selected = false;
    private ArrayList<Hero> mData = null;
    private BaseAdapter myAdadpter = null;

    private AutoCompleteTextView atv_content;
    private MultiAutoCompleteTextView matv_content;

    private static final String[] data = new String[]{
            "小猪猪", "小狗狗", "小鸡鸡", "小猫猫", "小咪咪"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        mContext = SpinnerActivity.this;
        mData = new ArrayList<Hero>();
        bindViews();


        atv_content = (AutoCompleteTextView) findViewById(R.id.atv_content);
        matv_content = (MultiAutoCompleteTextView) findViewById(R.id.matv_content);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpinnerActivity.
                this, android.R.layout.simple_dropdown_item_1line, data);
        atv_content.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        matv_content.setAdapter(adapter);
        matv_content.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void bindViews() {
        spin_one = (Spinner) findViewById(R.id.spin_one);
        spin_two = (Spinner) findViewById(R.id.spin_two);

        mData.add(new Hero(R.drawable.q,"迅捷斥候：提莫（Teemo）"));
        mData.add(new Hero(R.drawable.a,"诺克萨斯之手：德莱厄斯（Darius）"));
        mData.add(new Hero(R.drawable.g,"无极剑圣：易（Yi）"));
        mData.add(new Hero(R.drawable.q,"德莱厄斯：德莱文（Draven）"));
        mData.add(new Hero(R.drawable.loading_12,"德邦总管：赵信（XinZhao）"));
        mData.add(new Hero(R.drawable.loading_13,"狂战士：奥拉夫（Olaf）"));

        myAdadpter = new MyAdapter<Hero>(mData,R.layout.item_spin_hero) {
            @Override
            public void bindView(ViewHolder holder, Hero obj) {
                holder.setImageResource(R.id.Spinner_img_icon,obj.gethIcon());
                holder.setText(R.id.Spinner_txt_name, obj.gethName());
            }
        };
        spin_two.setAdapter(myAdadpter);


        AdapterView.OnItemSelectedListener Mylistener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()) {
                    case R.id.spin_one:
                        if (one_selected) {
                            Toast.makeText(mContext, "您的分段是~：" + parent.getItemAtPosition(position).toString(),
                                    Toast.LENGTH_SHORT).show();
                        } else one_selected = true;
                        break;
                    case R.id.spin_two:
                        if (two_selected) {
                            TextView txt_name = (TextView) view.findViewById(R.id.Spinner_txt_name);
                            Toast.makeText(mContext, "您选择的英雄是~：" + txt_name.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        } else two_selected = true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spin_two.setOnItemSelectedListener(Mylistener);
        spin_one.setOnItemSelectedListener(Mylistener);

    }

}
