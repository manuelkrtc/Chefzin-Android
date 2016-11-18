package com.future333.chefzin.tools;

/**
 * Created by manuel on 4/10/16.
 */
public class ToolsApi {

    final static String URL_BASE = "http://23.251.149.115/chefzin/";

    final static String URL_IMG      = URL_BASE + "imgpub/";
    final static String URL_SERVICES = URL_BASE + "api/v1/";

    public final static String URL_LOGIN                = URL_SERVICES + "login";
    public final static String URL_REGISTER             = URL_SERVICES + "register";
    public final static String URL_HORARIOS             = URL_SERVICES + "gethorariosfranjas/all";
    public final static String URL_INFO_USER            = URL_SERVICES + "getuser?api_token=";
    public final static String URL_REGISTER_GOOGLE      = URL_SERVICES + "register/google";
    public final static String URL_REGISTER_FACEBOOK    = URL_SERVICES + "register/facebook";
    public final static String URL_GET_CHEFZ            = URL_SERVICES + "getdata?id_horarios_franjas=";

    public final static String URL_IMG_CHEF             = URL_IMG + "chef/";
    public final static String URL_IMG_PLATO            = URL_IMG + "plato/";


    public interface OnLogInListener {
        public void onSuccessful();
        public void onError(String error);
    }

    public interface OnLogOutListener{
        public void onSuccessful();
    }

}
