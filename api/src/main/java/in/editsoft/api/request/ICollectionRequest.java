package in.editsoft.api.request;

/**
 * Collection Request interface
 */
public interface ICollectionRequest extends IRequest {

    int getPageSize();

    int getPageOffset();

    void setOffset(int offset);
}
