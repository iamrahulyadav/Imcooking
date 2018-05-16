package com.imcooking.activity.Sub;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.DishDetails;
import com.imcooking.R;
import com.imcooking.adapters.CartAdatper;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Cart_activity extends AppCompatActivity implements View.OnClickListener {
    TextView txtPlus,txtMinus,txt_DishCount,txtCartFollow,txtDishPrice,txtTax,txtTotalprice,txtChef_Name,txt_chef_follow;
    ImageView imgDish,imgChefImg;
    String dishname,dishimage,price,chef_id, foodieid;
    TinyDB tinyDB;
    private Gson gson = new Gson();
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    static int count=0;
    RatingBar ratingBar;

RecyclerView recyclerView;
   // ArrayList<AddCart> dishlist = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        tinyDB = new TinyDB(getApplicationContext());
        String login_data = tinyDB.getString("login_data");
        userDataBean = gson.fromJson(login_data, ApiResponse.UserDataBean.class);
        foodieid = userDataBean.getUser_id()+"";
        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recycler_cart_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // adapter = new CartAdatper(this, dishlist);
        ratingBar = findViewById(R.id.actvity_cart_rating);
        txtChef_Name=findViewById(R.id.chef_name);
        txt_chef_follow=findViewById(R.id.activity_cart_tv_chef_followers);
 /*       txtPlus=findViewById(R.id.tv_plus);
        txtPlus.setOnClickListener(this);
        txtMinus.setOnClickListener(this);
        txtMinus=findViewById(R.id.tv_minus);
 */
        txtCartFollow = findViewById(R.id.activity_cart_tv_follow);
        txt_DishCount=findViewById(R.id.tv_dish_count);
        txtDishPrice=findViewById(R.id.tv_dish_price);
        txtTax=findViewById(R.id.tv_tax);
        txtTotalprice=findViewById(R.id.tv_total);
        imgDish=findViewById(R.id.img_dish);
        imgChefImg=findViewById(R.id.chef_profile_image);

        txtCartFollow.setOnClickListener(this);
       setdetails();
    }

    private void setdetails() {
        AddToCart addToCart=new AddToCart();
        addToCart.setFoodie_id(Integer.parseInt(foodieid));
        new GetData(getApplicationContext(), Cart_activity.this)
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
                                            chef_id = apiResponse.getAdd_cart().getChef_id()+"";
                                           txtChef_Name.setText(apiResponse.getAdd_cart().getChef_name());
                                         // imgChefImg.setImageURI(apiResponse.getAdd_cart().getChef_image());
                                           txt_chef_follow.setText(apiResponse.getAdd_cart().getFollow()+" Followers");
                                          // txtDishPrice.setText(apiResponse.getAdd_cart().get);
                                            CartAdatper cartAdatper = new CartAdatper(getApplicationContext(),
                                                    apiResponse.getAdd_cart().getAdd_dish());
                                            recyclerView.setAdapter(cartAdatper);
                                            ratingBar.setRating(Float.parseFloat(apiResponse.getAdd_cart().getRating()+""));

                                            Picasso.with(getApplicationContext()).load(GetData.IMG_BASE_URL+apiResponse.getAdd_cart().getChef_image()).into(imgChefImg);
                                        }
//                                        apiResponse.isStatus();
//                                            Log.d("ShowResponse", apiResponse.getUser_data().toString());
                                    }
                                });
                            }
                        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
       String cnt;
        switch (id){
            case R.id.tv_plus:
                 cnt= String.valueOf(++count);

                setDishCount(cnt);

                break;

            case R.id.tv_minus:

             if(count>0)
             {
                 cnt= String.valueOf(--count);
                setDishCount(cnt);
             }
             else{
                 Toast.makeText(this, "no item added", Toast.LENGTH_SHORT).show();
                 }
            break;
            case R.id.activity_cart_tv_follow:
                if (chef_id!=null&&foodieid!=null){
                    followChef(chef_id, foodieid);
                }
                break;

    }}

    public void setDishCount(String s){
        txt_DishCount.setText(s);
    }

    private void followChef(String chef_id, String foodie_id){
        final String request="{\"chef_id\":"+chef_id+",\"foodie_id\":"+foodie_id+"}";
        new GetData(getApplicationContext(), Cart_activity.this).getResponse(request, "follow", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {
                setdetails();
                ApiResponse apiResponse = new ApiResponse();
                apiResponse = gson.fromJson(result, ApiResponse.class);
                String s = apiResponse.getMsg();
                Log.d("TAG", "onSuccess: "+result);
                Toast.makeText(Cart_activity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
