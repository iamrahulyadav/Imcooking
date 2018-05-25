package com.imcooking.activity.Sub.Chef;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.imcooking.R;

public class ChefEditDish extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private String name, cuisine, price, description, special_note;
    private EditText edt_name, edt_cuisine, edt_price, edt_description, edt_special_note;
    private SwitchCompat switch_1, switch_2, switch_3;
    private String sw_1, sw_2, sw_3;

    private String dish_currently_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_edit_dish);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        init();
        getMyIntentData();
    }

    private void init(){
        edt_name = findViewById(R.id.chef_edit_dish_name);
        edt_cuisine = findViewById(R.id.chef_edit_dish_cuisine);
        edt_price = findViewById(R.id.chef_edit_dish_price);
        edt_description = findViewById(R.id.chef_edit_dish_description);
        edt_special_note = findViewById(R.id.chef_edit_dish_special_note);

        switch_1 = findViewById(R.id.edit_dish_switch_current_available);
        switch_2 = findViewById(R.id.edit_dish_switch_home_delivery);
        switch_3 = findViewById(R.id.edit_dish_switch_current_available);

        switch_1.setOnCheckedChangeListener(this);
        switch_2.setOnCheckedChangeListener(this);
        switch_3.setOnCheckedChangeListener(this);
    }

    private void getMyIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = getIntent().getExtras().getString("name");
            cuisine = getIntent().getExtras().getString("cuisine");
            price = getIntent().getExtras().getString("price");
            description = getIntent().getExtras().getString("description");
            special_note = getIntent().getExtras().getString("special_note");

            edt_name.setText(name);
            edt_cuisine.setText(cuisine);
            edt_price.setText(price);
            edt_description.setText(description);
            edt_special_note.setText(special_note);

        } else{}
    }

    public void edit_dish_submit(View view){

        name = edt_name.getText().toString().trim();
        cuisine = edt_cuisine.getText().toString().trim();
        price = edt_price.getText().toString().trim();
        description = edt_description.getText().toString().trim();
        special_note = edt_special_note.getText().toString().trim();


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int id = compoundButton.getId();
        if(id == R.id.edit_dish_switch_current_available){

            if(b){
                sw_1 = "yes";
            } else{
                sw_1 = "no";
            }
        } else if(id == R.id.edit_dish_switch_home_delivery){
            if(b){
                sw_2 = "yes";
            } else{
                sw_2 = "no";
            }

        } else if(id == R.id.edit_dish_switch_pickup){
            if(b){
                sw_3 = "yes";
            } else{
                sw_3 = "no";
            }

        } else {}
    }
}
