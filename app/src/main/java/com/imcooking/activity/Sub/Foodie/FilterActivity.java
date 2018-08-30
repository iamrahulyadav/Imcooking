package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.BaseClass;

public class FilterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        BaseClass.setLightStatusBar(getWindow().getDecorView(),FilterActivity.this);
        init();
    }

    private AppCompatRatingBar ratingBar;
    private CheckBox checkBox_home, checkBox_pickup;
    private EditText edt_max, edt_min;
    private String str_ratings = "0.0", str_min_price = "0", str_max_price = "0",
            str_check_home = "0", str_check_pickup = "0";

    private void init(){

        ratingBar = findViewById(R.id.filter_ratingbar);

        checkBox_home = findViewById(R.id.filter_check_home);
        checkBox_pickup = findViewById(R.id.filter_check_pickup);

        edt_max = findViewById(R.id.filter_edt_max_price);
        edt_min = findViewById(R.id.filter_edt_min_price);

        checkBox_home.setOnCheckedChangeListener(this);
        checkBox_pickup.setOnCheckedChangeListener(this);
    }

    private boolean isRatingSelected;
    private boolean isMaxPriceSelected;
    private boolean isMinPriceSelected;
    private boolean isHomeDeliverySelected;
    private boolean isPickupSelected;

    public void filter_apply(View view){

        isRatingSelected = false;
        isMaxPriceSelected = false;
        isMinPriceSelected = false;
        isHomeDeliverySelected = false;
        isPickupSelected = false;

        if(ratingBar.getRating() != 0.0){
            isRatingSelected = true;
        }
        if(!edt_max.getText().toString().trim().equals("")){
            isMaxPriceSelected = true;
        }
        if(!edt_min.getText().toString().trim().equals("")){
            isMinPriceSelected = true;
        }
        if(str_check_home.equals("1")){
            isHomeDeliverySelected = true;
        }
        if(str_check_pickup.equals("1")){
            isPickupSelected = true;
        }

        int i = (int)ratingBar.getRating();
        str_ratings = i + "";
        str_max_price = edt_max.getText().toString().trim();
        str_min_price = edt_max.getText().toString().trim();

        Intent intent = new Intent();
        if(isRatingSelected || isMaxPriceSelected || isMinPriceSelected || isHomeDeliverySelected ||
                isPickupSelected) {
            if(isRatingSelected) {
                intent.putExtra("rating", str_ratings);
            }
            if(isMinPriceSelected){
                intent.putExtra("min_price", str_min_price);
            }
            if(isMaxPriceSelected){
                intent.putExtra("max_price", str_max_price);
            }
            if(isHomeDeliverySelected){
                intent.putExtra("check_home", str_check_home);
            }
            if(isPickupSelected){
                intent.putExtra("check_pickup", str_check_pickup);
            }
            setResult(01, intent);
            finish();
        } else {
            setResult(02, intent);
            finish();
        }
    }

    public void filter_reset(View view){
        ratingBar.setRating(0);
        checkBox_home.setChecked(false);
        checkBox_pickup.setChecked(false);
        edt_max.setText("");
        edt_min.setText("");

        str_ratings = "0"; str_min_price = ""; str_max_price = ""; str_check_home = "0"; str_check_pickup = "0";
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(compoundButton.getId() == R.id.filter_check_home){
            if (b){
                str_check_home = "1";
            } else {
                str_check_home = "0";
            }
        } else if(compoundButton.getId() == R.id.filter_check_pickup){
            if (b){
                str_check_pickup = "1";
            } else {
                str_check_pickup = "0";
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(02, intent);
        finish();
    }
}