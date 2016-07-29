package com.example.admin.myapplication.activities;

import com.example.admin.myapplication.tools.AsyncDrawable;
import com.example.admin.myapplication.tools.BitmapWorkerTask;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.admin.myapplication.R;


public class ThumbnailActivity extends AppCompatActivity {

    ImageView image;
    ImageView image1;
    ImageView image2;
    Bitmap bitmap;
    Bitmap mPlaceHolderBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);


        image = (ImageView) findViewById(R.id.image);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcomebackgroud);

        //使用缩略图类
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
        image.setImageBitmap(bitmap);


        //前台加载
        image1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.welcomebackgroud, 100, 100));

        //异步后台加载
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(image2, getResources());
        bitmapWorkerTask.execute(R.drawable.welcomebackgroud, 100, 100);



        /**
         * http://my.oschina.net/rengwuxian/blog/182885
         *高效使用Bitmaps有什么好处？

         我们常常提到的“Android程序优化”，通常指的是性能和内存的优化，即：更快的响应速度，更低的内存占用。Android程序的性能和内存问题，
         大部分都和图片紧密相关，而图片的加载在很多情况下很用到Bitmap（位图）这个类。而由于Bitmap自身的特性（将每个像素的属性全部保存在内存中），
         导致稍有不慎就会创建出一个占用内存非常大的Bitmap对象，从而导致加载过慢，还会有内存溢出的风险。所以，Android程序要做优化，Bitmap的优化是必不可少的一步。
         需要对Bitmap进行优化的情形

         首先请看一行代码：

         mImageView.setImageResource(R.drawable.my_image);
         这是一行从资源文件中加载图片到ImageView的代码。通常这段代码没什么问题，但有些情况下，你需要对这段代码进行优化。
         例如当图片的尺寸远远大于ImageView的尺寸时，或者当你要在一个ListView或GridView中批量加载一些大小未知的图片时。
         实际上，以上这行代码会在运行时使用BitmapFactory.decodeStream()方法将资源图片生成一个Bitmap，然后由这个Bitmap生成一个Drawable，
         最后再将这个Drawable设置到ImageView。由于在过程中生成了Bitmap，因此如果你使用的图片过大，就会导致性能和内存占用的问题。
         另外，需要优化的情形不止这一种，这里就不再列举。

         下面分步说明使用代码来减小Bitmap的尺寸从而达到减小内存占用的方法：

         1. 获取原图片尺寸

         通常，我们使用BitmapFactory.decodeResource()方法来从资源文件中读取一张图片并生成一个Bitmap。但如果使用一个BitmapFactory.Options对象，
         并把该对象的inJustDecodeBounds属性设置为true，decodeResource()方法就不会生成Bitmap对象，而仅仅是读取该图片的尺寸和类型信息：



         BitmapFactory.Options options = new BitmapFactory.Options();
         options.inJustDecodeBounds = true;
         BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
         int imageHeight = options.outHeight;
         int imageWidth = options.outWidth;
         String imageType = options.outMimeType;

         2. 根据原图尺寸和目标区域的尺寸计算出合适的Bitmap尺寸

         BitmapFactory.Options类有一个参数inSampleSize，该参数为int型，他的值指示了在解析图片为Bitmap时在长宽两个方向上像素缩小的倍数。
         inSampleSize的默认值和最小值为1（当小于1时，解码器将该值当做1来处理），且在大于1时，该值只能为2的幂（当不为2的幂时，解码器会取与该值最接近的2的幂）。
         例如，当inSampleSize为2时，一个2000*1000的图片，将被缩小为1000*500，相应地，它的像素数和内存占用都被缩小为了原来的1/4：

            public static int calculateInSampleSize(
                        BitmapFactory.Options options, int reqWidth, int reqHeight) {
                // 原始图片的宽高
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;

                if (height > reqHeight || width > reqWidth) {

                    final int halfHeight = height / 2;
                    final int halfWidth = width / 2;

                    // 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
                    while ((halfHeight / inSampleSize) > reqHeight
                            && (halfWidth / inSampleSize) > reqWidth) {
                        inSampleSize *= 2;
                    }
                }

                return inSampleSize;
            }

         3. 根据计算出的inSampleSize生成Bitmap
         public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {

            // 首先设置 inJustDecodeBounds=true 来获取图片尺寸
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // 计算 inSampleSize 的值
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // 根据计算出的 inSampleSize 来解码图片生成Bitmap
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
         }

         这里有一点要注意，就是要在第二遍decode之前把inJustDecodeBounds设置回false。

         4. 调用以上的decodeSampledBitmapFromResource方法，使用自定尺寸的Bitmap。

         如果你要将一张大图设置为一个100*100的缩略图，执行以下代码：

         mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));

         到此，使用decodeResource()方法将一个大图解析为小尺寸bitmap的应用就完成了。同理，还可以使用decodeStream()，decodeFile()等方法做相同的事，原理是一样的。

         延伸：一个Bitmap到底占用多大内存？系统给每个应用程序分配多大内存？

         · Bitmap占用的内存为：像素总数 * 每个像素占用的内存。在Android中，Bitmap有四种像素类型：ARGB_8888、ARGB_4444、ARGB_565、ALPHA_8，他们每个像素占用的字节数分别为4、2、2、1。因此，一个2000*1000的ARGB_8888类型的Bitmap占用的内存为2000*1000*4=8000000B=8MB。

         · Android根据设备屏幕尺寸和dpi的不同，给系统分配的单应用程序内存大小也不同，具体如下表（表格取自Android 4.4 Compatibility Definition Document (CDD)）：



         屏幕尺寸	 DPI	 应用内存
         small / normal / large	 ldpi / mdpi	 16MB
         small / normal / large	 tvdpi / hdpi	 32MB
         small / normal / large	 xhdpi	 64MB
         small / normal / large	 400dpi	 96MB
         small / normal / large	 xxhdpi	 128MB
         xlarge	 mdpi	 32MB
         xlarge	 tvdpi / hdpi	 64MB
         xlarge	 xhdpi	 128MB
         xlarge	 400dpi	 192MB
         xlarge	 xxhdpi	 256MB

         */
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 原始图片的宽高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // 首先设置 inJustDecodeBounds=true 来获取图片尺寸
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 计算 inSampleSize 的值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 根据计算出的 inSampleSize 来解码图片生成Bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

//    public void loadBitmap(int resId, ImageView imageView) {
//        if (cancelPotentialWork(resId, imageView)) {
//            final BitmapWorkerTask task = new BitmapWorkerTask(image, getResources());
//            final AsyncDrawable asyncDrawable =
//                    new AsyncDrawable(getResources(), mPlaceHolderBitmap, task);
//            imageView.setImageDrawable(asyncDrawable);
//            task.execute(resId);
//        }
//    }
}
