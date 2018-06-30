package com.imcooking.fragment.chef;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefFollowers;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
import com.imcooking.adapters.AdatperChefFollowers;
import com.imcooking.adapters.AdatperChefMyOrderList;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefFollowersFragment extends Fragment {

    TinyDB tinyDB;
    List<ChefFollowers.FoodieDetailsListBean> list=new ArrayList<>();
    private RecyclerView recyclerView;

    public ChefFollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_folowers, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();
    }

    private void init() {

        tinyDB=new TinyDB(getContext());

        recyclerView = getView().findViewById(R.id.fragment_chef_followers_recycler);
        CustomLayoutManager manager1 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager1);

        getorderList();


    }

    public void getorderList(){

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";

         String s = "{\"chef_id\":" + user_id + "}";
//       String s = "{\"Chef_id\": 5}";
        Log.d("MyRequest", s);
        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, GetData.FOODIE_DETAILS_FOLLOW_CHEF, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ChefFollowers chefFollowers = new ChefFollowers();
                            chefFollowers = new Gson().fromJson(result, ChefFollowers.class);
                            if(chefFollowers.isStatus()){
                                if(!chefFollowers.getFoodie_details_list().isEmpty()){
                                    setMyAdapter(chefFollowers.getFoodie_details_list());
                                }
                                else {
                                    Toast.makeText(getContext(), "Followers list is empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMyAdapter(List<ChefFollowers.FoodieDetailsListBean> list){
        AdatperChefFollowers adatperChefFollowers = new AdatperChefFollowers(getContext(),
                getFragmentManager(), list);
        recyclerView.setAdapter(adatperChefFollowers);
    }

}
