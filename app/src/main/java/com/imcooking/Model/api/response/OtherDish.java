package com.imcooking.Model.api.response;

import java.util.List;

public class OtherDish {

    /**
     * status : true
     * chef : {"chef_name":"Patrick","chef_phone":96325874112,"chef_image":"1525759957234.jpg","rating":0,"ratingno":0,"follow":0,"like":0,"address":"United Kingdom West Lothian, "}
     * chef_dish : [{"dish_like":0,"dish_id":10,"dish_name":"Cobba salad","dish_price":80,"dish_video":"1525170327How to make a Cobb Salad.mp4","dish_description":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_special_note":"Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine","dish_from":"8:00 AM","dish_to":"12:00 PM","dish_quantity":"12","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"Yes","dish_deliverymiles":10,"dish_image":["1520916866chaatpapri.jpg","1520916894alumatar.jpg","1525170327cccoba.jpg","1525170327cobbaa.jpg","1525170327cobba.jpg"]},{"dish_like":0,"dish_id":16,"dish_name":"smash tometo wth bhati","dish_price":120,"dish_video":null,"dish_description":"sdad","dish_special_note":"asdsa","dish_from":"11:30 AM","dish_to":"1:30 PM","dish_quantity":"10","dish_available":"Yes","dish_homedelivery":"Yes","dish_pickup":"No","dish_deliverymiles":17,"dish_image":["1525511259chaatpapri.jpg"]}]
     */

    private boolean status;
    private ChefBean chef;
    private List<ChefDishBean> chef_dish;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ChefBean getChef() {
        return chef;
    }

    public void setChef(ChefBean chef) {
        this.chef = chef;
    }

    public List<ChefDishBean> getChef_dish() {
        return chef_dish;
    }

    public void setChef_dish(List<ChefDishBean> chef_dish) {
        this.chef_dish = chef_dish;
    }

    public static class ChefBean {
        /**
         * chef_name : Patrick
         * chef_phone : 96325874112
         * chef_image : 1525759957234.jpg
         * rating : 0
         * ratingno : 0
         * follow : 0
         * like : 0
         * address : United Kingdom West Lothian,
         */

        private String chef_name;
        private String chef_phone;
        private String chef_image;
        private int rating;
        private int ratingno;
        private int follow;
        private int like;
        private String address;

        public String getChef_name() {
            return chef_name;
        }

        public void setChef_name(String chef_name) {
            this.chef_name = chef_name;
        }

        public String getChef_phone() {
            return chef_phone;
        }

        public void setChef_phone(String chef_phone) {
            this.chef_phone = chef_phone;
        }

        public String getChef_image() {
            return chef_image;
        }

        public void setChef_image(String chef_image) {
            this.chef_image = chef_image;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getRatingno() {
            return ratingno;
        }

        public void setRatingno(int ratingno) {
            this.ratingno = ratingno;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class ChefDishBean {
        /**
         * dish_like : 0
         * dish_id : 10
         * dish_name : Cobba salad
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

        private String dishlike;
        private String likeno;
        private int dish_like;
        private int dish_id;
        private String dish_name;
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
        private int dish_deliverymiles;
        private List<String> dish_image;

        public String getDishlike() {
            return dishlike;
        }

        public void setDishlike(String dishlike) {
            this.dishlike = dishlike;
        }

        public String getLikeno() {
            return likeno;
        }

        public void setLikeno(String likeno) {
            this.likeno = likeno;
        }

        public int getDish_like() {
            return dish_like;
        }

        public void setDish_like(int dish_like) {
            this.dish_like = dish_like;
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
