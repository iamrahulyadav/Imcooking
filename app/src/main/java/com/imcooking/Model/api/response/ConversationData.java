package com.imcooking.Model.api.response;

import java.util.List;

public class ConversationData {


    /**
     * status : true
     * conversation_msg : [{"conversation_sender_id":10,"conversation_request_id":4,"conversation_reciver_id":21,"conversation_message":" ","conversation_staus":"yes","conversation_date":"08/09/2018 04:36:23 pm"},{"conversation_sender_id":21,"conversation_request_id":4,"conversation_reciver_id":10,"conversation_message":" ","conversation_staus":"yes","conversation_date":"08/07/2018 07:08:53 pm"},{"conversation_sender_id":21,"conversation_request_id":4,"conversation_reciver_id":10,"conversation_message":"thanks tony","conversation_staus":"reply","conversation_date":"08/07/2018 07:08:42 pm"},{"conversation_sender_id":10,"conversation_request_id":4,"conversation_reciver_id":21,"conversation_message":" ","conversation_staus":"yes","conversation_date":"08/07/2018 07:05:11 pm"},{"conversation_sender_id":10,"conversation_request_id":4,"conversation_reciver_id":21,"conversation_message":"hi, thank you. Will get it done","conversation_staus":"reply","conversation_date":"08/07/2018 07:04:40 pm"},{"conversation_sender_id":10,"conversation_request_id":4,"conversation_reciver_id":21,"conversation_message":"hi, thank you. Will get it done","conversation_staus":"yes","conversation_date":"08/07/2018 07:04:33 pm"}]
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
         * conversation_sender_id : 10
         * conversation_request_id : 4
         * conversation_reciver_id : 21
         * conversation_message :
         * conversation_staus : yes
         * conversation_date : 08/09/2018 04:36:23 pm
         */

        private String conversation_sender_id;
        private String conversation_request_id;
        private String conversation_reciver_id;
        private String conversation_message;
        private String conversation_staus;
        private String conversation_date;

        public String getConversation_sender_id() {
            return conversation_sender_id;
        }

        public void setConversation_sender_id(String conversation_sender_id) {
            this.conversation_sender_id = conversation_sender_id;
        }

        public String getConversation_request_id() {
            return conversation_request_id;
        }

        public void setConversation_request_id(String conversation_request_id) {
            this.conversation_request_id = conversation_request_id;
        }

        public String getConversation_reciver_id() {
            return conversation_reciver_id;
        }

        public void setConversation_reciver_id(String conversation_reciver_id) {
            this.conversation_reciver_id = conversation_reciver_id;
        }

        public String getConversation_message() {
            return conversation_message;
        }

        public void setConversation_message(String conversation_message) {
            this.conversation_message = conversation_message;
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
