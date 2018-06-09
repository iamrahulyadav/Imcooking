package com.imcooking.Model.api.response;

public class CartAddedItemList {
    String dish_id;
    String dish_price;
    String dish_qyt;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String dish_price) {
        this.dish_price = dish_price;
    }

    public String getDish_qyt() {
        return dish_qyt;
    }

    public void setDish_qyt(String dish_qyt) {
        this.dish_qyt = dish_qyt;
    }
}
