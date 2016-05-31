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


/////////////////////////////////////////////////////////////////////////////////////////////////////

/**
Observable.just(1,2,3).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                @Override
                public Observable<?> call(Observable<? extends Void> observable) {
                        //重复3次
                        return observable.zipWith(Observable.range(1, 3), new Func2<Void, Integer, Integer>() {
                @Override
                public Integer call(Void aVoid, Integer integer) {
                        return integer;
                        }
                        }).flatMap(new Func1<Integer, Observable<?>>() {
                @Override
                public Observable<?> call(Integer integer) {
                        System.out.println("delay repeat the " + integer + " count");
                        //1秒钟重复一次
                        return Observable.timer(1, TimeUnit.SECONDS);
                        }
                        });
                        }
        }).subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                        System.out.println("Sequence complete.");
                        }

                @Override
                public void onError(Throwable e) {
                        System.err.println("Error: " + e.getMessage());
                        }

                @Override
                public void onNext(Integer value) {
                        System.out.println("Next:" + value);
                        }
        });

 运行结果如下：
 Next:1
 Next:2
 Next:3
 repeat the 1 count
 Next:1
 Next:2
 Next:3
 repeat the 2 count
 Next:1
 Next:2
 Next:3
 repeat the 3 count
 Next:1
 Next:2
 Next:3
 Sequence complete.

*/

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 //定义邮件内容
 final String[] mails = new String[]{"Here is an email!", "Another email!", "Yet another email!"};
 //每隔1秒就随机发布一封邮件
 Observable<String> endlessMail = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (subscriber.isUnsubscribed()) return;
                    Random random = new Random();
                    while (true) {
                    String mail = mails[random.nextInt(mails.length)];
                    subscriber.onNext(mail);    //这里并没有立即执行，而是把内容放到List里面
                    Thread.sleep(1000);
                }
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
}).subscribeOn(Schedulers.io());
 //把上面产生的邮件内容缓存到列表中，并每隔3秒通知订阅者
 endlessMail.buffer(3, TimeUnit.SECONDS).subscribe(new Action1<List<String>>() {
             //每隔3秒从List中取一次数据
             @Override
             public void call(List<String> list) {

                 System.out.println(String.format("You've got %d new messages!  Here they are!", list.size()));
                 for (int i = 0; i < list.size(); i++)
                     System.out.println("**" + list.get(i).toString());
             }
 });

 You’ve got 3 new messages! Here they are!(after 3s)
  **Here is an email!
  **Another email!
  **Another email!
 You’ve got 3 new messages! Here they are!(after 6s)
  **Here is an email!
  **Another email!
  **Here is an email!
 ……
             buffer操作符周期性地收集源Observable产生的结果到列表中，并把这个列表提交给订阅者，订阅者处理后，清空buffer列表，同时接收下一次收集的结果并提交给订阅者，周而复始。
             需要注意的是，一旦源Observable在产生结果的过程中出现异常，即使buffer已经存在收集到的结果，订阅者也会马上收到这个异常，并结束整个过程。
 */


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 private Observable<File> listFiles(File f){
     if(f.isDirectory()){
         return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
            @Override
            public Observable<File> call(File file) {
                return listFiles(f);
            }
        });
     } else {
         return Observable.just(f);
     }
 }


 @Override
 public void onClick(View v) {
     Observable.just(getApplicationContext().getExternalCacheDir())
     .flatMap(new Func1<File, Observable<File>>() {
         @Override
         public Observable<File> call(File file) {
             //参数file是just操作符产生的结果，这里判断file是不是目录文件，如果是目录文件，则递归查找其子文件flatMap操作符神奇的地方在于，返回的结果还是一个Observable，而这个Observable其实是包含多个文件的Observable的，输出应该是ExternalCacheDir下的所有文件
             return listFiles(file);
         }
     })
     .subscribe(new Action1<File>() {
         @Override
         public void call(File file) {
             System.out.println(file.getAbsolutePath());
         }
     });

 }


 flatMap操作符是把Observable产生的结果转换成多个Observable，然后把这多个Observable“扁平化”成一个Observable，并依次提交产生的结果给订阅者。

 flatMap操作符通过传入一个函数作为参数转换源Observable，在这个函数中，你可以自定义转换规则，最后在这个函数中返回一个新的Observable，
 然后flatMap操作符通过合并这些Observable结果成一个Observable，并依次提交结果给订阅者。

 值得注意的是，flatMap操作符在合并Observable结果时，有可能存在交叉的情况，如下流程图所示：

 ancatMap操作符与flatMap操作符类似，都是把Observable产生的结果转换成多个Observable，然后把这多个Observable“扁平化”成一个Observable，并依次提交产生的结果给订阅者。

 与flatMap操作符不同的是，concatMap操作符在处理产生的Observable时，采用的是“连接(concat)”的方式，
 而不是“合并(merge)”的方式，这就能保证产生结果的顺序性，也就是说提交给订阅者的结果是按照顺序提交的，不会存在交叉的情况。

 */

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*

switch()和flatMap()很像，除了一点:当源Observable发射一个新的数据项时，如果旧数据项订阅还未完成，就取消旧订阅数据和停止监视那个数据项产生的Observable
,开始监视新的数据项.

Observable.just("A", "B", "C", "D", "E").switchMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                Observable<String> ob = Observable.just(s);
                return ob;
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtils.d("------>onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("------>onError()" + e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.d("------>onNext:" + s);
            }
        });

运行结果：
02-26 17:10:28.878 28598-28598/com.rxandroid.test1 D/----->: ------>onNext:A
02-26 17:10:28.878 28598-28598/com.rxandroid.test1 D/----->: ------>onNext:B
02-26 17:10:28.878 28598-28598/com.rxandroid.test1 D/----->: ------>onNext:C
02-26 17:10:28.878 28598-28598/com.rxandroid.test1 D/----->: ------>onNext:D
02-26 17:10:28.878 28598-28598/com.rxandroid.test1 D/----->: ------>onNext:E
02-26 17:10:28.878 28598-28598/com.rxandroid.test1 D/----->: ------>onCompleted()

这是在同一线程产生数据，所以当第二个数据项来临时，第一个已经完成了，同理，c,d,e都将完成
下面来看并发产生数据项:

Observable.just("A", "B", "C", "D", "E").switchMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s).subscribeOn(Schedulers.newThread());
            }
        })
		.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtils.d("------>onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("------>onError()" + e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.d("------>onNext:" + s);
            }
        });

运行结果：
02-26 17:19:17.138 4980-4980/com.rxandroid.test1 D/----->: ------>onNext:E
02-26 17:19:17.138 4980-4980/com.rxandroid.test1 D/----->: ------>onCompleted()

当源Observable变换出的Observable同时进行时,
A --> 取消空的，没有可以取消的
B--> A被取消
C-->  B被取消
D-->  C被取消
E-->  D被取消

*/

////////////////////////////////////////////////////////////////////////////////////////
//http://blog.csdn.net/axuanqq/article/details/50739660
/**
 *
 * groupBy操作符顾名思义就是分组的意思:
 第一步:构造分组  call的返回值决定了组名

 groupBy操作符是对源Observable产生的结果进行分组，形成一个类型为GroupedObservable的结果集，GroupedObservable中存在一个方法为getKey()，可以通过该方法获取结果集的Key值（类似于HashMap的key)。

 值得注意的是，由于结果集中的GroupedObservable是把分组结果缓存起来，如果对每一个GroupedObservable不进行处理（既不订阅执行也不对其进行别的操作符运算），
 就有可能出现内存泄露。因此，如果你对某个GroupedObservable不进行处理，最好是对其使用操作符take(0)处理。

  Observable.range(0, 10).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer % 3;////分成0，1，2 三个小组
            }
        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onCompleted() {
                LogUtils.d("------>onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("------>onError()" + e);
            }

            @Override
            public void onNext(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.d("------>inner onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("------>inner onError()" + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.d("------>group:" + integerIntegerGroupedObservable.getKey() + "  value:" + integer);
                    }
                });
            }
        });


 结果为:
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:0  value:0
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:1  value:1
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:2  value:2
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:0  value:3
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:1  value:4
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:2  value:5
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:0  value:6
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:1  value:7
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:2  value:8
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>group:0  value:9
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>inner onCompleted()
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>inner onCompleted()
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>inner onCompleted()
 02-26 18:01:36.218 9796-9796/com.rxandroid.test1 D/----->: ------>onCompleted()

 call的返回值并不是组的个数:

  Observable.range(0, 10).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
               // return integer % 3;////分成0，1，2 三个小组
                return 2;
            }
        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onCompleted() {
                LogUtils.d("------>onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("------>onError()" + e);
            }

            @Override
            public void onNext(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.d("------>inner onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("------>inner onError()" + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.d("------>group:" + integerIntegerGroupedObservable.getKey() + "  value:" + integer);
                    }
                });
            }
        });

 结果只有一个组，这个组的组名叫2：
 02-26 18:14:19.228 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:0
 02-26 18:14:19.228 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:1
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:2
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:3
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:4
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:5
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:6
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:7
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:8
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>group:2  value:9
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>inner onCompleted()
 02-26 18:14:19.238 21365-21365/com.rxandroid.test1 D/----->: ------>onCompleted()


 Observable.interval(1, TimeUnit.SECONDS).take(10).groupBy(new Func1<Long, Long>() {
            @Override
            public Long call(Long value) {
                //按照key为0,1,2分为3组
                return value % 3;
            }
        }).subscribe(new Action1<GroupedObservable<Long, Long>>() {
            @Override
            public void call(GroupedObservable<Long, Long> result) {
                result.subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long value) {
                        System.out.println("key:" + result.getKey() +", value:" + value);
                    }
                });
            }
        });

 运行结果如下：
 key:0, value:0
 key:1, value:1
 key:2, value:2
 key:0, value:3
 key:1, value:4
 key:2, value:5
 key:0, value:6
 key:1, value:7
 key:2, value:8
 key:0, value:9
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 Observable.just(1,2,3,4,5,6).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                //对源Observable产生的结果，都统一乘以3处理
                return integer*3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("next:" + integer);
            }
        });

 运行结果如下：
 next:3
 next:6
 next:9
 next:12
 next:15
 next:18

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 *
 cast操作符类似于map操作符，不同的地方在于map操作符可以通过自定义规则，把一个值A1变成另一个值A2，A1和A2的类型可以一样也可以不一样；
 而cast操作符主要是做类型转换的，传入参数为类型class，如果源Observable产生的结果不能转成指定的class，则会抛出ClassCastException运行时异常。*
 *
  Observable.just(1,2,3,4,5,6).cast(Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer value) {
                System.out.println("next:"+value);
            }
        });

 运行结果如下：
 next:1
 next:2
 next:3
 next:4
 next:5
 next:6
 */


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 scan操作符通过遍历源Observable产生的结果，依次对每一个结果项按照指定规则进行运算，计算后的结果作为下一个迭代项参数，每一次迭代项都会把计算结果输出给订阅者。

 Observable.just(1, 2, 3, 4, 5)
    .scan(new Func2<Integer, Integer, Integer>() {
        @Override
        public Integer call(Integer sum, Integer item) {
            //参数sum就是上一次的计算结果
            return sum + item;
        }
    }).subscribe(new Subscriber<Integer>() {
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
 Next: 3
 Next: 6
 Next: 10
 Next: 15
 Sequence complete.
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 *
 * window操作符

 window操作符非常类似于buffer操作符，区别在于buffer操作符产生的结果是一个List缓存，而window操作符产生的结果是一个Observable，
 订阅者可以对这个结果Observable重新进行订阅处理。

 Observable.interval(1, TimeUnit.SECONDS).take(12)
                .window(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Observable<Long>>() {
                    @Override
                    public void call(Observable<Long> observable) {
                        System.out.println("subdivide begin......");
                        observable.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                System.out.println("Next:" + aLong);
                            }
                        });
                    }
                });

运行结果如下：
subdivide begin……
Next:0
Next:1
subdivide begin……
Next:2
Next:3
Next:4
subdivide begin……
Next:5
Next:6
Next:7
subdivide begin……
Next:8
Next:9
Next:10
subdivide begin……
Next:11

 */


////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 debounce操作符对源Observable每产生一个结果后，如果在规定的间隔时间内没有别的结果产生，则把这个结果提交给订阅者处理，否则忽略该结果。

 值得注意的是，如果源Observable产生的最后一个结果后在规定的时间间隔内调用了onCompleted，那么通过debounce操作符也会把这个结果提交给订阅者。

 Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                try {
                    //产生结果的间隔时间分别为100、200、300...900毫秒
                    for (int i = 1; i < 10; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(i * 100);
                    }
                    subscriber.onCompleted();
                }catch(Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
          .debounce(400, TimeUnit.MILLISECONDS)  //超时时间为400毫秒
          .subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Next:" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("Error:" + throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("completed!");
                    }
                });

 运行结果如下：
 Next:4
 Next:5
 Next:6
 Next:7
 Next:8
 Next:9
 completed!
 */


///////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 *
 * distinct操作符

 distinct操作符对源Observable产生的结果进行过滤，把重复的结果过滤掉，只输出不重复的结果给订阅者，非常类似于SQL里的distinct关键字。

 Observable.just(1, 2, 1, 1, 2, 3)
          .distinct()
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 elementAt操作符

 elementAt操作符在源Observable产生的结果中，仅仅把指定索引的结果提交给订阅者，索引是从0开始的

 Observable.just(1,2,3,4,5,6).elementAt(2)
          .subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Next:" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("Error:" + throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("completed!");
                    }
                });

 运行结果如下：
 Next:3
 completed!
 */


///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 filter操作符

 filter操作符是对源Observable产生的结果按照指定条件进行过滤，只有满足条件的结果才会提交给订阅者

 Observable.just(1, 2, 3, 4, 5)
          .filter(new Func1<Integer, Boolean>() {
              @Override
              public Boolean call(Integer item) {
                return( item < 4 );
              }
          }).subscribe(new Subscriber<Integer>() {
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 ofType操作符

ofType操作符类似于filter操作符，区别在于ofType操作符是按照类型对结果进行过滤，其流程图如下：

 Observable.just(1, "hello world", true, 200L, 0.23f)
          .ofType(Float.class)
          .subscribe(new Subscriber<Object>() {
              @Override
              public void onNext(Object item) {
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
 Next: 0.23
 Sequence complete.
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 first操作符

 first操作符是把源Observable产生的结果的第一个提交给订阅者，first操作符可以使用elementAt(0)和take(1)替代。

    Observable.just(1,2,3,4,5,6,7,8)
          .first()
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

 Next: 1
 Sequence complete.
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 single操作符

 single操作符是对源Observable的结果进行判断，如果产生的结果满足指定条件的数量不为1，则抛出异常，否则把满足条件的结果提交给订阅者
 也就是说必须有一个结果满足single()条件

 Observable.just(1,2,3,4,5,6,7,8)
          .single(new Func1<Integer, Boolean>() {
              @Override
              public Boolean call(Integer integer) {
                  //取大于10的第一个数字
                  return integer>10;
              }
          })
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

 * 运行结果如下：
 Error: Sequence contains no elements
 */


///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**

 last操作符

 last操作符把源Observable产生的结果的最后一个提交给订阅者，last操作符可以使用takeLast(1)替代。

 Observable.just(1, 2, 3)
          .last()
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
 Next: 3
 Sequence complete.

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 ignoreElements操作符

 ignoreElements操作符忽略所有源Observable产生的结果，只把Observable的onCompleted和onError事件通知给订阅者。
 ignoreElements操作符适用于不太关心Observable产生的结果，只是在Observable结束时(onCompleted)或者出现错误时能够收到通知。

    Observable.just(1,2,3,4,5,6,7,8).ignoreElements()
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
 Sequence complete.

 sample操作符

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**

 sample操作符

 sample操作符定期扫描源Observable产生的结果，在指定的时间间隔范围内对源Observable产生的结果进行采样，


 Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                try {
                    //前8个数字产生的时间间隔为1秒，后一个间隔为3秒
                    for (int i = 1; i < 9; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(1000);
                    }
                    Thread.sleep(2000);
                    subscriber.onNext(9);
                    subscriber.onCompleted();
                } catch(Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
          .sample(2200, TimeUnit.MILLISECONDS)  //采样间隔时间为2200毫秒
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
 Next: 3
 Next: 5
 Next: 7
 Next: 8
 Sequence complete.

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**

 skip操作符

 skip操作符针对源Observable产生的结果，跳过前面n个不进行处理，而把后面的结果提交给订阅者处理，其流程

 Observable.just(1,2,3,4,5,6,7).skip(3)
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
 Next: 4
 Next: 5
 Next: 6
 Next: 7
 Sequence complete.

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**

 skipLast操作符

 skipLast操作符针对源Observable产生的结果，忽略Observable最后产生的n个结果，而把前面产生的结果提交给订阅者处理，

 值得注意的是，skipLast操作符提交满足条件的结果给订阅者是存在延迟效果的，

 Observable.just(1,2,3,4,5,6,7).skipLast(3)
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 take操作符

 take操作符是把源Observable产生的结果，提取前面的n个提交给订阅者，而忽略后面的结果，其流程图如下：

 Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
          .take(4)
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**

 takeFirst操作符

 takeFirst操作符类似于take操作符，同时也类似于first操作符，都是获取源Observable产生的结果列表中符合指定条件的前一个或多个，
 与first操作符不同的是，first操作符如果获取不到数据，则会抛出NoSuchElementException异常，
 而takeFirst则会返回一个空的Observable，该Observable只有onCompleted通知而没有onNext通知。

Observable.just(1,2,3,4,5,6,7).takeFirst(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                //获取数值大于3的数据
                return integer>3;
            }
        })
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
Next: 4
Sequence complete.

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 takeLast操作符

 takeLast操作符是把源Observable产生的结果的后n项提交给订阅者，提交时机是Observable发布onCompleted通知之时

 Observable.just(1,2,3,4,5,6,7).takeLast(2)
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
Next: 6
Next: 7
Sequence complete.

 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 */