package com.custom.volley.requester;

/**
 * Created by Riad on 27-07-17.
 */

import com.custom.volley.requester.request.AbstractRequestInitiator;

/**
 * Singleton instance of a request generator.
 * The type of request we use is set by {@link #abstractRequestInitiator}
 */
public class RequestGenerator {
    private static RequestGenerator instance;

    /**
     * Since we have a single instance of {@link RequestGenerator} we will have a single instance
     * of {@link #abstractRequestInitiator} as well. Which ensure that the library initiator is
     * created once.
     */
    private AbstractRequestInitiator abstractRequestInitiator;

    private RequestGenerator() {}

    public static RequestGenerator getInstance() {
        if (instance == null) instance = new RequestGenerator();

        return instance;
    }

    public AbstractRequestInitiator getRequesterInitiator() {
        if (abstractRequestInitiator == null) abstractRequestInitiator = new VolleyInitiator();

        return abstractRequestInitiator;
    }


}