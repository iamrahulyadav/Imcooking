package com.imcooking.Model.api.response;

import java.util.List;

public class HomeData {
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

        private String chef_id;
        private String chef_name;
        private String address;
        private String added_no_of_cart;
        private String like;
        private String dishlikeno;
        private String dishlike;
        private String follow;
        private String rating;
        private String ratingno;
        private String dish_id;
        private String dish_name;
        private String dish_cuisine_id;
        private String dish_subcuisine_id;
        private String dish_subcuisine;
        private String dish_price;
        private Object dish_video;
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
        private List<DishCuisineBean> dish_cuisine;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAdded_no_of_cart() {
            return added_no_of_cart;
        }

        public void setAdded_no_of_cart(String added_no_of_cart) {
            this.added_no_of_cart = added_no_of_cart;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getDishlikeno() {
            return dishlikeno;
        }

        public void setDishlikeno(String dishlikeno) {
            this.dishlikeno = dishlikeno;
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

        public List<DishCuisineBean> getDish_cuisine() {
            return dish_cuisine;
        }

        public void setDish_cuisine(List<DishCuisineBean> dish_cuisine) {
            this.dish_cuisine = dish_cuisine;
        }

        public List<String> getDish_image() {
            return dish_image;
        }

        public void setDish_image(List<String> dish_image) {
            this.dish_image = dish_image;
        }

        public static class DishCuisineBean {
            /**
             * cuisine_id : 32
             * cuisine_name : German Food
             * cuisine_image : 1521270107german.jpg
             * cuisine_status : 1
             */

            private String cuisine_id;
            private String cuisine_name;
            private String cuisine_image;
            private String cuisine_status;

            public String getCuisine_id() {
                return cuisine_id;
            }

            public void setCuisine_id(String cuisine_id) {
                this.cuisine_id = cuisine_id;
            }

            public String getCuisine_name() {
                return cuisine_name;
            }

            public void setCuisine_name(String cuisine_name) {
                this.cuisine_name = cuisine_name;
            }

            public String getCuisine_image() {
                return cuisine_image;
            }

            public void setCuisine_image(String cuisine_image) {
                this.cuisine_image = cuisine_image;
            }

            public String getCuisine_status() {
                return cuisine_status;
            }

            public void setCuisine_status(String cuisine_status) {
                this.cuisine_status = cuisine_status;
            }
        }
    }

    public static class FavouriteDataBean {
        /**
         * chef_id : 72
         * chef_name : imcooking
         * address : noida
         * added_no_of_cart : 0
         * dish_id : 10
         * like : 0
         * dishlikeno : 1
         * dishlike : 0
         * follow : 1
         * rating : 0
         * ratingno : 0
         * dish_name : Cobba salad
         * dish_video : 1525170327How to make a Cobb Salad.mp4
         * dish_price : 80.00
         * dish_description : Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine
         * dish_special_note : Everyone loves a Cobb salad and this is a great recipe. It makes plain old, shredded iceberg lettuce shine
         * dish_from : 8:00 AM
         * dish_to : 12:00 PM
         * dish_available : Yes
         * dish_homedeliver : Yes
         * dish_pickup : Yes
         * dish_quantity : 12
         * dish_cuisine_name : [{"cuisine_id":29,"cuisine_name":"American Food","cuisine_image":"1521269956american.jpg","cuisine_status":"1"}]
         * dish_subcuisine_id : 23
         * dish_subcuisine : Cobb salad
         * distance :
         * dish_image : ["a1.jpg","a2.jpg","a3.jpg","a4.jpg","1525170327cobba.jpg"]
         */

        private String chef_id;
        private String chef_name;
        private String address;
        private String added_no_of_cart;
        private String dish_id;
        private String like;
        private String dishlikeno;
        private String dishlike;
        private String follow;
        private String rating;
        private String ratingno;
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
        private String dish_quantity;
        private String dish_subcuisine_id;
        private String dish_subcuisine;
        private String distance;
        private List<DishCuisineNameBean> dish_cuisine_name;
        private List<String> dish_image;
        private String dish_deliverymiles;

        public String getDish_deliverymiles() {
            return dish_deliverymiles;
        }

        public void setDish_deliverymiles(String dish_deliverymiles) {
            this.dish_deliverymiles = dish_deliverymiles;
        }

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAdded_no_of_cart() {
            return added_no_of_cart;
        }

        public void setAdded_no_of_cart(String added_no_of_cart) {
            this.added_no_of_cart = added_no_of_cart;
        }

        public String getDish_id() {
            return dish_id;
        }

        public void setDish_id(String dish_id) {
            this.dish_id = dish_id;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getDishlikeno() {
            return dishlikeno;
        }

        public void setDishlikeno(String dishlikeno) {
            this.dishlikeno = dishlikeno;
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

        public String getDish_quantity() {
            return dish_quantity;
        }

        public void setDish_quantity(String dish_quantity) {
            this.dish_quantity = dish_quantity;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<DishCuisineNameBean> getDish_cuisine_name() {
            return dish_cuisine_name;
        }

        public void setDish_cuisine_name(List<DishCuisineNameBean> dish_cuisine_name) {
            this.dish_cuisine_name = dish_cuisine_name;
        }

        public List<String> getDish_image() {
            return dish_image;
        }

        public void setDish_image(List<String> dish_image) {
            this.dish_image = dish_image;
        }

        public static class DishCuisineNameBean {
            /**
             * cuisine_id : 29
             * cuisine_name : American Food
             * cuisine_image : 1521269956american.jpg
             * cuisine_status : 1
             */

            private String cuisine_id;
            private String cuisine_name;
            private String cuisine_image;
            private String cuisine_status;

            public String getCuisine_id() {
                return cuisine_id;
            }

            public void setCuisine_id(String cuisine_id) {
                this.cuisine_id = cuisine_id;
            }

            public String getCuisine_name() {
                return cuisine_name;
            }

            public void setCuisine_name(String cuisine_name) {
                this.cuisine_name = cuisine_name;
            }

            public String getCuisine_image() {
                return cuisine_image;
            }

            public void setCuisine_image(String cuisine_image) {
                this.cuisine_image = cuisine_image;
            }

            public String getCuisine_status() {
                return cuisine_status;
            }

            public void setCuisine_status(String cuisine_status) {
                this.cuisine_status = cuisine_status;
            }
        }
    }
}
