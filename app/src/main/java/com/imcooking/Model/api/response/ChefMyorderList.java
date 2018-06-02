package com.imcooking.Model.api.response;

import java.util.List;

public class ChefMyorderList {

    /**
     * status : true
     * my_order_list : [{"foodie_id":1,"foodie_name":"i am foodie","foodie_image":"80.png","ordertime":"2018-03-14 10:32:03","bookdate":"2018-03-14","price":120,"order_status":"Ready to deliver","order_order_id":"80351749","order_id":1,"follow":0,"rating":""}]
     */

    private boolean status;
    private List<MyOrderListBean> my_order_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<MyOrderListBean> getMy_order_list() {
        return my_order_list;
    }

    public void setMy_order_list(List<MyOrderListBean> my_order_list) {
        this.my_order_list = my_order_list;
    }

    public static class MyOrderListBean {
        /**
         * foodie_id : 1
         * foodie_name : i am foodie
         * foodie_image : 80.png
         * ordertime : 2018-03-14 10:32:03
         * bookdate : 2018-03-14
         * price : 120
         * order_status : Ready to deliver
         * order_order_id : 80351749
         * order_id : 1
         * follow : 0
         * rating :
         */

        private int foodie_id;
        private String foodie_name;
        private String foodie_image;
        private String ordertime;
        private String bookdate;
        private int price;
        private String order_status;
        private String order_order_id;
        private int order_id;
        private int follow;
        private String rating;

        public int getFoodie_id() {
            return foodie_id;
        }

        public void setFoodie_id(int foodie_id) {
            this.foodie_id = foodie_id;
        }

        public String getFoodie_name() {
            return foodie_name;
        }

        public void setFoodie_name(String foodie_name) {
            this.foodie_name = foodie_name;
        }

        public String getFoodie_image() {
            return foodie_image;
        }

        public void setFoodie_image(String foodie_image) {
            this.foodie_image = foodie_image;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getBookdate() {
            return bookdate;
        }

        public void setBookdate(String bookdate) {
            this.bookdate = bookdate;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
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