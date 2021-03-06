package com.imcooking.activity.Sub.Chef;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.Login;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {
EditText etOldpass,etNewpass,etConfirmpass;
TextView txtChangepass;
TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        tinyDB = new TinyDB(getApplicationContext());
        etOldpass=findViewById(R.id.activity_change_password_et_oldpass);
        etNewpass=findViewById(R.id.activity_change_password_et_newpass);
        etConfirmpass=findViewById(R.id.activity_change_password_et_confirm);

        txtChangepass=findViewById(R.id.activity_change_password_tv_changepass);
        txtChangepass.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        try {
            changepassword();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        });

    }

    private void changepassword() throws JSONException {
        String oldpass = etOldpass.getText().toString().trim();
        String newpass = etNewpass.getText().toString().trim();
        String confirmpass = etConfirmpass.getText().toString().trim();
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        String login = tinyDB.getString("login_data");
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);

        String user_id=apiResponse.getUser_id()+"";

        if(!oldpass.isEmpty() && !newpass.isEmpty() && !confirmpass.isEmpty()) {

            String s = "{\"oldpassword\":\"" +oldpass+ "\",\"new_password\":"+newpass+"," +
                    "\"confirmpassword\":"+confirmpass+",\"user_id\":\""+user_id+"\"}" ;

//                   JSONObject jsonObject = new JSONObject(s);
            Log.d("ShowResponse", s);


            new GetData(getApplicationContext(), ChangePassword.this)
                    .getResponse(s, GetData.CHANGE_PASS, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                               Log.d("ShowResponseresult", result);

                    final String response = result;
                    final ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);

                    Log.d("ShowResponse", apiResponse.isStatus() + "");
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Log.d("ShowResponse", apiResponse.getMsg());
                           if (apiResponse.isStatus()) {
                               if (apiResponse.getMsg().equals("Successfully change password")) {
                                   refreshText();
                                   BaseClass.showToast(getApplicationContext(), "Password Successfully changed");
                               } else {
                                   BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                               }
                           } else {
                                   BaseClass.showToast(getApplicationContext(), apiResponse.getMsg());
                           }
                       }
                   });
                }
            });
        } else{
            BaseClass.showToast(getApplicationContext(), "All fields are required.");
        }
    }

    private void refreshText(){
        etConfirmpass.setText("");
        etNewpass.setText("");
        etOldpass.setText("");
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}