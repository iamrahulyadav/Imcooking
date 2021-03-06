package com.imcooking.activity.main.setup;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.ApiRequest.Login;
import com.imcooking.Model.ApiRequest.Verification;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.notification.Config;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout layout;
    TextView txtRegister, txtLogin;
    private EditText edt_uname, edt_pass;
    private Dialog dialog;
    private EditText edt_passcode;
    private String user_id;
    private TinyDB tinyDB ;
    private String device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }

        // Get Device Id From Shared Prefwrences
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
//        if(regId != null)

        if (!TextUtils.isEmpty(regId)) {
//            txtRegId.setText("Firebase Reg Id: " + regId);
            device_id = regId;
        }
        else {
//            txtRegId.setText("Firebase Reg Id is not received yet!");
            device_id = "123";
        }

        Log.d("MyDeviceId", device_id + "");

        tinyDB = new TinyDB(getApplicationContext());

//        find id
        txtRegister = findViewById(R.id.activity_login_txtregister);
        layout = findViewById(R.id.activity_login_inputlayout);
        txtLogin = findViewById(R.id.activity_login_txtlogin);

        edt_uname = findViewById(R.id.activity_login_edtUserName);
        edt_pass = findViewById(R.id.activity_login_edtPass);

//        set click listener
        txtLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);

        //        set animation in layout
        Animation animationLayout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        layout.startAnimation(animationLayout);

//        set click listener
        txtLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);

        createMyDialog();

    }

    private void createMyDialog() {

        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_verification);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        edt_passcode = dialog.findViewById(R.id.dialog_verification_edit);
    }

    public void dialog_verification_cancel(View view){
        dialog.dismiss();
    }

    public void forgot_password(View view){
        startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_login_txtregister:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.activity_login_txtlogin:

                login();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            default:
                break;
        }
    }

    private void login(){
        String uname = edt_uname.getText().toString().trim();
        String pass = edt_pass.getText().toString().trim();

        if(!uname.isEmpty() && !pass.isEmpty()) {

            Login data = new Login();
            data.setUser_name(uname);
            data.setPassword(pass);
            data.setDevice_id(device_id);
//            data.setDevice_id("cM7WiSvFCvI:APA91bHrXcZOzGoxDKT7ksLche1KAzgxStLCtgyUjD3GiXBchJPp4p0qsOG67M3KkPkvcK4OKbuvjhqHCP8CrW8UlVfI548etzPkXQu1w1tZH0IVchq23yDZ-BP13XvtjWo5yLQ-RR2hC6IHVk3Mn7AbzQPAFOqj8Q");

            Log.d("MyRequest", new Gson().toJson(data));

            new GetData(getApplicationContext(), LoginActivity.this).getResponse(new Gson().toJson(data),
                    GetData.LOGIN, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    //           Log.d("Show Response", response);

                    final String response = result;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);

                            if (apiResponse.isStatus()) {
                                if (apiResponse.getMsg().equals("Successfully login")) {

                                    tinyDB.putString("login_data",new Gson().toJson(apiResponse.getUser_data()));
                                 //   BaseClass.showToast(getApplicationContext(), "Login Successfull");
                                    String loginData = tinyDB.getString("login_data");
                                    Log.d("LoginData", loginData);

                                    edt_uname.setText("");
                                    edt_pass.setText("");

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                } else {
                                    BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                }
                            } else {
                                if (apiResponse.getMsg().equals("User name and password wrong")) {

                                    BaseClass.showToast(getApplicationContext(), "Wrong User Name and Password");
                                }else if (apiResponse.getMsg().equals("Passcode Required")) {
                                    dialog.show();
                                    user_id = apiResponse.getUser_data().getUser_id() + "";
                                    dialog.findViewById(R.id.wrong_passcode).setVisibility(View.GONE);
                                } else if (apiResponse.getMsg().equals("Your account is yet not verfied by Admin, please contact app admin.")){
                                    edt_pass.setText("");
                                    edt_uname.setText("");
                                    Toast.makeText(LoginActivity.this, "Your account is yet not verfied by Admin, please contact app admin.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            });
        } else{
            BaseClass.showToast(getApplicationContext(), "All fields are required.");
        }
    }

    public void verify_user(View view){

        String passcode = edt_passcode.getText().toString().trim();

        if(!passcode.isEmpty()) {
            Verification data = new Verification();
            data.setUser_id(user_id);
            data.setPasscode(passcode);
            new GetData(getApplicationContext(), LoginActivity.this).getResponse(new Gson().toJson(data),
                    "varification", new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    //           Log.d("Show Response", response);

                    final String response = result;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);

                            Log.d("ShowResponse", apiResponse.isStatus() + "");
                            Log.d("ShowResponse", apiResponse.getMsg());

                            if (apiResponse.isStatus()) {
                                if (apiResponse.getMsg().equals("Successfully varification")) {
                                    BaseClass.showToast(getApplicationContext(), "Login Successfull");
                                    tinyDB.putString("login_data",new Gson().toJson(apiResponse.getUser_data()));
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                }
                            } else {
                                if (apiResponse.getMsg().equals("Sorry, the passcode you provided is not correct, to check your passcode please check your registered email.")) {
                                  //  BaseClass.showToast(getApplicationContext(), apiResponse.getMsg());
                                    dialog.findViewById(R.id.wrong_passcode).setVisibility(View.VISIBLE);
                                } else {
                                    BaseClass.showToast(getApplicationContext(), apiResponse.getMsg()  );
                                }
                            }
                        }
                    });
                }
            });
        } else{
            BaseClass.showToast(getApplicationContext(), "All fields are required.");
        }
    }

}