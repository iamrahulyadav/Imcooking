package com.imcooking.splash;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.utils.AppBaseActivity;
import com.mukesh.tinydb.TinyDB;

public class SplashActivity extends AppBaseActivity {
    ImageView imgSplash, imgLogo;
    private Scene scene0;
    TinyDB tinyDB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        tinyDB = new TinyDB(getApplicationContext());

//        find id
        imgSplash = findViewById(R.id.splashbg);
        imgLogo = findViewById(R.id.splash_logo);

        Animation animationLogo = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.logo_animation);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_bottom);
        imgSplash.startAnimation(animation);
        imgLogo.startAnimation(animationLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tinyDB.contains("login_data")){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            }
        }, 3000);
    }


}
