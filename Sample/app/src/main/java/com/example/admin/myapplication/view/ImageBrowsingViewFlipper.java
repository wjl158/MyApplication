package com.example.admin.myapplication.view;

        import android.content.Context;
        import android.graphics.drawable.Drawable;
        import android.os.AsyncTask;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewTreeObserver;
        import android.view.animation.Animation;
        import android.widget.ImageView;
        import android.widget.ViewFlipper;

/**
 * 图片浏览类
 * @author sunday
 * 2014-1-13
 * zhengchao1937@163.com   http://blog.csdn.net/ff20081528/article/details/18089883
 * qq:804935743
 */
public class ImageBrowsingViewFlipper extends ViewFlipper {
    private final static String TAG = "ImageBrowsingView";

    /**
     * VIEW滚动时的默认速度
     */
    private final static int DEFAULT_CROLL_SPEED = 30;

    /**
     * View滚动时，让线程休眠时间，目的让用户的眼睛可以看到图片滚动的效果
     */
    private final static int SLEEP_TIME = 20;

    /**
     * 图片资源
     */
    private Drawable[] imgsDraw;

    private Context mContext;

    /**
     * VIEW滚动时的速度，默认值为DEFAULT_CROLL_SPEED
     */
    private int crollSpeed = DEFAULT_CROLL_SPEED;

    /**
     * 记录手指按下时的X轴的坐标
     */
    private float xDown;

    /**
     * 记录手指移动的时候的X轴的坐标
     */
    private float xMove;

    /**
     * 记录手指抬起时的X轴的坐标
     */
    private float xUp;

    /**
     * ViewFlipperde的宽度
     */
    private int vfWidth;

    /**
     * 图片的数量
     */
    private int imgsLen;
    /**
     * 当前显示的子View
     */
    private ImageView ivCurr;

    /**
     * 下一个将要显示的子View
     */
    private ImageView ivNext;

    /**
     * 之前已经显示过的子View
     */
    private ImageView ivLast;

    /**
     * 当前子View的位置
     */
    private int currViewPosition;

    /**
     * 回调接口
     */
    private IImageBrowsingMark mImgBrowsingMark;

    /**
     * ViewFlipper子View切换时将要显示的View的进入动画
     */
    private Animation lInAnim;

    /**
     * ViewFlipper子View切换时当前显示的View的退出动画
     */
    private Animation lOutAnim;

    public ImageBrowsingViewFlipper(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ImageBrowsingViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 设置View滚动时的速度
     * @param crollSpeed
     */
    public void setCrollSpeed(int crollSpeed) {
        this.crollSpeed = crollSpeed;
    }

    public int getVfWidth() {
        return vfWidth;
    }

    public void setImgsDraw(Drawable[] imgsDraw) {
        this.imgsDraw = imgsDraw;
        initImages();
    }

    public void setVfWidth(int vfWidth) {
        this.vfWidth = vfWidth;
    }

    public IImageBrowsingMark getmImgBrowsingMark() {
        return mImgBrowsingMark;
    }

    public void setmImgBrowsingMark(IImageBrowsingMark mImgBrowsingMark) {
        this.mImgBrowsingMark = mImgBrowsingMark;
    }

    private void init() {
        //视图树观察者,用于获取ViewFlipper的宽度
        ViewTreeObserver vto = getViewTreeObserver();
        //当一个视图树将要绘制时，所要调用的回调函数的接口类
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int height = ImageBrowsingViewFlipper.this.getMeasuredHeight();
                int width =ImageBrowsingViewFlipper.this.getMeasuredWidth();
                Log.e(TAG, "height = " + height + " ; width = " + width);
                vfWidth = width;
                return true;
            }
        });
    }

    /**
     * 初始化Image
     */
    private void initImages() {
        imgsLen = imgsDraw.length;
        // 添加图片源
        for (int i = 0; i < imgsLen; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setOnTouchListener(onTouchListener);
            iv.setImageDrawable(imgsDraw[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(iv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }
    }

    /**
     * 停止滑动和动画并保存动画
     */
    private void stopAndSaveAnimation() {
        ImageBrowsingViewFlipper.this.stopFlipping();
        if(lInAnim == null && lOutAnim == null) {
            lInAnim = ImageBrowsingViewFlipper.this.getInAnimation();
            lOutAnim = ImageBrowsingViewFlipper.this.getOutAnimation();
        }
        ImageBrowsingViewFlipper.this.setInAnimation(null);
        ImageBrowsingViewFlipper.this.setOutAnimation(null);
    }

    /**
     * 开启滑动并使用动画
     */
    private void startAndUseAnimation() {
        ImageBrowsingViewFlipper.this.startFlipping();
        ImageBrowsingViewFlipper.this.setInAnimation(lInAnim);
        ImageBrowsingViewFlipper.this.setOutAnimation(lOutAnim);
    }

    /**
     * 实现OnTouchListener事件
     */
    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                //手指按下时的逻辑
                case MotionEvent.ACTION_DOWN:
                    Log.e(TAG, "MotionEvent.ACTION_DOWN" );
                    xDown = event.getRawX();
                    stopAndSaveAnimation();
                    break;
                //手指移动时的逻辑
                case MotionEvent.ACTION_MOVE:
                    Log.e(TAG, "ACTION_MOVE" );
                    xMove = event.getRawX();
                    //手指滑动的距离
                    int xDistance = (int)(xMove - xDown);
                    Log.e(TAG, "-----------------  xDown = " + xDown + "; xMove = " + xMove);
                    //当前显示的ImageView
                    ImageView ivCurr = (ImageView) ImageBrowsingViewFlipper.this.getCurrentView();
                    currViewPosition = ImageBrowsingViewFlipper.this.getDisplayedChild();
                    //下一个ImageView
                    ImageView ivNext = getNextView(currViewPosition);
                    //上一个ImageView
                    ImageView ivLast = getLastView(currViewPosition);

                    //在ViewFlipper工作机制中，某一个时间点只有当前的子View是VISIBLE状态，其他的子View都是GONE状态。
                    //所以要在滑动的时候看见他们，必须将他们设置为VISIBLE状态
                    ivNext.setVisibility(View.VISIBLE);
                    ivLast.setVisibility(View.VISIBLE);
                    //将当前的ImageView移动到这个(-xDistance, 0)位置
                    ivCurr.scrollTo(-xDistance, 0);

                    //将下一个ImageView移动到(xNext, 0)位置
                    int xNext = - vfWidth - xDistance;
                    Log.e(TAG, "xNext = " + xNext);
                    ivNext.scrollTo(xNext, 0);
                    //将上一个ImageView移动到(xLast, 0)位置
                    int xLast = vfWidth - xDistance;
                    Log.e(TAG, "xLast = " + xLast);
                    ivLast.scrollTo(xLast, 0);
                    break;
                //手指抬起时的逻辑
                case MotionEvent.ACTION_UP:
                    Log.e(TAG, "ACTION_UP" );
                    xUp = event.getRawX();
                    //判断用户手指的意图，这块可以自己改写逻辑
                    if(wantToNext()) {
                        Log.e(TAG, "wantToNext-------------");
                        new ScrollToNextTask().execute(crollSpeed);
                    } else if(wantToLast()) {
                        Log.e(TAG, "wantToLast-------------");
                        new ScrollToLastTask().execute(crollSpeed);
                    } else {
                        new ScrollToLastTask().execute(crollSpeed);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    /**
     *  判断当前手势的意图是不是想显示上一个子View
     * @return 当前手势想移动到上一个则返回true，否则返回false。
     */
    protected boolean wantToLast() {
        return xUp - xDown > 0;
    }

    /**
     *  判断当前手势的意图是不是想显示下一个子View
     * @return 当前手势想移动到下一个则返回true，否则返回false。
     */
    protected boolean wantToNext() {
        return xUp - xDown < 0;
    }

    /**
     * 得到上一个子视图的ImageView对象
     * @param currViewPosition 当前View的位置
     * @return 子视图对象
     */
    private ImageView getLastView(int currViewPosition) {
        int lastViewPosition = currViewPosition - 1;
        if(currViewPosition == 0) {
            lastViewPosition = imgsLen - 1;
        }
        Log.e(TAG, "lastViewPosition = " + lastViewPosition);
        return (ImageView) this.getChildAt(lastViewPosition);
    }

    /**
     * 得到下一个子视图的ImageView对象
     * @param currViewPosition 当前View的位置
     * @return 子视图对象
     */
    private ImageView getNextView(int currViewPosition) {
        int nextViewPosition = currViewPosition + 1;
        if(currViewPosition == imgsLen - 1) {
            nextViewPosition = 0;
        }
        Log.e(TAG, "nextViewPosition = " + nextViewPosition);
        return (ImageView) this.getChildAt(nextViewPosition);
    }

    /**
     * 意图显示下一个子View
     * @author sunday
     *
     */
    class ScrollToNextTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... speed) {
            ivCurr = (ImageView) ImageBrowsingViewFlipper.this.getCurrentView();
            int currViewPosition = ImageBrowsingViewFlipper.this.getDisplayedChild();
            ivNext = getNextView(currViewPosition);
            ivLast = getLastView(currViewPosition);

            int xDistance = Math.abs((int)(xUp - xDown));
            while (true) {
                xDistance = xDistance + speed[0];
                if(xDistance > vfWidth) {
                    xDistance = vfWidth;
                    break;
                }
                publishProgress(xDistance);
                sleep(SLEEP_TIME);
            }
            return xDistance;
        }
        @Override
        protected void onProgressUpdate(Integer... xDistance) {
            int xNext = - vfWidth + xDistance[0];
            Log.e(TAG, "xNext = " + xNext);
            ivCurr.scrollTo(xDistance[0], 0);
            ivNext.scrollTo(xNext, 0);
        }
        @Override
        protected void onPostExecute(Integer xDistance) {
            int xNext = - vfWidth + xDistance;
            Log.e(TAG, "xNext = " + xNext);
            ivCurr.scrollTo(xDistance, 0);
            ivNext.scrollTo(xNext, 0);
            int currPosition = displayedNextChild(currViewPosition);
            ImageBrowsingViewFlipper.this.setDisplayedChild(currPosition);
            ivNext.setVisibility(View.VISIBLE);
            if(null != mImgBrowsingMark.getMarkView())
                mImgBrowsingMark.getMarkView().setMark(currPosition);
            ivCurr.setVisibility(View.GONE);
            ivLast.setVisibility(View.GONE);
            //将当前（移动发生之前）的ImageView移动到（0，0）位置因为在滑动时它的位置被改变
            ivCurr.scrollTo(0, 0);
            //将上一个（移动发生之前）的ImageView移动到（0，0）位置
            ivLast.scrollTo(0, 0);
            startAndUseAnimation();
        }
    }

    /**
     * 意图显示上一个子View
     * @author sunday
     *
     */
    class ScrollToLastTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... speed) {
            ivCurr = (ImageView) ImageBrowsingViewFlipper.this.getCurrentView();
            int currViewPosition = ImageBrowsingViewFlipper.this.getDisplayedChild();
            ivNext = getNextView(currViewPosition);
            ivLast = getLastView(currViewPosition);
            int xDistance = Math.abs((int)(xUp - xDown));
            while (true) {
                xDistance = xDistance + speed[0];
                if(xDistance > vfWidth) {
                    xDistance = vfWidth;
                    break;
                }
                publishProgress(xDistance);
                sleep(SLEEP_TIME);
            }
            return xDistance;
        }
        @Override
        protected void onProgressUpdate(Integer... xDistance) {
            int xLast = vfWidth - xDistance[0];
            Log.e(TAG, "xLast = " + xLast);
            ivCurr.scrollTo(-xDistance[0], 0);
            ivLast.scrollTo(xLast, 0);
        }
        @Override
        protected void onPostExecute(Integer xDistance) {
            int xLast = vfWidth - xDistance;
            Log.e(TAG, "xLast = " + xLast);
            ivCurr.scrollTo(-xDistance, 0);
            ivLast.scrollTo(xLast, 0);
            int currPosition = displayedLastChild(currViewPosition);
            ImageBrowsingViewFlipper.this.setDisplayedChild(currPosition);
            ivLast.setVisibility(View.VISIBLE);
            if(null != mImgBrowsingMark.getMarkView())
                mImgBrowsingMark.getMarkView().setMark(currPosition);
            ivCurr.setVisibility(View.GONE);
            ivNext.setVisibility(View.GONE);
            ivCurr.scrollTo(0, 0);
            ivNext.scrollTo(0, 0);
            startAndUseAnimation();
        }
    }

    /**
     * 视图向左滑动将要显示的子视图的位置
     * @param currViewPosition
     * @return
     */
    private int displayedNextChild(int currViewPosition) {
        int nextViewPosition = currViewPosition + 1;
        if(currViewPosition == imgsLen - 1) {
            nextViewPosition = 0;
        }
        return nextViewPosition;
    }

    /**
     * 视图向右滑动将要显示的子视图的位置
     * @param currViewPosition
     * @return
     */
    private int displayedLastChild(int currViewPosition) {
        int lastViewPosition = currViewPosition - 1;
        if(currViewPosition == 0) {
            lastViewPosition = imgsLen - 1;
        }
        return lastViewPosition;
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millis
     *            指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  解决Receiver not registered: android.widget.ViewFlipper问题，在android2.1,2.2上
     */
    @Override
    protected void onDetachedFromWindow () {
        try {
            super.onDetachedFromWindow();
        }	catch (IllegalArgumentException e) {
            stopFlipping();
        }
    }

    /**
     * 重写showNext()方法，用于实现图片自动切换时，图片的指示标也跟着切换
     */
    @Override
    public void showNext() {
        super.showNext();
        if(null != mImgBrowsingMark.getMarkView())
            mImgBrowsingMark.getMarkView().setMark(getDisplayedChild());
    }

    /**
     * 图片浏览指示标的回调接口
     * @author sunday
     *
     */
    public interface IImageBrowsingMark {
        public MarkView getMarkView();
    }
}
