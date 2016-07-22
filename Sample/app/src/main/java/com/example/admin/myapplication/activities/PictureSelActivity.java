package com.example.admin.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.admin.myapplication.R;

import java.io.File;

public class PictureSelActivity extends AppCompatActivity {

    private static final int QUREST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_sel);
    }

    public void btnSelectPic(View v)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(intent, QUREST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == QUREST_CODE)
            {
                //临时图片保存目录
                File mTempDir = new File(Environment.getExternalStorageDirectory(), "Temp");
                if (!mTempDir.exists())
                {
                    mTempDir.mkdir();
                };

                //临时图片文件名
                String fileName = "Temp_" + String.valueOf( System.currentTimeMillis());

                File cropFile = new File( mTempDir, fileName);

                //用 文件 生成一个URI，即将这个临时图片和一个资源地址关联起来
                Uri outputUri = Uri.fromFile(cropFile);



                Intent cropIntent = new Intent();
                cropIntent.setData(data.getData());

                //
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
        }
    }
}
