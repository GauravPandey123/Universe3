package in.editsoft.api.util;

/**
 * HttpCodes
 */
public enum HttpCodes {
    CONFLICT(409, "Conflict with requested resource"),
    NONE(-1, "None");

    int code;
    String message;

    HttpCodes(int mCode, String message) {
        this.code = mCode;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static HttpCodes getHttpCode(int code) {
        HttpCodes[] values = values();
        for (HttpCodes httpCodes : values) {
            if (httpCodes.getCode() == code) {
                return httpCodes;
            }
        }
        return NONE;
    }
}
