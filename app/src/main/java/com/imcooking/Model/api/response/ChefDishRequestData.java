package com.imcooking.Model.api.response;

import java.util.List;

public class ChefDishRequestData {

    /**
     * status : true
     * chef_dish_details : [{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"panner","request_date":"5/29/2018","request_quantity":"2","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"mater paner","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"paner","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"paner","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"sahi paner","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"paner","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"paner","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":5,"foodie_name":"brijesh chef","foodie_email":"brijesh@gmail.com","foodie_phone":987456321,"foodie_address":"noida","dish_name":"paner123","request_date":"30/05/2018","request_quantity":"5","request_cusine_name":"indian"},{"foodie_id":104,"foodie_name":"jatinder","foodie_email":"kh.vaibhav10@gmail.com","foodie_phone":7988961496,"foodie_address":"","dish_name":"hii","request_date":"11/06/18","request_quantity":"6","request_cusine_name":"Indian Food"}]
     */

    private boolean status;
    private List<ChefDishDetailsBean> chef_dish_details;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ChefDishDetailsBean> getChef_dish_details() {
        return chef_dish_details;
    }

    public void setChef_dish_details(List<ChefDishDetailsBean> chef_dish_details) {
        this.chef_dish_details = chef_dish_details;
    }

    public static class ChefDishDetailsBean {
        /**
         * foodie_id : 5
         * foodie_name : brijesh chef
         * foodie_email : brijesh@gmail.com
         * foodie_phone : 987456321
         * foodie_address : noida
         * dish_name : panner
         * request_date : 5/29/2018
         * request_quantity : 2
         * request_cusine_name : indian
         */

        private int foodie_id;
        private String foodie_name;
        private String foodie_email;
        private String foodie_phone;
        private String foodie_address;
        private String dish_name;
        private String request_date;
        private String request_quantity;
        private String request_cusine_name;

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

        public String getFoodie_email() {
            return foodie_email;
        }

        public void setFoodie_email(String foodie_email) {
            this.foodie_email = foodie_email;
        }

        public String getFoodie_phone() {
            return foodie_phone;
        }

        public void setFoodie_phone(String foodie_phone) {
            this.foodie_phone = foodie_phone;
        }

        public String getFoodie_address() {
            return foodie_address;
        }

        public void setFoodie_address(String foodie_address) {
            this.foodie_address = foodie_address;
        }

        public String getDish_name() {
            return dish_name;
        }

        public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
        }

        public String getRequest_date() {
            return request_date;
        }

        public void setRequest_date(String request_date) {
            this.request_date = request_date;
        }

        public String getRequest_quantity() {
            return request_quantity;
        }

        public void setRequest_quantity(String request_quantity) {
            this.request_quantity = request_quantity;
        }

        public String getRequest_cusine_name() {
            return request_cusine_name;
        }

        public void setRequest_cusine_name(String request_cusine_name) {
            this.request_cusine_name = request_cusine_name;
        }
    }
}
