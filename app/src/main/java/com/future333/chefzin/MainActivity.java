package com.future333.chefzin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    RelativeLayout fragment;

    FragmentChef    fragmentChef;
    FragmentLogin   fragmentLogin;
    FragmentHorary  fragmentHorary;
    FragmentProfile fragmentProfile;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializate();
        goFragmentHorary();
    }

    private void inicializate(){

        fragment = (RelativeLayout) findViewById(R.id.rlFragment);

        fragmentChef    = new FragmentChef();
        fragmentLogin   = new FragmentLogin();
        fragmentHorary  = new FragmentHorary();
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

    public void goFragmentChef(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentChef);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void goFragmentHorary(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentHorary);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }
}
