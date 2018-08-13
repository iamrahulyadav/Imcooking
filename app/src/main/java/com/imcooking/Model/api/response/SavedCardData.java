package com.imcooking.Model.api.response;

import java.util.List;

/**
 * Created by Rakhi on 8/10/2018.
 */
public class SavedCardData {

    /**
     * status : true
     * payment_details_list : [{"payment_details_id":1,"payment_details_foodied_id":2,"payment_details_card_type":"Visa","payment_details_card_number":2147483647,"payment_details_expiry_date":"20/12/2019","payment_details_name_of_card":"sbi","payment_details_create_date":"2018-08-09 12:11:20"},{"payment_details_id":2,"payment_details_foodied_id":2,"payment_details_card_type":"Visa ","payment_details_card_number":111111111444447895,"payment_details_expiry_date":"20/12/2019","payment_details_name_of_card":"sbi","payment_details_create_date":"2018-08-10 16:51:59"},{"payment_details_id":6,"payment_details_foodied_id":2,"payment_details_card_type":"Visa","payment_details_card_number":2147483647,"payment_details_expiry_date":"20/12/2019","payment_details_name_of_card":"sbi","payment_details_create_date":"2018-08-09 12:17:48"}]
     */

    private boolean status;
    private List<PaymentDetailsListBean> payment_details_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PaymentDetailsListBean> getPayment_details_list() {
        return payment_details_list;
    }

    public void setPayment_details_list(List<PaymentDetailsListBean> payment_details_list) {
        this.payment_details_list = payment_details_list;
    }

    public static class PaymentDetailsListBean {
        /**
         * payment_details_id : 1
         * payment_details_foodied_id : 2
         * payment_details_card_type : Visa
         * payment_details_card_number : 2147483647
         * payment_details_expiry_date : 20/12/2019
         * payment_details_name_of_card : sbi
         * payment_details_create_date : 2018-08-09 12:11:20
         */

        private int payment_details_id;
        private int payment_details_foodied_id;
        private String payment_details_card_type;
        private String payment_details_card_number;
        private String payment_details_expiry_date;
        private String payment_details_name_of_card;
        private String payment_details_create_date;

        public int getPayment_details_id() {
            return payment_details_id;
        }

        public void setPayment_details_id(int payment_details_id) {
            this.payment_details_id = payment_details_id;
        }

        public int getPayment_details_foodied_id() {
            return payment_details_foodied_id;
        }

        public void setPayment_details_foodied_id(int payment_details_foodied_id) {
            this.payment_details_foodied_id = payment_details_foodied_id;
        }

        public String getPayment_details_card_type() {
            return payment_details_card_type;
        }

        public void setPayment_details_card_type(String payment_details_card_type) {
            this.payment_details_card_type = payment_details_card_type;
        }

        public String getPayment_details_card_number() {
            return payment_details_card_number;
        }

        public void setPayment_details_card_number(String payment_details_card_number) {
            this.payment_details_card_number = payment_details_card_number;
        }

        public String getPayment_details_expiry_date() {
            return payment_details_expiry_date;
        }

        public void setPayment_details_expiry_date(String payment_details_expiry_date) {
            this.payment_details_expiry_date = payment_details_expiry_date;
        }

        public String getPayment_details_name_of_card() {
            return payment_details_name_of_card;
        }

        public void setPayment_details_name_of_card(String payment_details_name_of_card) {
            this.payment_details_name_of_card = payment_details_name_of_card;
        }

        public String getPayment_details_create_date() {
            return payment_details_create_date;
        }

        public void setPayment_details_create_date(String payment_details_create_date) {
            this.payment_details_create_date = payment_details_create_date;
        }
    }
}
