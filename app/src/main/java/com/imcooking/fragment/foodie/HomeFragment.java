package com.imcooking.fragment.foodie;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapsInitializer;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.ApiRequest.Home;
import com.imcooking.Model.ApiRequest.SearchHomeRequest;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.Sub.Foodie.FilterHomeActivity;
import com.imcooking.activity.Sub.Foodie.SelectLocActivity;
import com.imcooking.activity.home.MainActivity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, CuisionAdatper.CuisionInterface ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
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
    List<HomeData.FavouriteDataBean>favorite_2 = new ArrayList<>();
    private Toolbar toolbar;
    CustomViewPager viewPager;
    HomeBottomPagerAdapter homeBottomPagerAdapter;
    private TextView tv_cusine, arrow_show_detail, txtCityName;
    private RecyclerView cuisinRecycler;
    private LinearLayout layout;
    ViewPager bottomViewPager;
    ImageView imgCart,imgFilter;
    boolean isApplyFiltered;
    CuisionAdatper cuisionAdatper;
    private Spinner sp;

    String latitudeq="51.5198117";
    String longitudeq="-0.0939186" ;
    String min_miles = "0";
    String max_miles = "10";
    public static String foodie_id = "4";
    String country = "101";
    private CuisineData cuisineData = new CuisineData();
    private List<CuisineData.CuisineDataBean>cuisionList=new ArrayList<>();

    public static TextView cart_icon;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

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
        if (BaseClass.checkGooglePlayService(getActivity())){
            setupLocation();
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

        layout_no_record_found.setVisibility(View.GONE);

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);
        getHomeData();

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
        cuisionAdatper = new CuisionAdatper(getContext(),cuisionList);
        cuisinRecycler.setAdapter(cuisionAdatper);
        cuisionAdatper.CuisionInterfaceMethod(this);
    }

    private void getHomeData(){
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
                        if (homeData.isStatus()) {
                            layout.setVisibility(View.VISIBLE);
                            setMyData();
//                            adapter.notifyDataSetChanged();
//                            homeBottomPagerAdapter.notifyDataSetChanged();
                        } else{
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("msg").equals("Not record found")){
                                    BaseClass.showToast(getContext(), "No record found");
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
        chefDishBeans.addAll(homeData.getChef_dish());
        if (homeData.getFavourite_data()!=null){
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
            startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                    userDataBean.getUser_id()));
           getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (v.getId()==R.id.fragment_home_txtcity){
            startActivityForResult(new Intent(getActivity(), SelectLocActivity.class),2);
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
                getHomeData();
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

    private GoogleApiClient mgoogleApiclient;

    public void checkGPSStatus()
    {
        LocationManager locationManager =(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSProviderEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isGPSProviderEnable)
        {
            showSettingsAlert();
        }
    }
    private void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
            alertDialog.setTitle(getResources().getString(R.string.gps_sett));

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.gps_not_enabled));

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton(getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
//                checkGPSStatus();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    protected synchronized void setupLocation() {
        if (BaseClass.requestPermissionToLocation(getActivity(),this)) {
            checkGPSStatus();
            mgoogleApiclient = new GoogleApiClient.Builder(getActivity()).
                    addConnectionCallbacks(this).
                    addOnConnectionFailedListener(this).
                    addApi(LocationServices.API).build();

            mgoogleApiclient.connect();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        android.location.Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mgoogleApiclient);
        if (mLocation != null) {
            latitudeq = mLocation.getLatitude()+"";
            longitudeq = mLocation.getLongitude()+"";
            Log.d("Current Locations",latitudeq+" , "+longitudeq);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private static final int REQUEST_LOCATION_PERMISSION = 2;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    setupLocation();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}
