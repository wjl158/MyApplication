package com.example.admin.myapplication.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.fragmet.FragmentTwo;
import com.example.admin.myapplication.R;

public class Fragment3Activity extends BaseActivity {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;

    //Fragment Object
    private FragmentTwo fg1,fg2,fg3,fg4;
    private FragmentManager fManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment3);

        fManager = getFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar1);


        RadioGroup.OnCheckedChangeListener Listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fTransaction = fManager.beginTransaction();
                hideAllFragment(fTransaction);
                switch (checkedId){
                    case R.id.rb_channel1:
                        if(fg1 == null){
                            fg1 = new FragmentTwo();
                            Bundle bundle = new Bundle();
                            bundle.putString("value", "第一个Fragment");
                            fg1.setArguments(bundle);
                            fTransaction.add(R.id.ly_content1,fg1);
                        }else{
                            fTransaction.show(fg1);
                        }
                        break;
                    case R.id.rb_message1:
                        if(fg2 == null){
                            fg2 = new FragmentTwo();
                            Bundle bundle = new Bundle();
                            bundle.putString("value", "第二个Fragment");
                            fg2.setArguments(bundle);
                            fTransaction.add(R.id.ly_content1,fg2);
                        }else{
                            fTransaction.show(fg2);
                        }
                        break;
                    case R.id.rb_better1:
                        if(fg3 == null){
                            fg3 = new FragmentTwo();
                            Bundle bundle = new Bundle();
                            bundle.putString("value", "第三个Fragment");
                            fg3.setArguments(bundle);
                            fTransaction.add(R.id.ly_content1,fg3);
                        }else{
                            fTransaction.show(fg3);
                        }
                        break;
                    case R.id.rb_setting1:
                        if(fg4 == null){
                            fg4 = new FragmentTwo();
                            Bundle bundle = new Bundle();
                            bundle.putString("value", "第四个Fragment");
                            fg4.setArguments(bundle);
                            fTransaction.add(R.id.ly_content1,fg4);
                        }else{
                            fTransaction.show(fg4);
                        }
                        break;
                }
                fTransaction.commit();
            }
        };
        rg_tab_bar.setOnCheckedChangeListener(Listener);
        //获取第一个单选按钮，并设置其为选中状态
        rb_channel = (RadioButton) findViewById(R.id.rb_channel1);
        rb_channel.setChecked(true);
    }


    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }
}
