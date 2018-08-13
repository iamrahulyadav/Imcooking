package com.imcooking.activity.Sub.Chef;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.AddAddressActivity;
import com.imcooking.fragment.chef.ChefHome;
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
    private String str_;
//    private TextView tv;

    private String chef_id;

    private boolean do_call = true;

    private void init(){

        sw = findViewById(R.id.chef_activate_switch);
        sw.setOnCheckedChangeListener(this);

        ChefProfileData1 chefProfileData1 = new ChefProfileData1();
        chefProfileData1 = ChefHome.chefProfileData1;
        chef_id = chefProfileData1.getChef_data().getChef_id() + "";

        if(chefProfileData1.getChef_data().getActivate_status().equals("1")){
            do_call = false;
            sw.setText("Activate");
            sw.setChecked(true);
            str_ = "1";
        } else {
            do_call = true;
            sw.setText("Deactivate");
            sw.setChecked(false);
            str_ = "0";
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b) {
            str_ = "1";
        } else {
            str_ = "0";
        }
        if (do_call) {
            activate_deactivate(str_);
        } else{
            do_call = true;
        }
    }

    private void activate_deactivate(final String str){

        String s = "{\"chef_id\":\"" + chef_id +  "\",\"status\":\"" + str + "\"}" ;
        Log.d("MyRequest", s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getApplicationContext()).sendMyData(jsonObject, "chef_active_deactive",
                    ChefActivateDeactivate.this,
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject job = new JSONObject(result);
                                boolean status = job.getBoolean("status");
                                String msg = job.getString("msg");

                                if(status){
                                    if(msg.equals("chef status update successfully")){
                                        if (str.equals("0")){
                                            sw.setText("Deactivate");

                                            BaseClass.showToast(getApplicationContext(), "Profile Deactivated Successfully.");
                                            str_ = "0";
                                        } else{
                                            sw.setText("Activate");

                                            BaseClass.showToast(getApplicationContext(), "Profile Activated Successfully");
                                            str_ = "1";
                                        }

                                    } else if (msg.equals("Already updated")){
                                        if(str.equals("1")){
                                            BaseClass.showToast(getApplicationContext(), "Profile Activated");
                                        } else{

                                            BaseClass.showToast(getApplicationContext(), "Profile Deactivated");
                                        }
                                    } else {
                                        BaseClass.showToast(getApplicationContext(), "Something Went Wrong.");
                                    }
                                } else{
                                    BaseClass.showToast(getApplicationContext(), "Something Went Wrong.");
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