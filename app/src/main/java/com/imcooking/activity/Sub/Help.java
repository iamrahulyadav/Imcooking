package com.imcooking.activity.Sub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.imcooking.R;
import com.imcooking.activity.main.setup.ForgotPassActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.utils.AppBaseActivity;

public class Help extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void help_legal(View view){

        startActivity(new Intent(Help.this, Help1.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }
}
