package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.OtherDish;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.OtherDishAdatper;
import com.imcooking.fragment.foodie.HomeDetails;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OtherDishDishActivity extends AppBaseActivity implements OtherDishAdatper.OtherDishInterface {
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
    }

    private LinearLayout layout_main;
    private FrameLayout frame_main;
    private ImageView iv_cart;
    private TextView tv_count;

    private void init(){

        iv_cart = findViewById(R.id.activity_other_dish_cart_icon);
        tv_count = findViewById(R.id.activity_other_dish_cart_icon);


        layout_main = findViewById(R.id.other_dish_activity_layout);
        frame_main = findViewById(R.id.other_dish_activity_layout_frame);
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


        tinyDB = new TinyDB(getApplicationContext());
        userDataBean = new ApiResponse.UserDataBean();
        String s = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(s, ApiResponse.UserDataBean.class);
        foodie_id = userDataBean.getUser_id();

        String cart_count = tinyDB.getString("cart_count");
        tv_count.setText(cart_count);


        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), CartActivity.class).putExtra("foodie_id",
                        userDataBean.getUser_id()));
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
    }

    String chef_id; int foodie_id;
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean;

    private void getChefOtherList(String chef_id){
        layout.setVisibility(View.GONE);

        String detailRequest = "{\"chef_id\":" + chef_id +",\"foodie_id\":"+ HomeFragment.foodie_id+ "}";
        new GetData(getApplicationContext(),OtherDishDishActivity.this).getResponse(detailRequest,
                GetData.DISH_CHEF,
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
                                if (otherDish.getChef().getLike()>0){
                                    txtLike.setText(otherDish.getChef().getLike()+" Likes ");
                                } else txtLike.setText(0 +" Like");

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
                                            .placeholder(R.drawable.details_profile).into(imgPic);
                                }
                                if (chefDishBeans!=null && chefDishBeans.size()>0){
                                    setDishAdapter(chefDishBeans);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    private void setDishAdapter(List<OtherDish.ChefDishBean>chefDishBeans){
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

        layout_main.setVisibility(View.VISIBLE);
        frame_main.setVisibility(View.GONE);
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
    public void OtherDishInterfaceMethod(View view, int position, String TAG) {
        if (TAG.equalsIgnoreCase("name_detail")){
            if(MainActivity.my_tag.equals(new HomeFragment().getClass().getName()) ||
                    MainActivity.my_tag.equals(new HomeDetails().getClass().getName())) {

                HomeDetails fragment = new HomeDetails();
                Bundle bundle = new Bundle();
                bundle.putString("dish_id", chefDishBeans.get(position).getDish_id() + "");
                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.other_dish_activity_layout_frame, fragment)
//                        .addToBackStack("")
                        .commit();
                frame_main.setVisibility(View.VISIBLE);
                layout_main.setVisibility(View.GONE);
//                BaseClass.callFragment(fragment, fragment
//                        .getClass().getName(), getSupportFragmentManager());

            } else {
                Intent filrestintent = new Intent();
                filrestintent.putExtra("dish_id", chefDishBeans.get(position).getDish_id() + "");
                setResult(OTHER_DISH_CODE, filrestintent);
                finish();
            }
        } else if (TAG.equalsIgnoreCase("like")){
            dishlike(chefDishBeans.get(position).getDish_id()+"",position);
        }

    }


    private void dishlike(String dishid, final int position){
        String s ="{\"chef_id\":" + chef_id + ",\"foodie_id\":" + HomeFragment.foodie_id + ",\"dish_id\":" + dishid + "}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            Log.d("MyRequest", jsonObject.toString());

            new GetData(getApplicationContext(), OtherDishDishActivity.this).sendMyData(jsonObject,
                    GetData.DISH_LIKE, OtherDishDishActivity.this,
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject job = new JSONObject(result);
                                if(job.getBoolean("status")){
                                    if(job.getString("msg").equals("Successfully dish like")){
                                        BaseClass.showToast(getApplicationContext(), "Successfully Liked");
                                        chefDishBeans.get(position).setDishlike("1");
                                        int likeno = Integer.parseInt(chefDishBeans.get(position).getLikeno())+1;
                                        chefDishBeans.get(position).setLikeno(likeno+"");
                                        otherDishAdatper.notifyDataSetChanged();
                                    } else if(job.getString("msg").equals("Successfully unlike")){
                                        chefDishBeans.get(position).setDishlike("0");
                                        int likeno = Integer.parseInt(chefDishBeans.get(position).getLikeno())-1;
                                        chefDishBeans.get(position).setLikeno(likeno+"");
                                        otherDishAdatper.notifyDataSetChanged();
                                        BaseClass.showToast(getApplicationContext(), "Dish Successfully unliked");
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
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        frame_main.setVisibility(View.GONE);
//        layout_main.setVisibility(View.VISIBLE);
//        int i = getSupportFragmentManager().getBackStackEntryCount();
//        Toast.makeText(this, i + "", Toast.LENGTH_SHORT).show();


    }
}