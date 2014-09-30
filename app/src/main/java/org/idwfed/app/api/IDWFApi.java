package org.idwfed.app.api;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.idwfed.app.callback.CreateWccDocumentCallback;
import org.idwfed.app.callback.FoldersCallback;
import org.idwfed.app.callback.LoginCallback;
import org.idwfed.app.callback.PublicDocumentsCallback;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public interface IDWFApi {
    void getFolders(Context context, String url, FoldersCallback callback);
    void createWccDoc(Context context, String title, String description, String body, String country, String url, String uid, String username, String password, String imageData, String imageContentType, String imageCaption, String sourceCaption, String sourceUrl, String theme, final CreateWccDocumentCallback callback);
    void updateWccDoc(String documentId);
    void login(Context context, String username, String password, String url, LoginCallback callback);
    void deleteWccDoc(String documentId);
    RequestQueue getRequestQueue(Context context);
    void getPublicDocuments(Context context, final PublicDocumentsCallback callback);
}
