package com.custom.volley.requester.request;

/**
 * Created by Riad on 27-07-17.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.custom.volley.requester.error.NetworkConnectionResponseError;
import com.custom.volley.requester.fields.BaseRequestFields;
import com.custom.volley.requester.headers.RequestHeadersGenerator;
import com.custom.volley.requester.response.NetworkResponseHandler;
import com.custom.volley.requester.types.NetworkPriorityTypes;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Generic volley request. This is a library specific class that contains volley-related call.
 * This class responds the the calling layer through {@link NetworkResponseHandler}
 * It handles generating network requests and handling the response/error.
 *
 * Inherit from this class in-case there are specific changes you need to make to your request.
 * For example, if you need to add a default header such as: "Accept:application/json" for every
 * request you make override {@link #getHeaders()} add it from there.
 * @param <FinalResponse> The final type to be returned as a result.
 * @param <IntermediateResponse> Intermediate type used for parsing json results before returning
 *                              the {@link FinalResponse}
 */
public abstract class CustomVolleyRequest<FinalResponse, IntermediateResponse> extends
        Request<FinalResponse> {

    protected final Gson gson = new Gson();

    protected NetworkResponseHandler<FinalResponse> networkResponseHandler;

    private BaseRequestFields<FinalResponse, IntermediateResponse> volleyRequestFields;

    /**
     * @param volleyFields The fields specific for each request
     * @param networkResponseHandler The handler that connects to the main thread layer.
     */
    public CustomVolleyRequest(final BaseRequestFields<FinalResponse, IntermediateResponse> volleyFields,
                                  final NetworkResponseHandler<FinalResponse> networkResponseHandler) {
        super(volleyFields.getMethodType().getType(), volleyFields.getUrl(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                log(error, volleyFields.getVolleyRequestsTag());
                if(error.networkResponse != null) {
                    NetworkConnectionResponseError networkConnectionResponseError = new NetworkConnectionResponseError(
                            error.networkResponse.statusCode, error.networkResponse.headers,
                            error.networkResponse.data);
                    networkResponseHandler.onConnectionFailed(networkConnectionResponseError);
                }
            }

            private void log(VolleyError error, String tag) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    final int statusCode = networkResponse.statusCode;
                    final String msg = error.getMessage();
                    final byte[] data = networkResponse.data;
                    Log.e(tag, "Error statusCode = " + statusCode + ", message = " + msg +
                            ", data = " + new String(data), error);
                } else {
                    Log.e(tag, error.getMessage(), error);
                }
            }
        });

        this.volleyRequestFields = volleyFields;
        this.networkResponseHandler = networkResponseHandler;
    }

    /**
     * @return The request fields for the request.
     */
    protected BaseRequestFields<FinalResponse, IntermediateResponse> getVolleyRequestFields() {
        return volleyRequestFields;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(volleyRequestFields.getBody() == null) return super.getBody();

        return volleyRequestFields.getBody().getBytes();
    }

    /**
     * By default, this returns the headers added to {@link BaseRequestFields#headersToAdd}
     * If you need default headers, inherit from {@link CustomVolleyRequest} and override this method.
     * Add this in the overriden method for example:
     * "
     *      return new RequestHeadersGenerator.Builder()
                    .addHeaders(volleyRequestFields.getHeadersToAdd())
                    .addAcceptJson()
                    .build().getHeaders();
     * "
     * The latter would add "Accept: application/json" to every request you make.
     *
     * @return Map containing the headers added to the request.
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return new RequestHeadersGenerator.Builder()
                .addHeaders(volleyRequestFields.getHeadersToAdd())
                .build().getHeaders();
    }

    /**
     * Maps {@link NetworkPriorityTypes} with {@link com.android.volley.Request.Priority}
     * @return {@link com.android.volley.Request.Priority}
     */
    @Override
    public Priority getPriority() {
        NetworkPriorityTypes networkPriorityTypes = volleyRequestFields.getPriority();
        if(networkPriorityTypes.equals(NetworkPriorityTypes.IMMEDIATE)) {
            return Priority.IMMEDIATE;
        } else if (networkPriorityTypes.equals(NetworkPriorityTypes.HIGH)) {
            return Priority.HIGH;
        } else if (networkPriorityTypes.equals(NetworkPriorityTypes.NORMAL)) {
            return Priority.NORMAL;
        } else if (networkPriorityTypes.equals(NetworkPriorityTypes.LOW)) {
            return Priority.LOW;
        } else {
            return Priority.NORMAL;
        }
    }

    /**
     * Deliver the success response to the handler. This method is called after {@link #parseNetworkResponse(NetworkResponse)}
     * knowing that the latter has a success response with the result.
     * @param response The {@link FinalResponse} returned from server
     */
    @Override
    protected void deliverResponse(FinalResponse response) {
        networkResponseHandler.onSuccess(response);
    }

    /**
     * Parsing the volley response. Parse the {@link NetworkResponse} to determine if our response
     * was successful or contained an error.
     */
    protected abstract Response<FinalResponse> parseNetworkResponse(NetworkResponse response);
}
