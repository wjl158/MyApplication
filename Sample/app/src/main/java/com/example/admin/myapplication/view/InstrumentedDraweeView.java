/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.admin.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.admin.myapplication.Intf.FrescoInstrumented;
import com.example.admin.myapplication.unity.FrescoInstrumentation;
import com.example.admin.myapplication.unity.PerfListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * {@link SimpleDraweeView} with instrumentation.
 */
public class InstrumentedDraweeView extends SimpleDraweeView implements FrescoInstrumented {

  /**
   * 图片监控类，就是在图片上画状态控件，在图片OnDraw的时候，传递图片的Canvas给监控类，那监控类就可以在图片的Canvas上面画东西了
   */
  private FrescoInstrumentation mInstrumentation;

  /**
   * 图片类的状态监听接口，比如在图片请求之前会调用onSubmit接口。 这里会把处理交给mInstrumentation即在OnSubmit是调用mInstrumentation.onStart();
   * 这样可以实现用mInstrumentation监听图片的请求状态
   * 其实也可以这样写：Instrumentation implements  ControllerListener<Object>
   */
  private ControllerListener<Object> mListener;

  public InstrumentedDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
    super(context, hierarchy);
    init();
  }

  public InstrumentedDraweeView(Context context) {
    super(context);
    init();
  }

  public InstrumentedDraweeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public InstrumentedDraweeView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    mInstrumentation = new FrescoInstrumentation(this);
    mListener = new BaseControllerListener<Object>() {
      @Override
      public void onSubmit(String id, Object callerContext) {
        mInstrumentation.onStart();
      }
      @Override
      public void onFinalImageSet(
        String id,
        @Nullable Object imageInfo,
        @Nullable Animatable animatable) {
        mInstrumentation.onSuccess();
      }
      @Override
      public void onFailure(String id, Throwable throwable) {
        mInstrumentation.onFailure();
      }
      @Override
      public void onRelease(String id) {
        mInstrumentation.onCancellation();
      }
    };
  }


  //在适配器ImageListAdapter.getView(int position, View convertView, ViewGroup parent) 时被调用，
  // String uri = getItem(position);
  // view.initInstrumentation(uri, mPerfListener);
  @Override
  public void initInstrumentation(String tag, PerfListener perfListener) {
    mInstrumentation.init(tag, perfListener);
  }

 //ImageView在自绘的时候 同时将canvas画布传递给 mInstrumentation，并调用mInstrumentation的onDraw在canvas上面画东西
  @Override
  public void onDraw(final Canvas canvas) {
    super.onDraw(canvas);
    mInstrumentation.onDraw(canvas);
  }

  //SimpleDraweeView 有两个方法可以设置所要加载显示图片，简单的方法就是setImageURI。
  //如果你需要对加载显示的图片做更多的控制和定制，那就需要用到DraweeController，本页说明如何使用。
  @Override
  public void setImageURI(Uri uri, @Nullable Object callerContext) {
    SimpleDraweeControllerBuilder controllerBuilder = getControllerBuilder()
        .setUri(uri)
        .setCallerContext(callerContext)
        .setOldController(getController());
    if (controllerBuilder instanceof AbstractDraweeControllerBuilder) {
      ((AbstractDraweeControllerBuilder<?,?,?,?>) controllerBuilder)
          .setControllerListener(mListener);
      //设置监听
      //你也许想在图片下载完成后执行一些动作，比如使某个别的 View 可见，或者显示一些文字。你也许还想在下载失败后做一些事，比如向用户显示一条失败信息。

     // 图片是后台线程异步加载的，所以你需要某一方式来监听 DraweeController 传递的事件。我们可以使用一个 ControllerListener 实现事件的监听。
    }


//    DraweeController controller = Fresco.newDraweeControllerBuilder()
//            .setUri(uri)
//            .setTapToRetryEnabled(true)
//            .setOldController(mSimpleDraweeView.getController())
//            .setControllerListener(listener)
//            .build();


    //设置DraweeController
    //也可以这样创建DraweeController controller = Fresco.newDraweeControllerBuilder()
    setController(controllerBuilder.build());
  }

  public ControllerListener<Object> getListener() {
    return mListener;
  }
}
