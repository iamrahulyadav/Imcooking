package com.imcooking.Model.api.response;

import java.util.List;

public class FoodieMyorderList {

    /**
     * status : true
     * foodie_order_list : [{"chef_id":5,"chef_name":"brijesh chef","chef_image":"1520577541jobseeker.png","ordertime":null,"bookdate":"2018-05-09","price":65,"order_status":"New order","order_order_id":"213211","order_id":6,"follow":3,"rating":4.5},{"chef_id":5,"chef_name":"brijesh chef","chef_image":"1520577541jobseeker.png","ordertime":null,"bookdate":"2018-05-09","price":65,"order_status":"New order","order_order_id":"213211","order_id":5,"follow":3,"rating":4.5}]
     */

    private boolean status;
    private List<FoodieOrderListBean> foodie_order_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FoodieOrderListBean> getFoodie_order_list() {
        return foodie_order_list;
    }

    public void setFoodie_order_list(List<FoodieOrderListBean> foodie_order_list) {
        this.foodie_order_list = foodie_order_list;
    }

    public static class FoodieOrderListBean {
        /**
         * chef_id : 5
         * chef_name : brijesh chef
         * chef_image : 1520577541jobseeker.png
         * ordertime : null
         * bookdate : 2018-05-09
         * price : 65
         * order_status : New order
         * order_order_id : 213211
         * order_id : 6
         * follow : 3
         * rating : 4.5
         */

        private int chef_id;
        private String chef_name;
        private String chef_image;
        private Object ordertime;
        private String bookdate;
        private String price;
        private String order_status;
        private String order_order_id;
        private String order_id;
        private String follow;
        private String rating;

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

        public Object getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(Object ordertime) {
            this.ordertime = ordertime;
        }

        public String getBookdate() {
            return bookdate;
        }

        public void setBookdate(String bookdate) {
            this.bookdate = bookdate;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_order_id() {
            return order_order_id;
        }

        public void setOrder_order_id(String order_order_id) {
            this.order_order_id = order_order_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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
    }
}
