package org.idwfed.app.callback;

import org.idwfed.app.exception.PublicDocumentsException;
import org.idwfed.app.response.PublicDocumentsResponse;

/**
 * Created by kevintanhongann on 9/16/14.
 */
public interface PublicDocumentsCallback {
    void onFail(PublicDocumentsException exception);
    void onSuccess(PublicDocumentsResponse response);
}
