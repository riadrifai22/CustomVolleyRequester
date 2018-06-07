package com.ezvolley.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ezvolley.types.NetworkMethodTypes;
import com.google.gson.Gson;
import java.util.Map;

/**
 * Created by Riad on 27-07-17.
 */

/**
 * A simple version of {@link BaseEzFields} that receives the response from the server, puts
 * it in a String object(Volley by default) and then lets {@link Gson} serialize the String into
 * your desired {@link FinalResponse}. Note that the string could be json for example.
 *
 * @param <FinalResponse> The final Model you would like to receive in
 * {@link com.ezvolley.response.NetworkResponseHandler#onSuccess(Object)}
 */
public class EzFields<FinalResponse> extends BaseEzFields<FinalResponse, String> {

    /**
     * The Class of the desired return returned by the request. For example: JsonObject.class
     */
    private Class<FinalResponse> klass;

    /**
     *
     *@param methodType Example: {@link NetworkMethodTypes#GET}
     * @param url Full request url
     * @param body Body to send along with the request.
     * @param headersToAdd Headers of the current request. See
     *                     {@link com.ezvolley.headers.RequestHeadersGenerator} for
     *                     building headers.
     * @param klass The Model class to be returned on success.
     */
    public EzFields(NetworkMethodTypes methodType, @NonNull String url,
                    @Nullable String body,
                    @Nullable Map<String, String> headersToAdd,
                    @NonNull Class<FinalResponse> klass) {
        super(methodType, url, body, headersToAdd);
        this.klass = klass;
    }

    /**
     * Serialize the server response into the desired Model class.
     * @return An object of the desired {@link FinalResponse} type.
     */
    @Override
    public FinalResponse returnFromJson(Gson gson, String json) {
        return gson.fromJson(json, klass);
    }
}
