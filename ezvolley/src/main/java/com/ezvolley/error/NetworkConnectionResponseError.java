package com.ezvolley.error;

/**
 * Created by Riad on 27-07-17.
 */

import java.util.Map;

/**
 * Invoked when we have connection error related to the library responsible for creating internet calls.
 */
public class NetworkConnectionResponseError {
    private int statusCode;
    private Map<String, String> headers;
    private byte[] data;

    public NetworkConnectionResponseError(int statusCode, Map<String, String> headers, byte[] data) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getData() {
        return data;
    }
}
