package com.ezvolley;

/**
 * Created by Riad on 27-07-17.
 */

import com.ezvolley.request.AbstractEzVolley;

/**
 * Singleton instance of a request generator.
 * The type of request we use is set by {@link #abstractEzVolley}
 */
public class RequestGenerator {
    private static RequestGenerator instance;

    /**
     * Since we have a single instance of {@link RequestGenerator} we will have a single instance
     * of {@link #abstractEzVolley} as well. Which ensure that the library initiator is
     * created once.
     */
    private AbstractEzVolley abstractEzVolley;

    private RequestGenerator() {}

    public static RequestGenerator getInstance() {
        if (instance == null) instance = new RequestGenerator();

        return instance;
    }

    public AbstractEzVolley getRequesterInitiator() {
        if (abstractEzVolley == null) abstractEzVolley = new EzVolley();

        return abstractEzVolley;
    }


}