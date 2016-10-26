package com.future333.chefzin.tools;

/**
 * Created by manuel on 4/10/16.
 */
public class ApiTools {

    public final static String URL_BASE = "http://23.251.149.115/chefzin/api/v1/";

    public final static String URL_LOGIN                = "login";
    public final static String URL_REGISTER             = "register";
    public final static String URL_HORARIOS             = "gethorariosfranjas/all";
    public final static String URL_INFO_USER            = "getuser?api_token=";
    public final static String URL_REGISTER_FACEBOOK    = "register/facebook";

    public interface OnLogInListener {
        public void onSuccessful();
        public void onError(String error);
    }

    public interface OnLogOutListener{
        public void onSuccessful();
    }

}
