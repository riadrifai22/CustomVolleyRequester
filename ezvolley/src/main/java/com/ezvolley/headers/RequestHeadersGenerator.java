package com.ezvolley.headers;

/**
 * Created by Riad on 27-07-17.
 */

import com.ezvolley.fields.BaseEzFields;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that returns HashMap<String, String> of the headers to add for the volley request.
 * The headers are added through builder pattern for the class {@link Builder}
 *
 *
 * Example of usage:
 *
 *      Map<String, String> headers = new RequestHeadersGenerator.Builder()
                                        .addAcceptJson()
                                        .build().getHeaders();
 * Use this to set headers for your requests.
 * Set the headers for your requests using {@link BaseEzFields#headersToAdd}
 */
public class RequestHeadersGenerator {

    private Map<String, String> headers;

    public static class Builder {

        private Map<String, String> headers = new HashMap<>();

        /**
         * Called in case you want to add specific headers in one method only
         * @param headers ezvolley headers to be added
         */
        public Builder addHeaders(Map<String, String> headers) {
            if (headers != null) {
                for (String header : headers.keySet()) {
                    this.headers.put(header, headers.get(header));
                }
            }
            return this;
        }

        public Builder addAcceptJson() {
            headers.put("Accept", "application/json");
            return this;
        }

        public Builder addSendJson() {
            headers.put("Content-Type", "application/json");
            return this;
        }


        public RequestHeadersGenerator build() {
            return new RequestHeadersGenerator(this);
        }
    }

    private RequestHeadersGenerator(Builder builder) {
        this.headers = builder.headers;
    }

    public Map<String, String> getHeaders() { return headers; }
}
