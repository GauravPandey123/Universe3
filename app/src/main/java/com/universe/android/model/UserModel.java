package com.universe.android.model;

/**
 * Created by ankush.bansal on 11-03-2018.
 */

public class UserModel {

  private   String id;
   private String userName;
   private String userStatus;
   private String userDateStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserDateStatus() {
        return userDateStatus;
    }

    public void setUserDateStatus(String userDateStatus) {
        this.userDateStatus = userDateStatus;
    }
}


