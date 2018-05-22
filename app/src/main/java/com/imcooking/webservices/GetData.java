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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.imcooking.activity.main.setup.SignUpActivity;
import com.imcooking.utils.BaseClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;

/**
 * Created by vaibhav on 2/21/2017.
 */
public class GetData {

    private Context context;
    private Activity activity;
    public GetData(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
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