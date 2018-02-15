package com.universe.android.web;

import android.content.Context;
import android.util.Log;


import com.universe.android.BuildConfig;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Constants;
import com.universe.android.utility.Prefs;

import java.util.Map;

import in.editsoft.api.callback.APICallback;
import in.editsoft.api.exception.APIException;
import in.editsoft.api.interceptor.AddCookiesInterceptor;
import in.editsoft.api.interceptor.ReceivedCookiesInterceptor;
import in.editsoft.api.service.APIService;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * BaseService
 */
public abstract class BaseService<U, T extends BaseRequest, L extends BaseResponse<L>> extends APIService<U, T, L> {
    public Context mContext = null;
    private boolean addCookies = false;
    private boolean receiveCookies = false;
    private static final int MAX_LOG_LENGTH = 500;
    public static final String KEY_X_AUTH = "X-AUTHORIZATION";
    public static final String KEY_USER_AUTH = "X-USER-AUTH";
    public static final String X_API_DEBUG = "X-API-DEBUG";
    private static HttpLoggingInterceptor sLoggingInterceptor;

    public void executeService(T request, APICallback<L> callback) {
        request.setAuthToken(Prefs.getStringPrefs(AppConstants.AUTHORIZATION));
        request.addHeader("X-APP-VERSION", BuildConfig.VERSION_NAME);
        request.addHeader(AppConstants.TOKEN_KEY, Prefs.getStringPrefs(AppConstants.AUTHORIZATION));
        request.addHeaders(getHeaders());
        try {
            execute(request, callback);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    public void setContext(Context context) {
        mContext = context;
    }

    protected Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public HttpLoggingInterceptor.Level getLogLevel() {
        return HttpLoggingInterceptor.Level.BODY;
    }

    @Override
    protected Interceptor getLoggingInterceptor() {
        if (sLoggingInterceptor != null) {
            return sLoggingInterceptor;
        } else {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    // Split by line, then ensure each line can fit into Log's maximum length.
                    for (int i = 0, length = message.length(); i < length; i++) {
                        int newline = message.indexOf('\n', i);
                        newline = newline != -1 ? newline : length;
                        do {
                            int end = Math.min(newline, i + MAX_LOG_LENGTH);
                            Log.d("EDITSOFT_API", message.substring(i, end));
                            i = end;
                        } while (i < newline);
                    }
                }
            });

            HttpLoggingInterceptor.Level logLevel = this.getLogLevel();
            if (logLevel == null) {
                logLevel = HttpLoggingInterceptor.Level.BASIC;
            }

            httpLoggingInterceptor.setLevel(logLevel);
            sLoggingInterceptor = httpLoggingInterceptor;
            return httpLoggingInterceptor;
        }
    }

    @Override
    protected Interceptor getAddCookiesInterceptor() {
        if (mContext != null && addCookies) {
            return new AddCookiesInterceptor(mContext);
        }
        return null;
    }

    @Override
    protected Interceptor getReceivedCookiesInterceptor() {
        if (mContext != null || receiveCookies) {
            return new ReceivedCookiesInterceptor(mContext);
        }
        return null;
    }

    public void shouldSendCookies(boolean bool) {
        addCookies = bool;
    }

    public void shouldReceiveCookies(boolean bool) {
        receiveCookies = bool;
    }
}
