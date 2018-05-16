package com.imcooking.Model.api.response;

import java.util.List;

public class ChefIloveData {

    private List<ChefloveBean> cheflove;

    public List<ChefloveBean> getCheflove() {
        return cheflove;
    }

    public void setCheflove(List<ChefloveBean> cheflove) {
        this.cheflove = cheflove;
    }

    public static class ChefloveBean {
        /**
         * chef_name : i am foodie
         * chef_image : 80.png
         * cuisine_name : Indian Food
         * rating : 4
         * miles : null
         */

        private String chef_name;
        private String chef_image;
        private String cuisine_name;
        private int rating;
        private Object miles;

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

        public String getCuisine_name() {
            return cuisine_name;
        }

        public void setCuisine_name(String cuisine_name) {
            this.cuisine_name = cuisine_name;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public Object getMiles() {
            return miles;
        }

        public void setMiles(Object miles) {
            this.miles = miles;
        }
    }
}
