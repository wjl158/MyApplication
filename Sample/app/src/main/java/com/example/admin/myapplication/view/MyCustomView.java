package com.example.admin.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.admin.myapplication.R;

/**
 * Created by admin on 2016/05/23.
 */
public class MyCustomView extends LinearLayout {

    /**
     * 下拉头的View
     */
    private View header;

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
        setOrientation(VERTICAL);

        //动态添加View
        addView(header, 0);
    }

}
