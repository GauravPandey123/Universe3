package in.editsoft.api.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

//TODO Make A More Generic one
public class ReceivedCookiesInterceptor implements Interceptor {
    private static final String TAG = ReceivedCookiesInterceptor.class.getSimpleName();
    private Context context;
    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    } // AddCookiesInterceptor()
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            SharedPreferences preferences = context.getSharedPreferences("PREFERENCES",Context.MODE_PRIVATE);
            //TODO Decide do we need old Cookie
            HashSet<String> cookies = new HashSet<>();
//            HashSet<String> cookies = (HashSet<String>) preferences.getStringSet("PREF_COOKIES", new HashSet<String>());

            for (String header : originalResponse.headers("Set-Cookie")) {
                Log.e(TAG, "intercept: " + header);
                cookies.add(header);
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet("PREF_COOKIES", cookies).apply();
            editor.commit();

//            SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
//            memes.putStringSet("PREF_COOKIES", cookies).apply();
//            memes.commit();
        }

        return originalResponse;
    }
}