package com.travmahrajvar.bringmefood.utils;

/**
 * Created by mi6 on 12/12/17.
 */

public class Users {
    String userID;
    String userName;
    String userToken;

    public Users(String userID, String userName, String userToken){
        this.userID = userID;
        this.userName = userName;
        this.userToken = userToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
