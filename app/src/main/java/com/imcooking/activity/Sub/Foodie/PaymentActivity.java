package com.imcooking.activity.Sub.Foodie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.PlaceOrder;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefOrderDetailsActivity;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppBaseActivity {
    private TextView txt_price, txt_place_order;
    private PlaceOrder placeOrder = new PlaceOrder();
    private Gson gson = new Gson();
    private String payment_type="cod", chef_name;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BaseClass.setLightStatusBar(getWindow().getDecorView(),PaymentActivity.this);
        }
        if (getIntent().hasExtra("order_details")){
            placeOrder = gson.fromJson(getIntent().getStringExtra("order_details"), PlaceOrder.class);
        }
        if (getIntent().hasExtra("chef_name")){
            chef_name = getIntent().getStringExtra("chef_name");
        }

        init();
    }

    private void init(){
        txt_price = findViewById(R.id.activity_payment_total_price);
        txt_place_order = findViewById(R.id.activity_payment_btn_place);
        radioGroup = findViewById(R.id.activity_payment_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.activity_payment_radio_paypal){
                    payment_type = "paypal";
                }
                else if(checkedId==R.id.activity_payment_radio_cod){
                    payment_type = "cod";
                }
            }
        });

        txt_price.setText("£"+placeOrder.getTotal_price());

        txt_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder.setPayment_type(payment_type);
                Log.d(PaymentActivity.class.getName(), "Rakhi "+gson.toJson(placeOrder));
                makePayment(placeOrder);
            }
        });

    }

    private void makePayment(PlaceOrder placeOrder ){
        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(placeOrder));
            new GetData(getApplicationContext(), PaymentActivity.this).sendMyData(jsonObject, GetData.PLACE_ORDER, PaymentActivity.this, new GetData.MyCallback() {
                @Override
                public void onSuccess(final String result) {
                    /*{"status":true,"booking_id":"CO14061939577511","msg":"Dish Booking Successfully"}*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject1 = new JSONObject(result);
                                if (jsonObject1.getBoolean("status")){
                                    createMyDialog(jsonObject1.getString("booking_id"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(PaymentActivity.class.getName(), "Rakhi "+result);
                        }
                    });
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Dialog dialog ;
    private void createMyDialog(final String booking_id){
        dialog= new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.dialog_add_to_cart);
        ImageView imgDia = dialog.findViewById(R.id.imageDialog);
        TextView txtDialog = dialog.findViewById(R.id.imgdialog_text);
        TextView txtOk = dialog.findViewById(R.id.txtdialog_ok);
        imgDia.setImageResource(R.drawable.ordersuccess);
        txtDialog.setText("Your Order has been Successfully Place with \"" +chef_name+"\" ");
        dialog.findViewById(R.id.tv_cancel_add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  startActivity(new Intent(PaymentActivity.this, ChefOrderDetailsActivity.class)
                        .putExtra("order_id",booking_id));*/

                startActivity(new Intent(PaymentActivity.this, MainActivity.class).putExtra("pay","payorder"));
            }
        });
    }


}

