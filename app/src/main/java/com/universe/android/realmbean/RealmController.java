package com.universe.android.realmbean;

import android.content.Context;

import com.universe.android.enums.FormEnum;
import com.universe.android.model.Questions;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public Questions getSurveyQuestionVOFromJson(RealmQuestion realmQuestion) {
        Questions question = new Questions();
        if (realmQuestion != null) {
            if (realmQuestion.getVisibility().equalsIgnoreCase("Yes")){
                question.setVisibility(true);
            }else {
                question.setVisibility(false);
            }
            if (realmQuestion.getRequired().equalsIgnoreCase("true")){
                question.setRequired(true);
            }else {
                question.setRequired(false);
            }

            question.setTitle(realmQuestion.getTitle());
            question.setInputType(realmQuestion.getInputType());
            question.setType(realmQuestion.getType());
            question.setDisplayOrder(realmQuestion.getDisplayOrder());
            question.setPlaceholder(realmQuestion.getPlaceholder());
            question.setOptionValues(realmQuestion.getOptionValues());

            question.setMaxLength(realmQuestion.getMaxLength());
            question.setMaxValue(realmQuestion.getMaxValue());
            question.setMinValue(realmQuestion.getMinValue());
            question.setResponses(realmQuestion.getResponses());
            question.setCategoryId(realmQuestion.getCategoryId());
            question.setSurveyId(realmQuestion.getSurveyId());
            question.setQuestionId(realmQuestion.getId());
            question.setLongTitle(realmQuestion.getLongTitle());

        }
        return question;
    }


    public boolean isAnySyncPending() {
        boolean isPending = false;
        Realm realm = Realm.getDefaultInstance();
        try {

            RealmResults<RealmSurveys> realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ISSYNC, true).equalTo(AppConstants.ISUPDATE, false).findAll();
            RealmResults<RealmSurveys> realmUpdateSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ISSYNC, true).equalTo(AppConstants.ISUPDATE, false).findAll();

            if (realmSurveys != null && realmSurveys.size() > 0) {
                isPending = true;
            } else if (realmUpdateSurveys != null && realmUpdateSurveys.size() > 0) {
                isPending = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
        } finally {
            realm.close();

        }
        return isPending;
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

    public void saveSyncFormInputResponse(String responseData, boolean isUpdate) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if (responseData != null) {
                JSONArray jsonResponse = new JSONArray(responseData);
                if (jsonResponse != null && jsonResponse.length() > 0) {
                    if (isUpdate) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            jsonResponse.optJSONObject(i).put(AppConstants.ISUPDATE, true);
                        }
                    } else {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            jsonResponse.optJSONObject(i).put(AppConstants.ISSYNC, true);
                        }
                    }
                }
                realm.beginTransaction();
                realm.createOrUpdateAllFromJson(RealmSurveys.class, jsonResponse);
                if (!isUpdate) {
                    RealmResults<RealmSurveys> realmDeleteInputForms = realm.where(RealmSurveys.class).equalTo(AppConstants.ISSYNC, false).findAll();
                    if (realmDeleteInputForms != null && realmDeleteInputForms.size() > 0) {
                        realmDeleteInputForms.deleteAllFromRealm();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            realm.close();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }


    public void saveFormInputFromSubmit(String responseData, String isUpdate, String formId) {
        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject jsonResponse = new JSONObject(responseData);
            if (jsonResponse != null) {
                realm.beginTransaction();
                if (Utility.validateString(isUpdate)) {
                    jsonResponse.put(AppConstants.ISUPDATE, true);
                } else {
                    jsonResponse.put(AppConstants.ISSYNC, true);
                }
                if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmSurveys.class, jsonResponse);
                } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmClient.class, jsonResponse);
                } else if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmCustomer.class, jsonResponse);
                } else if (formId.equalsIgnoreCase(FormEnum.category.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmCategory.class, jsonResponse);
                } else if (formId.equalsIgnoreCase(FormEnum.question.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmQuestion.class, jsonResponse);
                }
            }
        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    public void saveFormInputFromAnswersSubmit(String responseData, String isUpdate, String formId) {
        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject jsonResponse = new JSONObject(responseData);
            if (jsonResponse != null) {
                realm.beginTransaction();
                if (Utility.validateString(isUpdate)) {
                    jsonResponse.put(AppConstants.ISUPDATE, true);
                } else {
                    jsonResponse.put(AppConstants.ISSYNC, true);
                }

                    realm.createOrUpdateObjectFromJson(RealmAnswers.class, jsonResponse);

            }
        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }
    public void saveUserDetail(String responseData) {


        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateObjectFromJson(RealmUser.class, responseData);
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
    public void saveQuestions(String responseData) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(RealmQuestions.class, new JSONArray(responseData));
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
    public void saveAnswers(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateAllFromJson(RealmAnswers.class, new JSONArray(responseData));
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

    public void saveSurveyQuestions(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateAllFromJson(RealmQuestion.class, new JSONArray(responseData));
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


    public void saveSurveysResponse(String responseData) {
        if (responseData != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(RealmSurveys.class, new JSONArray(responseData));
            } catch (Exception e) {
                realm.cancelTransaction();
                realm.close();
                e.printStackTrace();
            } finally {
                realm.commitTransaction();
                realm.close();
            }
        }
    }

    public void saveClientsResponse(String responseData) {
        if (responseData != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(RealmClient.class, new JSONArray(responseData));
            } catch (Exception e) {
                realm.cancelTransaction();
                realm.close();
                e.printStackTrace();
            } finally {
                realm.commitTransaction();
                realm.close();
            }
        }
    }

    public void saveCustomersResponse(String responseData) {
        if (responseData != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(RealmCustomer.class, new JSONArray(responseData));
            } catch (Exception e) {
                realm.cancelTransaction();
                realm.close();
                e.printStackTrace();
            } finally {
                realm.commitTransaction();
                realm.close();
            }
        }
    }

    public void saveCategoryResponse(String responseData) {
        if (responseData != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(RealmCategory.class, new JSONArray(responseData));
            } catch (Exception e) {
                realm.cancelTransaction();
                realm.close();
                e.printStackTrace();
            } finally {
                realm.commitTransaction();
                realm.close();
            }
        }
    }
}
