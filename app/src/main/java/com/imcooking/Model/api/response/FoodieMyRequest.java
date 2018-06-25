package com.imcooking.Model.api.response;

import java.util.List;

// FIXME generate failure  field _$FoodieRequestDishChefDetails189
// FIXME generate failure  field _$FoodieRequestDishChefDetails250
public class FoodieMyRequest {


    /**
     * status : true
     * foodie_request_dish_chef_details : [{"request_id":15,"chef_id":103,"chef_name":"vkumar","chef_phone":991001727,"chef_email":"rakh1@askonlinesolutions.com","chef_address":"E-164, Sector-15, Noida, UP","chef_image":"83.png","chef_description":"yes","chef_request_datetime":"21/06/18 2 : 19","chef_rating":0,"chef_accepted":"no","conversation_details":[{"conversation_sender_id":116,"conversation_request_id":15,"conversation_reciver_id":103,"conversation_message":"null test","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/20/2018 11:01:40 am"},{"conversation_sender_id":116,"conversation_request_id":15,"conversation_reciver_id":103,"conversation_message":"test","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/20/2018 09:26:37 am"},{"conversation_sender_id":116,"conversation_request_id":15,"conversation_reciver_id":103,"conversation_message":"test","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/20/2018 09:22:49 am"}]}]
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
         * request_id : 15
         * chef_id : 103
         * chef_name : vkumar
         * chef_phone : 991001727
         * chef_email : rakh1@askonlinesolutions.com
         * chef_address : E-164, Sector-15, Noida, UP
         * chef_image : 83.png
         * chef_description : yes
         * chef_request_datetime : 21/06/18 2 : 19
         * chef_rating : 0
         * chef_accepted : no
         * conversation_details : [{"conversation_sender_id":116,"conversation_request_id":15,"conversation_reciver_id":103,"conversation_message":"null test","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/20/2018 11:01:40 am"},{"conversation_sender_id":116,"conversation_request_id":15,"conversation_reciver_id":103,"conversation_message":"test","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/20/2018 09:26:37 am"},{"conversation_sender_id":116,"conversation_request_id":15,"conversation_reciver_id":103,"conversation_message":"test","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/20/2018 09:22:49 am"}]
         */

        private int request_id;
        private int chef_id;
        private String chef_name;
        private String chef_phone;
        private String chef_email;
        private String chef_address;
        private String chef_image;
        private String chef_description;
        private String chef_request_datetime;
        private int chef_rating;
        private String chef_accepted;
        private String request_cusine_name;
        private String request_dishname;
        private String request_note;
        private String request_quantity;

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

        public String getRequest_dishname() {
            return request_dishname;
        }

        public void setRequest_dishname(String request_dishname) {
            this.request_dishname = request_dishname;
        }

        public String getRequest_note() {
            return request_note;
        }

        public void setRequest_note(String request_note) {
            this.request_note = request_note;
        }

        private List<ConversationDetailsBean> conversation_details;

        public int getRequest_id() {
            return request_id;
        }

        public void setRequest_id(int request_id) {
            this.request_id = request_id;
        }

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

        public String getChef_description() {
            return chef_description;
        }

        public void setChef_description(String chef_description) {
            this.chef_description = chef_description;
        }

        public String getChef_request_datetime() {
            return chef_request_datetime;
        }

        public void setChef_request_datetime(String chef_request_datetime) {
            this.chef_request_datetime = chef_request_datetime;
        }

        public int getChef_rating() {
            return chef_rating;
        }

        public void setChef_rating(int chef_rating) {
            this.chef_rating = chef_rating;
        }

        public String getChef_accepted() {
            return chef_accepted;
        }

        public void setChef_accepted(String chef_accepted) {
            this.chef_accepted = chef_accepted;
        }

        public List<ConversationDetailsBean> getConversation_details() {
            return conversation_details;
        }

        public void setConversation_details(List<ConversationDetailsBean> conversation_details) {
            this.conversation_details = conversation_details;
        }

        public static class ConversationDetailsBean {
            /**
             * conversation_sender_id : 116
             * conversation_request_id : 15
             * conversation_reciver_id : 103
             * conversation_message : null test
             * conversation_offer_price : 220.00
             * conversation_staus : 1
             * conversation_date : 06/20/2018 11:01:40 am
             */

            private int conversation_sender_id;
            private int conversation_request_id;
            private int conversation_reciver_id;
            private String conversation_message;
            private String conversation_offer_price;
            private String conversation_staus;
            private String conversation_date;

            public int getConversation_sender_id() {
                return conversation_sender_id;
            }

            public void setConversation_sender_id(int conversation_sender_id) {
                this.conversation_sender_id = conversation_sender_id;
            }

            public int getConversation_request_id() {
                return conversation_request_id;
            }

            public void setConversation_request_id(int conversation_request_id) {
                this.conversation_request_id = conversation_request_id;
            }

            public int getConversation_reciver_id() {
                return conversation_reciver_id;
            }

            public void setConversation_reciver_id(int conversation_reciver_id) {
                this.conversation_reciver_id = conversation_reciver_id;
            }

            public String getConversation_message() {
                return conversation_message;
            }

            public void setConversation_message(String conversation_message) {
                this.conversation_message = conversation_message;
            }

            public String getConversation_offer_price() {
                return conversation_offer_price;
            }

            public void setConversation_offer_price(String conversation_offer_price) {
                this.conversation_offer_price = conversation_offer_price;
            }

            public String getConversation_staus() {
                return conversation_staus;
            }

            public void setConversation_staus(String conversation_staus) {
                this.conversation_staus = conversation_staus;
            }

            public String getConversation_date() {
                return conversation_date;
            }

            public void setConversation_date(String conversation_date) {
                this.conversation_date = conversation_date;
            }
        }
    }
}
