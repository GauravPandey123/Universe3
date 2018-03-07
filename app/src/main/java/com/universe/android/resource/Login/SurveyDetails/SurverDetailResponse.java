package com.universe.android.resource.Login.SurveyDetails;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 05-03-2018.
 */

public class SurverDetailResponse extends BaseResponse<SurverDetailResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"target":20,"submitted":0,"Inprogress":0,"newRetailer":0,"crystalCustomer":0,"crystaDoctor":[{"detail":{"_id":"5a97cdf0d0a8c60474c13ba8","name":"Harjeet Singh"},"totalAssign":0,"submittedCount":0,"progress":0,"retailorCount":0,"crystalCustomer":0},{"detail":{"_id":"5a97cdf0d0a8c60474c13ba8","name":"Harjeet Singh"},"totalAssign":0,"submittedCount":0,"progress":0,"retailorCount":0,"crystalCustomer":0}]}
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
         * target : 20
         * submitted : 0
         * Inprogress : 0
         * newRetailer : 0
         * crystalCustomer : 0
         * crystaDoctor : [{"detail":{"_id":"5a97cdf0d0a8c60474c13ba8","name":"Harjeet Singh"},"totalAssign":0,"submittedCount":0,"progress":0,"retailorCount":0,"crystalCustomer":0},{"detail":{"_id":"5a97cdf0d0a8c60474c13ba8","name":"Harjeet Singh"},"totalAssign":0,"submittedCount":0,"progress":0,"retailorCount":0,"crystalCustomer":0}]
         */

        private int target;
        private int submitted;
        private int Inprogress;
        private int newRetailer;
        private int crystalCustomer;
        private List<CrystaDoctorBean> crystaDoctor;

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getSubmitted() {
            return submitted;
        }

        public void setSubmitted(int submitted) {
            this.submitted = submitted;
        }

        public int getInprogress() {
            return Inprogress;
        }

        public void setInprogress(int Inprogress) {
            this.Inprogress = Inprogress;
        }

        public int getNewRetailer() {
            return newRetailer;
        }

        public void setNewRetailer(int newRetailer) {
            this.newRetailer = newRetailer;
        }

        public int getCrystalCustomer() {
            return crystalCustomer;
        }

        public void setCrystalCustomer(int crystalCustomer) {
            this.crystalCustomer = crystalCustomer;
        }

        public List<CrystaDoctorBean> getCrystaDoctor() {
            return crystaDoctor;
        }

        public void setCrystaDoctor(List<CrystaDoctorBean> crystaDoctor) {
            this.crystaDoctor = crystaDoctor;
        }

        public static class CrystaDoctorBean {
            /**
             * detail : {"_id":"5a97cdf0d0a8c60474c13ba8","name":"Harjeet Singh"}
             * totalAssign : 0
             * submittedCount : 0
             * progress : 0
             * retailorCount : 0
             * crystalCustomer : 0
             */

            private DetailBean detail;
            private int totalAssign;
            private int submittedCount;
            private int progress;
            private int retailorCount;
            private int crystalCustomer;

            public DetailBean getDetail() {
                return detail;
            }

            public void setDetail(DetailBean detail) {
                this.detail = detail;
            }

            public int getTotalAssign() {
                return totalAssign;
            }

            public void setTotalAssign(int totalAssign) {
                this.totalAssign = totalAssign;
            }

            public int getSubmittedCount() {
                return submittedCount;
            }

            public void setSubmittedCount(int submittedCount) {
                this.submittedCount = submittedCount;
            }

            public int getProgress() {
                return progress;
            }

            public void setProgress(int progress) {
                this.progress = progress;
            }

            public int getRetailorCount() {
                return retailorCount;
            }

            public void setRetailorCount(int retailorCount) {
                this.retailorCount = retailorCount;
            }

            public int getCrystalCustomer() {
                return crystalCustomer;
            }

            public void setCrystalCustomer(int crystalCustomer) {
                this.crystalCustomer = crystalCustomer;
            }

            public static class DetailBean {
                /**
                 * _id : 5a97cdf0d0a8c60474c13ba8
                 * name : Harjeet Singh
                 */

                private String _id;
                private String name;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
