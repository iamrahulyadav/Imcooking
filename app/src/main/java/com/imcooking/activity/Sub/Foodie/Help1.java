package com.imcooking.activity.Sub.Foodie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class Help1 extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);
        BaseClass.setLightStatusBar(getWindow().getDecorView(),Help1.this);


    }
}
