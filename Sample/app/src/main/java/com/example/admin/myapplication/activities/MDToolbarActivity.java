package com.example.admin.myapplication.activities;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.view.DividerGridItemDecoration;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MDToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mdtoolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_activity_mdtoolbar_toolbar);

        // App Logo
        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle("张三");
        // Sub Title
        toolbar.setSubtitle("今天天气不错");

        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //菜单事件方法一
    public void onComposeAction(MenuItem mi) {
        // handle click here

        Snackbar.make(findViewById(R.id.tb_activity_mdtoolbar_toolbar), "有点意思", Snackbar.LENGTH_SHORT).show();
    }

    //菜单事件方法二
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                Snackbar.make(findViewById(R.id.tb_activity_mdtoolbar_toolbar), "有点意思1", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.miProfile:
                Snackbar.make(findViewById(R.id.tb_activity_mdtoolbar_toolbar), "有点意思2", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.miProfile1:
                Snackbar.make(findViewById(R.id.tb_activity_mdtoolbar_toolbar), "有点意思3", Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
