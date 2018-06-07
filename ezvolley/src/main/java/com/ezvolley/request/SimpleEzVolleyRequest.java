package com.ezvolley.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ezvolley.fields.BaseEzFields;
import com.ezvolley.response.NetworkResponseHandler;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Riad on 27-07-17.
 */

/**
 * Simple request that returns the server response and stores it in a string (see {@link #parseNetworkResponse(NetworkResponse)}
 * and then lets {@link com.google.gson.Gson} serialize it into the desired {@link FinalResponse}
 */
public class SimpleEzVolleyRequest<FinalResponse> extends AbstractEzVolleyRequest<FinalResponse, String> {
    /**
     * @param volleyFields           The fields specific for each request
     * @param networkResponseHandler The handler that connects to the UI layer.
     */
    public SimpleEzVolleyRequest(BaseEzFields<FinalResponse, String> volleyFields,
                                 NetworkResponseHandler<FinalResponse> networkResponseHandler) {
        super(volleyFields, networkResponseHandler);
    }

    @Override
    protected Response<FinalResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            /**
             * {@link #getVolleyRequestFields()#returnFromJson()} is implemented differently for each
             * type of {@link BaseEzFields} or any child class.
             */
            FinalResponse finalResponse = getVolleyRequestFields().returnFromJson(gson, json);
            return Response.success(finalResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } finally {
            getNetworkResponseHandler().onResponseHeadersAvailable(response.headers);
        }
    }
}
