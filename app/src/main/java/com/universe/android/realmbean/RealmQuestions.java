package com.universe.android.realmbean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmQuestions extends RealmObject {
    @PrimaryKey
    private String _id;

    private String formId;

    private String formName;

    private String formSchema;




    private String createdAt;


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormSchema() {
        return formSchema;
    }

    public void setFormSchema(String formSchema) {
        this.formSchema = formSchema;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
}
