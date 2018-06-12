package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.OtherDish;
import com.imcooking.R;
import com.imcooking.adapters.OtherDishAdatper;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OtherDishActivity extends AppBaseActivity implements OtherDishAdatper.CuisionInterface{
    private TextView tv_title,txtReset, txtFilter,txtLike, txtchecfName,txtcall, txtAddress, txtfollower ;
    private ImageView btnHome ;
    LinearLayout layout;
    RecyclerView recyclerView;
    RatingBar ratingBar;
    ImageView imgPic;
    OtherDishAdatper otherDishAdatper;
    List<OtherDish.ChefDishBean>chefDishBeans = new ArrayList<>();
    OtherDish otherDish = new OtherDish();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_dish);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        init();

        //find id
    }

    private void init(){
        layout = findViewById(R.id.chef_dish);
        recyclerView = findViewById(R.id.activity_other_dish_recycler);
        txtLike = findViewById(R.id.activity_other_dish_txtChefLike);
        txtAddress = findViewById(R.id.activity_other_dish_txtChefAddress);
        txtchecfName = findViewById(R.id.activity_other_dish_txtChefName);
        txtfollower = findViewById(R.id.activity_other_dish_txtChefFollower);
        txtcall = findViewById(R.id.activity_other_dish_txtCall);
        imgPic = findViewById(R.id.other_dish_profile_image);
        ratingBar = findViewById(R.id.activity_other_dish_rating);
        chef_id = getIntent().getStringExtra("chef_id");

        CustomLayoutManager manager = new CustomLayoutManager(getApplicationContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        getChefOtherList(chef_id);
    }

    String chef_id;

    private void getChefOtherList(String chef_id){
        layout.setVisibility(View.GONE);

        String detailRequest = "{\"chef_id\":" + chef_id + "}";
        new GetData(getApplicationContext(),OtherDishActivity.this).getResponse(detailRequest,
                "dishchef",
                new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout.setVisibility(View.VISIBLE);
                        if (result!=null){
                            otherDish = new Gson().fromJson(result, OtherDish.class);
                            if (otherDish.isStatus()){
                                chefDishBeans.addAll(otherDish.getChef_dish());
                                txtfollower.setText(otherDish.getChef().getFollow()+" Followers");
                                txtchecfName.setText(otherDish.getChef().getChef_name());
                                txtAddress.setText(otherDish.getChef().getAddress());
                                txtLike.setText(otherDish.getChef().getLike()+" Likes ");
                                ratingBar.setRating(otherDish.getChef().getRating());
                                txtcall.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (otherDish.getChef().getChef_phone()!=null){
                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:"+otherDish.getChef().getChef_phone()));
                                            startActivity(intent);
                                        }
                                    }
                                });
                                if (otherDish.getChef().getChef_image()!=null){
                                    Picasso.with(getApplicationContext()).load(GetData.IMG_BASE_URL+otherDish.getChef().getChef_image())
                                            .into(imgPic);
                                }
                                setDishAdapter();
                            }
                        }
                    }
                });
            }
        });
    }

    private void setDishAdapter(){
        otherDishAdatper = new OtherDishAdatper(getApplicationContext(),chefDishBeans);
        recyclerView.setAdapter(otherDishAdatper);
        otherDishAdatper.CuisionInterfaceMethod(this);
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

    public static int OTHER_DISH_CODE = 2;

    @Override
    public void CuisionInterfaceMethod(View view, int position) {
        Intent filrestintent=new Intent();
        filrestintent.putExtra("dish_id", chefDishBeans.get(position).getDish_id() + "");
        setResult(OTHER_DISH_CODE,filrestintent);
//                Log.d("VKK", gson.toJson(listModel));
        finish();
    }
}