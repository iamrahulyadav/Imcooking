package com.imcooking.activity.Sub.Foodie;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imcooking.MainActivity1;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.ApiRequest.PlaceOrder;
import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.CartAdatper;
import com.imcooking.adapters.CartAddListAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.splash.SplashActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, CartAdatper.CartInterface, CartAddListAdatper.AddInterfaceMethod{

    TextView txtChef_Name ,txtShopNow, tvAdditem,tvplaceorder,txt_add_new_item,txtfollowers, txt_address,
            txt_add_address, txt_time_picker,txt_pick_add,
            txt_to_time_value,txtPayment;

    ImageView imgChefImg;
    int foodie_id;
    RatingBar ratingBar;
    RadioGroup radioGroup;
//    private Spinner addressSpi;
    RadioButton radioButtoncheck;
    LinearLayout cartLayout, linearLayoutplaceorde,no_record_Layout,linearLayoutpayment,
            linearLayout_delivery,linearLayout_pickup, linear_time_picker, linearTo;
    private TextView txtTax,txtTotalprice;
    private RecyclerView recyclerView;
    private String type="",isPickup, isHome,delivery_type="1" ;// 1 = delivery 2 = pickup
    private ApiResponse.UserDataBean userDataBean;
    private TinyDB tinyDB;
    private String login_data, user_phone;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        userDataBean = new ApiResponse.UserDataBean();
        tinyDB = new TinyDB(getApplicationContext());
        login_data = tinyDB.getString("login_data");
        Log.d("LoginData", login_data);
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);
        user_phone = userDataBean.getUser_phone();
        foodie_id = userDataBean.getUser_id();

        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recycler_cart_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

/*
        if (extras != null) {
            foodie_id = extras.getInt("foodie_id");
        }
*/

        linearTo = findViewById(R.id.actvity_cart_txtToLayout);
        txtPayment = findViewById(R.id.activity_cart_tv_payment);
        txtShopNow = findViewById(R.id.activity_cart_shop_now);
        txt_to_time_value = findViewById(R.id.actvity_cart_txtToTime);
        txt_add_new_item = findViewById(R.id.cart_tv_addnewitem);
        txt_time_picker = findViewById(R.id.actvity_cart_txtFromTimeValue);
        txt_add_address = findViewById(R.id.cart_addnewaddress);
        txtChef_Name=findViewById(R.id.chef_name);

//        addressSpi =  findViewById(R.id.activity_cart_spinner);

        txtTax=findViewById(R.id.tv_tax);
        txt_pick_add = findViewById(R.id.activity_cart_current_add);
        txt_address = findViewById(R.id.activity_cart_txtAddress);
        txtTotalprice=findViewById(R.id.tv_total);
        imgChefImg=findViewById(R.id.chef_profile_image);
        ratingBar=findViewById(R.id.activity_cart_rating);
        txtfollowers=findViewById(R.id.activity_cart_tv_chef_followers);
        radioGroup=findViewById(R.id.radioGroup);
        cartLayout = findViewById(R.id.cart_layput);
        no_record_Layout = findViewById(R.id.home_no_record_image);
        //int idradio=radioGroup.getCheckedRadioButtonId();
        // radioButton=findViewById(idradio);

        linearLayoutplaceorde=findViewById(R.id.cart_Linearlayout_placeorder);
        linearLayoutpayment=findViewById(R.id.cart_linearlayout_payment);
        linearLayout_delivery=findViewById(R.id.linearlayout_delivery_address);
        linearLayout_pickup=findViewById(R.id.linearlayout_pickup_address);
        tvAdditem=findViewById(R.id.cart_tv_addnewitem);
        tvplaceorder=findViewById(R.id.cart_tv_place_order);
        linear_time_picker = findViewById(R.id.actvity_cart_txtFromTime);

        //       set listener
        setListener();

        createMyDialog();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioButtonDelivery){
                    linearLayout_delivery.setVisibility(View.VISIBLE);
                    linearLayout_pickup.setVisibility(View.GONE);
                    delivery_type = "1";
                }
                else if(checkedId==R.id.radioButtonPick){
                    linearLayout_delivery.setVisibility(View.GONE);
                    linearLayout_pickup.setVisibility(View.VISIBLE);
                    txt_add_address.setVisibility(View.GONE);
                    delivery_type = "2";
                }
            }
        });

        setdetails();
    }

    private void setListener(){
        linear_time_picker.setOnClickListener(this);
        tvAdditem.setOnClickListener(this);
        tvplaceorder.setOnClickListener(this);
        txt_add_address.setOnClickListener(this);
        linearTo.setOnClickListener(this);
        txtPayment.setOnClickListener(this);
        txtShopNow.setOnClickListener(this);
        txt_add_new_item.setOnClickListener(this);
    }

    CartAdatper cartAdatper;
    private void setTime(final String type){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CartActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //                    eReminderTime.setText( selectedHour + ":" + selectedMinute);
                        String s = getTime(selectedHour, selectedMinute);

                        if (type.equalsIgnoreCase("to")){
                            txt_to_time_value.setText(s);
                           // txt_to_time_value.setText(selectedHour + " : " + selectedMinute);
                        } else if (type.equalsIgnoreCase("from")){
                            txt_time_picker.setText(s);
                            //txt_time_picker.setText(selectedHour + " : " + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    List<AddCart.AddDishBean> dishDetails;
    AddToCart addToCart;
    String TAG = CartActivity.class.getName(),chef_id;

    int hour, minutes;

    private String getTime(int hourOfDay, int minute){
        hour = hourOfDay;
        minutes = minute;
        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12){
            timeSet = "PM";
        }else{
            timeSet = "AM";
        }

        String min = "";
        if (minutes < 10)
            min = "0" + minutes ;
        else
            min = String.valueOf(minutes);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(min ).append(" ").append(timeSet).toString();
        return aTime;
    }

    private void setdetails()  {

        cartLayout.setVisibility(View.GONE);
        dishDetails = new ArrayList<>();
        addToCart=new AddToCart();
        addToCart.setFoodie_id(foodie_id);

        Log.d(TAG, "MyRequest: "+new Gson().toJson(addToCart));

        new GetData(getApplicationContext(), CartActivity.this)
                .getResponse(new Gson().toJson(addToCart), GetData.CART,
                        new GetData.MyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                final String response = result;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ApiResponse apiResponse = new Gson().fromJson(response,
                                                ApiResponse.class);
                                        if(apiResponse.isStatus()){
                                            no_record_Layout.setVisibility(View.GONE);
                                            cartLayout.setVisibility(View.VISIBLE);
                                            txtChef_Name.setText(apiResponse.getAdd_cart().getChef_name());
                                            chef_id = apiResponse.getAdd_cart().getChef_id()+"";
                                            // imgChefImg.setImageURI(apiResponse.getAdd_cart().getChef_image());
                                            if (apiResponse.getAdd_cart().getFollow()>1){
                                                txtfollowers.setText(apiResponse.getAdd_cart().getFollow()+" Followers");
                                            }
                                            else {
                                                txtfollowers.setText(apiResponse.getAdd_cart().getFollow()+" Follower");
                                            }
                                            if (apiResponse.getAdd_cart().getAdd_dish()!=null &&
                                                    apiResponse.getAdd_cart().getAdd_dish().size()>0)
                                             HomeFragment.cart_icon.setText(apiResponse.getAdd_cart().getAdd_dish().size() + "");
                                            tinyDB.putString("cart_count", apiResponse.getAdd_cart().getAdd_dish().size() + "");

                                            txt_pick_add.setText(apiResponse.getAdd_cart().getChef_address());
                                            if (apiResponse.getAdd_cart().getChef_image()!=null){
                                                Picasso.with(getApplicationContext()).load(GetData.IMG_BASE_URL +
                                                        apiResponse.getAdd_cart().getChef_image()).skipMemoryCache().into(imgChefImg);
                                            } else {
                                                imgChefImg.setBackgroundResource(R.drawable.details_profile);
                                            }
                                            ratingBar.setRating(apiResponse.getAdd_cart().getRating());
                                            dishDetails.addAll(apiResponse.getAdd_cart().getAdd_dish());
                                            update_total_price();
                                            setMyAdapter(dishDetails);
                                        }
                                        else {
                                            no_record_Layout.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            }
                        });
    }

    private void setMyAdapter(List<AddCart.AddDishBean> dishDetails){
        cartAdatper = new CartAdatper(getApplicationContext(),
                dishDetails, this);
        recyclerView.setAdapter(cartAdatper);
//        cartAdatper.CartInterfaceMethod(this);
    }

    private void update_total_price(){
        double total_price = 0;
        for(int i=0; i<dishDetails.size(); i++){
            Double s_q = Double.parseDouble(dishDetails.get(i).getDish_quantity_selected());
            Double d_p = Double.parseDouble(dishDetails .get(i).getDish_price());
            Double s_price = d_p * s_q;
            total_price = total_price + s_price;
        }

        txtTotalprice.setText("£" + total_price + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.cart_tv_place_order:
                if(user_phone == null){
                    dialog_alert.show();
                } else {
                    checkDishAvailableTime();
                }
                break;
            case R.id.cart_tv_addnewitem:
                startActivity(new Intent(CartActivity.this, OtherDishDishActivity.class)
                        .putExtra("chef_id", chef_id));
                break;
            case R.id.radioGroup:
                //if(){}

                break;
            case R.id.actvity_cart_txtFromTime:
                type = "from";
                setTime(type);
                break;
            case R.id.cart_addnewaddress:
                startActivity(new Intent(CartActivity.this, AddAddressActivity.class));
                break;
            case R.id.actvity_cart_txtToLayout:
                type = "to";
                setTime(type);
                break;
            case R.id.activity_cart_tv_payment:
                if(delivery_type.equals("1")) {
                    if (txt_address.getText().toString().equals("Select an Address")) {
                        BaseClass.showToast(getApplicationContext(), "Please select an address");
                    } else {
                        //click_payment();
                        getAvailability();
                    }
                } else {
//                    click_payment();
                    getLatLong(txt_pick_add.getText().toString().trim());
                }
                break;
            case R.id.activity_cart_shop_now:
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
        }
    }

    private void click_payment(){
        String address;
        List<PlaceOrder.DishorderBean>dishorderBeanList = new ArrayList<>();
        if (txt_address.getText().toString().isEmpty())
            address = txt_pick_add.getText().toString().trim();
        else
            address = txt_address.getText().toString().trim();

        for (int i = 0; i<dishDetails.size();i++){
            PlaceOrder.DishorderBean dishorderBean = new PlaceOrder.DishorderBean();
            dishorderBean.setDish_id(dishDetails.get(i).getDish_id()+"");
            dishorderBean.setPrice(dishDetails.get(i).getDish_price());
            dishorderBean.setQuantity(dishDetails.get(i).getDish_quantity_selected());
            dishorderBeanList.add(dishorderBean);
        }

        PlaceOrder placeOrder = new PlaceOrder();
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(currentTime);
        placeOrder.setChef_id(chef_id);
        placeOrder.setFoodie_id(foodie_id+"");
        placeOrder.setDishorder(dishorderBeanList);
        placeOrder.setDelivery_type(delivery_type);
        placeOrder.setTax("");
        placeOrder.setTotal_price(txtTotalprice.getText().toString().trim().replace("£",""));
        placeOrder.setAddress(address);
        placeOrder.setFrom_time(txt_time_picker.getText().toString().trim());
        placeOrder.setTo_time(txt_to_time_value.getText().toString().trim());
        placeOrder.setBookdate(date);
        placeOrder.setPayment_type("");
        placeOrder.setTransaction_id("COD");
        startActivity(new Intent(CartActivity.this, MainActivity1.class)
                .putExtra("order_details", new Gson().toJson(placeOrder))
                .putExtra("chef_name", txtChef_Name.getText().toString().trim()));

    }

    private void checkDishAvailableTime(){

        boolean st = true;
        for(int i=0; i<dishDetails.size(); i++){
            String time_from = dishDetails.get(i).getDish_from();
            String time_to = dishDetails.get(i).getDish_to();

            int time_status = BaseClass.compareToCurrentTime(time_to);
            Log.d("CurrentTime", time_status + "");
            if(time_status == 1){
//                st = true;
                } else if (time_status == 0){
                st = false;
                Toast.makeText(CartActivity.this, "The dish " +
                        dishDetails.get(i).getDish_name().toUpperCase() +
                        " is not available after " + time_to + ". Please remove this dish from your cart to place your order now.", Toast.LENGTH_LONG).show();
//                BaseClass.showToast(getApplicationContext(), "");
            }

        }
        if(st){
            linearLayoutplaceorde.setVisibility(LinearLayout.GONE);
            linearLayoutpayment.setVisibility(RelativeLayout.VISIBLE);
        }
    }

    private Dialog dialog_alert;
    private TextView tv_dialog, tv_ok_dialog, tv_cross_dialog;

    private void createMyDialog(){

        dialog_alert = new Dialog(CartActivity.this);
        dialog_alert.setContentView(R.layout.dialog_add_dish);
        dialog_alert.setCancelable(false);
        dialog_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_alert.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        tv_dialog = dialog_alert.findViewById(R.id.dialog_add_dish_text);
        tv_ok_dialog = dialog_alert.findViewById(R.id.dialog_add_dish_btn);
        tv_cross_dialog = dialog_alert.findViewById(R.id.dialog_add_dish_cross);

        tv_dialog.setText(getResources().getString(R.string.dialog_add_dish_text_4));

        tv_ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_alert.dismiss();
            }
        });
        tv_cross_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_alert.dismiss();
            }
        });
    }

    private void addressDialog(final List<AddressListData.AddressBean> addressBeanList) {
        LinearLayout ll = findViewById(R.id.cart_item_right1);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(CartActivity.this, "h", Toast.LENGTH_SHORT).show();
                openDialog(addressBeanList);
            }
        });
    }

    private List<AddressListData.AddressBean> addressBeanList;

    private void getAddress(){
        addressBeanList = new ArrayList<>();
        new GetData(getApplicationContext(), CartActivity.this).getResponse("{\"foodie_id\":" +
                HomeFragment.foodie_id + "}", "addresslist", new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //   layout.setVisibility(View.VISIBLE);
                        AddressListData addressListData = new AddressListData();
                        if (result!=null){
                            addressListData = new Gson().fromJson(result, AddressListData.class);
                            if (addressListData.getAddress()!=null){
                                addressBeanList.addAll(addressListData.getAddress());
                                addressDialog(addressBeanList);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void CartInterfaceMethod(int position, String click_type) {
        if(click_type.equals("plus")){
            int i = Integer.parseInt(dishDetails.get(position).getDish_quantity_selected());
            if (dishDetails.get(position).getDish_quantity()!=null){
                if(i < Integer.parseInt(dishDetails.get(position).getDish_quantity())) {
                    plus_minus_api(position, (i+1));
                } else{
                    BaseClass.showToast(getApplicationContext(),
                            "You have reached maximum number of availability for this item.");
                }
            }

        } else if(click_type.equals("minus")){
            int i = Integer.parseInt(dishDetails.get(position).getDish_quantity_selected());
            if(i > 1) {
                plus_minus_api(position, (i-1));
            } else{
                BaseClass.showToast(getApplicationContext(),
                        "You can not select less than \"1\" item");
            }
        } else {
            deleteData(position, chef_id);
        }
    }

    private void plus_minus_api(final int position, final int selected_qty){
        final String s = "{\"chef_id\":" + chef_id + ",\"foodie_id\":" + foodie_id +
                ",\"dish_id\":" + dishDetails.get(position).getDish_id() + ",\"dish_quantity\":" + selected_qty
                + "}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            new GetData(getApplicationContext(), CartActivity.this).sendMyData(jsonObject,
                    GetData.CART_SELECTED_QTY, CartActivity.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            ApiResponse apiResponse = new Gson().fromJson(result, ApiResponse.class);
                            if(apiResponse.isStatus()){
                                if(apiResponse.getMsg().equals("update quantity in cart Successfully")){
                                    dishDetails.get(position).setDish_quantity_selected(selected_qty + "");
                                    cartAdatper.notifyDataSetChanged();
                                    BaseClass.showToast(getApplicationContext(), "Updated Selected Item");
                                    update_total_price();
                                } else{
                                    BaseClass.showToast(getApplicationContext(), "Something Went Wrong.");
                                }
                            } else{
                                BaseClass.showToast(getApplicationContext(), "Something Went Wrong.");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void deleteData(final int pos, String chef_id){

        String dishId = dishDetails.get(pos).getDish_id()+"";
        AddToCart addToCart=new AddToCart();
        addToCart.setChef_id(Integer.parseInt(chef_id));
        addToCart.setFoodie_id(Integer.parseInt(HomeFragment.foodie_id));
        addToCart.setDish_id(dishId);
        addToCart.setAddcart_yes("");
        addToCart.setAddcart_id(dishDetails.get(pos).getAddcart_id()+"");

        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(addToCart));
            Log.d(TAG, "deleteData: " + jsonObject);
            new GetData(getApplicationContext(), CartActivity.this).sendMyData(jsonObject, GetData.ADD_CART, CartActivity.this, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d(CartAdatper.class.getName(), "Rakhi: "+result);
                    dishDetails.remove(pos);
                    cartAdatper.notifyDataSetChanged();

                    int i = Integer.parseInt(tinyDB.getString("cart_count"));
                    tinyDB.putString("cart_count", (i - 1) + "");

                    BaseClass.showToast(getApplicationContext(), "Item successfully removed from your cart.");
                    update_total_price();
                    if (dishDetails.size()==0){
                        cartLayout.setVisibility(View.GONE);
                        no_record_Layout.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private BottomSheetDialog dialog;

    private void openDialog(List<AddressListData.AddressBean> addressBeanList){
        dialog = new BottomSheetDialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_cart_address);
        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_cart_address_recycler);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        setAddList(recyclerView, addressBeanList);
    }

    private void setAddList(RecyclerView savedAddress,  List<AddressListData.AddressBean> addressBeanList){
        CartAddListAdatper addListAdatper = new CartAddListAdatper(getApplicationContext(), addressBeanList);
        savedAddress.setAdapter(addListAdatper);
        addListAdatper.AddInterfaceMethod(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void AddressInterfaceMethod(View view, int position) {
        dialog.dismiss();
        txt_address.setText(addressBeanList.get(position).getAddress_title()+" : \n \n"
                +addressBeanList.get(position).getAddress_address());

        getLatLong(addressBeanList.get(position).getAddress_address());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private JSONObject jsonObject2;
    private double latitude,longitude;

    private void getLatLong(String place){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+place+"&key=AIzaSyD8rFBw_mmTdTCVQ4IdjhzcXt5P1trKrYw";
        url = url.replace(" ", "+");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i=0; i<jsonArray.length();i++){
                            JSONObject jsonObject ;
                            jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("geometry");
                            jsonObject2 = jsonObject1.getJSONObject("location");
                            latitude = jsonObject2.getDouble("lat");
                            longitude = jsonObject2.getDouble("lng");
                            Log.d(TAG, "onActivityResult: "+latitude+"\n"+longitude);
                            if (!delivery_type.equalsIgnoreCase("1")){
                                getAvailability();
                            }
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private void getAvailability(){

        String request = "{\"chef_id\":\""+chef_id+"\",\n" +
                " \"foodie_lat\":\""+latitude+"\",\n" +
                " \"foodie_lang\":\""+longitude+"\"\n" +
                "}";
        Log.d(TAG, "MyRequest: "+request);

        new GetData(getApplicationContext(), CartActivity.this).getResponse(request,
                GetData.CHECK_FOODIE_DISTANCE, new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject obj = new JSONObject(result);

                            Log.d("My App", obj.toString());

                            boolean status = obj.getBoolean("status");
                            if (status){
                                String msg = obj.getString("msg");
                                if (msg.equalsIgnoreCase("No")){
                                    createPayDialog();
                               //      Toast.makeText(CartActivity.this, "dish not available", Toast.LENGTH_SHORT).show();
                                } else {
                                    click_payment();
                                }
                            }

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
                        }

                    }
                });
            }
        });


    }

    private void createPayDialog(){
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog_payment_confm);

        dialog.findViewById(R.id.txtdialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

    }

}