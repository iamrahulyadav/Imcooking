package com.imcooking.activity.Sub.Chef;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.imcooking.R;

public class ChefEditDish extends AppCompatActivity {

    private String name, cuisine, price, description, special_note;
    private EditText edt_name, edt_cuisine, edt_price, edt_description, edt_special_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_edit_dish);

        init();
        getMyIntentData();
    }

    private void init(){
        edt_name = findViewById(R.id.chef_edit_dish_name);
        edt_cuisine = findViewById(R.id.chef_edit_dish_cuisine);
        edt_price = findViewById(R.id.chef_edit_dish_price);
        edt_description = findViewById(R.id.chef_edit_dish_description);
        edt_special_note = findViewById(R.id.chef_edit_dish_special_note);
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
}
