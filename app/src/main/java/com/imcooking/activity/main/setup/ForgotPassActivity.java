package com.imcooking.activity.main.setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;

public class ForgotPassActivity extends AppBaseActivity {

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

    }

    public void forgot_to_login(View view){
        finish();
    }
}
