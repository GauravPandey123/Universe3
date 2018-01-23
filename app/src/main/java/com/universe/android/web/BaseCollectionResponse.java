package com.universe.android.web;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.ListIterator;

import in.editsoft.api.response.ICollectionResponse;
import in.editsoft.api.response.IResponse;

/**
 * BaseResponse
 */
public abstract class BaseCollectionResponse<T extends BaseResponse<T>> extends BaseResponse<T> implements ICollectionResponse<T> {

    @SerializedName("dt")
    protected List<T> data;
    private int mCollectionSize;

    @Override
    public final boolean isValid(String scenario, boolean isStrict) {
        return this.isValid(scenario, isStrict, false);
    }

    public List<T> getData() {
        return this.data;
    }

    public boolean hasMoreData() {
        return this.getPageSize() == this.getCollectionSize();
    }

    public int getVersion() {
        return 0;
    }

    @Override
    public final boolean isValid(String condition, boolean isStrict, boolean partial) {
        if (this.data != null && this.data.size() != 0) {
            this.mCollectionSize = this.data.size();
            ListIterator iterator = this.data.listIterator();

            while (iterator.hasNext()) {
                IResponse response = (IResponse) iterator.next();
                boolean success = response.isValid(condition, isStrict);
                if (isStrict && !success) {
                    return false;
                }

                if (!success) {
                    iterator.remove();
                }
            }

            return this.isValid(condition);
        } else {
            return this.allowEmptyCollection() && this.isValid(condition);
        }
    }

    public boolean isValid(String scenario) {
        return true;
    }

    public int getCollectionSize() {
        return this.mCollectionSize;
    }

    public boolean allowEmptyCollection() {
        return false;
    }

    public int getPageSize() {
        return 10;
    }
}
