package org.idwfed.app.exception;

import android.util.Log;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public class LoginException extends RuntimeException {

    public LoginException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        Log.e("login", detailMessage, throwable);
    }

}
