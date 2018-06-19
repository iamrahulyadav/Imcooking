package com.imcooking.fragment.chef.chefprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefDishRequestData;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
import com.imcooking.adapters.AdatperChefMyOrderList;
import com.imcooking.adapters.AdatperChefMyRequestList;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ChefMyRequestFragment extends Fragment {

    TinyDB tinyDB;
    private RecyclerView requestRecyclerView;
    private LinearLayout no_recordLayout;
    private LinearLayout layoutToolbar;
    private String strtext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chef_request_list, container, false);
        if (getArguments()!=null){
            strtext=getArguments().getString("message");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        no_recordLayout = getView().findViewById(R.id.fragment_my_request_chef_no_record_image);
        requestRecyclerView = getView().findViewById(R.id.fragment_chef_request_list_recycler);
        layoutToolbar = getView().findViewById(R.id.chefrequest_txtname);
        tinyDB=new TinyDB(getContext());
        CustomLayoutManager manager = new CustomLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

        };
        if (strtext!=null){
            layoutToolbar.setVisibility(View.VISIBLE);
        }
        requestRecyclerView.setLayoutManager(manager);
        getorderList();

    }

    public void getorderList(){

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";
        String s = "{\"chef_id\":" + user_id + "}";

        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, GetData.DISH_REQUEST_LIST, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ChefDishRequestData chefMyorderList = new ChefDishRequestData();
                            chefMyorderList = new Gson().fromJson(result, ChefDishRequestData.class);

                            if(chefMyorderList.isStatus()){
                                if(chefMyorderList.getChef_dish_details()!=null && chefMyorderList.getChef_dish_details().size()>0){
                                    requestRecyclerView.setVisibility(View.VISIBLE);
                                    no_recordLayout.setVisibility(View.GONE);
                                    setDishAdapter(chefMyorderList.getChef_dish_details());
                                }
                                else {
                                    if (strtext!=null){
                                        layoutToolbar.setVisibility(View.VISIBLE);
                                    }
                                    requestRecyclerView.setVisibility(View.GONE);
                                    no_recordLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            else {
                                BaseClass.showToast(getContext(),getResources().getString(R.string.error));
                            }
                        }
                    });



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();

    private void setDishAdapter(List<ChefDishRequestData.ChefDishDetailsBean> list) {
        AdatperChefMyRequestList chefMyRequestsAdatper = new AdatperChefMyRequestList(getContext(),list);
        requestRecyclerView.setAdapter(chefMyRequestsAdatper);

    }

}

