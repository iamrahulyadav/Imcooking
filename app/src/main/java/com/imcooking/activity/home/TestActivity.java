package com.imcooking.activity.home;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imcooking.R;
import com.imcooking.fragment.foodie.TestFragment;

public class TestActivity extends AppCompatActivity {
    public static final int requestcode = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TestFragment()).commit();
    }
}
