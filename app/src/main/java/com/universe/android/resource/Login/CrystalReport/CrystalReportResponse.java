package com.universe.android.resource.Login.CrystalReport;

import com.universe.android.model.ListBean;
import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class CrystalReportResponse extends BaseResponse<CrystalReportResponse> {
    /**
     * errorMsg :
     * statusCode : 200
     * response : {"submitted":{"count":2,"list":[{"_id":"5aa24c5a0001850d1c9d5c3c","date":"2018-03-09T07:00:00.328Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-09T08:37:01.280Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5aa247ad0001850d1c9d5c3a"}},{"_id":"5aa28ec64bd54008e44bd81e","date":"2018-03-09T12:00:00.991Z","customerId":{"_id":"5a82dbb7f08a61114407e578","updatedAt":"2018-03-07T13:32:10.134Z","createdAt":"2018-02-13T12:36:07.611Z","responses":"{\"contactPerson\":\"ayush\",\"name\":\"tititititit\",\"status\":22,\"isActive\":1,\"description\":\"description hai ye\",\"address\":\"c 277chsn\",\"surveyId\":\"5a82d9b0f08a61114407e576\"}","isActive":0,"status":"0","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"c 277chsn","description":"description hai ye","contactPerson":"ayush","name":"Blossom Agricore","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9fe9d93d45380bfccac1c9"}}]},"Inprogress":{"count":1,"list":[{"_id":"5aa51c584bd54008e44bd82e","date":"2018-03-11T10:00:00.344Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-07T13:38:34.785Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9feb5ae54f6d101c98e2e9"}}]},"newRetailer":{"count":0,"list":[]},"crystalCustomer":{"count":0,"list":[]}}
     */

    private String errorMsg;
    private int statusCode;
    private ResponseBean response;

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"submitted":{"count":2,"list":[{"_id":"5aa24c5a0001850d1c9d5c3c","date":"2018-03-09T07:00:00.328Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-09T08:37:01.280Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5aa247ad0001850d1c9d5c3a"}},{"_id":"5aa28ec64bd54008e44bd81e","date":"2018-03-09T12:00:00.991Z","customerId":{"_id":"5a82dbb7f08a61114407e578","updatedAt":"2018-03-07T13:32:10.134Z","createdAt":"2018-02-13T12:36:07.611Z","responses":"{\"contactPerson\":\"ayush\",\"name\":\"tititititit\",\"status\":22,\"isActive\":1,\"description\":\"description hai ye\",\"address\":\"c 277chsn\",\"surveyId\":\"5a82d9b0f08a61114407e576\"}","isActive":0,"status":"0","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"c 277chsn","description":"description hai ye","contactPerson":"ayush","name":"Blossom Agricore","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9fe9d93d45380bfccac1c9"}}]},"Inprogress":{"count":0,"list":[]},"newRetailer":{"count":0,"list":[]},"crystalCustomer":{"count":0,"list":[]}}
     */

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
         * submitted : {"count":2,"list":[{"_id":"5aa24c5a0001850d1c9d5c3c","date":"2018-03-09T07:00:00.328Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-09T08:37:01.280Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5aa247ad0001850d1c9d5c3a"}},{"_id":"5aa28ec64bd54008e44bd81e","date":"2018-03-09T12:00:00.991Z","customerId":{"_id":"5a82dbb7f08a61114407e578","updatedAt":"2018-03-07T13:32:10.134Z","createdAt":"2018-02-13T12:36:07.611Z","responses":"{\"contactPerson\":\"ayush\",\"name\":\"tititititit\",\"status\":22,\"isActive\":1,\"description\":\"description hai ye\",\"address\":\"c 277chsn\",\"surveyId\":\"5a82d9b0f08a61114407e576\"}","isActive":0,"status":"0","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"c 277chsn","description":"description hai ye","contactPerson":"ayush","name":"Blossom Agricore","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9fe9d93d45380bfccac1c9"}}]}
         * Inprogress : {"count":1,"list":[{"_id":"5aa51c584bd54008e44bd82e","date":"2018-03-11T10:00:00.344Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-07T13:38:34.785Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9feb5ae54f6d101c98e2e9"}}]}
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
             * list : [{"_id":"5aa24c5a0001850d1c9d5c3c","date":"2018-03-09T07:00:00.328Z","customerId":{"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-09T08:37:01.280Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5aa247ad0001850d1c9d5c3a"}},{"_id":"5aa28ec64bd54008e44bd81e","date":"2018-03-09T12:00:00.991Z","customerId":{"_id":"5a82dbb7f08a61114407e578","updatedAt":"2018-03-07T13:32:10.134Z","createdAt":"2018-02-13T12:36:07.611Z","responses":"{\"contactPerson\":\"ayush\",\"name\":\"tititititit\",\"status\":22,\"isActive\":1,\"description\":\"description hai ye\",\"address\":\"c 277chsn\",\"surveyId\":\"5a82d9b0f08a61114407e576\"}","isActive":0,"status":"0","surveyId":"5a86d4a9b69a800980dadd82","image":"","createdBy":"5a799e932779e608b435a279","address":"c 277chsn","description":"description hai ye","contactPerson":"ayush","name":"Blossom Agricore","__v":0,"contactNo":89999998765,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9fe9d93d45380bfccac1c9"}}]
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


        }

        public static class InprogressBean {
            /**
             * count : 1
             * list : [{"_id":"5aa51c584bd54008e44bd82e","date":"2018-03-11T10:00:00.344Z","customerId":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-07T13:38:34.785Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5a9feb5ae54f6d101c98e2e9"}}]
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


        }

        public static class NewRetailerBean {
            /**
             * count : 0
             * list : []
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
        }

        public static class CrystalCustomerBean {
            /**
             * count : 0
             * list : []
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
        }
    }
}
