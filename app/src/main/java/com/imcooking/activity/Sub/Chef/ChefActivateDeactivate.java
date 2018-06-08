package com.imcooking.activity.Sub.Chef;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.AddAddressActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import org.json.JSONException;
import org.json.JSONObject;

public class ChefActivateDeactivate extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_activate_deactivate);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            BaseClass.setLightStatusBar(getWindow().getDecorView(),ChefActivateDeactivate.this);
        }

        init();
    }

    private SwitchCompat sw;
    private String str;
//    private TextView tv;

    private void init(){

//        tv = findViewById(R.id.chef_activate_text);
        sw = findViewById(R.id.chef_activate_switch);
        sw.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(b){
            str = "1";
        } else{
            str = "0";
        }

        activate_deactivate(str);

    }

    private void activate_deactivate(final String str){

        String s = "{\"chef_id\":4,\"status\":" + str + "}" ;

        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getApplicationContext()).sendMyData(jsonObject, "chef_active_deactive", ChefActivateDeactivate.this,
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject job = new JSONObject(result);
                                boolean status = job.getBoolean("status");
                                String msg = job.getString("msg");

                                if(status){
                                    if(msg.equals("chef status update successfully")){
                                        if (str.equals("1")){
                                            sw.setText("Activate");
//                                            sw.setShowText(true);
                                            BaseClass.showToast(getApplicationContext(), "Profile Activated Successfully");
                                        } else{
                                            sw.setText("Deactivate");
//                                            sw.setShowText(false);
                                            BaseClass.showToast(getApplicationContext(), "Profile Deactivated Successfully");
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
