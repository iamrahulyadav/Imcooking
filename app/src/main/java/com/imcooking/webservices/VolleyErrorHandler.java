package com.imcooking.webservices;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.imcooking.utils.BaseClass;

public class VolleyErrorHandler {


    public static void networkErrorHandler(VolleyError error, Activity context) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            BaseClass.showToast(context.getApplicationContext(), "Please Check our Internet Connection");
        } else if (error instanceof AuthFailureError) {
            BaseClass.showToast(context.getApplicationContext(), "Server Error1");
            //TODO
        } else if (error instanceof ServerError) {
            BaseClass.showToast(context.getApplicationContext(), "Server Error2");
            //TODO
        } else if (error instanceof NetworkError) {
            BaseClass.showToast(context.getApplicationContext(), "Please Check our Internet Connection");
            //TODO
        } else if (error instanceof ParseError) {
            BaseClass.showToast(context.getApplicationContext(), "Server Error3");
            //TODO
        }

    }
}
