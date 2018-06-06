package com.imcooking.Model.api.response;

public class FollowUnfollow {
    /**
     * status : true
     * key : 0
     * msg : Successfully follow
     */

    private boolean status;
    private String key;
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
