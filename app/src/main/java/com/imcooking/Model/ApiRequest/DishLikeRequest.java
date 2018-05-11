package com.imcooking.Model.ApiRequest;

public class DishLikeRequest {

    /**
     * chef_id : 2
     * foodie_id : 21
     * dish_id : 6
     */

    private String chef_id;
    private String foodie_id;
    private String dish_id;

    public String getChef_id() {
        return chef_id;
    }

    public void setChef_id(String chef_id) {
        this.chef_id = chef_id;
    }

    public String getFoodie_id() {
        return foodie_id;
    }

    public void setFoodie_id(String foodie_id) {
        this.foodie_id = foodie_id;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }
}
