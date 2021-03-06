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
    public final static String URL_INFO_USER            = URL_SERVICES + "getuser?api_token=";
    public final static String URL_REGISTER_GOOGLE      = URL_SERVICES + "register/google";
    public final static String URL_REGISTER_FACEBOOK    = URL_SERVICES + "register/facebook";

    public final static String URL_GET_CHEFZ            = URL_SERVICES + "getdata?id_horarios_franjas=";
    public final static String URL_GET_HORARIOS         = URL_SERVICES + "gethorariosfranjas/all";
    public final static String URL_GET_COBERTURA        = URL_SERVICES + "cobertura?ciudad=bogota";
    public final static String URL_GET_VERSION          = URL_SERVICES + "build/beta/last?plataforma=android&tipo_app=user";

    public final static String URL_ORDEN_CREATE         = URL_SERVICES + "orden/crear";
    public final static String URL_ORDEN_PRODUCT_ADD    = URL_SERVICES + "orden/plato/add";
    public final static String URL_ORDEN_PRODUCT_DELETE = URL_SERVICES + "orden/plato/delete";
    public final static String URL_ORDEN_CHECKOUT       = URL_SERVICES + "orden/checkout";

    public final static String URL_USER_RECORD          = URL_SERVICES + "orden/gethistorial/usuario?api_token=";

    public final static String URL_ADDRESS_CREATE       = URL_SERVICES + "direccion/crear";
    public final static String URL_ADDRESS_GET          = URL_SERVICES + "direccion/get?api_token=";

    public final static String URL_IMG_CHEF             = URL_IMG + "chef/";
    public final static String URL_IMG_PLATO            = URL_IMG + "plato/";


    public interface OnApiListenerError {
        public void onSuccessful();
        public void onError(String error);
    }

    public interface OnApiListener {
        public void onSuccessful();
    }

}
