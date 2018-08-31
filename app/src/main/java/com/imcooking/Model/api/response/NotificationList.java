package com.imcooking.Model.api.response;

import java.util.List;

/**
 * Created by Rakhi on 8/27/2018.
 */
public class NotificationList {

    /**
     * status : true
     * notification : [{"notification_id":20,"notification_user_id":3,"notification_title":"New Request Received ","notification_description":"New Request for a dish has been received from VK","notification_body":"","notification_date":"2018-08-24 12:52:27","notification_status":"0"},{"notification_id":22,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:29:50","notification_status":"0"},{"notification_id":23,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:33:08","notification_status":"0"},{"notification_id":24,"notification_user_id":3,"notification_title":"New Request Received ","notification_description":"New Request for a dish has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:33:30","notification_status":"0"},{"notification_id":25,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:36:01","notification_status":"0"},{"notification_id":26,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:42:28","notification_status":"0"},{"notification_id":27,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:44:19","notification_status":"0"},{"notification_id":28,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:50:58","notification_status":"0"},{"notification_id":29,"notification_user_id":3,"notification_title":"New Order Received ","notification_description":"New Order has been received from Rakhi","notification_body":"","notification_date":"2018-08-27 10:52:56","notification_status":"0"}]
     */

    private boolean status;
    private List<NotificationBean> notification;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<NotificationBean> getNotification() {
        return notification;
    }

    public void setNotification(List<NotificationBean> notification) {
        this.notification = notification;
    }

    public static class NotificationBean {
        /**
         * notification_id : 20
         * notification_user_id : 3
         * notification_title : New Request Received
         * notification_description : New Request for a dish has been received from VK
         * notification_body :
         * notification_date : 2018-08-24 12:52:27
         * notification_status : 0
         */

        private int notification_id;
        private int notification_user_id;
        private String notification_title;
        private String notification_description;
        private String notification_body;
        private String notification_date;
        private String notification_status;
        private String notification_type;

        public String getNotification_type() {
            return notification_type;
        }

        public void setNotification_type(String notification_type) {
            this.notification_type = notification_type;
        }

        public int getNotification_id() {
            return notification_id;
        }

        public void setNotification_id(int notification_id) {
            this.notification_id = notification_id;
        }

        public int getNotification_user_id() {
            return notification_user_id;
        }

        public void setNotification_user_id(int notification_user_id) {
            this.notification_user_id = notification_user_id;
        }

        public String getNotification_title() {
            return notification_title;
        }

        public void setNotification_title(String notification_title) {
            this.notification_title = notification_title;
        }

        public String getNotification_description() {
            return notification_description;
        }

        public void setNotification_description(String notification_description) {
            this.notification_description = notification_description;
        }

        public String getNotification_body() {
            return notification_body;
        }

        public void setNotification_body(String notification_body) {
            this.notification_body = notification_body;
        }

        public String getNotification_date() {
            return notification_date;
        }

        public void setNotification_date(String notification_date) {
            this.notification_date = notification_date;
        }

        public String getNotification_status() {
            return notification_status;
        }

        public void setNotification_status(String notification_status) {
            this.notification_status = notification_status;
        }
    }
}
