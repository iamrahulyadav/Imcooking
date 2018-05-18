package com.imcooking.Model.api.response;

import java.util.List;

public class AddressListData {

    /**
     * status : true
     * address : [{"address_id":2,"address_foodie_id":4,"address_title":"Office","address_address":"Noida sector 63","address_status":"1"},{"address_id":3,"address_foodie_id":4,"address_title":"Homedd","address_address":"Noida sector 63","address_status":"1"},{"address_id":4,"address_foodie_id":4,"address_title":"Home","address_address":"Noida sector 63","address_status":"1"}]
     */

    private boolean status;
    private List<AddressBean> address;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * address_id : 2
         * address_foodie_id : 4
         * address_title : Office
         * address_address : Noida sector 63
         * address_status : 1
         */

        private int address_id;
        private int address_foodie_id;
        private String address_title;
        private String address_address;
        private String address_status;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public int getAddress_foodie_id() {
            return address_foodie_id;
        }

        public void setAddress_foodie_id(int address_foodie_id) {
            this.address_foodie_id = address_foodie_id;
        }

        public String getAddress_title() {
            return address_title;
        }

        public void setAddress_title(String address_title) {
            this.address_title = address_title;
        }

        public String getAddress_address() {
            return address_address;
        }

        public void setAddress_address(String address_address) {
            this.address_address = address_address;
        }

        public String getAddress_status() {
            return address_status;
        }

        public void setAddress_status(String address_status) {
            this.address_status = address_status;
        }
    }
}
