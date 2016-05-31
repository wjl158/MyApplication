package com.example.admin.myapplication.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.fragmet.FragmentTwo;
import com.example.admin.myapplication.R;

public class Fragment2Activity extends BaseActivity {
    //UI Object
    private TextView txt_topbar;
    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private TextView txt_setting;
    private FrameLayout ly_content;

    //Fragment Object
    private FragmentTwo fg1,fg2,fg3,fg4;
    private FragmentManager fManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);

        fManager = getFragmentManager();
        bindViews();
        txt_channel.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_channel = (TextView) findViewById(R.id.txt_channel);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_better = (TextView) findViewById(R.id.txt_better);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fTransaction = fManager.beginTransaction();
                hideAllFragment(fTransaction);
                switch (v.getId()){
                    case R.id.txt_channel:
                        setSelected();
                        txt_channel.setSelected(true);
                        if(fg1 == null){
                            fg1 = new FragmentTwo("第一个Fragment");
                            fTransaction.add(R.id.ly_content,fg1);
                        }else{
                            fTransaction.show(fg1);
                        }
                        break;
                    case R.id.txt_message:
                        setSelected();
                        txt_message.setSelected(true);
                        if(fg2 == null){
                            fg2 = new FragmentTwo("第二个Fragment");
                            fTransaction.add(R.id.ly_content,fg2);
                        }else{
                            fTransaction.show(fg2);
                        }
                        break;
                    case R.id.txt_better:
                        setSelected();
                        txt_better.setSelected(true);
                        if(fg3 == null){
                            fg3 = new FragmentTwo("第三个Fragment");
                            fTransaction.add(R.id.ly_content,fg3);
                        }else{
                            fTransaction.show(fg3);
                        }
                        break;
                    case R.id.txt_setting:
                        setSelected();
                        txt_setting.setSelected(true);
                        if(fg4 == null){
                            fg4 = new FragmentTwo("第四个Fragment");
                            fTransaction.add(R.id.ly_content,fg4);
                        }else{
                            fTransaction.show(fg4);
                        }
                        break;
                }
                fTransaction.commit();
            }
        };

        txt_channel.setOnClickListener(Listener);
        txt_message.setOnClickListener(Listener);
        txt_better.setOnClickListener(Listener);
        txt_setting.setOnClickListener(Listener);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
        txt_setting.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }


}
