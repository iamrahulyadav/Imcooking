package com.imcooking.Model.api.response;

import java.util.List;

public class ChefAndDish {


    /**
     * status : true
     * response : {"user_list":[{"user_id":1,"user_name":"Brijesh"},{"user_id":5,"user_name":"brijesh chef"}],"dish_lst":[{"dish_id":5,"dish_name":"bri paner"}]}
     */

    private boolean status;
    private ResponseBean response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private List<UserListBean> user_list;
        private List<DishLstBean> dish_lst;

        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        public List<DishLstBean> getDish_lst() {
            return dish_lst;
        }

        public void setDish_lst(List<DishLstBean> dish_lst) {
            this.dish_lst = dish_lst;
        }

        public static class UserListBean {
            /**
             * user_id : 1
             * user_name : Brijesh
             */

            private int user_id;
            private String user_name;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }

        public static class DishLstBean {
            /**
             * dish_id : 5
             * dish_name : bri paner
             */

            private int dish_id;
            private String dish_name;

            public int getDish_id() {
                return dish_id;
            }

            public void setDish_id(int dish_id) {
                this.dish_id = dish_id;
            }

            public String getDish_name() {
                return dish_name;
            }

            public void setDish_name(String dish_name) {
                this.dish_name = dish_name;
            }
        }
    }
}
