package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaWeatherActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    /**
     * 天气预报API地址
     */
    private static final String WEATHRE_API_URL="http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
    public EditText cityET;
    public TextView queryTV;
    public TextView weatherTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_weather);

        //获取控件实例
        cityET = (EditText) findViewById(R.id.city);
        queryTV = (TextView) findViewById(R.id.query);
        weatherTV = (TextView) findViewById(R.id.weather);
        //对查询按钮侦听点击事件
        queryTV.setOnClickListener(this);
        weatherTV.setOnTouchListener(this);
    }


    public void onClick(View v)
    {
        if(v.getId() == R.id.query){
            weatherTV.setText("");
            String city = cityET.getText().toString();
            if(TextUtils.isEmpty(city)){
                Toast.makeText(this, "城市不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            //采用普通写法创建Observable
            observableAsNormal(city);
            //采用lambda写法创建Observable
//            observableAsLambda(city);
        }
    }

   public boolean onTouch(View v, MotionEvent event)
   {

       return true;
   }


    /**
     * 获取指定城市的天气情况
     * @param city
     * @return
     * @throws
     */
    private String getWeather(String city) throws Exception{
        BufferedReader reader = null;
        HttpURLConnection connection=null;
        try {
            String urlString = String.format(WEATHRE_API_URL, URLEncoder.encode(city, "GBK"));
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            //连接
            connection.connect();

            //处理返回结果
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line="";
            while(!TextUtils.isEmpty(line = reader.readLine()))
                buffer.append(line);
            return buffer.toString();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析xml获取天气情况
     * @param weatherXml
     * @return
     */
    private Weather parseWeather(String weatherXml){
        //采用Pull方式解析xml
        StringReader reader = new StringReader(weatherXml);
        XmlPullParser xmlParser = Xml.newPullParser();
        Weather weather = null;
        try {
            xmlParser.setInput(reader);
            int eventType = xmlParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        weather = new Weather();
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = xmlParser.getName();
                        if("city".equals(nodeName)){
                            weather.city = xmlParser.nextText();
                        } else if("savedate_weather".equals(nodeName)){
                            weather.date = xmlParser.nextText();
                        } else if("temperature1".equals(nodeName)) {
                            weather.temperature = xmlParser.nextText();
                        } else if("temperature2".equals(nodeName)){
                            weather.temperature += "-" + xmlParser.nextText();
                        } else if("direction1".equals(nodeName)){
                            weather.direction = xmlParser.nextText();
                        } else if("power1".equals(nodeName)){
                            weather.power = xmlParser.nextText();
                        } else if("status1".equals(nodeName)){
                            weather.status = xmlParser.nextText();
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
            return weather;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }
    }


    /**
     * 采用普通写法创建Observable
     * @param city
     */
    private void observableAsNormal(String city){
         Observable.create(new Observable.OnSubscribe<Weather>() {
            @Override
            public void call(Subscriber<? super Weather> subscriber) {
                //1.如果已经取消订阅，则直接退出
                if(subscriber.isUnsubscribed()) return;
                try {
                    //2.开网络连接请求获取天气预报，返回结果是xml格式
                    String weatherXml = getWeather("深圳");
                    //3.解析xml格式，返回weather实例
                    Weather weather = parseWeather(weatherXml);
                    //4.发布事件通知订阅者
                    subscriber.onNext(weather);
                    //5.事件通知完成
                    subscriber.onCompleted();
                } catch(Exception e){
                    //6.出现异常，通知订阅者
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())    //让Observable运行在新线程中
                .observeOn(AndroidSchedulers.mainThread())   //让subscriber运行在主线程中
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                        //对应上面的第5点：subscriber.onCompleted();
                        //这里写事件发布完成后的处理逻辑

                    }

                    @Override
                    public void onError(Throwable e) {
                        //对应上面的第6点：subscriber.onError(e);
                        //这里写出现异常后的处理逻辑
                        Toast.makeText(RxJavaWeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Weather weather) {
                        //对应上面的第4点：subscriber.onNext(weather);
                        //这里写获取到某一个事件通知后的处理逻辑
                        if(weather != null)
                            weatherTV.setText(weather.toString());
                    }
                });

        //3.1 subscribeOn(Schedulers.newThread())表示开一个新线程处理Observable.create()方法里的逻辑，也就是处理网络请求和解析xml工作
        //3.2 observeOn(AndroidSchedulers.mainThread())表示subscriber所运行的线程是在UI线程上，也就是更新控件的操作是在UI线程上
        //3.3 如果这里只有subscribeOn()方法而没有observeOn()方法，那么Observable.create()和subscriber()都是运行在subscribeOn()所指定的线程中；
        //3.4 如果这里只有observeOn()方法而没有subscribeOn()方法，那么Observable.create()运行在主线程(UI线程)中，而subscriber()是运行在observeOn()所指定的线程中(本例的observeOn()恰好是指定主线程而已)
    }


    /**
     * 采用lambda写法创建Observable
     * @param city
     */

    private void observableAsLambda(String city){
          Observable.create(subscriber->{
                    if(subscriber.isUnsubscribed()) return;
                    try {
                        String weatherXml = getWeather(city);
                        Weather weather = parseWeather(weatherXml);
                        subscriber.onNext(weather);
                        subscriber.onCompleted();
                    } catch(Exception e){
                        subscriber.onError(e);
                    }
                }
        ).subscribeOn(Schedulers.newThread())    //让Observable运行在新线程中
                .observeOn(AndroidSchedulers.mainThread())   //让subscriber运行在主线程中
                .subscribe(
                        weather->{
                            if(weather != null)
                                weatherTV.setText(weather.toString());
                        },
                        e->{
                            Toast.makeText(RxJavaWeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
//        Observable.defer()
    }


    /**
     * 天气情况类
     */
    private class Weather{
        /**
         * 城市
         */
        String city;
        /**
         * 日期
         */
        String date;
        /**
         * 温度
         */
        String temperature;
        /**
         * 风向
         */
        String direction;
        /**
         * 风力
         */
        String power;
        /**
         * 天气状况
         */
        String status;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("城市:" + city + "\r\n");
            builder.append("日期:" + date + "\r\n");
            builder.append("天气状况:" + status + "\r\n");
            builder.append("温度:" + temperature + "\r\n");
            builder.append("风向:" + direction + "\r\n");
            builder.append("风力:" + power + "\r\n");
            return builder.toString();
        }
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView view = (ImageView) findViewById(R.id.iv_welcome);
        view.setImageResource(R.drawable.welcome);
        //本地图片缓存路径
        File localBitmapFile = new File(getLocalBitmapPath());
        if(localBitmapFile.exists())
            mAdBitmapDrawable = BitmapFactory.decodeFile(localBitmapFile);
        //开新线程从网络获取图片
        AsyncAdImageFromNet();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mAdBitmapDrawable是本地缓存获取到的图片，如果获取到图片，则显示出来，否则直接跳转到主页面
                if(mAdBitmapDrawable != null){
                    view.setImageDrawable(mAdBitmapDrawable);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();
                        }
                    }, 2000);
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);

    }
*/
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView view = (ImageView) findViewById(R.id.iv_welcome);
        view.setImageResource(R.mipmap.welcome);

        Observable.mergeDelayError(
                //在新线程中加载本地缓存图片
                loadBitmapFromLocal().subscribeOn(Schedulers.io()),
                //在新线程中加载网络图片
                loadBitmapFromNet().subscribeOn(Schedulers.newThread()),
                Observable.timer(3,TimeUnit.SECONDS).map(c->null))
                //每隔2秒获取加载数据
                .sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .flatMap(r->{
                    if(r==null)  //如果没有获取到图片，直接跳转到主页面
                        return Observable.empty();
                    else { //如果获取到图片，则停留2秒再跳转到主页面
                        view.setImageDrawable(r);
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                }).subscribe(
                r->{
                },
                e->{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                },
                ()->{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
        );
    }
 这里使用了几个操作符：首先是mergeDelayError，它的意思是合并几个不同的Observable；sample的意思是每隔一段时间就进行采样，
 在时间间隔范围内获取最后一个发布的Observable; flatMap的意思是把某一个Observable转换成另一个Observable。
*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber<? super Integer> observer) {
                try {
                if (!observer.isUnsubscribed()) {
                for (int i = 1; i < 5; i++) {
                observer.onNext(i);
                }
                observer.onCompleted();
                }
                } catch (Exception e) {
                observer.onError(e);
                }
                }
                } )
 .subscribe(new Subscriber<Integer>() {
        @Override
        public void onNext(Integer item) {
                System.out.println("Next: " + item);
                }

        @Override
        public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
                }

        @Override
        public void onCompleted() {
                System.out.println("Sequence complete.");
                }
        });
 运行结果如下：
 Next: 1
 Next: 2
 Next: 3
 Next: 4
 Sequence complete.
*/

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
Integer[] items = { 0, 1, 2, 3, 4, 5 };
    Observable myObservable = Observable.from(items);

myObservable.subscribe(
        new Action1<Integer>() {
                        @Override
                        public void call(Integer item) {
                                System.out.println(item);
                                }
                                },
        new Action1<Throwable>() {
                        @Override
                        public void call(Throwable error) {
                                System.out.println("Error encountered: " + error.getMessage());
                                }
                                },
        new Action0() {
                        @Override
                        public void call() {
                                System.out.println("Sequence complete");
                                }
                               }
        );

 运行结果如下：
 0
 1
 2
 3
 4
 5
 Sequence complete
*/
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
Observable.just(1, 2, 3)
        .subscribe(new Subscriber<Integer>() {
                @Override
                public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                        }

                @Override
                public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                        }

                @Override
                public void onCompleted() {
                        System.out.println("Sequence complete.");
                        }
        });
运行结果如下：
Next: 1
Next: 2
Next: 3
Sequence complete.
*/

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
        i=10;
        Observable justObservable = Observable.just(i);
        i=12;
        Observable deferObservable = Observable.defer(new Func0<Observable<Object>>() {
                    @Override
                    public Observable<Object> call() {
                            return Observable.just(i);
                            }
                            });
        i=15;

        justObservable.subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                            System.out.println("just result:" + o.toString());
                            }
        });

        deferObservable.subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                            }

                    @Override
                    public void onError(Throwable e) {

                            }

                    @Override
                    public void onNext(Object o) {
                            System.out.println("defer result:" + o.toString());
                            }
        });}
 其中i是类的成员变量，运行结果如下：
 just result:10
 defer result:15

 可以看到，just操作符是在创建Observable就进行了赋值操作，而defer是在订阅者订阅时才创建Observable，此时才进行真正的赋值操作
 */

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
//每隔两秒产生一个数字
 Observable.interval(2, 2, TimeUnit.SECONDS)  //不停的执行
 Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())  //只执行一次
Observable.timer(2, 2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
        @Override
        public void onCompleted() {
                System.out.println("Sequence complete.");
                }

        @Override
        public void onError(Throwable e) {
                System.out.println("error:" + e.getMessage());
                }

        @Override
        public void onNext(Long aLong) {
                System.out.println("Next:" + aLong.toString());
                }
});
 运行结果如下：
 Next:0
 Next:1
 Next:2
 Next:3
 ……
 */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
//产生从3开始，个数为10个的连续数字
Observable.range(3,10).subscribe(new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
                System.out.println("Sequence complete.");
                }

        @Override
        public void onError(Throwable e) {
                System.out.println("error:" + e.getMessage());
                }

        @Override
        public void onNext(Integer i) {
                System.out.println("Next:" + i.toString());
                }
        });

 运行结果如下：
 Next:3
 Next:4
 Next:5
 Next:6
 ….
 Next:12
 Sequence complete.
*/

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
//连续产生两组(3,4,5)的数字
Observable.range(3,3).repeat(2).subscribe(new Subscriber<Integer>() {
    @Override
    public void onCompleted() {
            System.out.println("Sequence complete.");
            }

    @Override
    public void onError(Throwable e) {
            System.out.println("error:" + e.getMessage());
            }

    @Override
    public void onNext(Integer i) {
            System.out.println("Next:" + i.toString());
            }
});
 运行结果如下：
 Next:3
 Next:4
 Next:5
 Next:3
 Next:4
 Next:5
 Sequence complete.
 */