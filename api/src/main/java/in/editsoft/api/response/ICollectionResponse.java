package in.editsoft.api.response;

/**
 * Collection Response Interface
 */
public interface ICollectionResponse<T extends IResponse<T>> extends IResponse<T> {

    /**
     * @return count for collection
     */
    int getCollectionSize();

    /**
     * @return True if collection can be empty
     */
    boolean allowEmptyCollection();
}
