package in.editsoft.api.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import in.editsoft.api.BuildConfig;
import in.editsoft.api.callback.APICallback;
import in.editsoft.api.exception.APIException;
import in.editsoft.api.request.IRequest;
import in.editsoft.api.response.IResponse;
import in.editsoft.api.util.APIConstants;
import in.editsoft.api.util.DateTimeAdapter;
import in.editsoft.api.util.NetworkUtil;
import in.editsoft.api.util.Validation;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Service class to serve api requests
 */
public abstract class APIService<U, T extends IRequest, L extends IResponse<L>> implements IService<U, T, L>, APIConstants {
    private static final String TAG = APIService.class.getSimpleName();
    /**
     * Use to build Gson object for serialization and deserialization of pojo
     */
    private static Gson sGson;
    /**
     * Default loging interceptor shared across all services
     */
    private static HttpLoggingInterceptor sLoggingInterceptor;

    @Override
    public final void execute(final T request, final APICallback<L> callback) throws APIException {
        if (callback == null) {
            throw new APIException(APIException.Kind.NULL, "Callback may not be null");
        } else if (getAPI() == null) {
            callback.onComplete();
            callback.onFailure(new APIException(APIException.Kind.NULL, "API Interface may not be null"));
        } else if (request == null) {
            callback.onComplete();
            callback.onFailure(new APIException(APIException.Kind.NULL, "Request may not be null"));
        } else if (!request.isValid(request.getScenario())) {
            callback.onComplete();
            callback.onFailure(new APIException(APIException.Kind.INVALID_REQUEST, "Invalid request"));
        } else {
            String baseUrl = request.getUrl();
            if (Validation.isNullOrEmpty(baseUrl)) {
                callback.onComplete();
                callback.onFailure(new APIException(APIException.Kind.NULL, "Base Url may not be null"));
                return;
            }


            //TODO Handle Cookie Manager and improvise
            Log.e(TAG, "execute: cookieManager Added");
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(getConnectionTimeoutInSeconds(), SECONDS)
                    .readTimeout(getReadTimeoutInSeconds(), SECONDS)
                    .addInterceptor(getHeaderInterceptor(request.getHeaders()))
                    .cookieJar(new JavaNetCookieJar(cookieManager));
            Interceptor loggingInterceptor = getLoggingInterceptor();
            if (loggingInterceptor != null) {
                builder.addInterceptor(loggingInterceptor);
            }
            Interceptor networkInterceptor = getNetworkInterceptor();
            if (networkInterceptor != null) {
                builder.addNetworkInterceptor(networkInterceptor);
            }
            Interceptor addCookiesInterceptor = getAddCookiesInterceptor();
            if (addCookiesInterceptor != null) {
                builder.addInterceptor(addCookiesInterceptor);
            }
            Interceptor receivedCookiesInterceptor = getReceivedCookiesInterceptor();
            if (receivedCookiesInterceptor != null) {
                builder.addInterceptor(receivedCookiesInterceptor);
            }
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
//            final Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//
//                    .addConverterFactory(GsonConverterFactory.create(getGson()))
//                    .build();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    request.setVersion(callback.getCachedResponseVersion());
                    Call<L> call = APIService.this.onExecute(retrofit.create(getAPI()), request);
                    call.enqueue(callback);
                }
            }).start();
        }
    }

    private Interceptor getHeaderInterceptor(final Map<String, String> headers) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (headers == null) {
                    return chain.proceed(request);
                }
                Set<String> headerKeys = headers.keySet();
                Request.Builder builder = request.newBuilder();
                for (String key : headerKeys) {
                    builder.addHeader(key, headers.get(key));
                }
                return chain.proceed(builder.build());
            }
        };
    }

    protected Interceptor getLoggingInterceptor() {
        if (sLoggingInterceptor != null) {
            return sLoggingInterceptor;
        }

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, " : " + message);
                    }
                });
        HttpLoggingInterceptor.Level logLevel = getLogLevel();
        if (logLevel == null) {
            logLevel = HttpLoggingInterceptor.Level.BASIC;
        }
        httpLoggingInterceptor.setLevel(logLevel);

        return sLoggingInterceptor = httpLoggingInterceptor;
    }

    protected Interceptor getNetworkInterceptor() {
        return null;
    }

    protected Interceptor getAddCookiesInterceptor() {
        return null;
    }

    protected Interceptor getReceivedCookiesInterceptor() {
        return null;
    }


    private Gson getGson() {
        if (sGson != null) {
            return sGson;
        }
        GsonBuilder gsonBuilder = getGsonBuilder();
        //default implementation for Gson converter
        if (gsonBuilder == null) {
            gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateTimeAdapter());
        }
        return sGson = getGsonBuilder().create();
    }

    @Override
    public HttpLoggingInterceptor.Level getLogLevel() {
        return BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC;
    }

    @Override
    public GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.registerTypeAdapter(Date.class, new DateTimeAdapter());
    }

    @Override
    public long getConnectionTimeoutInSeconds() {
        return NetworkUtil.getNetworkClass() == NetworkUtil.NETWORK_CLASS_2_G ? TWO_G_TIMEOUT : THREE_G_TIMEOUT;
    }

    @Override
    public long getReadTimeoutInSeconds() {
        return NetworkUtil.getNetworkClass() == NetworkUtil.NETWORK_CLASS_2_G ? TWO_G_TIMEOUT : THREE_G_TIMEOUT;
    }

}
