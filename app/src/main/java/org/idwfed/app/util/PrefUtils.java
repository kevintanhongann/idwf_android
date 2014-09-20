package org.idwfed.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public class PrefUtils {



    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("org.idwfed.app", Context.MODE_PRIVATE);
    }

    public static void setUsername(Context context, String username){
        getPreferences(context).edit().putString("idwf.app.username", username).commit();
    }

    public static String getUsername(Context context){
        return getPreferences(context).getString("idwf.app.username", "");
    }

    public static void setServerUrl(Context context, String serverUrl){
        getPreferences(context).edit().putString("idwf.app.serverurl", serverUrl).commit();
    }

    public static String getServerUrl(Context context){
        return getPreferences(context).getString("idwf.app.serverurl", "");
    }

    public static void setPassword(Context context, String password){
        getPreferences(context).edit().putString("idwf.app.password", password).commit();
    }

    public static String getPassword(Context context){
        return getPreferences(context).getString("idwf.app.password", "");
    }

}
