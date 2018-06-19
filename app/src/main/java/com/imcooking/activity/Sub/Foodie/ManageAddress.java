package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.AddDelRequest;
import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;


import com.imcooking.adapters.AddListAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class ManageAddress extends AppBaseActivity implements AddListAdatper.AddInterfaceMethod{
   private TextView txtAddress;
   private List<AddressListData.AddressBean>addressBeanList = new ArrayList<>();
   private RecyclerView savedAddress;
   private TinyDB tinyDB;
   private Gson gson = new Gson();
   ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
   private String foodie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        tinyDB = new TinyDB(getApplicationContext());
        String s = tinyDB.getString("login_data");
        userDataBean = gson.fromJson(s, ApiResponse.UserDataBean.class);
        foodie_id = userDataBean.getUser_id()+"";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

//        find id
        txtAddress = findViewById(R.id.activity_manage_address_txtAddAddress);
        savedAddress  = findViewById(R.id.activity_manage_address_recycler);
        //layout = findViewById(R.id.address_layout);
        //layout.setVisibility(View.GONE);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        savedAddress.setLayoutManager(horizontalLayoutManagaer);
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageAddress.this, AddAddressActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private void getAddress(){
        if (addressBeanList!=null){
            addressBeanList.clear();
        }
        new GetData(getApplicationContext(), ManageAddress.this).getResponse("{\"foodie_id\":" +
                foodie_id + "}", "addresslist", new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                   //   layout.setVisibility(View.VISIBLE);
                      AddressListData addressListData = new AddressListData();
                      if (result!=null){
                          addressListData = new Gson().fromJson(result, AddressListData.class);
                          if (addressListData.getAddress()!=null){
                              addressBeanList.addAll(addressListData.getAddress());
                              setAddList();
                          }
                      }
                  }
              });
            }
        });
    }


    private AddListAdatper chefILoveAdatper;
    private void setAddList(){
        chefILoveAdatper = new AddListAdatper(getApplicationContext(), addressBeanList);
        savedAddress.setAdapter(chefILoveAdatper);
        chefILoveAdatper.AddInterfaceMethod(this);

    }

    @Override
    public void AddressInterfaceMethod(View view, int position, String tag) {
        if (tag.equals("edit")){
            startActivity(new Intent(ManageAddress.this, AddAddressActivity.class)
                    .putExtra("address_id",addressBeanList.get(position).getAddress_id()+"")
            .putExtra("name", addressBeanList.get(position).getAddress_address())
                    .putExtra("edit",true).putExtra("title", addressBeanList.get(position).getAddress_title()));
        } else if (tag.equals("delete")){
            deleteAdd(HomeFragment.foodie_id+"", addressBeanList.get(position).getAddress_id()+"", position);
        }
    }

    private void deleteAdd(String foodieid, String address_id, final int pos){
        AddDelRequest addDelRequest = new AddDelRequest();
        addDelRequest.setAddress_id(address_id);
        addDelRequest.setFoodie_id(foodieid);
        String request = new Gson().toJson(addDelRequest);
        new GetData(getApplicationContext(),ManageAddress.this).getResponse(request, "addressdelete", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("TAG", "data "+result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addressBeanList.remove(pos);
                        chefILoveAdatper.notifyItemRemoved(pos);
                        savedAddress.removeViewAt(pos);
                        chefILoveAdatper.notifyDataSetChanged();
                    //    getAddress();
                    }
                });
            }
        });
    }

}
