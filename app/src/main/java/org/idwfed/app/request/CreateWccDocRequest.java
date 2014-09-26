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
 * Created by kevintanhongann on 9/26/14.
 */
public class CreateWccDocRequest extends JsonObjectRequest {

    private String username;
    private String password;
    private Response.Listener<JSONObject> listener;


    public CreateWccDocRequest(int method, String url, String username, String password, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
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
