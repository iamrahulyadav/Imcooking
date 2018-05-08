package com.imcooking.Model.api.response;

import java.util.List;

public class HomeData {
    /**
     * status : true
     * chef_dish : [{"chef_id":73,"chef_name":"Tomos","like":2,"dishlike":1,"follow":2,"rating":4,"ratingno":2,"dish_id":9,"dish_name":"Pisto","dish_cuisine_id":31,"dish_cuisine":"Spanish Cuisine","dish_subcuisine_id":19,"dish_subcuisine":"Tortilla Espanola","dish_price":75,"dish_video":"1525170031José Andrés Foods- Fried Egg with Pisto.mp4","dish_description":"But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be ..","dish_special_note":"But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be .","dish_from":"2:30 PM","dish_to":"6:30 PM","dish_quantity":"10","dish_available":"Yes","dish_homedelivery":"No","dish_pickup":"Yes","dish_deliverymiles":40,"distance":0,"address":"United Kingdom London, ","dish_image":["1525170031polbo.jpg","1525170031pisto.jpg","1525170031patatas.jpg"]},{"chef_id":72,"chef_name":"Patrick","like":0,"dishlike":0,"follow":0,"rating":0,"ratingno":0,"dish_id":10,"dish_name":"Cobba salad","dish_cuisine_id":29,"dish_cuisine":"American Food","dish_subcuisine_id":23,"dish_subcuisine":"Cobb salad","dish_price":80,"dish_video":"1525170327How to make a Cobb Salad.mp4","dish_description":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_special_note":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_from":"8:00 AM","dish_to":"12:00 PM","dish_quantity":"12","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"Yes","dish_deliverymiles":10,"distance":1.3,"address":"United Kingdom West Lothian, ","dish_image":["1520916866chaatpapri.jpg","1520916894alumatar.jpg","1525170327cccoba.jpg","1525170327cobbaa.jpg","1525170327cobba.jpg"]},{"chef_id":72,"chef_name":"Patrick","like":0,"dishlike":0,"follow":0,"rating":0,"ratingno":0,"dish_id":16,"dish_name":"smash tometo wth bhati","dish_cuisine_id":29,"dish_cuisine":"American Food","dish_subcuisine_id":23,"dish_subcuisine":"Cobb salad","dish_price":120,"dish_video":null,"dish_description":"sdad","dish_special_note":"asdsa","dish_from":"11:30 AM","dish_to":"1:30 PM","dish_quantity":"10","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"No","dish_deliverymiles":17,"distance":1.3,"address":"United Kingdom West Lothian, ","dish_image":["1525511259chaatpapri.jpg"]},{"chef_id":71,"chef_name":"William","like":0,"dishlike":0,"follow":0,"rating":5,"ratingno":2,"dish_id":11,"dish_name":"Rahata","dish_cuisine_id":30,"dish_cuisine":"French Food","dish_subcuisine_id":22,"dish_subcuisine":"Ratatouille","dish_price":120,"dish_video":"1525170684Simple Ratatouille - French Guy Cooking.mp4","dish_description":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_special_note":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_from":"10:30 AM","dish_to":"11:00 PM","dish_quantity":"12","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"Yes","dish_deliverymiles":30,"distance":3.1,"address":"United Kingdom Cambridge, Cambridge","dish_image":["1521010293beefvindaloo.jpg","1525170684chaatpapri.jpg","1525170684chanadal.jpg","1525170684alumatar.jpg","1525170684chapati.jpg"]}]
     * favourite_data : [{"dish_id":9,"chef_name":"Tomos","like":2,"follow":2,"rating":4,"ratingno":2,"dishlike":1,"dish_name":"Pisto","dish_video":"1525170031José Andrés Foods- Fried Egg with Pisto.mp4","dish_price":75,"dish_description":"But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be ..","dish_special_note":"But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be .","dish_from":"2:30 PM","dish_to":"6:30 PM","dish_available":"Yes","dish_homedeliver":"No","dish_pickup":"Yes","dish_image":["1525170031polbo.jpg","1525170031pisto.jpg","1525170031patatas.jpg"]}]
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
         * chef_id : 73
         * chef_name : Tomos
         * like : 2
         * dishlike : 1
         * follow : 2
         * rating : 4
         * ratingno : 2
         * dish_id : 9
         * dish_name : Pisto
         * dish_cuisine_id : 31
         * dish_cuisine : Spanish Cuisine
         * dish_subcuisine_id : 19
         * dish_subcuisine : Tortilla Espanola
         * dish_price : 75
         * dish_video : 1525170031José Andrés Foods- Fried Egg with Pisto.mp4
         * dish_description : But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be ..
         * dish_special_note : But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be .
         * dish_from : 2:30 PM
         * dish_to : 6:30 PM
         * dish_quantity : 10
         * dish_available : Yes
         * dish_homedelivery : No
         * dish_pickup : Yes
         * dish_deliverymiles : 40
         * distance : 0
         * address : United Kingdom London,
         * dish_image : ["1525170031polbo.jpg","1525170031pisto.jpg","1525170031patatas.jpg"]
         */

        private String chef_id;
        private String chef_name;
        private String like;
        private String dishlike;
        private String follow;
        private String rating;
        private String ratingno;
        private String dish_id;
        private String dish_name;
        private String dish_cuisine_id;
        private String dish_cuisine;
        private String dish_subcuisine_id;
        private String dish_subcuisine;
        private String dish_price;
        private String dish_video;
        private String dish_description;
        private String dish_special_note;
        private String dish_from;
        private String dish_to;
        private String dish_quantity;
        private String dish_available;
        private String dish_homedelivery;
        private String dish_pickup;
        private String dish_deliverymiles;
        private String distance;
        private String address;
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

        public String getDishlike() {
            return dishlike;
        }

        public void setDishlike(String dishlike) {
            this.dishlike = dishlike;
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

        public String getRatingno() {
            return ratingno;
        }

        public void setRatingno(String ratingno) {
            this.ratingno = ratingno;
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

        public String getDish_video() {
            return dish_video;
        }

        public void setDish_video(String dish_video) {
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

        public String getDish_deliverymiles() {
            return dish_deliverymiles;
        }

        public void setDish_deliverymiles(String dish_deliverymiles) {
            this.dish_deliverymiles = dish_deliverymiles;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
         * dish_id : 9
         * chef_name : Tomos
         * like : 2
         * follow : 2
         * rating : 4
         * ratingno : 2
         * dishlike : 1
         * dish_name : Pisto
         * dish_video : 1525170031José Andrés Foods- Fried Egg with Pisto.mp4
         * dish_price : 75
         * dish_description : But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be ..
         * dish_special_note : But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be .
         * dish_from : 2:30 PM
         * dish_to : 6:30 PM
         * dish_available : Yes
         * dish_homedeliver : No
         * dish_pickup : Yes
         * dish_image : ["1525170031polbo.jpg","1525170031pisto.jpg","1525170031patatas.jpg"]
         */

        private String dish_id;
        private String chef_name;
        private String like;
        private String follow;
        private String rating;
        private String ratingno;
        private String dishlike;
        private String dish_name;
        private String dish_video;
        private String dish_price;
        private String dish_description;
        private String dish_special_note;
        private String dish_from;
        private String dish_to;
        private String dish_available;
        private String dish_homedeliver;
        private String dish_pickup;
        private List<String> dish_image;

        public String getDish_id() {
            return dish_id;
        }

        public void setDish_id(String dish_id) {
            this.dish_id = dish_id;
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

        public String getRatingno() {
            return ratingno;
        }

        public void setRatingno(String ratingno) {
            this.ratingno = ratingno;
        }

        public String getDishlike() {
            return dishlike;
        }

        public void setDishlike(String dishlike) {
            this.dishlike = dishlike;
        }

        public String getDish_name() {
            return dish_name;
        }

        public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
        }

        public String getDish_video() {
            return dish_video;
        }

        public void setDish_video(String dish_video) {
            this.dish_video = dish_video;
        }

        public String getDish_price() {
            return dish_price;
        }

        public void setDish_price(String dish_price) {
            this.dish_price = dish_price;
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

        public String getDish_available() {
            return dish_available;
        }

        public void setDish_available(String dish_available) {
            this.dish_available = dish_available;
        }

        public String getDish_homedeliver() {
            return dish_homedeliver;
        }

        public void setDish_homedeliver(String dish_homedeliver) {
            this.dish_homedeliver = dish_homedeliver;
        }

        public String getDish_pickup() {
            return dish_pickup;
        }

        public void setDish_pickup(String dish_pickup) {
            this.dish_pickup = dish_pickup;
        }

        public List<String> getDish_image() {
            return dish_image;
        }

        public void setDish_image(List<String> dish_image) {
            this.dish_image = dish_image;
        }
    }
}
