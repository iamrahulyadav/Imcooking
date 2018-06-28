package com.imcooking.activity.Sub.Foodie;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.ApiRequest.PlaceOrder;
import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.adapters.CartAdatper;
import com.imcooking.adapters.CartAddListAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

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
    private Spinner addressSpi;
    RadioButton radioButtoncheck;
    LinearLayout cartLayout, linearLayoutplaceorde,no_record_Layout,linearLayoutpayment,
            linearLayout_delivery,linearLayout_pickup, linear_time_picker, linearTo;
    private TextView txtTax,txtTotalprice;
    RecyclerView recyclerView;
    private String type="", delivery_type="1" ;// 1 = delivery 2 = pickup

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/
     /*   String imageUrl= "https://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg";
        try {
            URL url = new URL(imageUrl);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.d(TAG, "imgbit"+image);
        } catch(IOException e) {
            System.out.println(e);
        }*/
        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recycler_cart_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (extras != null) {
            foodie_id = extras.getInt("foodie_id");
        }

        linearTo = findViewById(R.id.actvity_cart_txtToLayout);
        txtPayment = findViewById(R.id.activity_cart_tv_payment);
        txtShopNow = findViewById(R.id.activity_cart_shop_now);
        txt_to_time_value = findViewById(R.id.actvity_cart_txtToTime);
        txt_add_new_item = findViewById(R.id.cart_tv_addnewitem);
        txt_time_picker = findViewById(R.id.actvity_cart_txtFromTimeValue);
        txt_add_address = findViewById(R.id.cart_addnewaddress);
        txtChef_Name=findViewById(R.id.chef_name);
        addressSpi =  findViewById(R.id.activity_cart_spinner);
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

    private void setdetails() {

        cartLayout.setVisibility(View.GONE);
        dishDetails = new ArrayList<>();
        addToCart=new AddToCart();
        addToCart.setFoodie_id(foodie_id);
        Log.d(TAG, "MyRequest: "+new Gson().toJson(addToCart));
        new GetData(getApplicationContext(), CartActivity.this)
                .getResponse(new Gson().toJson(addToCart), "cart",
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
                                            HomeFragment.cart_icon.setText(apiResponse.getAdd_cart().getAdd_dish().size()+"");
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
                linearLayoutplaceorde.setVisibility(LinearLayout.GONE);
                linearLayoutpayment.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.cart_tv_addnewitem:
                startActivity(new Intent(CartActivity.this, OtherDishDishActivity.class).putExtra("chef_id", chef_id));
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
                /*
                 * chef_id : 7
                 * foodie_id : 2
                 * dishorder : [{"dish_id":"2","quantity":"2","price":"100"},{"dish_id":"2","quantity":"2","price":"100"}]
                 * delivery_type : 2
                 * tax : 2.00
                 * total_price : 200
                 * address : 2
                 * from_time : 245
                 * to_time : 2
                 * payment_type : cod
                 * transaction_id : 45645645456
                 * bookdate : 1
                 */
                String address;
                if (txt_address.getText().toString().trim().isEmpty()&& txt_pick_add.getText().toString().trim().isEmpty()){
                    BaseClass.showToast(getApplicationContext(), "Please select an address");
                } else {
                    List<PlaceOrder.DishorderBean>dishorderBeanList = new ArrayList<>();
                    if (txt_address.getText().toString().isEmpty()) address = txt_pick_add.getText().toString().trim();
                    else address = txt_address.getText().toString().trim();

                    for (int i = 0; i<dishDetails.size();i++){
                        PlaceOrder.DishorderBean dishorderBean = new PlaceOrder.DishorderBean();
                        dishorderBean.setDish_id(dishDetails.get(i).getDish_id()+"");
                        dishorderBean.setPrice(dishDetails.get(i).getDish_price());
                        dishorderBean.setQuantity(dishDetails.get(i).getDish_quantity_selected());
                        dishorderBeanList.add(dishorderBean);
                    }

                    PlaceOrder placeOrder = new PlaceOrder();
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = format.format(currentTime);
                    Log.d(TAG, "Rakhi "+date);
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
                    startActivity(new Intent(CartActivity.this, Payment1Activity.class)
                            .putExtra("order_details", new Gson().toJson(placeOrder))
                            .putExtra("chef_name", txtChef_Name.getText().toString().trim()));
                }
                break;
            case R.id.activity_cart_shop_now:
                finish();
                break;
        }
    }


    private void addressDialog(final List<AddressListData.AddressBean> addressBeanList) {
        txt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        setAddList(recyclerView, addressBeanList);
    }

    private CartAddListAdatper addListAdatper;
    private void setAddList(RecyclerView savedAddress,  List<AddressListData.AddressBean> addressBeanList){
        addListAdatper = new CartAddListAdatper(getApplicationContext(), addressBeanList);
        savedAddress.setAdapter(addListAdatper);
        addListAdatper.AddInterfaceMethod(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void AddressInterfaceMethod(View view, int position) {
        dialog.dismiss();
        txt_address.setText(addressBeanList.get(position).getAddress_title()+" : \n \n"
                +addressBeanList.get(position).getAddress_address());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
