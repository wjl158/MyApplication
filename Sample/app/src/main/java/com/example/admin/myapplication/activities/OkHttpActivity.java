package com.example.admin.myapplication.activities;

import android.app.DownloadManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.File;
import java.io.IOException;

public class OkHttpActivity extends AppCompatActivity {

    String htmlStr;
    static final String url = "https://www.baidu.com";
    // 用于刷新界面
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    TextView tv = (TextView)findViewById(R.id.tv_actiity_ok_http_text);
                    tv.setText(htmlStr);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        Button btnGet = (Button)findViewById(R.id.btn_actiity_ok_http_get);
        Button btnPost = (Button)findViewById(R.id.btn_actiity_ok_http_post);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.btn_actiity_ok_http_post:
                        post();
                        break;
                    case R.id.btn_actiity_ok_http_get:
                        get();
                        break;
                    default:
                        ;
                }
            }
        };

        btnGet.setOnClickListener(listener);
        btnPost.setOnClickListener(listener);


    }


    public void get()
    {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();

        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();

        //new call
        Call call = mOkHttpClient.newCall(request);

        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                htmlStr =  response.body().string();
                handler.sendEmptyMessage(0x001);


//                runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        TextView tv = (TextView)findViewById(R.id.tv_actiity_ok_http_1);
//                        tv.setText(htmlStr);
//                    }
//
//                });

            }
        });
    }

    public void post()
    {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();

//        Request request = buildMultipartFormRequest(
//                url, new File[]{file}, new String[]{fileKey}, null);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username","张鸿洋");

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Request request, IOException e)
            {
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                htmlStr =  response.body().string();
                handler.sendEmptyMessage(0x001);
            }
        });
    }
}
