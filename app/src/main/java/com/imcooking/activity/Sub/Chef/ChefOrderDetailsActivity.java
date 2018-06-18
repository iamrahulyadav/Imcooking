package com.imcooking.activity.Sub.Chef;

import android.app.Dialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imcooking.Model.api.response.OrderDetailsData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterFoodieMyOrderDetailList;
import com.imcooking.adapters.ChefILoveAdatper;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChefOrderDetailsActivity extends AppBaseActivity {
    private RecyclerView recyclerView;
    private String order_id, delivery_type, chef_status, chef_id, foodie_id="107", request_id;
    private TextView txt_chef_name, txt_order_type,txt_date, txtAddress, txt_total_price,
            txt_qyt, txt_price, txt_email, txt_pay_mode,txt_phone,txt_order_status,
            txt_order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_order_details);
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        chef_id = ChefHome.chef_id;

        if (getIntent().hasExtra("order_id")){
            order_id = getIntent().getStringExtra("order_id");
        }
        init();
    }

    private void init(){

        recyclerView = findViewById(R.id.order_details_recycler);
        txt_chef_name = findViewById(R.id.item_foodie_my_order_chef);
        txt_date = findViewById(R.id.item_foodie_my_order_placed_date);
        txt_total_price = findViewById(R.id.item_foodie_my_order_total_price);
        txt_qyt = findViewById(R.id.item_foodie_my_order_qyt);
        txt_order_type = findViewById(R.id.item_foodie_my_order_type);
        txt_price = findViewById(R.id.item_foodie_my_order_price);
        txt_email = findViewById(R.id.item_foodie_my_order_foodie_email);
        txt_pay_mode = findViewById(R.id.item_foodie_my_order_payment_mode);
        txt_order_id = findViewById(R.id.item_foodie_my_order_id);
        txtAddress = findViewById(R.id.item_foodie_my_order_address);
        txt_phone = findViewById(R.id.item_foodie_my_order_foodie_phone);
        txt_order_status = findViewById(R.id.item_foodie_my_order_details_status);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        txt_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delivery_type.equals("1")){
                  //1=delivery
                    txt_order_status.setText("Delivery");
                    createDialog(delivery_type);
                } else if (delivery_type.equals("2")){
                    //2=pickup
                    txt_order_status.setText("Pickup");
                    createDialog(delivery_type);
                }
            }
        });

        getOrderDetails();
    }

    private List<OrderDetailsData.OrderDetailsBean>orderDetailsBeans;

    private void getOrderDetails(){
        String s = "{\n" +
                " \"order_id\":\""+order_id+"\"\n" +
                "}";
        orderDetailsBeans = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getApplicationContext(), ChefOrderDetailsActivity.this).sendMyData(jsonObject,
                    GetData.ORDER_DETAILS, ChefOrderDetailsActivity.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            OrderDetailsData orderDetailsData = new OrderDetailsData();
                            orderDetailsData = new Gson().fromJson(result, OrderDetailsData.class);
                            if (orderDetailsData.getOrder_details()!=null){
                                orderDetailsBeans.addAll(orderDetailsData.getOrder_details());
                                if (orderDetailsBeans.size()>0){
                                    foodie_id = orderDetailsBeans.get(0).getOrder_foodie_email();
                                    txt_chef_name.setText(orderDetailsBeans.get(0).getOrder_foodie_name()+"");
                                    txt_order_id.setText(orderDetailsBeans.get(0).getOrder_order_id());
                                    txt_date.setText(orderDetailsBeans.get(0).getOrder_from_time()+" - "+orderDetailsBeans.get(0).getOrder_to_time());
                                    txtAddress.setText(orderDetailsBeans.get(0).getOrder_addres());
                                    txt_order_type.setText(orderDetailsBeans.get(0).getOrder_payment_type());
                                    txt_pay_mode.setText(orderDetailsBeans.get(0).getOrder_payment_type());
                                    txt_email.setText(orderDetailsBeans.get(0).getOrder_foodie_email());
                                    txt_total_price.setText("£"+orderDetailsBeans.get(0).getOrder_total_price());
                                    txt_phone.setText(orderDetailsBeans.get(0).getOrder_foodie_phone());
                                    delivery_type = orderDetailsBeans.get(0).getDelivery_type();
                                    setOrderDetails();
                                }
                            }
                            Log.d(ChefOrderDetailsActivity.class.getName(), "Rakhi : "+result);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    AdapterFoodieMyOrderDetailList myOrderDetailList;
    private void setOrderDetails(){
        myOrderDetailList = new AdapterFoodieMyOrderDetailList(getApplicationContext(), orderDetailsBeans);
        recyclerView.setAdapter(myOrderDetailList);
    }
    Dialog dialog;
    private void createDialog(String delivery_type){
        dialog = new Dialog(ChefOrderDetailsActivity.this);
        dialog.setContentView(R.layout.dialog_view_response);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        TextView txtAccept, txt_decline, txtreply, txtin_process, txt_ready, txt_on_way, txt_delivered, txt_not_delivered;

        txtAccept = dialog.findViewById(R.id.view_response_accept);
        txt_decline = dialog.findViewById(R.id.view_response_decline);
        txtreply = dialog.findViewById(R.id.view_response_reply);
        txtin_process = dialog.findViewById(R.id.view_response_in_process);
        txt_ready = dialog.findViewById(R.id.view_response_ready);
        txt_on_way = dialog.findViewById(R.id.view_response_on_way);
        txt_delivered = dialog.findViewById(R.id.view_response_delivered);
        txt_not_delivered = dialog.findViewById(R.id.view_response_not_delivered);

        if (delivery_type.equals("1")){
            dialog.findViewById(R.id.layout_accept_view).setVisibility(View.GONE);
            dialog.findViewById(R.id.layout_accept_delivered).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.layout_accept_in_process).setVisibility(View.VISIBLE);
        } else if (delivery_type.equals("2")){
            dialog.findViewById(R.id.layout_accept_view).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.layout_accept_delivered).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.layout_accept_in_process).setVisibility(View.VISIBLE);
        }
        dialog.findViewById(R.id.dialog_view_response_offer_price).setVisibility(View.GONE);
        dialog.findViewById(R.id.dialog_view_response_offer_edt).setVisibility(View.GONE);

        txt_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "2";
                updateStatus();
            }
        });

        txtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "1";
                updateStatus();
            }
        });

        txtreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "4";
                updateStatus();

            }
        });

        txtin_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "3";
                updateStatus();

            }
        });

        txt_on_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "5";
                updateStatus();

            }
        });

        txt_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "4";
                updateStatus();

            }
        });

        txt_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "8";
                updateStatus();
            }
        });

        txt_not_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "9";
                updateStatus();

            }
        });

        dialog.findViewById(R.id.view_response_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    private void updateStatus(){
        String s ="{\n" +
                " \"chef_id\":\""+chef_id+"\",\n" +
                "   \"foodie_id\":\""+foodie_id+"\",\n" +
                "   \"request_id\":\""+request_id+"\",\n" +
                "   \"status\":\""+chef_status+"\" \n" +
                "}";
        try {
            JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), ChefOrderDetailsActivity.this).sendMyData(jsonObject,
                    GetData.CHEF_ACCEPT_REQUEST, ChefOrderDetailsActivity.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            dialog.dismiss();
                            Log.d(ChefOrderDetailsActivity.class.getName(), "Rakhi : "+result);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
