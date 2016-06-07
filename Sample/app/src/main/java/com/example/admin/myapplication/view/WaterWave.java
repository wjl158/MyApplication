package com.example.admin.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2016/06/07.
 */
public class WaterWave extends View {

    /**
     * 波形的List
     */
    private List<Wave> waveList;

    /**
     * 最大的不透明度，完全不透明
     */
    private static final int MAX_ALPHA = 255;

    protected static final int FLUSH_ALL = -1;

    private boolean isStart = true;

    // /**
    // * 按下的时候x坐标
    // */
    // private int xDown;
    // /**
    // * 按下的时候y的坐标
    // */
    // private int yDown;
    // /**
    // * 用来表示圆环的半径
    // */
    // private float radius;
    // private int alpha;

    /*
     * 1、两参构造函数
     */
    public WaterWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        waveList = Collections.synchronizedList(new ArrayList<Wave>());
    }

    /**
     * onMeasure方法，确定控件大小，这里使用默认的
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    /**
     * 画出需要的图形的方法，这个方法比较关键
     */
    protected void onDraw(Canvas canvas) {
        // 重绘所有圆环
        for (int i = 0; i < waveList.size(); i++) {
            Wave wave = waveList.get(i);
            canvas.drawCircle(wave.xDown, wave.yDown, wave.radius, wave.paint);
        }

    }

    /**
     * 初始化paint
     */
    private Paint initPaint(int alpha, float width) {
        /*
         * 新建一个画笔
         */
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStrokeWidth(width);

        // 设置是环形方式绘制
        paint.setStyle(Paint.Style.STROKE);

        // System.out.println("alpha=" + alpha);
        paint.setAlpha(alpha);
        // System.out.println("得到的透明度：" + paint.getAlpha());

        paint.setColor(Color.RED);
        return paint;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    flushState();

                    invalidate();

                    if (waveList != null && waveList.size() > 0) {
                        handler.sendEmptyMessageDelayed(0, 50);
                    }

                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 刷新状态
     */
    private void flushState() {
        for (int i = 0; i < waveList.size(); i++) {
            Wave wave = waveList.get(i);
            if (isStart == false && wave.alpha == 0) {
                waveList.remove(i);
                wave.paint = null;
                wave = null;
                continue;
            } else if (isStart == true) {
                isStart = false;
            }
            wave.radius += 5;
            wave.alpha -= 10;
            if (wave.alpha < 0) {
                wave.alpha = 0;
            }
            wave.width = wave.radius / 4;
            wave.paint.setAlpha(wave.alpha);
            wave.paint.setStrokeWidth(wave.width);
        }

    }

    // private Paint paint;
    // private float width;

    @Override
    /**
     * 触摸事件的方法
     */
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Wave wave = new Wave();
                wave.radius = 0;
                wave.alpha = MAX_ALPHA;
                wave.width = wave.radius / 4;
                wave.xDown = (int) event.getX();
                wave.yDown = (int) event.getY();
                wave.paint = initPaint(wave.alpha, wave.width);
                if (waveList.size() == 0) {
                    isStart = true;
                }
                System.out.println("isStart=" + isStart);
                waveList.add(wave);
                // 点击之后刷洗一次图形
                invalidate();
                if (isStart) {
                    handler.sendEmptyMessage(0);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }

        return true;
    }

    private class Wave {
        int waveX;
        int waveY;
        /**
         * 用来表示圆环的半径
         */
        float radius;
        Paint paint;
        /**
         * 按下的时候x坐标
         */
        int xDown;
        /**
         * 按下的时候y的坐标
         */
        int yDown;
        float width;
        int alpha;
    }

}