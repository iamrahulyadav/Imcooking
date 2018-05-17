package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;

public class EditProfile extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


    }

    private void init(){

    }

    private void getPorfile(){
        new GetData(getApplicationContext(), EditProfile.this).getResponse("", "profile", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {

            }
        });
    }
}
