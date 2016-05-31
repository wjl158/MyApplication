package com.example.admin.myapplication.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.fragmet.FragmentFour;
import com.example.admin.myapplication.R;

public class Fragment4Activity extends BaseActivity {
    //Activity UI Object
    private LinearLayout ly_tab_menu_channel;
    private TextView tab_menu_channel;
    private TextView tab_menu_channel_num;
    private LinearLayout ly_tab_menu_message;
    private TextView tab_menu_message;
    private TextView tab_menu_message_num;
    private LinearLayout ly_tab_menu_better;
    private TextView tab_menu_better;
    private TextView tab_menu_better_num;
    private LinearLayout ly_tab_menu_setting;
    private TextView tab_menu_setting;
    private ImageView tab_menu_setting_partner;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    private FragmentFour fg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment4);
        bindViews();
        ly_tab_menu_channel.performClick();
        fg1 = new FragmentFour();
        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();
        fTransaction.add(R.id.ly_content4, fg1).commit();
    }

    private void bindViews() {
        ly_tab_menu_channel = (LinearLayout) findViewById(R.id.ly_tab_menu_channel4);
        tab_menu_channel = (TextView) findViewById(R.id.tab_menu_channel4);
        tab_menu_channel_num = (TextView) findViewById(R.id.tab_menu_channel_num4);
        ly_tab_menu_message = (LinearLayout) findViewById(R.id.ly_tab_menu_message4);
        tab_menu_message = (TextView) findViewById(R.id.tab_menu_message4);
        tab_menu_message_num = (TextView) findViewById(R.id.tab_menu_message_num4);
        ly_tab_menu_better = (LinearLayout) findViewById(R.id.ly_tab_menu_better4);
        tab_menu_better = (TextView) findViewById(R.id.tab_menu_better4);
        tab_menu_better_num = (TextView) findViewById(R.id.tab_menu_better_num4);
        ly_tab_menu_setting = (LinearLayout) findViewById(R.id.ly_tab_menu_setting4);
        tab_menu_setting = (TextView) findViewById(R.id.tab_menu_setting4);
        tab_menu_setting_partner = (ImageView) findViewById(R.id.tab_menu_setting_partner4);


        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ly_tab_menu_channel4:
                        setSelected();
                        tab_menu_channel.setSelected(true);
                        tab_menu_channel_num.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.ly_tab_menu_message4:
                        setSelected();
                        tab_menu_message.setSelected(true);
                        tab_menu_message_num.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.ly_tab_menu_better4:
                        setSelected();
                        tab_menu_better.setSelected(true);
                        tab_menu_better_num.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.ly_tab_menu_setting4:
                        setSelected();
                        tab_menu_setting.setSelected(true);
                        tab_menu_setting_partner.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };

        ly_tab_menu_channel.setOnClickListener(Listener);
        ly_tab_menu_message.setOnClickListener(Listener);
        ly_tab_menu_better.setOnClickListener(Listener);
        ly_tab_menu_setting.setOnClickListener(Listener);

    }


    //重置所有文本的选中状态
    private void setSelected() {
        tab_menu_channel.setSelected(false);
        tab_menu_message.setSelected(false);
        tab_menu_better.setSelected(false);
        tab_menu_setting.setSelected(false);
    }

}
