package com.ezvolley.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ezvolley.types.NetworkMethodTypes;

import java.util.Map;

/**
 * Created by Riad on 09-Feb-18.
 */

public class EzGetFields<FinalResponse> extends EzFields<FinalResponse> {

    public EzGetFields(@NonNull String url, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.GET, url, null, null, klass);
    }

    public EzGetFields(@NonNull String url, @Nullable String body,
                       @Nullable Map<String, String> headersToAdd, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.GET, url, body, headersToAdd, klass);
    }
}
