package com.imcooking.Model.api.response;

public class AddDelRequest {

    /**
     * foodie_id : 19
     * address_id : 12
     */

    private String foodie_id;
    private String address_id;

    public String getFoodie_id() {
        return foodie_id;
    }

    public void setFoodie_id(String foodie_id) {
        this.foodie_id = foodie_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
