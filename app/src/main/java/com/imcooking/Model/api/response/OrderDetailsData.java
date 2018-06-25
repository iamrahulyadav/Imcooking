package com.imcooking.Model.api.response;

import java.util.List;

public class OrderDetailsData {


    /**
     * status : true
     * order_details : [{"order_id":28,"order_order_id":"CO2506202725204","order_percentage":null,"chef_name":"brijesh chef","chef_email":"brijesh@gmail.com","chef_phone":987456321,"chef_address":"noida","order_foodie_id":116,"order_foodie_name":"Rakhi","order_foodie_email":"s.rakhi@askonlinesolutions.com","order_foodie_phone":958187463,"delivery_type":"2","order_price":"345.00","order_total_price":"690.00","booking_date":"2018-06-25 16:45:37","order_dish_name":"india food","order_dish_image":[{"dishimage_image":"1524488071kheer.jpg"},{"dishimage_image":"1524488071gulabjamun.jpg"},{"dishimage_image":"1524488071chaatpapri.jpg"},{"dishimage_image":"1524488071chanadal.jpg"},{"dishimage_image":"1524488071alugobi.jpg"}],"order_quantity":2,"order_from_time":"","order_addres":"noida","order_to_time":"","order_payment_type":"paypal","order_transaction_id":"PAY-8S273276JJ494213GLMYM53A","order_status":0,"order_createdate":"06/25/2018 11:16:09 am"}]
     */

    private boolean status;
    private List<OrderDetailsBean> order_details;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<OrderDetailsBean> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(List<OrderDetailsBean> order_details) {
        this.order_details = order_details;
    }

    public static class OrderDetailsBean {
        /**
         * order_id : 28
         * order_order_id : CO2506202725204
         * order_percentage : null
         * chef_name : brijesh chef
         * chef_email : brijesh@gmail.com
         * chef_phone : 987456321
         * chef_address : noida
         * order_foodie_id : 116
         * order_foodie_name : Rakhi
         * order_foodie_email : s.rakhi@askonlinesolutions.com
         * order_foodie_phone : 958187463
         * delivery_type : 2
         * order_price : 345.00
         * order_total_price : 690.00
         * booking_date : 2018-06-25 16:45:37
         * order_dish_name : india food
         * order_dish_image : [{"dishimage_image":"1524488071kheer.jpg"},{"dishimage_image":"1524488071gulabjamun.jpg"},{"dishimage_image":"1524488071chaatpapri.jpg"},{"dishimage_image":"1524488071chanadal.jpg"},{"dishimage_image":"1524488071alugobi.jpg"}]
         * order_quantity : 2
         * order_from_time :
         * order_addres : noida
         * order_to_time :
         * order_payment_type : paypal
         * order_transaction_id : PAY-8S273276JJ494213GLMYM53A
         * order_status : 0
         * order_createdate : 06/25/2018 11:16:09 am
         */

        private int order_id;
        private String order_order_id;
        private Object order_percentage;
        private String chef_name;
        private String chef_email;
        private String chef_phone;
        private String chef_address;
        private int order_foodie_id;
        private String order_foodie_name;
        private String order_foodie_email;
        private String order_foodie_phone;
        private String delivery_type;
        private String order_price;
        private String order_total_price;
        private String booking_date;
        private String order_dish_name;
        private int order_quantity;
        private String order_from_time;
        private String order_addres;
        private String order_to_time;
        private String order_payment_type;
        private String order_transaction_id;
        private String order_status;
        private String order_createdate;
        private List<OrderDishImageBean> order_dish_image;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_order_id() {
            return order_order_id;
        }

        public void setOrder_order_id(String order_order_id) {
            this.order_order_id = order_order_id;
        }

        public Object getOrder_percentage() {
            return order_percentage;
        }

        public void setOrder_percentage(Object order_percentage) {
            this.order_percentage = order_percentage;
        }

        public String getChef_name() {
            return chef_name;
        }

        public void setChef_name(String chef_name) {
            this.chef_name = chef_name;
        }

        public String getChef_email() {
            return chef_email;
        }

        public void setChef_email(String chef_email) {
            this.chef_email = chef_email;
        }

        public String getChef_phone() {
            return chef_phone;
        }

        public void setChef_phone(String chef_phone) {
            this.chef_phone = chef_phone;
        }

        public String getChef_address() {
            return chef_address;
        }

        public void setChef_address(String chef_address) {
            this.chef_address = chef_address;
        }

        public int getOrder_foodie_id() {
            return order_foodie_id;
        }

        public void setOrder_foodie_id(int order_foodie_id) {
            this.order_foodie_id = order_foodie_id;
        }

        public String getOrder_foodie_name() {
            return order_foodie_name;
        }

        public void setOrder_foodie_name(String order_foodie_name) {
            this.order_foodie_name = order_foodie_name;
        }

        public String getOrder_foodie_email() {
            return order_foodie_email;
        }

        public void setOrder_foodie_email(String order_foodie_email) {
            this.order_foodie_email = order_foodie_email;
        }

        public String getOrder_foodie_phone() {
            return order_foodie_phone;
        }

        public void setOrder_foodie_phone(String order_foodie_phone) {
            this.order_foodie_phone = order_foodie_phone;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getOrder_total_price() {
            return order_total_price;
        }

        public void setOrder_total_price(String order_total_price) {
            this.order_total_price = order_total_price;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
        }

        public String getOrder_dish_name() {
            return order_dish_name;
        }

        public void setOrder_dish_name(String order_dish_name) {
            this.order_dish_name = order_dish_name;
        }

        public int getOrder_quantity() {
            return order_quantity;
        }

        public void setOrder_quantity(int order_quantity) {
            this.order_quantity = order_quantity;
        }

        public String getOrder_from_time() {
            return order_from_time;
        }

        public void setOrder_from_time(String order_from_time) {
            this.order_from_time = order_from_time;
        }

        public String getOrder_addres() {
            return order_addres;
        }

        public void setOrder_addres(String order_addres) {
            this.order_addres = order_addres;
        }

        public String getOrder_to_time() {
            return order_to_time;
        }

        public void setOrder_to_time(String order_to_time) {
            this.order_to_time = order_to_time;
        }

        public String getOrder_payment_type() {
            return order_payment_type;
        }

        public void setOrder_payment_type(String order_payment_type) {
            this.order_payment_type = order_payment_type;
        }

        public String getOrder_transaction_id() {
            return order_transaction_id;
        }

        public void setOrder_transaction_id(String order_transaction_id) {
            this.order_transaction_id = order_transaction_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_createdate() {
            return order_createdate;
        }

        public void setOrder_createdate(String order_createdate) {
            this.order_createdate = order_createdate;
        }

        public List<OrderDishImageBean> getOrder_dish_image() {
            return order_dish_image;
        }

        public void setOrder_dish_image(List<OrderDishImageBean> order_dish_image) {
            this.order_dish_image = order_dish_image;
        }

        public static class OrderDishImageBean {
            /**
             * dishimage_image : 1524488071kheer.jpg
             */

            private String dishimage_image;

            public String getDishimage_image() {
                return dishimage_image;
            }

            public void setDishimage_image(String dishimage_image) {
                this.dishimage_image = dishimage_image;
            }
        }
    }
}
