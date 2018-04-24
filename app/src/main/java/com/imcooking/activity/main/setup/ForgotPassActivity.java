package com.imcooking.activity.main.setup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.ApiResponse;
import com.imcooking.Model.ApiRequest.ForgotPassword;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

public class ForgotPassActivity extends AppBaseActivity {

    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_pass);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_forgot_pass);
        Animation animationLayout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        layout.startAnimation(animationLayout);


        edt = findViewById(R.id.forgot_pass_email);
    }

    public void forgot_to_login(View view){
        finish();
    }

    public void reset_my_password(View view){

        String str = edt.getText().toString().trim();

        if(!str.isEmpty()){

            ForgotPassword data = new ForgotPassword();
            data.setEmail(str);

            new GetData(getApplicationContext(), ForgotPassActivity.this).getResponse(new Gson().toJson(data), "forgotpassword",
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            final String response = result;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);

                                    if (apiResponse.isStatus()) {
                                        if (apiResponse.getMsg().equals("Your password send to your email")) {
                                            BaseClass.showToast(getApplicationContext(), "Your password has been sent to your email id. Please login again.");
                                            finish(); // startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                                        } else {
                                            BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                        }
                                    } else {
                                        if (apiResponse.getMsg().equals("Email id does not exists")) {
                                            BaseClass.showToast(getApplicationContext(), "Please enter your registered email id.");
                                        } else {
                                            BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                        }
                                    }
                                    edt.setText("");
                                }
                            });
                        }
                    });
        }
        else {
            BaseClass.showToast(getApplicationContext(), "Please Enter your registered Email Address");
        }
    }
}
