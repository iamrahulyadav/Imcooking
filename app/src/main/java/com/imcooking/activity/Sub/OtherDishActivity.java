package com.imcooking.activity.Sub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;

public class OtherDishActivity extends AppBaseActivity {
    private TextView tv_title,txtReset, txtFilter ;
    private ImageView btnHome ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_dish);
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
