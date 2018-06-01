package com.imcooking.Model.api.response;

import java.util.List;

// FIXME generate failure  field _$FoodieRequestDishChefDetails189
// FIXME generate failure  field _$FoodieRequestDishChefDetails250
public class FoodieMyRequest {


    /**
     * status : true
     * foodie_request_dish_chef_details : [{"chef_id":72,"chef_name":"imcooking","chef_phone":96325874112,"chef_email":"Patrick123@gmail.com","chef_address":"noida","chef_image":"1525759957234.jpg","chef_description":"sdfsdf"}]
     */

    private boolean status;
    private List<FoodieRequestDishChefDetailsBean> foodie_request_dish_chef_details;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FoodieRequestDishChefDetailsBean> getFoodie_request_dish_chef_details() {
        return foodie_request_dish_chef_details;
    }

    public void setFoodie_request_dish_chef_details(List<FoodieRequestDishChefDetailsBean> foodie_request_dish_chef_details) {
        this.foodie_request_dish_chef_details = foodie_request_dish_chef_details;
    }

    public static class FoodieRequestDishChefDetailsBean {
        /**
         * chef_id : 72
         * chef_name : imcooking
         * chef_phone : 96325874112
         * chef_email : Patrick123@gmail.com
         * chef_address : noida
         * chef_image : 1525759957234.jpg
         * chef_description : sdfsdf
         */

        private int chef_id;
        private String chef_name;
        private long chef_phone;
        private String chef_email;
        private String chef_address;
        private String chef_image;
        private String chef_description;

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

        public String getChef_address() {
            return chef_address;
        }

        public void setChef_address(String chef_address) {
            this.chef_address = chef_address;
        }

        public String getChef_image() {
            return chef_image;
        }

        public void setChef_image(String chef_image) {
            this.chef_image = chef_image;
        }

        public String getChef_description() {
            return chef_description;
        }

        public void setChef_description(String chef_description) {
            this.chef_description = chef_description;
        }
    }
}
