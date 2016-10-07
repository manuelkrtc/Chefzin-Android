package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.model.Controller.UserCtr;
import com.future333.chefzin.R;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.User;
import com.future333.chefzin.tools.ApiTools;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.tools.ViewTools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by manuel on 6/09/16.
 */
public class FragmentLogin extends Fragment {

    Activity    ctx;
    AppHandler  app;
    ToolsNotif  toolsNotif;

    //View
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etEmailLog;
    EditText etPasswordLog;
    EditText etPasswordConfirm;

    CheckBox checkTerm;

    Button btnLogIn;
    Button btnGmail;
    Button btnRegister;
    Button btnFacebook;

    TextView tvLogIn;
    TextView tvRegister;
    TextView tvRecoverPassword;

    LinearLayout zoneLogIn;
    LinearLayout zoneRegister;

    //Variables
    ArrayList<EditText> editTextsLogin;
    ArrayList<EditText> editTextsRegister;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

        toolsNotif = new ToolsNotif(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        checkTerm           = (CheckBox) v.findViewById(R.id.checkTerm);

        zoneLogIn           = (LinearLayout) v.findViewById(R.id.zoneLogIn);
        zoneRegister        = (LinearLayout) v.findViewById(R.id.zoneRegister);

        tvLogIn             = (TextView) v.findViewById(R.id.tvLogIn);
        tvRegister          = (TextView) v.findViewById(R.id.tvRegister);
        tvRecoverPassword   = (TextView) v.findViewById(R.id.tvRecoverPassword);

        btnLogIn            = (Button) v.findViewById(R.id.btnLogIn);
        btnGmail            = (Button) v.findViewById(R.id.btnGmail);
        btnRegister         = (Button) v.findViewById(R.id.btnRegister);
        btnFacebook         = (Button) v.findViewById(R.id.btnFacebook);

        etEmailLog          = (EditText) v.findViewById(R.id.etEmailLog);
        etPasswordLog       = (EditText) v.findViewById(R.id.etPasswordLog);
        etName              = (EditText) v.findViewById(R.id.etName);
        etEmail             = (EditText) v.findViewById(R.id.etEmail);
        etPassword          = (EditText) v.findViewById(R.id.etPassword);
        etPasswordConfirm   = (EditText) v.findViewById(R.id.etPasswordConfirm);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listen();
        createArraysEditText();
        isViewRegister(true);
    }

    private void listen(){
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isViewRegister(true);
            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isViewRegister(false);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailLog.getText().toString();
                String password = etPasswordLog.getText().toString();
                app.userCtr.logIn(ctx, email, password, new UserCtr.OnLogInListener() {
                    @Override
                    public void onSuccessful() {
                        ViewTools.msj(ctx,"Bienvenido " + app.userCtr.getUser().getNombres());
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String error) {
                        ViewTools.msj(ctx,error);
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).goFragmentProfile();
            }
        });
    }

    //----------------------------------- METHODS --------------------------------------------------

    /** determina si la vista es de registro o login */
    private void isViewRegister(boolean isViewRegister){
        if(isViewRegister){
            zoneLogIn.setVisibility(View.GONE);
            zoneRegister.setVisibility(View.VISIBLE);
            ViewTools.focusableForm(getActivity(),editTextsRegister);
        }else {
            zoneLogIn.setVisibility(View.VISIBLE);
            zoneRegister.setVisibility(View.GONE);
            ViewTools.focusableForm(getActivity(),editTextsLogin);
        }
    }

    /** crea los arreglos de editText tanto para login como para Registro */
    private void  createArraysEditText(){
        editTextsLogin      = new ArrayList<>();
        editTextsRegister   = new ArrayList<>();

        editTextsRegister.add(etName);
        editTextsRegister.add(etEmail);
        editTextsRegister.add(etPassword);
        editTextsRegister.add(etPasswordConfirm);

        editTextsLogin.add(etEmailLog);
        editTextsLogin.add(etPasswordLog);
    }

}
