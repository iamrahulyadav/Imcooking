package com.imcooking.Model.api.response;

import java.util.List;

public class ChefFollowers {


    /**
     * status : true
     * foodie_details_list : [{"foodie_name":"i am foodie","foodie_phone":9874562,"foodie_email":"foodie@gmail.com","foodie_address":"","foodie_image":"80.png","foodie_miles":null}]
     */

    private boolean status;
    private List<FoodieDetailsListBean> foodie_details_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FoodieDetailsListBean> getFoodie_details_list() {
        return foodie_details_list;
    }

    public void setFoodie_details_list(List<FoodieDetailsListBean> foodie_details_list) {
        this.foodie_details_list = foodie_details_list;
    }

    public static class FoodieDetailsListBean {
        /**
         * foodie_name : i am foodie
         * foodie_phone : 9874562
         * foodie_email : foodie@gmail.com
         * foodie_address :
         * foodie_image : 80.png
         * foodie_miles : null
         */

        private String foodie_name;
        private int foodie_phone;
        private String foodie_email;
        private String foodie_address;
        private String foodie_image;
        private Object foodie_miles;

        public String getFoodie_name() {
            return foodie_name;
        }

        public void setFoodie_name(String foodie_name) {
            this.foodie_name = foodie_name;
        }

        public int getFoodie_phone() {
            return foodie_phone;
        }

        public void setFoodie_phone(int foodie_phone) {
            this.foodie_phone = foodie_phone;
        }

        public String getFoodie_email() {
            return foodie_email;
        }

        public void setFoodie_email(String foodie_email) {
            this.foodie_email = foodie_email;
        }

        public String getFoodie_address() {
            return foodie_address;
        }

        public void setFoodie_address(String foodie_address) {
            this.foodie_address = foodie_address;
        }

        public String getFoodie_image() {
            return foodie_image;
        }

        public void setFoodie_image(String foodie_image) {
            this.foodie_image = foodie_image;
        }

        public Object getFoodie_miles() {
            return foodie_miles;
        }

        public void setFoodie_miles(Object foodie_miles) {
            this.foodie_miles = foodie_miles;
        }
    }
}
