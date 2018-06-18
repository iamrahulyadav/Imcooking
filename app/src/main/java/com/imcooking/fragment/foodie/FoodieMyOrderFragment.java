package com.imcooking.fragment.foodie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.FoodieMyorderList;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.AdapterFoodieMyOrderList;
import com.imcooking.utils.BaseClass;
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
public class FoodieMyOrderFragment extends Fragment implements AdapterFoodieMyOrderList.MyorderInterface{
    TinyDB tinyDB;
    private LinearLayout no_record_Layout;
    private NestedScrollView nestedScrollView;
    private TextView txtShop;
    public FoodieMyOrderFragment() {
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
        nestedScrollView = getView().findViewById(R.id.foodie_order_list);
        rv_1 = getView().findViewById(R.id.recycler_foodie_my_orders_current);
        no_record_Layout = getView().findViewById(R.id.fragment_my_order_foodie_no_record_image);
        txtShop = getView().findViewById(R.id.fragment_my_order_shop_now);

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

        txtShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.callFragment(new HomeFragment(), HomeFragment.class.getName(), getFragmentManager());
            }
        });
        getorderList();
    }

    public void getorderList(){
        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id = apiResponse.getUser_id()+"";
        String s = "{\"foodie_id\": "+user_id+"}";

        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, "myorder", getActivity(),
                    new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {

                    FoodieMyorderList foodieMyorder = new FoodieMyorderList();
                    if (visibilityArray!=null){
                        visibilityArray.clear();
                    }

                    foodieMyorder = new Gson().fromJson(result, FoodieMyorderList.class);

                    if(foodieMyorder.isStatus()){
                        if(!foodieMyorder.getFoodie_order_list().isEmpty()){
                            for (int i=0;i<foodieMyorder.getFoodie_order_list().size();i++){
                                visibilityArray.add(false);
                            }
                            nestedScrollView.setVisibility(View.VISIBLE);
                            no_record_Layout.setVisibility(View.GONE);
                            setMyAdapter(foodieMyorder.getFoodie_order_list());
                        }
                        else {
                            nestedScrollView.setVisibility(View.GONE);
                            no_record_Layout.setVisibility(View.VISIBLE);
//                            Toast.makeText(getContext(), "Order list is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {

                        Toast.makeText(getContext(), "Something went wrong .", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    AdapterFoodieMyOrderList adapterFoodieMyOrder;
    private void setMyAdapter(List<FoodieMyorderList.FoodieOrderListBean> arrayList){
       adapterFoodieMyOrder = new AdapterFoodieMyOrderList(getContext(),
                getFragmentManager(), arrayList, this, visibilityArray);
        rv_1.setAdapter(adapterFoodieMyOrder);

//        rv_2.setAdapter(adapterFoodieMyOrder);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_my_order.setImageResource(R.drawable.ic_salad_1);

    }

    private ArrayList<Boolean>visibilityArray = new ArrayList<>();

    @Override
    public void getDetails(int position) {
        if (visibilityArray.get(position)) visibilityArray.set(position,false);
        else visibilityArray.set(position, true);
        adapterFoodieMyOrder.notifyDataSetChanged();
        BaseClass.showToast(getContext(), position+"");
    }
}
