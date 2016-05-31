package com.example.admin.myapplication.activities;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.view.ImageBrowsingViewFlipper;
import com.example.admin.myapplication.view.ImageBrowsingViewFlipper.IImageBrowsingMark;
import com.example.admin.myapplication.view.MarkView;
import com.example.admin.myapplication.R;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MyFlipper2Activity extends BaseActivity {
    private MarkView markView;
    private Drawable[] imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imgs = new Drawable[5];
        imgs[0] =  getResources().getDrawable(R.drawable.img1);
        imgs[1] =  getResources().getDrawable(R.drawable.img2);
        imgs[2] =  getResources().getDrawable(R.drawable.img3);
        imgs[3] =  getResources().getDrawable(R.drawable.img4);
        imgs[4] =  getResources().getDrawable(R.drawable.img5);

        setContentView(R.layout.activity_my_flipper2);

        ImageBrowsingViewFlipper ibvf = (ImageBrowsingViewFlipper) findViewById(R.id.viewflipper);
        //设置图片浏览指示标接口
//        ibvf.setmImgBrowsingMark(this);
        ibvf.setmImgBrowsingMark(new IImageBrowsingMark() {
            @Override
            public MarkView getMarkView() {
                return markView;
            }
        });
        //设置图片
        ibvf.setImgsDraw(imgs);

        markView = (MarkView) findViewById(R.id.markView);
        markView.setMarkCount(imgs.length);
        //起始位置设置为0
        markView.setMark(0);

        // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
        Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
        Animation lOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_out);

        ibvf.setInAnimation(lInAnim);
        ibvf.setOutAnimation(lOutAnim);
        // 设置自动播放功能
        ibvf.setAutoStart(true);
        if(ibvf.isAutoStart() && !ibvf.isFlipping()){
            ibvf.startFlipping();
        }
    }

}
