package com.example.admin.myapplication.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        EditText edtText = (EditText)findViewById(R.id.edtText);
//        edtText.setText(edtText.toString());
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // 设置Intent的源地址和目标地址
                intent.setClass(MainActivity.this, ArrayAdapterDemo.class);
                //Intent可以通过Bundle进行数据的传递
                Bundle bundle = new Bundle();
                bundle.putString("name", "chenzheng_java");
                bundle.putInt("age", 23);
                intent.putExtras(bundle);
                // 调用startActivity方法发送意图给系统
                startActivity(intent);
                //关闭当前activity，添加了该语句后，用户通过点击返回键是无法返回该activity的
                //MainActivity.this.finish();

            }
        });


        Log.i("OnCreate", "1");
        Log.i("OnCreate", "2");
        Log.i("OnCreate", "3");
        Log.i("OnCreate", "4");


        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SimpleCursorAdapterDemo.class);
                startActivity(intent);
            }
        });

        Button btnTest = (Button)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TestDemo.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnTestNew = (Button)findViewById(R.id.btnTestNew);
        btnTestNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Test.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnTestNew1 = (Button)findViewById(R.id.btnTestNew1);
        btnTestNew1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TextviewTest.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnTestNew2= (Button)findViewById(R.id.btnTestNew2);
        btnTestNew2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Textview1Test.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });


        Button btnBaseLv= (Button)findViewById(R.id.btnBaseLv);
        btnBaseLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BaseAdapterDemo.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnUpdateLV= (Button)findViewById(R.id.btnUpdateLV);
        btnUpdateLV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UpdateListView.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnGridView= (Button)findViewById(R.id.btnGridView);
        btnGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GridViewActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnExpand= (Button)findViewById(R.id.btnExpand);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ExpandableListActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnSpinner= (Button)findViewById(R.id.btnSpinner);
        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SpinnerActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnFlipper= (Button)findViewById(R.id.btnFlipper);
        btnFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyFlipperActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnFlipper0= (Button)findViewById(R.id.btnFlipper0);
        btnFlipper0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyFlipper1Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnFlipper2= (Button)findViewById(R.id.btnFlipper2);
        btnFlipper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyFlipper2Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnViewPager= (Button)findViewById(R.id.btnViewPager);
        btnViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ViewPagerActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnViewPager1= (Button)findViewById(R.id.btnViewPager1);
        btnViewPager1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ViewPager1Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnViewPager2= (Button)findViewById(R.id.btnViewPager2);
        btnViewPager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ViewPager2Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnFragment1= (Button)findViewById(R.id.btnFragment1);
        btnFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Fragment1Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnFragment2= (Button)findViewById(R.id.btnFragment2);
        btnFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Fragment2Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });



        Button btnFragment3= (Button)findViewById(R.id.btnFragment3);
        btnFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Fragment3Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });


        Button btnFragment4= (Button)findViewById(R.id.btnFragment4);
        btnFragment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Fragment4Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnSnackbar= (Button)findViewById(R.id.btnSnackbar);
        btnSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "真要点我?", Snackbar.LENGTH_LONG)
                        .setAction("真的!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "你真点我了！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });




        FloatingActionButton btnFloatAct= (FloatingActionButton)findViewById(R.id.btnFloatAct);
        btnFloatAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "真要点我?", Snackbar.LENGTH_LONG)
                        .setAction("真的!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "你真点我了！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        FloatingActionButton btnFloatAct1= (FloatingActionButton)findViewById(R.id.btnFloatAct1);
        btnFloatAct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "真要点我?", Snackbar.LENGTH_LONG)
                        .setAction("真的!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "你真点我了！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        Button btnTabLayout= (Button)findViewById(R.id.btnTabLayout);
        btnTabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TabLayoutActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });


        Button btnZD= (Button)findViewById(R.id.btnZD);
        btnZD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CoordinatorLayoutActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });


        Button btnRecycler= (Button)findViewById(R.id.btnRecycler);
        btnRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RecyclerViewActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnToolbar= (Button)findViewById(R.id.btnToolbar);
        btnToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ToolbarActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });

        Button btnDrawble= (Button)findViewById(R.id.btnDrawble);
        btnDrawble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DrawbleDemoActivity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });
        Button btnDrawble1= (Button)findViewById(R.id.btnDrawble1);
        btnDrawble1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DrawbleDemo1Activity.class);
                intent.putExtra("abc", "12345566");
                startActivity(intent);
            }
        });
        //单击事件
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.btnTengWangGe:
                        showTengWangGe();
                        break;
                    case R.id.imgBtn:
                         imgBtnClick(v);
                        break;
                    case R.id.imgV:
                        imgVClick(v);
                        break;
                    case R.id.chk1:
                        chk1Click(v);
                        break;
                    case R.id.chk2:
                        chk2Click(v);
                        break;
                    default:
                        ;

                }
            }
        };
        findViewById(R.id.btnTengWangGe).setOnClickListener(listener);
//        findViewById(R.id.imgBtn).setOnClickListener(listener);
        findViewById(R.id.imgV).setOnClickListener(listener);
        findViewById(R.id.chk1).setOnClickListener(listener);
        findViewById(R.id.chk2).setOnClickListener(listener);


        //键盘事件
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (v.getId())
                {
                    case R.id.edtToast:
                        return edtToastKeyDown(v, keyCode, event);
                    default:
                        break;
                }
                return false;
            }
        };

        findViewById(R.id.edtToast).setOnKeyListener(keyListener);


        //触摸事件
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId())
                {
                    case R.id.imgBtn:
                        return imgBtnTouch(v, event);
                    case R.id.imgV:
                        return imgVTouch(v, event);
                }
                return false;
            }
        };

        findViewById(R.id.imgBtn).setOnTouchListener(touchListener);
//        findViewById(R.id.imgV).setOnTouchListener(touchListener);

    }

    public void showTestDemo(){
        Intent intent = new Intent();
        intent.setClass(this, TestDemo.class);
        startActivity(intent);
    }

    public void showTengWangGe(){
        Intent intent = new Intent();
        intent.setClass(this, MyTextView.class);
        startActivity(intent);

    }

    public boolean edtToastKeyDown(View v, int keyCode, KeyEvent event){
        if(event.getAction()== KeyEvent.ACTION_DOWN && keyCode== KeyEvent.KEYCODE_ENTER){
            //按住把EditView中的文版显示在吐司消息中
            Toast.makeText(this, ((EditText)v).getText(),
                    Toast.LENGTH_SHORT).show();
            //返回true说明你已经处理了这个事件并且它应该就此终止，如果返回false就表示此事件还需要继续传递下去
            return true;
        }

        return false;

    }


    public boolean imgBtnTouch(View v, MotionEvent event){
        Toast.makeText(getApplicationContext(), "触摸"+((ImageView)v).getId(),
                Toast.LENGTH_SHORT).show();
        return false;

    }
    public boolean imgVTouch(View v, MotionEvent event){
        Toast.makeText(getApplicationContext(), "触摸"+((ImageView)v).getId(),
                Toast.LENGTH_SHORT).show();
        return false;

    }

    public void imgBtnClick(View v){
        Toast.makeText(getApplicationContext(), "点击"+((ImageView)v).getId(),
                Toast.LENGTH_SHORT).show();
    }

    public void imgVClick(View v){
        Toast.makeText(getApplicationContext(), "点击"+((ImageView)v).getId(),
                Toast.LENGTH_SHORT).show();
    }

    public void chk1Click(View v){
        if (((CheckBox)v).isChecked()) {
            Toast.makeText(this, ((CheckBox)v).getText(), Toast.LENGTH_SHORT).show();
        }
    }
    public void chk2Click(View v){
        if (((CheckBox)v).isChecked()) {
            Toast.makeText(this, ((CheckBox)v).getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
