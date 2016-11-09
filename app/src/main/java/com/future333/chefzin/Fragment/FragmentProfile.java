package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Controller.UserCtr;
import com.future333.chefzin.model.User;
import com.future333.chefzin.tools.ApiTools;
import com.future333.chefzin.tools.ImageLoader;
import com.future333.chefzin.tools.ViewTools;
import com.future333.chefzin.view.TextViewImage;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentProfile extends Fragment {

    Activity    ctx;
    AppHandler  app;

    //Views
    Button      btnLogOut;
    Button      btnConditions;
    Button      btnSettingData;
    Button      btnFrequentQuestions;
    TextView    tvName;
    ImageView   ivProfile;
    ViewGroup   zoneInfoUser;

    TextViewImage tviEmail;
    TextViewImage tviPhone;

    //instance
    ImageLoader _imageL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

        _imageL = new ImageLoader(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogOut            = (Button)v.findViewById(R.id.btnLogOut);
        btnConditions        = (Button)v.findViewById(R.id.btnConditions);
        btnSettingData       = (Button)v.findViewById(R.id.btnSettingData);
        btnFrequentQuestions = (Button)v.findViewById(R.id.btnFrequentQuestions);
        tvName               = (TextView)   v.findViewById(R.id.tvName);
        ivProfile            = (ImageView)  v.findViewById(R.id.ivProfile);
        zoneInfoUser         = (ViewGroup)  v.findViewById(R.id.zoneInfoUser);
        tviEmail             = (TextViewImage)v.findViewById(R.id.tviEmail);
        tviPhone             = (TextViewImage)v.findViewById(R.id.tviPhone);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(app.userCtr.getUser().getFoto() != null)
            _imageL.loadAndDisplayCircledImage(app.userCtr.getUser().getFoto(), ivProfile);

        listen();
        setInfo();
    }

    private void listen(){
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.userCtr.logOut(ctx, new ApiTools.OnLogOutListener() {
                    @Override
                    public void onSuccessful() {
                        ViewTools.msj(ctx,"Vuelve pronto.");
                        getActivity().onBackPressed();
                    }
                });
            }
        });

        btnSettingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewTools.switchVisibisilite(zoneInfoUser);
            }
        });
    }

    //----------------------------------- METHODS --------------------------------------------------
    private void setInfo(){
        User user = app.userCtr.getUser();
        if(user != null){
            if(user.getNombres() == null && user.getNombres() == null) tvName.setVisibility(View.GONE);
            else tvName.setText(user.getNombres() + " " + user.getApellidos() );

            if(user.getEmail() == null) tviEmail.setVisibility(View.GONE);
            else tviEmail.setText(user.getEmail());

            if(user.getTelefono() == null) tviPhone.setVisibility(View.GONE);
            else tviPhone.setText(user.getTelefono());
        }
    }
}
