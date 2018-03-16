package com.universe.android.realmbean;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmAnswers extends RealmObject {
    @PrimaryKey
    private String _id;
    private String answers;
    private String submitbyCD;
    private String submitbyRM;
    private int _v;
    private String responses;
    private String submitbyZM;
    private String cd_Status;
    private String rm_Status;
    private String zm_Status;
    private boolean pendingforCD;
    private boolean inprocessforCD;
    private boolean rejectedforCD;
    private boolean completedforCD;
    private boolean pendingforRM;
    private boolean inprocessforRM;
    private boolean rejectedforRM;
    private boolean completedforRM;
    private boolean pendingforZM;
    private boolean inprocessforZM;
    private boolean rejectedforZM;
    private boolean completedforZM;
    private boolean isSync=true;
    private boolean isUpdate=true;
    private Date date;
    private Date createdAt;
    private Date updatedAt;
    private String surveyId;
    private String customerId;
    private String workflow;
    private String customer;
    private String requester;
    private String approval1;
    private String approval2;
    private String approval3;
    private String approval4;
    private String approval5;
    private String approval6;
    private String requester_status;
    private String approval1_status;
    private String approval2_status;
    private String approval3_status;
    private String approval4_status;
    private String approval5_status;
    private String approval6_status;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getSubmitbyCD() {
        return submitbyCD;
    }

    public void setSubmitbyCD(String submitbyCD) {
        this.submitbyCD = submitbyCD;
    }

    public String getSubmitbyRM() {
        return submitbyRM;
    }

    public void setSubmitbyRM(String submitbyRM) {
        this.submitbyRM = submitbyRM;
    }

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public String getSubmitbyZM() {
        return submitbyZM;
    }

    public void setSubmitbyZM(String submitbyZM) {
        this.submitbyZM = submitbyZM;
    }

    public boolean isPendingforCD() {
        return pendingforCD;
    }

    public void setPendingforCD(boolean pendingforCD) {
        this.pendingforCD = pendingforCD;
    }

    public boolean isInprocessforCD() {
        return inprocessforCD;
    }

    public void setInprocessforCD(boolean inprocessforCD) {
        this.inprocessforCD = inprocessforCD;
    }

    public boolean isRejectedforCD() {
        return rejectedforCD;
    }

    public void setRejectedforCD(boolean rejectedforCD) {
        this.rejectedforCD = rejectedforCD;
    }

    public boolean isCompletedforCD() {
        return completedforCD;
    }

    public void setCompletedforCD(boolean completedforCD) {
        this.completedforCD = completedforCD;
    }

    public boolean isPendingforRM() {
        return pendingforRM;
    }

    public void setPendingforRM(boolean pendingforRM) {
        this.pendingforRM = pendingforRM;
    }

    public boolean isInprocessforRM() {
        return inprocessforRM;
    }

    public void setInprocessforRM(boolean inprocessforRM) {
        this.inprocessforRM = inprocessforRM;
    }

    public boolean isRejectedforRM() {
        return rejectedforRM;
    }

    public void setRejectedforRM(boolean rejectedforRM) {
        this.rejectedforRM = rejectedforRM;
    }

    public boolean isCompletedforRM() {
        return completedforRM;
    }

    public void setCompletedforRM(boolean completedforRM) {
        this.completedforRM = completedforRM;
    }

    public boolean isPendingforZM() {
        return pendingforZM;
    }

    public void setPendingforZM(boolean pendingforZM) {
        this.pendingforZM = pendingforZM;
    }

    public boolean isInprocessforZM() {
        return inprocessforZM;
    }

    public void setInprocessforZM(boolean inprocessforZM) {
        this.inprocessforZM = inprocessforZM;
    }

    public boolean isRejectedforZM() {
        return rejectedforZM;
    }

    public void setRejectedforZM(boolean rejectedforZM) {
        this.rejectedforZM = rejectedforZM;
    }

    public boolean isCompletedforZM() {
        return completedforZM;
    }

    public void setCompletedforZM(boolean completedforZM) {
        this.completedforZM = completedforZM;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public String getCd_Status() {
        return cd_Status;
    }

    public void setCd_Status(String cd_Status) {
        this.cd_Status = cd_Status;
    }

    public String getRm_STatus() {
        return rm_Status;
    }

    public void setRm_STatus(String rm_STatus) {
        this.rm_Status = rm_STatus;
    }

    public String getZm_Status() {
        return zm_Status;
    }

    public void setZm_Status(String zm_Status) {
        this.zm_Status = zm_Status;
    }

    public String getRm_Status() {
        return rm_Status;
    }

    public void setRm_Status(String rm_Status) {
        this.rm_Status = rm_Status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getApproval1() {
        return approval1;
    }

    public void setApproval1(String approval1) {
        this.approval1 = approval1;
    }

    public String getApproval2() {
        return approval2;
    }

    public void setApproval2(String approval2) {
        this.approval2 = approval2;
    }

    public String getApproval3() {
        return approval3;
    }

    public void setApproval3(String approval3) {
        this.approval3 = approval3;
    }

    public String getApproval4() {
        return approval4;
    }

    public void setApproval4(String approval4) {
        this.approval4 = approval4;
    }

    public String getApproval5() {
        return approval5;
    }

    public void setApproval5(String approval5) {
        this.approval5 = approval5;
    }

    public String getApproval6() {
        return approval6;
    }

    public void setApproval6(String approval6) {
        this.approval6 = approval6;
    }

    public String getRequester_status() {
        return requester_status;
    }

    public void setRequester_status(String requester_status) {
        this.requester_status = requester_status;
    }

    public String getApproval1_status() {
        return approval1_status;
    }

    public void setApproval1_status(String approval1_status) {
        this.approval1_status = approval1_status;
    }

    public String getApproval2_status() {
        return approval2_status;
    }

    public void setApproval2_status(String approval2_status) {
        this.approval2_status = approval2_status;
    }

    public String getApproval3_status() {
        return approval3_status;
    }

    public void setApproval3_status(String approval3_status) {
        this.approval3_status = approval3_status;
    }

    public String getApproval4_status() {
        return approval4_status;
    }

    public void setApproval4_status(String approval4_status) {
        this.approval4_status = approval4_status;
    }

    public String getApproval5_status() {
        return approval5_status;
    }

    public void setApproval5_status(String approval5_status) {
        this.approval5_status = approval5_status;
    }

    public String getApproval6_status() {
        return approval6_status;
    }

    public void setApproval6_status(String approval6_status) {
        this.approval6_status = approval6_status;
    }
}
