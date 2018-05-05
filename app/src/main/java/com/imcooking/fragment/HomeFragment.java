package com.imcooking.fragment;


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
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.CuisionAdatper;
import com.imcooking.adapters.HomeBottomPagerAdapter;
import com.imcooking.adapters.HomeDishPagerAdapter;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomViewPager;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, CuisionAdatper.OnItemClickListenerCategory {

    SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
    private HomeData homeData = new HomeData();
    private ArrayList<String>spinnerData =new ArrayList<>();

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
    HomeDishPagerAdapter adapter;
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
    CuisionAdatper cuisionAdatper;

    private CuisionAdatper.OnItemClickListenerCategory listener;

    private Spinner sp;


    private void init(){

        sp = getView().findViewById(R.id.home_spiner);
        layout = getView().findViewById(R.id.home_layout);
        cusine_list = getView().findViewById(R.id.home_cuisine_list);
        tv_cusine = getView().findViewById(R.id.home_cuisine);
        bottomViewPager = getView().findViewById(R.id.fragment_home_bottom);
        tv_cusine.setOnClickListener(this);

        arrow_show_detail = getView().findViewById(R.id.home_show_detail_1);
        txtCityName = getView().findViewById(R.id.fragment_home_txtcity);

        arrow_show_detail.setOnClickListener(this);
        cuisinRecycler = getView(). findViewById(R.id.fragment_home_cuisine_recycler);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cuisinRecycler.setLayoutManager(horizontalLayoutManagaer);
        spinnerData.add("0mile - 10miles");
        spinnerData.add("10miles - 20miles");
        spinnerData.add("20miles - 30miles");
        spinnerData.add("30miles - 40miles");


//        cuisionAdatper = new CuisionAdatper(getContext(),cuisionList);

    //    cuisinRecycler.setAdapter(cuisionAdatper);

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

//                str_licence_origin= (String) getKeyFromValue(SplashActivity.country,item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

            HomeDetails fragment = new HomeDetails();
            Bundle bundle = new Bundle();
            bundle.putString("dish_id", homeData.getChef_dish().get(0).getDish_id() + "");
            fragment.setArguments(bundle);

            BaseClass.callFragment(fragment, fragment
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

        layout.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtCityName.setText(MainActivity.stringBuffer.toString());
            }
        },3000);
        getHomeData();
        getCuisone();

    }

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

                cuisionAdatper = new CuisionAdatper(getContext(),cuisionList);
                cuisinRecycler.setAdapter(cuisionAdatper);
            }
        });
    }

    private void getHomeData(){

        String latitude = "28.5748119";
        String longitude = "7.0869837" ;
        String min_miles = "1";
        String max_miles = "50";
        String foodie_id = "4";
        String country = "101";

        Home data = new Home();
        data.setLatitude("28.5748119");
        data.setLongitude("77.0869837");
        data.setMin_miles("1");
        data.setMax_miles("50");
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
    List<HomeData.FavouriteDataBean>favouriteDataBeans = new ArrayList<>();
    private void setMyData(){
        if (chefDishBeans!=null){
            chefDishBeans.clear();
        }
        if (favouriteDataBeans!=null){
            favouriteDataBeans.clear();
        }
        chefDishBeans.addAll(homeData.getChef_dish());
        favouriteDataBeans.addAll(homeData.getFavourite_data());

        setMyViewPager();
        setBottomViewPager();
        milesSpinner();
    }

    private void setMyViewPager() {
        adapter = new HomeDishPagerAdapter(getContext(), getFragmentManager(),chefDishBeans);
        viewPager = (CustomViewPager) getView().findViewById(R.id.home_viewPager);
        viewPager.setAdapter(adapter);
    }

    private void setBottomViewPager() {
        Log.d("Debug", favouriteDataBeans.size() + "");
        homeBottomPagerAdapter = new HomeBottomPagerAdapter(getContext(), getFragmentManager(),favouriteDataBeans);
        bottomViewPager.setAdapter(homeBottomPagerAdapter);
    }


    @Override
    public void onItemClickCategory(int position) {
        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
    }
}
