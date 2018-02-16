package com.universe.android.component;



import com.google.common.base.Predicate;
import com.universe.android.model.MultiSpinnerList;
import com.universe.android.model.Questions;
import com.universe.android.model.SpinnerList;
import com.universe.android.utility.Utility;

import java.util.Map;


public class FilterPredicate {

    public final Predicate<SpinnerList> predicateSpnList = new Predicate<SpinnerList>() {
        public boolean apply(SpinnerList question) {
            if (question.isChecked()) {
                return true;
            }
            return false;
        }
    };
    public final Predicate<Questions> validQuestionPredicate = new Predicate<Questions>() {
        public boolean apply(Questions question) {
            if (question.isRequired()) {
                return !Utility.validateString(question.getAnswer());
            }
            return false;
        }
    };
    public final Predicate<Map<String, Questions>> validQuestionMapPredicate = new Predicate<Map<String, Questions>>() {
        @Override
        public boolean apply(Map<String, Questions> input) {
            for (Map.Entry<String, Questions> entry : input.entrySet()) {
                Questions question = (Questions) entry.getValue();
                if (question.isRequired()) {
                    return !Utility.validateString(question.getAnswer());
                }
            }
            return false;
        }

    };
    public final Predicate<MultiSpinnerList> filterMultiSpnList = new Predicate<MultiSpinnerList>() {
        public boolean apply(MultiSpinnerList question) {
            if (question.isChecked()) {
                return true;
            }
            return false;
        }
    };

    public final Predicate<MultiSpinnerList> filterMultiSpnListEdittext = new Predicate<MultiSpinnerList>() {
        public boolean apply(MultiSpinnerList question) {
            if (!question.isChecked()) {
                return true;
            }
            return false;
        }
    };
}
