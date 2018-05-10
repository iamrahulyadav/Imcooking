package com.imcooking.activity.Sub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.webservices.GetData;

public class Cart_activity extends AppCompatActivity {
TextView txtPlus,txtMinus,txt_itemCount,txtDishPrice,txtTax,txtTotalprice,txtChef_Name,txt_chef_follow;
ImageView imgDish,imgChefImg;
static int count=0;
int foodie_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            foodie_id = extras.getInt("foodie_id");
            // and get whatever type user account id is
        }
        txtChef_Name=findViewById(R.id.chef_name);




        setdetails();
    }

    private void setdetails() {
        AddToCart addToCart=new AddToCart();
        addToCart.setFoodie_id(foodie_id);
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

                                            txtChef_Name.setText(apiResponse.getAdd_cart().getChef_name());
                                        }

//                                        apiResponse.isStatus();

//                                            Log.d("ShowResponse", apiResponse.getUser_data().toString());



                                    }
                                });
                            }
                        });

    }

}
