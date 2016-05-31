package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class AnimActivity extends BaseActivity {
    ImageView img;
    LinearLayout mLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        img = (ImageView)findViewById(R.id.img_activity_anim_img);
        mLay = (LinearLayout)findViewById(R.id.linearlayout_activity_anim_1);
            /** 常用方法 */
            //animation.setRepeatCount(int repeatCount);//设置重复次数
            //animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
            //animation.setStartOffset(long startOffset);//执行前的等待时间



    }


    //位移动画
    public void trans(View v)
    {

        TranslateAnimation tranAnim = new TranslateAnimation(0, 100, 0, 100);
        tranAnim.setDuration(3000);
        tranAnim.setFillAfter(true);
        img.startAnimation(tranAnim);
    }

    //透明渐变
    public void alpha(View v)
    {
        //透明度动画
        AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1);
        alphaAnim.setDuration(3000);
        img.startAnimation(alphaAnim);

    }

    //旋转
    public void rotate(View v)
    {
        //                //以左上角为坐标0,0为中心点旋转180度
        //                Animation rotateAnimation = new RotateAnimation(0f, 180f);

        //                //以坐标pivotXValue,pivotYValue为中心点旋转180度
        //                Animation rotateAnimation = new RotateAnimation(0f, 180f, 176, 176);

        //以坐标pivotXValue * width,pivotYValue * width为中心点旋转360度
        Animation rotateAnimation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(1000);
        img.startAnimation(rotateAnimation);
    }

    //缩放
    public void scale(View v)
    {
        //////////////////ScaleAnimation动画参数///////////////////////////////////////
        //                float fromX 动画起始时 X坐标上的伸缩尺寸 这个表示缩放的比例。1表示原始宽度尺寸，0.5表示缩小了一半，2表示放大了1倍
        //                float toX 动画结束时 X坐标上的伸缩尺寸
        //                float fromY 动画起始时Y坐标上的伸缩尺寸
        //                float toY 动画结束时Y坐标上的伸缩尺寸
        //                int pivotXType 动画在X轴相对于物件位置类型 可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
        //                float pivotXValue 动画相对于物件的X坐标的开始位置
        //                int pivotYType 动画在Y轴相对于物件位置类型 可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
        //                float pivotYValue 动画相对于物件的Y坐标的开始位置

        //                pivotXType pivotYType有三种默认值：
        //
        //                RELATIVE_TO_PARENT 相对于父控件
        //
        //                RELATIVE_TO_SELF 相对于符自己
        //
        //                RELATIVE_TO_ABSOLUTE 绝对坐标
        ///////////////////////////////////////////////////////////////////////////////

        //                ScaleAnimation scaleAnim = new ScaleAnimation(0.5f, 1, 0.5f, 1);

        //                //表示从400,400的位置回到0，0的位置。这样就产生一个位移的效果
        //                ScaleAnimation scaleAnim = new ScaleAnimation(0.5f, 1, 0.5f, 1, 400f,400f);


        //左上角x坐标 = (1 - 0.5) * width * 2 = 1 * width 即1 倍宽度的位置，那原先x = 0 ,现在x= width
        ScaleAnimation scaleAnim = new ScaleAnimation(0.5f, 1, 1f, 1,
                Animation.RELATIVE_TO_SELF, 2f,
                Animation.RELATIVE_TO_SELF, 1f);
        scaleAnim.setDuration(3000);

        img.startAnimation(scaleAnim);
    }

    //动画集合
    public void more(View v) {

        TranslateAnimation tranAnim = new TranslateAnimation(0, 100, 0, 100);
        tranAnim.setDuration(3000);

        AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1);
        alphaAnim.setDuration(3000);

        ScaleAnimation scaleAnim = new ScaleAnimation(0.9f, 1, 1f, 1,
                Animation.RELATIVE_TO_SELF, 10f,
                Animation.RELATIVE_TO_SELF, 1f);
        scaleAnim.setDuration(3000);

        Animation rotateAnimation = new RotateAnimation(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);


        AnimationSet animSet = new AnimationSet(true);
        animSet.setFillAfter(true);//true 表示动画结束后保持最后的形态 false表示图像回到初始状态
        animSet.addAnimation(tranAnim);
        animSet.addAnimation(alphaAnim);
        animSet.addAnimation(scaleAnim);
        animSet.addAnimation(rotateAnimation);
        animSet.setRepeatCount(3);
        animSet.setDuration(3000);

        img.startAnimation(animSet);

        //取消动画
        //animSet.cancel();

    }

    //动画集合
    public void xmldonghua(View v) {

        //                在这些属性里面还可以加上%和p,例如:
        //                Android:toXDelta="100%",表示自身的100%,也就是从View自己的位置开始。
        //                android:toXDelta="80%p",表示父层View的80%,是以它父层View为参照的。

        //        android:duration="500"
        //        android:fromXDelta="50%p"  表示左上角x坐标 + %50的父控件宽度,
        //                                   如：当前X坐标为 50, 父控件宽度为100 那么
        //                                   result = 50 +  50%*100 = 100
        //        android:toXDelta="50" />  toXDelta表示相对于最初图片的位置的偏移 50表示移动完成后，左上角X的位置增加了50
        //上面表示100, 0移动到（初始的X）+50,0

//        android:fromXDelta="0" android:toXDelta="-100%p" 往左邊消失
//        android:fromXDelta="-100%p" android:toXDelta="0" 從左邊進
//        android:fromXDelta="0" android:toXDelta="100%p" 往右邊消失
//        android:fromXDelta="100%p" android:toXDelta="0" 從右邊進

        Animation  alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        alphaAnimation.setFillAfter(true);

        mLay.startAnimation(alphaAnimation);

    }
}
