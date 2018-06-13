package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

public class Help extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        BaseClass.setLightStatusBar(getWindow().getDecorView(),Help.this);
    }

    public void help_legal(View view){

        startActivity(new Intent(Help.this, Help1.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }
}
