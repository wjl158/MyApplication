package com.example.admin.myapplication.activities;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.adapter.MyPagerAdapter;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

import java.util.ArrayList;

public class ViewPager2Activity extends BaseActivity {
    private ViewPager vpager_four;
    private ImageView img_cursor;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        initViews();
    }


    private void initViews() {
        vpager_four = (ViewPager) findViewById(R.id.vpager_four);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        img_cursor = (ImageView) findViewById(R.id.img_cursor);  //滑动条

        //下划线动画的相关设置：
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpWidth) / 2;// 计算偏移量

        //其实这几句并没有什么卵用
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置

        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        two = one * 2;// 移动两页的偏移量,比如1直接跳3


        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.activity_main, null, false));
        listViews.add(mInflater.inflate(R.layout.activity_spinner, null, false));
        listViews.add(mInflater.inflate(R.layout.activity_view_pager, null, false));
        vpager_four.setAdapter(new MyPagerAdapter(listViews));
        vpager_four.setCurrentItem(0);          //设置ViewPager当前页，从0开始算






        ViewPager.OnPageChangeListener MyListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        if (currIndex == 1) {
                            animation = new TranslateAnimation(one, 0, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(offset, one, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, one, 0, 0);
                        }
                        break;
                    case 2:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(offset, two, 0, 0);
                        } else if (currIndex == 1) {
                            animation = new TranslateAnimation(one, two, 0, 0);
                        }
                        break;
                }
                currIndex = position;
                animation.setFillAfter(true);// true表示图片停在动画结束位置
                animation.setDuration(300); //设置动画时间为300毫秒
                img_cursor.startAnimation(animation);//开始动画
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        vpager_four.addOnPageChangeListener(MyListener);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_one:
                        vpager_four.setCurrentItem(0);
                        break;
                    case R.id.tv_two:
                        vpager_four.setCurrentItem(1);
                        break;
                    case R.id.tv_three:
                        vpager_four.setCurrentItem(2);
                        break;
                }
            }
        };

        tv_one.setOnClickListener(listener);
        tv_two.setOnClickListener(listener);
        tv_three.setOnClickListener(listener);
    }




}
