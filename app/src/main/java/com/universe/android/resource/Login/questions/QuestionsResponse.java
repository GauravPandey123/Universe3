package com.universe.android.resource.Login.questions;

import com.google.gson.annotations.SerializedName;
import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class QuestionsResponse extends BaseResponse<QuestionsResponse> {
    @Override
    public boolean isValid(String condition, boolean isStrict) {
        return true;
    }

    /**
     * errorMsg :
     * statusCode : 200
     * response : [{"_id":"5a829efb31083100ccb70a8a","formId":"survey","formName":"Survey","formSchema":{"updatedAt":"2018-01-31T04:12:49.622Z","createdAt":"2018-01-31T04:12:49.622Z","properties":{"required":["title","status","expiryDate","isActive"],"description":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"},"expiryDate":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"status":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":0,"maxValue":120,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"},"title":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}},"type":"object"}},{"_id":"5a829f6931083100ccb70a8f","formId":"client","formName":"Client","formSchema":{"updatedAt":"2018-01-31T04:12:49.622Z","createdAt":"2018-01-31T04:12:49.622Z","properties":{"required":["name","contactPerson","contactNo","isActive","surveyId","expiryDate","status","address"],"surveyId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmSurveys","placeholder":"Select Survey","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Survey","displayOrder":2,"inputType":"select"},"description":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"},"address":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Address","displayOrder":11,"inputType":"textarea"},"expiryDate":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"status":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":15,"maxValue":1200,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"},"contactNo":{"required":false,"alpha":false,"validationMessage":{"302":"Number is Required!!"},"maxValueLength":10,"maxLength":10,"visibility":true,"type":"long","description":"","hindiTitle":"मोबाइल नंबर","title":"Mobile No.","displayOrder":13,"inputType":"textbox"},"contactPerson":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Contact Person Name","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"},"name":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}},"type":"object"}},{"_id":"5a82a09831083100ccb70a9e","formId":"customer","formName":"Customer","formSchema":{"updatedAt":"2018-01-31T04:12:49.622Z","createdAt":"2018-01-31T04:12:49.622Z","properties":{"required":["name","contactPerson","contactNo","isActive","surveyId","expiryDate","status","address","clientId"],"clientId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmClient","placeholder":"Select Client","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Client","displayOrder":2,"inputType":"select"},"surveyId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmSurveys","placeholder":"Select Survey","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Survey","displayOrder":2,"inputType":"select"},"description":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"},"address":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Address","displayOrder":11,"inputType":"textarea"},"expiryDate":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"status":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":15,"maxValue":1200,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"},"contactNo":{"required":false,"alpha":false,"validationMessage":{"302":"Number is Required!!"},"maxValueLength":10,"maxLength":10,"visibility":true,"type":"long","description":"","hindiTitle":"मोबाइल नंबर","title":"Mobile No.","displayOrder":13,"inputType":"textbox"},"contactPerson":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Contact Person Name","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"},"name":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"maxValueLength":100,"required":true,"visibility":true,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}},"type":"object"}},{"_id":"5a82a0cb31083100ccb70aa3","formId":"category","formName":"Category","formSchema":{"updatedAt":"2018-01-31T04:12:49.622Z","createdAt":"2018-01-31T04:12:49.622Z","properties":{"required":["categoryType","categoryName","contactNo","isActive","surveyId","expiryDate","status","address","clientId","customerId"],"customerId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmCustomer","placeholder":"Select Customer","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Customer","displayOrder":2,"inputType":"select"},"clientId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmClient","placeholder":"Select Client","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Client","displayOrder":2,"inputType":"select"},"surveyId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmSurveys","placeholder":"Select Survey","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Survey","displayOrder":2,"inputType":"select"},"description":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"},"address":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Address","displayOrder":11,"inputType":"textarea"},"expiryDate":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"status":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":15,"maxValue":1200,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"},"contactNo":{"required":false,"alpha":false,"validationMessage":{"302":"Number is Required!!"},"maxValueLength":10,"maxLength":10,"visibility":true,"type":"long","description":"","hindiTitle":"मोबाइल नंबर","title":"Mobile No.","displayOrder":13,"inputType":"textbox"},"categoryName":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Category Name","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"},"categoryType":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"CategoryType","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}},"type":"object"}},{"_id":"5a82a17631083100ccb70aab","formId":"question","formName":"Question","formSchema":{"updatedAt":"2018-01-31T04:12:49.622Z","createdAt":"2018-01-31T04:12:49.622Z","properties":{"categoryId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmCustomer","placeholder":"Select Category","type":"string","description":"","hindiTitle":"","title":"Category","displayOrder":2,"inputType":"select"},"customerId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmCustomer","placeholder":"Select Customer","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Customer","displayOrder":2,"inputType":"select"},"clientId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmClient","placeholder":"Select Client","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Client","displayOrder":2,"inputType":"select"},"surveyId":{"required":true,"alpha":false,"visibility":true,"apiModel":"RealmSurveys","placeholder":"Select Survey","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Survey","displayOrder":2,"inputType":"select"},"orientation":{"required":true,"visibility":true,"optionValues":["Vertical","Horizontal"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Orientation","displayOrder":8,"inputType":"radio"},"optionValues":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"Option Values","displayOrder":1,"inputType":"select"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"visibility":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Visible","displayOrder":8,"inputType":"radio"},"displayOrder":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":0,"visibility":true,"minValue":0,"maxValue":0,"type":"number","description":"","hindiTitle":"आयु","title":"Display Order","displayOrder":7,"inputType":"textbox"},"maxLength":{"required":false,"alpha":false,"validationMessage":{"302":"Number is Required!!"},"maxValueLength":0,"maxLength":3,"visibility":true,"type":"number","description":"","hindiTitle":"मोबाइल नंबर","title":"Maximum Length","displayOrder":13,"inputType":"textbox"},"minValue":{"required":false,"alpha":false,"validationMessage":{"302":"Number is Required!!"},"maxValueLength":0,"maxLength":3,"visibility":true,"type":"number","description":"","hindiTitle":"मोबाइल नंबर","title":"Minimum Value","displayOrder":13,"inputType":"textbox"},"maxValue":{"required":false,"alpha":false,"validationMessage":{"302":"Number is Required!!"},"maxValueLength":0,"maxLength":3,"visibility":true,"type":"number","description":"","hindiTitle":"मोबाइल नंबर","title":"Maximum Value","displayOrder":13,"inputType":"textbox"},"required":["title","longTitle","inputType","type","required","isActive","visibility","displayOrder","surveys","clientId","customerId","categoryId"],"type":{"required":true,"visibility":true,"optionValues":["String","Number","Float","Long"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"","title":"Type","displayOrder":8,"inputType":"select"},"inputType":{"required":true,"optionValues":["textBox","radio","checkbox"],"enum":["Yes","No"],"alpha":false,"visibility":true,"placeholder":"Select InputType","type":"string","description":"","hindiTitle":"पंचायत का नाम","title":"Input Type","displayOrder":2,"inputType":"select"},"longTitle":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"maxValueLength":1000,"maxLength":1000,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Category Name","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"},"title":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}},"type":"object"}}]
     */

    private String errorMsg;
    private int statusCode;
    private List<ResponseBean> response;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * _id : 5a829efb31083100ccb70a8a
         * formId : survey
         * formName : Survey
         * formSchema : {"updatedAt":"2018-01-31T04:12:49.622Z","createdAt":"2018-01-31T04:12:49.622Z","properties":{"required":["title","status","expiryDate","isActive"],"description":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"},"expiryDate":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"status":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":0,"maxValue":120,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"},"title":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}},"type":"object"}
         */

        private String _id;
        private String formId;
        private String formName;
        private FormSchemaBean formSchema;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public FormSchemaBean getFormSchema() {
            return formSchema;
        }

        public void setFormSchema(FormSchemaBean formSchema) {
            this.formSchema = formSchema;
        }

        public static class FormSchemaBean {
            /**
             * updatedAt : 2018-01-31T04:12:49.622Z
             * createdAt : 2018-01-31T04:12:49.622Z
             * properties : {"required":["title","status","expiryDate","isActive"],"description":{"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"},"expiryDate":{"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"},"isActive":{"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"},"status":{"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":0,"maxValue":120,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"},"title":{"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}}
             * type : object
             */

            private String updatedAt;
            private String createdAt;
            private PropertiesBean properties;
            private String type;

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public PropertiesBean getProperties() {
                return properties;
            }

            public void setProperties(PropertiesBean properties) {
                this.properties = properties;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class PropertiesBean {
                /**
                 * required : ["title","status","expiryDate","isActive"]
                 * description : {"required":false,"alpha":false,"visibility":true,"maxValueLength":200,"type":"string","description":"","hindiTitle":"टिप्पणियों","title":"Description","displayOrder":11,"inputType":"textarea"}
                 * expiryDate : {"required":true,"visibility":true,"alpha":false,"format":"date","type":"string","description":"","hindiTitle":"तिथि","title":"ExpiryDate","displayOrder":1,"inputType":"date"}
                 * isActive : {"required":true,"visibility":true,"optionValues":["Yes","No"],"enum":["Yes","No"],"type":"string","description":"","hindiTitle":"नैदानिक परीक्षण किया गया है या नहीं?","title":"Active","displayOrder":8,"inputType":"radio"}
                 * status : {"validationMessage":{"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"},"required":true,"alpha":false,"minimum":0,"maximum":1000,"visibility":true,"minValue":0,"maxValue":120,"type":"number","description":"","hindiTitle":"आयु","title":"Status","displayOrder":7,"inputType":"textbox"}
                 * title : {"validationMessage":{"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"},"required":true,"visibility":true,"maxValueLength":100,"maxLength":100,"type":"string","description":"","hindiTitle":"व्यक्ति का नाम मिले","title":"Title","pattern":"^[A-Za-z\\s]+$","displayOrder":5,"inputType":"textbox"}
                 */

                private DescriptionBean description;
                private ExpiryDateBean expiryDate;
                private IsActiveBean isActive;
                private StatusBean status;
                private TitleBean title;
                private List<String> required;

                public DescriptionBean getDescription() {
                    return description;
                }

                public void setDescription(DescriptionBean description) {
                    this.description = description;
                }

                public ExpiryDateBean getExpiryDate() {
                    return expiryDate;
                }

                public void setExpiryDate(ExpiryDateBean expiryDate) {
                    this.expiryDate = expiryDate;
                }

                public IsActiveBean getIsActive() {
                    return isActive;
                }

                public void setIsActive(IsActiveBean isActive) {
                    this.isActive = isActive;
                }

                public StatusBean getStatus() {
                    return status;
                }

                public void setStatus(StatusBean status) {
                    this.status = status;
                }

                public TitleBean getTitle() {
                    return title;
                }

                public void setTitle(TitleBean title) {
                    this.title = title;
                }

                public List<String> getRequired() {
                    return required;
                }

                public void setRequired(List<String> required) {
                    this.required = required;
                }

                public static class DescriptionBean {
                    /**
                     * required : false
                     * alpha : false
                     * visibility : true
                     * maxValueLength : 200
                     * type : string
                     * description :
                     * hindiTitle : टिप्पणियों
                     * title : Description
                     * displayOrder : 11
                     * inputType : textarea
                     */

                    private boolean required;
                    private boolean alpha;
                    private boolean visibility;
                    private int maxValueLength;
                    private String type;
                    private String description;
                    private String hindiTitle;
                    private String title;
                    private int displayOrder;
                    private String inputType;

                    public boolean isRequired() {
                        return required;
                    }

                    public void setRequired(boolean required) {
                        this.required = required;
                    }

                    public boolean isAlpha() {
                        return alpha;
                    }

                    public void setAlpha(boolean alpha) {
                        this.alpha = alpha;
                    }

                    public boolean isVisibility() {
                        return visibility;
                    }

                    public void setVisibility(boolean visibility) {
                        this.visibility = visibility;
                    }

                    public int getMaxValueLength() {
                        return maxValueLength;
                    }

                    public void setMaxValueLength(int maxValueLength) {
                        this.maxValueLength = maxValueLength;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getHindiTitle() {
                        return hindiTitle;
                    }

                    public void setHindiTitle(String hindiTitle) {
                        this.hindiTitle = hindiTitle;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public int getDisplayOrder() {
                        return displayOrder;
                    }

                    public void setDisplayOrder(int displayOrder) {
                        this.displayOrder = displayOrder;
                    }

                    public String getInputType() {
                        return inputType;
                    }

                    public void setInputType(String inputType) {
                        this.inputType = inputType;
                    }
                }

                public static class ExpiryDateBean {
                    /**
                     * required : true
                     * visibility : true
                     * alpha : false
                     * format : date
                     * type : string
                     * description :
                     * hindiTitle : तिथि
                     * title : ExpiryDate
                     * displayOrder : 1
                     * inputType : date
                     */

                    private boolean required;
                    private boolean visibility;
                    private boolean alpha;
                    private String format;
                    private String type;
                    private String description;
                    private String hindiTitle;
                    private String title;
                    private int displayOrder;
                    private String inputType;

                    public boolean isRequired() {
                        return required;
                    }

                    public void setRequired(boolean required) {
                        this.required = required;
                    }

                    public boolean isVisibility() {
                        return visibility;
                    }

                    public void setVisibility(boolean visibility) {
                        this.visibility = visibility;
                    }

                    public boolean isAlpha() {
                        return alpha;
                    }

                    public void setAlpha(boolean alpha) {
                        this.alpha = alpha;
                    }

                    public String getFormat() {
                        return format;
                    }

                    public void setFormat(String format) {
                        this.format = format;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getHindiTitle() {
                        return hindiTitle;
                    }

                    public void setHindiTitle(String hindiTitle) {
                        this.hindiTitle = hindiTitle;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public int getDisplayOrder() {
                        return displayOrder;
                    }

                    public void setDisplayOrder(int displayOrder) {
                        this.displayOrder = displayOrder;
                    }

                    public String getInputType() {
                        return inputType;
                    }

                    public void setInputType(String inputType) {
                        this.inputType = inputType;
                    }
                }

                public static class IsActiveBean {
                    /**
                     * required : true
                     * visibility : true
                     * optionValues : ["Yes","No"]
                     * enum : ["Yes","No"]
                     * type : string
                     * description :
                     * hindiTitle : नैदानिक परीक्षण किया गया है या नहीं?
                     * title : Active
                     * displayOrder : 8
                     * inputType : radio
                     */

                    private boolean required;
                    private boolean visibility;
                    private String type;
                    private String description;
                    private String hindiTitle;
                    private String title;
                    private int displayOrder;
                    private String inputType;
                    private List<String> optionValues;
                    @SerializedName("enum")
                    private List<String> enumX;

                    public boolean isRequired() {
                        return required;
                    }

                    public void setRequired(boolean required) {
                        this.required = required;
                    }

                    public boolean isVisibility() {
                        return visibility;
                    }

                    public void setVisibility(boolean visibility) {
                        this.visibility = visibility;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getHindiTitle() {
                        return hindiTitle;
                    }

                    public void setHindiTitle(String hindiTitle) {
                        this.hindiTitle = hindiTitle;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public int getDisplayOrder() {
                        return displayOrder;
                    }

                    public void setDisplayOrder(int displayOrder) {
                        this.displayOrder = displayOrder;
                    }

                    public String getInputType() {
                        return inputType;
                    }

                    public void setInputType(String inputType) {
                        this.inputType = inputType;
                    }

                    public List<String> getOptionValues() {
                        return optionValues;
                    }

                    public void setOptionValues(List<String> optionValues) {
                        this.optionValues = optionValues;
                    }

                    public List<String> getEnumX() {
                        return enumX;
                    }

                    public void setEnumX(List<String> enumX) {
                        this.enumX = enumX;
                    }
                }

                public static class StatusBean {
                    /**
                     * validationMessage : {"101":"Age can't be less than {{schema.minimum}}","103":"Age can't be more than {{schema.maximum}}"}
                     * required : true
                     * alpha : false
                     * minimum : 0
                     * maximum : 1000
                     * visibility : true
                     * minValue : 0
                     * maxValue : 120
                     * type : number
                     * description :
                     * hindiTitle : आयु
                     * title : Status
                     * displayOrder : 7
                     * inputType : textbox
                     */

                    private ValidationMessageBean validationMessage;
                    private boolean required;
                    private boolean alpha;
                    private int minimum;
                    private int maximum;
                    private boolean visibility;
                    private int minValue;
                    private int maxValue;
                    private String type;
                    private String description;
                    private String hindiTitle;
                    private String title;
                    private int displayOrder;
                    private String inputType;

                    public ValidationMessageBean getValidationMessage() {
                        return validationMessage;
                    }

                    public void setValidationMessage(ValidationMessageBean validationMessage) {
                        this.validationMessage = validationMessage;
                    }

                    public boolean isRequired() {
                        return required;
                    }

                    public void setRequired(boolean required) {
                        this.required = required;
                    }

                    public boolean isAlpha() {
                        return alpha;
                    }

                    public void setAlpha(boolean alpha) {
                        this.alpha = alpha;
                    }

                    public int getMinimum() {
                        return minimum;
                    }

                    public void setMinimum(int minimum) {
                        this.minimum = minimum;
                    }

                    public int getMaximum() {
                        return maximum;
                    }

                    public void setMaximum(int maximum) {
                        this.maximum = maximum;
                    }

                    public boolean isVisibility() {
                        return visibility;
                    }

                    public void setVisibility(boolean visibility) {
                        this.visibility = visibility;
                    }

                    public int getMinValue() {
                        return minValue;
                    }

                    public void setMinValue(int minValue) {
                        this.minValue = minValue;
                    }

                    public int getMaxValue() {
                        return maxValue;
                    }

                    public void setMaxValue(int maxValue) {
                        this.maxValue = maxValue;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getHindiTitle() {
                        return hindiTitle;
                    }

                    public void setHindiTitle(String hindiTitle) {
                        this.hindiTitle = hindiTitle;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public int getDisplayOrder() {
                        return displayOrder;
                    }

                    public void setDisplayOrder(int displayOrder) {
                        this.displayOrder = displayOrder;
                    }

                    public String getInputType() {
                        return inputType;
                    }

                    public void setInputType(String inputType) {
                        this.inputType = inputType;
                    }

                    public static class ValidationMessageBean {
                        /**
                         * 101 : Age can't be less than {{schema.minimum}}
                         * 103 : Age can't be more than {{schema.maximum}}
                         */

                        @SerializedName("101")
                        private String _$101;
                        @SerializedName("103")
                        private String _$103;

                        public String get_$101() {
                            return _$101;
                        }

                        public void set_$101(String _$101) {
                            this._$101 = _$101;
                        }

                        public String get_$103() {
                            return _$103;
                        }

                        public void set_$103(String _$103) {
                            this._$103 = _$103;
                        }
                    }
                }

                public static class TitleBean {
                    /**
                     * validationMessage : {"201":"Name is too log. max {{schema.maxLength}} characters","202":"Only Alphabets are allowed","302":"Name is Required!!"}
                     * required : true
                     * visibility : true
                     * maxValueLength : 100
                     * maxLength : 100
                     * type : string
                     * description :
                     * hindiTitle : व्यक्ति का नाम मिले
                     * title : Title
                     * pattern : ^[A-Za-z\s]+$
                     * displayOrder : 5
                     * inputType : textbox
                     */

                    private ValidationMessageBeanX validationMessage;
                    private boolean required;
                    private boolean visibility;
                    private int maxValueLength;
                    private int maxLength;
                    private String type;
                    private String description;
                    private String hindiTitle;
                    private String title;
                    private String pattern;
                    private int displayOrder;
                    private String inputType;

                    public ValidationMessageBeanX getValidationMessage() {
                        return validationMessage;
                    }

                    public void setValidationMessage(ValidationMessageBeanX validationMessage) {
                        this.validationMessage = validationMessage;
                    }

                    public boolean isRequired() {
                        return required;
                    }

                    public void setRequired(boolean required) {
                        this.required = required;
                    }

                    public boolean isVisibility() {
                        return visibility;
                    }

                    public void setVisibility(boolean visibility) {
                        this.visibility = visibility;
                    }

                    public int getMaxValueLength() {
                        return maxValueLength;
                    }

                    public void setMaxValueLength(int maxValueLength) {
                        this.maxValueLength = maxValueLength;
                    }

                    public int getMaxLength() {
                        return maxLength;
                    }

                    public void setMaxLength(int maxLength) {
                        this.maxLength = maxLength;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getHindiTitle() {
                        return hindiTitle;
                    }

                    public void setHindiTitle(String hindiTitle) {
                        this.hindiTitle = hindiTitle;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getPattern() {
                        return pattern;
                    }

                    public void setPattern(String pattern) {
                        this.pattern = pattern;
                    }

                    public int getDisplayOrder() {
                        return displayOrder;
                    }

                    public void setDisplayOrder(int displayOrder) {
                        this.displayOrder = displayOrder;
                    }

                    public String getInputType() {
                        return inputType;
                    }

                    public void setInputType(String inputType) {
                        this.inputType = inputType;
                    }

                    public static class ValidationMessageBeanX {
                        /**
                         * 201 : Name is too log. max {{schema.maxLength}} characters
                         * 202 : Only Alphabets are allowed
                         * 302 : Name is Required!!
                         */

                        @SerializedName("201")
                        private String _$201;
                        @SerializedName("202")
                        private String _$202;
                        @SerializedName("302")
                        private String _$302;

                        public String get_$201() {
                            return _$201;
                        }

                        public void set_$201(String _$201) {
                            this._$201 = _$201;
                        }

                        public String get_$202() {
                            return _$202;
                        }

                        public void set_$202(String _$202) {
                            this._$202 = _$202;
                        }

                        public String get_$302() {
                            return _$302;
                        }

                        public void set_$302(String _$302) {
                            this._$302 = _$302;
                        }
                    }
                }
            }
        }
    }
}
