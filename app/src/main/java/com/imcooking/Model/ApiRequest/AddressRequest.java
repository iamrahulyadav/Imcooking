package com.imcooking.Model.ApiRequest;

public class AddressRequest {

    /**
     * foodie_id : 10
     * title : Officenew
     * address : Noida sector 63
     * address_id :
     */

    private String foodie_id;
    private String title;
    private String address;
    private String address_id;

    public String getFoodie_id() {
        return foodie_id;
    }

    public void setFoodie_id(String foodie_id) {
        this.foodie_id = foodie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
