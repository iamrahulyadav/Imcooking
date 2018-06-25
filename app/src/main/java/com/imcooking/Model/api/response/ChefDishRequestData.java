package com.imcooking.Model.api.response;

import com.android.volley.toolbox.StringRequest;

import java.util.List;

public class ChefDishRequestData {


    /**
     * status : true
     * chef_dish_details : [{"foodie_id":104,"foodie_name":"jatinder","foodie_email":"kh.vaibhav10@gmail.com","foodie_phone":7988961496,"foodie_address":"","dish_name":"test","request_date":"11/06/18","request_quantity":"2","request_cusine_name":"Indian Food","conversation_details":[{"conversation_sender_id":103,"conversation_request_id":2,"conversation_reciver_id":104,"conversation_message":"asdhsjf hiii","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/21/2018 07:28:47 am"}]},{"foodie_id":116,"foodie_name":"Rakhi","foodie_email":"s.rakhi@askonlinesolutions.com","foodie_phone":958187463,"foodie_address":"","dish_name":"gulab jamun","request_date":"21/06/18","request_quantity":"5","request_cusine_name":"Cajun Food","conversation_details":[]}]
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
         * foodie_id : 104
         * foodie_name : jatinder
         * foodie_email : kh.vaibhav10@gmail.com
         * foodie_phone : 7988961496
         * foodie_address :
         * dish_name : test
         * request_date : 11/06/18
         * request_quantity : 2
         * request_cusine_name : Indian Food
         * conversation_details : [{"conversation_sender_id":103,"conversation_request_id":2,"conversation_reciver_id":104,"conversation_message":"asdhsjf hiii","conversation_offer_price":"220.00","conversation_staus":"1","conversation_date":"06/21/2018 07:28:47 am"}]
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
        private String request_id;
        private List<ConversationDetailsBean> conversation_details;

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

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

        public List<ConversationDetailsBean> getConversation_details() {
            return conversation_details;
        }

        public void setConversation_details(List<ConversationDetailsBean> conversation_details) {
            this.conversation_details = conversation_details;
        }

        public static class ConversationDetailsBean {
            /**
             * conversation_sender_id : 103
             * conversation_request_id : 2
             * conversation_reciver_id : 104
             * conversation_message : asdhsjf hiii
             * conversation_offer_price : 220.00
             * conversation_staus : 1
             * conversation_date : 06/21/2018 07:28:47 am
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
