package com.universe.android.listneners;

import android.graphics.Bitmap;

/**
 * Created by Konstantin on 12.01.2015.
 */
public interface ScreenShotable {
    public void takeScreenShot();

    public Bitmap getBitmap();
}
