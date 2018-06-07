package com.ezvolley.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ezvolley.types.NetworkMethodTypes;

import java.util.Map;

/**
 * Created by Riad on 09-Feb-18.
 */

public class EzPostFields<FinalResponse> extends EzFields<FinalResponse> {

    public EzPostFields(@NonNull String url, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.POST, url, null, null, klass);
    }

    public EzPostFields(@NonNull String url, @Nullable String body,
                        @Nullable Map<String, String> headersToAdd, @NonNull Class<FinalResponse> klass) {
        super(NetworkMethodTypes.POST, url, body, headersToAdd, klass);
    }
}
