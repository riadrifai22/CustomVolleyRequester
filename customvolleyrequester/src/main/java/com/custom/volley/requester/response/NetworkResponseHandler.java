package com.custom.volley.requester.response;

/**
 * Created by Riad on 27-07-17.
 */

import com.custom.volley.requester.error.NetworkConnectionResponseError;

/**
 * The contacting layer between Volley and UI layer.
 * @param <T> Final response returned upon success
 */
public abstract class NetworkResponseHandler <T> {
    /**
     * Success of a call with the returned response.
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * To notify the UI that the required request has been added to the request queue in case we need
     * to cancel it later.
     * Always called AFTER the request has been added to the queue.
     *
     * Override when passing the handler to access the request id.
     * @param requestId The unique requestId for the request. Use it to cancel the generated request.
     */
    public void onRequestAddedToQueue(int requestId){}

    /**
     * In case of library error; by default, this library logs the error with the passed volley tag
     * in {@link com.custom.volley.requester.fields.BaseRequestFields#volleyRequestsTag}
     * Override when passing the handler to access the error.
     * @param connectionError
     */
    public void onConnectionFailed(NetworkConnectionResponseError connectionError){}
}
