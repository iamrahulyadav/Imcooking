package com.imcooking.activity.Sub.Chef;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.ModelChefEditProfile;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.fragment.chef.chefprofile.ChefHome;
import com.imcooking.fragment.chef.chefprofile.RequestDishFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChefEditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_edit_profile);

        init();
        getProfileData();
    }
    private CuisineData cuisineData = new CuisineData();

    private EditText edt_name, edt_address, edt_city, edt_email, edt_zipcoede;
    private Spinner sp_miles, sp_cuisine;
    private SwitchCompat /*sw_notification,*/ sw_available;
    private String str_id, str_name, str_address, str_city, str_email, str_zipcode, str_miles, str_cuisine/*, str_notification*/,
                    str_available = "0";
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean;


    private void init(){

        tinyDB = new TinyDB(getApplicationContext());
        userDataBean = new ApiResponse.UserDataBean();

        edt_name = findViewById(R.id.chef_edit_profile_name);
        edt_address = findViewById(R.id.chef_edit_profile_address);
        edt_city = findViewById(R.id.chef_edit_profile_city);
        edt_email = findViewById(R.id.chef_edit_profile_email);
        edt_zipcoede = findViewById(R.id.chef_edit_profile_zipcode);

        sp_miles = findViewById(R.id.chef_edit_profile_spinner_miles);
        sp_cuisine = findViewById(R.id.chef_edit_profile_spinner_select_cuisines);

        sp_miles.setOnItemSelectedListener(this);
        sp_cuisine.setOnItemSelectedListener(this);

//        sw_notification = findViewById(R.id.chef_edit_profile_notification);
        sw_available = findViewById(R.id.chef_edit_profile_available);

//        sw_notification.setOnCheckedChangeListener(this);
        sw_available.setOnCheckedChangeListener(this);

        ArrayList<String> spinnerData =new ArrayList<>();
        spinnerData.add("10 miles ");
        spinnerData.add("20 miles ");
        spinnerData.add("30 miles ");
        spinnerData.add("50 miles ");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,
                R.layout.spinner_row, spinnerData);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        sp_miles.setAdapter(arrayAdapter);
        sp_cuisine.setAdapter(arrayAdapter);

        String login_data = tinyDB.getString("login_data");

        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);

        str_id = userDataBean.getUser_id() + "";

        getMyCuisines();
    }

    private void getProfileData(){

        ChefProfileData1 chefProfileData1 = new ChefProfileData1();
        chefProfileData1 = ChefHome.chefProfileData1;

        str_id = chefProfileData1.getChef_data().getChef_id() + "";
        str_name = chefProfileData1.getChef_data().getChef_full_name() + "";
        str_address = chefProfileData1.getChef_data().getAddress() + "";
//        str_city = chefProfileData1.getChef_data().getChef_city() + "";
        str_email = chefProfileData1.getChef_data().getChef_email() + "";
//        str_zipcode = chefProfileData1.getChef_data(). + "";

        edt_email.setText(str_email);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int id = adapterView.getId();
        if(id == R.id.chef_edit_profile_spinner_miles){

            str_miles = adapterView.getItemAtPosition(i).toString();

        } else if (id == R.id.chef_edit_profile_spinner_select_cuisines){

            str_cuisine = adapterView.getItemAtPosition(i).toString();

        } else {}
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int id = compoundButton.getId();
       /* if(id == R.id.chef_edit_profile_notification){

            if(b) str_notification = "1";
            else  str_notification = "0";

        } else */if(id == R.id.chef_edit_profile_available){

            if(b) str_available = "1";
            else  str_available = "0";

        } else {}
    }

    public void chef_edit_profile_save(View view){

//        str_id
        str_name = edt_name.getText().toString().trim();
        str_address = "E-164, Sector-15, Noida, UP"; //edt_address.getText().toString().trim();
        str_city = "Noida"; //edt_city.getText().toString();
        str_email = edt_email.getText().toString().trim();
        str_zipcode = "201301"; // edt_zipcoede.getText().toString().trim();
//        str_miles , str_notification, str_available, str_cuisine

        ArrayList<String> cuisine_list = new ArrayList<>();
        cuisine_list.add(str_cuisine);

        ModelChefEditProfile data = new ModelChefEditProfile();

        data.setChef_id(str_id);
        data.setFullname(str_name);
        data.setAddress(str_address);
        data.setCity(str_city);
        data.setEmail(str_email);
        data.setZipcode(str_zipcode);
        data.setDefault_miles(str_miles);
        data.setAvailable(str_available);
        data.setCuisine_list("29");

        if(!str_name.isEmpty()) {
            if(!str_address.isEmpty()){
                if(!str_city.isEmpty()){
                        if(!str_zipcode.isEmpty()){
                            try {
                                JSONObject jsonObject = new JSONObject(new Gson().toJson(data));

                                Log.d("MyRequest", jsonObject.toString());
                                new GetData(getApplicationContext()).sendMyData(jsonObject, "chef_profile_update",
                                        ChefEditProfile.this, new GetData.MyCallback() {
                                            @Override
                                            public void onSuccess(String result) {

                                                try {
                                                    JSONObject job = new JSONObject(result);
                                                    if(job.getBoolean("status")){
                                                        if(job.getString("msg").equals("chef profile update successfully")){
                                                            BaseClass.showToast(getApplicationContext(), "Profle Updated Successfully");
                                                            finish();
                                                        } else{
                                                            BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                                        }
                                                    } else{
                                                        BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }  else{
                            BaseClass.showToast(getApplicationContext(), "All Fields Required");
                        }
                    }  else{
                        BaseClass.showToast(getApplicationContext(), "All Fields Required");
                    }
            }  else{
                BaseClass.showToast(getApplicationContext(), "All Fields Required");
            }
        }  else{
            BaseClass.showToast(getApplicationContext(), "All Fields Required");
        }
    }


    public void chef_edit_profile_cancel(View view){

    }

    private void getMyCuisines() {

        try {
            String s = "";
            JSONObject jsonObject = new JSONObject("{}");


            new GetData(getApplicationContext(), ChefEditProfile.this).sendMyData(jsonObject, "cuisine",
                    ChefEditProfile.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            cuisineData = new Gson().fromJson(result, CuisineData.class);
//                            cuisineList.addAll(cuisineData.getCuisine_data());

                            setMyCuisines(cuisineData);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setMyCuisines(CuisineData cuisines){

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i<cuisines.getCuisine_data().size(); i++){
            arrayList.add(cuisines.getCuisine_data().get(i).getCuisine_name());
        }

        sp_cuisine.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                R.layout.spinner_row_1, arrayList);
        arrayAdapter1.setDropDownViewResource(R.layout.spinner_row_1);
        sp_cuisine.setAdapter(arrayAdapter1);
        sp_cuisine.setOnItemSelectedListener(this);
    }


}
