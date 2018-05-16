package com.imcooking.fragment.chef;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.ChefProfile;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.AdapterChefHomeViewPager;
import com.imcooking.adapters.Page_Adapter;
import com.imcooking.fragment.chef.chefprofile.AboutChefFragment;
import com.imcooking.fragment.chef.chefprofile.ChefDishListFragment;
import com.imcooking.fragment.chef.chefprofile.RequestDishFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefHome extends Fragment {

    public ChefHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_home, container, false);
    }

    String TAG = ChefProfile.class.getName();
    public static ChefProfileData chefProfileData = new ChefProfileData();
    TextView txtName, txtAddress,txtFollowers;
    ImageView imgChef, imgBack;
    Page_Adapter adapter;
    ViewPager pager;

    private TextView tv_phoneno;
    private LinearLayout layout;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        getchefProfile();


//        setMyData();
    }

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private void init(){
        layout = getView().findViewById(R.id.layout_chef_home);

        pager=  getView().findViewById(R.id.cardet_viewpager);
        txtName = getView().findViewById(R.id.activity_chef_txtname);
        txtAddress = getView().findViewById(R.id.activity_chef_txtAdderss);
        txtFollowers = getView().findViewById(R.id.activity_chef_txtFollower);
        imgChef = getView().findViewById(R.id.chef_profile_image);
        imgBack = getView().findViewById(R.id.imgBack);

        tv_phoneno = getView().findViewById(R.id.chef_home_phoneno);



       /* for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getContext()).inflate(R.layout.my_tab_layout, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
            tab.select();
        }*/
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterChefHomeViewPager adapter = new AdapterChefHomeViewPager(getChildFragmentManager());
        adapter.addFragment(new AboutChefFragment(), "ABOUT CHEF");
        adapter.addFragment(new ChefDishListFragment(), "DISH");
        adapter.addFragment(new RequestDishFragment(), "DISH REQUESTS");
        viewPager.setAdapter(adapter);
    }

    private void getchefProfile(){
        String s ="{\"chef_id\":\"72\"}";
        layout.setVisibility(View.GONE);
        new GetData(getContext(),getActivity()).getResponse(s, "chefdetails", new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                Log.d(TAG, "onSuccess: "+result);

                if (result!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chefProfileData = new Gson().fromJson(result, ChefProfileData.class);

                            if(chefProfileData.isStatus()) {
                                layout.setVisibility(View.VISIBLE);
                                txtAddress.setText(chefProfileData.getChef_data().getAddress());
                                txtName.setText(chefProfileData.getChef_data().getChef_name());
                                tv_phoneno.setText(chefProfileData.getChef_data().getChef_phone() + "");
                                Picasso.with(getContext()).load(GetData.IMG_BASE_URL + chefProfileData
                                        .getChef_data().getChef_image())
//                                .placeholder( R.drawable.progress_animation )
                                        .into(imgChef);
                                if(chefProfileData.getChef_data().getFollow() == 1) {
                                    txtFollowers.setText(chefProfileData.getChef_data().getFollow() + " Follower");
                                } else if(chefProfileData.getChef_data().getFollow() > 1){
                                    txtFollowers.setText(chefProfileData.getChef_data().getFollow() + " Followers");
                                } else{}
                                viewPager = getView().findViewById(R.id.chef_home_viewpager);
                                setupViewPager(viewPager);

                                tabLayout = (TabLayout) getView().findViewById(R.id.chef_home_tablayout);
                                tabLayout.setupWithViewPager(viewPager);
                            } else{
                                BaseClass.showToast(getContext(), "Something Went Wrong.");
                            }
                        }
                    });
                }
            }
        });
    }

    private void setMyData(){


    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
