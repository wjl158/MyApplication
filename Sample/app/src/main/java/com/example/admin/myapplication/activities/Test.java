package com.example.admin.myapplication.activities;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class Test extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView txtZQD = (TextView) findViewById(R.id.txtZQD);
        Drawable[] drawable = txtZQD.getCompoundDrawables();
        // 数组下表0~3,依次是:左上右下
        drawable[1].setBounds(0, 0, 50, 50);
        txtZQD.setCompoundDrawables(drawable[0], drawable[1], drawable[2],
                drawable[3]);
    }




    //    ①Drawable[] drawable = txtZQD.getCompoundDrawables( );
//    获得四个不同方向上的图片资源,数组元素依次是:左上右下的图片
//    ②drawable1.setBounds(100, 0, 200, 200);
//    接着获得资源后,可以调用setBounds设置左上右下坐标点,比如这里设置了代表的是:
//    长是:从离文字最左边开始100dp处到200dp处
//    宽是:从文字上方0dp处往上延伸200dp!
//            ③txtZQD.setCompoundDrawables(drawable[0], drawable1, drawable2,
//    drawable3);为TextView重新设置drawable数组!没有图片可以用null代替哦!
//    PS：另外，从上面看出我们也可以直接在Java代码中调用setCompoundDrawables为
//    TextView设置图片！


//    <TextView
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    android:drawableTop="@drawable/loading_13"   -----------------------这一句---------------------------
//    android:drawableLeft="@drawable/loading_12"
//    android:drawableRight="@drawable/loading_12"
//    android:drawableBottom="@drawable/loading_12"
//    android:drawablePadding="10dp"
//    android:text="张全蛋"
//    android:gravity="center"
//    android:id="@+id/txtZQD"
//    android:layout_alignParentTop="true"
//    android:layout_centerHorizontal="true"
//
//    android:layout_marginTop="71dp" />
}
