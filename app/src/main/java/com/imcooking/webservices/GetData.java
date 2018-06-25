package com.imcooking.webservices;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.imcooking.R;
import com.imcooking.activity.main.setup.SignUpActivity;
import com.imcooking.utils.BaseClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import retrofit2.http.POST;

/**
 * Created by vaibhav on 2/21/2017.
 */
public class GetData {

    private Context context;
    private Activity activity;
//...................................  API ...................................

    public static String PROFILE_IMAGE="profileimage";
    public static String GETPROFILE_PIC ="get_user_profileimage";
    public static String ADD_CART = "addcart";
    public static String DISH_LIKE= "dishlike";
    public static String DISH_DETAILS = "dishdetails";
    public static String DISH_CHEF = "dishchef";
    public static String CART_SELECTED_QTY = "cart_quantity_save";
    public static String CHEF_DETAILS = "chefdetails";
    public static String PLACE_ORDER = "place_order";
    public static String ORDER_DETAILS = "order_detail";
    public static String CHEF_ACCEPT_REQUEST ="chef_accept_request";
    public static String ADDRESS = "address";
    public static String DISH_REQUEST_LIST = "DishRequestList";
    public static String CHEF_ORDER_LIST = "chef_order_list";
    public static String CHEF_MY_CUISINE_LIST = "chef_my_cuisine_list";
    public static String CONVERSATION_CHAT ="conversation_chat";
    public static String REQUEST_DISH = "request_dish";
    public static String CONVER_CHEF_FOODIE_DISH_ACCEPT  ="conversation_chat_chef_foofie";


    public GetData(final Context context, Activity activity) {
/*
        Map<String, String> jsonData = new HashMap<String, String>();
        jsonData.put("user_type","2");
        jsonData.put("foodie_id","104");
        jsonData.put("name","gg");
        jsonData.put("email","kh.vaibhav10@gmail.com");
        jsonData.put("phone","566909900");
        Log.d("data:",jsonData.toString());


        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://webdevelopmentreviews.net/imcooking/api/foodieprofileedit",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
Toast.makeText(context,"......."+response,Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                      //  VolleyErrorHandler.networkErrorHandler(error);
                    }
                }){

                protected Map<String, String> getParams() {
                Map<String, String> jsonData = new HashMap<String, String>();
                    jsonData.put("user_type","2");
                    jsonData.put("foodie_id","104");
                    jsonData.put("name","gg");
                    jsonData.put("email","kh.vaibhav10@gmail.com");
                    jsonData.put("phone","566909900");
                    Log.d("data:",jsonData.toString());
                    return jsonData;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
*/
        this.context = context;
        this.activity = activity;
    }

    public JSONObject getResponseJson() {
        return responseJson;
    }

    public   JSONObject responseJson = new JSONObject();
    public String str = "";

    public String sendMyData(JSONObject jsonObject, String url, final Activity activity, final MyCallback callback){
        if(BaseClass.isNetworkConnected(context)) {

            final ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            String myurl = BASE_URL + url;
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, myurl
                    , jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                            progressDialog.hide();
                            Log.d("CallBack", jsonObject.toString());

                            str = jsonObject.toString();
                            if (jsonObject.has("status"))

                                callback.onSuccess(str);

                            else
                                BaseClass.showToast(context, context.getResources().getString(R.string.error));

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    progressDialog.hide();
                    Log.d("TAG", "onErrorResponse:sta " + volleyError);
                    VolleyErrorHandler.networkErrorHandler(volleyError, activity);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(activity);

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjReq);

        }
        return str;

    }


    public GetData(Context context){
        this.context = context;
    }

    public static final MediaType JSON = MediaType.parse("application/json");
    public final static String BASE_URL = "http://webdevelopmentreviews.net/imcooking/api/";
    public final static String IMG_BASE_URL = "http://webdevelopmentreviews.net/imcooking/upload/";

    public void getResponse(String jsonString, String api_name, final MyCallback callback) {

        if(BaseClass.isNetworkConnected(context)) {

            final ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(JSON, jsonString);

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(BASE_URL + api_name)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .readTimeout(30000, TimeUnit.SECONDS)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, final IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    progressDialog.dismiss();

                    if (response != null && response.body().toString().length() > 0) {
                        if (request.body() != null) {
                            String msg = response.body().string();
                            Log.d("CallBack", msg);
                            try {
                                JSONObject jsonObject  = new JSONObject(msg);
                                if (jsonObject.has("status")){
                                    callback.onSuccess(msg);
                                } else {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("TAG", "run: "+"Server error ");
                                          //  Toast.makeText(context, "Server error ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } else{
//            View v = activity.findViewById(android.R.id.content);
            BaseClass.no_internet(activity, context);
        }
    }

    public void getResponseAdap(String jsonString, String api_name, final MyCallback callback) {

        if(BaseClass.isNetworkConnected(context)) {

            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(JSON, jsonString);

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(BASE_URL + api_name)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .readTimeout(30000, TimeUnit.SECONDS)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, final IOException e) {

                    ((SignUpActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            String msg = e.getMessage();

                            BaseClass.showToast(context, "No Internet");
//                        Utility.message(getApplicationContext(), getResources().getString(R.string.no_internet_connection));
                        }
                    });
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    progressDialog.dismiss();

                    if (response != null && response.body().toString().length() > 0) {
                        if (request.body() != null) {
                            String msg = response.body().string();
                            Log.d("CallBack", msg);
                            try {
                                JSONObject jsonObject  = new JSONObject(msg);
                                if (jsonObject.has("status")){
                                    callback.onSuccess(msg);
                                } else {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("TAG", "run: "+"Server error ");
                                            //  Toast.makeText(context, "Server error ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } else{
//            View v = activity.findViewById(android.R.id.content);
            /*BaseClass.no_internet(activity, context);*/
        }
    }

    public interface MyCallback {
        void onSuccess(String result);
    }
}