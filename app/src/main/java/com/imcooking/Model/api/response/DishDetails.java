package com.imcooking.Model.api.response;

import java.util.List;

public class DishDetails {

    /**
     * dish_details : {"chef_id":11,"chef_name":"sadam","like":0,"follow":0,"rating":5,"dish_id":"5","dish_name":"matter paner","dish_cuisine_id":32,"dish_cuisine":"German Food","dish_subcuisine_id":15,"dish_subcuisine":"Kimchi","dish_price":560,"dish_video":null,"dish_description":"food description","dish_special_note":"food special note description","dish_from":"2:00 AM","dish_to":"10:00 PM","dish_quantity":null,"dish_available":"Yes","dish_homedelivery":"No","dish_pickup":"Yes","dish_deliverymiles":21,"dish_image":["1520672129kheer.jpg"]}
     */

    private DishDetailsBean dish_details;

    public DishDetailsBean getDish_details() {
        return dish_details;
    }

    public void setDish_details(DishDetailsBean dish_details) {
        this.dish_details = dish_details;
    }

    public static class DishDetailsBean {
        /**
         * chef_id : 11
         * chef_name : sadam
         * like : 0
         * follow : 0
         * rating : 5
         * dish_id : 5
         * dish_name : matter paner
         * dish_cuisine_id : 32
         * dish_cuisine : German Food
         * dish_subcuisine_id : 15
         * dish_subcuisine : Kimchi
         * dish_price : 560
         * dish_video : null
         * dish_description : food description
         * dish_special_note : food special note description
         * dish_from : 2:00 AM
         * dish_to : 10:00 PM
         * dish_quantity : null
         * dish_available : Yes
         * dish_homedelivery : No
         * dish_pickup : Yes
         * dish_deliverymiles : 21
         * dish_image : ["1520672129kheer.jpg"]
         */

        private String chef_id;
        private String chef_name;
        private String like;
        private String follow;
        private String rating;
        private String dish_id;
        private String dish_name;
        private String dish_cuisine_id;
        private String dish_cuisine;
        private String dish_subcuisine_id;
        private String dish_subcuisine;
        private String dish_price;
        private Object dish_video;
        private String dish_description;
        private String dish_special_note;
        private String dish_from;
        private String dish_to;
        private Object dish_quantity;
        private String dish_available;
        private String dish_homedelivery;
        private String dish_pickup;
        private String dish_deliverymiles;
        private List<String> dish_image;

        public String getChef_id() {
            return chef_id;
        }

        public void setChef_id(String chef_id) {
            this.chef_id = chef_id;
        }

        public String getChef_name() {
            return chef_name;
        }

        public void setChef_name(String chef_name) {
            this.chef_name = chef_name;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getDish_id() {
            return dish_id;
        }

        public void setDish_id(String dish_id) {
            this.dish_id = dish_id;
        }

        public String getDish_name() {
            return dish_name;
        }

        public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
        }

        public String getDish_cuisine_id() {
            return dish_cuisine_id;
        }

        public void setDish_cuisine_id(String dish_cuisine_id) {
            this.dish_cuisine_id = dish_cuisine_id;
        }

        public String getDish_cuisine() {
            return dish_cuisine;
        }

        public void setDish_cuisine(String dish_cuisine) {
            this.dish_cuisine = dish_cuisine;
        }

        public String getDish_subcuisine_id() {
            return dish_subcuisine_id;
        }

        public void setDish_subcuisine_id(String dish_subcuisine_id) {
            this.dish_subcuisine_id = dish_subcuisine_id;
        }

        public String getDish_subcuisine() {
            return dish_subcuisine;
        }

        public void setDish_subcuisine(String dish_subcuisine) {
            this.dish_subcuisine = dish_subcuisine;
        }

        public String getDish_price() {
            return dish_price;
        }

        public void setDish_price(String dish_price) {
            this.dish_price = dish_price;
        }

        public Object getDish_video() {
            return dish_video;
        }

        public void setDish_video(Object dish_video) {
            this.dish_video = dish_video;
        }

        public String getDish_description() {
            return dish_description;
        }

        public void setDish_description(String dish_description) {
            this.dish_description = dish_description;
        }

        public String getDish_special_note() {
            return dish_special_note;
        }

        public void setDish_special_note(String dish_special_note) {
            this.dish_special_note = dish_special_note;
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

        public Object getDish_quantity() {
            return dish_quantity;
        }

        public void setDish_quantity(Object dish_quantity) {
            this.dish_quantity = dish_quantity;
        }

        public String getDish_available() {
            return dish_available;
        }

        public void setDish_available(String dish_available) {
            this.dish_available = dish_available;
        }

        public String getDish_homedelivery() {
            return dish_homedelivery;
        }

        public void setDish_homedelivery(String dish_homedelivery) {
            this.dish_homedelivery = dish_homedelivery;
        }

        public String getDish_pickup() {
            return dish_pickup;
        }

        public void setDish_pickup(String dish_pickup) {
            this.dish_pickup = dish_pickup;
        }

        public String getDish_deliverymiles() {
            return dish_deliverymiles;
        }

        public void setDish_deliverymiles(String dish_deliverymiles) {
            this.dish_deliverymiles = dish_deliverymiles;
        }

        public List<String> getDish_image() {
            return dish_image;
        }

        public void setDish_image(List<String> dish_image) {
            this.dish_image = dish_image;
        }
    }
}
