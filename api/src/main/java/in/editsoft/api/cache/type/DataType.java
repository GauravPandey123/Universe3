package in.editsoft.api.cache.type;

import in.editsoft.api.response.IResponse;

/**
 * Data type for response to be cached.
 */
public interface DataType<T extends IResponse> {

    String getName();

    Class<T> getType();
}
