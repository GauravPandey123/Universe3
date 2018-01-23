package in.editsoft.api.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Network util
 */
public class NetworkUtil implements APIConstants {

    /**
     * Current network is GSM
     */
    public static final int NETWORK_TYPE_GSM = 16;

    /**
     * Current network is Wifi
     */
    public static final int NETWORK_TYPE_WIFI = 17;
    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;

    /**
     * Unknown network class.
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    public static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    public static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks.
     */
    public static final int NETWORK_CLASS_4_G = 3;

    /**
     * Class of broadly defined "Wifi" networks.
     */
    public static final int NETWORK_CLASS_WIFI = 4;
    private static final String TAG = NetworkUtil.class.getSimpleName();

    public static int getNetworkClass() {
        return getNetworkClass(getNetworkType());
    }

    /**
     * Return general class of network type, such as "3G" or "4G". In cases
     * where classification is contentious, this method is conservative.
     */
    private static int getNetworkClass(int networkType) {
        switch (networkType) {
            case NetworkUtil.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetworkUtil.NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetworkUtil.NETWORK_CLASS_4_G;
            case NetworkUtil.NETWORK_TYPE_WIFI:
                return NetworkUtil.NETWORK_CLASS_WIFI;
            default:
                return NetworkUtil.NETWORK_CLASS_UNKNOWN;
        }
    }

    private static int getNetworkType() {
        Context context = App.context;
        if (checkForContext(context)) {
            return NetworkUtil.NETWORK_TYPE_UNKNOWN;
        }
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        if (networkInfo.isConnected()) {
//            return NetworkUtil.NETWORK_TYPE_WIFI;
//        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkType();
    }

    public static boolean isInternetConnected() {
        Context context = App.context;
        if (checkForContext(context)) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private static boolean checkForContext(Context context) {
        if (context == null) {
            Log.e(TAG, "Context can may not be null ", new NullPointerException("Initialize App context first to get network state"));
            return true;
        }
        return false;
    }
}
