package com.example.admin.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseActivity;

public class TestDemo extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo);

        Intent intent = getIntent();
        String string = intent.getStringExtra("abc");

        EditText edtText = (EditText)findViewById(R.id.editText);
        edtText.setText(string);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final EditText edtDial = (EditText) findViewById(R.id.edtDial);

        Button btn = (Button)findViewById(R.id.btnDial);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + edtDial.getText().toString()));
                startActivity(intent);
            }
        });


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                String str = btn.getText().toString();
                setTitle(str);
            }
        };


        findViewById(R.id.btnS1).setOnClickListener(listener);
        findViewById(R.id.btnS2).setOnClickListener(listener);
        findViewById(R.id.btnS3).setOnClickListener(listener);
    }

}
