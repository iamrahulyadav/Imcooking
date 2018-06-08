package com.imcooking.fragment.chef.chefprofile;

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
import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
import com.imcooking.adapters.AdatperChefMyOrderList;
import com.imcooking.adapters.AdatperChefMyRequestList;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chef_request_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        requestRecyclerView = getView().findViewById(R.id.fragment_chef_request_list_recycler);
tinyDB=new TinyDB(getContext());
        CustomLayoutManager manager = new CustomLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

        };
getorderList();

    }

    public void getorderList(){

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";

        // String s = "{\"chef_id\":" + user_id + "}";

        String s = "{\"chef_id\": 1}";
        Log.d("Tags",s);
        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, "chef_order_list", getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            ChefMyorderList chefMyorderList = new ChefMyorderList();

                            chefMyorderList = new Gson().fromJson(result, ChefMyorderList.class);

                            if(chefMyorderList.isStatus()){
                                if(!chefMyorderList.getMy_order_list().isEmpty()){

                                    setDishAdapter(chefMyorderList.getMy_order_list());

                                }
                                else {
                                    Toast.makeText(getContext(), "request list is empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "spmething went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();

    private void setDishAdapter(List<ChefMyorderList.MyOrderListBean> list) {
        AdatperChefMyRequestList chefMyRequestsAdatper = new AdatperChefMyRequestList(getContext(),list);
        requestRecyclerView.setAdapter(chefMyRequestsAdatper);

    }

}

