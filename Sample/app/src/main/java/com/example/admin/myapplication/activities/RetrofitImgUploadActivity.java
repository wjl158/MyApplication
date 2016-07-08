package com.example.admin.myapplication.activities;

import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.HttpResponse;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * ThinkPHP服务器代码实现
 *  public function upload(){
         // 获取表单上传文件 例如上传了001.jpg
         Log::write($_FILES, '图片上传开始11111');
         $file = request()->file('image');  //区分大小写
         // 移动到框架应用根目录/public/uploads/ 目录下
         $info = $file->move(ROOT_PATH . 'public' . DS . 'uploads');

         if($info){
         // 成功上传后 获取上传信息
         echo $info->getExtension(); // 输出 jpg
         echo $info->getFilename(); // 输出 42a79759f284b767dfcb2a0197904287.jpg
         $Req = apache_request_headers();
         $s = '';
         while($color=each($Req)){
         $s = $s.$color['key'];
         }

         }else{
         // 上传失败获取错误信息
         echo $file->getError();
     }
 }
 */

public class RetrofitImgUploadActivity extends AppCompatActivity {

    public Button btnUpload;
    public Button btnUpload1;

    public interface BlogService {
        @GET("blog/{id}")
            //这里的{id} 表示是一个变量
        Call<String> getFirstBlog(/** 这里的id表示的是上面的{id} */@Path("id") int id);
    }

    public interface UploadImg {
        /**
         * 上传一张图片
         *
         * @param description
         * @param imgs
         * @return
         */
        @Multipart
        @POST("upload/")
        Call<String> uploadImage(@Part("fileName") String description,
                                 @Part("image\"; filename=\"image.png\"") RequestBody imgs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_img_upload);

        btnUpload = (Button) findViewById(R.id.btn_activity_retrofit_img_upload_Upload);
        btnUpload1 = (Button) findViewById(R.id.btn_activity_retrofit_img_upload_Upload1);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descriptionString = "hello, this is description speaking";


                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://192.168.4.43/TP5RC4/public/index.php/index/index/upload/")
                        .build();

                UploadImg service = retrofit.create(UploadImg.class);

                File file = new File("/storage/emulated/0/short.png");

                RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);


                Call<String> call = service.uploadImage("share.png", requestBody1);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("OK", "OK: ");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Log.e("onFailure", "onFailure: ");
                    }
                });

            }
        });


        btnUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())  //不加这一句还报错 尼玛的
                        .baseUrl("http://192.168.4.43/TP5RC4/public/index.php/index/index/index/")
                        .build();

                BlogService service = retrofit.create(BlogService.class);
                Call<String> call = service.getFirstBlog(2);
                // 用法和OkHttp的call如出一辙
                // 不同的是如果是Android系统回调方法执行在主线程
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("OK", "OK: ");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Log.e("onFailure", "onFailure: ");
                    }
                });
            }
        });
    }


    public void btnUpload3Click(View v){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s = Con();
                subscriber.onNext(s);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(RetrofitImgUploadActivity.this, s, Toast.LENGTH_SHORT).show();//Post失败
                    }
                });
    }

    public String Con(){
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try
        {
            URL url = new URL("http://192.168.4.43/TP5RC4/public/index.php/index/index/upload");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
            con.setDoOutput(true);
          /* Read from the connection. Default is true.*/
            con.setDoInput(true);
          /* Post cannot use caches */
            con.setUseCaches(false);
          /* Set the post method. Default is GET*/
            con.setRequestMethod("POST");
          /* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
          /*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*/
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
          /* 设置DataOutputStream，getOutputStream中默认调用connect()*/
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"image\";filename=\"" +
                    "short.png" + "\"" + end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream("/storage/emulated/0/short.png");
          /* 设置每次写入8192bytes */
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];   //8k
            int length = -1;
          /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1)
            {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* 关闭流，写入的东西自动生成Http正文*/
            fStream.close();
          /* 关闭DataOutputStream */
            ds.close();
          /* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
          /* 显示网页响应内容 */
          //  Toast.makeText(RetrofitImgUploadActivity.this, b.toString().trim(), Toast.LENGTH_SHORT).show();//Post成功

            return  b.toString().trim();
        } catch (Exception e)
        {
            /* 显示异常信息 */
            //Toast.makeText(RetrofitImgUploadActivity.this, "Fail:" + e, Toast.LENGTH_SHORT).show();//Post失败
            return  "Fail:" + e;
        }


    }


    public void btnUploadNoHttpClick(View view)
    {
        String mImagePath = "/storage/emulated/0/short.png";
        TextUtils.isEmpty(mImagePath);
        File file = new File(mImagePath);
        if (file.exists())
        {
            Request<String> request = NoHttp.createStringRequest("http://192.168.4.43/TP5RC4/public/index.php/index/index/upload", RequestMethod.POST);

            // 添加普通参数。
            request.add("user", "yolanda");

            // 上传文件需要实现NoHttp的Binary接口，NoHttp默认实现了FileBinary、InputStreamBinary、ByteArrayBitnary、BitmapBinary。
            FileBinary fileBinary0 = new FileBinary(file);
            /**
             * 监听上传过程，如果不需要监听就不用设置。
             * 第一个参数：what，what和handler的what一样，会在回调被调用的回调你开发者，作用是一个Listener可以监听多个文件的上传状态。
             * 第二个参数： 监听器。
             */

            fileBinary0.setUploadListener(0, new OnUploadListener() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onCancel(int what) {

                }

                @Override
                public void onProgress(int what, int progress) {

                }

                @Override
                public void onFinish(int what) {

                }

                @Override
                public void onError(int what, Exception exception) {

                }
            });

            request.add("image", fileBinary0);// 添加1个文件

            NoHttp.newRequestQueue().add(0, request, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                    Toast.makeText(getApplicationContext(), "成功:" + response.get(),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                    Toast.makeText(getApplicationContext(), "失败" + exception,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish(int what) {

                }
            });

        }

    }


}
