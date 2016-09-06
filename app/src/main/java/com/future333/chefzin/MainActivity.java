package com.future333.chefzin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    RelativeLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = (RelativeLayout) findViewById(R.id.rlFragment);

        setFragment();

    }

    private void setFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentLogin fragmentLogin = new FragmentLogin();
        fragmentTransaction.add(R.id.rlFragment, fragmentLogin);
        fragmentTransaction.commit();
    }
}
