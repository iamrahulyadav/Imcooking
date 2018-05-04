package com.imcooking.Model.api.response;

import java.util.List;

public class CuisineData {

    private List<CuisineDataBean> cuisine_data;

    public List<CuisineDataBean> getCuisine_data() {
        return cuisine_data;
    }

    public void setCuisine_data(List<CuisineDataBean> cuisine_data) {
        this.cuisine_data = cuisine_data;
    }

    public static class CuisineDataBean {
        /**
         * cuisine_id : 18
         * cuisine_name : Indian Food
         * cuisine_image : 1521269514indianfood.jpg
         */

        private int cuisine_id;
        private String cuisine_name;
        private String cuisine_image;

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

        public String getCuisine_image() {
            return cuisine_image;
        }

        public void setCuisine_image(String cuisine_image) {
            this.cuisine_image = cuisine_image;
        }
    }
}
