package in.editsoft.api.exception;

import java.io.IOException;

/**
 * APIException
 */
public class APIException extends Exception {
    private final Kind kind;
    private final int code;
    private final Throwable cause;
    private final APIError error;
    private String data;

    public APIException(Kind kind, String msg) {
        this(kind, -1, msg, null, null, "");
    }

    public APIException(Kind kind, String msg, String data) {
        this(kind, -1, msg, null, null, data);
    }

    public APIException(Kind kind, int code, String msg) {
        this(kind, code, msg, null, null, "");
    }

    public APIException(Kind unexpected, String msg, Throwable cause) {
        this(unexpected, -1, msg, null, cause, "");
    }

    public APIException(Kind unexpected, String msg, Throwable cause, String data) {
        this(unexpected, -1, msg, null, cause, data);
    }

    public APIException(Kind kind, int code, String msg, APIError error, Throwable cause, String data) {
        super(msg);
        this.kind = kind;
        this.code = code;
        this.error = error;
        this.cause = cause;
        this.data = data;
    }

    public String getData() {
        if (data == null || data.isEmpty()) {
            data = getMessage();
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Kind getKind() {
        return kind;
    }

    public int getCode() {
        return code;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public APIError getError() {
        return error;
    }

    @Deprecated
    public String getMsg() {
        return getMessage();
    }

    /**
     * Identifies the event kind which triggered a {@link APIException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * An exception was thrown while (de)serializing a body.
         */
        CONVERSION,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,

        /**
         * call back is null
         */
        NULL,

        /**
         *
         */
        SOCKET_TIMEOUT,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,

        /**
         * Exception kind for invalid request
         */
        INVALID_REQUEST,

        /**
         * Exception kind for invalid response
         */
        INVALID_RESPONSE,

        /**
         * Exception for incompatible data cache version
         */
        CACHE_EXCEPTION,

        /**
         * Exception constant if resource not found.
         */
        RESOURCE_NOT_FOUND,

        /**
         *
         */
        CLASS_CAST_EXCEPTION,
    }
}
