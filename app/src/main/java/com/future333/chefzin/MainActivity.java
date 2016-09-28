package com.future333.chefzin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    ImageView btnMenu;
    ImageView btnUpdate;
    ImageView btnProfile;
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

        btnMenu     = (ImageView) findViewById(R.id.btnMenu);
        btnUpdate   = (ImageView) findViewById(R.id.btnUpdate);
        btnProfile  = (ImageView) findViewById(R.id.btnProfile);
        fragment    = (RelativeLayout) findViewById(R.id.rlFragment);

        fragmentChef    = new FragmentChef();
//        fragmentLogin   = new FragmentLogin();
        fragmentLogin   = FragmentLogin.newInstance();
        fragmentHorary  = new FragmentHorary();
        fragmentProfile = new FragmentProfile();
    }


    //--------------------------------------- goFragments ------------------------------------------
    private void goFragmentLogin(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentLogin.newInstance());
        fragmentTransaction.addToBackStack(null);
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
        fragmentTransaction.addToBackStack(null);
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

    //-------------------------------------- buttons Toolbar ---------------------------------------
    public void btnProfile(View view){
        goFragmentLogin();
    }

    private void setToolbartEmpty(){
        btnMenu.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
        btnProfile.setVisibility(View.GONE);
    }

    private void setToolbartFull(){
        btnMenu.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
        btnProfile.setVisibility(View.GONE);
    }
}
