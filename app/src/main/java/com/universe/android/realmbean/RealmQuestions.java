package com.universe.android.realmbean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmQuestions extends RealmObject {
    @PrimaryKey
    private String id;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
