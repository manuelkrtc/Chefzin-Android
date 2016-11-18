package com.future333.chefzin;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Controller.ChefCtr;
import com.future333.chefzin.model.Controller.HoraryCtr;
import com.future333.chefzin.model.Controller.ShopCart;
import com.future333.chefzin.model.Controller.UserCtr;

/**
 * Created by manuel on 3/10/16.
 */
public class AppHandler extends Application {

    public ChefCtr      chefCtr     = new ChefCtr();
    public UserCtr      userCtr     = new UserCtr();
    public ShopCart     shopCart    = new ShopCart();
    public HoraryCtr    horaryCtr   = new HoraryCtr();

    public Chef chefSelect   = new Chef();

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
