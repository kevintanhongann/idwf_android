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

    public void setUsername(Context context, String username){
        getPreferences(context).edit().putString("idwf.app.username", username).commit();
    }

    public String getUsername(Context context){
        return getPreferences(context).getString("idwf.app.username", "");
    }
}
