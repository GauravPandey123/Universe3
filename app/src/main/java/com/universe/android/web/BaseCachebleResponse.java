package com.universe.android.web;


import in.editsoft.api.response.APIResponse;
import in.editsoft.api.util.Validation;

public abstract class BaseCachebleResponse<T extends BaseCachebleResponse<T>> extends APIResponse<T> {

    public boolean isNullOrEmpty(String p_string) {
        return Validation.isNullOrEmpty(p_string);
    }

    public boolean isNotNull(Object p_object) {
        return p_object != null;
    }

}
