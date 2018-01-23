package in.editsoft.api.request;


import in.editsoft.api.response.IResponse;

/**
 * CollectionRequest
 */
public abstract class CollectionRequest extends APIRequest implements ICollectionRequest, IResponse {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private int mPageOffset = 0;


    @Override
    public int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    @Override
    public int getPageOffset() {
        return mPageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.mPageOffset = pageOffset;
    }

}
