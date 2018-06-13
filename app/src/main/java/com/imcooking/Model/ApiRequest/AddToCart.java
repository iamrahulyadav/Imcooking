package com.imcooking.Model.ApiRequest;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AddToCart {
    private String addcart_id;
    private int chef_id;
    private String dish_id;
    private int foodie_id;
    private String addcart_yes;

    public String getAddcart_yes() {
        return addcart_yes;
    }

    public void setAddcart_yes(String addcart_yes) {
        this.addcart_yes = addcart_yes;
    }

    public String getAddcart_id() {
        return addcart_id;
    }

    public void setAddcart_id(String addcart_id) {
        this.addcart_id = addcart_id;
    }

    public int getChef_id() {
        return chef_id;
    }

    public void setChef_id(int chef_id) {
        this.chef_id = chef_id;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public int getFoodie_id() {
        return foodie_id;
    }

    public void setFoodie_id(int foodie_id) {
        this.foodie_id = foodie_id;
    }



}