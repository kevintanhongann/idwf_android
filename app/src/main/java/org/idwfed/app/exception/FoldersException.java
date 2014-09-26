package org.idwfed.app.exception;

import android.util.Log;

/**
 * Created by kevintanhongann on 9/25/14.
 */
public class FoldersException extends RuntimeException{

    public FoldersException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        Log.e("folders", detailMessage, throwable);
    }
}
