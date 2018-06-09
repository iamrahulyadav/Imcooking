package com.imcooking.Model.api.response;

public class CartAddedItemList {
    int dish_id;
    double dish_price;
    int dish_qyt;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public double getDish_price() {
        return dish_price;
    }

    public void setDish_price(double dish_price) {
        this.dish_price = dish_price;
    }

    public int getDish_qyt() {
        return dish_qyt;
    }

    public void setDish_qyt(int dish_qyt) {
        this.dish_qyt = dish_qyt;
    }
}
