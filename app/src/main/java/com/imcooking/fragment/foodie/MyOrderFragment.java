package com.imcooking.fragment.foodie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.FoodieMyorder;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.AdapterFoodieMyOrder;
import com.imcooking.adapters.AdapterFoodieMyRequest;
import com.imcooking.adapters.CartAdatper;
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
public class MyOrderFragment extends Fragment {
    TinyDB tinyDB;
List<FoodieMyorder>fooodieorderList=new ArrayList<>();
    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        init();
    }

    private RecyclerView rv_1, rv_2;

    private void init(){

        rv_1 = getView().findViewById(R.id.recycler_foodie_my_orders_current);
        CustomLayoutManager manager = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_1.setLayoutManager(manager);

        rv_2 = getView().findViewById(R.id.recycler_foodie_my_orders_past);
        CustomLayoutManager manager1 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_2.setLayoutManager(manager1);
        tinyDB=new TinyDB(getContext());
        getorderList();

        //        setMyAdapter();

    }

    public void getorderList(){

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";

       // String s = "{\"foodie_id\":" + user_id + "}";
        String s = "{\"foodie_id\": 4}";
        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, "myorder", getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {

                    FoodieMyorder foodieMyorder = new FoodieMyorder();

                    foodieMyorder = new Gson().fromJson(result, FoodieMyorder.class);

                    if(foodieMyorder.isStatus()){
                        if(!foodieMyorder.getFoodie_order_list().isEmpty()){

                            setMyAdapter(foodieMyorder.getFoodie_order_list());

                        }
                        else {
                            Toast.makeText(getContext(), "order list is empty", Toast.LENGTH_SHORT).show();
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

    private void setMyAdapter(List<FoodieMyorder.FoodieOrderListBean> arrayList){
        AdapterFoodieMyOrder adapterFoodieMyOrder = new AdapterFoodieMyOrder(getContext(),
                getFragmentManager(), arrayList);
        rv_1.setAdapter(adapterFoodieMyOrder);
        rv_2.setAdapter(adapterFoodieMyOrder);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_my_order.setImageResource(R.drawable.ic_salad_1);

    }
}
