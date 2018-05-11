package com.imcooking.activity.Sub;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.imcooking.adapters.HomeDishPagerAdapter;
import com.imcooking.adapters.Page_Adapter;
import com.imcooking.fragment.chefprofile.AboutChefFragment;
import com.imcooking.fragment.chefprofile.ChefDishListFragment;
import com.imcooking.fragment.chefprofile.RequestDishFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.CustomViewPager;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

public class ChefProfile extends AppBaseActivity {
    TextView txtName, txtAddress,txtFollowers, txtCall;
    ImageView imgChef, imgBack;
    Page_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);

//        find id
        init();
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
    private void setupTab(){
        AboutChefFragment tab1Fragment=new AboutChefFragment();
        ChefDishListFragment tab2Fragment=new ChefDishListFragment();
        RequestDishFragment tab3Fragment=new RequestDishFragment();
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Dish List"));
        tabLayout.addTab(tabLayout.newTab().setText("Request"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter=new Page_Adapter(getSupportFragmentManager(),tabLayout.getTabCount(),tab1Fragment,tab2Fragment,tab3Fragment);
        pager.setAdapter(adapter);
//        pager.setOffscreenPageLimit(2);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getchefProfile();
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
    ChefProfileData chefProfileData = new ChefProfileData();

    private void getchefProfile(){
        String s ="{\"chef_id\":\"72\"}";
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
                           txtFollowers.setText(chefProfileData.getChef_data().getFollow()+" Followers");
                       }
                   });
                }
            }
        });
    }
}
