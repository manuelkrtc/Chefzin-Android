package com.future333.chefzin;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Controller.CtrApp;
import com.future333.chefzin.model.Controller.CtrChef;
import com.future333.chefzin.model.Controller.CtrHorary;
import com.future333.chefzin.model.Controller.CtrCart;
import com.future333.chefzin.model.Controller.CtrUser;

/**
 * Created by manuel on 3/10/16.
 */
public class AppHandler extends Application {

    public CtrChef      ctrChef     = new CtrChef();
    public CtrUser      ctrUser     = new CtrUser();
    public CtrCart      ctrCart     = new CtrCart();
    public CtrHorary    ctrHorary   = new CtrHorary();
    public CtrApp       ctrApp      = new CtrApp();

    public Chef chefSelect   = new Chef();

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
