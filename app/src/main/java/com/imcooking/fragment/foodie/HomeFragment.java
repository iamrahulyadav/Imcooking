package com.imcooking.fragment.foodie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.Home;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.Sub.Foodie.FilterActivity;
import com.imcooking.activity.Sub.Foodie.SelectLocActivity;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.CuisionAdatper;
import com.imcooking.adapters.HomeBottomPagerAdapter;
import com.imcooking.adapters.HomeDishPagerAdapter;
import com.imcooking.splash.SplashActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomViewPager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements
        View.OnClickListener, HomeDishPagerAdapter.click_dish_pager_like,
        HomeBottomPagerAdapter.click_dish_pager_like_2, CuisionAdatper.Interface_CuisineAdapter {

    public static TextView cart_icon;
    Button getLocationBtn;
    private HomeData homeData = new HomeData();
    private ArrayList<String>spinnerData =new ArrayList<>();
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private Gson gson = new Gson();
    private LinearLayout layout_no_record_found,cusine_list;
    private String TAG = HomeFragment.class.getName();
    List<HomeData.ChefDishBean>chefDishBeans = new ArrayList<>();
    List<HomeData.ChefDishBean>chefDishBeans_filter_all = new ArrayList<>();
    List<HomeData.ChefDishBean>chefDishBeans_filter_cuisine = new ArrayList<>();

    List<HomeData.ChefDishBean>favorite_1 = new ArrayList<>();
    List<HomeData.FavouriteDataBean>favouriteDataBeans = new ArrayList<>();
    CustomViewPager viewPager;
    HomeBottomPagerAdapter homeBottomPagerAdapter;
    private TextView tv_cusine,  txtCityName, txtSerach;
    private RecyclerView cuisinRecycler;
    private LinearLayout layout, layout2;
    ViewPager bottomViewPager;
    ImageView imgCart,imgFilter;
    CuisionAdatper cuisionAdatper;
    private Spinner sp;

    String latitudeq= SplashActivity.latitude+"";
    String longitudeq=SplashActivity.longitude+"" ;
    String min_miles = "0";
    String max_miles = "10";
    public static String foodie_id = "4";
    String country = "101";
    private CuisineData cuisineData = new CuisineData();
    private List<CuisineData.CuisineDataBean>cuisionList=new ArrayList<>();
    private List<CuisineData.CuisineDataBean>cuisionList_filter_all=new ArrayList<>();

    private boolean isFilterApplied;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Toast.makeText(getContext(), "Home", Toast.LENGTH_SHORT).show();

        getLocationBtn = (Button)getView().findViewById(R.id.getLocationBtn);
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

    private ArrayList<String> arr_like_status_1 = new ArrayList<>();
    private ArrayList<String> arr_like_status_1_filter_all = new ArrayList<>();
    private ArrayList<String> arr_like_status_1_filter_cuisine = new ArrayList<>();

    private ArrayList<String> arr_like_status_2 = new ArrayList<>();

    private void init(){

        cart_icon = getView().findViewById(R.id.home_cart_text_count);
        layout_no_record_found = getView().findViewById(R.id.home_no_record_image);
        layout2 = getView().findViewById(R.id.home_no_record_image_2);

        sp = getView().findViewById(R.id.home_spiner);
        layout = getView().findViewById(R.id.home_layout);
        cusine_list = getView().findViewById(R.id.home_cuisine_list);
        tv_cusine = getView().findViewById(R.id.home_cuisine);
        bottomViewPager = getView().findViewById(R.id.fragment_home_bottom);
        imgFilter = getView().findViewById(R.id.fragment_home_img_filter);
        tv_cusine.setOnClickListener(this);
        viewPager =  getView().findViewById(R.id.home_viewPager);
        txtSerach = getView().findViewById(R.id.fragment_home_search_img);
        txtCityName = getView().findViewById(R.id.fragment_home_txtcity);
        imgCart = getView().findViewById(R.id.fragment_home_img_cart);
        imgCart.setOnClickListener(this);

        imgFilter.setOnClickListener(this);
        txtSerach.setOnClickListener(this);
        txtCityName.setOnClickListener(this);
        cuisinRecycler = getView(). findViewById(R.id.fragment_home_cuisine_recycler);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cuisinRecycler.setLayoutManager(horizontalLayoutManagaer);
        if (spinnerData!=null){
            spinnerData.clear();
        }


        latitudeq= SplashActivity.latitude+"";
        longitudeq=SplashActivity.longitude+"" ;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer = getAddress(new LatLng(SplashActivity.latitude, SplashActivity.longitude));
            txtCityName.setText(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
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


        getCuisone();
        milesSpinner();
    }

    String selectedValue;
    String selectedmiles;
    private void milesSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_row, spinnerData);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        sp.setAdapter(arrayAdapter);
        if (selectedmiles != null) {
            int spinnerPosition = arrayAdapter.getPosition(selectedValue);
            sp.setSelection(spinnerPosition);
        }

        sp.setSelection(2);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (position){
                    case 0:
                        min_miles = "0";
                        max_miles = "10";
                        getHomeData(latitudeq, longitudeq);

                        break;
                    case 1:
                        min_miles = "0";
                        max_miles = "20";
                        getHomeData(latitudeq, longitudeq);
                        break;
                    case 2:
                        min_miles = "0";
                        max_miles = "30";
                        getHomeData(latitudeq, longitudeq);
                        break;
                    case 3:
                        min_miles = "0";
                        max_miles = "50";
                        getHomeData(latitudeq, longitudeq);
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

    }

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
                        setCuisionAdapter(cuisionList);
                    }
                });
    }

    private ArrayList<String> arr_cuisines = new ArrayList<>();
    private ArrayList<String> arr_cuisines_status = new ArrayList<>();
    private void setCuisionAdapter(List<CuisineData.CuisineDataBean> cuisionList){

        arr_cuisines.clear();
        arr_cuisines_status.clear();
        arr_cuisines.add("All");
        arr_cuisines_status.add("1");
        for(int i=0; i<cuisionList.size(); i++){
            arr_cuisines.add(cuisionList.get(i).getCuisine_name());
            arr_cuisines_status.add("0");
        }

        cuisionList_filter_all.addAll(cuisionList);
        cuisionAdatper = new CuisionAdatper(getContext(), arr_cuisines, arr_cuisines_status, this);
        cuisinRecycler.setAdapter(cuisionAdatper);
//        cuisionAdatper.CuisionInterfaceMethod(this);
    }

    List<HomeData.FavouriteDataBean> list = new ArrayList<>();

    private void getHomeData(String latitudeq, String longitudeq){
        list = new ArrayList<>();
        Home data = new Home();
        data.setLatitude(latitudeq);
        data.setLongitude(longitudeq);
        data.setMin_miles(min_miles);
        data.setMax_miles(max_miles);
        data.setCountry("");
        data.setFoodie_id(foodie_id);
        Log.d("MyRequest", new Gson().toJson(data));
        layout.setVisibility(View.GONE);
        new GetData(getContext(), getActivity()).getResponse(new Gson().toJson(data), GetData.HOME, new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.d("VK", result);
                final String response = result;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: data"+response.toString());
                        layout.setVisibility(View.VISIBLE);
                        homeData = new Gson().fromJson(response, HomeData.class);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject!=null&&jsonObject.has("add_acrt_count")){
                                cart_icon.setText(jsonObject.getString("add_acrt_count"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (homeData.isStatus()) {
                            arr_like_status_1.clear(); arr_like_status_2.clear();
                            for(int i=0; i<homeData.getChef_dish().size(); i++){
                                arr_like_status_1.add(homeData.getChef_dish().get(i).getDishlike());
                            }
                            if(homeData.getFavourite_data().size() != 0){
                                for(int i=0; i<homeData.getFavourite_data().size(); i++){
                                    arr_like_status_2.add(homeData.getFavourite_data().get(i).getDishlike());
                                }
                                bottomViewPager.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.GONE);
                            } else {
                                bottomViewPager.setVisibility(View.GONE);
                                layout2.setVisibility(View.VISIBLE);
                            }

                            layout.setVisibility(View.VISIBLE);
                            viewPager.setVisibility(View.VISIBLE);
                            layout_no_record_found.setVisibility(View.GONE);

                            isFilterApplied = false;
                            setMyData(/*arr_like_status_1, arr_like_status_2*/);
//                            adapter.notifyDataSetChanged();
//                            homeBottomPagerAdapter.notifyDataSetChanged()
                        } else{
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("msg").equals("Not record found")){
                             //       BaseClass.showToast(getContext(), "No record found");
                                    layout_no_record_found.setVisibility(View.VISIBLE);
                                    layout.setVisibility(View.VISIBLE);
                                    viewPager.setVisibility(View.GONE);

                                    JSONArray jar = jsonObject.getJSONArray("favourite_data");
                                    Log.d("VK", jar.toString());

                                    if(homeData.getFavourite_data().size() != 0){
                                        for(int i=0; i<homeData.getFavourite_data().size(); i++){
                                            arr_like_status_2.add(homeData.getFavourite_data().get(i).getDishlike());
                                        }
                                        layout2.setVisibility(View.GONE);
                                        bottomViewPager.setVisibility(View.VISIBLE);
                                        isFilterApplied = false;
                                    } else{
                                        layout2.setVisibility(View.VISIBLE);
                                        bottomViewPager.setVisibility(View.GONE);
                                    }

                                    setMyData();

//                                    List<HomeData.FavouriteDataBean> list = new ArrayList<>();
//
//                                    list.clear();
//                                    list = Arrays.asList(new Gson().fromJson(jar.toString(), HomeData.FavouriteDataBean[].class));
//

                                    /*
                                    homeData.setFavourite_data((List<HomeData.FavouriteDataBean>) jar);

                                    if (favouriteDataBeans!=null){
                                        favouriteDataBeans.clear();
                                    }
                                    if (homeData.getFavourite_data()!=null){
                                        favouriteDataBeans.addAll(homeData.getFavourite_data());
                                    }
*/
//                                    setBottomViewPager(list);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    private void setMyData(/*ArrayList<String> like_1, ArrayList<String> like_2*/){

//        if(!isFilterApplied) {
            if (chefDishBeans != null) {
                chefDishBeans.clear();
            }

            if (homeData.getChef_dish() != null && homeData.getChef_dish().size() > 0) {
                chefDishBeans.addAll(homeData.getChef_dish());
            } /*else {
                viewPager.setVisibility(View.GONE);
                layout_no_record_found.setVisibility(View.VISIBLE);
            }*/
            setMyViewPager(chefDishBeans, arr_like_status_1);
//        } else{
//
//        }




        if (favouriteDataBeans != null) {
            favouriteDataBeans.clear();
        }
        if (homeData.getFavourite_data() != null && homeData.getFavourite_data().size() > 0) {
            favouriteDataBeans.addAll(homeData.getFavourite_data());
        } else if (homeData.getFavourite_data().size() == 0) {
            layout2.setVisibility(View.VISIBLE);
            bottomViewPager.setVisibility(View.GONE);
        }

        setBottomViewPager(favouriteDataBeans);
    }

    private HomeDishPagerAdapter adapter;
    private void setMyViewPager(List<HomeData.ChefDishBean> mylist, ArrayList<String> arr_like_status_1) {

        if(mylist != null && mylist.size()>0) {
            chefDishBeans_filter_all.addAll(mylist);
            arr_like_status_1_filter_all.addAll(arr_like_status_1);
        } else {
            viewPager.setVisibility(View.GONE);
            layout_no_record_found.setVisibility(View.VISIBLE);
        }
        adapter = new HomeDishPagerAdapter(getActivity(),getContext(), getFragmentManager()
                , chefDishBeans_filter_all, this, arr_like_status_1_filter_all);
        viewPager.setAdapter(adapter);
    }

    private void setBottomViewPager(List<HomeData.FavouriteDataBean> mylist) {
        Log.d("Debug", favouriteDataBeans.size() + "");
        homeBottomPagerAdapter = new HomeBottomPagerAdapter(getContext(), getFragmentManager()
                , mylist,this, arr_like_status_2);
        bottomViewPager.setAdapter(homeBottomPagerAdapter);
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
                        .setDuration(300);
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_aarrow, 0);
                cuisine_status = true;
            }
            else{
                cusine_list.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .setDuration(300)
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
        }

        else if (v.getId()==R.id.fragment_home_img_filter){
//            startActivityForResult(new Intent(getContext(), FilterHomeActivity.class),1);
            startActivityForResult(new Intent(getContext(), FilterActivity.class), 0143);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }
        else if (v.getId() == R.id.fragment_home_img_cart)
        {
            startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                    userDataBean.getUser_id()));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (v.getId()==R.id.fragment_home_txtcity){
            startActivityForResult(new Intent(getActivity(), SelectLocActivity.class),2);
        } else if (v.getId()==R.id.fragment_home_search_img){
            BaseClass.callFragment1(new SearchFragment(), new SearchFragment().getClass().getName()
                    , getFragmentManager());
        } else {}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if(resultCode == 0143)
            {
                String str_rating = data.getExtras().getString("rating");
                String str_min_price = data.getExtras().getString("min_price");
                String str_max_price = data.getExtras().getString("max_price");
                String str_check_home = data.getExtras().getString("check_home");
                String str_check_pickup = data.getExtras().getString("check_pickup");

                filterData(str_rating, str_min_price, str_max_price, str_check_home, str_check_pickup);



                //                filter_data(data.getFloatExtra("ratingvalue", 0),
//                        data.getIntExtra("progressChangedValue", 0));
            } else if (requestCode==2){
                latitudeq = data.getDoubleExtra("latitude",0)+"";
                longitudeq = data.getDoubleExtra("longitude",0)+"";
                txtCityName.setText(data.getStringExtra("name"));
                Log.d(TAG, "onActivityResult: "+latitudeq+"\n"+longitudeq+"\n");
                getHomeData(latitudeq, longitudeq);
            }
        }
    }

    private void filterData(String str_rating, String str_min_price, String str_max_price,
                            String str_check_home, String str_check_pickup){

        Log.d("FilteredData", str_rating);
        Log.d("FilteredData", str_min_price);
        Log.d("FilteredData", str_max_price);
        Log.d("FilteredData", str_check_home);
        Log.d("FilteredData", str_check_pickup);
        if(str_rating.equals("0.0") && str_min_price.equals("0") && str_max_price.equals("0") &&
                str_check_home.equals("0") && str_check_pickup.equals("0")){

            BaseClass.showToast(getContext(), "No Filter Applied");
        } else {
            List<HomeData.ChefDishBean> cDB = new ArrayList<>();
            boolean data_added = false;
            for (HomeData.ChefDishBean bean : chefDishBeans) {
                if (bean.getDish_cuisine()!=null && bean.getDish_cuisine().size()>0) {
                    data_added = false;
                    if(str_rating.equals(bean.getRating())){
                        data_added = true;
                    }
                    if(Float.parseFloat(bean.getDish_price()) >= Float.parseFloat(str_min_price) &&
                            Float.parseFloat(bean.getDish_price()) <= Float.parseFloat(str_max_price)){
                        data_added = true;
                    }
                    if(str_check_home.equals("0") && str_check_pickup.equals("1")) {
                        if (bean.getDish_homedelivery().equalsIgnoreCase("No") && bean.getDish_pickup().equalsIgnoreCase("Yes")) {
                            data_added = true;
                        }
                    } else if(str_check_home.equals("1") && str_check_pickup.equals("0")) {
                        if(bean.getDish_homedelivery().equalsIgnoreCase("Yes") && bean.getDish_pickup().equalsIgnoreCase("No")){
                            data_added = true;
                        }
                    } else if(str_check_home.equals("1") && str_check_pickup.equals("1")){
                        if(bean.getDish_homedelivery().equalsIgnoreCase("Yes") && bean.getDish_pickup().equalsIgnoreCase("Yes")){
                            data_added = true;
                        }
                    }
                }
                if(data_added) {
                    cDB.add(bean);
                }
            }

            arr_like_status_1_filter_all.clear();
            chefDishBeans_filter_all.clear();
            chefDishBeans_filter_all.addAll(cDB);
            for(int i=0; i<cDB.size(); i++){
                arr_like_status_1_filter_all.add(cDB.get(i).getDishlike());
            }
            adapter.notifyDataSetChanged();

            if(cDB != null && cDB.size()>0){
                viewPager.setVisibility(View.VISIBLE);
                layout_no_record_found.setVisibility(View.GONE);
            } else{
                viewPager.setVisibility(View.GONE);
                layout_no_record_found.setVisibility(View.VISIBLE);
            }

            Log.d("FilteredData", arr_like_status_1_filter_all.size() + "\n" + chefDishBeans_filter_all.size());
        }
    }


    public StringBuffer getAddress(LatLng latLng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses = null;
        StringBuffer result = new StringBuffer();
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses!=null&&addresses.size()!=0){
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    result.append(city);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void click_me(int position) {

//        if(arr_like_status_1.get(position).equals("0")){

            like_dislike(position);
//        } else{
//
//        }
//        BaseClass.showToast(getContext(), position + "");

    }

    private void like_dislike(final int position){

        String s = "{\"chef_id\":" + homeData.getChef_dish().get(position).getChef_id() +
                ",\"foodie_id\":" + foodie_id +
                ",\"dish_id\":" + homeData.getChef_dish().get(position).getDish_id() + "}";

        try {
            JSONObject job = new JSONObject(s);

            new GetData(getContext(), getActivity()).sendMyData(job, "dishlike", getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getBoolean("status")){
                            if(jsonObject.getString("msg").equals("Successfully dish like")){
                                arr_like_status_1_filter_all.set(position, "1");
                                BaseClass.showToast(getContext(), "Successfully Liked");
                                int i = Integer.parseInt(homeData.getChef_dish().get(position).getDishlikeno());
                                homeData.getChef_dish().get(position).setDishlikeno((i+1) + "");
                            } else if(jsonObject.getString("msg").equals("Successfully unlike")){
                                arr_like_status_1_filter_all.set(position, "0");
                                BaseClass.showToast(getContext(), "Dish Successfully unliked");
                                int i = Integer.parseInt(homeData.getChef_dish().get(position).getDishlikeno());
                                homeData.getChef_dish().get(position).setDishlikeno((i-1) + "");
                            } else{
                                BaseClass.showToast(getContext(), "Something Went Wrong");
                            }

                            adapter.notifyDataSetChanged();
                        } else{
                            BaseClass.showToast(getContext(), "Something Went Wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void click_me_2(int position) {
        like_dislike_2(position);

    }

    private void like_dislike_2(final int position){

        String s = "{\"chef_id\":" + homeData.getFavourite_data().get(position).getChef_id() +
                ",\"foodie_id\":" + foodie_id +
                ",\"dish_id\":" + homeData.getFavourite_data().get(position).getDish_id() + "}";

        try {
            JSONObject job = new JSONObject(s);

            new GetData(getContext(), getActivity()).sendMyData(job, "dishlike", getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getBoolean("status")){
                            if(jsonObject.getString("msg").equals("Successfully dish like")){
                                arr_like_status_2.set(position, "1");
                                BaseClass.showToast(getContext(), "Successfully Liked");
                                int i = Integer.parseInt(homeData.getFavourite_data().get(position).getDishlikeno());
                                homeData.getFavourite_data().get(position).setDishlikeno((i+1) + "");
//                                list.set(position, "");
//                                list.get(position).
                            } else if(jsonObject.getString("msg").equals("Successfully unlike")){
                                arr_like_status_2.set(position, "0");
                                BaseClass.showToast(getContext(), "Dish Successfully unlike");
                                int i = Integer.parseInt(homeData.getFavourite_data().get(position).getDishlikeno());
                                homeData.getFavourite_data().get(position).setDishlikeno((i-1) + "");
                            } else{
                                BaseClass.showToast(getContext(), "Something Went Wrong");
                            }

                            homeBottomPagerAdapter.notifyDataSetChanged();
                        } else{
                            BaseClass.showToast(getContext(), "Something Went Wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void method_CuisineAdapter(int position) {
        if(position == 0){
            for(int i=0; i<arr_cuisines.size(); i++){
                arr_cuisines_status.set(i, "0");
            }
            arr_cuisines_status.set(0, "1");
            cuisionAdatper.notifyDataSetChanged();

            arr_like_status_1_filter_all.clear();
            chefDishBeans_filter_all.clear();

            arr_like_status_1_filter_all.addAll(arr_like_status_1);
            chefDishBeans_filter_all.addAll(chefDishBeans);
            adapter.notifyDataSetChanged();

            if(chefDishBeans_filter_all!=null && chefDishBeans_filter_all.size()>0) {
                layout_no_record_found.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            } else{
                layout_no_record_found.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
            }

        } else{
            for(int i=0; i<arr_cuisines.size(); i++){
                if(i == position){
                    arr_cuisines_status.set(i, "1");
                } else{
                    arr_cuisines_status.set(i, "0");
                }
            }
            cuisionAdatper.notifyDataSetChanged();
            filterCuisine(position, arr_cuisines.get(position));

        }
    }

    List<HomeData.ChefDishBean>cuisionChefList;

    private void filterCuisine(int position, String cuision){
        cuisionChefList = new ArrayList<>();
        for (HomeData.ChefDishBean  bean: chefDishBeans){
            if (bean.getDish_cuisine()!=null && bean.getDish_cuisine().size()>0){
                String cuisionVa = bean.getDish_cuisine().get(0).getCuisine_name();
                if (cuision.equalsIgnoreCase(cuisionVa)){
                    cuisionChefList.add(chefDishBeans.get(position));
                }
            }
        }

//        cuisionChefList = getByName(chefDishBeans, cuision);
        if (cuisionChefList.size()>0){
            layout_no_record_found.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
//            setMyViewPager(cuisionChefList);

        } else {
            layout_no_record_found.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        }

        chefDishBeans_filter_all.clear();
        chefDishBeans_filter_all.addAll(cuisionChefList);

        arr_like_status_1_filter_all.clear();
        for(int i=0; i<cuisionChefList.size(); i++){
            arr_like_status_1_filter_all.add(cuisionChefList.get(i).getDishlike());
        }
        adapter.notifyDataSetChanged();
    }

}