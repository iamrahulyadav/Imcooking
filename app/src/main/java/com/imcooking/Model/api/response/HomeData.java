package com.imcooking.Model.api.response;

import java.util.List;

public class HomeData {

    /**
     * status : true
     * chef_dish : [{"chef_id":8,"chef_name":"shalu","like":0,"follow":0,"rating":0,"dish_id":7,"dish_name":"sdfsdf","dish_cuisine_id":32,"dish_cuisine":"German Food","dish_subcuisine_id":18,"dish_subcuisine":"Bratkartoffeln","dish_price":500,"dish_video":null,"dish_description":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_special_note":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_from":"2:30 AM","dish_to":"6:00 AM","dish_quantity":"20","dish_available":"Yes","dish_homedelivery":"No","dish_pickup":"Yes","dish_deliverymiles":10,"dish_image":["1524488146alugobi.jpg","1524488146chilichecken.jpg","1524488146chapati.jpg","1524488146barfi.jpg","1524488146gulabjamun.jpg"]}]
     * favourite_data : [{"image":"1521271424776694.jpg"},{"image":"1521271267776693.jpg"},{"image":"1521271424776694.jpg"},{"image":"1521271267776693.jpg"},{"image":"1521271267776693.jpg"}]
     */

    private boolean status;
    private List<ChefDishBean> chef_dish;
    private List<FavouriteDataBean> favourite_data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ChefDishBean> getChef_dish() {
        return chef_dish;
    }

    public void setChef_dish(List<ChefDishBean> chef_dish) {
        this.chef_dish = chef_dish;
    }

    public List<FavouriteDataBean> getFavourite_data() {
        return favourite_data;
    }

    public void setFavourite_data(List<FavouriteDataBean> favourite_data) {
        this.favourite_data = favourite_data;
    }

    public static class ChefDishBean {
        /**
         * chef_id : 8
         * chef_name : shalu
         * like : 0
         * dishlike : 0
         * follow : 0
         * rating : 0
         * ratingno : 0
         * dish_id : 7
         * dish_name : sdfsdf
         * dish_cuisine_id : 32
         * dish_cuisine : German Food
         * dish_subcuisine_id : 18
         * dish_subcuisine : Bratkartoffeln
         * dish_price : 500
         * dish_video : null
         * dish_description : Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine
         * dish_special_note : Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine
         * dish_from : 2:30 AM
         * dish_to : 6:00 AM
         * dish_quantity : 20
         * dish_available : Yes
         * dish_homedelivery : No
         * dish_pickup : Yes
         * dish_deliverymiles : 10
         * distance : 0
         * dish_image : ["1524488146alugobi.jpg","1524488146chilichecken.jpg","1524488146chapati.jpg","1524488146barfi.jpg","1524488146gulabjamun.jpg"]
         */

        private int chef_id;
        private String chef_name;
        private int like;
        private int dishlike;
        private int follow;
        private int rating;
        private int ratingno;
        private int dish_id;
        private String dish_name;
        private int dish_cuisine_id;
        private String dish_cuisine;
        private int dish_subcuisine_id;
        private String dish_subcuisine;
        private int dish_price;
        private Object dish_video;
        private String dish_description;
        private String dish_special_note;
        private String dish_from;
        private String dish_to;
        private String dish_quantity;
        private String dish_available;
        private String dish_homedelivery;
        private String dish_pickup;
        private int dish_deliverymiles;
        private float distance;
        private String address;
        private List<String> dish_image;

        public int getDishlike() {
            return dishlike;
        }

        public void setDishlike(int dishlike) {
            this.dishlike = dishlike;
        }

        public int getRatingno() {
            return ratingno;
        }

        public void setRatingno(int ratingno) {
            this.ratingno = ratingno;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

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

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getDish_id() {
            return dish_id;
        }

        public void setDish_id(int dish_id) {
            this.dish_id = dish_id;
        }

        public String getDish_name() {
            return dish_name;
        }

        public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
        }

        public int getDish_cuisine_id() {
            return dish_cuisine_id;
        }

        public void setDish_cuisine_id(int dish_cuisine_id) {
            this.dish_cuisine_id = dish_cuisine_id;
        }

        public String getDish_cuisine() {
            return dish_cuisine;
        }

        public void setDish_cuisine(String dish_cuisine) {
            this.dish_cuisine = dish_cuisine;
        }

        public int getDish_subcuisine_id() {
            return dish_subcuisine_id;
        }

        public void setDish_subcuisine_id(int dish_subcuisine_id) {
            this.dish_subcuisine_id = dish_subcuisine_id;
        }

        public String getDish_subcuisine() {
            return dish_subcuisine;
        }

        public void setDish_subcuisine(String dish_subcuisine) {
            this.dish_subcuisine = dish_subcuisine;
        }

        public int getDish_price() {
            return dish_price;
        }

        public void setDish_price(int dish_price) {
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

        public int getDish_deliverymiles() {
            return dish_deliverymiles;
        }

        public void setDish_deliverymiles(int dish_deliverymiles) {
            this.dish_deliverymiles = dish_deliverymiles;
        }

        public List<String> getDish_image() {
            return dish_image;
        }

        public void setDish_image(List<String> dish_image) {
            this.dish_image = dish_image;
        }
    }

    public static class FavouriteDataBean {
        /**
         * image : 1521271424776694.jpg
         */

        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
