package com.imcooking.fragment.chef.chefprofile;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.ModelFoodieRequestADish;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodieRequestADish extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public FoodieRequestADish() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foodie_request_a_dish, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

//    private CuisineData cuisineData = new CuisineData();
    private String selected_cuisine, selected_cuisine_id;
    private EditText edt_dish_title, edt_dish_note;
    private TextView tv_plus, tv_minus, tv_qty, tv_date, tv_time, btn;
    private String str_name, str_qty, str_date = "2018/06/03", str_time = "24:00 PM", str_note;
    private LinearLayout layout_date, layout_time;
//    private TextView tv_date, tv_time;

    private ApiResponse.UserDataBean userDataBean;
    private TinyDB tinyDB;
    private String loginData, user_id, user_phone, user_full_name;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private Spinner sp;

    private void init(){

        edt_dish_title = getView().findViewById(R.id.foodie_request_a_dish_name);
        edt_dish_note = getView().findViewById(R.id.foodie_request_a_dish_note);

        tv_plus = getView().findViewById(R.id.foodie_request_a_dish_qty_plus);
        tv_minus = getView().findViewById(R.id.foodie_request_a_dish_qty_minus);
        tv_qty = getView().findViewById(R.id.foodie_request_a_dish_qty);
        tv_date = getView().findViewById(R.id.foodie_request_a_dish_date);
        tv_time = getView().findViewById(R.id.foodie_request_a_dish_time);
        btn = getView().findViewById(R.id.foodie_request_a_dish_btn);

        tv_plus.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        btn.setOnClickListener(this);

        layout_date = getView().findViewById(R.id.foodie_request_a_dish_date_icon);
        layout_time = getView().findViewById(R.id.foodie_request_a_dish_time_icon);

        layout_date.setOnClickListener(this);
        layout_time.setOnClickListener(this);

        sp = getView().findViewById(R.id.foodie_request_a_dish_spinner_cuisine);
        sp.setOnItemSelectedListener(this);

//        getCuisines();

        createMyDialog();
        createMyDatePicker();
        setMyCuisines(ChefHome.cuisineData);
    }

    private void createMyDatePicker(){
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tv_date.setText(sdf.format(myCalendar.getTime()));
            }
        };
    }

    private void getCuisines(){
        /*try {
            String s = "";
            JSONObject jsonObject = new JSONObject("{}");


//            layout.setVisibility(View.GONE);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, "cuisine",
                    getActivity(), new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
//                            layout.setVisibility(View.VISIBLE);
                            cuisineData = new Gson().fromJson(result, CuisineData.class);
//                            cuisineList.addAll(cuisineData.getCuisine_data());

                            setMyCuisines(cuisineData);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private void setMyCuisines(CuisineData cuisines){

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i<cuisines.getCuisine_data().size(); i++){
            arrayList.add(cuisines.getCuisine_data().get(i).getCuisine_name());
        }

        if(getContext() != null) {
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(),
                    R.layout.spinner_row_1, arrayList);
            arrayAdapter1.setDropDownViewResource(R.layout.spinner_row_1);
            sp.setAdapter(arrayAdapter1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        selected_cuisine = adapterView.getItemAtPosition(i).toString();
        selected_cuisine_id = ChefHome.cuisineData.getCuisine_data().get(i).getCuisine_id() + "";
        Log.d("MySpinner", selected_cuisine_id);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.foodie_request_a_dish_qty_plus) {

            tv_qty.setText(Integer.parseInt(tv_qty.getText().toString()) + 1 + "");

        } else if (id == R.id.foodie_request_a_dish_qty_minus) {

            if (Integer.parseInt(tv_qty.getText().toString()) > 1) {
                tv_qty.setText(Integer.parseInt(tv_qty.getText().toString()) - 1 + "");
            }
        } else if (id == R.id.foodie_request_a_dish_btn) {

            request();

        } else if (id == R.id.foodie_request_a_dish_date_icon) {

            new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            dob_pick();

        } else if (id == R.id.foodie_request_a_dish_time_icon) {


            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(),
                    new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                    eReminderTime.setText( selectedHour + ":" + selectedMinute);
                    tv_time.setText(selectedHour + " : " + selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        } else {
        }
    }

    Calendar mCalendar = Calendar.getInstance();
    long timeInMilliseconds;
    private int mYear, mMonth, mDay;
    private String dob;

    private void dob_pick(){
        // Get Current Date
/*        final Calendar c = Calendar.getInstance();
        mYear = Calendar.getInstance().get(Calendar.YEAR)-18;
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        timeInMilliseconds = Utility.getTimeDate(mYear + "-" + mMonth + "-" + mDay);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dob = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        tv_date.setText(Utility.convertSimpleDate(dob));
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(timeInMilliseconds);
   */ }

    private void request(){

        userDataBean = new ApiResponse.UserDataBean();
        tinyDB = new TinyDB(getContext());
        loginData = tinyDB.getString("login_data");
        Log.d("LoginData", loginData);
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_id = userDataBean.getUser_id() + "";
        user_full_name = userDataBean.getFull_name() + "";
        user_phone = userDataBean.getUser_phone();
        /*if(user_full_name.equals("null")){
            Toast.makeText(getContext(), "full name null", Toast.LENGTH_SHORT).show();
        }
        if(user_phone == null){
            Toast.makeText(getContext(), "phone null", Toast.LENGTH_SHORT).show();
        }*/

        str_name = edt_dish_title.getText().toString().trim();
        str_qty = tv_qty.getText().toString().trim();
        str_date = tv_date.getText().toString().trim();
        str_time = tv_time.getText().toString().trim();
        str_note = edt_dish_note.getText().toString().trim();

        if(user_phone != null) {
            if (!str_name.isEmpty()) {
                if (!str_qty.equals("0")) {
//                if(!str_date.isEmpty()){
//                    if(!str_time.isEmpty()){
                    if (!str_note.isEmpty()) {
                        if (BaseClass.isNetworkConnected(getContext())) {

                            ModelFoodieRequestADish requestData = new ModelFoodieRequestADish();
                            requestData.setRequest_chef_id(ChefHome.chef_id);
                            requestData.setRequest_foodie_id(user_id);
                            requestData.setRequest_dishname(str_name);
                            requestData.setRequest_status("1");
                            requestData.setRequest_cusine_name(selected_cuisine);
                            requestData.setRequest_quantity(str_qty);
                            requestData.setRequest_date(str_date);
                            requestData.setRequest_time(str_time);
                            requestData.setRequest_note(str_note);

                            String s = new Gson().toJson(requestData);

                            Log.d("MyRequest", s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                new GetData(getContext()).sendMyData(jsonObject, GetData.REQUEST_DISH,
                                        getActivity(), new GetData.MyCallback() {
                                            @Override
                                            public void onSuccess(String result) {

                                                ApiResponse apiResponse = new Gson().fromJson(result, ApiResponse.class);
                                                if (apiResponse.isStatus()) {
                                                    if (apiResponse.getMsg().equals("Add Request dish Successfully")) {
                                                        BaseClass.showToast(getContext(),
                                                                "Dish Request successfully sent");
                                                    } else {
                                                        BaseClass.showToast(getContext(),
                                                                "Dish not added");
                                                    }

                                                    edt_dish_title.setText("");
                                                    tv_qty.setText("0");
                                                    tv_date.setText("");
                                                    tv_time.setText("");
                                                    edt_dish_note.setText("");
                                                } else {
                                                    BaseClass.showToast(getContext(),
                                                            getResources().getString(R.string.error));
                                                }
                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            BaseClass.showToast(getContext(), "Please check your internet connection");
                        }
                    } else {
                        BaseClass.showToast(getContext(), "Please enter description");
                    }
/*                    }  else{
                        BaseClass.showToast(getContext(),"Please select a time");
                    }
                }  else{
                    BaseClass.showToast(getContext(),"Please select a date");
                }*/
                } else {
                    BaseClass.showToast(getContext(), "Please select dish quantity");
                }
            } else {
                BaseClass.showToast(getContext(), "Please enter dish title");
            }
        } else{
            dialog.show();
        }
    }

    private Dialog dialog;
    private TextView tv_dialog, tv_ok_dialog, tv_cross_dialog;

    private void createMyDialog(){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_dish);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        tv_dialog = dialog.findViewById(R.id.dialog_add_dish_text);
        tv_ok_dialog = dialog.findViewById(R.id.dialog_add_dish_btn);
        tv_cross_dialog = dialog.findViewById(R.id.dialog_add_dish_cross);

        tv_dialog.setText(getResources().getString(R.string.dialog_add_dish_text_3));

        tv_ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_cross_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}