package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;

public class ManageAddress extends AppBaseActivity {
    TextView txtAddress;
    RecyclerView savedAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

//        find id
        txtAddress = findViewById(R.id.activity_manage_address_txtAddAddress);
        savedAddress  = findViewById(R.id.activity_manage_address_recycler);

        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageAddress.this, AddAddressActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }
}
