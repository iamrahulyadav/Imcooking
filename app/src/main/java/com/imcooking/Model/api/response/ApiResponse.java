package com.imcooking.Model.api.response;

public class ApiResponse {
    /**
     * status : true
     * msg : Successfully login
     * user_data : {"user_id":28,"user_type":"1","user_name":"vk","full_name":null,"user_email":"a@a.a","user_phone":null}
     */

    private boolean status;
    private String msg;
    private UserDataBean user_data;
    public DishDetails dish_details;
    public AddCart add_cart;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserDataBean getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataBean user_data) {
        this.user_data = user_data;
    }

    public DishDetails getDish_details() {
        return dish_details;
    }

    public void setDish_details(DishDetails dish_details) {
        this.dish_details = dish_details;
    }

    public AddCart getAdd_cart() {
        return add_cart;
    }

    public void setAdd_cart(AddCart add_cart) {
        this.add_cart = add_cart;
    }

    public static class UserDataBean {
        /**
         * user_id : 28
         * user_type : 1
         * user_name : vk
         * full_name : null
         * user_email : a@a.a
         * user_phone : null
         */

        private int user_id;
        private String user_type;
        private String user_name;
        private Object full_name;
        private String user_email;
        private Object user_phone;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public Object getFull_name() {
            return full_name;
        }

        public void setFull_name(Object full_name) {
            this.full_name = full_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public Object getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(Object user_phone) {
            this.user_phone = user_phone;
        }
    }














/*
    *//**
     * status : true
     * msg : Successfully registered
     * user_data : {"user_id":23,"user_type":"1","user_name":null,"user_email":"vaibhav@gmail.com","user_phone":null}
     *//*

    private boolean status;
    private String msg;
    private UserDataBean user_data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserDataBean getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataBean user_data) {
        this.user_data = user_data;
    }

    public static class UserDataBean {
        *//**
         * user_id : 23
         * user_type : 1
         * user_name : null
         * full_name : null
         * user_email : vaibhav@gmail.com
         * user_phone : null
         *//*

        private int user_id;
        private String user_type;
        private String user_name;
        private Object full_name;
        private String user_email;

        public Object getFull_name() {
            return full_name;
        }

        public void setFull_name(Object full_name) {
            this.full_name = full_name;
        }

        private Object user_phone;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public Object getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(Object user_phone) {
            this.user_phone = user_phone;
        }
    }*/
}
