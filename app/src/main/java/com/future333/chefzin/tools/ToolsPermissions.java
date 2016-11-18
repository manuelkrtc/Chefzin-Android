package com.future333.chefzin.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by manuel on 18/11/16.
 */

public class ToolsPermissions {

    static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;


    public static boolean checkPermissionsLocation(Activity ctx){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            } else {
                ToolsView.msj(ctx,"La cago tiene q activar esta mierda manualmente");
            }
            return false;
        }
        return true;
    }

}
