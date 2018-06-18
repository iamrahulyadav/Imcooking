package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class FoodieOrderDetailActivity extends AppBaseActivity {
    private TextView txt_chef_name, txt_date, txt_total_price, txt_qyt, txt_price, txt_email, txt_pay_mode,
    txt_order_id;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodie_order_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BaseClass.setLightStatusBar(getWindow().getDecorView(),FoodieOrderDetailActivity.this);
        }

        init();
    }

    private void init(){
        txt_chef_name = findViewById(R.id.item_foodie_my_order_chef);
        txt_date = findViewById(R.id.item_foodie_my_order_placed_date);
        txt_total_price = findViewById(R.id.item_foodie_my_order_total_price);
        txt_qyt = findViewById(R.id.item_foodie_my_order_qyt);
        txt_price = findViewById(R.id.item_foodie_my_order_price);
        txt_email = findViewById(R.id.item_foodie_my_order_foodie_email);
        txt_pay_mode = findViewById(R.id.item_foodie_my_order_payment_mode);
        txt_order_id = findViewById(R.id.item_foodie_my_order_id);
        recyclerView = findViewById(R.id.activity_other_dish_recycler);
    }
}
