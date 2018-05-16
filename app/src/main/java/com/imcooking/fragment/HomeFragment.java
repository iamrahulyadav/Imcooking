package com.imcooking.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.ApiRequest.Home;
import com.imcooking.Model.ApiRequest.SearchHomeRequest;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.activity.Sub.AddAddressActivity;
import com.imcooking.activity.Sub.Cart_activity;
import com.imcooking.activity.Sub.FavoriteCusine;
import com.imcooking.activity.Sub.FilterHomeActivity;
import com.imcooking.activity.Sub.SelectLocActivity;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.CuisionAdatper;
import com.imcooking.adapters.HomeBottomPagerAdapter;
import com.imcooking.adapters.HomeDishPagerAdapter;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomViewPager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, CuisionAdatper.CuisionInterface {

    SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
    private HomeData homeData = new HomeData();
    private ArrayList<String>spinnerData =new ArrayList<>();
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private Gson gson = new Gson();
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
    CustomViewPager viewPager;
    HomeBottomPagerAdapter homeBottomPagerAdapter;

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
    private RecyclerView cuisinRecycler;
    private LinearLayout layout;
    ViewPager bottomViewPager;
    ImageView imgCart,imgFilter;
    boolean isApplyFiltered;
    CuisionAdatper cuisionAdatper;
    private Spinner sp;


    private void init(){

        sp = getView().findViewById(R.id.home_spiner);
        layout = getView().findViewById(R.id.home_layout);
        cusine_list = getView().findViewById(R.id.home_cuisine_list);
        tv_cusine = getView().findViewById(R.id.home_cuisine);
        bottomViewPager = getView().findViewById(R.id.fragment_home_bottom);
        imgFilter = getView().findViewById(R.id.fragment_home_img_filter);
        tv_cusine.setOnClickListener(this);
        viewPager =  getView().findViewById(R.id.home_viewPager);

        arrow_show_detail = getView().findViewById(R.id.home_show_detail_1);
        txtCityName = getView().findViewById(R.id.fragment_home_txtcity);
        imgCart = getView().findViewById(R.id.fragment_home_img_cart);
        imgCart.setOnClickListener(this);

        imgFilter.setOnClickListener(this);
        arrow_show_detail.setOnClickListener(this);
        txtCityName.setOnClickListener(this);
        cuisinRecycler = getView(). findViewById(R.id.fragment_home_cuisine_recycler);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cuisinRecycler.setLayoutManager(horizontalLayoutManagaer);
        if (spinnerData!=null){
            spinnerData.clear();
        }
        spinnerData.add("10 miles ");
        spinnerData.add("20 miles ");
        spinnerData.add("30 miles ");
        spinnerData.add("50 miles ");
        tinyDB = new TinyDB(getContext());
        String s = tinyDB.getString("login_data");
        userDataBean = gson.fromJson(s, ApiResponse.UserDataBean.class);
        foodie_id = userDataBean.getUser_id()+"";
//        cuisionAdatper = new CuisionAdatper(getContext(),cuisionList);
    //    cuisinRecycler.setAdapter(cuisionAdatper);
        layout.setVisibility(View.GONE);

        getCuisone();
        getHomeData();
        milesSpinner();
    }

    String s;
    String selectedmiles;
    private void milesSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_row, spinnerData);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        sp.setAdapter(arrayAdapter);
        if (selectedmiles != null) {

            int spinnerPosition = arrayAdapter.getPosition(s);
            sp.setSelection(spinnerPosition);
        }

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (position){
                    case 0:
                        min_miles = "0";
                        max_miles = "10";
                        getHomeData();
                        break;
                    case 1:
                        min_miles = "0";
                        max_miles = "20";
                        getHomeData();
                        break;
                    case 2:
                        min_miles = "0";
                        max_miles = "30";
                        getHomeData();
                        break;
                    case 3:
                        min_miles = "0";
                        max_miles = "50";
                        getHomeData();
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private boolean cuisine_status = false;

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

    }

    String latitude = "28.5748119";
    String longitude = "7.0869837" ;
    String min_miles = "0";
    String max_miles = "10";
    public static String foodie_id = "4";
    String country = "101";
    private CuisineData cuisineData = new CuisineData();
    private List<CuisineData.CuisineDataBean>cuisionList=new ArrayList<>();
    private void getCuisone(){
        if (cuisionList!=null){
            cuisionList.clear();
        }
        new GetData(getContext(), getActivity()).getResponse("", "cuisine",
                new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {
                final String response = result;
                cuisineData = new Gson().fromJson(result,CuisineData.class);
                cuisionList.addAll(cuisineData.getCuisine_data());
                setCuisionAdapter();
            }
        });
    }

    private void setCuisionAdapter(){
        cuisionAdatper = new CuisionAdatper(getContext(),cuisionList);
        cuisinRecycler.setAdapter(cuisionAdatper);
        cuisionAdatper.CuisionInterfaceMethod(this);
    }

    private void getHomeData(){

        Home data = new Home();
        data.setLatitude("51.5198117");
        data.setLongitude("-0.0939186");
        data.setMin_miles(min_miles);
        data.setMax_miles(max_miles);
        data.setCountry("230");
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
                        Log.d(TAG, "run: data"+response.toString());
                        homeData = new Gson().fromJson(response, HomeData.class);
                        if (homeData.isStatus()) {
                            layout.setVisibility(View.VISIBLE);
                            setMyData();
//                            adapter.notifyDataSetChanged();
//                            homeBottomPagerAdapter.notifyDataSetChanged();
                        } else{
                            BaseClass.showToast(getContext(), "Something Went Wrong");
                        }
                    }
                });
            }
        });
    }

    private String TAG = HomeFragment.class.getName();
    List<HomeData.ChefDishBean>chefDishBeans = new ArrayList<>();
    List<HomeData.ChefDishBean>favorite_1 = new ArrayList<>();
    List<HomeData.FavouriteDataBean>favouriteDataBeans = new ArrayList<>();
    List<HomeData.FavouriteDataBean>favorite_2 = new ArrayList<>();
    private void setMyData(){
        if (chefDishBeans!=null){
            chefDishBeans.clear();
        }
        if (favouriteDataBeans!=null){
            favouriteDataBeans.clear();
        }
        chefDishBeans.addAll(homeData.getChef_dish());
        favouriteDataBeans.addAll(homeData.getFavourite_data());
        setMyViewPager(chefDishBeans);
        setBottomViewPager(favouriteDataBeans);

    }

    private void setMyViewPager(List<HomeData.ChefDishBean> mylist) {
        HomeDishPagerAdapter adapter = new HomeDishPagerAdapter(getActivity(),getContext(), getFragmentManager(), mylist);
        viewPager.setAdapter(adapter);
    }

    private void setBottomViewPager(List<HomeData.FavouriteDataBean> mylist) {
        Log.d("Debug", favouriteDataBeans.size() + "");
        homeBottomPagerAdapter = new HomeBottomPagerAdapter(getContext(), getFragmentManager(), mylist);
        bottomViewPager.setAdapter(homeBottomPagerAdapter);
    }

    @Override
    public void CuisionInterfaceMethod(View view, int position) {
        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.home_cuisine){
            if(!cuisine_status){
                cusine_list.setVisibility(View.VISIBLE);
                cusine_list.setAlpha(0.0f);
                cusine_list.animate()
                        .translationY(0)
                        .alpha(1.0f)
                        .setListener(null)
                        .setDuration(1000);
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_aarrow, 0);
                cuisine_status = true;
            }else{
                cusine_list.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .setDuration(1000)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                cusine_list.setVisibility(View.GONE);
                            }
                        });
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_arrow, 0);
                cuisine_status = false;
            }
        } else if(v.getId() == R.id.home_show_detail_1){

            HomeDetails fragment = new HomeDetails();
            Bundle bundle = new Bundle();
            bundle.putString("dish_id", homeData.getChef_dish().get(0).getDish_id() + "");
            fragment.setArguments(bundle);

            BaseClass.callFragment(fragment, fragment
                    .getClass().getName(), getFragmentManager());

        }
        else if (v.getId()==R.id.fragment_home_img_filter){
            startActivityForResult(new Intent(getContext(), FilterHomeActivity.class),1);
        }
        else if (v.getId() == R.id.fragment_home_img_cart)
        {
            startActivity(new Intent(getContext(), Cart_activity.class).putExtra("foodie_id",
                    userDataBean.getUser_id()));
           getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (v.getId()==R.id.fragment_home_txtcity){
            startActivity(new Intent(getActivity(), SelectLocActivity.class));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if(resultCode== FilterHomeActivity.FILTER_RESPONSE_CODE)
            {
//                Toast.makeText(getContext(), ""+data.getFloatExtra("ratingvalue",0), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), ""+data.getIntExtra("progressChangedValue",0), Toast.LENGTH_SHORT).show();
                filter_data(data.getFloatExtra("ratingvalue", 0),
                        data.getIntExtra("progressChangedValue", 0));
            }
        }
    }

    private void filter_data(Float rating, int price){
//        int rating_ = (int) rating;
        favorite_1.clear();
        for(int i=0; i<chefDishBeans.size(); i++){
            HomeData.ChefDishBean list = chefDishBeans.get(i);

            if(list.getRating().equals((rating.intValue() + "")) /*&& list.getDish_price().equals(price + "")*/){
                favorite_1.add(list);
            }
        }

        setMyViewPager(favorite_1);


        favorite_2.clear();
        for(int i=0; i<favouriteDataBeans.size(); i++){
            HomeData.FavouriteDataBean list = favouriteDataBeans.get(i);

            if(list.getRating().equals((rating.intValue() + "")) /*&& list.getDish_price().equals(price + "")*/){
                favorite_2.add(list);
            }
        }

        setBottomViewPager(favorite_2);


    }



}
