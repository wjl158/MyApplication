package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.admin.myapplication.R;

public class LayoutInflateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflate);

        /**方法一
           LayoutInflater.from(this).inflate(R.layout.button, null);
        */

        /**方法二
          LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linelayout1);

          LayoutInflater.from(this).inflate(R.layout.button, linearLayout);
         */


        /**方法三  外层layout无效
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linelayout1);

        //当第二个参数为NULL时，这里R.layout.button里的LinearLayout
        View view =  LayoutInflater.from(this).inflate(R.layout.button, null, false);
        linearLayout.addView(view);
         */




        /**方法四 和方法二效果一样  外层layout有效
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linelayout1);

        //
        View view =  LayoutInflater.from(this).inflate(R.layout.button, linearLayout, false);
        linearLayout.addView(view);
         */


        /**方法五 和方法三效果一样 不报错 外层layout无效*/
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linelayout1);

        //当第二个参数为NULL时，这里R.layout.button里的LinearLayout
        View view =  LayoutInflater.from(this).inflate(R.layout.button, null, true);
        linearLayout.addView(view);



        //总结：View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
        // 如果root为null，attachToRoot将失去作用，设置任何值都没有意义。
        //root != null，attachToRoot == true；不用调用addView
        //root != null，attachToRoot == false；需要调用addView
        //root == null, attachToRoot == false；需要调用addView，外层layout无效
        //root == null, attachToRoot == true；需要调用addView，外层layout无效，效果同root == null, attachToRoot == false；


    }
}
