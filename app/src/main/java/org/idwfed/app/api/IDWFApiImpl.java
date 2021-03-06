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
import org.idwfed.app.callback.FoldersCallback;
import org.idwfed.app.callback.LoginCallback;
import org.idwfed.app.callback.PublicDocumentsCallback;
import org.idwfed.app.callback.ValidatorCallback;
import org.idwfed.app.domain.Image;
import org.idwfed.app.exception.CreateWccDocException;
import org.idwfed.app.exception.FoldersException;
import org.idwfed.app.exception.LoginException;
import org.idwfed.app.exception.PublicDocumentsException;
import org.idwfed.app.exception.ValidatorException;
import org.idwfed.app.request.CreateWccDocRequest;
import org.idwfed.app.request.LoginRequest;
import org.idwfed.app.request.PublicDocumentsRequest;
import org.idwfed.app.response.FoldersResponse;
import org.idwfed.app.response.LoginResponse;
import org.idwfed.app.response.CreateWccDocResponse;
import org.idwfed.app.response.PublicDocumentsResponse;
import org.idwfed.app.response.ValidatorResponse;
import org.idwfed.app.util.Consts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public enum IDWFApiImpl implements IDWFApi {

    INSTANCE;

    private RequestQueue rq;

    @Override
    public void createWccDoc(Context context, String title, String description, String body, List<String> countries, String url, String uid, String username, String password, Image image, String sourceCaption, String sourceUrl, List<String> themes, final CreateWccDocumentCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("body", body);
            jsonObject.put("source_url", sourceUrl);
            jsonObject.put("source_caption", sourceCaption);
            if (themes != null && !themes.isEmpty()) {
                JSONArray themesJsonArray = new JSONArray(themes);
                //jsonObject.put("idwf_themes", themes.toArray(new String[themes.size()]));
                jsonObject.put("idwf_themes", themesJsonArray);
            }
            jsonObject.put("image_caption", image.getCaption());
            if (countries != null && !countries.isEmpty()) {
                JSONArray countriesJsonAry = new JSONArray(countries);
                //jsonObject.put("related_countries", countries.toArray(new String[countries.size()]));
                jsonObject.put("related_countries", countriesJsonAry);
            }
            JSONObject imageJsonObject = new JSONObject();
            imageJsonObject.put("data", image.getBase64Encode());
            imageJsonObject.put("filename", "string");
            imageJsonObject.put("content_type", image.getContentType());
            imageJsonObject.put("size", image.getSize());
            if (image.getBase64Encode() != null) {
                jsonObject.put("image", imageJsonObject);
            }
            Log.d(Consts.LOGTAG, "createWccDoc request " + jsonObject);
            CreateWccDocRequest request = new CreateWccDocRequest(Request.Method.POST, url, username, password, jsonObject, new Response.Listener<JSONObject>() {
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
    public void getFolders(Context context, String url, final FoldersCallback callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(Consts.LOGTAG,
                        "getFolders response " + response.toString());
                //parse response object to json
                Type type = new TypeToken<FoldersResponse>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ").serializeNulls().create();
                FoldersResponse foldersResponse = gson
                        .fromJson(response.toString(), type);
                Log.d(Consts.LOGTAG,
                        "foldersResponse " + foldersResponse);
                callback.onSuccess(foldersResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    callback.onFail(new FoldersException("Request timeout", error));
                } else if (error instanceof NoConnectionError) {
                    callback.onFail(new FoldersException("No connection", error));
                } else {
                    callback.onFail(new FoldersException(error.getMessage(), error));
                }
            }
        });

        getRequestQueue(context).add(request);
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
    public void getPublicDocuments(Context context, String url, final PublicDocumentsCallback callback) {
        //String url = "http://storyapi.dev.inigo-tech.com/@@API/plone/api/1.0/documents";
        Log.d(Consts.LOGTAG, "getPublicDocuments url "+url);
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

    @Override
    public void getPublicDocumentsWithAuth(Context context, String url, String username, String password, final PublicDocumentsCallback callback) {
        PublicDocumentsRequest request = new PublicDocumentsRequest(Request.Method.GET, url, username, password, null, new Response.Listener<JSONObject>() {
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

    @Override
    public void validateLogin(Context context, String url, final ValidatorCallback callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(Consts.LOGTAG,
                        "validateLogin response " + response.toString());
                //parse response object to json
                Type type = new TypeToken<ValidatorResponse>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ").serializeNulls().create();
                ValidatorResponse validatorResponse = gson
                        .fromJson(response.toString(), type);
                Log.d(Consts.LOGTAG,
                        "validatorResponse " + validatorResponse);
                callback.onSuccess(validatorResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    callback.onFail(new ValidatorException("Request timeout", error));
                }else if(error instanceof NoConnectionError){
                    callback.onFail(new ValidatorException("No internet connection", error));
                }else{
                    callback.onFail(new ValidatorException(error.getMessage(), error));
                }
            }
        });

        getRequestQueue(context).add(request);
    }


}
