package com.imcooking.activity.main.setup;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.ApiRequest.SignUp;
import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

public class SignUpActivity extends AppBaseActivity implements RadioGroup.OnCheckedChangeListener {

    LinearLayout linearLayout;
    TextView txtLogin;
    private EditText edt_uname, edt_email, edt_pass, edt_conf_pass;
    private RadioGroup radioGroup;
    private String radio = "";

    // Dialog
    private Dialog dialog;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_sign_up);

//        find id
        linearLayout = findViewById(R.id.activity_signup_inputlayout);
        txtLogin = findViewById(R.id.activity_signup_txtLogin);

        edt_uname = findViewById(R.id.activity_signup_edtUserName);
        edt_email = findViewById(R.id.activity_signup_edtEmail);
        edt_pass = findViewById(R.id.activity_signup_edtPass);
        edt_conf_pass = findViewById(R.id.activity_signup_edtConfirmPass);

        radioGroup = findViewById(R.id.signup_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);

//        set animation in layout
        Animation animationLayout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        linearLayout.startAnimation(animationLayout);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createMyDialog();
    }

    private void createMyDialog(){

        dialog = new Dialog(SignUpActivity.this);
        dialog.setContentView(R.layout.dialog_signup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        tv = dialog.findViewById(R.id.dialog_signup_text);
    }

    public void register(View view) {

        String uname = edt_uname.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String pass = edt_pass.getText().toString().trim();
        String conf_pass = edt_conf_pass.getText().toString().trim();
        String selected = radio.trim();

        if(!selected.equals("")) {

            if (!uname.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !conf_pass.isEmpty()) {

                if (BaseClass.checkemail(email)) {

                    if (pass.equals(conf_pass)) {

                        String str = "";
                        if(selected.equals("Chef")) str = "1";
                        else str = "2";
                        SignUp data = new SignUp();
                        data.setUser_type(str);
                        data.setUser_name(uname);
                        data.setEmail(email);
                        data.setPassword(pass);
                        data.setConfirmpassword(conf_pass);

                        new GetData(getApplicationContext(), SignUpActivity.this)
                                .getResponse(new Gson().toJson(data), GetData.SIGNUP,
                                new GetData.MyCallback() {
                                    @Override
                                    public void onSuccess(String result) {

                                        final String response = result;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);
//                                                tv.setText(apiResponse.getMsg());
                                                if (apiResponse.isStatus()) {
                                                    if (apiResponse.getMsg().equals("Foodie successfully registered and sent verification code your email!")) {
                                                        edt_uname.setText("");
                                                        edt_email.setText("");
                                                        edt_pass.setText("");
                                                        edt_conf_pass.setText("");
                                                        tv.setText("You have been successfully registered as Foodie with IM Cooking.\n An email with verification code has been sent to your registered email id. Please verify after login.");
//                                                        dialog.show();
                                                    } else if (apiResponse.getMsg().equals("Chef successfully registered and sent verification code your email!")){
                                                        edt_uname.setText("");
                                                        edt_email.setText("");
                                                        edt_pass.setText("");
                                                        edt_conf_pass.setText("");
                                                        tv.setText("You have been successfully registered as Chef with IM Cooking.\n An email with verification code has been sent to your registered email id. Please verify after login.");
                                                      }
                                                    else {
                                                        BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                                    }
                                                } else {
                                                    if (apiResponse.getMsg().equals("User name already exist")){
                                                        tv.setText("Username already exists");
                                                       // BaseClass.showToast(getApplicationContext(), "Username already exists.");
                                                    } else if (apiResponse.getMsg().equals("Email already exist")){
                                                        tv.setText("Email already exists");
                                                    }

//                                                    dialog.show();
                                                }
                                                dialog.show();
                                            }
                                        });
                                    }
                                });
                    } else {
                        BaseClass.showToast(getApplicationContext(), "Password does not match.");
                        edt_pass.setText("");
                        edt_conf_pass.setText("");
                    }
                } else {
                    BaseClass.showToast(getApplicationContext(), "Please Enter a Valid Email.");
                    edt_email.setText("");
                }
            } else {
                BaseClass.showToast(getApplicationContext(), "All fields are required.");
            }
        } else {
            BaseClass.showToast(getApplicationContext(), "Please select an option.");
        }
    }

    public void register_okay(View view){
            dialog.dismiss();
            finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        RadioButton radioButton = findViewById(checkedId);
        radio = radioButton.getText().toString();
//        BaseClass.showToast(getApplicationContext(),radioButton.getText().toString());
    }
}


