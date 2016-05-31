package com.example.admin.myapplication.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RxJavaActivity extends AppCompatActivity {

    static class Food{}
    static class Fruit extends Food{}
    static class Apple extends Fruit{}
    static class RedApple extends Apple{}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.d("1", "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d("1", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("1", "Error!");
            }
        };

        //执行任务的人，里面的函数表示这个人能做哪些事。具体做事要由指挥部安排
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d("1", "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d("1", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("1", "Error!");
            }
        };


        //被观察者-我方间谍observable
        //指挥部 -   onSubscribe
        //执行任务的人subscriber

        //1、我方间谍（observable）得到情报后，（情报的内容可能是任务信息被泄露给敌方，这时要通知任务执行者做好准备，并通知指挥部改变任务计划）
        //2、告诉执行任务的人（subscriber）做准备, 同时通知指挥部：onSubscribeobservable.subscribe(subscriber) 所有的通知都在这里实现
        //3、指挥部onSubscribe得到通知后，告知执行任务的人（subscriber）你们已经暴露了，接下来你们要怎么做: onSubscribe.call(subscriber)
        //知道以上流程后，我们要做的是什么呢:
        //创建一个执行任务的类，这个类定义了能做哪一些任务
        //

        //充当指挥部的角色，
        Observable.OnSubscribe<String> onSubscribe =  new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        };

        //public class Observable<T> {}
        //Observable observable相当于Observable<Object> observable
        Observable observable = Observable.create(onSubscribe);
        observable.subscribe(subscriber);

        //Observable observable相当于observable<Object> observable
        //subscribe(Subscriber<? super T> subscriber)

//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello");
//                subscriber.onNext("Hi");
//                subscriber.onNext("Aloha");
//                subscriber.onCompleted();
//            }
//        });


//        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");




        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d("1", s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d("2", "completed");
            }
        };

// 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
// 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
// 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);







//        String[] names ={"abc", "def"};
//        Observable.from(names)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String name) {
//                        Log.d("2", name);
//                    }
//                });


 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        String[] names ={"abc", "def"};

        //任务的执行者，只会干一件事情，就是call
        Action1<String> anction = new Action1<String>() {
            @Override
            public void call(String name) {
                Log.d("2", name);
            }
        };

        //from(names)会产生指挥部，names就是指挥部初始化时得到的内容。
        //但指挥部有一个call函数，这个定义了任务执行者执行任务的步骤
        //这里并没有定义call里面的内容，说明这个call的步骤是系统提供的模板，可以分析下from(names)
        //这里产生了一个OnSubscribeFromArray指挥部对象，并且它的内部有一个array属性 array = names//  String[] names ={"abc", "def"};
        // OnSubscribeFromArray的call是已经定义好的，不需要我们再去定义，定义如下
        //@Override
        //public void call(Subscriber<? super T> child) {
        //    child.setProducer(new FromArrayProducer<T>(child, array));
        //}
        //subscribe(anction)最后会调用指挥部的OnSubscribeFromArray.call(anction)，就是上面的函数
        Observable.from(names)
                .subscribe(anction);
        //指挥部会依次把数组（String[] names ={"abc", "def"};）里的每一个元素当做参数，调用任务执行者的call
        //即
        // anction.call("abc");
        // anction.call(""def"")


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /**
        int drawableRes = 0;
        final ImageView imageView = (ImageView)findViewById(R.id.img_activity_anim_img);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                //Drawable drawable = getDrawable(drawableRes));
                Drawable drawable = null;
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        });*/



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //.map会new一个新的Observable，
        // return new Observable<R>(new OnSubscribeLift<T, R>(onSubscribe, operator));
        //OnSubscribeLift.parent = onSubscribe
        //OnSubscribeLift.operator = onSubscribe

        //新的Observable结构如下
        //Observable.OnSubscribe = OnSubscribeLift;  所以.subscribe的时候调用OnSubscribeLift.call(subscribe)
        //OnSubscribeLift.parent = onSubscribe
        //OnSubscribeLift.operator = operator
        Func1<String, Bitmap> myMap = new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String filePath) { // 参数类型 String
                //return getBitmapFromPath(filePath); // 返回类型 Bitmap
                Log.e("1", "getBitmapFromPath");
                return  null;
            }
        };
        Action1<Bitmap> Mysub = new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) { // 参数类型 Bitmap
                //showBitmap(bitmap);
                Log.e("1", "showBitmap");

            }
        };

        Observable.just("images/logo.png", "images/logo.png") // 输入类型 String
                .map(myMap)
                .subscribe(Mysub);
        //其实调用过程是这样的
        // Bitmap bmp = myMap.call(""images/logo.png"")
        //Mysub.call(bmp);


//
//        Observable.just(1, 2, 3, 4)
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer number) {
//                        Log.d("2", "number:" + number);
//                    }
//                });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Student[] students = new Student[3];
        Student[] students = new Student[]{new Student(), new Student(),new Student()};
        Subscriber<Course> subscriber1 = new Subscriber<Course>() {
            @Override
            public void onNext(Course course) {
                Log.e("1", course.getName());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("1", "error");
            }
        };

        //这里传3个student
        //每一个student调用一次flatMap
        //每一个subscribe.next()之前,先调用.flatmap里的call

        //执行参数students[0]，flatmap.call得到 Observable.from(1, 2)
        //1.next
        //2.next

        //执行参数students[1]，flatmap.call得到 Observable.from(1, 2)
        //1.next
        //2.next
        //执行参数students[2]，flatmap.call得到 Observable.from(1, 2)
        //1.next
        //2.next

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber1);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    public class MyOnSub implements Observable.OnSubscribe<String>
    {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    }

    public class Student{
        List<Course> courses;

        public List<Course> getCourses(){
            List<Course> coursesList = new ArrayList<>();
            coursesList.add(new Course("haha"));
            coursesList.add(new Course("hehe"));
            return coursesList;
        }
    }

    public  class  Course{
        public  String mName;
        public Course(String name){
            this.mName = name;
        }

        public  String getName(){
            return this.mName;
        }
    }
}
