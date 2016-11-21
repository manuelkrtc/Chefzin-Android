package com.future333.chefzin.tools;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by manuel on 18/11/16.
 */

public class ToolsPermissions {

    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;


    /**
     * Verifica permisos de localizacion, y call onRequestPermissionsResult en Activity.
     */
    public static boolean enabledPermissionsLocation(Activity ctx){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            } else {
                ToolsView.msj(ctx,"Si desea activar los permisos de localización debes acceder a la configuración del dispositivo.");
            }
            return false;
        }
        return true;
    }

    /**
     * Verifica permisos de localizacion, y call onRequestPermissionsResult en Fragment.
     */
    public static boolean enabledPermissionsLocation(Activity ctx, Fragment fragment){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.ACCESS_FINE_LOCATION)){
                fragment.requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            } else {
                ToolsView.msj(ctx,"Si desea activar los permisos de localización debes acceder a la configuración del dispositivo.");
            }
            return false;
        }
        return true;
    }

    /**
     * Verifica si el gps esta activado, Si no lo lleva a configuracion..
     */
    public static void enableLocation(final Activity ctx){
        LocationManager lm = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
            dialog.setTitle("Chefzin requiere localización");
            dialog.setMessage("El GPS no se encuentra activado.");
            dialog.setPositiveButton("Ajustes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    ctx.startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }
}
