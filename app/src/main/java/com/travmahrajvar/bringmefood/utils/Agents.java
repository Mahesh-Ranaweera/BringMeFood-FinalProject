package com.travmahrajvar.bringmefood.utils;

/**
 * Created by mi6 on 12/11/17.
 */

public class Agents {
    String location;
    String userID;
    String resturant;

    public Agents(String userID, String location, String resturant){
        this.userID = userID;
        this.location = location;
        this.resturant = resturant;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getResturant() {
        return resturant;
    }

    public void setResturant(String resturant) {
        this.resturant = resturant;
    }
}
