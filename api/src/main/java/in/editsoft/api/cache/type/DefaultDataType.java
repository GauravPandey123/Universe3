package in.editsoft.api.cache.type;

import in.editsoft.api.response.IResponse;

/**
 * Class tells about the data type for caching.
 */
public class DefaultDataType<T extends IResponse<T>> implements DataType<T> {

    private Class<T> type;
    private final String name;

    public DefaultDataType(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<T> getType() {
        return type;
    }
}
