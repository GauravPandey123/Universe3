package com.universe.android.web;

import android.text.TextUtils;


import com.universe.android.utility.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.editsoft.api.request.APIRequest;
import in.editsoft.api.util.APIConstants;

/**
 * BaseRequest
 */
public abstract class BaseRequest extends APIRequest implements APIConstants {
    private Map<String, String> mHeaderMap;

    @Override
    public String getUrl() {
        return getBaseUrl();
    }

    @Override
    public final Map<String, String> getHeaders() {
        return mHeaderMap;
    }

    public final void addHeader(String k, String v) {
        if (TextUtils.isEmpty(k)) {
            return;
        }
        if (mHeaderMap == null) {
            mHeaderMap = new HashMap<>();
        }
        mHeaderMap.put(k, v);
    }

    public final void addHeaders(Map<String, String> headerMap) {
        if (headerMap == null || headerMap.isEmpty()) {
            return;
        }
        Set<String> keys = headerMap.keySet();
        for (String k : keys) {
            addHeader(k, headerMap.get(k));
        }
    }
    public String getBaseUrl() {
        return Constants.BASE_URL;
    }
}