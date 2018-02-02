package com.universe.android.component;




import com.universe.android.model.Questions;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QuestionMapComparator {

    public static Map<String, Questions> sortByValue(Map<String, Questions> unsortMap) {

        List<Map.Entry<String, Questions>> list =
                new LinkedList<Map.Entry<String, Questions>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Questions>>() {
            public int compare(Map.Entry<String, Questions> o1,
                               Map.Entry<String, Questions> o2) {
                return (o1.getValue().getDisplayOrder()) - (o2.getValue().getDisplayOrder());
            }
        });

        Map<String, Questions> sortedMap = new LinkedHashMap<String, Questions>();
        for (Map.Entry<String, Questions> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }
}
