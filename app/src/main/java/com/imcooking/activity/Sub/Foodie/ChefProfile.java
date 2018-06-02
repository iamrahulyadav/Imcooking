package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterChefHomeViewPager;
import com.imcooking.adapters.Page_Adapter;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.chef.chefprofile.AboutChefFragment;
import com.imcooking.fragment.chef.chefprofile.ChefDishListFragment;
import com.imcooking.fragment.chef.chefprofile.RequestDishFragment;
import com.imcooking.fragment.foodie.RequestADishFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

public class ChefProfile extends AppBaseActivity {
    TextView txtName, txtAddress,txtFollowers, txtCall;
    ImageView imgChef, imgBack;
    Page_Adapter adapter;
    String chefId, foodie_id;

    public static int CHEF_PROFILE_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);

        chefId = getIntent().getStringExtra("chef_id");
        foodie_id = getIntent().getStringExtra("foodie_id");

       ChefHome fragment = new ChefHome();

        Bundle args = new Bundle();
        args.putString("chef_id", chefId);
        args.putString("foodie_id", foodie_id);

        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_chef_profile, fragment).commit();

    }

    ViewPager pager;
    private void init(){
        pager=  findViewById(R.id.cardet_viewpager);
        txtName = findViewById(R.id.activity_chef_txtname);
        txtAddress = findViewById(R.id.activity_chef_txtAdderss);
        txtCall = findViewById(R.id.activity_chef_txtCall);
        txtFollowers = findViewById(R.id.activity_chef_txtFollower);
        imgChef = findViewById(R.id.chef_profile_image);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabLayout= (TabLayout) findViewById(R.id.cardet_Tab);
//        setupTab();
    }

    TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
//        getchefProfile();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    String TAG = ChefProfile.class.getName();
    public static ChefProfileData chefProfileData = new ChefProfileData();

    private void getchefProfile(){
        String s ="{\"chef_id\":"+chefId+"}";
        new GetData(getApplicationContext(),ChefProfile.this).getResponse(s, "chefdetails", new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                Log.d(TAG, "onSuccess: "+result);

                if (result!=null){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           chefProfileData = new Gson().fromJson(result, ChefProfileData.class);
                           txtAddress.setText(chefProfileData.getChef_data().getAddress());
                           txtName.setText(chefProfileData.getChef_data().getChef_name());
                           Picasso.with(getApplicationContext()).load(GetData.IMG_BASE_URL+chefProfileData
                                   .getChef_data().getChef_image())
//                                .placeholder( R.drawable.progress_animation )
                                   .into(imgChef);
                           if (chefProfileData.getChef_data().getFollow()==1){
                               txtFollowers.setText(chefProfileData.getChef_data().getFollow()+" Follower");
                           } else {
                               txtFollowers.setText(chefProfileData.getChef_data().getFollow()+" Followers");
                           }
                           viewPager = findViewById(R.id.chef_home_viewpager);
                           setupViewPager(viewPager);

                           tabLayout = (TabLayout) findViewById(R.id.chef_home_tablayout);
                           tabLayout.setupWithViewPager(viewPager);
                       }
                   });
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterChefHomeViewPager adapter = new AdapterChefHomeViewPager(getSupportFragmentManager());
        adapter.addFragment(new AboutChefFragment(), "ABOUT CHEF");
        adapter.addFragment(new ChefDishListFragment(), "DISH");
        adapter.addFragment(new RequestADishFragment(), "REQUEST A DISH");
        viewPager.setAdapter(adapter);
    }
}
