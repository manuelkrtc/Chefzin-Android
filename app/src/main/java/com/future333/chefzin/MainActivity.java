package com.future333.chefzin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    RelativeLayout fragment;

    FragmentLogin   fragmentLogin;
    FragmentProfile fragmentProfile;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        inicializate();
        goFragmentLogin();
    }

    private void inicializate(){

        fragment = (RelativeLayout) findViewById(R.id.rlFragment);

        fragmentLogin   = new FragmentLogin();
        fragmentProfile = new FragmentProfile();
    }

    private void goFragmentLogin(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentLogin);
        fragmentTransaction.commit();
    }

    public void goFragmentProfile(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentProfile);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }
}
