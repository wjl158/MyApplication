package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.admin.myapplication.base.BaseActivity;
import com.example.admin.myapplication.fragmet.Fragmentone;
import com.example.admin.myapplication.R;

public class Fragment1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1);

        Display dis = getWindowManager().getDefaultDisplay();
        if(dis.getWidth() > dis.getHeight())
        {
            Fragmentone f1 = new Fragmentone();
            getFragmentManager().beginTransaction().replace(R.id.rlLayoutFragment, f1).commit();
        }

        else
        {
            Fragmentone f2 = new Fragmentone();
            getFragmentManager().beginTransaction().replace(R.id.rlLayoutFragment, f2).commit();
        }



//        FragmentManager fManager = getSupportFragmentManager( );
//        FragmentTransaction fTransaction = fManager.beginTransaction();
//        Fragmentthree t1 = new Fragmentthree();
//        Fragmenttwo t2 = new Fragmenttwo();
//        Bundle bundle = new Bundle();
//        bundle.putString("key",id);
//        t2.setArguments(bundle);
//        fTransaction.add(R.id.fragmentRoot, t2, "~~~");
//        fTransaction.addToBackStack(t1);
//        fTransaction.commit();

    }
}
