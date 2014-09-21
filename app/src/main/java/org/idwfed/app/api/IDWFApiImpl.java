package org.idwfed.app.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.idwfed.app.callback.CreateWccDocumentCallback;
import org.idwfed.app.callback.LoginCallback;
import org.idwfed.app.callback.PublicDocumentsCallback;
import org.idwfed.app.exception.CreateWccDocException;
import org.idwfed.app.exception.LoginException;
import org.idwfed.app.exception.PublicDocumentsException;
import org.idwfed.app.request.LoginRequest;
import org.idwfed.app.response.LoginResponse;
import org.idwfed.app.response.CreateWccDocResponse;
import org.idwfed.app.response.PublicDocumentsResponse;
import org.idwfed.app.util.Consts;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public enum IDWFApiImpl implements IDWFApi {

    INSTANCE;

    private RequestQueue rq;


    @Override
    public void createWccDoc(Context context, String title, String description, String body, String country, String url, final CreateWccDocumentCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("effective", "2001-05-11");
            jsonObject.put("expires", "2015-12-11");
            jsonObject.put("subjects", "[]");
            jsonObject.put("document_type", "Letter");
            jsonObject.put("document_owner", "admin");
            jsonObject.put("text", body);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(Consts.LOGTAG,
                            "createWccDoc response " + response.toString());
                    //parse response object to json
                    Type type = new TypeToken<CreateWccDocResponse>() {
                    }.getType();
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ").serializeNulls().create();
                    CreateWccDocResponse createWccDocResponse = gson
                            .fromJson(response.toString(), type);
                    Log.d(Consts.LOGTAG,
                            "createWccDocResponse " + createWccDocResponse);
                    callback.onSuccess(createWccDocResponse);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError) {
                        callback.onFail(new CreateWccDocException("Request timeout", error));
                    } else if (error instanceof NoConnectionError) {
                        callback.onFail(new CreateWccDocException("No connection", error));
                    } else {
                        callback.onFail(new CreateWccDocException(error.getMessage(), error));
                    }
                }
            });
            getRequestQueue(context).add(request);
        } catch (JSONException e) {
            callback.onFail(new CreateWccDocException(e.getMessage(), e));
        }

    }

    @Override
    public void updateWccDoc(String documentId) {

    }

    @Override
    public void login(Context context, String username, String password, String url, final LoginCallback callback) {
        LoginRequest loginRequest = new LoginRequest(Request.Method.GET, url, username, password, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    callback.onFail(new LoginException("Request timeout", error));
                } else if (error instanceof NoConnectionError) {
                    callback.onFail(new LoginException("No connnection", error));
                } else {
                    callback.onFail(new LoginException(error.getMessage(), error));
                }
            }
        }, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                callback.onSuccess(response);
            }
        });

        getRequestQueue(context).add(loginRequest);
    }

    @Override
    public void deleteWccDoc(String documentId) {

    }

    @Override
    public synchronized RequestQueue getRequestQueue(Context context) {
        if (rq == null) {
            rq = Volley.newRequestQueue(context);
        }
        return rq;
    }

    @Override
    public void getPublicDocuments(Context context, final PublicDocumentsCallback callback) {
        String url = "http://storyapi.dev.inigo-tech.com/@@API/plone/api/1.0/documents";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(Consts.LOGTAG,
                        "getPublicDocuments response " + response.toString());
                //parse response object to json
                Type type = new TypeToken<PublicDocumentsResponse>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ").serializeNulls().create();
                PublicDocumentsResponse publicDocumentsResponse = gson
                        .fromJson(response.toString(), type);
                Log.d(Consts.LOGTAG,
                        "publicDocumentsResponse " + publicDocumentsResponse);
                callback.onSuccess(publicDocumentsResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    callback.onFail(new PublicDocumentsException("Request timeout", error));
                } else if (error instanceof NoConnectionError) {
                    callback.onFail(new PublicDocumentsException("No connection", error));
                } else {
                    callback.onFail(new PublicDocumentsException(error.getMessage(), error));
                }
            }
        });

        getRequestQueue(context).add(request);
    }


}
