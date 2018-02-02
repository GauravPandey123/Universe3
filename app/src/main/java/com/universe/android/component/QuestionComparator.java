package com.universe.android.component;



import com.universe.android.model.Questions;

import java.util.Comparator;

/**
 * Created by nikul on 25/4/17.
 */

public class QuestionComparator implements Comparator<Questions> {

    @Override
    public int compare(Questions o1, Questions o2) {
        return o1.getDisplayOrder() - (o2.getDisplayOrder());
    }
}
