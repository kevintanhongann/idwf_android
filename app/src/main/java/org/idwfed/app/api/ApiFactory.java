package org.idwfed.app.api;

/**
 * Created by kevintanhongann on 9/14/14.
 */
public enum ApiFactory {

    INSTANCE;

    public IDWFApi getIDWFApi(){
        return IDWFApiImpl.INSTANCE;
    }

}
