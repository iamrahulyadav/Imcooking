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
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefOrderDetailsActivity;
import com.imcooking.adapters.AdatperChefMyOrderList;
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
public class ChefMyOrderListFragment extends Fragment implements AdatperChefMyOrderList.ChefMyOrderInterface{
    TinyDB tinyDB;
    RecyclerView recyclerView;
    private LinearLayout no_record_Layout;
    private NestedScrollView nestedScrollView ;
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


    private void init(View view) {

        tinyDB=new TinyDB(getContext());
        getorderList();
        recyclerView = view.findViewById(R.id.fragment_chef_order_list_recycler);
        no_record_Layout = view.findViewById(R.id.fragment_my_order_chef_no_record_image);
        nestedScrollView = view.findViewById(R.id.chef_order_list_scroll);
        CustomLayoutManager manager1 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager1);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private List<ChefMyorderList.MyOrderListBean> list;
    public void getorderList(){
        list = new ArrayList<>();
        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";

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
                            if(chefMyorderList.isStatus()){
                              if(chefMyorderList.getMy_order_list()!=null && chefMyorderList.getMy_order_list().size()>0){
                                  list.addAll(chefMyorderList.getMy_order_list());
                                  nestedScrollView.setVisibility(View.VISIBLE);
                                  no_record_Layout.setVisibility(View.GONE);
                                  setMyAdapter(list);
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

    private void setMyAdapter(List<ChefMyorderList.MyOrderListBean> list){
        AdatperChefMyOrderList adatperChefMyOrderList = new AdatperChefMyOrderList(getContext(),
                 list, this);
        recyclerView.setAdapter(adatperChefMyOrderList);
    }


    @Override
    public void chefOrderdetails(int pos) {
       String orderid = list.get(pos).getOrder_order_id();
       startActivity(new Intent(getActivity(), ChefOrderDetailsActivity.class).putExtra("order_id", orderid));
    }


}
