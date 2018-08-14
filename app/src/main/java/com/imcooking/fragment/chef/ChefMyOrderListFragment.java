package com.imcooking.fragment.chef;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.Model.api.response.FoodieMyorderList;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefOrderDetailsActivity;
import com.imcooking.adapters.AdatperChefMyOrderList;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
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
public class ChefMyOrderListFragment extends Fragment implements AdatperChefMyOrderList.ChefMyOrderInterface{
    TinyDB tinyDB;
    RecyclerView recyclerView, rv_prevoius;
    private LinearLayout no_record_Layout;
    private NestedScrollView nestedScrollView ;
    private RelativeLayout currentLayout;

   public ChefMyOrderListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_chef_order_list, container, false);
        init(view);
        return view;
    }

    private TextView tv_current_time;
    private void init(View view) {

        tinyDB=new TinyDB(getContext());

        rv_prevoius = view.findViewById(R.id.recycler_chef_my_orders_past);
        recyclerView = view.findViewById(R.id.fragment_chef_order_list_recycler);
        no_record_Layout = view.findViewById(R.id.fragment_my_order_chef_no_record_image);
        nestedScrollView = view.findViewById(R.id.chef_order_list_scroll);
        currentLayout = view.findViewById(R.id.fragment_chef_order_txt_current);

        CustomLayoutManager manager1 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager1);
        CustomLayoutManager manager2 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_prevoius.setLayoutManager(manager2);

        tv_current_time = view.findViewById(R.id.fragment_my_order_current_time);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        Date d1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = sdf1.format(d1);

//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        tv_current_time.setText(dayOfTheWeek + "  " + currentDateTimeString);

    }

    @Override
    public void onResume() {
        super.onResume();
        getorderList();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private List<ChefMyorderList.MyOrderListBean> currentOrderListBeans;
    private List<ChefMyorderList.MyOrderListBean> prevoiusOrderListBeans;

    public void getorderList(){
        currentOrderListBeans = new ArrayList<>();
        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        String s = "{\"chef_id\": "+user_id+"}";
        Log.d("Tags",s);

        try {
            JSONObject job = new JSONObject(s);
            new GetData(getContext(), getActivity()).sendMyData(job, GetData.CHEF_ORDER_LIST, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            ChefMyorderList chefMyorderList = new ChefMyorderList();
                            chefMyorderList = new Gson().fromJson(result, ChefMyorderList.class);
                            prevoiusOrderListBeans = new ArrayList<>();
                            if(chefMyorderList.isStatus()){
                              if(chefMyorderList.getMy_order_list()!=null && chefMyorderList.getMy_order_list().size()>0){
                                  nestedScrollView.setVisibility(View.VISIBLE);
                                  no_record_Layout.setVisibility(View.GONE);

                                  for (ChefMyorderList.MyOrderListBean orderListBean : chefMyorderList.getMy_order_list()){
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
                                      if (date2.compareTo(date1) >0||date2.compareTo(date1) ==0 &&(!status.equals("2") && !status.equals("8")&&
                                              !status.equals("9"))) {
                                          currentOrderListBeans.add(orderListBean);
                                            Collections.sort(currentOrderListBeans, new Comparator<ChefMyorderList.MyOrderListBean>() {
                                                @Override
                                                public int compare(ChefMyorderList.MyOrderListBean myOrderListBean, ChefMyorderList.MyOrderListBean t1) {
                                                    if (myOrderListBean.getBookdate() == null || t1.getBookdate() == null)
                                                        return 0;
                                                    return t1.getBookdate().compareTo(myOrderListBean.getBookdate());
                                                }
                                            });
                                      } else {
                                          prevoiusOrderListBeans.add(orderListBean);
                                      }
                                  }
                                  setMyAdapter(currentOrderListBeans);
                                } else {
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

    private void setMyAdapter(List<ChefMyorderList.MyOrderListBean> list){
        if (list.size()>0){
            AdatperChefMyOrderList adatperChefMyOrderList = new AdatperChefMyOrderList(getContext(),
                    list, this,"current");
            recyclerView.setAdapter(adatperChefMyOrderList);
            currentLayout.setVisibility(View.VISIBLE);
        } else currentLayout.setVisibility(View.GONE);


        AdatperChefMyOrderList adatperChefMyOrderList1 = new AdatperChefMyOrderList(getContext(),
                prevoiusOrderListBeans, this, "previous");
        rv_prevoius.setAdapter(adatperChefMyOrderList1);
    }

    @Override
    public void chefOrderdetails(int pos, String TAG) {
        if (TAG.equals("current")){
            String orderid = currentOrderListBeans.get(pos).getOrder_order_id();
            startActivity(new Intent(getActivity(), ChefOrderDetailsActivity.class).putExtra("order_id", orderid));
        } else if (TAG.equals("previous")){
            String orderid = prevoiusOrderListBeans.get(pos).getOrder_order_id();
            startActivity(new Intent(getActivity(), ChefOrderDetailsActivity.class).putExtra("order_id", orderid));
        }
    }
}