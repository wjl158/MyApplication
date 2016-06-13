package com.example.admin.myapplication.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.myapplication.R;

public class NavigationViewActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.dl_main_drawer);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nv_main_navigation);

        setupDrawerContent(navigationView);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void btnClick(View v)
    {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

}

