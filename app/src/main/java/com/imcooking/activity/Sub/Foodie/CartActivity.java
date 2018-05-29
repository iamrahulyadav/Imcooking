package com.imcooking.activity.Sub.Foodie;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.adapters.CartAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.webservices.GetData;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
TextView txtPlus,txtMinus,txt_DishCount,txtDishPrice,txtTax,txtTotalprice,txtChef_Name,txt_chef_follow;
ImageView imgDish,imgChefImg;
static int count=0;
int foodie_id;
RecyclerView recyclerView;
   // ArrayList<AddCart> dishlist = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_3);

        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recycler_cart_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // adapter = new CartAdatper(this, dishlist);

        if (extras != null) {
            foodie_id = extras.getInt("foodie_id");
            // and get whatever type user account id is
        }
        txtChef_Name=findViewById(R.id.chef_name);
        txt_chef_follow=findViewById(R.id.activity_cart_tv_chef_followers);
 /*       txtPlus=findViewById(R.id.tv_plus);
        txtPlus.setOnClickListener(this);
        txtMinus.setOnClickListener(this);
        txtMinus=findViewById(R.id.tv_minus);
 */       txt_DishCount=findViewById(R.id.tv_dish_count);
        txtDishPrice=findViewById(R.id.tv_dish_price);
        txtTax=findViewById(R.id.tv_tax);
        txtTotalprice=findViewById(R.id.tv_total);
        imgDish=findViewById(R.id.img_dish);
        imgChefImg=findViewById(R.id.chef_profile_image);
       setdetails();
    }

    private void setdetails() {
        AddToCart addToCart=new AddToCart();
        addToCart.setFoodie_id(foodie_id);
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
                                           txt_chef_follow.setText(apiResponse.getAdd_cart().getFollow()+"Followers");
                                            HomeFragment.cart_icon.setText(apiResponse.getAdd_cart().getAdd_dish().size()+"");
                                          // txtDishPrice.setText(apiResponse.getAdd_cart().get);
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


    @Override
    public void onClick(View v) {
        int id = v.getId();
       String cnt;
        switch (id){
           /* case R.id.tv_plus:
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
*/


    }}

    public void setDishCount(String s){
        txt_DishCount.setText(s);
    }
}
