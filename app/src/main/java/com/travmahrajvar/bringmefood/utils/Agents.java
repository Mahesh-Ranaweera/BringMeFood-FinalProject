package com.travmahrajvar.bringmefood.utils;

/**
 * Created by mi6 on 12/11/17.
 */

public class Agents {
    String location;
    String userID;
    String foodRunID;
    String resturant;
    String agentTocken;
    String agentName;

    public Agents(String userID, String location, String resturant, String agentTocken, String agentName, String foodRunID){
        this.userID = userID;
        this.location = location;
        this.resturant = resturant;
        this.agentTocken = agentTocken;
        this.agentName = agentName;
        this.foodRunID = foodRunID;
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

    public String getResturant() {return resturant;}

    public void setResturant(String resturant) {
        this.resturant = resturant;
    }

    public String getAgentTocken() {return agentTocken;}

    public void setAgentTocken(String agentTocken) {this.agentTocken = agentTocken;}

    public String getAgentName() {return agentName;}

    public void setAgentName(String agentName) {this.agentName = agentName;}

    public String getFoodRunID() {
        return foodRunID;
    }

    public void setFoodRunID(String foodRunID) {
        this.foodRunID = foodRunID;
    }
}
