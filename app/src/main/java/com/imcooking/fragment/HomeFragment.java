package com.imcooking.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.ApiRequest.Home;
import com.imcooking.Model.ApiRequest.SearchHomeRequest;
import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
    private HomeData homeData = new HomeData();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private Toolbar toolbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        LinearLayout toolbar_left = getView().findViewById(R.id.toolbar_left);
        toolbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).drawerLayout1.openDrawer(GravityCompat.START);
            }
        });

        init();
    }

    private TextView tv_cusine, arrow_show_detail, txtCityName;
    private LinearLayout cusine_list;
    ImageView imgBottom;
    private TextView tv_dish_name, tv_chef_name, tv_chef_likes, tv_chef_followers, tv_dish_distance, tv_dish_delivery,
                        tv_dish_price;

    private void init(){

        cusine_list = getView().findViewById(R.id.home_cuisine_list);
        tv_cusine = getView().findViewById(R.id.home_cuisine);
        tv_cusine.setOnClickListener(this);

        arrow_show_detail = getView().findViewById(R.id.home_show_detail_1);
        imgBottom = getView().findViewById(R.id.fragment_home_bottom_img);

        txtCityName = getView().findViewById(R.id.fragment_home_txtcity);

        arrow_show_detail.setOnClickListener(this);

        tv_dish_name = getView().findViewById(R.id.home_show_detail_1);
        tv_chef_name = getView().findViewById(R.id.home_chef_name);
        tv_chef_likes = getView().findViewById(R.id.home_chef_like);
        tv_chef_followers = getView().findViewById(R.id.home_chef_follow);
        tv_dish_distance = getView().findViewById(R.id.home_dish_distance);
        tv_dish_delivery = getView().findViewById(R.id.home_dish_home_delivery);
        tv_dish_price = getView().findViewById(R.id.home_dish_price);
    }


    private boolean cuisine_status = false;

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.home_cuisine){
            if(!cuisine_status){
                cusine_list.setVisibility(View.VISIBLE);
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_aarrow, 0);
                cuisine_status = true;
            }else{
                cusine_list.setVisibility(View.GONE);
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_arrow, 0);
                cuisine_status = false;
            }
        } else if(v.getId() == R.id.home_show_detail_1){
            BaseClass.callFragment(new com.imcooking.fragment.HomeDetails(), new com.imcooking.fragment.HomeDetails()
                    .getClass().getName(), getFragmentManager());

        }
        else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtCityName.setText(MainActivity.stringBuffer.toString());
            }
        },3000);


        getHomeData();
    }

    private void getHomeData(){

        String latitude = "28.5748119";
        String longitude = "7.0869837" ;
        String min_miles = "1";
        String max_miles = "10";
        String foodie_id = "4";
        String country = "101";

        Home data = new Home();
        data.setLatitude("28.5748119");
        data.setLongitude("77.0869837");
        data.setMin_miles("1");
        data.setMax_miles("10");
        data.setCountry("101");
        data.setFoodie_id("4");

        Log.d("MyRequest", new Gson().toJson(data));
        new GetData(getContext(), getActivity()).getResponse(new Gson().toJson(data), "search", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.d("VK", result);
                final String response = result;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        homeData = new Gson().fromJson(response, HomeData.class);
                        if (homeData.isStatus()) {

                            setMyData();
                        } else{
                            BaseClass.showToast(getContext(), "Something Went Wrong");
                        }
                    }
                });
            }
        });
    }

    private String TAG = HomeFragment.class.getName();
    private void setMyData(){

        tv_dish_name.setText(homeData.getChef_dish().get(0).getDish_name());
        tv_chef_name.setText(homeData.getChef_dish().get(0).getChef_name());
        tv_chef_likes.setText(String.valueOf(homeData.getChef_dish().get(0).getLike()));
        tv_chef_followers.setText(homeData.getChef_dish().get(0).getFollow()+"");
        tv_dish_distance.setText(homeData.getChef_dish().get(0).getDish_deliverymiles()+" miles");
        if(homeData.getChef_dish().get(0).getDish_homedelivery().equals("No")){
            tv_dish_delivery.setText("No Home Delivery");
        } else {tv_dish_delivery.setText("Home Delivery");}
        tv_dish_price.setText("$" + homeData.getChef_dish().get(0).getDish_price());
        Log.d(TAG, "setMyData: "+GetData.IMG_BASE_URL+homeData.getFavourite_data().get(0));
      //  Picasso.with(getContext()).load(GetData.IMG_BASE_URL+homeData.getFavourite_data().get(0).getImage()).into(imgBottom);


    }
}
