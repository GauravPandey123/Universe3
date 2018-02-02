package com.universe.android.utility;

import android.text.InputFilter;
import android.text.Spanned;


public class InputFilterMinMaxFloat implements InputFilter {
    private int min, max;

    public InputFilterMinMaxFloat(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMaxFloat(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            float input = Float.valueOf(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, float c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}