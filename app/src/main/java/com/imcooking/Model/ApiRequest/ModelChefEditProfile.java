package com.imcooking.Model.ApiRequest;

public class ModelChefEditProfile {
    /**
     * chef_id : 72
     * fullname : imcooking
     * address : noida
     * city : 213
     * email : Patrick123@gmail.com
     * zipcode : EC4Y 7BB
     * default_miles : 45
     * cuisine_list : 29
     * available : 1
     * about : "abc"
     */

    private String chef_id;
    private String fullname;
    private String address;
    private String city;
    private String email;
    private String zipcode;
    private String default_miles;
    private String cuisine_list;
    private String available;
    private String about;
    private String phone;
    private String lat;
    private String lang;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChef_id() {
        return chef_id;
    }

    public void setChef_id(String chef_id) {
        this.chef_id = chef_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDefault_miles() {
        return default_miles;
    }

    public void setDefault_miles(String default_miles) {
        this.default_miles = default_miles;
    }

    public String getCuisine_list() {
        return cuisine_list;
    }

    public void setCuisine_list(String cuisine_list) {
        this.cuisine_list = cuisine_list;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
