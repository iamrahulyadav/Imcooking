package com.imcooking.activity.Sub.Chef;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.OrderDetailsData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterFoodieMyOrderDetailList;
import com.imcooking.adapters.ChefILoveAdatper;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChefOrderDetailsActivity extends AppBaseActivity {
    private RecyclerView recyclerView;
    private String order_id, delivery_type, chef_status, chef_id, foodie_id="107", user_type;
    private TextView txt_chef_name, txt_order_type,txt_date, txtAddress, txt_total_price,
            txt_qyt, txt_price, txt_email, txt_pay_mode,txt_phone,txt_order_status,
            txt_order_id;
    private TinyDB tinyDB;
    private NestedScrollView nestedScrollView;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_order_details);
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        tinyDB = new TinyDB(getApplicationContext());
        userDataBean = gson.fromJson(tinyDB.getString("login_data"), ApiResponse.UserDataBean.class);

        chef_id = userDataBean.getUser_id()+"";
        user_type = userDataBean.getUser_type();

        if (getIntent().hasExtra("order_id"))
            order_id = getIntent().getStringExtra("order_id");

        init();
    }

    private void init(){

        nestedScrollView = findViewById(R.id.chef_order_list_scroll);
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
        nestedScrollView.setVisibility(View.GONE);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        if (user_type.equalsIgnoreCase("1")){
            txt_order_status.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    createDialog(delivery_type);
                }
            });
        }

        getOrderDetails();
    }

    private List<OrderDetailsData.OrderDetailsBean>orderDetailsBeans;

    private void getOrderDetails(){
        final String s = "{\n" +
                " \"order_id\":\""+order_id+"\"\n" +
                "}";
        Log.d("TAG", "MyRequest: "+s);
        orderDetailsBeans = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getApplicationContext(), ChefOrderDetailsActivity.this).sendMyData(jsonObject,
                    GetData.ORDER_DETAILS, ChefOrderDetailsActivity.this, new GetData.MyCallback() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(String result) {
                            nestedScrollView.setVisibility(View.VISIBLE);
                            OrderDetailsData orderDetailsData = new OrderDetailsData();
                            orderDetailsData = new Gson().fromJson(result, OrderDetailsData.class);
                            if (orderDetailsData.getOrder_details()!=null){
                                orderDetailsBeans.addAll(orderDetailsData.getOrder_details());
                                if (orderDetailsBeans.size()>0){
                                    foodie_id = orderDetailsBeans.get(0).getOrder_foodie_id()+"";
                                    if (user_type.equals("2")){
                                        txt_chef_name.setText(orderDetailsBeans.get(0).getChef_name()+"");
                                        txtAddress.setText(orderDetailsBeans.get(0).getChef_address());
                                        txt_phone.setText(orderDetailsBeans.get(0).getChef_phone());
                                        txt_email.setText(orderDetailsBeans.get(0).getChef_email());

                                    } else {
                                        if (orderDetailsBeans.get(0).getOrder_foodie_name()!=null)
                                            txt_chef_name.setText(orderDetailsBeans.get(0).
                                                    getOrder_foodie_name()+"");
                                        txt_phone.setText(orderDetailsBeans.get(0).getOrder_foodie_phone());
                                        txtAddress.setText(orderDetailsBeans.get(0).getOrder_addres());
                                        txt_email.setText(orderDetailsBeans.get(0).getOrder_foodie_email());

                                    }

                                    txt_order_id.setText("#"+orderDetailsBeans.get(0).getOrder_order_id());
                                    txt_order_type.setText(orderDetailsBeans.get(0).getOrder_payment_type());
                                    txt_pay_mode.setText(orderDetailsBeans.get(0).getOrder_payment_type());
                                    txt_total_price.setText("£"+orderDetailsBeans.get(0).getOrder_total_price());
                                    delivery_type = orderDetailsBeans.get(0).getDelivery_type();

                                    if (delivery_type.equals("1")){
                                        //1=delivery
                                        txt_order_type.setText("Delivery");
                                        @SuppressLint("SimpleDateFormat")
                                        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date myDate = null;
                                        try {
                                            myDate = timeFormat.parse(orderDetailsBeans.get(0).getBooking_date());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                                                "MMM dd, yyyy HH:mm a");
                                        String finalDate = dateFormat.format(myDate);
                                        txt_date.setText(finalDate);

                                    } else if (delivery_type.equals("2")){
                                        //2=pickup
                                        txt_date.setText(orderDetailsBeans.get(0).getOrder_from_time()+" - "+orderDetailsBeans.get(0).getOrder_to_time());
                                        txt_order_type.setText("Pickup");
                                    }
                                    if (user_type.equalsIgnoreCase("1")){
                                        chef_status =  orderDetailsBeans.get(0).getOrder_status();
                                        Toast.makeText(ChefOrderDetailsActivity.this, ""+chef_status, Toast.LENGTH_SHORT).show();
                                        txt_order_status.setText("Change Status");
                                    } else {
                                        if (orderDetailsBeans.get(0).getOrder_status() != null) {
                                            String status = orderDetailsBeans.get(0).getOrder_status();
                                            if (delivery_type.equals("1")){
                                                if (status.equals("0"))
                                                    txt_order_status.setText("Order Placed");
                                                else if (status.equals("1"))
                                                    txt_order_status.setText("Completed");
                                                else if (status.equals("2"))
                                                    txt_order_status.setText("Canceled ");
                                                else if (status.equals("3"))
                                                    txt_order_status.setText("In Prepration");
                                                else if (status.equals("4"))
                                                    txt_order_status.setText("Ready to Delivery");
                                                else if (status.equals("5"))
                                                    txt_order_status.setText("On Way");
                                                else if (status.equals("8"))
                                                    txt_order_status.setText("Delivered");
                                                else if (status.equals("9"))
                                                    txt_order_status.setText("Not Delivered");
                                            } else if (delivery_type.equals("2")){
                                                if (status.equals("0"))
                                                    txt_order_status.setText("Order Placed");
                                                else if (status.equals("1"))
                                                    txt_order_status.setText("Completed");
                                                else if (status.equals("2"))
                                                    txt_order_status.setText("Canceled ");
                                                else if (status.equals("3"))
                                                    txt_order_status.setText("In Prepration");
                                                else if (status.equals("4"))
                                                    txt_order_status.setText("Ready to Pick");
                                                else if (status.equals("8"))
                                                    txt_order_status.setText("Picked");
                                                else if (status.equals("9"))
                                                    txt_order_status.setText("Not Picked by client");
                                            }

                                        }
                                    }
                                    setOrderDetails();
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setOrderDetails(){
        AdapterFoodieMyOrderDetailList myOrderDetailList = new AdapterFoodieMyOrderDetailList(getApplicationContext(),
                orderDetailsBeans);
        recyclerView.setAdapter(myOrderDetailList);
    }

    private Dialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createDialog(String delivery_type){
        dialog = new Dialog(ChefOrderDetailsActivity.this);
        dialog.setContentView(R.layout.dialog_view_response);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        TextView txtin_process, txt_ready, txt_on_way, txt_delivered, txt_not_delivered, txt_canceled, tv_completed;

        txtin_process = dialog.findViewById(R.id.view_response_in_process);
        txt_ready = dialog.findViewById(R.id.view_response_ready);
        txt_on_way = dialog.findViewById(R.id.view_response_on_way);
        txt_delivered = dialog.findViewById(R.id.view_response_delivered);
        txt_not_delivered = dialog.findViewById(R.id.view_response_not_delivered);
        txt_canceled = dialog.findViewById(R.id.view_response_canceled);
        tv_completed = dialog.findViewById(R.id.view_response_completed);

        if (delivery_type.equals("1")){

            /*  if (delivery_type.equals("1")){
                if (status.equals("0"))
                    holder.txt_order_status.setText("New Order");
                else if (status.equals("1"))
                    holder.txt_order_status.setText("Completed");
                else if (status.equals("2"))
                    holder.txt_order_status.setText("Canceled ");
                else if (status.equals("3"))
                    holder.txt_order_status.setText("In Prepration");
                else if (status.equals("4"))
                    holder.txt_order_status.setText("Ready to Delivery");
                else if (status.equals("5"))
                    holder.txt_order_status.setText("On Way");
                else if (status.equals("8"))
                    holder.txt_order_status.setText("Delivered");
                else if (status.equals("9"))
                    holder.txt_order_status.setText("Not Delivered");
            }
            else if (delivery_type.equals("2")){
                if (status.equals("0"))
                    holder.txt_order_status.setText("Order Placed");
                else if (status.equals("1"))
                    holder.txt_order_status.setText("Completed");
                else if (status.equals("2"))
                    holder.txt_order_status.setText("Canceled ");
                else if (status.equals("3"))
                    holder.txt_order_status.setText("In Prepration");
                else if (status.equals("4"))
                    holder.txt_order_status.setText("Ready to Pick");
                else if (status.equals("8"))
                    holder.txt_order_status.setText("Picked");
                else if (status.equals("9"))
                    holder.txt_order_status.setText("Not Picked by client");
            }*/


           /* if (chef_status.equals("1")){
                txt_canceled.setClickable(false);
                txt_canceled.setBackground(getDrawable(R.drawable.shape_response_disable));
            } else if (chef_status.equals("4")){

            }*/

            txt_ready.setText("Ready to Delivery");
            txt_on_way.setText("On Way");
            txt_delivered.setText("Delivered");
            txt_not_delivered.setText("Not Delivered");
        } else if (delivery_type.equals("2")){
            txt_ready.setText("Ready to Pick");
            txt_on_way.setVisibility(View.GONE);
            txt_delivered.setText("Picked");
            txt_not_delivered.setText("Not Picked by client");
        } else { }

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

        tv_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "1";
                updateStatus();
            }
        });


        txt_canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chef_status = "2";
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
                "   \"request_id\":\""+order_id+"\",\n" +
                "   \"status\":\""+chef_status+"\" \n" +
                "}";
        Log.d("TAG", "updateStatus: "+s);
        try {
            final JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), ChefOrderDetailsActivity.this).sendMyData(jsonObject,
                    GetData.CHEF_ACCEPT_REQUEST, ChefOrderDetailsActivity.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            try {
                                JSONObject jsonObject1= new JSONObject(result);
                                if(jsonObject1.getBoolean("status")){
                                    if(jsonObject1.getString("msg").equals("chef status changed Successfully")){
                                        dialog.dismiss();
                                        finish();
                                        Log.d(ChefOrderDetailsActivity.class.getName(), "Rakhi : "+result);
                                    } else{
                                        BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                    }
                                } else{
                                    BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
