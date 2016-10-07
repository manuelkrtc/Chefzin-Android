package com.future333.chefzin.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;


/**
 * Created by manuel on 14/10/15.
 */
public class ToolsNotif {

    Activity context;
    ProgressDialog progressDialog;


    public ToolsNotif(Activity context){
        this.context = context;
        progressDialog = new ProgressDialog(context);//inicializamos
    }

    //--------------------------------------------------- Progress Dialog --------------------------

    public void showDialogProgress(){

        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Por favor esperar");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void showDialogProgress(String title){

        progressDialog.setTitle(title);
        progressDialog.setMessage("Por favor esperar");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void showDialogProgress(String title, String msj){

        progressDialog.setTitle(title);
        progressDialog.setMessage(msj);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    
    public void cancelDialogProgress(){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
            }
        });
    }


    //---------------------------------------------------------------

    /**Muestra un mensaje y la opcion aceptar, la cual cierra el dialog*/
    public void dialogAceptar(String title, String msj, final Metodos metodos){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msj)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        metodos.paramAceptar();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**Muestra un mensaje y la opcion aceptar, la cual cierra el dialog*/
    public void dialogAceptar(String title, String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msj)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //-------------------------------------------------------------------

    /**Muestra un mensaje y la opcion aceptar, la cual cierra el dialog*/
    public void dialogAcepCan(String title, String msj, final Metodos metodos){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msj)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        metodos.paramAceptar();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //-------------------------------------------------------------------

    public interface Metodos{

        public void paramAceptar();

    }
}
















