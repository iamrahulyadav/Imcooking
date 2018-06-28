package com.imcooking.activity.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.FavoriteCusine;
import com.imcooking.activity.Sub.Foodie.Help;
import com.imcooking.activity.Sub.Foodie.ManageAddress;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.chef.ChefMyOrderListFragment;
import com.imcooking.fragment.chef.chefprofile.ChefMyRequestFragment;
import com.imcooking.fragment.foodie.FoodieMyRequestFragment;
import com.imcooking.fragment.foodie.HomeFragment;

import com.imcooking.fragment.foodie.FoodieMyOrderFragment;
import com.imcooking.fragment.foodie.NotificationFragment;
import com.imcooking.fragment.foodie.ProfileFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.mukesh.tinydb.TinyDB;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private float lastTranslate = 0.0f;
    private LinearLayout frame_view;
    //    public static Toolbar toolbar;
    public static DrawerLayout drawerLayout1;
    public NavigationView navigationView;
    TextView txtChefUserName, txtMobile;
    private TinyDB tinyDB;
    private TextView tv_name, tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout1 = findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        frame_view = (LinearLayout) findViewById(R.id.frame_view);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        tv_name = headerLayout.findViewById(R.id.nav_haeder_txtName);
        tv_phone = headerLayout.findViewById(R.id.nav_header_phone);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (navigationView.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    frame_view.setTranslationX(moveFactor);
                } else {
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
        getUserData();
        if (user_type.equals("1")) {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                String tag1 = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                if (tag1.equals(ChefHome.class.getName())) {

                } else {

                    ChefHome fragment = new ChefHome();

                    Bundle args = new Bundle();
                    args.putString("chef_id", user_id);
                    args.putString("foodie_id", "4");

                    fragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
                }
            } else {
                ChefHome fragment = new ChefHome();

                Bundle args = new Bundle();
                args.putString("chef_id", user_id);
                args.putString("foodie_id", "4");

                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

            }
//            BaseClass.callFragment(new ChefHome(), ChefHome.class.getName(), getSupportFragmentManager());
        } else if (getIntent().hasExtra("pay")) {
            BaseClass.callFragment(new FoodieMyOrderFragment(), FoodieMyOrderFragment.class.getName(), getSupportFragmentManager());
        } else { // 2
            BaseClass.callFragment(new HomeFragment(), HomeFragment.class.getName(), getSupportFragmentManager());
        }
    }

    public static ImageView iv_home, iv_profile, iv_my_order, iv_notification;
    public static TextView tv_home, tv_profile, tv_my_order, tv_notification;

    private String loginData;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private String user_type, user_id;

    private void init() {

        tinyDB = new TinyDB(getApplicationContext());

        iv_home = findViewById(R.id.bottom_home_image);
        iv_profile = findViewById(R.id.bottom_profile_image);
        iv_my_order = findViewById(R.id.bottom_my_order_image);
        iv_notification = findViewById(R.id.bottom_notification_image);

        tv_home = findViewById(R.id.bottom_home_text);
        tv_profile = findViewById(R.id.bottom_profile_text);
        tv_my_order = findViewById(R.id.bottom_my_order_text);
        tv_notification = findViewById(R.id.bottom_notification_text);
    }

    private String user_name, user_phone;

    private void getUserData() {

        loginData = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();
        user_id = userDataBean.getUser_id() + "";
        if (userDataBean.getFull_name()!=null)
            user_name = userDataBean.getFull_name() + "";
        else user_name="Your Name";
        if (user_type.equals("1")) navigationView.getMenu().findItem(R.id.navigation_address).setVisible(false);

        if (userDataBean.getUser_phone()!=null)
            user_phone = userDataBean.getUser_phone() + "";
        else user_phone="XXXXXXXXXX";

        tv_name.setText(user_name);
        tv_phone.setText(user_phone);
    }

    public void bottom_click(View view) {

        int id = view.getId();
        setBottomColor();

        if (id == R.id.bottom_home_layout){
            tv_home.setTextColor(getResources().getColor(R.color.theme_color));
            iv_home.setImageResource(R.drawable.ic_home_1);
            if(user_type.equals("2")) {
                BaseClass.callFragment(new HomeFragment(), new HomeFragment().getClass().getName(), getSupportFragmentManager());
            } else{
                if(getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    String tag1 = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                    if (tag1.equals(ChefHome.class.getName())) {

                    } else {

                        ChefHome fragment = new ChefHome();

                        Bundle args = new Bundle();
                        args.putString("chef_id", user_id);
                        args.putString("foodie_id", "4");

                        fragment.setArguments(args);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame, fragment)
                                .addToBackStack(ChefHome.class.getName()).commit();
                    }
                } else{
             /*       ChefHome fragment = new ChefHome();

                    Bundle args = new Bundle();
                    args.putString("chef_id", user_id);
                    args.putString("foodie_id", "4");

                    fragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
*/
                }
            }
        } else if (id == R.id.bottom_profile_layout){
            tv_profile.setTextColor(getResources().getColor(R.color.theme_color));
            iv_profile.setImageResource(R.drawable.ic_user_name_1);

            if(user_type.equals("2")) {
                BaseClass.callFragment(new ProfileFragment(), new ProfileFragment().getClass().getName(), getSupportFragmentManager());
            } else{
                if(getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    String tag1 = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                    if (tag1.equals(ChefHome.class.getName())) {

                    } else {

                        ChefHome fragment = new ChefHome();

                        Bundle args = new Bundle();
                        args.putString("chef_id", user_id);
                        args.putString("foodie_id", "4");

                        fragment.setArguments(args);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame, fragment)
                                .addToBackStack(ChefHome.class.getName())
                                .commit();
                    }
                }/* else{
                    ChefHome fragment = new ChefHome();

                    Bundle args = new Bundle();
                    args.putString("chef_id", user_id);
                    args.putString("foodie_id", "4");

                    fragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

                }*/
            }
        } else if (id == R.id.bottom_my_order_layout){
            tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
            iv_my_order.setImageResource(R.drawable.ic_salad_1);
            if(user_type.equals("2")) {
                BaseClass.callFragment(new FoodieMyOrderFragment(), new FoodieMyOrderFragment().getClass().getName(), getSupportFragmentManager());
            }else if(user_type.equals("1")){
                BaseClass.callFragment(new ChefMyOrderListFragment(), new ChefMyOrderListFragment().getClass().getName(),
                        getSupportFragmentManager());
            }
        } else if (id == R.id.bottom_notification_layout){
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
/*
        Bitmap bitmap = BaseClass.getBitmapFromURL1("S");

        String s = BaseClass.BitMapToString(bitmap);
        Log.d("MyBase64", s);*/
    }

    public void open_menu(View view){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }
    @Override
    public void onBackPressed() {

        i = getSupportFragmentManager().getBackStackEntryCount();
//        Toast.makeText(getApplicationContext(), i+"", Toast.LENGTH_SHORT).show();
        String tag1 = "";
        if(i !=0) {
            tag1 = getSupportFragmentManager().getBackStackEntryAt(i- 1).getName();
        } else {
//            tag1 = getSupportFragmentManager().getBackStackEntryAt(i).getName();
        }
        Log.d("MyTag", tag1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finishAffinity();
//            finish();
        } else if(tag1.equals(HomeFragment.class.getName()) || tag1.equals(ChefHome.class.getName())){
            getSupportFragmentManager().popBackStack();
            finishAffinity();
//            finish();
        } else{
            super.onBackPressed();
        }
    }

    public static int i;
    public static String my_tag;

    @Override
    protected void onStop() {
        super.onStop();

        i = getSupportFragmentManager().getBackStackEntryCount();
//        Toast.makeText(getApplicationContext(), i+"", Toast.LENGTH_SHORT).show();
        if(i !=0)
            my_tag = getSupportFragmentManager().getBackStackEntryAt(i-1).getName();
//        Toast.makeText(this, my_tag, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.navigation_logout:
                new TinyDB(getApplicationContext()).remove("login_data");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.navigation_myrequest:
                if(user_type.equals("2")) {
                    BaseClass.callFragment(new FoodieMyRequestFragment(), FoodieMyRequestFragment.class.getName(), getSupportFragmentManager());
                }
                else if(user_type.equals("1")){
                    Bundle bundle=new Bundle();
                    bundle.putString("message", "drawer");
                    //set Fragmentclass Arguments
                    ChefMyRequestFragment fragobj=new ChefMyRequestFragment();
                    fragobj.setArguments(bundle);
                    BaseClass.callFragment(fragobj, ChefMyRequestFragment.class.getName(),
                            getSupportFragmentManager());
                }

                break;
            case R.id.navigation_cuisine:
                if(user_type.equals("2")) {
                    startActivity(new Intent(MainActivity.this, FavoriteCusine.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    startActivity(new Intent(MainActivity.this, FavoriteCusine.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }

                break;
            case R.id.navigation_myorder:
                if(user_type.equals("2")) {
                    BaseClass.callFragment(new FoodieMyOrderFragment(), FoodieMyOrderFragment.class.getName(),
                            getSupportFragmentManager());
                }
                else if(user_type.equals("1")){
                    BaseClass.callFragment(new ChefMyOrderListFragment(), ChefMyOrderListFragment.class.getName(),
                            getSupportFragmentManager());
                } else {}
                break;

            case R.id.navigation_help:
                startActivity(new Intent(getApplicationContext(), Help.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.navigation_address:
                startActivity(new Intent(getApplicationContext(), ManageAddress.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            default:
                break;
                }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}