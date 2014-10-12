package org.idwfed.app.exception;

import android.util.Log;

/**
 * Created by kevintanhongann on 10/3/14.
 */
public class ValidatorException extends RuntimeException {

    public ValidatorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        Log.e("validator", detailMessage, throwable);
    }
}
