package in.editsoft.api.exception;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghav on 8/19/2015.
 * Custom Error object for server error
 */
public class APIError {

    @SerializedName("name")
    private String name;

    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private int status;

    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}
