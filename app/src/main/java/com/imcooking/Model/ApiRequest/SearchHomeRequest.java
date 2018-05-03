package com.imcooking.Model.ApiRequest;

public class SearchHomeRequest {

    /**
     * latitude : 28.5748119
     * longitude : 77.0869837
     * min_miles : 1
     * max_miles : 10
     * country : 101
     * foodie_id : 1
     */

    private String latitude;
    private String longitude;
    private String min_miles;
    private String max_miles;
    private String country;
    private String foodie_id;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMin_miles() {
        return min_miles;
    }

    public void setMin_miles(String min_miles) {
        this.min_miles = min_miles;
    }

    public String getMax_miles() {
        return max_miles;
    }

    public void setMax_miles(String max_miles) {
        this.max_miles = max_miles;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFoodie_id() {
        return foodie_id;
    }

    public void setFoodie_id(String foodie_id) {
        this.foodie_id = foodie_id;
    }
}
