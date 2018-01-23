package in.editsoft.api.callback;


import android.content.Context;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import in.editsoft.api.cache.DBException;
import in.editsoft.api.cache.DataCache;
import in.editsoft.api.cache.FileCache;
import in.editsoft.api.cache.type.DataType;
import in.editsoft.api.exception.APIError;
import in.editsoft.api.exception.APIException;
import in.editsoft.api.response.ErrorResponse;
import in.editsoft.api.response.IResponse;
import in.editsoft.api.util.APIConstants;
import in.editsoft.api.util.App;
import in.editsoft.api.util.HttpCodes;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * APICallback for the API
 */
public abstract class APICallback<T extends IResponse<T>> implements ICallback<T>, APIConstants {

    private static final String TAG = APICallback.class.getSimpleName();
    private final DataType<T> mDataType;
    private final DataCache mDataCache;

    private T mCachedResponse;
    private Context mContext;

    /**
     * Use {@link #APICallback(Context)}
     * to avoid using Application context
     */
    @Deprecated
    public APICallback() {
        this(App.context);
    }

    public APICallback(Context context) {
        mContext = context;
        mDataType = getDBDataType();
        mDataCache = getDataCache();
    }

    @SuppressWarnings("unused")
    protected T getCachedResponse() {
        return mCachedResponse;
    }

    public Context getContext() {
        return mContext;
    }

    @WorkerThread
    public int getCachedResponseVersion() {
        if (mCachedResponse == null) {
            final DataType<T> dataType = mDataType;
            final DataCache dataCache = mDataCache;
            if (dataType != null && getDataCache() != null) {
                try {
                    mCachedResponse = dataCache.load(App.context, dataType);
                } catch (DBException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        }
        return mCachedResponse == null ? 0 : mCachedResponse.getVersion();
    }

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        onComplete();
        String data = "";
        ErrorResponse error = new ErrorResponse();
        try {
            if (response.errorBody() != null) {
                data = response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null && !data.isEmpty()) {
            try {
                error = new Gson().fromJson(data, ErrorResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int status = response != null ? response.code() : APIException.Kind.UNEXPECTED.ordinal();

        if (response == null) {
            onFailure(new APIException(APIException.Kind.INVALID_RESPONSE, "response == null"));
            return;
        }
        //check for error body
        if (!response.isSuccessful()) {
            String msg = "";
            if (error.getError() != null) {
                msg = error.getError().getMobile();
            }
            onFailure(getAPIException(response, null, msg));
            return;
        }

        T apiResponse = response.body();
        if (apiResponse == null) {
            onFailure(new APIException(APIException.Kind.NULL, status, "IResponse can't be null"));
            return;
        }

        if (mCachedResponse != null) {
            if (apiResponse.getVersion() == mCachedResponse.getVersion()) {
                //do partial update
                if (apiResponse.isValid(getScenario(), useStrictValidation(), true))
                    mCachedResponse.update(apiResponse);
                onSuccess(mCachedResponse);
                return;
            } else {
                //clear cache on version mismatch
                removeFromCacheAsync();
                mCachedResponse = null;
            }
        }

        if (!apiResponse.isValid(getScenario(), useStrictValidation())) {
            APIException validationError = new APIException(APIException.Kind.INVALID_RESPONSE
                    , status, "Validation error for the given scenario");
            onFailure(validationError);
            return;
        }

        if (apiResponse.getVersion() > 0) {
            addToCacheAsync(apiResponse);
        }
        onSuccess(apiResponse);
    }

    public DataCache getDataCache() {
        return FileCache.getInstance();
    }

    @Override
    public void onFailure(Call<T> call, Throwable cause) {
        onComplete();
        APIException apiException;
        if (cause instanceof UnknownHostException) {
            apiException = new APIException(APIException.Kind.NETWORK,
                    "Unable to connect. Please try again");
        } else if (cause instanceof SocketTimeoutException) {
            apiException = new APIException(APIException.Kind.SOCKET_TIMEOUT,
                    "Internet connection is Slow. Try again");
        } else {
            apiException = new APIException(APIException.Kind.UNEXPECTED,
                    cause != null ? cause.getMessage() : "Unknown");
        }
        onFailure(apiException);
    }

    private APIException getAPIException(Response<T> response, Throwable cause, String data) {
        if (cause != null) {
            if (cause instanceof SocketTimeoutException) {
                return new APIException(APIException.Kind.SOCKET_TIMEOUT, "No Internet Error", cause);
            } else if (cause instanceof JSONException) {
                return new APIException(APIException.Kind.CONVERSION, "Conversion Error", cause);
            } else if (cause instanceof IOException) {
                return new APIException(APIException.Kind.NETWORK, "Network Error", cause);
            } else {
                return new APIException(APIException.Kind.UNEXPECTED, "Unknown", cause);
            }
        } else if (response != null) {
            int status = response.code();
            String responseMsg = response.message();
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                switch (HttpCodes.getHttpCode(status)) {
                    case CONFLICT:
                        Log.e(TAG, "getAPIException: case : CONFLICT");
                        return new APIException(APIException.Kind.HTTP,
                                status, responseMsg, parseApiError(errorBody), null, data);
                    default:
                        Log.e(TAG, "getAPIException: case : DEFAULT");
                        return new APIException(APIException.Kind.HTTP, responseMsg, data);
                }
            }
        }
        return new APIException(APIException.Kind.UNEXPECTED, "Unknown Error");
    }

    private APIError parseApiError(ResponseBody error) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(error.string(), APIError.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean useStrictValidation() {
        return false;
    }

    @Override
    public String getScenario() {
        return null;
    }

    private void removeFromCacheAsync() {
        final DataType<T> dataType = mDataType;
        final DataCache dataCache = mDataCache;

        if (dataType == null) {
            Log.w(TAG, "Data type is null abort clear caching!");
            return;
        }
        if (dataCache == null) {
            Log.w(TAG, "Data cache is null abort clear caching!");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataCache.remove(App.context, dataType);
                } catch (DBException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        }).start();
    }

    private void addToCacheAsync(final T apiResponse) {
        if (apiResponse == null || apiResponse.getVersion() <= 0) {
            return;
        }
        final DataType<T> dataType = mDataType;
        final DataCache dataCache = mDataCache;

        if (dataType == null) {
            Log.w(TAG, "Data type is null abort caching!");
            return;
        }
        if (dataCache == null) {
            Log.w(TAG, "Data cache is null abort caching!");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataCache.save(App.context, apiResponse, dataType);
                } catch (DBException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        }).start();
    }

    public abstract DataType<T> getDBDataType();
}
