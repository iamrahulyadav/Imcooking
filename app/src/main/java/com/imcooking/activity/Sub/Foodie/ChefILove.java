package com.imcooking.activity.Sub.Foodie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;

public class ChefILove extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ilove);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
    }
}
