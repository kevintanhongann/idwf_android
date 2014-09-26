package org.idwfed.app.callback;

import org.idwfed.app.exception.FoldersException;
import org.idwfed.app.response.FoldersResponse;

/**
 * Created by kevintanhongann on 9/25/14.
 */
public interface FoldersCallback {
    void onFail(FoldersException exception);
    void onSuccess(FoldersResponse response);
}
