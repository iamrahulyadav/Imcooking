package com.imcooking.activity.Sub.Foodie;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.adapters.CartAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {
TextView txtChef_Name ,tvAdditem,tvplaceorder,txtfollowers;
ImageView imgChefImg;
int foodie_id;
RatingBar ratingBar;
RadioGroup radioGroup;
RadioButton radioButtoncheck;
LinearLayout linearLayoutplaceorde,linearLayoutpayment,linearLayout_delivery,linearLayout_pickup;
public static TextView  txtTax,txtTotalprice;
RecyclerView recyclerView;
  @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recycler_cart_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (extras != null) {
            foodie_id = extras.getInt("foodie_id");
            // and get whatever type user account id is
        }
        txtChef_Name=findViewById(R.id.chef_name);
        txtTax=findViewById(R.id.tv_tax);
        txtTotalprice=findViewById(R.id.tv_total);
        imgChefImg=findViewById(R.id.chef_profile_image);
        ratingBar=findViewById(R.id.activity_cart_rating);
        txtfollowers=findViewById(R.id.activity_cart_tv_chef_followers);
        radioGroup=findViewById(R.id.radioGroup);
      //int idradio=radioGroup.getCheckedRadioButtonId();
     // radioButton=findViewById(idradio);
      linearLayoutplaceorde=findViewById(R.id.cart_Linearlayout_placeorder);
      linearLayoutpayment=findViewById(R.id.cart_linearlayout_payment);
      linearLayout_delivery=findViewById(R.id.linearlayout_delivery_address);
      linearLayout_pickup=findViewById(R.id.linearlayout_pickup_address);
      tvAdditem=findViewById(R.id.cart_tv_addnewitem);
      tvplaceorder=findViewById(R.id.cart_tv_place_order);
      tvAdditem.setOnClickListener(this);
      tvplaceorder.setOnClickListener(this);
      radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {

     if(checkedId==R.id.radioButton1){
         linearLayout_delivery.setVisibility(View.VISIBLE);
         linearLayout_pickup.setVisibility(View.GONE);

     }
     else if(checkedId==R.id.radioButton2){
         linearLayout_delivery.setVisibility(View.GONE);
         linearLayout_pickup.setVisibility(View.VISIBLE);

     }
          }
      });
       setdetails();
    }

    String TAG = CartActivity.class.getName();
    private void setdetails() {
        AddToCart addToCart=new AddToCart();
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

                                           txtChef_Name.setText(apiResponse.getAdd_cart().getChef_name());
                                         // imgChefImg.setImageURI(apiResponse.getAdd_cart().getChef_image());
                                            if (apiResponse.getAdd_cart().getFollow()>1){
                                                txtfollowers.setText(apiResponse.getAdd_cart().getFollow()+"Followers");
                                            }
                                            else {
                                                txtfollowers.setText(apiResponse.getAdd_cart().getFollow()+"Follower");

                                            }
                                          HomeFragment.cart_icon.setText(apiResponse.getAdd_cart().getAdd_dish().size()+"");
                                            Picasso.with(getApplicationContext()).load(GetData.IMG_BASE_URL +
                                                    apiResponse.getAdd_cart().getChef_image()).into(imgChefImg);
                                                ratingBar.setRating(apiResponse.getAdd_cart().getRating());
                                            CartAdatper cartAdatper = new CartAdatper(getApplicationContext(),
                                                    apiResponse.getAdd_cart().getAdd_dish());
                                            recyclerView.setAdapter(cartAdatper);
                                        }

//                                        apiResponse.isStatus();

//                                            Log.d("ShowResponse", apiResponse.getUser_data().toString());

                                    }
                                });
                            }
                        });

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

                break;
            case R.id.radioGroup:
               //if(){}

                break;
    }
}

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
}


