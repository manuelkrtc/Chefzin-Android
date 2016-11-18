package com.future333.chefzin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.future333.chefzin.Fragment.FragmentCheckout;
import com.future333.chefzin.Fragment.FragmentChef;
import com.future333.chefzin.Fragment.FragmentHorary;
import com.future333.chefzin.Fragment.FragmentLogin;
import com.future333.chefzin.Fragment.FragmentMap;
import com.future333.chefzin.Fragment.FragmentProduct;
import com.future333.chefzin.Fragment.FragmentProfile;
import com.future333.chefzin.Fragment.FragmentRecord;
import com.future333.chefzin.model.User;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsSystem;
import com.future333.chefzin.tools.ToolsView;


public class MainActivity extends FragmentActivity {

    Activity    ctx;
    AppHandler  app;

    //View
    ImageView btnMenu;
    ImageView btnUpdate;
    ImageView btnProfile;
    RelativeLayout fragment;

    FragmentChef fragmentChef;
    FragmentLogin fragmentLogin;
    FragmentHorary fragmentHorary;
    FragmentProfile fragmentProfile;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializate();

        ToolsSystem.getHash(ctx);

        app.userCtr.getUserLocal(ctx);

        app.horaryCtr.getApiHorary(ctx, new ToolsApi.OnLogInListener() {
            @Override
            public void onSuccessful() {
                goFragmentHorary();
            }

            @Override
            public void onError(String error) {
                ToolsView.msj(ctx,error);
            }
        });
    }

    private void inicializate(){

        ctx = this;
        app = ((AppHandler)getApplication());

        btnMenu     = (ImageView) findViewById(R.id.btnMenu);
        btnUpdate   = (ImageView) findViewById(R.id.btnUpdate);
        btnProfile  = (ImageView) findViewById(R.id.btnProfile);
        fragment    = (RelativeLayout) findViewById(R.id.rlFragment);

        fragmentChef    = new FragmentChef();
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

        setToolbartEmpty();
    }

    public void goFragmentProfile(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentProfile);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        setToolbartEmpty();
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

    public void goFragmentMenu(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, new FragmentProduct());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void goFragmentCheckout(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentCheckout.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void goFragmentRecord(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentRecord.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        setToolbartEmpty();
    }

    private void goFragmentMap(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentMap.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        setToolbartEmpty();
    }

    @Override
    public void onBackPressed() {
        setToolbartFull();
        getFragmentManager().popBackStack();
    }

    //-------------------------------------- buttons Toolbar ---------------------------------------
    public void btnProfile(View view){
        User user = app.userCtr.getUser();
        if(user == null)    goFragmentLogin();
        else                goFragmentProfile();
    }

    public void btnReload(View view){
        goFragmentMap();

    }

    public void btnRecord(View view){
        goFragmentRecord();
    }

    private void setToolbartEmpty(){
        btnMenu.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
        btnProfile.setVisibility(View.GONE);
    }

    private void setToolbartFull(){
        btnMenu.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        btnProfile.setVisibility(View.VISIBLE);
    }
}
