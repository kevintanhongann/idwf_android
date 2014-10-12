package org.idwfed.app.callback;

import org.idwfed.app.exception.ValidatorException;
import org.idwfed.app.response.ValidatorResponse;

/**
 * Created by kevintanhongann on 10/3/14.
 */
public interface ValidatorCallback {
    void onFail(ValidatorException ex);
    void onSuccess(ValidatorResponse response);
}
