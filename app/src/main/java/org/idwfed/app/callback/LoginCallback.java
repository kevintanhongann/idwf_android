package org.idwfed.app.callback;

import org.idwfed.app.exception.LoginException;
import org.idwfed.app.response.LoginResponse;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public interface LoginCallback {
    void onFail(LoginException ex);
    void onSuccess(LoginResponse response);
}
