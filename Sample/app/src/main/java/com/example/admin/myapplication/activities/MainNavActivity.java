package com.example.admin.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.model.Icon;
import com.example.admin.myapplication.adapter.MyAdapter;
import com.example.admin.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class MainNavActivity extends BaseActivity {
    private Context mContext;
    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Icon> mData = null;

    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        Log.e("退后", "调用了 OnBackPressed");

        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.rv_mainnav_root), "再按一次退出程序", Snackbar.LENGTH_LONG).show();

            Snackbar snackbar =
                    Snackbar.make(findViewById(R.id.rv_mainnav_root), "再按一次退出程序", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar snackbar =
                                    Snackbar.make(findViewById(R.id.rv_mainnav_root), "确定", Snackbar.LENGTH_LONG);
                            setSnackbarMessageTextColor(snackbar, Color.parseColor("#FF0000"));
                            snackbar.show();
                        }
                    });
            setSnackbarMessageTextColor(snackbar, Color.parseColor("#FFFFFF"));
            snackbar.show();


            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            AppExit(this);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.e("错误", "调用了OnStop");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e("错误", "调用了 onPause");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.e("错误", "调用了 onRestart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e("错误", "调用了 onResume");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.e("错误", "调用了 onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // 隐藏上面状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Fresco.initialize(this);

        setContentView(R.layout.activity_main_nav);

//        //隐藏下面状态栏
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Log.e("错误", "调用了 onCreate");
        //1
        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.ic_my_01, "1、适配器1"));
        mData.add(new Icon(R.drawable.ic_my_02, "2、适配器2"));
        mData.add(new Icon(R.drawable.ic_my_03, "3、适配器3"));

        //2
        mData.add(new Icon(R.drawable.ic_my_04, "4、滕王阁序"));
        mData.add(new Icon(R.drawable.ic_my_05, "5、测试0"));
        mData.add(new Icon(R.drawable.ic_my_06, "6、测试1"));

        //3
        mData.add(new Icon(R.drawable.ic_my_07, "7、测试2"));
        mData.add(new Icon(R.drawable.ic_my_08, "8、列表"));
        mData.add(new Icon(R.drawable.ic_my_09, "9、折叠列表"));

        //4
        mData.add(new Icon(R.drawable.ic_my_10, "10、更新列表"));
        mData.add(new Icon(R.drawable.ic_my_11, "11、碎片0"));
        mData.add(new Icon(R.drawable.ic_my_12, "12、碎片1"));

        //5
        mData.add(new Icon(R.drawable.ic_my_13, "13、碎片2"));
        mData.add(new Icon(R.drawable.ic_my_14, "14、碎片3"));
        mData.add(new Icon(R.drawable.ic_my_15, "15、网格"));

        //6
        mData.add(new Icon(R.drawable.ic_my_16, "16、翻转视图0"));
        mData.add(new Icon(R.drawable.ic_my_17, "17、翻转视图1"));
        mData.add(new Icon(R.drawable.ic_my_18, "18、翻转视图2"));

        //7
        mData.add(new Icon(R.drawable.ic_my_19, "19、滑动视图0"));
        mData.add(new Icon(R.drawable.ic_my_20, "20、滑动视图1"));
        mData.add(new Icon(R.drawable.ic_my_21, "21、滑动视图2"));

        //8
        mData.add(new Icon(R.drawable.ic_my_22, "22、工具栏"));
        mData.add(new Icon(R.drawable.ic_my_23, "23、TabLayout"));
        mData.add(new Icon(R.drawable.ic_my_24, "24、折叠导航"));

        //9
        mData.add(new Icon(R.drawable.ic_my_25, "25、图片浏览RecylerView"));
        mData.add(new Icon(R.drawable.ic_my_26, "26、图形0"));
        mData.add(new Icon(R.drawable.ic_my_27, "27、图形1"));

        //10
        mData.add(new Icon(R.drawable.ic_my_28, "28、位图"));
        mData.add(new Icon(R.drawable.ic_my_28, "29、http"));
        mData.add(new Icon(R.drawable.ic_my_25, "30、对话框"));

        //11
        mData.add(new Icon(R.drawable.ic_my_01, "31、Popup菜单"));
        mData.add(new Icon(R.drawable.ic_my_01, "32、Menu菜单"));
        mData.add(new Icon(R.drawable.ic_my_02, "33、RecylerView"));

        //12
        mData.add(new Icon(R.drawable.ic_my_01, "34、OkHttp"));
        mData.add(new Icon(R.drawable.ic_my_03, "35、Fresco"));
        mData.add(new Icon(R.drawable.ic_my_05, "36、下拉刷新"));

        //13
        mData.add(new Icon(R.drawable.ic_my_06, "37、自定义View"));
        mData.add(new Icon(R.drawable.ic_my_06, "38、动画"));
        mData.add(new Icon(R.drawable.ic_my_06, "39、FrescoList"));

        //14
        mData.add(new Icon(R.drawable.ic_my_08, "40、ListView"));
        mData.add(new Icon(R.drawable.ic_my_08, "41、RxJava"));
        mData.add(new Icon(R.drawable.ic_my_08, "42、RxJava天气"));

        //15
        mData.add(new Icon(R.drawable.ic_my_18, "43、抽屉DrawerLayout"));
        mData.add(new Icon(R.drawable.ic_my_18, "44、图片轮播"));
        mData.add(new Icon(R.drawable.ic_my_19, "45、MD-FloatBtn"));

        //16
        mData.add(new Icon(R.drawable.ic_my_20, "46、MD-Toolbar"));
        mData.add(new Icon(R.drawable.ic_my_20, "47、MD-NavigationView"));
        mData.add(new Icon(R.drawable.ic_my_20, "48、MD-AppBarLayout"));

        //17
        mData.add(new Icon(R.drawable.ic_my_20, "49、MD-AppBarLayout1"));
        mData.add(new Icon(R.drawable.ic_my_20, "50、MD-TextInputLayout"));
        mData.add(new Icon(R.drawable.ic_my_01, "51、Retrofit图片上传"));

        //default
        mData.add(new Icon(R.drawable.ic_my_06, "n、其他"));


        mAdapter = new MyAdapter<Icon>(mData, R.layout.navtextlayout) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.nav_img_icon, obj.getiId());
                holder.setText(R.id.navtext, obj.getiName());
            }
        };

        grid_photo = (GridView)findViewById(R.id.navgridView);

        grid_photo.setAdapter(mAdapter);
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position)
                {

                    //1
                    case 0:
                        intent.setClass(MainNavActivity.this, ArrayAdapterDemo.class);
                        break;
                    case 1:
                        intent.setClass(MainNavActivity.this, SimpleCursorAdapterDemo.class);
                        break;
                    case 2:
                        intent.setClass(MainNavActivity.this, BaseAdapterDemo.class);
                        break;

                    //2
                    case 3:
                        intent.setClass(MainNavActivity.this, MyTextView.class);
                        break;
                    case 4:
                        intent.setClass(MainNavActivity.this, Test.class);
                        break;
                    case 5:
                        intent.setClass(MainNavActivity.this, TextviewTest.class);
                        break;

                    //3
                    case 6:
                        intent.setClass(MainNavActivity.this, Textview1Test.class);
                        break;
                    case 7:
                        intent.setClass(MainNavActivity.this, SpinnerActivity.class);
                        break;
                    case 8:
                        intent.setClass(MainNavActivity.this, ExpandableListActivity.class);
                        break;

                    //4
                    case 9:
                        intent.setClass(MainNavActivity.this, UpdateListView.class);
                        break;
                    case 10:
                        intent.setClass(MainNavActivity.this, Fragment1Activity.class);
                        break;
                    case 11:
                        intent.setClass(MainNavActivity.this, Fragment2Activity.class);
                        break;


                    //5
                    case 12:
                        intent.setClass(MainNavActivity.this, Fragment3Activity.class);
                        break;
                    case 13:
                        intent.setClass(MainNavActivity.this, Fragment4Activity.class);
                        break;
                    case 14:
                        intent.setClass(MainNavActivity.this, GridViewActivity.class);
                        break;

                    //6
                    case 15:
                        intent.setClass(MainNavActivity.this, MyFlipperActivity.class);
                        break;
                    case 16:
                        intent.setClass(MainNavActivity.this, MyFlipper1Activity.class);
                        break;
                    case 17:
                        intent.setClass(MainNavActivity.this, MyFlipper2Activity.class);
                        break;

                    //7
                    case 18:
                        intent.setClass(MainNavActivity.this, ViewPagerActivity.class);
                        break;
                    case 19:
                        intent.setClass(MainNavActivity.this, ViewPager1Activity.class);
                        break;
                    case 20:
                        intent.setClass(MainNavActivity.this, ViewPager2Activity.class);
                        break;


                    //8
                    case 21:
                        intent.setClass(MainNavActivity.this, ToolbarActivity.class);
                        break;
                    case 22:
                        intent.setClass(MainNavActivity.this, TabLayoutActivity.class);
                        break;
                    case 23:
                        intent.setClass(MainNavActivity.this, CoordinatorLayoutActivity.class);
                        break;

                    //9
                    case 24:
                        intent.setClass(MainNavActivity.this, RecyclerViewActivity.class);
                        break;
                    case 25:
                        intent.setClass(MainNavActivity.this, DrawbleDemoActivity.class);
                        break;
                    case 26:
                        intent.setClass(MainNavActivity.this, DrawbleDemo1Activity.class);
                        break;

                    //10
                    case 27:
                        intent.setClass(MainNavActivity.this, BitmapActivity.class);
                        break;
                    case 28:
                        intent.setClass(MainNavActivity.this, HttpActivity.class);
                        break;
                    case 29:
                        intent.setClass(MainNavActivity.this, AltDlgActivity.class);
                        break;

                    //11
                    case 30:
                        intent.setClass(MainNavActivity.this, PopupActivity.class);
                        break;
                    case 31:
                        intent.setClass(MainNavActivity.this, MenuActivity.class);
                        break;
                    case 32:
                        intent.setClass(MainNavActivity.this, RecyclerViewDemoActivity.class);
                        break;
                    //12
                    case 33:
                        intent.setClass(MainNavActivity.this, OkHttpActivity.class);
                        break;
                    case 34:
                        intent.setClass(MainNavActivity.this, FrescoDemoActivity.class);
                        break;
                    case 35:
                        intent.setClass(MainNavActivity.this, PullRefreshActivity.class);
                        break;

                    //13
                    case 36:
                        intent.setClass(MainNavActivity.this, CustomViewActivity.class);
                        break;
                    case 37:
                        intent.setClass(MainNavActivity.this, AnimActivity.class);
                        break;
                    case 38:
                        intent.setClass(MainNavActivity.this, FrescoListActivity.class);
                        break;

                    //14
                    case 39:
                        intent.setClass(MainNavActivity.this, FrescoList1Activity.class);
                        break;
                    case 40:
                        intent.setClass(MainNavActivity.this, RxJavaActivity.class);
                        break;
                    case 41:
                        intent.setClass(MainNavActivity.this, RxJavaWeatherActivity.class);
                        break;

                    //15
                    case 42:
                        intent.setClass(MainNavActivity.this, DrawerlayoutActivity.class);
                        break;
                    case 43:
                        intent.setClass(MainNavActivity.this, RollviewActivity.class);
                        break;
                    case 44:
                        intent.setClass(MainNavActivity.this, FloatingActionButtonActivity.class);
                        break;

                    //16
                    case 45:
                        intent.setClass(MainNavActivity.this, MDToolbarActivity.class);
                        break;
                    case 46:
                        intent.setClass(MainNavActivity.this, NavigationViewActivity.class);
                        break;
                    case 47:
                        intent.setClass(MainNavActivity.this, MDAppBarLayoutActivity.class);
                        break;

                    //17
                    case 48:
                        intent.setClass(MainNavActivity.this, MDAppBarLayout1Activity.class);
                        break;
                    case 49:
                        intent.setClass(MainNavActivity.this, TextInputLayoutActivity.class);
                        break;
                    case 50:
                        intent.setClass(MainNavActivity.this, RetrofitImgUploadActivity.class);
                        break;

                    default:
                        intent.setClass(MainNavActivity.this, MainActivity.class);
                }

                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });
    };


}
