package org.idwfed.app.request;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.GsonBuilder;

import org.idwfed.app.response.LoginResponse;
import org.idwfed.app.util.Consts;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevintanhongann on 9/20/14.
 */
public class LoginRequest extends Request<LoginResponse> {


    private String username;

    private String password;

    private Response.Listener<LoginResponse> listener;

    public LoginRequest(int method, String url, String username, String password, Response.ErrorListener errorListener, Response.Listener<LoginResponse> listener) {
        super(method, url, errorListener);
        this.username = username;
        this.password = password;
        this.listener = listener;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", username, password);
        Log.d(Consts.LOGTAG, "credentials "+creds);
        String auth = "Basic "+Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }

    @Override
    protected Response<LoginResponse> parseNetworkResponse(NetworkResponse response) {
        Log.d(Consts.LOGTAG, "response.data "+response.data);
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.d(Consts.LOGTAG, "loginResponse " + json);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
            LoginResponse loginResponse = gsonBuilder.create().fromJson(json, LoginResponse.class);
            return Response.success(loginResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(e.getMessage(), e));
        }
    }

    @Override
    protected void deliverResponse(LoginResponse response) {
        Log.d(Consts.LOGTAG, "deliverResponse "+response);
        listener.onResponse(response);
    }
}
