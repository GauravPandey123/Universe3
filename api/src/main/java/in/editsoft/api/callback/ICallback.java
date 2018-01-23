package in.editsoft.api.callback;

import android.support.annotation.NonNull;

import in.editsoft.api.exception.APIException;
import in.editsoft.api.response.IResponse;
import retrofit2.Callback;

/**
 * Interface for request and response
 */
public interface ICallback<T extends IResponse<T>> extends Callback<T> {

    /**
     * @return scenario for response validation
     */
    String getScenario();

    /**
     * @param response from the rest API
     */
    void onSuccess(@NonNull T response);

    /**
     * @param e response from rest API
     */
    void onFailure(APIException e);


    /**
     * Marker method. to be implemented in child class if required
     */
    void onComplete();

    /**
     * Default value is false.
     *
     * @return True if strict validation is enabled
     */
    boolean useStrictValidation();

}
