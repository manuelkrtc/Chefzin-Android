package com.future333.chefzin;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Controller.CtrChef;
import com.future333.chefzin.model.Controller.CtrHorary;
import com.future333.chefzin.model.Controller.CtrCart;
import com.future333.chefzin.model.Controller.CtrUser;

/**
 * Created by manuel on 3/10/16.
 */
public class AppHandler extends Application {

    public CtrChef chefCtr     = new CtrChef();
    public CtrUser userCtr     = new CtrUser();
    public CtrCart shopCart    = new CtrCart();
    public CtrHorary horaryCtr   = new CtrHorary();

    public Chef chefSelect   = new Chef();

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
