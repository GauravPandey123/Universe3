package com.universe.android.resource.Login.CrystalReport;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class CrystalReportResponse extends BaseResponse<CrystalReportResponse> {
    /**
     * errorMsg :
     * statusCode : 200
     * response : {"submitted":{"count":2,"list":[{"_id":"5a9be11f8a7d5513989e275a","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-02-09T13:30:57.529Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"territory":"Amtala","pincode":9999}},{"_id":"5a9c1ffb8a7d5513989e2765","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-02-12T04:49:19.490Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"territory":"Amtala","pincode":9999}}]},"Inprogress":{"count":0,"list":[]},"newRetailer":{"count":0,"list":[]},"crystalCustomer":{"count":0,"list":[]}}
     */

    private String errorMsg;
    private int statusCode;
    private ResponseBean response;

    @Override
    public boolean isValid(String condition, boolean isStrict) {
        return true;
    }

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

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }


    public static class ResponseBean {
        /**
         * submitted : {"count":2,"list":[{"_id":"5a9be11f8a7d5513989e275a","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-02-09T13:30:57.529Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"territory":"Amtala","pincode":9999}},{"_id":"5a9c1ffb8a7d5513989e2765","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-02-12T04:49:19.490Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"territory":"Amtala","pincode":9999}}]}
         * Inprogress : {"count":0,"list":[]}
         * newRetailer : {"count":0,"list":[]}
         * crystalCustomer : {"count":0,"list":[]}
         */

        private SubmittedBean submitted;
        private InprogressBean Inprogress;
        private NewRetailerBean newRetailer;
        private CrystalCustomerBean crystalCustomer;

        public SubmittedBean getSubmitted() {
            return submitted;
        }

        public void setSubmitted(SubmittedBean submitted) {
            this.submitted = submitted;
        }

        public InprogressBean getInprogress() {
            return Inprogress;
        }

        public void setInprogress(InprogressBean Inprogress) {
            this.Inprogress = Inprogress;
        }

        public NewRetailerBean getNewRetailer() {
            return newRetailer;
        }

        public void setNewRetailer(NewRetailerBean newRetailer) {
            this.newRetailer = newRetailer;
        }

        public CrystalCustomerBean getCrystalCustomer() {
            return crystalCustomer;
        }

        public void setCrystalCustomer(CrystalCustomerBean crystalCustomer) {
            this.crystalCustomer = crystalCustomer;
        }

        public static class SubmittedBean {
            /**
             * count : 2
             * list : [{"_id":"5a9be11f8a7d5513989e275a","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-02-09T13:30:57.529Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"territory":"Amtala","pincode":9999}},{"_id":"5a9c1ffb8a7d5513989e2765","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-02-12T04:49:19.490Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"territory":"Amtala","pincode":9999}}]
             */

            private int count;
            private List<ListBean> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * _id : 5a9be11f8a7d5513989e275a
                 * customerId : {"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-02-09T13:30:57.529Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"territory":"Amtala","pincode":9999}
                 */

                private String _id;
                private CustomerIdBean customerId;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public CustomerIdBean getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(CustomerIdBean customerId) {
                    this.customerId = customerId;
                }

                public static class CustomerIdBean {
                    /**
                     * _id : 5a7da291f0249f1038c0b3f3
                     * updatedAt : 2018-02-09T13:30:57.529Z
                     * createdAt : 2018-02-09T13:30:57.529Z
                     * isActive : 0
                     * status : 0
                     * clientId : asdasd
                     * surveyId : asdas
                     * image :
                     * createdBy : asdas
                     * address : udhyog vihar phase 4 gurgaon
                     * contactNo : 89999998765
                     * description : asd
                     * contactPerson : asdsa
                     * name : Chemical India
                     * __v : 0
                     * territory : Amtala
                     * pincode : 9999
                     */

                    private String _id;
                    private String updatedAt;
                    private String createdAt;
                    private int isActive;
                    private String status;
                    private String clientId;
                    private String surveyId;
                    private String image;
                    private String createdBy;
                    private String address;
                    private long contactNo;
                    private String description;
                    private String contactPerson;
                    private String name;
                    private int __v;
                    private String territory;
                    private int pincode;

                    public String get_id() {
                        return _id;
                    }

                    public void set_id(String _id) {
                        this._id = _id;
                    }

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

                    public int getIsActive() {
                        return isActive;
                    }

                    public void setIsActive(int isActive) {
                        this.isActive = isActive;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    public String getClientId() {
                        return clientId;
                    }

                    public void setClientId(String clientId) {
                        this.clientId = clientId;
                    }

                    public String getSurveyId() {
                        return surveyId;
                    }

                    public void setSurveyId(String surveyId) {
                        this.surveyId = surveyId;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public String getCreatedBy() {
                        return createdBy;
                    }

                    public void setCreatedBy(String createdBy) {
                        this.createdBy = createdBy;
                    }

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }

                    public long getContactNo() {
                        return contactNo;
                    }

                    public void setContactNo(long contactNo) {
                        this.contactNo = contactNo;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getContactPerson() {
                        return contactPerson;
                    }

                    public void setContactPerson(String contactPerson) {
                        this.contactPerson = contactPerson;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int get__v() {
                        return __v;
                    }

                    public void set__v(int __v) {
                        this.__v = __v;
                    }

                    public String getTerritory() {
                        return territory;
                    }

                    public void setTerritory(String territory) {
                        this.territory = territory;
                    }

                    public int getPincode() {
                        return pincode;
                    }

                    public void setPincode(int pincode) {
                        this.pincode = pincode;
                    }
                }
            }
        }

        public static class InprogressBean {
            /**
             * count : 0
             * list : []
             */

            private int count;
            private List<?> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }

        public static class NewRetailerBean {
            /**
             * count : 0
             * list : []
             */

            private int count;
            private List<?> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }

        public static class CrystalCustomerBean {
            /**
             * count : 0
             * list : []
             */

            private int count;
            private List<?> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }
    }
}
