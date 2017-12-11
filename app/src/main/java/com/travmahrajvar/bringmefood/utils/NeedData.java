package com.travmahrajvar.bringmefood.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mi6 on 12/10/17.
 */

public class NeedData implements Serializable {
    private ArrayList<String> userFoodList;
    private String deliveryLoc;

    public NeedData(ArrayList userFoodList, String deliveryLoc){
        this.userFoodList = userFoodList;
        this.deliveryLoc = deliveryLoc;
    }

    public ArrayList<String> getUserFoodList() {
        return userFoodList;
    }

    public void setUserFoodList(ArrayList<String> userFoodList) {
        this.userFoodList = userFoodList;
    }

    public String getDeliveryLoc() {
        return deliveryLoc;
    }

    public void setDeliveryLoc(String deliveryLoc) {
        this.deliveryLoc = deliveryLoc;
    }
}
