package org.idwfed.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.idwfed.app.domain.Item;

import java.lang.reflect.Type;
import java.util.List;

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

    public static void setUserDocs(Context context, List<Item> items){
        Gson gson = new Gson();
        String json = gson.toJson(items);
        getPreferences(context).edit().putString("idwf.app.items", json).commit();
    }

    public static List<Item> getUserDocs(Context context){
        Type collectionType = new TypeToken<List<Item>>() {
        }.getType();
        return new Gson().fromJson(getPreferences(context).getString("idwf.app.items", ""), collectionType);
    }

}
