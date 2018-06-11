package com.imcooking.Model.api.response;

import java.util.List;

public class AddCart {


    /**
     * chef_id : 5
     * chef_name : brijesh chef
     * chef_image : 1520577541jobseeker.png
     * rating : 5
     * follow : 3
     * add_dish : [{"addcart_id":9,"dish_id":5,"dish_image":"1520916667kheer.jpg","dish_name":"matter paner"},{"addcart_id":10,"dish_id":6,"dish_image":"1524488071kheer.jpg","dish_name":"india food"}]
     */

    private int chef_id;
    private String chef_name;
    private String chef_image;
    private float rating;
    private int follow;
    private List<AddDishBean> add_dish;

    public List<AddDishBean> getItemprice() {
        return itemprice;
    }

    public void setItemprice(List<AddDishBean> itemprice) {
        this.itemprice = itemprice;
    }

    private List<AddDishBean> itemprice;

    public int getChef_id() {
        return chef_id;
    }

    public void setChef_id(int chef_id) {
        this.chef_id = chef_id;
    }

    public String getChef_name() {
        return chef_name;
    }

    public void setChef_name(String chef_name) {
        this.chef_name = chef_name;
    }

    public String getChef_image() {
        return chef_image;
    }

    public void setChef_image(String chef_image) {
        this.chef_image = chef_image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public List<AddDishBean> getAdd_dish() {
        return add_dish;
    }

    public void setAdd_dish(List<AddDishBean> add_dish) {
        this.add_dish = add_dish;
    }

    public static class AddDishBean {
        /**
         * addcart_id : 9
         * dish_id : 5
         * dish_image : 1520916667kheer.jpg
         * dish_name : matter paner
         */

        private int addcart_id;
        private int dish_id;
        private String dish_image;
        private String dish_quantity;
        private String dish_available;
        private String dish_name;
        private String dish_price;

        public String getDish_quantity() {
            return dish_quantity;
        }

        public void setDish_quantity(String dish_quantity) {
            this.dish_quantity = dish_quantity;
        }

        public String getDish_available() {
            return dish_available;
        }

        public void setDish_available(String dish_available) {
            this.dish_available = dish_available;
        }

        public String getDish_price() {
            return dish_price;
        }

        public void setDish_price(String dish_price) {
            this.dish_price = dish_price;
        }


        public int getAddcart_id() {
            return addcart_id;
        }

        public void setAddcart_id(int addcart_id) {
            this.addcart_id = addcart_id;
        }

        public int getDish_id() {
            return dish_id;
        }

        public void setDish_id(int dish_id) {
            this.dish_id = dish_id;
        }

        public String getDish_image() {
            return dish_image;
        }

        public void setDish_image(String dish_image) {
            this.dish_image = dish_image;
        }

        public String getDish_name() {
            return dish_name;
        }

        public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
        }
    }
}


