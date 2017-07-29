package com.custom.volley.requester.types;

/**
 * Created by Riad on 27-07-17.
 */

/**
 * Mapping between Method types and integers.
 */
public enum NetworkMethodTypes {
    GET (0),
    POST(1),
    PUT(2),
    DELETE(3),
    HEAD(4),
    OPTIONS(5),
    TRACE (6),
    PATCH (7);

    private int type;

    NetworkMethodTypes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
