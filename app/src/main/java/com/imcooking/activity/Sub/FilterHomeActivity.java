package com.imcooking.activity.Sub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class FilterHomeActivity extends AppBaseActivity implements View.OnClickListener{
    private TextView tv_title,txtReset, txtFilter, tv_selected_price ;
    private ImageView btnHome ;
    RatingBar ratingBar;
    SeekBar seekBar;
    int price;
    float ratingvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            BaseClass.setLightStatusBar(getWindow().getDecorView(),FilterHomeActivity.this);
        }
        setupToolBar();
//        find id
        init();
    }

    int progressChangedValue = 0;

    private void init(){
        ratingBar = findViewById(R.id.activity_filter_ratingbar);
        seekBar = findViewById(R.id.actvity_filter_seekbar);
        tv_selected_price = findViewById(R.id.filter_selected_price);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

//                tv_selected_price.setText("Price\t\t" + progressChangedValue);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(FilterHomeActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupToolBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        tv_title = (TextView) myToolbar.findViewById(R.id.custom_toolbar_txtTitle);
        btnHome = (ImageView) myToolbar.findViewById(R.id.custom_toolbar_imgback);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onBackPressed(); }
        });
        tv_title.setText("Filter");
        setSupportActionBar(myToolbar);
    }

    public static String FILTER_RESPONSE = "filter_response";
    public static int FILTER_RESPONSE_CODE = 220;


    public void filter_reset(View view){
        ratingvalue=0;
        progressChangedValue = 0;
        ratingBar.setRating(ratingvalue);
        seekBar.setProgress(progressChangedValue);
    }

    public void filter(View view){
        ratingvalue = ratingBar.getRating();
        Intent filrestintent=new Intent();
        filrestintent.putExtra("ratingvalue",ratingvalue);
        filrestintent.putExtra("progressChangedValue", progressChangedValue);
        setResult(FILTER_RESPONSE_CODE,filrestintent);
//                Log.d("VKK", gson.toJson(listModel));
        finish();
//        Toast.makeText(this, ""+ratingvalue, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
         /*   case R.id.actvity_filter_txtReset:
                ratingvalue=0;
                progressChangedValue = 0;
                ratingBar.setRating(ratingvalue);
                seekBar.setProgress(progressChangedValue);
                break;
            case R.id.actvity_filter_txtFilter:
                ratingvalue = ratingBar.getRating();
                Intent filrestintent=new Intent();
                filrestintent.putExtra("ratingvalue",ratingvalue);
                filrestintent.putExtra("progressChangedValue", progressChangedValue);
                setResult(FILTER_RESPONSE_CODE,filrestintent);
//                Log.d("VKK", gson.toJson(listModel));
                finish();
                Toast.makeText(this, ""+ratingvalue, Toast.LENGTH_SHORT).show();

                break;*/
        }
    }
}
