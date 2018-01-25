package com.universe.android.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.universe.android.R;
import com.universe.android.UniverseApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;


/**
 * Created by Gaurav on 28/1/17.
 */

public class Utility {
    private static Context mContext = UniverseApplication.getContext();
    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;
    private static final int VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    private static final int VERSION_JELLYBEAN = Build.VERSION_CODES.JELLY_BEAN;
    private static final String IMAGE_NAME = "SavedImage.png";

    public static boolean isJellyBean() {
        return BUILD_VERSION >= VERSION_JELLYBEAN;
    }

    public static boolean isLollipop() {
        return BUILD_VERSION >= VERSION_LOLLIPOP;
    }

    public static boolean validateString(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean validateEmail(String email) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        return emailPattern.matcher(email).matches();
    }

    public static boolean validateURL(String URL){
        Pattern urlPattern = Patterns.WEB_URL;
// Pattern urlPattern = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");
        return urlPattern.matcher(URL).matches();
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Point getDisplayInfo(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else {
            display.getSize(point);
        }
        return point;
    }

    public static void showToast(int msgId) {
        Toast.makeText(mContext, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static int getIntRes(int resId) {
        return mContext.getResources().getInteger(resId);
    }

    public static String getStringRes(int resId) {
        return mContext.getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return mContext.getResources().getStringArray(resId);
    }

    public static int getColorRes(int resId) {
        return mContext.getResources().getColor(resId);
    }

    public static float getDimenRes(int resId) {
        return mContext.getResources().getDimension(resId);
    }

    public static Drawable getDrawableRes(int id) {
        return mContext.getResources().getDrawable(id);
    }

    public static Drawable getDrawableImage(String name) {
        int drawable = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        return ContextCompat.getDrawable(mContext, drawable);
    }

    public static File getSavedImage(boolean cache) {
        String path;
        if (cache) {
            path = mContext.getCacheDir() + "/" + System.currentTimeMillis() + ".png";
        } else {
            path = mContext.getCacheDir() + IMAGE_NAME;
        }
        return new File(path);
    }

    public static int getSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }




    @SuppressLint("NewApi")
    public static void setDrawable(View view, int value) {
        if (mContext != null && view != null) {
            Drawable drawable = getDrawableRes(value);
            if (isJellyBean()) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    public static void setProgressDrawable(ProgressBar pBar, int drawable) {
        if (!isLollipop()) {
            pBar.setIndeterminateDrawable(getDrawableRes(drawable));
        } else {
            pBar.getIndeterminateDrawable().setColorFilter(getColorRes(R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    public static void hideKeyboard(EditText editText) {
        hideKeyboard(editText, false);
    }

    public static void hideKeyboard(EditText editText, boolean clear) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        if (clear) {
            editText.setText("");
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static boolean doesPackageExist(String targetPackage){
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public static PackageInfo getPackageInfo() {
        PackageInfo packageInfo = null;
        PackageManager manager = mContext.getPackageManager();
        try {
            packageInfo = manager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


    public static String getCurrentDay(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        switch (day){
            case Calendar.SUNDAY: return "Sunday";
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: return "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
        }
        return "Sunday";
    }

    public static String getCurrentTime(){
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        String time = simpleDateFormat.format(calander.getTime());
        return time;
    }

    public static String replaceSpaceToDashes(String value){
        return value.replaceAll("\\s","-");
    }
}
