package com.imcooking.fragment.foodie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.Home;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.Sub.Foodie.FilterHomeActivity;
import com.imcooking.activity.Sub.Foodie.SelectLocActivity;
import com.imcooking.activity.home.HomeSearchActivity1;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.home.TestActivity;
import com.imcooking.adapters.CuisionAdatper;
import com.imcooking.adapters.HomeBottomPagerAdapter;
import com.imcooking.adapters.HomeDishPagerAdapter;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomViewPager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements LocationListener,
        View.OnClickListener, CuisionAdatper.CuisionInterface{
    public static TextView cart_icon;
    LocationManager locationManager;
    Button getLocationBtn;
    private HomeData homeData = new HomeData();
    private ArrayList<String>spinnerData =new ArrayList<>();
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private Gson gson = new Gson();
    private LinearLayout layout_no_record_found,cusine_list;
    private String TAG = HomeFragment.class.getName();
    List<HomeData.ChefDishBean>chefDishBeans = new ArrayList<>();
    List<HomeData.ChefDishBean>favorite_1 = new ArrayList<>();
    List<HomeData.FavouriteDataBean>favouriteDataBeans = new ArrayList<>();
    private Toolbar toolbar;
    CustomViewPager viewPager;
    HomeBottomPagerAdapter homeBottomPagerAdapter;
    private TextView tv_cusine,  txtCityName, txtSerach;
    private RecyclerView cuisinRecycler;
    private LinearLayout layout;
    ViewPager bottomViewPager;
    ImageView imgCart,imgFilter;
    CuisionAdatper cuisionAdatper;
    private Spinner sp;

    String latitudeq="";
    String longitudeq="" ;
    String min_miles = "0";
    String max_miles = "10";
    public static String foodie_id = "4";
    String country = "101";
    private CuisineData cuisineData = new CuisineData();
    private List<CuisineData.CuisineDataBean>cuisionList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLocationBtn = (Button)getView().findViewById(R.id.getLocationBtn);
        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        LinearLayout toolbar_left = getView().findViewById(R.id.toolbar_left);
        toolbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).drawerLayout1.openDrawer(GravityCompat.START);
            }
        });
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, TestActivity.requestcode);
        }

        init();
    }

    private void init(){

        cart_icon = getView().findViewById(R.id.home_cart_text_count);
        layout_no_record_found = getView().findViewById(R.id.home_no_record_image);

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
        try {
            MapsInitializer.initialize(this.getContext());
        } catch (Exception e) {
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
        layout.setVisibility(View.GONE);

        getCuisone();
        milesSpinner();
//        if (txtCityName.getText().toString().isEmpty()){
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    txtCityName.setText(stringBuffer.toString());
//                    latitudeq = MainActivity.latitude+"";
//                    longitudeq = MainActivity.longitude+"";
//
//                }
//            },3000);
//        }

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

        getLocation();

        layout_no_record_found.setVisibility(View.GONE);

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
                        setCuisionAdapter();
                    }
                });
    }

    private void setCuisionAdapter(){
        cuisionAdatper = new CuisionAdatper(getContext(),this,cuisionList);
        cuisinRecycler.setAdapter(cuisionAdatper);
        cuisionAdatper.CuisionInterfaceMethod(this);
    }

    private void getHomeData(String latitudeq, String longitudeq){
      /*  Home data = new Home();
        data.setLatitude(latitudeq);
        data.setLongitude(longitudeq);
        data.setMin_miles(min_miles);
        data.setMax_miles(max_miles);
        data.setCountry("");
        data.setFoodie_id(foodie_id);*/
        Home data = new Home();
        data.setLatitude(latitudeq);
        data.setLongitude(longitudeq);
        data.setMin_miles(min_miles);
        data.setMax_miles(max_miles);
        data.setCountry("");
        data.setFoodie_id(foodie_id);
        Log.d("MyRequest", new Gson().toJson(data));
        new GetData(getContext(), getActivity()).getResponse(new Gson().toJson(data), "home", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.d("VK", result);
                final String response = result;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: data"+response.toString());
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
                            layout.setVisibility(View.VISIBLE);
                            viewPager.setVisibility(View.VISIBLE);
                            layout_no_record_found.setVisibility(View.GONE);

                            setMyData();
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

                                    List<HomeData.FavouriteDataBean> list = new ArrayList<>();
//
                                    list = Arrays.asList(new Gson().fromJson(jar.toString(), HomeData.FavouriteDataBean[].class));
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
                                    setBottomViewPager(list);
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

    private void setMyData(){
        if (chefDishBeans!=null){
            chefDishBeans.clear();
        }
        if (favouriteDataBeans!=null){
            favouriteDataBeans.clear();
        }
        if (homeData.getChef_dish()!=null&&homeData.getChef_dish().size()>0){
            chefDishBeans.addAll(homeData.getChef_dish());
        }
        if (homeData.getFavourite_data()!=null&&homeData.getFavourite_data().size()>0){
            favouriteDataBeans.addAll(homeData.getFavourite_data());
        }
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
        filterCuisine(position,cuisionList.get(position).getCuisine_name());
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
        }
        else if (v.getId()==R.id.fragment_home_img_filter){
            startActivityForResult(new Intent(getContext(), FilterHomeActivity.class),1);
        }
        else if (v.getId() == R.id.fragment_home_img_cart)
        {
            startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                    userDataBean.getUser_id()));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (v.getId()==R.id.fragment_home_txtcity){
            startActivityForResult(new Intent(getActivity(), SelectLocActivity.class),2);
        } else if (v.getId()==R.id.fragment_home_search_img){
            startActivity(new Intent(getContext(), HomeSearchActivity1.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
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
            } else if (requestCode==2){
                latitudeq = data.getDoubleExtra("latitude",0)+"";
                longitudeq = data.getDoubleExtra("longitude",0)+"";
                txtCityName.setText(data.getStringExtra("name"));
                Log.d(TAG, "onActivityResult: "+latitudeq+"\n"+longitudeq+"\n");
                getHomeData(latitudeq, longitudeq);
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
       /* favorite_2.clear();
        for(int i=0; i<favouriteDataBeans.size(); i++){
            HomeData.FavouriteDataBean list = favouriteDataBeans.get(i);

            if(list.getRating().equals((rating.intValue() + "")) *//*&& list.getDish_price().equals(price + "")*//*){
                favorite_2.add(list);
            }
        }

        setBottomViewPager(favorite_2);*/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case requestcode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                return;
            }
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    public final int requestcode = 101;

    @Override
    public void onLocationChanged(Location location) {
        latitudeq = location.getLatitude()+"";
        longitudeq = location.getLongitude()+"";
        getHomeData(latitudeq, longitudeq);

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer = getAddress(new LatLng(location.getLatitude(), location.getLongitude()));
            txtCityName.setText(stringBuffer.toString());

        }
        catch(Exception e) {

        }
    }

    public StringBuffer getAddress(LatLng latLng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        StringBuffer result = new StringBuffer();
        geocoder = new Geocoder(getContext(), Locale.getDefault());

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

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    List<HomeData.ChefDishBean>cuisionChefList;

    private void filterCuisine(int position, String cuision){
        cuisionChefList = new ArrayList<>();
        for (HomeData.ChefDishBean  bean: chefDishBeans){
            if (bean.getDish_cuisine()!=null&&bean.getDish_cuisine().size()>0){
                String cuisionVa = bean.getDish_cuisine().get(0).getCuisine_name();
                if (cuision.equalsIgnoreCase(cuisionVa)){
                    cuisionChefList.add(chefDishBeans.get(position));
                }
            }
        }
        if (cuisionChefList.size()>0){
            layout_no_record_found.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            setMyViewPager(cuisionChefList);

        } else {
            layout_no_record_found.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        }
    }
}
