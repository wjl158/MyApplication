package com.example.admin.myapplication.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapActivity extends BaseActivity {
    static ByteArrayOutputStream byteOut = null;
    private Bitmap bitmap = null;
    private Button btn_cut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        btn_cut = (Button) findViewById(R.id.bitmap_btn);
        btn_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureScreen();
            }
        });
    }


    public void captureScreen() {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                final View contentView = getWindow().getDecorView();
                try{
                    Log.e("HEHE",contentView.getHeight()+":"+contentView.getWidth());
                    bitmap = Bitmap.createBitmap(contentView.getWidth(),
                            contentView.getHeight(), Bitmap.Config.ARGB_4444);
                    contentView.draw(new Canvas(bitmap));
                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
                    savePic(bitmap, "sdcard/short.png");
                }catch (Exception e){e.printStackTrace();}
                finally {
                    try{
                        if (null != byteOut)
                            byteOut.close();
                        if (null != bitmap && !bitmap.isRecycled()) {
//                            bitmap.recycle();
                            bitmap = null;
                        }
                    }catch (IOException e){e.printStackTrace();}

                }
            }
        };
        try {
            action.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                boolean success= b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                if(success)
                    Toast.makeText(BitmapActivity.this, "截屏成功", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




//    private Map<String, SoftReference<Bitmap>> imageMap
//            = new HashMap<String, SoftReference<Bitmap>>();
//
//    public Bitmap loadBitmap(final String imageUrl,final ImageCallBack imageCallBack) {
//        SoftReference<Bitmap> reference = imageMap.get(imageUrl);
//        if(reference != null) {
//            if(reference.get() != null) {
//                return reference.get();
//            }
//        }
//        final Handler handler = new Handler() {
//            public void handleMessage(final android.os.Message msg) {
//                //加入到缓存中
//                Bitmap bitmap = (Bitmap)msg.obj;
//                imageMap.put(imageUrl, new SoftReference<Bitmap>(bitmap));
//                if(imageCallBack != null) {
//                    imageCallBack.getBitmap(bitmap);
//                }
//            }
//        };
//        new Thread(){
//            public void run() {
//                Message message = handler.obtainMessage();
//                message.obj = downloadBitmap(imageUrl);
//                handler.sendMessage(message);
//            }
//        }.start();
//        return null ;
//    }
//
//    // 从网上下载图片
//    private Bitmap downloadBitmap (String imageUrl) {
//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
//            return bitmap ;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//public interface ImageCallBack{
//    void getBitmap(Bitmap bitmap);
//}








//    private LruCache<String, Bitmap> mMemoryCache;
//    private LruCacheUtils() {
//        if (mMemoryCache == null)
//            mMemoryCache = new LruCache<String, Bitmap>(
//                    MAXMEMONRY / 8) {
//                @Override
//                protected int sizeOf(String key, Bitmap bitmap) {
//                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
//                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
//                }
//
//                @Override
//                protected void entryRemoved(boolean evicted, String key,
//                                            Bitmap oldValue, Bitmap newValue) {
//                    Log.v("tag", "hard cache is full , push to soft cache");
//
//                }
//            };
//    }
//
//    public void clearCache() {
//        if (mMemoryCache != null) {
//            if (mMemoryCache.size() > 0) {
//                Log.d("CacheUtils",
//                        "mMemoryCache.size() " + mMemoryCache.size());
//                mMemoryCache.evictAll();
//                Log.d("CacheUtils", "mMemoryCache.size()" + mMemoryCache.size());
//            }
//            mMemoryCache = null;
//        }
//    };
//
//
//public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
//        if (mMemoryCache.get(key) == null) {
//        if (key != null && bitmap != null)
//        mMemoryCache.put(key, bitmap);
//        } else
//        Log.w(TAG, "the res is aready exits");
//        };
//
//public synchronized Bitmap getBitmapFromMemCache(String key) {
//        Bitmap bm = mMemoryCache.get(key);
//        if (key != null) {
//        return bm;
//        }
//        return null;
//        }
//
///**
// * 移除缓存
// *
// * @param key
// */
//public synchronized void removeImageCache(String key) {
//        if (key != null) {
//        if (mMemoryCache != null) {
//        Bitmap bm = mMemoryCache.remove(key);
//        if (bm != null)
//        bm.recycle();
//        }
//        }
//        }