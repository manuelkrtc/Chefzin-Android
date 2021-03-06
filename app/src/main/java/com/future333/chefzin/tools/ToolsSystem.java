package com.future333.chefzin.tools;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by manuel on 10/26/16.
 */
public class ToolsSystem {

    public static void getHash(Activity ctx){
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(
                    "com.future333.chefzin",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    //---------------------------------- Version Name ----------------------------------------------
    public static void getVersionName(Activity ctx, TextView tv){
        String versionName = getVersionName(ctx);
        if(versionName == null) tv.setVisibility(View.GONE);
        else tv.setText(versionName);
    }

    public static String getVersionName(Activity ctx){
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkMinimumVersion(Activity ctx, String minimumVersion){
        String version = getVersionName(ctx);
        if(version != null){
            int[] versionArray           = ToolsFormat.arrayString_to_arrayInt(version.split("\\."));
            int[] minimumVersionArray    = ToolsFormat.arrayString_to_arrayInt(minimumVersion.split("\\."));

            if(versionArray.length!=3 || minimumVersionArray.length!=3) {
                Log.e("Version","Formato de version incorrecto");
                return false;
            }

            if(versionArray[0] > minimumVersionArray[0]) return true;
            if(versionArray[0] < minimumVersionArray[0]) return false;
            if(versionArray[0] == minimumVersionArray[0]) {
                if(versionArray[1] > minimumVersionArray[1]) return true;
                if(versionArray[1] < minimumVersionArray[1]) return false;
                if(versionArray[1] == minimumVersionArray[1]) {
                    if(versionArray[2] >= minimumVersionArray[2]) return true;
                    if(versionArray[2] < minimumVersionArray[2]) return false;
                }
            }
        }
        Log.e("Version","La version no coincidio en ninguna verificacion");
        return false;
    }

}
