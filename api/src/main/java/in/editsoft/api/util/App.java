package in.editsoft.api.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Gives App context
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        context = getApplicationContext();
    }

}
