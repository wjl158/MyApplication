package com.example.admin.myapplication.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.MyAdapter;
import com.example.admin.myapplication.model.Hero;
import com.example.admin.myapplication.model.NetImage;
import com.example.admin.myapplication.model.NetImageResult;
import com.example.admin.myapplication.tools.FrescoConfigConstants;
import com.example.admin.myapplication.tools.NetRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FrescoListActivity extends AppCompatActivity {
    public ArrayList<NetImage> mDatas;
    public BaseAdapter myAdadpter = null;
    public ListView lv;
    public EditText edt1;
    public  int mCount;
    private SharedPreferences preferences;

    public static final String searchPictureUrl="http://pic.sogou.com/pics?start=0&reqType=ajax&reqFrom=result&query=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Fresco，这一句第二个参数好像要不要速度都一样
        Fresco.initialize(this, FrescoConfigConstants.getImagePipelineConfig(this));

        setContentView(R.layout.activity_fresco_list);

        lv = (ListView)findViewById(R.id.lv_activity_fresco_list_main);
        edt1 = (EditText)findViewById(R.id.edt_activity_fresco_list_edit1);

        mDatas = new ArrayList<NetImage>();


        //每次打开先从缓存区数据
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCount = preferences.getInt("count", 0);

        if (mCount != 0)
        {
            for (int i = 0;  i < mCount; i ++)
            {
                NetImage netImg = new NetImage();
                netImg.setPic_url_noredirect(preferences.getString("" + i, ""));
                mDatas.add(netImg);
            }

        }
        setMyAdadpter();
        searchPicture("纯阳");

//        mDatas.add(new MyData(Uri.parse("http://imgsrc.baidu.com/forum/w%3D580/sign=2000aa6d339b033b2c88fcd225ce3620/80fb2a13b07eca8091128e8e962397dda04483ab.jpg")));
//        mDatas.add(new MyData(Uri.parse("res://com.example.admin.myapplication/" + R.drawable.js1)));

    }

    /**
     * 搜索图片请求
     * @param searchWord 搜索关键字
     */
    private void searchPicture(String searchWord) {
        try {
            searchWord = URLEncoder.encode(searchWord, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            searchWord ="girl";
        }
        //GET网络请求获取数据， 这里是异步执行的 excuste me? 我他喵的怎么知道是异步，要看里面的代码，这有点复杂呀
        NetRequest.getRequest(searchPictureUrl+searchWord, NetImageResult.class, new NetRequest.BeanCallback<NetImageResult>() {
            @Override
            public void onSuccess(NetImageResult response) {
                mDatas.clear();
                for(NetImage img : response.getItems())
                {
                    mDatas.add(img);
                }
                if (mDatas.size() > 0)
                {
                    SharedPreferences.Editor edit = preferences.edit();
                    for(int i = 0; i < mDatas.size(); i ++)
                    {

                        edit.putString("" + i, ((NetImage)mDatas.get(i)).getPic_url_noredirect());
                    }
                    edit.putInt("count", mDatas.size());
                    edit.commit();
                }
                myAdadpter.notifyDataSetChanged();
            }
            @Override
            public void onError(Exception exception, String errorInfo) {
                Toast.makeText(FrescoListActivity.this,"net error",Toast.LENGTH_SHORT).show();
//                recyclerAdapter = new ImageRecyclerAdapter(null,layoutSysle);
            }
        });
    }

    public void Search(View v)
    {
        searchPicture(edt1.getText().toString());
    }

    public void setMyAdadpter()
    {
            myAdadpter = new MyAdapter<NetImage>(mDatas,R.layout.item_fresco_list) {
                @Override
                public void bindView(ViewHolder holder, NetImage obj) {
                    SimpleDraweeView view = holder.getView(R.id.img_item_fresco_list_main);

                    //这一句不是很流畅，因为没有Resize
                    //view.setImageURI(obj.mUrl);

                    ImageRequest imageRequest =
                            ImageRequestBuilder.newBuilderWithSource(Uri.parse(obj.getPic_url_noredirect()))
                                    .setResizeOptions(
                                            new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))
                                    .setProgressiveRenderingEnabled(true)
                                    .build();
                    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequest)
                            .setAutoPlayAnimations(true)
                            .build();
                    view.setController(draweeController);
                }
            };

            lv.setAdapter(myAdadpter);



    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        mCount = mDatas.size();
//        if (mCount > 0){
//            outState.putInt("count", mCount);
//        };
//
//        for (int i = 0 ; i < mDatas.size(); i++)
//        {
//            outState.putString("" + i, ((NetImage)mDatas.get(i)).getPic_url_noredirect());
//        }
//    }

}
