package com.imcooking.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.fragment.HomeFragment;
import com.imcooking.fragment.MyOrderFragment;
import com.imcooking.fragment.NotificationFragment;
import com.imcooking.fragment.ProfileFragment;
import com.imcooking.utils.BaseClass;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout frame;
    private float lastTranslate = 0.0f;
    private LinearLayout frame_view;
//    public static Toolbar toolbar;
    public static DrawerLayout drawerLayout1;
//    public static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout1 = findViewById(R.id.drawer_layout);
/*
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
/*
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout toolbar_left = toolbar.findViewById(R.id.toolbar_left);
        toolbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
*/

//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        frame_view = (LinearLayout) findViewById(R.id.frame_view);
        frame = (FrameLayout) findViewById(R.id.frame);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close){


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (navigationView.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    frame_view.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frame_view.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }
        };


        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        init();
        BaseClass.callFragment(new HomeFragment(), new HomeFragment().getClass().getName(), getSupportFragmentManager());
    }

    public static ImageView iv_home, iv_profile, iv_my_order, iv_notification;
    public static TextView tv_home, tv_profile, tv_my_order, tv_notification;

    private void init(){

        iv_home = findViewById(R.id.bottom_home_image);
        iv_profile = findViewById(R.id.bottom_profile_image);
        iv_my_order = findViewById(R.id.bottom_my_order_image);
        iv_notification = findViewById(R.id.bottom_notification_image);

        tv_home = findViewById(R.id.bottom_home_text);
        tv_profile = findViewById(R.id.bottom_profile_text);
        tv_my_order = findViewById(R.id.bottom_my_order_text);
        tv_notification = findViewById(R.id.bottom_notification_text);
    }


    public void bottom_click(View view) {

        int id = view.getId();
        setBottomColor();

        if (id ==R.id.bottom_home_layout){
            tv_home.setTextColor(getResources().getColor(R.color.theme_color));
            iv_home.setImageResource(R.drawable.ic_home_1);
            BaseClass.callFragment(new HomeFragment(), new HomeFragment().getClass().getName(), getSupportFragmentManager());

        } else if (id ==R.id.bottom_profile_layout){
            tv_profile.setTextColor(getResources().getColor(R.color.theme_color));
            iv_profile.setImageResource(R.drawable.ic_user_name_1);
            BaseClass.callFragment(new ProfileFragment(), new ProfileFragment().getClass().getName(), getSupportFragmentManager());

        } else if (id ==R.id.bottom_my_order_layout){
            tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
            iv_my_order.setImageResource(R.drawable.ic_salad_1);
            BaseClass.callFragment(new MyOrderFragment(), new MyOrderFragment().getClass().getName(), getSupportFragmentManager());

        } else if (id ==R.id.bottom_notification_layout){
            tv_notification.setTextColor(getResources().getColor(R.color.theme_color));
            iv_notification.setImageResource(R.drawable.ic_ring_1);
            BaseClass.callFragment(new NotificationFragment(), new NotificationFragment().getClass().getName(), getSupportFragmentManager());

        } else{

        }
    }

    public void setBottomColor(){
        tv_home.setTextColor(getResources().getColor(R.color.text_color_10));
        iv_home.setImageResource(R.drawable.ic_home);

        tv_profile.setTextColor(getResources().getColor(R.color.text_color_10));
        iv_profile.setImageResource(R.drawable.ic_user_name);

        tv_my_order.setTextColor(getResources().getColor(R.color.text_color_10));
        iv_my_order.setImageResource(R.drawable.ic_salad);

        tv_notification.setTextColor(getResources().getColor(R.color.text_color_10));
        iv_notification.setImageResource(R.drawable.ic_ring);

    }


    @Override
    protected void onResume() {
        super.onResume();

//        tv_home.setTextColor(getResources().getColor(R.color.theme_color));
//        iv_home.setImageResource(R.drawable.ic_home_1);
    }

    public void open_menu(View view){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finishAffinity();
        }else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
