package org.idwfed.app.callback;

import org.idwfed.app.exception.CreateWccDocException;
import org.idwfed.app.response.CreateWccDocResponse;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public interface CreateWccDocumentCallback {
    void onFail(CreateWccDocException ex);
    void onSuccess(CreateWccDocResponse response);
}
