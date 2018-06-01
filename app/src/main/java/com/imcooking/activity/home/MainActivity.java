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
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.chef.chefprofile.RequestDishFragment;
import com.imcooking.fragment.foodie.ChefMyOrderListFragment;
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
    private GoogleMap googleMap;
    MapView mMapView;
    public static StringBuffer stringBuffer  = new StringBuffer();
    Context mContext;
    private FrameLayout frame;
   public static double latitude, longitude;
    private float lastTranslate = 0.0f;
    private LinearLayout frame_view;
//    public static Toolbar toolbar;
    public static DrawerLayout drawerLayout1;
    public NavigationView navigationView;
    TextView txtChefUserName, txtMobile;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout1 = findViewById(R.id.drawer_layout);
        checkGPSStatus();

        mMapView = (MapView) findViewById(R.id.ride_now_mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    /*Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT).show();*/
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
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
            ChefHome fragment = new ChefHome();

            Bundle args = new Bundle();
            args.putString("chef_id", user_id);
            args.putString("foodie_id", "4");

            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

//            BaseClass.callFragment(new ChefHome(), ChefHome.class.getName(), getSupportFragmentManager());
        } else { // 2
            BaseClass.callFragment(new HomeFragment(), HomeFragment.class.getName(), getSupportFragmentManager());
        }
    }

    public static ImageView iv_home, iv_profile, iv_my_order, iv_notification;
    public static TextView tv_home, tv_profile, tv_my_order, tv_notification;

    private String loginData;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private String user_type, user_id;

    private void init(){

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

    private void getUserData(){

        loginData = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();
        user_id = userDataBean.getUser_id() + "";
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
                ChefHome fragment = new ChefHome();

                Bundle args = new Bundle();
                args.putString("chef_id", user_id);
                args.putString("foodie_id", "4");

                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
            }
        } else if (id == R.id.bottom_profile_layout){
            tv_profile.setTextColor(getResources().getColor(R.color.theme_color));
            iv_profile.setImageResource(R.drawable.ic_user_name_1);

            if(user_type.equals("2")) {
                BaseClass.callFragment(new ProfileFragment(), new ProfileFragment().getClass().getName(), getSupportFragmentManager());
            } else{
                ChefHome fragment = new ChefHome();

                Bundle args = new Bundle();
                args.putString("chef_id", user_id);
                args.putString("foodie_id", "4");

                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
            }
        } else if (id == R.id.bottom_my_order_layout){
            tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
            iv_my_order.setImageResource(R.drawable.ic_salad_1);
            if(user_type.equals("2")) {
                BaseClass.callFragment(new FoodieMyOrderFragment(), new FoodieMyOrderFragment().getClass().getName(), getSupportFragmentManager());
            } else{
                BaseClass.callFragment(new ChefMyOrderListFragment(), new ChefMyOrderListFragment().getClass().getName(), getSupportFragmentManager());
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
//        tv_home.setTextColor(getResources().getColor(R.color.theme_color));
//        iv_home.setImageResource(R.drawable.ic_home_1);
    }

    public void open_menu(View view){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {

        String tag1 = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager()
                .getBackStackEntryCount() - 1).getName();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finishAffinity();
        } else if(tag1.equals(HomeFragment.class.getName()) || tag1.equals(ChefHome.class.getName())){
            getSupportFragmentManager().popBackStack();
            finishAffinity();
        } else{
            super.onBackPressed();
        }
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
                    BaseClass.callFragment(new FoodieMyRequestFragment(), new FoodieMyRequestFragment().getClass().getName(), getSupportFragmentManager());
                }
                break;
            case R.id.navigation_myorder:
                if(user_type.equals("2")) {
                    BaseClass.callFragment(new FoodieMyOrderFragment(), new FoodieMyOrderFragment().getClass().getName(),
                            getSupportFragmentManager());
        }
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    /*Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT).show();*/
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
                break;
        }
    }

    private GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener= new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
            try {
                stringBuffer=getAddress(new LatLng(location.getLatitude(),location.getLongitude()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TAG", "onMyLocationChange: "+stringBuffer+ "lat:  "+ location.getLatitude()+"\n"+"long: "+location.getLongitude());
            googleMap.clear();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(loc).zoom(19f).tilt(70).build();
            googleMap.addMarker(new MarkerOptions().position(loc).icon(bitmapDescriptorFromVector(MainActivity.this)));
            if (googleMap!=null){
                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }
        }
    };

    public StringBuffer getAddress(LatLng latLng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        StringBuffer result = new StringBuffer();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            /*String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();*/
            result.append(city);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_chef);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
