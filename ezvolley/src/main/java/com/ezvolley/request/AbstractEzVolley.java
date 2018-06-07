package com.ezvolley.request;

/**
 * Created by Riad on 27-07-17.
 */

import com.ezvolley.EzVolley;
import com.ezvolley.fields.BaseEzFields;
import com.ezvolley.response.NetworkResponseHandler;

/**
 * Contains the basic methods needed to make a web request. Not volley related.
 * This could be seen as a level of modularization in-case we need to have different kinds of
 * initiators.
 * See {@link EzVolley}
 */
public abstract class AbstractEzVolley {
    public abstract <FinalResponse> void createRequest(
            BaseEzFields<FinalResponse, String> baseEzFields,
            NetworkResponseHandler<FinalResponse> networkResponseHandler);


    /**
     * To cancel pending requests
     */
    public abstract void cancelRequest(int requestId);
}
