package com.imcooking.Model.api.response;

import java.util.List;

public class DishLikeData {

    /**
     * status : true
     * dish_like_foodie_list : [{"foodie_id":104,"foodie_name":"foodie","user_phone":7988961496,"user_email":"kh.vaibhav10@gmail.com","user_address":"","foodie_image":"14.png","user_description":null},{"foodie_id":116,"foodie_name":"foodietest2","user_phone":958187463,"user_email":"s.rakhi@askonlinesolutions.com","user_address":"","foodie_image":"87.png","user_description":null}]
     */

    private boolean status;
    private List<DishLikeFoodieListBean> dish_like_foodie_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DishLikeFoodieListBean> getDish_like_foodie_list() {
        return dish_like_foodie_list;
    }

    public void setDish_like_foodie_list(List<DishLikeFoodieListBean> dish_like_foodie_list) {
        this.dish_like_foodie_list = dish_like_foodie_list;
    }

    public static class DishLikeFoodieListBean {
        /**
         * foodie_id : 104
         * foodie_name : foodie
         * user_phone : 7988961496
         * user_email : kh.vaibhav10@gmail.com
         * user_address :
         * foodie_image : 14.png
         * user_description : null
         */

        private int foodie_id;
        private String foodie_name;
        private String user_phone;
        private String user_email;
        private String user_address;
        private String foodie_image;
        private Object user_description;

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

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public String getFoodie_image() {
            return foodie_image;
        }

        public void setFoodie_image(String foodie_image) {
            this.foodie_image = foodie_image;
        }

        public Object getUser_description() {
            return user_description;
        }

        public void setUser_description(Object user_description) {
            this.user_description = user_description;
        }
    }
}
