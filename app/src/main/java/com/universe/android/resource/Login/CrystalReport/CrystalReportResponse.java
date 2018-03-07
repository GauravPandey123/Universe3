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
     * response : {"submitted":{"count":1,"list":[{"_id":"5a9e94dea9becf0d60f4d3ae","date":"2018-03-06T11:00:00.268Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]},"Inprogress":{"count":1,"list":[{"_id":"5a9eab4d4269c90c30522104","date":"2018-03-06T13:00:00.768Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]},"newRetailer":{"count":1,"list":[{"_id":"5a9e84033b90d30ff02a33bc","date":"2018-03-06T10:00:00.711Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-06T12:07:43.983Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]},"crystalCustomer":{"count":1,"list":[{"_id":"5a9f7ca3a057720ffc90f0a9","date":"2018-03-07T04:00:00.384Z","customerId":{"_id":"5a83ca4296318c134c534cb9","updatedAt":"2018-02-14T05:34:21.018Z","createdAt":"2018-02-14T05:33:54.394Z","responses":"{\"contactPerson\":\"tes999\",\"name\":\"test\",\"status\":66,\"description\":\"jnjbb\",\"address\":\"ujuij\",\"surveyId\":\"5a83c9de96318c134c534cb7\",\"clientId\":\"5a83ca0a96318c134c534cb8\"}","isActive":0,"status":"0","clientId":"5a83ca0a96318c134c534cb8","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"Test","description":"Test","contactPerson":"tes999","name":"Agro Input Corporation","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]}}
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
         * submitted : {"count":1,"list":[{"_id":"5a9e94dea9becf0d60f4d3ae","date":"2018-03-06T11:00:00.268Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]}
         * Inprogress : {"count":1,"list":[{"_id":"5a9eab4d4269c90c30522104","date":"2018-03-06T13:00:00.768Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]}
         * newRetailer : {"count":1,"list":[{"_id":"5a9e84033b90d30ff02a33bc","date":"2018-03-06T10:00:00.711Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-06T12:07:43.983Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]}
         * crystalCustomer : {"count":1,"list":[{"_id":"5a9f7ca3a057720ffc90f0a9","date":"2018-03-07T04:00:00.384Z","customerId":{"_id":"5a83ca4296318c134c534cb9","updatedAt":"2018-02-14T05:34:21.018Z","createdAt":"2018-02-14T05:33:54.394Z","responses":"{\"contactPerson\":\"tes999\",\"name\":\"test\",\"status\":66,\"description\":\"jnjbb\",\"address\":\"ujuij\",\"surveyId\":\"5a83c9de96318c134c534cb7\",\"clientId\":\"5a83ca0a96318c134c534cb8\"}","isActive":0,"status":"0","clientId":"5a83ca0a96318c134c534cb8","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"Test","description":"Test","contactPerson":"tes999","name":"Agro Input Corporation","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]}
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
             * count : 1
             * list : [{"_id":"5a9e94dea9becf0d60f4d3ae","date":"2018-03-06T11:00:00.268Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]
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
                 * _id : 5a9e94dea9becf0d60f4d3ae
                 * date : 2018-03-06T11:00:00.268Z
                 * customerId : {"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}
                 */

                private String _id;
                private String date;
                private CustomerIdBean customerId;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public CustomerIdBean getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(CustomerIdBean customerId) {
                    this.customerId = customerId;
                }

                public static class CustomerIdBean {
                    /**
                     * _id : 5a811ccfa6f7eb1200adcbd9
                     * updatedAt : 2018-03-06T13:34:21.302Z
                     * createdAt : 2018-02-12T04:49:19.490Z
                     * isActive : 0
                     * status : 0
                     * clientId : asdasd
                     * surveyId : asdas
                     * image : https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png
                     * createdBy : asdas
                     * address : asdsa
                     * contactNo : 89999998765
                     * description : asd
                     * contactPerson : asdsa
                     * name : Ganga Pesticides
                     * __v : 0
                     * state : WB
                     * territory : Amtala
                     * pincode : 9999
                     * customer : CrystalCustomer
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
                    private String state;
                    private String territory;
                    private String pincode;
                    private String customer;

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

                    public String getState() {
                        return state;
                    }

                    public void setState(String state) {
                        this.state = state;
                    }

                    public String getTerritory() {
                        return territory;
                    }

                    public void setTerritory(String territory) {
                        this.territory = territory;
                    }

                    public String getPincode() {
                        return pincode;
                    }

                    public void setPincode(String pincode) {
                        this.pincode = pincode;
                    }

                    public String getCustomer() {
                        return customer;
                    }

                    public void setCustomer(String customer) {
                        this.customer = customer;
                    }
                }
            }
        }

        public static class InprogressBean {
            /**
             * count : 1
             * list : [{"_id":"5a9eab4d4269c90c30522104","date":"2018-03-06T13:00:00.768Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]
             */

            private int count;
            private List<ListBeanX> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
                /**
                 * _id : 5a9eab4d4269c90c30522104
                 * date : 2018-03-06T13:00:00.768Z
                 * customerId : {"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-06T13:34:21.302Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}
                 */

                private String _id;
                private String date;
                private CustomerIdBeanX customerId;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public CustomerIdBeanX getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(CustomerIdBeanX customerId) {
                    this.customerId = customerId;
                }

                public static class CustomerIdBeanX {
                    /**
                     * _id : 5a811ccfa6f7eb1200adcbd9
                     * updatedAt : 2018-03-06T13:34:21.302Z
                     * createdAt : 2018-02-12T04:49:19.490Z
                     * isActive : 0
                     * status : 0
                     * clientId : asdasd
                     * surveyId : asdas
                     * image : https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520343261034.png
                     * createdBy : asdas
                     * address : asdsa
                     * contactNo : 89999998765
                     * description : asd
                     * contactPerson : asdsa
                     * name : Ganga Pesticides
                     * __v : 0
                     * state : WB
                     * territory : Amtala
                     * pincode : 9999
                     * customer : CrystalCustomer
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
                    private String state;
                    private String territory;
                    private String pincode;
                    private String customer;

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

                    public String getState() {
                        return state;
                    }

                    public void setState(String state) {
                        this.state = state;
                    }

                    public String getTerritory() {
                        return territory;
                    }

                    public void setTerritory(String territory) {
                        this.territory = territory;
                    }

                    public String getPincode() {
                        return pincode;
                    }

                    public void setPincode(String pincode) {
                        this.pincode = pincode;
                    }

                    public String getCustomer() {
                        return customer;
                    }

                    public void setCustomer(String customer) {
                        this.customer = customer;
                    }
                }
            }
        }

        public static class NewRetailerBean {
            /**
             * count : 1
             * list : [{"_id":"5a9e84033b90d30ff02a33bc","date":"2018-03-06T10:00:00.711Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-06T12:07:43.983Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]
             */

            private int count;
            private List<ListBeanXX> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<ListBeanXX> getList() {
                return list;
            }

            public void setList(List<ListBeanXX> list) {
                this.list = list;
            }

            public static class ListBeanXX {
                /**
                 * _id : 5a9e84033b90d30ff02a33bc
                 * date : 2018-03-06T10:00:00.711Z
                 * customerId : {"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-06T12:07:43.983Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}
                 */

                private String _id;
                private String date;
                private CustomerIdBeanXX customerId;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public CustomerIdBeanXX getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(CustomerIdBeanXX customerId) {
                    this.customerId = customerId;
                }

                public static class CustomerIdBeanXX {
                    /**
                     * _id : 5a7da291f0249f1038c0b3f3
                     * updatedAt : 2018-03-06T12:07:43.983Z
                     * createdAt : 2018-02-09T13:30:57.529Z
                     * isActive : 0
                     * status : 0
                     * clientId : asdasd
                     * surveyId : asdas
                     * image : https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png
                     * createdBy : asdas
                     * address : udhyog vihar phase 4 gurgaon
                     * contactNo : 89999998765
                     * description : asd
                     * contactPerson : asdsa
                     * name : Chemical India
                     * __v : 0
                     * state : WB
                     * territory : Amtala
                     * pincode : 9999
                     * customer : CrystalCustomer
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
                    private String state;
                    private String territory;
                    private String pincode;
                    private String customer;

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

                    public String getState() {
                        return state;
                    }

                    public void setState(String state) {
                        this.state = state;
                    }

                    public String getTerritory() {
                        return territory;
                    }

                    public void setTerritory(String territory) {
                        this.territory = territory;
                    }

                    public String getPincode() {
                        return pincode;
                    }

                    public void setPincode(String pincode) {
                        this.pincode = pincode;
                    }

                    public String getCustomer() {
                        return customer;
                    }

                    public void setCustomer(String customer) {
                        this.customer = customer;
                    }
                }
            }
        }

        public static class CrystalCustomerBean {
            /**
             * count : 1
             * list : [{"_id":"5a9f7ca3a057720ffc90f0a9","date":"2018-03-07T04:00:00.384Z","customerId":{"_id":"5a83ca4296318c134c534cb9","updatedAt":"2018-02-14T05:34:21.018Z","createdAt":"2018-02-14T05:33:54.394Z","responses":"{\"contactPerson\":\"tes999\",\"name\":\"test\",\"status\":66,\"description\":\"jnjbb\",\"address\":\"ujuij\",\"surveyId\":\"5a83c9de96318c134c534cb7\",\"clientId\":\"5a83ca0a96318c134c534cb8\"}","isActive":0,"status":"0","clientId":"5a83ca0a96318c134c534cb8","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"Test","description":"Test","contactPerson":"tes999","name":"Agro Input Corporation","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}}]
             */

            private int count;
            private List<ListBeanXXX> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<ListBeanXXX> getList() {
                return list;
            }

            public void setList(List<ListBeanXXX> list) {
                this.list = list;
            }

            public static class ListBeanXXX {
                /**
                 * _id : 5a9f7ca3a057720ffc90f0a9
                 * date : 2018-03-07T04:00:00.384Z
                 * customerId : {"_id":"5a83ca4296318c134c534cb9","updatedAt":"2018-02-14T05:34:21.018Z","createdAt":"2018-02-14T05:33:54.394Z","responses":"{\"contactPerson\":\"tes999\",\"name\":\"test\",\"status\":66,\"description\":\"jnjbb\",\"address\":\"ujuij\",\"surveyId\":\"5a83c9de96318c134c534cb7\",\"clientId\":\"5a83ca0a96318c134c534cb8\"}","isActive":0,"status":"0","clientId":"5a83ca0a96318c134c534cb8","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"Test","description":"Test","contactPerson":"tes999","name":"Agro Input Corporation","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer"}
                 */

                private String _id;
                private String date;
                private CustomerIdBeanXXX customerId;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public CustomerIdBeanXXX getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(CustomerIdBeanXXX customerId) {
                    this.customerId = customerId;
                }

                public static class CustomerIdBeanXXX {
                    /**
                     * _id : 5a83ca4296318c134c534cb9
                     * updatedAt : 2018-02-14T05:34:21.018Z
                     * createdAt : 2018-02-14T05:33:54.394Z
                     * responses : {"contactPerson":"tes999","name":"test","status":66,"description":"jnjbb","address":"ujuij","surveyId":"5a83c9de96318c134c534cb7","clientId":"5a83ca0a96318c134c534cb8"}
                     * isActive : 0
                     * status : 0
                     * clientId : 5a83ca0a96318c134c534cb8
                     * surveyId : 5a86d4a9b69a800980dadd82
                     * image :
                     * createdBy : 5a799e932779e608b435a279
                     * address : Test
                     * description : Test
                     * contactPerson : tes999
                     * name : Agro Input Corporation
                     * __v : 0
                     * contactNo : 89999998765
                     * state : WB
                     * territory : Amtala
                     * pincode : 9999
                     * customer : CrystalCustomer
                     */

                    private String _id;
                    private String updatedAt;
                    private String createdAt;
                    private String responses;
                    private int isActive;
                    private String status;
                    private String clientId;
                    private String surveyId;
                    private String image;
                    private String createdBy;
                    private String address;
                    private String description;
                    private String contactPerson;
                    private String name;
                    private int __v;
                    private long contactNo;
                    private String state;
                    private String territory;
                    private String pincode;
                    private String customer;

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

                    public String getResponses() {
                        return responses;
                    }

                    public void setResponses(String responses) {
                        this.responses = responses;
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

                    public long getContactNo() {
                        return contactNo;
                    }

                    public void setContactNo(long contactNo) {
                        this.contactNo = contactNo;
                    }

                    public String getState() {
                        return state;
                    }

                    public void setState(String state) {
                        this.state = state;
                    }

                    public String getTerritory() {
                        return territory;
                    }

                    public void setTerritory(String territory) {
                        this.territory = territory;
                    }

                    public String getPincode() {
                        return pincode;
                    }

                    public void setPincode(String pincode) {
                        this.pincode = pincode;
                    }

                    public String getCustomer() {
                        return customer;
                    }

                    public void setCustomer(String customer) {
                        this.customer = customer;
                    }
                }
            }
        }
    }
}
