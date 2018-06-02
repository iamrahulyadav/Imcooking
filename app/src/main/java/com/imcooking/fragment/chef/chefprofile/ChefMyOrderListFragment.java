package com.imcooking.fragment.chef.chefprofile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
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
public class ChefMyOrderListFragment extends Fragment {
TinyDB tinyDB;
List<ChefMyorderList.MyOrderListBean>list=new ArrayList<>();
RecyclerView recyclerView;
   public ChefMyOrderListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        init();
        return inflater.inflate(R.layout.fragment_chef_order_list, container, false);
    }
    private void init() {

        tinyDB=new TinyDB(getContext());

        recyclerView = getView().findViewById(R.id.fragment_chef_order_list_recycler);
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

    public void getorderList(){

        String login = tinyDB.getString("login_data");
        ApiResponse.UserDataBean apiResponse = new ApiResponse.UserDataBean();
        apiResponse = new Gson().fromJson(login,ApiResponse.UserDataBean.class);
        String user_id=apiResponse.getUser_id()+"";

        // String s = "{\"chef_id\":" + user_id + "}";
        String s = "{\"Chef_id\": 1}";
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

                                    setMyAdapter(chefMyorderList.getMy_order_list());

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
    private void setMyAdapter(List<ChefMyorderList.MyOrderListBean> list){
        AdatperChefMyOrderList adatperChefMyOrderList = new AdatperChefMyOrderList(getContext(),
                getFragmentManager(), list);
        recyclerView.setAdapter(adatperChefMyOrderList);
    }
}
