package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppBaseActivity {

    private JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        init();
        getUserData();
    }

    private TextView tv_fullname, tv_email, tv_phone, btn;
    private EditText edt_full_name, edt_email, edt_phone;
    private String str_id, str_uname;

    private void init(){

        tv_fullname = findViewById(R.id.foodie_edit_profile_full_name);
        tv_email = findViewById(R.id.foodie_edit_profile_email);
        tv_phone = findViewById(R.id.foodie_edit_profile_phone);


        edt_full_name = findViewById(R.id.foodie_edit_profile_full_name_edit);
        edt_phone = findViewById(R.id.foodie_edit_profile_phone_edit);
        edt_email = findViewById(R.id.foodie_edit_profile_email_edit);
    }

    private void getUserData(){

        TinyDB tinyDB = new TinyDB(getApplicationContext());

        String login_data = tinyDB.getString("login_data");
        Log.d("LoginData", login_data);
        ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);

        String str_full_name, str_phone, str_email, dp;

        str_full_name = userDataBean.getFull_name() + "";
        str_phone = userDataBean.getUser_phone() + "";
        str_email = userDataBean.getUser_email() + "";
        str_id = userDataBean.getUser_id() + "";
        str_uname = userDataBean.getUser_name();


        tv_fullname.setText(str_full_name);
        if(str_full_name.equals("null")){
            tv_fullname.setText("Your Name");
        } else{
            tv_fullname.setText(str_full_name);
        }

        tv_phone.setText(str_phone);
        if(userDataBean.getUser_phone() == null){
            tv_phone.setText("9999999999");
        } else{
            tv_phone.setText(str_phone);
        }

        tv_email.setText(str_email);
        edt_email.setText(str_email);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
         }
    }
    JSONObject jsonObject;

    public void foodie_profile_update(View view){

        final String name = edt_full_name.getText().toString().trim();
        final String email = edt_email.getText().toString().trim();
        final String phone = edt_phone.getText().toString().trim();

        if(!name.isEmpty()) {
            if(!phone.isEmpty()){

                String s = "{\"user_type\":\"2\",\"foodie_id\":\"" + str_id + "\",\"name\":\"" + name + "\",\"email\":\"" +
                        email + "\", \"phone\":\"" + phone + "\"}";
                Log.d("MyRequest", s);

                try {
                    jsonData = new JSONObject(s);

                    new GetData(getApplicationContext()).sendMyData(jsonData, "foodieprofileedit",
                            EditProfile.this, new GetData.MyCallback() {
                                @Override
                                public void onSuccess(String result) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getBoolean("status")){

                                            BaseClass.showToast(getApplicationContext(), "Profile Updated Successfully");
                                            tv_phone.setText(phone);
                                            tv_fullname.setText(name);

                                            new TinyDB(getApplicationContext()).remove("login_data");

                                            ApiResponse.UserDataBean userData = new ApiResponse.UserDataBean();

                                            userData.setUser_type("2");
                                            userData.setFull_name(name);
                                            userData.setUser_email(email);
                                            userData.setUser_id(Integer.parseInt(str_id));
                                            userData.setUser_name(str_uname);
                                            userData.setUser_phone(phone);

                                            new TinyDB(getApplicationContext()).putString("login_data", new Gson().toJson(userData));
                                            String s = new TinyDB(getApplicationContext()).getString("login_data");
//                                            Toast.makeText(EditProfile.this, ""+s, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else{
                                            BaseClass.showToast(getApplicationContext(), "Something Went Wrong, Please Try Again Later");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else{
                BaseClass.showToast(getApplicationContext(), "Please Enter Phone Number");
            }
        } else{
            BaseClass.showToast(getApplicationContext(), "Please Enter Your Full Name");
        }
    }

    public void change_dp(View view){

    }

}
