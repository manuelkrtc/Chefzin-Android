package com.future333.chefzin;

import android.app.Application;

import com.future333.chefzin.model.Controller.HoraryCtr;
import com.future333.chefzin.model.Controller.UserCtr;

/**
 * Created by manuel on 3/10/16.
 */
public class AppHandler extends Application {

    public UserCtr      userCtr    = new UserCtr();
    public HoraryCtr    horaryCtr  = new HoraryCtr();

}
