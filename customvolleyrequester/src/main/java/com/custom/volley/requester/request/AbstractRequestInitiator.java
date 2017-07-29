package com.custom.volley.requester.request;

/**
 * Created by Riad on 27-07-17.
 */

import com.custom.volley.requester.fields.BaseRequestFields;
import com.custom.volley.requester.response.NetworkResponseHandler;

/**
 * Contains the basic methods needed to make a web request. Not volley related.
 * This could be seen as a level of modularization in-case we need to have different kinds of
 * initiators.
 * See {@link com.custom.volley.requester.VolleyInitiator}
 */
public abstract class AbstractRequestInitiator {
    public abstract <FinalResponse> void createRequest(
            BaseRequestFields<FinalResponse, String> baseRequestFields,
            NetworkResponseHandler<FinalResponse> networkResponseHandler);


    /**
     * To cancel pending requests
     */
    public abstract void cancelRequest(int requestId);
}
