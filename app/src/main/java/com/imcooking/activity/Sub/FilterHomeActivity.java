package com.imcooking.activity.Sub;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class FilterHomeActivity extends AppBaseActivity {
    private TextView tv_title,txtCancel,txtSave, txtName ;
    private ImageView btnHome ;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            BaseClass.setLightStatusBar(getWindow().getDecorView(),FilterHomeActivity.this);
        }
        setupToolBar();

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

}
