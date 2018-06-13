package com.imcooking.activity.Sub.Foodie;

import android.os.Bundle;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class Setting extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        BaseClass.setLightStatusBar(getWindow().getDecorView(),Setting.this);

    }
}
