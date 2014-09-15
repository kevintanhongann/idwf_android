package org.idwfed.app.api;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.idwfed.app.callback.LoginCallback;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public interface IDWFApi {
    void createWccDoc(String documentId);
    void updateWccDoc(String documentId);
    void login(Context context, String username, String password, String url, LoginCallback callback);
    void deleteWccDoc(String documentId);
    RequestQueue getRequestQueue(Context context);
    void getPublicDocuments(Context context);
}
