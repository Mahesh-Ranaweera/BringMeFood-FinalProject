package com.travmahrajvar.bringmefood.utils;

/**
 * Created by mi6 on 12/11/17.
 */

public class Agents {
    String location;
    String userID;
    String resturant;
    String agentTocken;
    String agentName;

    public Agents(String userID, String location, String resturant, String agentTocken, String agentName){
        this.userID = userID;
        this.location = location;
        this.resturant = resturant;
        this.agentTocken = agentTocken;
        this.agentName = agentName;
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

    public String getAgentTocken() {return agentTocken;}

    public void setAgentTocken(String agentTocken) {this.agentTocken = agentTocken;}

    public String getAgentName() {return agentName;}

    public void setAgentName(String agentName) {this.agentName = agentName;}
}
