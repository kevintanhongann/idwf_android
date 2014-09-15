package org.idwfed.app.exception;

import android.util.Log;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public class CreateWccDocException extends RuntimeException {

    public CreateWccDocException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        Log.e("CreateWccDoc", detailMessage, throwable);
    }
}
