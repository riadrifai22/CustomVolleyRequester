package com.ezvolley;

import android.app.Application;
import android.support.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ezvolley.fields.BaseEzFields;
import com.ezvolley.request.AbstractEzVolley;
import com.ezvolley.request.AbstractEzVolleyRequest;
import com.ezvolley.request.SimpleEzVolleyRequest;
import com.ezvolley.response.NetworkResponseHandler;

/**
 * Created by Riad on 27-07-17.
 */

/**
 * Inherit from this class incase you'd like to create different types of requests. NOTE: Your request though
 * must inherit from {@link AbstractEzVolleyRequest}
 */
public class EzVolley extends AbstractEzVolley {

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

    public static String requestTag = "EzVolley";


    public static void init(Application application) {
        init(application, null);
    }

    public static void init(Application application, @Nullable String tag) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(application.getApplicationContext());
            if (tag != null)
                requestTag = tag;
        }
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
     * using {@link AbstractEzVolleyRequest#setTag(Object)}
     *
     * 3) Add the request to the queue.
     *
     * 4) Notifies {@link NetworkResponseHandler#onRequestAddedToQueue(int)}.
     */
    private synchronized <FinalResponse, IntermediateResponse> void addToRequestQueue(
            AbstractEzVolleyRequest<FinalResponse, IntermediateResponse> abstractEzVolleyRequest,
            NetworkResponseHandler<FinalResponse> networkResponseHandler) {

        abstractEzVolleyRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES, BACKOFF_MULT));

        //if (abstractEzVolleyRequest.getMethod() == Request.Method.POST) abstractEzVolleyRequest.setShouldCache(false);

        requestId++;

        abstractEzVolleyRequest.setTag(requestId);

        requestQueue.add(abstractEzVolleyRequest);
        networkResponseHandler.onRequestAddedToQueue(requestId);
    }

    /**
     * This method is called to create the request and send to server.
     * By default, this method creates a {@link SimpleEzVolleyRequest}.
     * In case you'd like to use your ezvolley request that inherits from {@link AbstractEzVolleyRequest},
     * inherit {@link EzVolley} and override {@link #createRequest(BaseEzFields, NetworkResponseHandler)}
     */
    @Override
    public <FinalResponse> void createRequest(BaseEzFields<FinalResponse, String> baseEzFields,
                                              NetworkResponseHandler<FinalResponse> networkResponseHandler) {
        SimpleEzVolleyRequest<FinalResponse> simpleVolleyRequest = new SimpleEzVolleyRequest<>(
                baseEzFields, networkResponseHandler);

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
