package com.future333.chefzin;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.future333.chefzin.tools.ViewTools;

import java.util.ArrayList;

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

    ArrayList<EditText> editTextsLogin;
    ArrayList<EditText> editTextsRegister;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

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

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                ((MainActivity)getActivity()).goFragmentProfile();
            }
        });

    }

    private void isViewRegister(boolean isViewRegister){
        if(isViewRegister){
            zoneLogIn.setVisibility(View.GONE);
            zoneRegister.setVisibility(View.VISIBLE);
//            ViewTools.focusableEditText(getActivity(),etName);
            ViewTools.focusableForm(getActivity(),editTextsRegister);
        }else {
            zoneLogIn.setVisibility(View.VISIBLE);
            zoneRegister.setVisibility(View.GONE);
//            ViewTools.focusableEditText(getActivity(),etEmailLog);
            ViewTools.focusableForm(getActivity(),editTextsLogin);
        }
    }


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
