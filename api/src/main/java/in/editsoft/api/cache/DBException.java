package in.editsoft.api.cache;


import in.editsoft.api.exception.APIException;

/**
 * Exception for database related operations.
 */
public class DBException extends APIException {
    public DBException(APIException.Kind kind, int code, String msg) {
        super(kind, code, msg);
    }

    public DBException(Kind kind, String msg) {
        super(kind, msg);
    }
}
