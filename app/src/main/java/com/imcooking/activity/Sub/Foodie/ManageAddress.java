package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.R;


import com.imcooking.activity.Sub.Foodie.AddAddressActivity;
import com.imcooking.adapters.AddListAdatper;
import com.imcooking.adapters.ChefILoveAdatper;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;

import java.util.ArrayList;
import java.util.List;

public class ManageAddress extends AppBaseActivity {
   private TextView txtAddress;
   private List<AddressListData.AddressBean>addressBeanList = new ArrayList<>();
   private RecyclerView savedAddress;
 //  CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);

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
        new GetData(getApplicationContext(), ManageAddress.this).getResponse("{\"foodie_id\": \"4\"}", "addresslist", new GetData.MyCallback() {
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

    private void setAddList(){
        AddListAdatper chefILoveAdatper = new AddListAdatper(getApplicationContext(), addressBeanList);
        savedAddress.setAdapter(chefILoveAdatper);

    }
}
