package org.idwfed.app.exception;

import android.util.Log;

/**
 * Created by kevintanhongann on 9/16/14.
 */
public class PublicDocumentsException extends RuntimeException {

    public PublicDocumentsException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        Log.e("public documents", detailMessage, throwable);
    }
}
