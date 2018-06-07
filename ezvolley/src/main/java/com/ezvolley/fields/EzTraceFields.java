package com.ezvolley.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ezvolley.types.NetworkMethodTypes;

import java.util.Map;

/**
 * Created by Riad on 09-Feb-18.
 */

public class EzTraceFields<FinalResponse> extends EzFields<FinalResponse> {
    public EzTraceFields(@NonNull String url, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.TRACE, url, null, null, klass);
    }

    public EzTraceFields(@NonNull String url, @Nullable String body,
                         @Nullable Map<String, String> headersToAdd, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.TRACE, url, body, headersToAdd, klass);
    }
}
