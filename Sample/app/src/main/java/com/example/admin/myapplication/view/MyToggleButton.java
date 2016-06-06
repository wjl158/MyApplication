package com.example.admin.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.myapplication.R;

/*
 * 自定义view的几个步骤：
 * 1、首先需要写一个类来继承自View
 * 2、需要得到view的对象，那么需要重写构造方法，其中一参的构造方法用于new，二参的构造方法用于xml布局文件使用，三参的构造方法可以传入一个样式
 * 3、需要设置view的大小，那么需要重写onMeasure方法
 * 4、需要设置view的位置，那么需要重写onLayout方法，但是这个方法在自定义view的时候用的不多，原因主要在于view的位置主要是由父控件来决定
 * 5、需要绘制出所需要显示的view，那么需要重写onDraw方法
 * 6、当控件状态改变的时候，需要重绘view，那么调用invalidate();方法，这个方法实际上会重新调用onDraw方法
 * 7、在这其中，如果需要对view设置点击事件，可以直接调用setOnClickListener方法
 */

public class MyToggleButton extends View {

    /**
     * 是拖拽还是点击
     */
    private boolean isDrag;

    /**
     * 第一次的位置
     */
    private int firstX;

    /**
     * 第二次的位置
     */
    private int secondX;


    final private  int MAX_LEFT_DISTANCE = 274;


    /**
     * 开关按钮的背景
     */
    private Bitmap backgroundBitmap;
    /**
     * 开关按钮的滑动部分
     */
    private Bitmap slideButton;
    /**
     * 滑动按钮的左边界
     */
    private float slideBtn_left;
    /**
     * 当前开关的状态
     */
    private boolean currentState = false;

    /**
     * 在代码里面创建对象的时候，使用此构造方法
     *
     * @param context
     */
    public MyToggleButton(Context context) {
        super(context);
    }

    /**
     * 在布局文件中声明的view，创建时由系统自动调用
     *
     * @param context
     * @param attrs
     */
    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 测量尺寸时的回调方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置当前view的大小 width:view的宽，单位都是像素值 heigth:view的高，单位都是像素值
        setMeasuredDimension(backgroundBitmap.getWidth(),
                backgroundBitmap.getHeight());
    }

    // 这个方法对于自定义view的时候帮助不大，因为view的位置一般由父组件来决定的
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 画view的方法,绘制当前view的内容
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        Paint paint = new Paint();
        // 打开抗锯齿
        paint.setAntiAlias(true);

        // 画背景
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        // 画滑块
        canvas.drawBitmap(slideButton, slideBtn_left, 0, paint);
    }

    /**
     * 初始化view
     */
    private void initView() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.est);
        slideButton = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg_num);

        /*
         * 点击事件
         */
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isDrag) {
                    // 如果不是拖动事件，才进行点击事件的响应操作
                    currentState = !currentState;
                    flushState();
                    flushView();
                } else {
                    // 如果是拖动事件，则不进行点击事件的响应操作
                }
            }
        });
    }

    /**
     * 刷新视图
     */
    protected void flushView() {
        // 刷新当前view会导致ondraw方法的执行
        invalidate();
    }

    /**
     * 刷新当前的状态
     */
    protected void flushState() {
        if (currentState) {
            slideBtn_left = backgroundBitmap.getWidth()
                    - slideButton.getWidth();
        } else {
            slideBtn_left = 0;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        super.onTouchEvent(event);// 这一句不能少，否则无法触发onclick事件


        /*
            public static final int ACTION_DOWN             = 0;单点触摸动作
            public static final int ACTION_UP               = 1;单点触摸离开动作
            public static final int ACTION_MOVE             = 2;触摸点移动动作
            public static final int ACTION_CANCEL           = 3;触摸动作取消
            public static final int ACTION_OUTSIDE          = 4;触摸动作超出边界
            public static final int ACTION_POINTER_DOWN     = 5;多点触摸动作
            public static final int ACTION_POINTER_UP       = 6;多点离开动作
            以下是一些非touch事件
            public static final int ACTION_HOVER_MOVE       = 7;
            public static final int ACTION_SCROLL           = 8;
            public static final int ACTION_HOVER_ENTER      = 9;
            public static final int ACTION_HOVER_EXIT       = 10;

            掩码常量

            ACTION_MASK = 0X000000ff
            动作掩码
             ACTION_POINTER_INDEX_MASK = 0X0000ff00
            触摸点索引掩码

            ACTION_POINTER_INDEX_SHIFT = 8 获取触摸点索引需要移动的位数
        */


        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:
                // 当按下的时候
                isDrag = false;
                firstX = secondX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 当移动的时候
                isDrag = true;
                // 计算手指在屏幕上移动的距离
                int disX = (int) (event.getX() - secondX);
                secondX = (int) event.getX();

                Log.e("1", "onTouchEvent: " + disX);


                // 更新slideBtn_left的大小
                slideBtn_left = slideBtn_left + disX;


                // 做一个判断，防止滑块划出边界，滑块的范围应该是在[0,MAX_LEFT_DISTANCE];
                if (slideBtn_left < 0) {
                    slideBtn_left = 0;
                } else {
                    if (slideBtn_left > MAX_LEFT_DISTANCE) {
                        slideBtn_left = MAX_LEFT_DISTANCE;
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
                // 当抬起的时候


                // 抬起的时候，判断松开的位置是哪里，来由此来决定开关的状态是打开还是关闭
                if (slideBtn_left < MAX_LEFT_DISTANCE / 2) {
                    currentState = false;
                } else if (slideBtn_left >= MAX_LEFT_DISTANCE / 2) {
                    currentState = true;
                }


                // 由开关的状态标志，确定应该是打开还是关闭状态
                flushState();
                break;
        }


        // 根据状态标志来刷新对应的滑块停止位置，从而实现打开或者关闭效果
        flushView();


        // 返回true意味着消费掉本次事件，不让其他控件还可以接收到这个事件
        return true;
    }




}