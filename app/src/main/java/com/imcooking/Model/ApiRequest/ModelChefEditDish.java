package com.imcooking.Model.ApiRequest;

import java.util.List;

public class ModelChefEditDish {


    /**
     * dish_id : 104
     * user_id : 72
     * cuisine : 33
     * subcuisine : 15
     * dish_name : matter paner
     * description : food description
     * special_note : food special note description
     * dish_price : 560
     * dish_from : 2:00 AM
     * dish_to : 10:00 PM
     * available : Yes
     * homedeliver : No
     * pickup : Yes
     * deliverymiles : 21
     * dish_video : honey
     * dish_image : ["aa","aa"]
     */

    private String dish_id;
    private String user_id;
    private String cuisine;
    private String subcuisine;
    private String dish_name;
    private String description;
    private String special_note;
    private String dish_price;
    private String dish_from;
    private String dish_to;
    private String available;
    private String homedeliver;
    private String pickup;
    private String deliverymiles;
    private String dish_video;
    private String dish_qyt;

    public String getDish_qyt() {
        return dish_qyt;
    }

    public void setDish_qyt(String dish_qyt) {
        this.dish_qyt = dish_qyt;
    }

    private List<String> dish_image;

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getSubcuisine() {
        return subcuisine;
    }

    public void setSubcuisine(String subcuisine) {
        this.subcuisine = subcuisine;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecial_note() {
        return special_note;
    }

    public void setSpecial_note(String special_note) {
        this.special_note = special_note;
    }

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String dish_price) {
        this.dish_price = dish_price;
    }

    public String getDish_from() {
        return dish_from;
    }

    public void setDish_from(String dish_from) {
        this.dish_from = dish_from;
    }

    public String getDish_to() {
        return dish_to;
    }

    public void setDish_to(String dish_to) {
        this.dish_to = dish_to;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getHomedeliver() {
        return homedeliver;
    }

    public void setHomedeliver(String homedeliver) {
        this.homedeliver = homedeliver;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDeliverymiles() {
        return deliverymiles;
    }

    public void setDeliverymiles(String deliverymiles) {
        this.deliverymiles = deliverymiles;
    }

    public String getDish_video() {
        return dish_video;
    }

    public void setDish_video(String dish_video) {
        this.dish_video = dish_video;
    }

    public List<String> getDish_image() {
        return dish_image;
    }

    public void setDish_image(List<String> dish_image) {
        this.dish_image = dish_image;
    }
}
