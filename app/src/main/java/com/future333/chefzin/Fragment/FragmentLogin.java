package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.model.FormRegister;
import com.future333.chefzin.model.User;
import com.future333.chefzin.model.UserFacebook;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.tools.ToolsView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by manuel on 6/09/16.
 */
public class FragmentLogin extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    final static int RC_SIGN_IN_GMAIL    = 10;
    final static int RC_SIGN_IN_FACEBOOK = 20;

    Activity    ctx;
    AppHandler  app;
    ToolsNotif  toolsNotif;
    Fragment    thisFragment;

    //View
    EditText etName;
    EditText etEmail;
    EditText etLastName;
    EditText etPassword;
    EditText etTelephone;
    EditText etEmailLog;
    EditText etPasswordLog;
    EditText etPasswordConfirm;

    CheckBox checkTerm;

    Button btnLogIn;
    Button btnGoogle;
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

    CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());
        thisFragment    = this;
        toolsNotif      = new ToolsNotif(ctx);

        //facebook
        initApiFacebook();

        //gmail
       initApiGmail();
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
        btnGoogle           = (Button) v.findViewById(R.id.btnGoogle);
        btnRegister         = (Button) v.findViewById(R.id.btnRegister);
        btnFacebook         = (Button) v.findViewById(R.id.btnFacebook);

        etEmailLog          = (EditText) v.findViewById(R.id.etEmailLog);
        etPasswordLog       = (EditText) v.findViewById(R.id.etPasswordLog);
        etName              = (EditText) v.findViewById(R.id.etName);
        etEmail             = (EditText) v.findViewById(R.id.etEmail);
        etLastName          = (EditText) v.findViewById(R.id.etLastName);
        etPassword          = (EditText) v.findViewById(R.id.etPassword);
        etTelephone         = (EditText) v.findViewById(R.id.etTelephone);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN_FACEBOOK)
            callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_GMAIL) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();

                User user = new User();
                user.setUserGoogle(acct);

                app.userCtr.registerGoogle(ctx, user, new ToolsApi.OnLogInListener() {
                    @Override
                    public void onSuccessful() {
                        ToolsView.msj(ctx,"Bienvenido " + app.userCtr.getUser().getNombres());
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String error) {
                        ToolsView.msj(ctx,error);
                    }
                });

            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage((FragmentActivity) ctx);
        mGoogleApiClient.disconnect();
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

                app.userCtr.logIn(ctx, email, password, new ToolsApi.OnLogInListener() {
                    @Override
                    public void onSuccessful() {
                        ToolsView.msj(ctx,"Bienvenido " + app.userCtr.getUser().getNombres());
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String error) {
                        ToolsView.msj(ctx,error);
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormRegister formRegister = new FormRegister();
                formRegister.setNombres(etName.getText().toString());
                formRegister.setApellidos(etLastName.getText().toString());
                formRegister.setEmail(etEmail.getText().toString());
                formRegister.setPassword(etPassword.getText().toString());
                formRegister.setPassword_confirmation(etPasswordConfirm.getText().toString());
                formRegister.setTelefono(etTelephone.getText().toString());
                formRegister.setCheckTerm(checkTerm.isChecked());

                app.userCtr.register(ctx, formRegister, new ToolsApi.OnLogInListener() {
                    @Override
                    public void onSuccessful() {
                        ToolsView.msj(ctx,"Bienvenido " + app.userCtr.getUser().getNombres());
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String error) {
                        ToolsView.msj(ctx,error);
                    }
                });
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(thisFragment, Arrays.asList("public_profile", "user_friends"));

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN_GMAIL);
            }
        });
    }

    //----------------------------------- social networks --------------------------------------------------
    private void initApiFacebook(){
        FacebookSdk.sdkInitialize(ctx, RC_SIGN_IN_FACEBOOK);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                User user = new User();
                                user.setUserFacebook(new Gson().fromJson(object.toString(), UserFacebook.class));

                                app.userCtr.registerFacebook(ctx, user, new ToolsApi.OnLogInListener() {
                                    @Override
                                    public void onSuccessful() {
                                        ToolsView.msj(ctx,"Bienvenido " + app.userCtr.getUser().getNombres());
                                        getActivity().onBackPressed();
                                    }

                                    @Override
                                    public void onError(String error) {
                                        ToolsView.msj(ctx,error);
                                    }
                                });
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    private void initApiGmail(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .enableAutoManage((FragmentActivity) ctx /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    //----------------------------------- METHODS --------------------------------------------------

    /** determina si la vista es de registro o login */
    private void isViewRegister(boolean isViewRegister){
        if(isViewRegister){
            zoneLogIn.setVisibility(View.GONE);
            zoneRegister.setVisibility(View.VISIBLE);
            ToolsView.focusableForm(getActivity(),editTextsRegister);
        }else {
            zoneLogIn.setVisibility(View.VISIBLE);
            zoneRegister.setVisibility(View.GONE);
            ToolsView.focusableForm(getActivity(),editTextsLogin);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
