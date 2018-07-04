package com.imcooking.Model.api.response;

import java.util.List;

public class ChefIloveData {


    /**
     * status : true
     * cheflove : [{"chef_id":103,"chef_name":"Vaibhav","chef_phone":991001727,"chef_email":"rakh1@askonlinesolutions.com","chef_address":"E-164, Sector-15, Noida, UP","chef_image":"83.png","cuisine_name":"Indian Food","rating":0,"miles":"10 miles "}]
     */

    private boolean status;
    private List<ChefloveBean> cheflove;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ChefloveBean> getCheflove() {
        return cheflove;
    }

    public void setCheflove(List<ChefloveBean> cheflove) {
        this.cheflove = cheflove;
    }

    public static class ChefloveBean {
        /**
         * chef_id : 103
         * chef_name : Vaibhav
         * chef_phone : 991001727
         * chef_email : rakh1@askonlinesolutions.com
         * chef_address : E-164, Sector-15, Noida, UP
         * chef_image : 83.png
         * cuisine_name : Indian Food
         * rating : 0
         * miles : 10 miles
         */

        private int chef_id;
        private String chef_name;
        private String chef_phone;
        private String chef_email;
        private String chef_address;
        private String chef_image;
        private String cuisine_name;
        private String rating;
        private String miles;

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

        public String getChef_phone() {
            return chef_phone;
        }

        public void setChef_phone(String chef_phone) {
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

        public String getCuisine_name() {
            return cuisine_name;
        }

        public void setCuisine_name(String cuisine_name) {
            this.cuisine_name = cuisine_name;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getMiles() {
            return miles;
        }

        public void setMiles(String miles) {
            this.miles = miles;
        }
    }
}
