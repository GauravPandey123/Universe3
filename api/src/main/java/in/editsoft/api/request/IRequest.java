package in.editsoft.api.request;

import java.util.Map;

/**
 * Request interface
 */
public interface IRequest {

    /**
     * @return Unique id for the request
     */
    String getId();

    /**
     * Set the base url for the service
     *
     * @return base Url for the request
     */
    String getUrl();

    /**
     * Request Version
     *
     * @return version of the request
     */
    int getVersion();

    /**
     * Set the current version of requested data
     *
     * @param version An int value for versioning.
     */
    void setVersion(int version);

    /**
     * @return request header in string format.
     */
    String getAuthToken();

    /**
     * Se authentication token for request header
     *
     * @param authToken request header
     */
    void setAuthToken(String authToken);

    /**
     * Validation check fot request using given scenario
     *
     * @param Scenario condition to validate request ex. check for required fields.
     * @return True for valid request
     */
    boolean isValid(String Scenario);

    /**
     * @return scenario for response validation
     */
    String getScenario();

    /**
     * Method to difine scenario for validation
     *
     * @param scenario condition for the validation
     */
    void setScenario(String scenario);

    /**
     * @return all static headers
     */
    Map<String, String> getHeaders();
}
