package com.universe.android.web;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Map;

import in.editsoft.api.response.APIResponse;


/**
 * BaseResponse
 */
public abstract class BaseResponse<T extends BaseResponse<T>> extends APIResponse<T> {

    protected void writeMap(Parcel out, Map<String, String> map) {
        if (map == null) {
            return;
        }
        out.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            out.writeString(entry.getKey());
            out.writeString(entry.getValue());
        }
    }

    protected void readMap(Parcel in, @NonNull Map<String, String> map) {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            map.put(key, value);
        }
    }

}
