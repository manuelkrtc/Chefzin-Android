package com.future333.chefzin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by manuel on 6/09/16.
 */
public class FragmentLogin extends Fragment {

    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etEmailLog;
    EditText etPasswordLog;
    EditText etPasswordConfirm;

    CheckBox checkTerm;

    Button btnLogIn;
    Button btnRegister;

    Button btnGmail;
    Button btnTwitter;
    Button btnFacebook;

    TextView tvLogIn;
    TextView tvRegister;
    TextView tvRecoverPassword;

    LinearLayout zoneLogIn;
    LinearLayout zoneRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        inicializate(view);

        isViewRegister(true);

        return view;
    }

    private void inicializate(View v){
        etName              = (EditText) v.findViewById(R.id.etName);
        etEmail             = (EditText) v.findViewById(R.id.etEmail);
        etPassword          = (EditText) v.findViewById(R.id.etPassword);
        etEmailLog          = (EditText) v.findViewById(R.id.etEmailLog);
        etPasswordLog       = (EditText) v.findViewById(R.id.etPasswordLog);
        etPasswordConfirm   = (EditText) v.findViewById(R.id.etPasswordConfirm);

        checkTerm = (CheckBox) v.findViewById(R.id.checkTerm);

        btnLogIn    = (Button) v.findViewById(R.id.btnLogIn);
        btnRegister = (Button) v.findViewById(R.id.btnRegister);

        btnGmail    = (Button) v.findViewById(R.id.btnGmail);
        btnTwitter  = (Button) v.findViewById(R.id.btnTwitter);
        btnFacebook = (Button) v.findViewById(R.id.btnFacebook);

        tvLogIn             = (TextView) v.findViewById(R.id.tvLogIn);
        tvRegister          = (TextView) v.findViewById(R.id.tvRegister);
        tvRecoverPassword   = (TextView) v.findViewById(R.id.tvtvRecoverPassword);

        zoneLogIn       = (LinearLayout) v.findViewById(R.id.zoneLogIn);
        zoneRegister    = (LinearLayout) v.findViewById(R.id.zoneRegister);

        listen();
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
    }

    private void isViewRegister(boolean isViewRegister){
        if(isViewRegister){
            zoneLogIn.setVisibility(View.GONE);
            zoneRegister.setVisibility(View.VISIBLE);
        }else {
            zoneLogIn.setVisibility(View.VISIBLE);
            zoneRegister.setVisibility(View.GONE);
        }
    }
}
