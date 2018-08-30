package com.imcooking.activity.Sub.Foodie;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Setting extends AppBaseActivity implements CompoundButton.OnCheckedChangeListener {
    private String miles="10", foodie_id;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private TinyDB tinyDB;
    private Gson gson;
    private Spinner sp_miles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BaseClass.setLightStatusBar(getWindow().getDecorView(),Setting.this);

        init();
    }

    private EditText edt_miles;
    private SwitchCompat sw_notification;
    private String str_notification;
    private ArrayList<String> spinnerData = new ArrayList<>();

    private void init(){
        tinyDB = new TinyDB(getApplicationContext());
        gson = new Gson();
        String login = tinyDB.getString("login_data");
        userDataBean = gson.fromJson(login, ApiResponse.UserDataBean.class);
        miles = userDataBean.getUser_milesdistance();
        str_notification = userDataBean.getUser_notification();
        foodie_id = userDataBean.getUser_id()+"";
        sp_miles = findViewById(R.id.setting_spinner_miles);

        edt_miles = findViewById(R.id.foodie_settings_miles);
        sw_notification = findViewById(R.id.foodie_settings_notification);
        sw_notification.setOnCheckedChangeListener(this);

        if (miles!=null)
            edt_miles.setText(miles);

        if (spinnerData != null) {
            spinnerData.clear();
        }
        if (spinnerData != null) {

            spinnerData.add("05 miles ");
            spinnerData.add("10 miles ");
            spinnerData.add("20 miles ");
            spinnerData.add("30 miles ");
            spinnerData.add("50 miles ");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>  (this,R.layout.spinner_row, spinnerData);

        dataAdapter.setDropDownViewResource(R.layout.spinner_row);
        sp_miles.setAdapter(dataAdapter);


        if (miles != null) {
            int spinnerPosition = dataAdapter.getPosition(miles+" miles ");
            sp_miles.setSelection(spinnerPosition);
        }
        sp_miles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                miles = adapterView.getItemAtPosition(i)+"";
                Toast.makeText(Setting.this, ""+miles, Toast.LENGTH_SHORT).show();
                miles.replace("miles","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getMyData();
    }

    private void getMyData(){
        if (str_notification!=null){
            if (str_notification.equals("1"))
                sw_notification.setChecked(true);
            else if (str_notification.equals("0"))
                sw_notification.setChecked(false);
        }else sw_notification.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(compoundButton.getId() == R.id.foodie_settings_notification){

            if(b){
                str_notification = "1";
            } else {
                str_notification = "0";
            }
        } else{}
    }

    public void foodie_settings_update(View view){
     //   miles = edt_miles.getText().toString().trim();
        if (!miles.isEmpty()){
            setMiles();
        } else {
            edt_miles.setError("Please enter your default miles ");
            edt_miles.requestFocus();
        }
    }

    private void setMiles(){
        final String request = "{\"notification\":\""+str_notification+"\",\n" +
                " \"user_id\":\""+foodie_id+"\",\n" +
                " \"miles\":\""+miles.replace("miles","")+"\"\n" +
                "}";
        Log.d("TAG", "rakhi: "+request);

        new GetData(getApplicationContext(), Setting.this).getResponse(request, GetData.SETTINGS, new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result!=null){
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                boolean status = jsonObject.getBoolean("status");
                                if (status){
                                    userDataBean.setUser_milesdistance(miles);
                                    userDataBean.setUser_notification(str_notification);
                                    tinyDB.putString("login_data", gson.toJson(userDataBean));
                                    BaseClass.showToast(getApplicationContext(), "Settings updated successfully. ");
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

}
