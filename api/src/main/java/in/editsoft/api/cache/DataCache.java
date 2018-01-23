package in.editsoft.api.cache;

import android.content.Context;

import in.editsoft.api.cache.type.DataType;
import in.editsoft.api.response.IResponse;

/**
 * Caching response
 */
public interface DataCache {

    <T extends IResponse> void save(Context context, T response, DataType type) throws DBException;

    boolean remove(Context context, DataType type) throws DBException;

    <T extends IResponse> T load(Context context, DataType<T> type) throws DBException;

}
