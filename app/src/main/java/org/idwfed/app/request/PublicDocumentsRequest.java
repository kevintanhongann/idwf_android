package org.idwfed.app.request;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.idwfed.app.util.Consts;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevintanhongann on 10/3/14.
 */
public class PublicDocumentsRequest extends JsonObjectRequest {

    private String username;
    private String password;

    public PublicDocumentsRequest(int method, String url, String username, String password, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.username = username;
        this.password = password;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", username, password);
        Log.d(Consts.LOGTAG, "credentials " + creds);
        String auth = "Basic "+ Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
}
