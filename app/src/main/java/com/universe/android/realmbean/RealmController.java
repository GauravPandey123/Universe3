package com.universe.android.realmbean;

import android.content.Context;

import android.util.Log;


import com.universe.android.model.Questions;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    public Questions getQuestionVOFromJson(JSONObject jsonObject) {
        Questions question = new Questions();
        if (jsonObject != null) {
            question.setTitle(jsonObject.optString("title"));
            question.setDescription(jsonObject.optString("description"));
            question.setInputType(jsonObject.optString("inputType"));
            question.setType(jsonObject.optString("type"));
            question.setDisplayOrder(jsonObject.optInt("displayOrder"));
            question.setPlaceholder(jsonObject.optString("placeholder"));
            question.setApiModel(jsonObject.optString("apiModel"));
            question.setOptionValues(jsonObject.optString("optionValues"));
            question.setVisibility(jsonObject.optBoolean("visibility"));
            question.setRequired(jsonObject.optBoolean("required"));
            question.setMaxLength(jsonObject.optInt("maxLength"));
            question.setMaxValue(jsonObject.optInt("maxValue"));
            question.setMinValue(jsonObject.optInt("minValue"));
            question.setMaximum(jsonObject.optInt("maximum"));
            question.setMinimum(jsonObject.optInt("minimum"));
            question.setAlpha(jsonObject.optBoolean("alpha"));
            question.setHindiTitle(jsonObject.optString("hindiTitle"));

        }
        return question;
    }

    public void clearRealm(Context context) {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();

            realm.delete(RealmQuestions.class);
        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    public void saveQuestions(Context context) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(RealmQuestions.class, new JSONArray(Utility.getAssetJsonResponse(context,"questions.json")));
            } catch (Exception e) {
                if (realm.isInTransaction())
                realm.cancelTransaction();
                e.printStackTrace();
            } finally {
                if (realm.isInTransaction())
                realm.commitTransaction();
                realm.close();
            }

    }


}
