package com.imcooking.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.DishDetails;
import com.imcooking.R;
import com.imcooking.activity.Sub.ChefProfile;
import com.imcooking.activity.Sub.OtherDishActivity;
import com.imcooking.adapters.HomeDishPagerAdapter;
import com.imcooking.adapters.Page_Adapter;
import com.imcooking.adapters.Pager1;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetails extends Fragment implements View.OnClickListener {
    TabLayout tabLayout;
    Pager1 adapter;

    public HomeDetails() {
        // Required empty public constructor
    }

    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getString("dish_id");

/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_details, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private ImageView iv_share, imgTop,imgPickUp, imgDeliviery;
    TextView txtDishName, txtChefName, txtLike,txtOtherDish, txtDistance, txtPrice, txtDeliverytype, txtAvailable,txtTime ;
    private LinearLayout chef_profile, layout;
    ViewPager pager;

    private String TAG  = HomeDetails.class.getName();

    private void init(){
        iv_share = getView().findViewById(R.id.home_details_share);
        imgTop = getView().findViewById(R.id.fragment_home_details_img_top);
        txtOtherDish = getView().findViewById(R.id.home_details_txtOtherDish);
        txtDishName = getView().findViewById(R.id.fragment_home_details_txtDishName);
        txtChefName = getView().findViewById(R.id.fragment_home_details_txtChefName);
        txtDistance = getView().findViewById(R.id.fragment_home_details_txtDistance);
        txtPrice = getView().findViewById(R.id.fragment_home_details_txtdishPrice);
        txtDeliverytype = getView().findViewById(R.id.fragment_home_details_txtDeliveryType);
        txtLike = getView().findViewById(R.id.home_details_like);
        txtAvailable = getView().findViewById(R.id.fragment_home_details_txtAvailable);
        txtTime = getView().findViewById(R.id.fragment_home_details_txtTime);
        imgDeliviery =  getView().findViewById(R.id.home_pager_imgHomeDelivery);
        imgPickUp =  getView().findViewById(R.id.home_pager_imgPick);
        iv_share.setOnClickListener(this);
        tabLayout= (TabLayout)getView(). findViewById(R.id.cardet_Tab);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients of Recipe"));
        tabLayout.addTab(tabLayout.newTab().setText("Know Chef"));
        pager = getView().findViewById(R.id.cardet_viewpager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        chef_profile = getView().findViewById(R.id.home_details_chef_profile);
        chef_profile.setOnClickListener(this);
        layout = getView().findViewById(R.id.home_details_layout);
        txtOtherDish.setOnClickListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();

//        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getDetails();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    Gson gson = new Gson();
    String chef_id;
    ApiResponse apiResponse = new ApiResponse();
    DishDetails dishDetails = new DishDetails();
    ArrayList<String>nameList = new ArrayList<>();
    private void getDetails(){
        layout.setVisibility(View.GONE);
        if (nameList!=null){
            nameList.clear();
        }
        String detailRequest = "{\"dish_id\":" + id + "}";
        Log.d("MyRequest", detailRequest);
        new GetData(getContext(), getActivity()).getResponse(detailRequest, "dishdetails", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {

                String s  = result;
                Log.d(TAG, "onSuccess: data"+result);
                apiResponse.dish_details = gson.fromJson(result, DishDetails.class);
                dishDetails = apiResponse.dish_details;
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        layout.setVisibility(View.VISIBLE);

                        txtDishName.setText(dishDetails.getDish_details().getDish_name());
                        txtChefName.setText(dishDetails.getDish_details().getChef_name());
                        txtPrice.setText("$"+dishDetails.getDish_details().getDish_price());
                        chef_id = dishDetails.getDish_details().getChef_id()+"";
                        if (dishDetails.getDish_details().getDish_available().equalsIgnoreCase("yes")){
                            txtAvailable.setText("Available ");
                            txtAvailable.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
                        } else {
                            txtAvailable.setText("No");
                        }//248340568282
                        txtTime.setText(dishDetails.getDish_details().getDish_from()+"-"+dishDetails.getDish_details().getDish_to());
                        if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("No")){
                            txtDeliverytype.setText("Pickup");
                        } else if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("YES")
                                && dishDetails.getDish_details().getDish_pickup().equalsIgnoreCase("YES")){
                            txtDeliverytype.setText("Home Delivery / Pickup");
                        } else {
                            txtDeliverytype.setText("Home Delivery");
                        }
                        nameList.add(dishDetails.getDish_details().getDish_special_note());
                        nameList.add(dishDetails.getDish_details().getDish_description());
                        if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("No")){
                            txtDeliverytype.setText("Pickup");
                            imgPickUp.setVisibility(View.VISIBLE);
                        } else if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("YES")
                                && dishDetails.getDish_details().getDish_pickup().equalsIgnoreCase("YES")){
                            txtDeliverytype.setText("Home Delivery / Pickup");
                            imgDeliviery.setVisibility(View.VISIBLE);
                            imgPickUp.setVisibility(View.VISIBLE);
                        } else {
                            imgDeliviery.setVisibility(View.VISIBLE);
                            txtDeliverytype.setText("Home Delivery");
                        }
                        txtLike.setText(dishDetails.getDish_details().getLike()+"");

                        txtDistance.setText(dishDetails.getDish_details().getDish_deliverymiles()+" miles");
                        Picasso.with(getContext()).load(GetData.IMG_BASE_URL+dishDetails
                                .getDish_details().getDish_image().get(0))
//                                .placeholder( R.drawable.progress_animation )
                                .into(imgTop);

                        adapter=new Pager1(getContext(), nameList);
                        pager.setAdapter(adapter);
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
                });

                }

        });

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.home_details_share){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share Using"));
        } else if(id == R.id.home_details_chef_profile) {
            startActivity(new Intent(getContext(), ChefProfile.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if (id == R.id.home_details_txtOtherDish){
            startActivity(new Intent(getContext(), OtherDishActivity.class).putExtra("chef_id",chef_id));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        }{

        }
    }
}
