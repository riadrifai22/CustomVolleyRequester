package com.ezvolley.fields;

/**
 * Created by Riad on 27-07-17.
 */
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.volley.NetworkResponse;
import com.ezvolley.EzVolley;
import com.ezvolley.request.AbstractEzVolleyRequest;
import com.ezvolley.types.NetworkMethodTypes;
import com.ezvolley.types.NetworkPriorityTypes;
import com.google.gson.Gson;
import java.util.Map;

/**
 * This abstract class contains as fields the requirements to make a volley request.
 * {@link AbstractEzVolleyRequest} holds an instance of this to access
 * its fields in order to add them to the request.
 * Inherit from this class in-case you need extra fields to be used. See {@link EzFields}
 * In case there is no {@link IntermediateResponse} use {@link EzFields <FinalResponse>}
 * @param <FinalResponse> The final response type to be returned by {@link #returnFromJson(Gson, Object)}
 * @param <IntermediateResponse> In case there exists an object in the response we need to parse and
 *                              return as {@link FinalResponse}
 */
public abstract class BaseEzFields<FinalResponse, IntermediateResponse> {

    /**
     *The full url to the endpoint
     */
    private String url;

    /**
     * The body to send along with the request. Could be in Json format for example.
     * Ex: {
     *        email: test@test.com
     *     }
     */
    private String body;

    /**
     * Headers the server requires for requests.
     * Example of a map's entry: {key: "Accept", value:"application/json"}
     */
    private Map<String, String> headersToAdd;

    /**
     * The request priority. Call {@link #setPriority(NetworkPriorityTypes)} to set a different
     * priority type. See {@link NetworkPriorityTypes} for different priorities.
     */
    private NetworkPriorityTypes networkPriorityTypes = NetworkPriorityTypes.NORMAL; //Default to normal

    /**
     * The request method type. Ex: {@link NetworkMethodTypes#GET}
     */
    private NetworkMethodTypes methodType;

    /**
     * Tags are printed as debug statements in-case of error.
     */
    private String volleyRequestsTag;

    /**
     * @param methodType Example: {@link NetworkMethodTypes#GET}
     * @param url Full request url
     * @param body Body to send along with the request.
     * @param headersToAdd Headers of the current request. See
     *                     {@link com.ezvolley.headers.RequestHeadersGenerator} for
     *                     building headers.
     */

    public BaseEzFields(NetworkMethodTypes methodType, @NonNull String url,
                        @Nullable String body,
                        @Nullable Map<String, String> headersToAdd) {
        this.url = url;
        this.headersToAdd = headersToAdd;
        this.body = body;
        this.methodType = methodType;
    }

    /**
     * Different implementations depending on the type of request.
     * This method should be called in
     * {@link AbstractEzVolleyRequest#parseNetworkResponse(NetworkResponse)}
     * or any subclass of {@link AbstractEzVolleyRequest}
     * In case {@link NetworkResponse was successful, let the {@link Gson}} instance serialize
     * our {@link IntermediateResponse} response into our {@link FinalResponse}
     * See {@link EzFields#returnFromJson(Gson, String)}
     */
    public abstract FinalResponse returnFromJson(Gson gson, IntermediateResponse json);

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeadersToAdd() {
        return headersToAdd;
    }

    public NetworkMethodTypes getMethodType() {
        return methodType;
    }

    /**
     * The default type is {@link NetworkPriorityTypes#NORMAL}.
     * See {@link #setPriority(NetworkPriorityTypes)} to set a different priority.
     * @return The priority type
     */
    public NetworkPriorityTypes getPriority() {
        return networkPriorityTypes;
    }

    public void setPriority(NetworkPriorityTypes priority) {
        this.networkPriorityTypes = priority;
    }

    public void setEzRequestsTag (String tag) {
        this.volleyRequestsTag = tag;
    }

    public String getVolleyRequestsTag() {
        if (volleyRequestsTag == null) return EzVolley.requestTag;
        return volleyRequestsTag;
    }
}
