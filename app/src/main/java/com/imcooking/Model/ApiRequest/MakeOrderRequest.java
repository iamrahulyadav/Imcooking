package com.imcooking.Model.ApiRequest;

import java.util.List;

public class MakeOrderRequest {

    /**
     * chef_id : 7
     * foodie_id : 2
     * dishorder : [{"dish_id":"2","quantity":"2","price":"100"},{"dish_id":"2","quantity":"2","price":"100"}]
     * delivery_type : 2
     * tax : 2.00
     * total_price : 200
     * address : 2
     * from_time : 245
     * to_time : 2
     * payment_type : cod
     * transaction_id : 45645645456
     * bookdate : 1
     */

    private String chef_id;
    private String foodie_id;
    private String delivery_type;
    private String tax;
    private String total_price;
    private String address;
    private String from_time;
    private String to_time;
    private String payment_type;
    private String transaction_id;
    private String bookdate;
    private List<DishorderBean> dishorder;

    public String getChef_id() {
        return chef_id;
    }

    public void setChef_id(String chef_id) {
        this.chef_id = chef_id;
    }

    public String getFoodie_id() {
        return foodie_id;
    }

    public void setFoodie_id(String foodie_id) {
        this.foodie_id = foodie_id;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public List<DishorderBean> getDishorder() {
        return dishorder;
    }

    public void setDishorder(List<DishorderBean> dishorder) {
        this.dishorder = dishorder;
    }

    public static class DishorderBean {
        /**
         * dish_id : 2
         * quantity : 2
         * price : 100
         */

        private String dish_id;
        private String quantity;
        private String price;

        public String getDish_id() {
            return dish_id;
        }

        public void setDish_id(String dish_id) {
            this.dish_id = dish_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
