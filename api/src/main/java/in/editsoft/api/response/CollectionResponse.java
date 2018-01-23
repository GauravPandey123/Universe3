package in.editsoft.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.ListIterator;

/**
 * Class is used for named("dt") collection responses.
 * it can include normal responses as well.
 */
public class CollectionResponse<T extends IResponse<T>> extends APIResponse<T> implements ICollectionResponse<T> {

    @SerializedName("dt")
    private List<T> data;

    //gives the count for collection
    private int mCollectionSize;

    public List<T> getData() {
        return data;
    }

    public boolean hasMoreData() {
        return getPageSize() == getCollectionSize();
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void update(T response) {
    }

    @Override
    public final boolean isValid(String scenario, boolean isStrict) {
        return isValid(scenario, isStrict, false);
    }

    @Override
    public boolean isValid(String condition, boolean isStrict, boolean partial) {
        if (data != null && data.size() != 0) {
            mCollectionSize = data.size();
            ListIterator<T> iterator = data.listIterator();
            while (iterator.hasNext()) {
                T response = iterator.next();
                boolean success = response.isValid(condition, isStrict);
                if (isStrict && !success) {
                    return false;
                }
                if (!success) {
                    iterator.remove();
                }
            }
            return isValid(condition);
        } else {
            return allowEmptyCollection() && isValid(condition);
        }
    }

    public boolean isValid(String scenario) {
        return true;
    }

    @Override
    public int getCollectionSize() {
        return mCollectionSize;
    }

    @Override
    public boolean allowEmptyCollection() {
        return false;
    }

    public int getPageSize() {
        return 10;
    }
}
