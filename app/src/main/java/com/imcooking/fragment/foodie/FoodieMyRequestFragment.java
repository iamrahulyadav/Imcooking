package com.imcooking.fragment.foodie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.adapters.AdapterFoodieMyRequest;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class FoodieMyRequestFragment extends Fragment {

    TinyDB tinyDB;
    private LinearLayout no_recordLayout;
    private TextView txtShop;
    private List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list = new ArrayList();
    public FoodieMyRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foodie_my_request, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        init();
    }

    private RecyclerView rv;

    private void init(){
        txtShop = getView().findViewById(R.id.fragment_my_request_shop_now);
        no_recordLayout = getView().findViewById(R.id.fragment_my_request_foodie_no_record_image);
        rv = getView().findViewById(R.id.recycler_foodie_my_requests);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        myorderRequest();

        txtShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.callFragment(new HomeFragment(), HomeFragment.class.getName(), getFragmentManager());
            }
        });
    }


    public void myorderRequest(){
        tinyDB=new TinyDB(getContext());
        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        int user_id=apiResponse.getUser_id();

        String s = "{\"foodie_id\":" + user_id + "}";
        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, "foodie_myrequestdish_chefdetails",
                    getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(final String result) {
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FoodieMyRequest foodieMyRequest = new FoodieMyRequest();

                                foodieMyRequest = new Gson().fromJson(result, FoodieMyRequest.class);

                                if(foodieMyRequest.isStatus()){
                                    if(!foodieMyRequest.getFoodie_request_dish_chef_details().isEmpty()){
                                        rv.setVisibility(View.VISIBLE);
                                        no_recordLayout.setVisibility(View.GONE);
                                        setMyAdapter(foodieMyRequest.getFoodie_request_dish_chef_details());
                                    }
                                    else {
                                        rv.setVisibility(View.GONE);
                                        no_recordLayout.setVisibility(View.VISIBLE);
/*
                                        BaseClass.showToast(getContext(),"No request found");
*/
                                    }
                                }
                                else {

                                    BaseClass.showToast(getContext(), "Something went wrong");
                                }
                            }
                        });
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMyAdapter(List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list ){
        AdapterFoodieMyRequest adatper = new AdapterFoodieMyRequest(getContext(),list);
        rv.setAdapter(adatper);
    }


}