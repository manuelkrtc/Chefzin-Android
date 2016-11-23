package com.future333.chefzin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.future333.chefzin.tools.ToolsFragment;
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

        app.ctrUser.getUserLocal(ctx);

        app.ctrHorary.getApiHorary(ctx, new ToolsApi.OnLogInListener() {
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
    public void goFragmentLogin(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentLogin.newInstance());
        fragmentTransaction.addToBackStack(FragmentLogin.NAME);
        fragmentTransaction.commit();

        setToolbartEmpty();
    }

    public void goFragmentProfile(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentProfile);
        fragmentTransaction.addToBackStack(FragmentProfile.NAME);
        fragmentTransaction.commit();

        setToolbartEmpty();
    }

    public void goFragmentChef(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentChef);
        fragmentTransaction.addToBackStack(FragmentChef.NAME);
        fragmentTransaction.commit();
    }

    public void goFragmentHorary(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, fragmentHorary);
        fragmentTransaction.addToBackStack(FragmentHorary.NAME);
        fragmentTransaction.commit();
    }

    public void goFragmentMenu(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, new FragmentProduct());
        fragmentTransaction.addToBackStack(FragmentProduct.NAME);
        fragmentTransaction.commit();
    }

    public void goFragmentCheckout(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentCheckout.newInstance());
        fragmentTransaction.addToBackStack(FragmentCheckout.NAME);
        fragmentTransaction.commit();
    }

    private void goFragmentRecord(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentRecord.newInstance());
        fragmentTransaction.addToBackStack(FragmentRecord.NAME);
        fragmentTransaction.commit();

        setToolbartEmpty();
    }

    private void goFragmentMap(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, FragmentMap.newInstance());
        fragmentTransaction.addToBackStack(FragmentMap.NAME);
        fragmentTransaction.commit();

        setToolbartEmpty();
    }

    @Override
    public void onBackPressed() {

        if(ToolsFragment.getFragmentCurrent(ctx).equals(FragmentHorary.NAME)) return;
        getFragmentManager().popBackStack();

        setToolbar();
    }

    private void setToolbar(){

        String nameFragmentCurrent = ToolsFragment.getFragmentCurrent(ctx);

        if(nameFragmentCurrent.equals(FragmentHorary.NAME)) setToolbartHome();

        if(     nameFragmentCurrent.equals(FragmentCheckout.NAME)   ||
                nameFragmentCurrent.equals(FragmentChef.NAME)       ||
                nameFragmentCurrent.equals(FragmentLogin.NAME)      ||
                nameFragmentCurrent.equals(FragmentMap.NAME)        ||
                nameFragmentCurrent.equals(FragmentProduct.NAME)    ||
                nameFragmentCurrent.equals(FragmentProfile.NAME)    ||
                nameFragmentCurrent.equals(FragmentRecord.NAME)     ||
                nameFragmentCurrent.equals(FragmentHorary.NAME)     ||
                nameFragmentCurrent.equals(FragmentHorary.NAME)     ||
                nameFragmentCurrent.equals(FragmentHorary.NAME)     ||
                nameFragmentCurrent.equals(FragmentHorary.NAME)
                ) setToolbartHome();
    }

    //-------------------------------------- buttons Toolbar ---------------------------------------
    public void btnProfile(View view){
        User user = app.ctrUser.getUser();
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

    private void setToolbartHome(){
        btnMenu.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        btnProfile.setVisibility(View.VISIBLE);
    }

    private void setDisableLogin(){
        btnMenu.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        btnProfile.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case ToolsPermissions.MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    ToolsView.msj(ctx,"Cago1");
//                } else {
//                    ToolsView.msj(ctx,"cago2");
//                }
//                return;
//            }
//
//        }
    }


}
