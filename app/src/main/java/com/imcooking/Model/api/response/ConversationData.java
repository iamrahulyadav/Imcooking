package com.imcooking.Model.api.response;

import java.util.List;

public class ConversationData {

    /**
     * status : true
     * conversation_msg : [{"conversation_sender_id":104,"conversation_request_id":2,"conversation_reciver_id":72,"conversation_message":"gud nd u","conversation_offer_price":"100.00","conversation_staus":"1","conversation_date":"06/20/2018 09:05:51 am"},{"conversation_sender_id":104,"conversation_request_id":2,"conversation_reciver_id":72,"conversation_message":"gud nd u","conversation_offer_price":"100.00","conversation_staus":"1","conversation_date":"06/20/2018 08:55:51 am"},{"conversation_sender_id":104,"conversation_request_id":2,"conversation_reciver_id":72,"conversation_message":"gud nd u","conversation_offer_price":"100.00","conversation_staus":"1","conversation_date":"06/20/2018 07:18:25 am"},{"conversation_sender_id":104,"conversation_request_id":2,"conversation_reciver_id":72,"conversation_message":"gud nd ","conversation_offer_price":"100.00","conversation_staus":"1","conversation_date":"06/13/2018 10:19:08 am"},{"conversation_sender_id":104,"conversation_request_id":2,"conversation_reciver_id":72,"conversation_message":"gud nd u","conversation_offer_price":"100.00","conversation_staus":"1","conversation_date":"06/13/2018 10:10:55 am"}]
     */

    private boolean status;
    private List<ConversationMsgBean> conversation_msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ConversationMsgBean> getConversation_msg() {
        return conversation_msg;
    }

    public void setConversation_msg(List<ConversationMsgBean> conversation_msg) {
        this.conversation_msg = conversation_msg;
    }

    public static class ConversationMsgBean {
        /**
         * conversation_sender_id : 104
         * conversation_request_id : 2
         * conversation_reciver_id : 72
         * conversation_message : gud nd u
         * conversation_offer_price : 100.00
         * conversation_staus : 1
         * conversation_date : 06/20/2018 09:05:51 am
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
