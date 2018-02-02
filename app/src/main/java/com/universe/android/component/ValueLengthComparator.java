package com.universe.android.component;

import com.google.common.primitives.Ints;

import java.util.Comparator;


public class ValueLengthComparator implements Comparator<String> {


    @Override
    public int compare(String left, String right) {
        if (left != null && right != null) {
            return Ints.compare(right.length(), left.length());
        }
        return 0;
    }
}
