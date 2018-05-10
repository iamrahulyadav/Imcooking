package com.imcooking.Model.api.response;

import java.util.List;

public class ChefProfileData {

    /**
     * status : true
     * chef_data : {"chef_id":72,"chef_name":"Patrick","chef_phone":96325874112,"chef_email":"Patrick@gmail.com","chef_image":"1525759957234.jpg","chef_business":"Patrick food resturent","rating":0,"follow":0,"address":"United Kingdom West Lothian, ","cuisine_id":29,"cuisine_name":"American Food","bestcuisine_id":29,"bestcuisine_name":"American Food","about":"But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be"}
     * chef_dish : [{"dish_id":10,"dish_name":"Cobba salad","dish_cuisine_id":29,"dish_cuisine":"American Food","dish_subcuisine_id":23,"dish_subcuisine":"Cobb salad","dish_price":80,"dish_video":"1525170327How to make a Cobb Salad.mp4","dish_description":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_special_note":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_from":"8:00 AM","dish_to":"12:00 PM","dish_quantity":"12","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"Yes","dish_deliverymiles":10,"dish_image":["1520916866chaatpapri.jpg","1520916894alumatar.jpg","1525170327cccoba.jpg","1525170327cobbaa.jpg","1525170327cobba.jpg"]},{"dish_id":16,"dish_name":"smash tometo wth bhati","dish_cuisine_id":29,"dish_cuisine":"American Food","dish_subcuisine_id":23,"dish_subcuisine":"Cobb salad","dish_price":120,"dish_video":null,"dish_description":"sdad","dish_special_note":"asdsa","dish_from":"11:30 AM","dish_to":"1:30 PM","dish_quantity":"10","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"No","dish_deliverymiles":17,"dish_image":["1525511259chaatpapri.jpg"]}]
     */

    private boolean status;
    private ChefDataBean chef_data;
    private List<ChefDishBean> chef_dish;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ChefDataBean getChef_data() {
        return chef_data;
    }

    public void setChef_data(ChefDataBean chef_data) {
        this.chef_data = chef_data;
    }

    public List<ChefDishBean> getChef_dish() {
        return chef_dish;
    }

    public void setChef_dish(List<ChefDishBean> chef_dish) {
        this.chef_dish = chef_dish;
    }

    public static class ChefDataBean {
        /**
         * chef_id : 72
         * chef_name : Patrick
         * chef_phone : 96325874112
         * chef_email : Patrick@gmail.com
         * chef_image : 1525759957234.jpg
         * chef_business : Patrick food resturent
         * rating : 0
         * follow : 0
         * address : United Kingdom West Lothian,
         * cuisine_id : 29
         * cuisine_name : American Food
         * bestcuisine_id : 29
         * bestcuisine_name : American Food
         * about : But the real star is the food. Tables laden with delicacies line the walls. Everything you can think of, and things you have never dreamed of, lie in wait. Whole roasted cows and pigs and goats still turning on spits. Huge platters of fowl stuffed with savoury fruit and nuts. Ocean creatures drizzled in sauces or begging to be
         */

        private int chef_id;
        private String chef_name;
        private long chef_phone;
        private String chef_email;
        private String chef_image;
        private String chef_business;
        private int rating;
        private int follow;
        private String address;
        private int cuisine_id;
        private String cuisine_name;
        private int bestcuisine_id;
        private String bestcuisine_name;
        private String about;

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

        public long getChef_phone() {
            return chef_phone;
        }

        public void setChef_phone(long chef_phone) {
            this.chef_phone = chef_phone;
        }

        public String getChef_email() {
            return chef_email;
        }

        public void setChef_email(String chef_email) {
            this.chef_email = chef_email;
        }

        public String getChef_image() {
            return chef_image;
        }

        public void setChef_image(String chef_image) {
            this.chef_image = chef_image;
        }

        public String getChef_business() {
            return chef_business;
        }

        public void setChef_business(String chef_business) {
            this.chef_business = chef_business;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCuisine_id() {
            return cuisine_id;
        }

        public void setCuisine_id(int cuisine_id) {
            this.cuisine_id = cuisine_id;
        }

        public String getCuisine_name() {
            return cuisine_name;
        }

        public void setCuisine_name(String cuisine_name) {
            this.cuisine_name = cuisine_name;
        }

        public int getBestcuisine_id() {
            return bestcuisine_id;
        }

        public void setBestcuisine_id(int bestcuisine_id) {
            this.bestcuisine_id = bestcuisine_id;
        }

        public String getBestcuisine_name() {
            return bestcuisine_name;
        }

        public void setBestcuisine_name(String bestcuisine_name) {
            this.bestcuisine_name = bestcuisine_name;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }

    public static class ChefDishBean {
        /**
         * dish_id : 10
         * dish_name : Cobba salad
         * dish_cuisine_id : 29
         * dish_cuisine : American Food
         * dish_subcuisine_id : 23
         * dish_subcuisine : Cobb salad
         * dish_price : 80
         * dish_video : 1525170327How to make a Cobb Salad.mp4
         * dish_description : Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine
         * dish_special_note : Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine
         * dish_from : 8:00 AM
         * dish_to : 12:00 PM
         * dish_quantity : 12
         * dish_available : Yes
         * dish_homedelivery : Yes
         * dish_pickup : Yes
         * dish_deliverymiles : 10
         * dish_image : ["1520916866chaatpapri.jpg","1520916894alumatar.jpg","1525170327cccoba.jpg","1525170327cobbaa.jpg","1525170327cobba.jpg"]
         */

        private int dish_id;
        private String dish_name;
        private int dish_cuisine_id;
        private String dish_cuisine;
        private int dish_subcuisine_id;
        private String dish_subcuisine;
        private int dish_price;
        private String dish_video;
        private String dish_description;
        private String dish_special_note;
        private String dish_from;
        private String dish_to;
        private String dish_quantity;
        private String dish_available;
        private String dish_homedelivery;
        private String dish_pickup;
        private int dish_deliverymiles;
        private List<String> dish_image;

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
}
