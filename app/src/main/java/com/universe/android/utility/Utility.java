package com.universe.android.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.universe.android.R;
import com.universe.android.UniverseApplication;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * Created by Gaurav on 28/1/17.
 */

public class Utility {
    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;
    private static final int VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    private static final int VERSION_JELLYBEAN = Build.VERSION_CODES.JELLY_BEAN;
    private static final String IMAGE_NAME = "SavedImage.png";
    private static Context mContext = UniverseApplication.getContext();
    private static String formatedDate;
    private static String erg = "";
    private static TelephonyManager telephonyManager;

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

    public static boolean validateURL(String URL) {
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
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        if (clear) {
            editText.setText("");
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static boolean doesPackageExist(String targetPackage) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
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


    public static String getCurrentDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }
        return "Sunday";
    }

    public static String getCurrentTime() {
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        String time = simpleDateFormat.format(calander.getTime());
        return time;
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formatedDate = df.format(c.getTime());
        return formatedDate;
    }

    public static String dateDialogfrom() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        erg = String.valueOf(dayOfMonth);
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + year;

                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

        return erg;
    }


    public static String replaceSpaceToDashes(String value) {
        return value.replaceAll("\\s", "-");
    }


//    private static String getTodaysDate() {
//        DateTime date = new DateTime();
//        date = date.minusHours(7);
//
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(AppConstants.utc_format);
//        return dtf.print(date);
//    }


    public static void setEditFilter(EditText edSearch, int maxLength, String type, boolean isClear, boolean isAlpha) {
        if (edSearch != null) {
            if (AppConstants.NUMBER.equalsIgnoreCase(type)) {
                edSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (maxLength != 0) {
                    edSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                }
            } else {
                edSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                if (isAlpha && maxLength != 0) {
                    edSearch.setFilters(new InputFilter[]{new InputFilterAlphabets(), new InputFilter.LengthFilter(maxLength)});
                } else if (!isAlpha && maxLength != 0) {
                    edSearch.setFilters(new InputFilter[]{new InputFilterAlphaNumeric(), new InputFilter.LengthFilter(maxLength)});
                } else if (isAlpha) {
                    edSearch.setFilters(new InputFilter[]{new InputFilterAlphabets()});
                } else if (!isAlpha) {
                    edSearch.setFilters(new InputFilter[]{new InputFilterAlphaNumeric()});
                } else if (maxLength != 0) {
                    edSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                }
            }
            if (isClear)
                edSearch.getText().clear();

        }
    }

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static void setTextView(String title, TextView textViewId, boolean isMandatoryError) {
        if (Utility.validateString(title)) {
            if (title.contains("*")) {
                title = title.replace("*", "");
            }
        }
        if (isMandatoryError) {
            title = "<font color='#ff0000'>" + title + " * </font>";
        } else {
            title = "<font color='#000000'>" + title + "</font> " + AppConstants.ASTERIK_SIGN;
        }
        if (textViewId != null) {
            textViewId.setText(Html.fromHtml(title), TextView.BufferType.SPANNABLE);
        }
    }


    public static double round(double value, int places) {
        try {
            if (places > 0) {
                long factor = (long) Math.pow(10, places);
                value = value * factor;
                long tmp = Math.round(value);
                return (double) tmp / factor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static String getFormattedDate(String answer) {
        String formattedDate = null;
        try {
            if (Utility.validateString(answer)) {
                Date date1 = AppConstants.format4.parse(answer);
                if (date1 != null) {
                    formattedDate = AppConstants.format2.format(date1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    public static void setUIDTextViewFont(Context context, TextView textView) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "light.ttf");
        if (textView != null)
            textView.setTypeface(tf);
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    public static void showToastMessage(Activity activity, String strMsg) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_show, (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.tvToast);
        text.setText(strMsg);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static String getFormattedDates(String strCurrentDate, String dateFormat1, SimpleDateFormat format3) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat1);
            Date newDate = format.parse(strCurrentDate);
            return format3.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject formatDates(JSONObject jsonRequest) {
        try {
            if (jsonRequest != null) {
                if (jsonRequest.has(AppConstants.DATE)) {
                    String strCurrentDate = jsonRequest.optString(AppConstants.DATE);
                    if (strCurrentDate != null) {
                        String date;
                        date = Utility.getFormattedDates(strCurrentDate, AppConstants.format8, AppConstants.format3);
                        jsonRequest.put(AppConstants.DATE, date);
                    }
                }
                if (jsonRequest.has(AppConstants.EXPIRYDATE)) {
                    String strCurrentDate = jsonRequest.optString(AppConstants.EXPIRYDATE);
                    if (strCurrentDate != null) {
                        String date;
                        date = Utility.getFormattedDates(strCurrentDate, AppConstants.DATE_FORMAT, AppConstants.format1);
                        jsonRequest.put(AppConstants.EXPIRYDATE, date);
                    }
                }

                if (jsonRequest.has(AppConstants.CREATEDAT)) {
                    String strCurrentDate = jsonRequest.optString(AppConstants.CREATEDAT);
                    if (strCurrentDate != null) {
                        String date;
                        date = Utility.getFormattedDates(strCurrentDate, AppConstants.format8, AppConstants.format3);
                        jsonRequest.put(AppConstants.CREATEDAT, date);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }

    public static void animateView(View v) {
        if (v != null) {
            Animation fadeIn = new AlphaAnimation(0.5f, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(50);
            fadeIn.setStartOffset(100);
            Animation fadeOut = new AlphaAnimation(1, 0.5f);
            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
            fadeOut.setDuration(50);
            AnimationSet animation = new AnimationSet(false); //change to false
            animation.addAnimation(fadeOut);
            animation.addAnimation(fadeIn);
            v.startAnimation(animation);
        }
    }


    public static String getAssetJsonResponse(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        InputStream input;
        String text = "";

        try {
            input = assetManager.open(filename);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return text;
    }

    public static void saveLastSyncDate(Context context) {
        DateTime dateTime = new DateTime();
        DateTimeFormatter dtf = DateTimeFormat.forPattern(AppConstants.utc_format1);
        Prefs.putStringPrefs(AppConstants.LAST_SYNC_DATE, dtf.print(dateTime));
    }

    private void getImeiNumber()
    {

    }
    public static String getTodaysDate() {
        DateTime date = new DateTime();
        date = date.minusHours(7);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(AppConstants.utc_format);
        return dtf.print(date);
    }

}
