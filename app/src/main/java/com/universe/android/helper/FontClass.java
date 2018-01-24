package com.universe.android.helper;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by gaurva on 21/8/15.
 */
public class FontClass {
    private static Typeface openSansBold;
    private static Typeface openSansLight;
    private static Typeface openSansRegular;
    private static Typeface openSemiBold;
    private static Typeface simple_print;

    public static Typeface openSansBold(Context appContext) {
        if (openSansBold == null) {
            openSansBold = Typeface.createFromAsset(appContext.getAssets(), "fonts/opensans-bold.ttf");
        }
        return openSansBold;
    }

    public static Typeface openSansLight(Context appContext) {
        if (openSansLight == null) {
            openSansLight = Typeface.createFromAsset(appContext.getAssets(), "fonts/OpenSans_Light.ttf");
        }
        return openSansLight;
    }

    public static Typeface openSansRegular(Context appContext) {
        if (openSansRegular == null) {
            openSansRegular = Typeface.createFromAsset(appContext.getAssets(), "fonts/OpenSans_Regular.ttf");
        }
        return openSansRegular;
    }

    public static Typeface openSemiBold(Context appContext) {
        if (openSemiBold == null) {
            openSemiBold = Typeface.createFromAsset(appContext.getAssets(), "fonts/OpenSans_Semibold.ttf");
        }
        return openSansRegular;
    }

    public static Typeface simplePrint(Context appContext) {
        if (simple_print == null) {
            simple_print = Typeface.createFromAsset(appContext.getAssets(), "fonts/Simple Print.ttf");
        }
        return simple_print;
    }
}
