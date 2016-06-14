package com.example.admin.myapplication.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.google.gson.Gson;

public class MDAppBarLayout1Activity extends AppCompatActivity {
    private String mUrl;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mIvBook;
//    private BookBean mBookBean;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvRating;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdapp_bar_layout1);

        //设置Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        getSupportActionBar().setTitle("呕 我的上帝");
        getSupportActionBar().setSubtitle("呕 我的上帝oh");
        mToolbar.setTitle("呕 我的上帝11111");
        mToolbar.setSubtitle("呕 我的上帝oh22222");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
//        mIvBook = (ImageView) findViewById(R.id.iv_book_image);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("作者信息"));
        mTabLayout.addTab(mTabLayout.newTab().setText("章节"));
        mTabLayout.addTab(mTabLayout.newTab().setText("书籍简介"));

    }

//    @Override
//    protected int setLayoutResourseID() {
//        return R.layout.activity_book_detail;
//    }
//    @Override
//    protected void init() {
//        mUrl = getIntent().getStringExtra("url");
//    }
//    @Override
//    protected void initData() {
//        SolidHttpUtils.getInstance().loadString(mUrl, new SolidHttpUtils.HttpCallBack() {
//            @Override
//            public void onLoading() {
//            }
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                mBookBean = gson.fromJson(result, BookBean.class);
//                mCollapsingToolbarLayout.setTitle(mBookBean.getTitle());
//                mTvTitle.setText(mBookBean.getTitle());
//                mTvMsg.setText(mBookBean.getAuthor() + "/" + mBookBean.getPublisher() + "/" + mBookBean.getPubdate());
//                mTvRating.setText(mBookBean.getRating().getAverage() + "分");
//                SolidHttpUtils.getInstance().loadImage(mBookBean.getImages().getLarge(), mIvBook);
//                BookInfoPageAdapter adapter = new BookInfoPageAdapter(BookDetailActivity.this, mBookBean, getSupportFragmentManager());
//                mViewPager.setAdapter(adapter);
//                mTabLayout.setupWithViewPager(mViewPager);
//            }
//            @Override
//            public void onError(Exception e) {
//            }
//        });
//    }
//
}
