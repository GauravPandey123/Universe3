package in.editsoft.api.response;

import java.io.Serializable;

import in.editsoft.api.util.APIConstants;

/**
 * Response Interface
 */
public interface IResponse<T extends IResponse<T>> extends Serializable, APIConstants {

    /**
     * Request Version
     *
     * @return version of the request
     */
    int getVersion();

    /**
     * @param response partial response to be update in cached response
     */
    void update(T response);

    /**
     * @param condition use to validate response
     * @return True or False on validation
     */
    boolean isValid(String condition, boolean isStrict);

    /**
     * @param condition use to validate response
     * @return True or False on validation
     */
    /**
     * @param condition use to validate response
     * @param isStrict  whether validation is strict or not
     * @param partial   whether response is partial
     * @return true for valid response
     */
    boolean isValid(String condition, boolean isStrict, boolean partial);
}