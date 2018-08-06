package com.imcooking.activity.Sub.Foodie;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class Setting extends AppBaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BaseClass.setLightStatusBar(getWindow().getDecorView(),Setting.this);

        init();
    }

    private EditText edt_miles;
    private SwitchCompat sw_notification;
    private String str_notification;

    private void init(){

        edt_miles = findViewById(R.id.foodie_settings_miles);
        sw_notification = findViewById(R.id.foodie_settings_notification);
        sw_notification.setOnCheckedChangeListener(this);
        getMyData();
    }

    private void getMyData(){
        str_notification = "1";
        sw_notification.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(compoundButton.getId() == R.id.foodie_settings_notification){

            if(b){
                str_notification = "1";
            } else {
                str_notification = "0";
            }
        } else{}
    }

    public void foodie_settings_update(View view){

    }
}
