package com.imcooking.fragment.foodie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.FoodieMyorderList;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefOrderDetailsActivity;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.AdapterFoodieMyOrderList;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodieMyOrderFragment extends Fragment implements AdapterFoodieMyOrderList.MyorderInterface{
    TinyDB tinyDB;
    private LinearLayout no_record_Layout;
    private NestedScrollView nestedScrollView;
    private TextView txtShop, txtDayTime;
    private RelativeLayout currentLayout;

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
    private String current_time;

    private ImageView iv_cart;
    private TextView tv_count;

    private void init(){

        tinyDB = new TinyDB(getContext());

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        final String user_id = apiResponse.getUser_id()+"";

        tv_count = getView().findViewById(R.id.fragment_my_order_cart_count);


        iv_cart = getView().findViewById(R.id.fragment_my_order_img_cart);
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                        user_id));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        currentLayout = getView().findViewById(R.id.fragment_chef_order_txt_current);
        nestedScrollView = getView().findViewById(R.id.foodie_order_list);
        rv_1 = getView().findViewById(R.id.recycler_foodie_my_orders_current);
        no_record_Layout = getView().findViewById(R.id.fragment_my_order_foodie_no_record_image);
        txtShop = getView().findViewById(R.id.fragment_my_order_shop_now);
        txtDayTime = getView().findViewById(R.id.fragment_my_order_current_time);
        Date currentTime = Calendar.getInstance().getTime();
        txtDayTime.setText(new SimpleDateFormat("EEEE . HH:mm aa").format(currentTime));

        Log.d("CurrentTime", new SimpleDateFormat("HH:mm aa").format(currentTime));

//        current_time = new SimpleDateFormat("HH:mm aa").format(currentTime);

        CustomLayoutManager manager = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        rv_1.setLayoutManager(manager);

        rv_2 = getView().findViewById(R.id.recycler_foodie_my_orders_past);
        CustomLayoutManager manager1 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_2.setLayoutManager(manager1);

        txtShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.callFragment(new HomeFragment(), HomeFragment.class.getName(), getFragmentManager());
            }
        });
        getorderList();
    }
    List<FoodieMyorderList.FoodieOrderListBean>currentOrderListBeans;
    List<FoodieMyorderList.FoodieOrderListBean>prevoiusOrderListBeans;

    public void getorderList(){
        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id = apiResponse.getUser_id()+"";
        String s = "{\"foodie_id\": "+user_id+"}";

        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, GetData.FOODIE_MY_ORDER, getActivity(),
                    new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    @SuppressLint("SimpleDateFormat")
                    Date dt = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(dt);
                    c.add(Calendar.DATE, 1);
                    dt = c.getTime();
//                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

                    FoodieMyorderList foodieMyorder = new FoodieMyorderList();
                    foodieMyorder = new Gson().fromJson(result, FoodieMyorderList.class);

                    if(foodieMyorder.isStatus()){
                        if(!foodieMyorder.getFoodie_order_list().isEmpty()){

                            nestedScrollView.setVisibility(View.VISIBLE);
                            no_record_Layout.setVisibility(View.GONE);
                            currentOrderListBeans = new ArrayList<>();
                            prevoiusOrderListBeans = new ArrayList<>();

                            for (FoodieMyorderList.FoodieOrderListBean orderListBean : foodieMyorder.getFoodie_order_list()){
                                String status = orderListBean.getOrder_status();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date date1 = null;
                                try {
                                    date1 = format.parse(timeStamp);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date date2 = null;
                                try {
                                    date2 = format.parse(orderListBean.getBookdate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (date2.compareTo(date1) >0||date2.compareTo(date1) ==0 &&(!status.equals("2")
                                        && !status.equals("8")&& !status.equals("9"))) {
                                    currentOrderListBeans.add(orderListBean);
                                    Collections.sort(currentOrderListBeans, new Comparator<FoodieMyorderList.FoodieOrderListBean>() {
                                        @Override
                                        public int compare(FoodieMyorderList.FoodieOrderListBean foodieOrderListBean,
                                                           FoodieMyorderList.FoodieOrderListBean t1) {
                                            if (foodieOrderListBean.getBookdate() == null || t1.getBookdate() == null)
                                                return 0;
                                            return t1.getBookdate().compareTo(foodieOrderListBean.getBookdate());
                                        }
                                    });
                                } else {
                                    prevoiusOrderListBeans.add(orderListBean);
                                }
                            }
                            setMyAdapter(currentOrderListBeans, prevoiusOrderListBeans);
                        }
                        else {
                            nestedScrollView.setVisibility(View.GONE);
                            no_record_Layout.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        BaseClass.showToast(getContext(), getResources().getString(R.string.error));
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private AdapterFoodieMyOrderList adapterFoodieMyOrder, adapterFoodieMyOrder1;

    private void setMyAdapter(List<FoodieMyorderList.FoodieOrderListBean> currentOrderListBeans,
                              List<FoodieMyorderList.FoodieOrderListBean> arrayList){
        if (currentOrderListBeans.size()>0){
            adapterFoodieMyOrder = new AdapterFoodieMyOrderList(getContext(),
                    getFragmentManager(), currentOrderListBeans, this, "current");
            rv_1.setAdapter(adapterFoodieMyOrder);
            currentLayout.setVisibility(View.VISIBLE);
        } else currentLayout.setVisibility(View.GONE);

        adapterFoodieMyOrder1 = new AdapterFoodieMyOrderList(getContext(),
                getFragmentManager(), arrayList, this,  "previous");

        rv_2.setAdapter(adapterFoodieMyOrder1);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_my_order.setImageResource(R.drawable.ic_salad_1);

        String cart_count = tinyDB.getString("cart_count");
        tv_count.setText(cart_count);

    }


    @Override
    public void getDetails(int position, String TAG) {
       if (TAG.equals("current")){
           startActivity(new Intent(getActivity(), ChefOrderDetailsActivity.class)
                   .putExtra("order_id",currentOrderListBeans.get(position).getOrder_order_id()));
           adapterFoodieMyOrder.notifyDataSetChanged();
       } else if (TAG.equals("previous")){
           startActivity(new Intent(getActivity(), ChefOrderDetailsActivity.class)
                   .putExtra("order_id",prevoiusOrderListBeans.get(position).getOrder_order_id()));
           adapterFoodieMyOrder1.notifyDataSetChanged();
       } else if (TAG.equals("ratingbar")){
           BaseClass.showToast(getContext(), "Rate & Review");
       } else {

       }

    }
}
