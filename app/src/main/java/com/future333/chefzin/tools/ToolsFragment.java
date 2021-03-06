package com.future333.chefzin.tools;

/**
 * Created by manuel on 11/23/16.
 */

import android.app.Activity;

public class ToolsFragment {

    /*** Retorna el nombre de la iteracion puesta en fragmentTransaction.addToBackStack(Fragment.NAME);*/
    public static String getNameIteration(Activity ctx){
        int count = ctx.getFragmentManager().getBackStackEntryCount();
        return ctx.getFragmentManager().getBackStackEntryAt(count-1).getName();
    }

    /*** Retorna el nombre de la iteracion puesta en fragmentTransaction.addToBackStack(Fragment.NAME);*/
    public static String getNameIterationPrevious(Activity ctx){
        int count = ctx.getFragmentManager().getBackStackEntryCount();
        return ctx.getFragmentManager().getBackStackEntryAt(count-2).getName();
    }
}
