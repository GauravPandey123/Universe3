package in.editsoft.api.service;

import com.google.gson.GsonBuilder;

import in.editsoft.api.callback.APICallback;
import in.editsoft.api.exception.APIException;
import in.editsoft.api.request.IRequest;
import in.editsoft.api.response.IResponse;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

/**
 * Rest API service
 */
public interface IService<U, T extends IRequest, L extends IResponse<L>> {

    /**
     * @param request  to execute service
     * @param callback callback to handle response
     */
    void execute(T request, APICallback<L> callback) throws APIException;

    /**
     * @param api      to pass data in request
     * @param request  to send on server
     */
    Call<L> onExecute(U api, T request);


    /**
     * @return Gson Builder object to set customize Gson Object
     */
    GsonBuilder getGsonBuilder();

    /**
     * @return Log level for the logger
     */
    HttpLoggingInterceptor.Level getLogLevel();

    /**
     * @return connection timeout for SERVICE
     */
    long getConnectionTimeoutInSeconds();

    /**
     * @return read timeout for SERVICE
     */
    long getReadTimeoutInSeconds();

    /**
     * @return API Service class
     */
    Class<U> getAPI();

}