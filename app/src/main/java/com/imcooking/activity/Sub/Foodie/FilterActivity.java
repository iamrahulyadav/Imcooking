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
import android.widget.SeekBar;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.BaseClass;

public class FilterActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        BaseClass.setLightStatusBar(getWindow().getDecorView(),FilterActivity.this);

        init();
    }

    private AppCompatRatingBar ratingBar;
    private TextView tv_seekbar_text;
    private CheckBox checkBox_home, checkBox_pickup;
    private SeekBar seekBar;
    private String str_ratings = "0.0", str_min_price = "0", str_max_price = "0",
            str_check_home = "0", str_check_pickup = "0";

    private void init(){

        ratingBar = findViewById(R.id.filter_ratingbar);
        tv_seekbar_text = findViewById(R.id.filter_seekbar_text);
        checkBox_home = findViewById(R.id.filter_check_home);
        checkBox_pickup = findViewById(R.id.filter_check_pickup);
        seekBar = findViewById(R.id.filter_seekbar);

        ShapeDrawable thumb = new ShapeDrawable( new RectShape() );
        thumb.getPaint().setColor(getResources().getColor(R.color.theme_color));
        thumb.setIntrinsicHeight( 80 );
        thumb.setIntrinsicWidth( 300 );
        seekBar.setThumb( thumb );

        seekBar.setOnSeekBarChangeListener(this);
        checkBox_home.setOnCheckedChangeListener(this);
        checkBox_pickup.setOnCheckedChangeListener(this);
    }

    public void filter_apply(View view){
        if(str_check_home.equals("0") && str_check_pickup.equals("0")){
            BaseClass.showToast(getApplicationContext(), "Please Select either \"Home Delivery\" or \"Pick-up\" or both.");
        } else {
            str_ratings = ratingBar.getRating() + "";

            Intent intent = new Intent();
            intent.putExtra("rating", str_ratings);
            intent.putExtra("min_price", str_min_price);
            intent.putExtra("max_price", str_max_price);
            intent.putExtra("check_home", str_check_home);
            intent.putExtra("check_pickup", str_check_pickup);
            setResult(0143, intent);
            finish();
        }
    }

    public void filter_reset(View view){
        ratingBar.setRating(0);
        seekBar.setProgress(0);
        checkBox_home.setChecked(false);
        checkBox_pickup.setChecked(false);

        str_ratings = "0.0"; str_min_price = "0"; str_max_price = "0"; str_check_home = "0"; str_check_pickup = "0";
        tv_seekbar_text.setText("0");

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (i == 0) {
            tv_seekbar_text.setText("0");
        } else {
            if (i >= 20) {
                tv_seekbar_text.setText("$" + (i - 10) + " To " + "$" + (i + 10));
                str_min_price = (i - 10) + "";
                str_max_price = (i + 10) + "";
            } else {
                tv_seekbar_text.setText("$" + "0" + " To " + "$" + "20");
                str_min_price = "0";
                str_max_price = "20";
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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

        finish();
    }
}
