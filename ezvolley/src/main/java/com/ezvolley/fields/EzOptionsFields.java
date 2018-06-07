package com.ezvolley.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ezvolley.types.NetworkMethodTypes;

import java.util.Map;

/**
 * Created by Riad on 09-Feb-18.
 */

public class EzOptionsFields<FinalResponse> extends EzFields<FinalResponse> {
    public EzOptionsFields(@NonNull String url, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.OPTIONS, url, null, null, klass);
    }

    public EzOptionsFields(@NonNull String url, @Nullable String body,
                           @Nullable Map<String, String> headersToAdd, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.OPTIONS, url, body, headersToAdd, klass);
    }
}
