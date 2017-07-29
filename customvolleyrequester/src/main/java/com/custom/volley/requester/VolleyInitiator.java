package com.custom.volley.requester;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.custom.volley.requester.fields.BaseRequestFields;
import com.custom.volley.requester.request.AbstractRequestInitiator;
import com.custom.volley.requester.request.CustomVolleyRequest;
import com.custom.volley.requester.request.SimpleVolleyRequest;
import com.custom.volley.requester.response.NetworkResponseHandler;

/**
 * Created by Riad on 27-07-17.
 */

/**
 * Inherit from this class incase you'd like to create different types of requests. NOTE: Your request though
 * must inherit from {@link CustomVolleyRequest}
 */
public class VolleyInitiator extends AbstractRequestInitiator {

    /**
     * A single volley request queue instance initialized in {@link #init(Application)}.
     */
    private static RequestQueue requestQueue;

    /**
     * The socket timeout is set to Volley's default timeout.
     * See {@link #setConnectionTimeOut(int)} in-case you want to have your own time-out.
     */
    private static int TIMEOUT = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;

    /**
     * Max number of connection retries in-case of error. Set to Volley's default max.
     * See {@link #setMaxRetries(int)} to change the max number of retries.
     */
    private static int MAX_RETRIES = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

    /**
     * Back off multiplier, set by default to volley's default multiplier.
     * See {@link #setBackoffMult(float)} to change the multiplier.
     */
    private static float BACKOFF_MULT = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

    /**
     * An id that will be incremented after each request has been made. The id will be delivered
     * to the main thread through {@link NetworkResponseHandler#onRequestAddedToQueue(int)} in-case
     * we'd like to cancel the request later.
     */
    private static int requestId = 0;


    public static void init(Application application) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(application.getApplicationContext());
    }

    public static void setConnectionTimeOut(int timeOut) {
        TIMEOUT = timeOut;
    }

    public static void setMaxRetries(int maxRetries) {
        MAX_RETRIES = maxRetries;
    }

    public static void setBackoffMult(float backoffMult) {
        BACKOFF_MULT = backoffMult;
    }

    /**
     * 1) Sets the retry policy for the request. See {@link #TIMEOUT}, {@link #MAX_RETRIES}, and {@link #BACKOFF_MULT}
     *
     * 2) Increments the request id and connects the current request with its new id
     * using {@link CustomVolleyRequest#setTag(Object)}
     *
     * 3) Add the request to the queue.
     *
     * 4) Notifies {@link NetworkResponseHandler#onRequestAddedToQueue(int)}.
     */
    private synchronized <FinalResponse, IntermediateResponse> void addToRequestQueue(
            CustomVolleyRequest<FinalResponse, IntermediateResponse> customVolleyRequest,
            NetworkResponseHandler<FinalResponse> networkResponseHandler) {

        customVolleyRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES, BACKOFF_MULT));

        //if (customVolleyRequest.getMethod() == Request.Method.POST) customVolleyRequest.setShouldCache(false);

        requestId++;

        customVolleyRequest.setTag(requestId);

        requestQueue.add(customVolleyRequest);
        networkResponseHandler.onRequestAddedToQueue(requestId);
    }

    /**
     * This method is called to create the request and send to server.
     * By default, this method creates a {@link SimpleVolleyRequest}.
     * In case you'd like to use your custom request that inherits from {@link CustomVolleyRequest},
     * inherit {@link VolleyInitiator} and override {@link #createRequest(BaseRequestFields, NetworkResponseHandler)}
     */
    @Override
    public <FinalResponse> void createRequest(BaseRequestFields<FinalResponse, String> baseRequestFields,
                                              NetworkResponseHandler<FinalResponse> networkResponseHandler) {
        SimpleVolleyRequest<FinalResponse> simpleVolleyRequest = new SimpleVolleyRequest<>(
                baseRequestFields, networkResponseHandler);

        addToRequestQueue(simpleVolleyRequest, networkResponseHandler);
    }

    /**
     * To cancel pending requests still present in the request queue.
     * To access the requestId, see {@link NetworkResponseHandler#onRequestAddedToQueue(int)}
     * @param requestId the id to remove from the request queue
     */
    @Override
    public void cancelRequest(int requestId) {
        requestQueue.cancelAll(requestId);
    }
}
