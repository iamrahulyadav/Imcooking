package com.imcooking.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeFragment;

public class TestActivity extends AppCompatActivity {
    public static final int requestcode = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
    }
}
